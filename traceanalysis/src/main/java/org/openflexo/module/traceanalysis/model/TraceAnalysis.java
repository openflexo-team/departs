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

import static org.junit.Assert.assertTrue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openflexo.foundation.DefaultFlexoObject;
import org.openflexo.foundation.InvalidArgumentException;
import org.openflexo.foundation.view.FlexoConceptInstance;
import org.openflexo.foundation.view.View;
import org.openflexo.foundation.view.VirtualModelInstance;
import org.openflexo.foundation.viewpoint.FlexoConcept;

public class TraceAnalysis extends DefaultFlexoObject implements PropertyChangeListener {
	
	private static final Logger logger = Logger.getLogger(TraceAnalysis.class.getPackage().getName());

	private final View view;

	private final TraceAnalysisProject traceAnalysisProject;
	private TraceVirtualModelInstance traceVirtualModelInstance ;
	private SystemVirtualModelInstance systemVirtualModelInstance ;
	private ContextVirtualModelInstance contextVirtualModelInstance;
	private ObserverVirtualModelInstance observerVirtualModelInstance;
	private ArrayList<TraceAnalysisVirtualModelInstance> traceAnalysisVirtualModelInstances;
	
	private List<ConfigurationMask> configurationMasks;

	public TraceAnalysis(View view, TraceAnalysisProject traceAnalysisProject) throws InvalidArgumentException {
		super();
		this.traceAnalysisProject = traceAnalysisProject;
		this.view = view;
		view.getPropertyChangeSupport().addPropertyChangeListener(this);
	}

	public TraceAnalysisProject getTraceAnalysisProject() {
		return traceAnalysisProject;
	}

	public View getView() {
		return view;
	}

	public TraceAnalysisProjectNature getProjectNature() {
		return getTraceAnalysisProject().getProjectNature();
	}

	public String getName() {
		return view.getName();
	}

	public String getURI() {
		return traceAnalysisProject.getProject().getURI() + "/" + getName();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource() == view) {
		
		}
	}
	
	private VirtualModelInstance getVirtualModelInstanceConformToNamedVirtualModel(String name){
		for (VirtualModelInstance vmi : getView().getVirtualModelInstances()) {
			if(vmi.getVirtualModel().getName().equals(name)){
				return vmi;
			}
		}
		return null;
	}
	
	public List<FlexoConceptInstance> getInstances(FlexoConcept concept) {
		return getVirtualModelInstanceConformToNamedVirtualModel(concept.getVirtualModel().getName()).getFlexoConceptInstances(concept);
	}
	
	public List<TraceAnalysisVirtualModelInstance> getTraceAnalysisVirtualModelInstances(){
		
		if(traceAnalysisVirtualModelInstances==null){
			traceAnalysisVirtualModelInstances = new ArrayList<TraceAnalysisVirtualModelInstance>();
			traceAnalysisVirtualModelInstances.add(getTraceVirtualModelInstance());
			traceAnalysisVirtualModelInstances.add(getContextVirtualModelInstance());
			traceAnalysisVirtualModelInstances.add(getSystemVirtualModelInstance());
			traceAnalysisVirtualModelInstances.add(getObserverVirtualModelInstance());
		}
		
		return traceAnalysisVirtualModelInstances;
	}
	
	public SystemVirtualModelInstance getSystemVirtualModelInstance() {
		if(systemVirtualModelInstance==null){
			try {
				systemVirtualModelInstance=new SystemVirtualModelInstance(getVirtualModelInstanceConformToNamedVirtualModel("SystemVirtualModel"));
			} catch (InvalidArgumentException e) {
				logger.log(Level.SEVERE, "No System found");
			}
		}
		return systemVirtualModelInstance;
	}
	
	public ContextVirtualModelInstance getContextVirtualModelInstance() {
		if(contextVirtualModelInstance==null){
			try {
				contextVirtualModelInstance=new ContextVirtualModelInstance(getVirtualModelInstanceConformToNamedVirtualModel("ContextVirtualModel"));
			} catch (InvalidArgumentException e) {
				logger.log(Level.SEVERE, "No Context found");
			}
		}
		return contextVirtualModelInstance;
	}
	
	public ObserverVirtualModelInstance getObserverVirtualModelInstance() {
		if(observerVirtualModelInstance==null){
			try {
				observerVirtualModelInstance=new ObserverVirtualModelInstance(getVirtualModelInstanceConformToNamedVirtualModel("ObserverVirtualModel"));
			} catch (InvalidArgumentException e) {
				logger.log(Level.SEVERE, "No Observer found");
			}
		}
		return observerVirtualModelInstance;
	}
	
	public TraceVirtualModelInstance getTraceVirtualModelInstance() {
		if(traceVirtualModelInstance==null){
			try {
				traceVirtualModelInstance=new TraceVirtualModelInstance(getVirtualModelInstanceConformToNamedVirtualModel("TraceVirtualModel"));
				if(configurationMasks==null){
					configurationMasks = new ArrayList<ConfigurationMask>();
				}
				if(traceVirtualModelInstance.getVirtualModelInstance()!=null){
					for(FlexoConceptInstance fciMask : getTraceVirtualModelInstance().getVirtualModelInstance().getFlexoConceptInstances(getMaskFlexoConcept())){
						ConfigurationMask mask = new ConfigurationMask(this,fciMask);
						configurationMasks.add(mask);
					}
				}
			} catch (InvalidArgumentException e) {
				logger.log(Level.SEVERE, "No Trace found");
			}
		}
		return traceVirtualModelInstance;
	}
	
	public void setSystemVirtualModelInstance(SystemVirtualModelInstance systemVirtualModelInstance) {
		this.systemVirtualModelInstance = systemVirtualModelInstance;
	}

	public void setContextVirtualModelInstance(ContextVirtualModelInstance contextVirtualModelInstance) {
		this.contextVirtualModelInstance = contextVirtualModelInstance;
	}

	public void setObserverVirtualModelInstance(ObserverVirtualModelInstance observerVirtualModelInstance) {
		this.observerVirtualModelInstance = observerVirtualModelInstance;
	}

	public void setTraceVirtualModelInstance(TraceVirtualModelInstance traceVirtualModelInstance) {
		this.traceVirtualModelInstance = traceVirtualModelInstance;
	}
	
	@Override
	public boolean delete() {
		super.delete();
		return true;
	}

	public List<ConfigurationMask> getConfigurationMasks() {
		return configurationMasks;
	}

	private FlexoConcept getMaskFlexoConcept(){
		return getTraceVirtualModelInstance().getVirtualModelInstance().getVirtualModel().getFlexoConcept("Mask");
	}
	
	public void setConfigurationMasks(List<ConfigurationMask> configurationMasks) {
		this.configurationMasks = configurationMasks;
	}
	
	public ConfigurationMask getNewConfigurationMask(){
		FlexoConceptInstance newFlexoConceptInstance = getTraceVirtualModelInstance().getVirtualModelInstance().makeNewFlexoConceptInstance(getMaskFlexoConcept());
		return getConfigurationMask(newFlexoConceptInstance);
	}
	
	public ConfigurationMask getConfigurationMask(FlexoConceptInstance flexoConceptInstance) {
		if(configurationMasks==null){
			configurationMasks = new ArrayList<ConfigurationMask>();
		}
		for(ConfigurationMask mask : getConfigurationMasks()){
			if(mask.getFlexoConceptInstance().equals(flexoConceptInstance)){
				return mask;
			}
		}
		ConfigurationMask mask = new ConfigurationMask(this,flexoConceptInstance);
		getConfigurationMasks().add(mask);
		getPropertyChangeSupport().firePropertyChange("configurationMasks", null, this);
		return mask;
	}
	
}
