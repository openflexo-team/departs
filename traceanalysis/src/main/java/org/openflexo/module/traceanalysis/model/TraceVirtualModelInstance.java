/*
 * (c) Copyright 2013-2014 Openflexo
 *
 * This file is part of OpenFlexo.
 *
 * OpenFlexo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenFlexo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenFlexo. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.openflexo.module.traceanalysis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.InvalidArgumentException;
import org.openflexo.foundation.view.FlexoConceptInstance;
import org.openflexo.foundation.view.FreeModelSlotInstance;
import org.openflexo.foundation.view.VirtualModelInstance;
import org.openflexo.foundation.viewpoint.FlexoConcept;
import org.openflexo.technologyadapter.cdl.model.CDLObserverState;
import org.openflexo.technologyadapter.cdl.model.CDLParActivity;
import org.openflexo.technologyadapter.cdl.model.CDLProperty;
import org.openflexo.technologyadapter.fiacre.model.FiacreProcess;
import org.openflexo.technologyadapter.fiacre.model.FiacreState;
import org.openflexo.technologyadapter.fiacre.model.FiacreVariable;
import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObject;
import org.openflexo.technologyadapter.trace.model.OBPTraceConfiguration;
import org.openflexo.technologyadapter.trace.model.OBPTraceContext;
import org.openflexo.technologyadapter.trace.model.OBPTraceData;
import org.openflexo.technologyadapter.trace.model.OBPTraceObject;
import org.openflexo.technologyadapter.trace.model.OBPTraceProcess;
import org.openflexo.technologyadapter.trace.model.OBPTrace;
import org.openflexo.technologyadapter.trace.model.OBPTraceProperty;
import org.openflexo.technologyadapter.trace.model.OBPTraceState;
import org.openflexo.technologyadapter.trace.model.OBPTraceTransition;

public class TraceVirtualModelInstance extends TraceAnalysisVirtualModelInstance {
	
	private static final Logger logger = Logger.getLogger(TraceVirtualModelInstance.class.getPackage().getName());
	
	// The trace is a set of configurations and transitions
	private OBPTrace trace;
	
	private HashMap<FlexoObject, List<OBPTraceObject>> federatedTraceObjects;
	
	// A list of masks
	private List<ConfigurationMask> configurationMasks;
	private Mask selectedMask;
	
	public TraceVirtualModelInstance(VirtualModelInstance virtualModelInstance, TraceAnalysisProject traceAnalysisProject)
			throws InvalidArgumentException {
		super(virtualModelInstance,traceAnalysisProject);
		for(FlexoConceptInstance fciMask : getVirtualModelInstance().getFlexoConceptInstances(getConfigurationMaskFlexoConcept())){
			ConfigurationMask mask = new ConfigurationMask(fciMask, traceAnalysisProject, this);
			getConfigurationMasks().add(mask);
		}
		
		// TEMP create associations between trace and others
		federatedTraceObjects = new HashMap<FlexoObject, List<OBPTraceObject>>();
		for(OBPTraceConfiguration conf :getConfigurations()){
			for(OBPTraceBehaviourObject behaviourObject: conf.getBehaviourObjects()){
				FlexoObject object = getFlexoObjectNamed(behaviourObject.getType());
				List<OBPTraceObject> traceObjects = federatedTraceObjects.get(object);
				if(traceObjects==null){
					traceObjects = new ArrayList<OBPTraceObject>();
				}
				if(!traceObjects.contains(behaviourObject)){
					traceObjects.add(behaviourObject);
				}
				federatedTraceObjects.put(object, traceObjects);
			}
		}
	}
	
	private FlexoObject getFlexoObjectNamed(String name){
		if(getTraceAnalysisProject().getContextVirtualModelInstance().getName().equals(name)){
			return getTraceAnalysisProject().getContextVirtualModelInstance();
		}
		for(CDLProperty property: getTraceAnalysisProject().getObserverVirtualModelInstance().getCDLProperties()){
			if(property!=null && property.getName().equals(name)){
				return property;
			}
		}
		for(FiacreProcess process : getTraceAnalysisProject().getSystemVirtualModelInstance().getFiacreProcesses()){
			if(process!=null && process.getName().equals(name)){
				return process;
			}
		}
		return null;
	}
	
	public List<OBPTraceObject> getOBPTraceObjects(FlexoObject object){
		return federatedTraceObjects.get(object);
	}
	
	// Get the trace
	public OBPTrace getTraceOBP(){
		if(trace==null){
			FreeModelSlotInstance msi = (FreeModelSlotInstance) getVirtualModelInstance().getModelSlotInstance("traceModelSlot");
			trace = (OBPTrace) msi.getAccessedResourceData();
		}
		return trace;
	}
	
	// Get the whole set of Transitions
	public List<OBPTraceTransition> getTransitions(){
		return getTraceOBP().getTransitions();
	}

	// Get the whole set of Configurations
	public List<OBPTraceConfiguration> getConfigurations(){
		return getTraceOBP().getConfigurations();
	}
	
	// Get the configuration from its ID
	public OBPTraceConfiguration getConfiguration(int id){
		return getTraceOBP().getConfigurations().get(id);
	}
	
	// Get the value of a configuration's object
	public String getConfigurationValue(FlexoObject object, int config){
		OBPTraceConfiguration configuration = getTraceOBP().getConfigurations().get(config);
		return getConfigurationValue(object, configuration);
	}
	// Get the value of a configuration's object
	public String getConfigurationValue(FlexoObject object, OBPTraceConfiguration configuration){
		if(getConfigurationObject(object, configuration)!=null){
			return getConfigurationObject(object, configuration).getValue();
		}else{
			return null;
		}
	}
	
	// Get the object of a configuration
	public OBPTraceObject getConfigurationObject(FlexoObject object, int config){
		OBPTraceConfiguration configuration = getTraceOBP().getConfigurations().get(config);
		return getConfigurationObject(object, configuration);
	}
	// Get the object of a configuration
	public OBPTraceObject getConfigurationObject(FlexoObject object, OBPTraceConfiguration configuration){
		if(object instanceof FiacreProcess){
			return getProcessObject((FiacreProcess)object, configuration);
		} else if (object instanceof FiacreVariable){
			return getVariableObject((FiacreVariable)object, configuration);
		} else if(object instanceof FiacreState){
			return getStateObject((FiacreState)object, configuration);
		} else if(object instanceof CDLProperty){
			return getPropertyObject((CDLProperty)object, configuration);
		} else if(object instanceof CDLParActivity){
			return getContextObject((CDLParActivity)object, configuration);
		} else if(object instanceof CDLObserverState){
			return getObserverStateObject((CDLObserverState)object, configuration);
		}
		logger.log(Level.WARNING, "No value found for object " + object.toString());
		return null;
	}
	
	public OBPTraceContext getContextObject(CDLParActivity cdlParActivity, OBPTraceConfiguration configuration){
		OBPTraceContext context = configuration.getOBPTraceContext();
		if( context == null ){
			logger.log(Level.WARNING, "No value found for context " + context.getName());
			return null;
		}else{
			return context;
		}
	}
	
	public OBPTraceProperty getPropertyObject(CDLProperty property, OBPTraceConfiguration configuration){
		for(OBPTraceProperty traceProperty : configuration.getOBPTraceProperties()){
			if(traceProperty.getType().equals(property.getName())){
				return traceProperty;
			}
		}
		logger.log(Level.WARNING, "No value found for property " + property.getName());
		return null;
	}
	
	public OBPTraceProcess getProcessObject(FiacreProcess process, OBPTraceConfiguration configuration){
		for(OBPTraceProcess traceProcess : configuration.getOBPTraceProcesses()){
			if(traceProcess.getType().equals(process.getName())){
				return traceProcess;
			}
		}
		logger.log(Level.WARNING, "No value found for process " + process.getName());
		return null;
	}
	
	public OBPTraceData getVariableObject(FiacreVariable variable, OBPTraceConfiguration configuration){
		for(OBPTraceData traceData : getProcessObject(variable.getFiacreProcess(),configuration).getOBPTraceData()){
			if(traceData.getName().equals(variable.getName())){
				return traceData;
			}
		}
		logger.log(Level.WARNING, "No value found for variable " + variable.getName());
		return null;
	}
	
	public OBPTraceState getStateObject(FiacreState state, OBPTraceConfiguration configuration){
		if(getProcessObject(state.getFiacreProcess(),configuration).getState()!=null &&
				getProcessObject(state.getFiacreProcess(),configuration).getState().getName().equals(state.getName())){
			return (OBPTraceState) getProcessObject(state.getFiacreProcess(),configuration).getState();
		}
		logger.log(Level.WARNING, "No value found for state " + state.getName());
		return null;
	}

	public OBPTraceState getObserverStateObject(CDLObserverState state, OBPTraceConfiguration configuration){
		if(getPropertyObject(state.getCDLObserver(),configuration).getState()!=null &&
				getPropertyObject(state.getCDLObserver(),configuration).getState().getName().equals(state.getName())){
			return (OBPTraceState) getPropertyObject(state.getCDLObserver(),configuration).getState();
		}
		logger.log(Level.WARNING, "No value found for state " + state.getName());
		return null;
	}
	
	public List<ConfigurationMask> getConfigurationMasks() {
		if(configurationMasks==null){
			configurationMasks = new ArrayList<ConfigurationMask>();
		}
		return configurationMasks;
	}
	
	public ConfigurationMask getConfigurationMask(String name) {
		for(ConfigurationMask mask :configurationMasks){
			if(mask.getName().equals(name)){
				return mask;
			}
		}
		logger.log(Level.SEVERE, "No configuration mask named "+ name + " for trace " + getName());
		return null;
	}

	private FlexoConcept getConfigurationMaskFlexoConcept(){
		return getVirtualModelInstance().getVirtualModel().getFlexoConcept("ConfigurationMask");
	}
	
	public void setConfigurationMasks(List<ConfigurationMask> configurationMasks) {
		this.configurationMasks = configurationMasks;
	}
	
	public ConfigurationMask getNewConfigurationMask(){
		FlexoConceptInstance newFlexoConceptInstance = getVirtualModelInstance().makeNewFlexoConceptInstance(getConfigurationMaskFlexoConcept());
		return getConfigurationMask(newFlexoConceptInstance);
	}
	
	public ConfigurationMask getConfigurationMask(FlexoConceptInstance flexoConceptInstance) {
		for(ConfigurationMask mask : getConfigurationMasks()){
			if(mask.getFlexoConceptInstance().equals(flexoConceptInstance)){
				return mask;
			}
		}
		ConfigurationMask mask = new ConfigurationMask(flexoConceptInstance, getTraceAnalysisProject(), this);
		getConfigurationMasks().add(mask);
		getPropertyChangeSupport().firePropertyChange("configurationMasks", null, this);
		return mask;
	}
	
	public Mask getSelectedMask() {
		return selectedMask;
	}

	public void setSelectedMask(Mask selectedMask) {
		this.selectedMask = selectedMask;
	}
	
}
