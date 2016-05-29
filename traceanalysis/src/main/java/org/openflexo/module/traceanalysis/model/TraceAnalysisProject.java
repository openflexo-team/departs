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
import org.openflexo.foundation.FlexoServiceManager;
import org.openflexo.foundation.InvalidArgumentException;
import org.openflexo.foundation.fml.FlexoConcept;
import org.openflexo.foundation.fml.ViewPoint;
import org.openflexo.foundation.fml.ViewPointLibrary;
import org.openflexo.foundation.fml.VirtualModel;
import org.openflexo.foundation.fml.rm.ViewPointResource;
import org.openflexo.foundation.fml.rt.AbstractVirtualModelInstance;
import org.openflexo.foundation.fml.rt.FlexoConceptInstance;
import org.openflexo.foundation.fml.rt.View;
import org.openflexo.foundation.fml.rt.ViewLibrary;
import org.openflexo.foundation.fml.rt.VirtualModelInstance;
import org.openflexo.foundation.nature.ProjectWrapper;
import org.openflexo.foundation.resource.ResourceLoadingCancelledException;

/**
 * A Trace Analysis project is based on a the depart viewpoint. Such project is a view conformed to this viewpoint. This view is made of a
 * System vitrual model instance, an observer virtual model instance, a context virtual model instance as well as a set of trace analysis
 * virtual model instance. At the creation of the project a CDL and a System files are required to create the System, Observer and Context
 * VMI. Then the user can create a set of Trace analysis VMI, each requiring a trace file.
 * 
 * @author Vincent
 *
 */
public class TraceAnalysisProject extends DefaultFlexoObject implements ProjectWrapper<TraceAnalysisProjectNature> {

	private static final Logger logger = Logger.getLogger(TraceAnalysisProject.class.getPackage().getName());

	public static String VIEWPOINT_REPOSITORY = "Viewpoint";

	private final FlexoProject project;

	private final TraceAnalysisProjectNature projectNature;

	private final ViewPoint traceAnalysisViewPoint;

	// The while set of virtual model instance created for the project
	private ArrayList<TraceAnalysisVirtualModelInstance> traceAnalysisVirtualModelInstances;
	// The set of trace virtual model instance created in the project
	private List<TraceVirtualModelInstance> traceVirtualModelInstances;
	// The unique system VMI created for the project
	private SystemVirtualModelInstance systemVirtualModelInstance;
	// The unique context VMI created for the project
	private ContextVirtualModelInstance contextVirtualModelInstance;
	// The unique observer VMI created for the project
	private ObserverVirtualModelInstance observerVirtualModelInstance;

	// The view instanciated for the project and conformed to the traceAnalysisViewPoint
	private View traceAnalysisView;

	protected TraceAnalysisProject(FlexoProject project, TraceAnalysisProjectNature projectNature)
			throws FileNotFoundException, ResourceLoadingCancelledException, InvalidArgumentException, FlexoException {
		this.project = project;
		this.projectNature = projectNature;

		traceAnalysisViewPoint = retrieveViewpoint();

		if (traceAnalysisViewPoint == null) {
			throw new InvalidArgumentException("Trace Analysis ViewPoint should not be null");
		}

		// Retrieve views which are conformed to this viewpoint
		if (getViewLibrary().getViewsForViewPointWithURI(TraceAnalysisProjectNature.TRACE_ANALYSIS_VIEWPOINT_RELATIVE_URI).size() > 0) {
			traceAnalysisView = project.getViewLibrary()
					.getViewsForViewPointWithURI(TraceAnalysisProjectNature.TRACE_ANALYSIS_VIEWPOINT_RELATIVE_URI).get(0);
		}

		if (traceAnalysisView != null) {
			logger.info("Trace Analysis view " + traceAnalysisView.getName() + " retrieved");
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

	/*public void setSystemVirtualModelInstance(SystemVirtualModelInstance systemVirtualModelInstance) {
		this.systemVirtualModelInstance = systemVirtualModelInstance;
	}
	
	public void setContextVirtualModelInstance(ContextVirtualModelInstance contextVirtualModelInstance) {
		this.contextVirtualModelInstance = contextVirtualModelInstance;
	}
	
	public void setObserverVirtualModelInstance(ObserverVirtualModelInstance observerVirtualModelInstance) {
		this.observerVirtualModelInstance = observerVirtualModelInstance;
	}*/

	public View getTraceAnalysisView() {
		return traceAnalysisView;
	}

	public void setTraceAnalysisView(View traceAnalysisView) {
		this.traceAnalysisView = traceAnalysisView;
	}

	public VirtualModel getTraceVirtualModel() {
		return getTraceAnaylsisViewPoint().getVirtualModelNamed("TraceVirtualModel");
	}

	public VirtualModel getContextVirtualModel() {
		return getTraceAnaylsisViewPoint().getVirtualModelNamed("ContextVirtualModel");
	}

	public VirtualModel getObserverVirtualModel() {
		return getTraceAnaylsisViewPoint().getVirtualModelNamed("ObserverVirtualModel");
	}

	public VirtualModel getSystemVirtualModel() {
		return getTraceAnaylsisViewPoint().getVirtualModelNamed("SystemVirtualModel");
	}

	public List<FlexoConceptInstance> getInstances(FlexoConcept concept) {
		return getVirtualModelInstanceConformToNamedVirtualModel(concept.getVirtualModel().getName()).getFlexoConceptInstances(concept);
	}

	public List<TraceAnalysisVirtualModelInstance> getTraceAnalysisVirtualModelInstances() {

		if (traceAnalysisVirtualModelInstances == null) {
			traceAnalysisVirtualModelInstances = new ArrayList<TraceAnalysisVirtualModelInstance>();
			traceAnalysisVirtualModelInstances.addAll(getTraceVirtualModelInstances());
			traceAnalysisVirtualModelInstances.add(getContextVirtualModelInstance());
			traceAnalysisVirtualModelInstances.add(getSystemVirtualModelInstance());
			traceAnalysisVirtualModelInstances.add(getObserverVirtualModelInstance());
		}

		return traceAnalysisVirtualModelInstances;
	}

	public SystemVirtualModelInstance getSystemVirtualModelInstance() {
		if (systemVirtualModelInstance == null) {
			try {
				systemVirtualModelInstance = new SystemVirtualModelInstance(
						getVirtualModelInstanceConformToNamedVirtualModel("SystemVirtualModel"), this);
			} catch (InvalidArgumentException e) {
				logger.log(Level.SEVERE, "No System found");
			}
		}
		return systemVirtualModelInstance;
	}

	public ContextVirtualModelInstance getContextVirtualModelInstance() {
		if (contextVirtualModelInstance == null) {
			try {
				contextVirtualModelInstance = new ContextVirtualModelInstance(
						getVirtualModelInstanceConformToNamedVirtualModel("ContextVirtualModel"), this);
			} catch (InvalidArgumentException e) {
				logger.log(Level.SEVERE, "No Context found");
			}
		}
		return contextVirtualModelInstance;
	}

	public ObserverVirtualModelInstance getObserverVirtualModelInstance() {
		if (observerVirtualModelInstance == null) {
			try {
				observerVirtualModelInstance = new ObserverVirtualModelInstance(
						getVirtualModelInstanceConformToNamedVirtualModel("ObserverVirtualModel"), this);
			} catch (InvalidArgumentException e) {
				logger.log(Level.SEVERE, "No Observer found");
			}
		}
		return observerVirtualModelInstance;
	}

	public List<TraceVirtualModelInstance> getTraceVirtualModelInstances() {
		if (traceVirtualModelInstances == null) {
			traceVirtualModelInstances = new ArrayList<TraceVirtualModelInstance>();
			if (traceAnalysisView == null) {
				logger.log(Level.SEVERE, "No view is declared for this project");
				// getServiceManager().getProjectNatureService().getProjectNature(TraceAnalysisProjectNature.class).givesNature(getProject(),
				// getServiceManager());
				return null;
			}
			for (AbstractVirtualModelInstance<?, ?> vmi : traceAnalysisView.getVirtualModelInstances()) {
				if (vmi.getVirtualModel().getName().equals("TraceVirtualModel")) {
					createTraceVirtualModelInstance((VirtualModelInstance) vmi);
				}
			}
		}
		return traceVirtualModelInstances;
	}

	/**
	 * Return a Trace virtual model instance of this project from a given name, otherwise create it
	 * 
	 * @param name
	 * @return
	 */
	public TraceVirtualModelInstance getTraceVirtualModelInstance(VirtualModelInstance vmi) {
		for (TraceVirtualModelInstance traceVirtualModelInstance : getTraceVirtualModelInstances()) {
			if (traceVirtualModelInstance.getVirtualModelInstance().getURI().equals(vmi.getURI())) {
				return traceVirtualModelInstance;
			}
		}
		return createTraceVirtualModelInstance(vmi);
	}

	/**
	 * Create a trace virtual model instance
	 * 
	 * @param vmi
	 * @return
	 */
	private TraceVirtualModelInstance createTraceVirtualModelInstance(VirtualModelInstance vmi) {
		TraceVirtualModelInstance traceVirtualModelInstance;
		try {
			traceVirtualModelInstance = new TraceVirtualModelInstance(vmi, this);
			getTraceVirtualModelInstances().add(traceVirtualModelInstance);
			getPropertyChangeSupport().firePropertyChange("traceVirtualModelInstances", null, getTraceVirtualModelInstances());
			return traceVirtualModelInstance;
		} catch (InvalidArgumentException e) {
			logger.log(Level.SEVERE, "Problem occurs while creating a trace virtual model instance");
			return null;
		}
	}

	/**
	 * Return a virtual model instance of this project from a given name
	 * 
	 * @param name
	 * @return
	 */
	private VirtualModelInstance getVirtualModelInstanceConformToNamedVirtualModel(String name) {
		for (AbstractVirtualModelInstance<?, ?> vmi : traceAnalysisView.getVirtualModelInstances()) {
			if (vmi.getVirtualModel().getName().equals(name)) {
				return (VirtualModelInstance) vmi;
			}
		}
		logger.log(Level.WARNING, "No virtual model instance named " + name + " found in this project");
		return null;
	}

	@Override
	public FlexoServiceManager getServiceManager() {
		FlexoServiceManager returned = super.getServiceManager();
		if (returned == null) {
			return getProject().getServiceManager();
		}
		return returned;
	}

	private ViewPointLibrary getViewPointLibrary() {
		return getServiceManager().getViewPointLibrary();
	}

	private ViewLibrary getViewLibrary() {
		return getProject().getViewLibrary();
	}

	private ViewPoint retrieveViewpoint() throws FlexoException {
		try {
			// Retrieve the viewpoint resource from its URI. The viewpoint resource must be in a resource center!
			ViewPointResource traceAnalsyisViewPointResource = getViewPointLibrary()
					.getViewPointResource(TraceAnalysisProjectNature.TRACE_ANALYSIS_VIEWPOINT_RELATIVE_URI);
			return traceAnalsyisViewPointResource.getResourceData(null);
		} catch (Exception e) {
			logger.severe("No trace analysis viewpoint found resource centers");
			throw new FlexoException(e);
		}
	}
}
