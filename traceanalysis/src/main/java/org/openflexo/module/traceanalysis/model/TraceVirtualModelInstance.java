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
import org.openflexo.technologyadapter.trace.model.FlexoTraceOBPConfigData;
import org.openflexo.technologyadapter.trace.model.FlexoTraceOBPProcess;
import org.openflexo.technologyadapter.trace.model.FlexoTraceOBP;

public class TraceVirtualModelInstance extends TraceAnalysisVirtualModelInstance {
	
	private static final Logger logger = Logger.getLogger(TraceVirtualModelInstance.class.getPackage().getName());
	
	private List<ConfigurationMask> configurationMasks;
	
	public enum ParamKind {
		STATE
	}
	
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
	
	
	public FlexoObject getFlexoConfigDataValue(FlexoObject object, int config){
		FlexoTraceOBPConfigData configData = getTraceOBP().getFlexoConfigData().get(config);
		if(object instanceof FiacreProcess){
			for(FlexoTraceOBPProcess process : configData.getFlexoProcess()){
				if(process.getProcessType().equals(((FiacreProcess)object).getName())){
					return process;
				}
			}
		}
		return null;
	}
	
	public FlexoObject getFlexoConfigDataValue(FlexoObject object, FlexoTraceOBPConfigData configData){
		if(object instanceof FiacreProcess){
			for(FlexoTraceOBPProcess process : configData.getFlexoProcess()){
				if(process.getProcessType().equals(((FiacreProcess)object).getName())){
					return process;
				}
			}
		}
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
