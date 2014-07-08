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

	private final TAEProject taeProject;
	private TAEAnalyze taeAnalyze ;
	private TAESystem taeSystem ;
	private TAEContext taeContext;
	private TAEObserver taeObserver;
	private ArrayList<TAEObject> taeObjects;
	
	private List<TraceExplorationMask> traceExplorationMasks;

	public TraceAnalysis(View view, TAEProject taeProject) throws InvalidArgumentException {
		super();
		this.taeProject = taeProject;
		this.view = view;
		setTraceExplorationMasks(new ArrayList<TraceExplorationMask>());
		view.getPropertyChangeSupport().addPropertyChangeListener(this);
	}

	public TAEProject getTAEProject() {
		return taeProject;
	}

	public View getView() {
		return view;
	}

	public TAEProjectNature getProjectNature() {
		return getTAEProject().getProjectNature();
	}

	public String getName() {
		return view.getName();
	}

	public String getURI() {
		return taeProject.getProject().getURI() + "/" + getName();
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
	
	public List<TAEObject> getTAEObjects(){
		
		if(taeObjects==null){
			taeObjects = new ArrayList<TAEObject>();
			taeObjects.add(getTaeAnalyze());
			taeObjects.add(getTAEContext());
			taeObjects.add(getTAEObserver());
			taeObjects.add(getTAESystem());
		}
		
		return taeObjects;
	}
	
	public TAESystem getTAESystem() {
		if(taeSystem==null){
			try {
				taeSystem=new TAESystem(getVirtualModelInstanceConformToNamedVirtualModel("SystemVirtualModel"));
			} catch (InvalidArgumentException e) {
				logger.log(Level.SEVERE, "No System found");
			}
		}
		return taeSystem;
	}
	
	public TAEContext getTAEContext() {
		if(taeContext==null){
			try {
				taeContext=new TAEContext(getVirtualModelInstanceConformToNamedVirtualModel("ContextVirtualModel"));
			} catch (InvalidArgumentException e) {
				logger.log(Level.SEVERE, "No Context found");
			}
		}
		return taeContext;
	}
	
	public TAEObserver getTAEObserver() {
		if(taeObserver==null){
			try {
				taeObserver=new TAEObserver(getVirtualModelInstanceConformToNamedVirtualModel("ObserverVirtualModel"));
			} catch (InvalidArgumentException e) {
				logger.log(Level.SEVERE, "No Observer found");
			}
		}
		return taeObserver;
	}
	
	public TAEAnalyze getTaeAnalyze() {
		if(taeAnalyze==null){
			try {
				taeAnalyze=new TAEAnalyze(getVirtualModelInstanceConformToNamedVirtualModel("AnalyzeVirtualModel"));
			} catch (InvalidArgumentException e) {
				logger.log(Level.SEVERE, "No Analyze found");
			}
		}
		return taeAnalyze;
	}
	
	public void setTAESystem(TAESystem system) {
		this.taeSystem = system;
	}

	public void setTAEContext(TAEContext context) {
		this.taeContext = context;
	}

	public void setTAEObserver(TAEObserver observer) {
		this.taeObserver = observer;
	}

	public void setTaeAnalyze(TAEAnalyze taeAnalyze) {
		this.taeAnalyze = taeAnalyze;
	}
	
	@Override
	public boolean delete() {
		super.delete();
		return true;
	}

	public List<TraceExplorationMask> getTraceExplorationMasks() {
		return traceExplorationMasks;
	}

	public void setTraceExplorationMasks(List<TraceExplorationMask> traceExplorationMasks) {
		this.traceExplorationMasks = traceExplorationMasks;
	}
	
	
}
