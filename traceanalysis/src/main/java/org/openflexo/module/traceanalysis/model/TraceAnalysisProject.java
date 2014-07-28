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

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openflexo.foundation.DefaultFlexoObject;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.FlexoProject;
import org.openflexo.foundation.InvalidArgumentException;
import org.openflexo.foundation.nature.ProjectWrapper;
import org.openflexo.foundation.resource.ResourceLoadingCancelledException;
import org.openflexo.foundation.view.FlexoConceptInstance;
import org.openflexo.foundation.view.View;
import org.openflexo.foundation.view.VirtualModelInstance;
import org.openflexo.foundation.viewpoint.FlexoConcept;
import org.openflexo.foundation.viewpoint.ViewPoint;
import org.openflexo.foundation.viewpoint.VirtualModel;
import org.openflexo.foundation.viewpoint.rm.ViewPointResource;

public class TraceAnalysisProject extends DefaultFlexoObject implements ProjectWrapper<TraceAnalysisProjectNature> {

	private static final Logger logger = Logger.getLogger(TraceAnalysisProject.class.getPackage().getName());
	
	private final FlexoProject project;

	private final TraceAnalysisProjectNature projectNature;
	
	private final ViewPoint traceAnalysisViewPoint;
	private ArrayList<TraceAnalysisVirtualModelInstance> traceAnalysisVirtualModelInstances;
	
	private List<TraceVirtualModelInstance> traceVirtualModelInstances ;
	private SystemVirtualModelInstance systemVirtualModelInstance ;
	private ContextVirtualModelInstance contextVirtualModelInstance;
	private ObserverVirtualModelInstance observerVirtualModelInstance;
	
	private View traceAnalysisView;

	protected TraceAnalysisProject(FlexoProject project, TraceAnalysisProjectNature projectNature) throws FileNotFoundException,
			ResourceLoadingCancelledException, InvalidArgumentException, FlexoException {
		this.project = project;
		this.projectNature = projectNature;
		
		ViewPointResource traceAnalsyisViewPointResource = getProject().getServiceManager().getViewPointLibrary().getViewPointResource(TraceAnalysisProjectNature.TRACE_ANALYSIS_VIEWPOINT_RELATIVE_URI);
		
		try {
			traceAnalysisViewPoint = traceAnalsyisViewPointResource.getResourceData(null);
		} catch (Exception e) {
			throw new FlexoException(e);
		}

		if (traceAnalysisViewPoint == null) {
			throw new InvalidArgumentException("Could not load traceAnalysisViewPoint");
		}
	}

	public String getName() {
		return project.getProjectName();
	}

	@Override
	public FlexoProject getProject() {
		return project;
	}

	@Override
	public TraceAnalysisProjectNature getProjectNature() {
		return projectNature;
	}
	
	public ViewPoint getTraceAnaylsisViewPoint() {
		return traceAnalysisViewPoint;
	}
	
	public VirtualModel getTraceVirtualModel(){
		return getTraceAnaylsisViewPoint().getVirtualModelNamed("TraceVirtualModel");
	}
	public VirtualModel getContextVirtualModel(){
		return getTraceAnaylsisViewPoint().getVirtualModelNamed("ContextVirtualModel");
	}
	public VirtualModel getObserverVirtualModel(){
		return getTraceAnaylsisViewPoint().getVirtualModelNamed("ObserverVirtualModel");
	}
	public VirtualModel getSystemVirtualModel(){
		return getTraceAnaylsisViewPoint().getVirtualModelNamed("SystemVirtualModel");
	}
	
	private VirtualModelInstance getVirtualModelInstanceConformToNamedVirtualModel(String name){
		for (VirtualModelInstance vmi : traceAnalysisView.getVirtualModelInstances()) {
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
			traceAnalysisVirtualModelInstances.add(getContextVirtualModelInstance());
			traceAnalysisVirtualModelInstances.add(getSystemVirtualModelInstance());
			traceAnalysisVirtualModelInstances.add(getObserverVirtualModelInstance());
		}
		
		return traceAnalysisVirtualModelInstances;
	}
	
	public SystemVirtualModelInstance getSystemVirtualModelInstance() {
		if(systemVirtualModelInstance==null){
			try {
				systemVirtualModelInstance=new SystemVirtualModelInstance(getVirtualModelInstanceConformToNamedVirtualModel("SystemVirtualModel"), this);
			} catch (InvalidArgumentException e) {
				logger.log(Level.SEVERE, "No System found");
			}
		}
		return systemVirtualModelInstance;
	}
	
	public ContextVirtualModelInstance getContextVirtualModelInstance() {
		if(contextVirtualModelInstance==null){
			try {
				contextVirtualModelInstance=new ContextVirtualModelInstance(getVirtualModelInstanceConformToNamedVirtualModel("ContextVirtualModel"), this);
			} catch (InvalidArgumentException e) {
				logger.log(Level.SEVERE, "No Context found");
			}
		}
		return contextVirtualModelInstance;
	}
	
	public ObserverVirtualModelInstance getObserverVirtualModelInstance() {
		if(observerVirtualModelInstance==null){
			try {
				observerVirtualModelInstance=new ObserverVirtualModelInstance(getVirtualModelInstanceConformToNamedVirtualModel("ObserverVirtualModel"), this);
			} catch (InvalidArgumentException e) {
				logger.log(Level.SEVERE, "No Observer found");
			}
		}
		return observerVirtualModelInstance;
	}
	
	public List<TraceVirtualModelInstance> getTraceVirtualModelInstances() {
		if(traceVirtualModelInstances==null){
			traceVirtualModelInstances=new ArrayList<TraceVirtualModelInstance>();
		}
		return traceVirtualModelInstances;
	}
	
	public TraceVirtualModelInstance getTraceVirtualModelInstance(VirtualModelInstance vmi) {
		for(TraceVirtualModelInstance traceVirtualModelInstance : getTraceVirtualModelInstances()){
			if(traceVirtualModelInstance.getVirtualModelInstance().getURI().equals(vmi.getURI())){
				return traceVirtualModelInstance;
			}
		}

		try {
			TraceVirtualModelInstance traceVirtualModelInstance = new TraceVirtualModelInstance(getVirtualModelInstanceConformToNamedVirtualModel("TraceVirtualModel"), this);
			getTraceVirtualModelInstances().add(traceVirtualModelInstance);
			getPropertyChangeSupport().firePropertyChange("traceVirtualModelInstances", null, getTraceVirtualModelInstances());
			return traceVirtualModelInstance;
		} catch (InvalidArgumentException e) {
			logger.log(Level.SEVERE, "Trace virtual model instance cannot be instanciated");
		};
		return null;
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

	public View getTraceAnalysisView() {
		return traceAnalysisView;
	}

	public void setTraceAnalysisView(View traceAnalysisView) {
		this.traceAnalysisView = traceAnalysisView;
	}

}