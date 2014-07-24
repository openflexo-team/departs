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

import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.InvalidArgumentException;
import org.openflexo.foundation.view.FlexoConceptInstance;
import org.openflexo.foundation.view.FreeModelSlotInstance;
import org.openflexo.foundation.view.VirtualModelInstance;
import org.openflexo.foundation.viewpoint.FlexoConcept;
import org.openflexo.technologyadapter.fiacre.model.FiacreProcess;
import org.openflexo.technologyadapter.fiacre.model.FiacreState;
import org.openflexo.technologyadapter.fiacre.model.FiacreVariable;
import org.openflexo.technologyadapter.trace.model.FlexoTraceOBPConfigData;
import org.openflexo.technologyadapter.trace.model.FlexoTraceOBPData;
import org.openflexo.technologyadapter.trace.model.FlexoTraceOBPObject;
import org.openflexo.technologyadapter.trace.model.FlexoTraceOBPProcess;
import org.openflexo.technologyadapter.trace.model.FlexoTraceOBP;
import org.openflexo.technologyadapter.trace.model.FlexoTraceOBPState;

public class TraceVirtualModelInstance extends TraceAnalysisVirtualModelInstance {
	
	private static final Logger logger = Logger.getLogger(TraceVirtualModelInstance.class.getPackage().getName());
	
	private List<ConfigurationMask> configurationMasks;
	
	public TraceVirtualModelInstance(VirtualModelInstance virtualModelInstance, TraceAnalysis traceAnalysis)
			throws InvalidArgumentException {
		super(virtualModelInstance,traceAnalysis);
		for(FlexoConceptInstance fciMask : getVirtualModelInstance().getFlexoConceptInstances(getMaskFlexoConcept())){
			ConfigurationMask mask = new ConfigurationMask(fciMask, traceAnalysis);
			getConfigurationMasks().add(mask);
		}
	}

	public List<FlexoTraceOBPConfigData> getConfigurations(){
		return getTraceOBP().getFlexoConfigData();
	}
	
	public FlexoTraceOBPConfigData getConfiguration(int id){
		return getTraceOBP().getFlexoConfigData().get(id);
	}
	
	public FlexoTraceOBP getTraceOBP(){
		FreeModelSlotInstance msi = (FreeModelSlotInstance) getVirtualModelInstance().getModelSlotInstance("traceModelSlot");
		return (FlexoTraceOBP) msi.getAccessedResourceData();
	}
	
	public String getConfigurationValue(FlexoObject object, int config){
		FlexoTraceOBPConfigData configData = getTraceOBP().getFlexoConfigData().get(config);
		return getConfigurationValue(object, configData);
	}
	
	public String getConfigurationValue(FlexoObject object, FlexoTraceOBPConfigData configData){
		if(getConfigurationObject(object, configData)!=null){
			return getConfigurationObject(object, configData).getValue();
		}else{
			return null;
		}
	}
	
	public FlexoTraceOBPObject getConfigurationObject(FlexoObject object, int config){
		FlexoTraceOBPConfigData configData = getTraceOBP().getFlexoConfigData().get(config);
		return getConfigurationObject(object, configData);
	}
	
	public FlexoTraceOBPObject getConfigurationObject(FlexoObject object, FlexoTraceOBPConfigData configData){
		if(object instanceof FiacreProcess){
			return getProcessObject((FiacreProcess)object, configData);
		} else if (object instanceof FiacreVariable){
			return getVariableObject((FiacreVariable)object, configData);
		} else if(object instanceof FiacreState){
			return getStateObject((FiacreState)object, configData);
		}
		logger.log(Level.WARNING, "No value found for object " + object.toString());
		return null;
	}
	
	public FlexoTraceOBPProcess getProcessObject(FiacreProcess process, FlexoTraceOBPConfigData configData){
		for(FlexoTraceOBPProcess traceProcess : configData.getFlexoProcess()){
			if(traceProcess.getProcessType().equals(process.getName())){
				return traceProcess;
			}
		}
		logger.log(Level.WARNING, "No value found for process " + process.getName());
		return null;
	}
	
	public FlexoTraceOBPData getVariableObject(FiacreVariable variable, FlexoTraceOBPConfigData configData){
		for(FlexoTraceOBPData traceData : getProcessObject(variable.getFiacreProcess(),configData).getFlexoData()){
			if(traceData.getName().equals(variable.getName())){
				return traceData;
			}
		}
		logger.log(Level.WARNING, "No value found for variable " + variable.getName());
		return null;
	}
	
	public FlexoTraceOBPState getStateObject(FiacreState state, FlexoTraceOBPConfigData configData){
		if(getProcessObject(state.getFiacreProcess(),configData).getState()!=null &&
				getProcessObject(state.getFiacreProcess(),configData).getState().getName().equals(state.getName())){
			return (FlexoTraceOBPState) getProcessObject(state.getFiacreProcess(),configData).getState();
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

	private FlexoConcept getMaskFlexoConcept(){
		return getVirtualModelInstance().getVirtualModel().getFlexoConcept("Mask");
	}
	
	public void setConfigurationMasks(List<ConfigurationMask> configurationMasks) {
		this.configurationMasks = configurationMasks;
	}
	
	public ConfigurationMask getNewConfigurationMask(){
		FlexoConceptInstance newFlexoConceptInstance = getVirtualModelInstance().makeNewFlexoConceptInstance(getMaskFlexoConcept());
		return getConfigurationMask(newFlexoConceptInstance);
	}
	
	public ConfigurationMask getConfigurationMask(FlexoConceptInstance flexoConceptInstance) {
		for(ConfigurationMask mask : getConfigurationMasks()){
			if(mask.getFlexoConceptInstance().equals(flexoConceptInstance)){
				return mask;
			}
		}
		ConfigurationMask mask = new ConfigurationMask(flexoConceptInstance, getTraceAnalysis());
		getConfigurationMasks().add(mask);
		getPropertyChangeSupport().firePropertyChange("configurationMasks", null, this);
		return mask;
	}
	
	
}
