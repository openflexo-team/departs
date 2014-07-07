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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openflexo.foundation.DefaultFlexoObject;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.FlexoProject;
import org.openflexo.foundation.InvalidArgumentException;
import org.openflexo.foundation.nature.ProjectWrapper;
import org.openflexo.foundation.resource.ResourceLoadingCancelledException;
import org.openflexo.foundation.view.View;
import org.openflexo.foundation.view.VirtualModelInstance;
import org.openflexo.foundation.view.rm.ViewResource;
import org.openflexo.foundation.viewpoint.ViewPoint;
import org.openflexo.foundation.viewpoint.VirtualModel;
import org.openflexo.foundation.viewpoint.rm.ViewPointResource;

public class TAEProject extends DefaultFlexoObject implements ProjectWrapper<TAEProjectNature> {

	private final FlexoProject project;

	private final TAEProjectNature projectNature;
	
	private final ViewPoint traceAnalysisViewPoint;
	private final View traceAnalysisView;
	private final Map<VirtualModelInstance, TraceAnalysis> traceAnalysis;

	protected TAEProject(FlexoProject project, TAEProjectNature projectNature) throws FileNotFoundException,
			ResourceLoadingCancelledException, InvalidArgumentException, FlexoException {
		this.project = project;
		this.projectNature = projectNature;
		
		ViewPointResource taeViewPointResource = getProject().getServiceManager().getViewPointLibrary().getViewPointResource(TAEProjectNature.TRACE_ANALYSIS_VIEWPOINT_RELATIVE_URI);

		ViewResource taeViewResource = project.getViewLibrary().getResource(
				project.getURI() + TAEProjectNature.TRACE_ANALYSIS_VIEW_RELATIVE_URI);

		if (taeViewResource == null) {
			throw new InvalidArgumentException("Could not retrieve TraceAnalysisView resource (searched uri="
					+ (project.getURI() + TAEProjectNature.TRACE_ANALYSIS_VIEW_RELATIVE_URI) + ")");
		}

		traceAnalysis = new HashMap<VirtualModelInstance, TraceAnalysis>();
		
		try {
			traceAnalysisViewPoint = taeViewPointResource.getResourceData(null);
			traceAnalysisView = taeViewResource.getResourceData(null);
		} catch (Exception e) {
			throw new FlexoException(e);
		}

		if (traceAnalysisViewPoint == null) {
			throw new InvalidArgumentException("Could not load traceAnalysisViewPoint");
		}
		if (traceAnalysisView == null) {
			throw new InvalidArgumentException("Could not load traceAnalysisView");
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
	public TAEProjectNature getProjectNature() {
		return projectNature;
	}
	
	public ViewPoint getTraceAnaylsisViewPoint() {
		return traceAnalysisViewPoint;
	}
	
	public VirtualModel getAnalyzeVirtualModel(){
		return getTraceAnaylsisViewPoint().getVirtualModelNamed("AnalyzeVirtualModel");
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

	public View getTraceAnalysisView() {
		return traceAnalysisView;
	}
	
	public List<TraceAnalysis> getTraceAnalysis() {
		List<TraceAnalysis> returned = new ArrayList<TraceAnalysis>();
		for (VirtualModelInstance vmi : getTraceAnalysisView().getVirtualModelInstances()) {
			try {
				returned.add(getTraceAnalysis(vmi));
			} catch (InvalidArgumentException e) {
				TAEProjectNature.logger.warning(e.getMessage());
			}
		}
		return returned;
	}
	
	public TraceAnalysis getTraceAnalysis(VirtualModelInstance virtualModelInstance) throws InvalidArgumentException {
		TraceAnalysis returned = traceAnalysis.get(virtualModelInstance);
		if (returned == null) {
			returned = new TraceAnalysis(virtualModelInstance, this);
			traceAnalysis.put(virtualModelInstance, returned);
			getPropertyChangeSupport().firePropertyChange("getTraceAnalysis()", null, traceAnalysis);
		}
		return returned;
	}

	public TraceAnalysis getFreeModel(String traceAnalysisName) {
		for (TraceAnalysis traceAnalysis : getTraceAnalysis()) {
			if (traceAnalysis.getName().equals(traceAnalysisName)) {
				return traceAnalysis;
			}
		}
		return null;
	}

}