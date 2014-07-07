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

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoProject;
import org.openflexo.foundation.nature.ProjectNature;
import org.openflexo.foundation.nature.ProjectNatureService;
import org.openflexo.foundation.view.action.CreateView;
import org.openflexo.foundation.view.rm.ViewResource;
import org.openflexo.foundation.viewpoint.rm.ViewPointResource;
import org.openflexo.logging.FlexoLogger;

public class TAEProjectNature implements ProjectNature<TAEProjectNature, TAEProject> {

	static final Logger logger = FlexoLogger.getLogger(TAEProjectNature.class.getPackage().getName());

	private ProjectNatureService projectNatureService;

	public static final String TRACE_ANALYSIS_VIEW_NAME = "TraceAnalaysisView";
	public static final String TRACE_ANALYSIS_VIEW_RELATIVE_URI = "/" + TRACE_ANALYSIS_VIEW_NAME;
	public static final String TRACE_ANALYSIS_VIEWPOINT_NAME = "http://depart.v1";
	public static final String TRACE_ANALYSIS_VIEWPOINT_RELATIVE_URI = TRACE_ANALYSIS_VIEWPOINT_NAME;

	private final Map<FlexoProject, TAEProject> taeProjects;

	// Never call this: this is done via services
	public TAEProjectNature() {
		taeProjects = new HashMap<FlexoProject, TAEProject>();
	}

	@Override
	public void setProjectNatureService(ProjectNatureService projectNatureService) {
		this.projectNatureService = projectNatureService;
	}

	@Override
	public ProjectNatureService getProjectNatureService() {
		return projectNatureService;
	}

	/**
	 * Return boolean indicating if supplied project might be interpreted according to this nature
	 * 
	 * @param concept
	 * @return
	 */
	@Override
	public boolean hasNature(FlexoProject project) {
		if (project == null) {
			return false;
		}
		if (project.getServiceManager().getViewPointLibrary().getViewPoints().size() == 0) {
			return false;
		}
		/*if (project.getViewLibrary().getAllResources().size() == 0) {
			return false;
		}*/
		TAEProject factory = getTAEProject(project);
		if (factory == null) {
			return false;
		}
		return true;
	}

	/**
	 * Return wrapping object representing the interpretation of supplied project with this nature
	 * 
	 * @param project
	 * @return
	 */
	@Override
	public TAEProject getProjectWrapper(FlexoProject project) {
		return getTAEProject(project);
	}

	/**
	 * Gives to supplied FlexoProject this nature
	 * 
	 * @return
	 */
	@Override
	public void givesNature(FlexoProject project, FlexoEditor editor) {

		ViewPointResource traceAnalysisViewPointResource = editor.getServiceManager().getViewPointLibrary().getViewPointResource(TRACE_ANALYSIS_VIEWPOINT_RELATIVE_URI);
		
		if (traceAnalysisViewPointResource == null) {
			logger.log(Level.SEVERE, "No trace analysis viewpoint found in resource centers");
		}

		/*ViewResource traceAnalysisViewResource = project.getViewLibrary().getResource(
				project.getURI() + TRACE_ANALYSIS_VIEWPOINT_RELATIVE_URI);

		if (traceAnalysisViewResource == null) {
			CreateView action = CreateView.actionType.makeNewAction(project.getViewLibrary().getRootFolder(), null, editor);
			action.setNewViewName(TRACE_ANALYSIS_VIEW_NAME);
			action.setNewViewTitle(TRACE_ANALYSIS_VIEW_NAME);
			action.setViewpointResource(traceAnalysisViewPointResource);
			action.doAction();
			traceAnalysisViewResource = (ViewResource) action.getNewView().getResource();
		}*/

	}


	public TAEProject getTAEProject(FlexoProject project) {
		TAEProject returned = taeProjects.get(project);
		if (returned == null) {
			try {
				returned = new TAEProject(project, this);
				taeProjects.put(project, returned);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return returned;
	}

}
