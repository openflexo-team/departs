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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openflexo.antar.binding.TypeUtils;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.InvalidArgumentException;
import org.openflexo.foundation.view.FlexoConceptInstance;
import org.openflexo.foundation.view.FreeModelSlotInstance;
import org.openflexo.foundation.view.VirtualModelInstance;
import org.openflexo.foundation.viewpoint.FlexoConcept;
import org.openflexo.module.traceanalysis.model.mask.Mask;
import org.openflexo.module.traceanalysis.model.mask.MaskedConfiguration;
import org.openflexo.module.traceanalysis.model.mask.MaskedElement;
import org.openflexo.module.traceanalysis.model.mask.MaskedFederatedElement;
import org.openflexo.module.traceanalysis.model.mask.MaskedTransition;
import org.openflexo.technologyadapter.cdl.model.CDLObserverState;
import org.openflexo.technologyadapter.cdl.model.CDLParActivity;
import org.openflexo.technologyadapter.cdl.model.CDLProperty;
import org.openflexo.technologyadapter.fiacre.model.FiacreProcess;
import org.openflexo.technologyadapter.fiacre.model.FiacreState;
import org.openflexo.technologyadapter.fiacre.model.FiacreVariable;
import org.openflexo.technologyadapter.trace.model.OBPTrace;
import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObjectInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceConfiguration;
import org.openflexo.technologyadapter.trace.model.OBPTraceContextState;
import org.openflexo.technologyadapter.trace.model.OBPTraceData;
import org.openflexo.technologyadapter.trace.model.OBPTraceObject;
import org.openflexo.technologyadapter.trace.model.OBPTraceProcessState;
import org.openflexo.technologyadapter.trace.model.OBPTracePropertyState;
import org.openflexo.technologyadapter.trace.model.OBPTraceState;
import org.openflexo.technologyadapter.trace.model.OBPTraceTransition;
import org.openflexo.technologyadapter.trace.model.OBPTraceVariable;

public class TraceVirtualModelInstance extends TraceAnalysisVirtualModelInstance {
	
	private static final Logger logger = Logger.getLogger(TraceVirtualModelInstance.class.getPackage().getName());
	
	// The trace is a set of configurations and transitions
	private OBPTrace trace;
	
	private List<FederatedElement> federatedElements;

	// A list of masks
	private List<Mask> masks;
	private Mask selectedMask;
	
	// A list of masked elements
	private List<MaskedElement<?>> maskedElements;
	
	public TraceVirtualModelInstance(VirtualModelInstance virtualModelInstance, TraceAnalysisProject traceAnalysisProject)
			throws InvalidArgumentException {
		super(virtualModelInstance,traceAnalysisProject);
		for(FlexoConceptInstance fciMask : getVirtualModelInstance().getFlexoConceptInstances(getMaskFlexoConcept())){
			Mask mask = new Mask(fciMask, traceAnalysisProject, this);
			getMasks().add(mask);
		}
		maskedElements = new ArrayList<MaskedElement<?>>();
		federatedElements = new ArrayList<FederatedElement>();
		
		federate();
	}
	
	private void federate(){
		federateBehaviourObjects();
	}
	
	/**
	 * Create associations among all OBP resources which would'nt be effecient through viewpoints
	 */
	private void federateBehaviourObjects(){
		// Associate BehaviourObject instance with their corresponding System or context element
		for(OBPTraceBehaviourObjectInstance behaviourObjectInstance : getTraceOBP().getOBPTraceBehaviourObjectInstances()){
			FederatedElement element = new FederatedElement(getFlexoBehaviourObjectObjectNamed(behaviourObjectInstance.getType()), behaviourObjectInstance);	
			federatedElements.add(element);
			// Associate Data Types with their corresponding System or context element
			federateVariables(element);
		}
	}
	
	private void federateVariables(FederatedElement element){
		for(OBPTraceVariable variable : ((OBPTraceBehaviourObjectInstance)element.getValue()).getVariables()){
			FlexoObject object = getVariableFlexoObjectNamed(variable.getName(),element.getType());
			federatedElements.add(new FederatedElement(object, variable));	
		}
	}
	
	public List<FederatedElement> getFederatedElements() {
		return federatedElements;
	}
	
	public List<FederatedElement> getFilteredFederatedElement(Class<?> objectKind){
		List<FederatedElement> filteredElements = new ArrayList<FederatedElement>();
		for(FederatedElement object : federatedElements){
			
			if(TypeUtils.isAssignableTo(object.getValue(), objectKind)){
				filteredElements.add(object);
			}else if(TypeUtils.isAssignableTo(object.getType(), objectKind)){
				filteredElements.add(object);
			}
		}
		return filteredElements;
	}
	
	private FlexoObject getFlexoBehaviourObjectObjectNamed(String name){
		if(name.equals("env")){
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
	
	private FlexoObject getVariableFlexoObjectNamed(String name,FlexoObject object){
		if(object instanceof FiacreProcess){
			return ((FiacreProcess)object).getFiacreVariableNamed(name);
		}
		// TODO (context...)
		return null;
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
	
	public OBPTraceContextState getContextObject(CDLParActivity cdlParActivity, OBPTraceConfiguration configuration){
		OBPTraceContextState context = configuration.getOBPTraceContextState();
		if( context == null ){
			logger.log(Level.WARNING, "No value found for context " + context.getName());
			return null;
		}else{
			return context;
		}
	}
	
	public OBPTracePropertyState getPropertyObject(CDLProperty property, OBPTraceConfiguration configuration){
		for(OBPTracePropertyState traceProperty : configuration.getOBPTracePropertyState()){
			if(traceProperty.getOBPTraceBehaviourObjectInstance().getType().equals(property.getName())){
				return traceProperty;
			}
		}
		logger.log(Level.WARNING, "No value found for property " + property.getName());
		return null;
	}
	
	public OBPTraceProcessState getProcessObject(FiacreProcess process, OBPTraceConfiguration configuration){
		for(OBPTraceProcessState traceProcess : configuration.getOBPTraceProcessState()){
			if(traceProcess.getOBPTraceBehaviourObjectInstance().getType().equals(process.getName())){
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
	
	public List<Mask> getMasks() {
		if(masks==null){
			masks = new ArrayList<Mask>();
		}
		return masks;
	}
	
	public Mask getMask(String name) {
		for(Mask mask :masks){
			if(mask.getName().equals(name)){
				return mask;
			}
		}
		logger.log(Level.SEVERE, "No mask named "+ name + " for trace " + getName());
		return null;
	}

	private FlexoConcept getMaskFlexoConcept(){
		return getVirtualModelInstance().getVirtualModel().getFlexoConcept("ConfigurationMask");
	}
	
	public void setMasks(List<Mask> masks) {
		this.masks = masks;
	}
	
	public Mask getNewMask(){
		FlexoConceptInstance newFlexoConceptInstance = getVirtualModelInstance().makeNewFlexoConceptInstance(getMaskFlexoConcept());
		return getMask(newFlexoConceptInstance);
	}
	
	public Mask getMask(FlexoConceptInstance flexoConceptInstance) {
		for(Mask mask : getMasks()){
			if(mask.getFlexoConceptInstance().equals(flexoConceptInstance)){
				return mask;
			}
		}
		Mask mask = new Mask(flexoConceptInstance, getTraceAnalysisProject(), this);
		getMasks().add(mask);
		getPropertyChangeSupport().firePropertyChange("masks", null, this);
		return mask;
	}
	
	public Mask getSelectedMask() {
		return selectedMask;
	}

	public void setSelectedMask(Mask selectedMask) {
		this.selectedMask = selectedMask;
		getPropertyChangeSupport().firePropertyChange("selectedMask", null, selectedMask);
	}
	
	public MaskedElement<OBPTraceConfiguration> getMaskedElement(OBPTraceConfiguration object){
		for(MaskedElement maskedElement : maskedElements){
			if(maskedElement.getMaskedElement().equals(object)){
				return maskedElement;
			}
		}
		MaskedElement newMaskedElement = new MaskedConfiguration(object, selectedMask);
		maskedElements.add(newMaskedElement);
		return newMaskedElement;		
	}
	
	public MaskedElement<OBPTraceTransition> getMaskedElement(OBPTraceTransition object){
		for(MaskedElement maskedElement : maskedElements){
			if(maskedElement.getMaskedElement().equals(object)){
				return maskedElement;
			}
		}
		MaskedElement newMaskedElement = new MaskedTransition(object, selectedMask);
		maskedElements.add(newMaskedElement);
		return newMaskedElement;		
	}
	
	public MaskedElement getMaskedElement(FederatedElement federatedElement){
		for(MaskedElement maskedElement : maskedElements){
			if(maskedElement.getMaskedElement().equals(federatedElement)){
				return maskedElement;
			}
		}
		MaskedElement newMaskedElement = new MaskedFederatedElement(federatedElement, selectedMask);
		maskedElements.add(newMaskedElement);
		return newMaskedElement;		
	}
	
	public class FederatedElement{
		
		private final FlexoObject type;
		
		private final OBPTraceObject value;

		public FederatedElement(FlexoObject type, OBPTraceObject value) {
			super();
			this.type = type;
			this.value = value;
		}

		public FlexoObject getType() {
			return type;
		}

		public OBPTraceObject getValue() {
			return value;
		}		
	}
	
}
