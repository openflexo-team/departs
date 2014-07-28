/*
 * (c) Copyright 2010-2011 AgileBirds
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
package org.openflexo.module.traceanalysis.model.action;

import java.util.Vector;
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.FlexoObject.FlexoObjectImpl;
import org.openflexo.foundation.FlexoProject;
import org.openflexo.foundation.action.FlexoAction;
import org.openflexo.foundation.action.FlexoActionType;
import org.openflexo.foundation.resource.SaveResourceException;
import org.openflexo.module.traceanalysis.model.TraceAnalysisProjectNature;

/**
 * This action is used to gives {@link TraceAnalysisProjectNature} to a {@link FlexoProject}<br>
 * 
 * 
 * @author vincent
 * 
 */
public class ConvertToTraceAnalysisProject extends FlexoAction<ConvertToTraceAnalysisProject, FlexoProject, FlexoObject> {

	private static final Logger logger = Logger.getLogger(ConvertToTraceAnalysisProject.class.getPackage().getName());

	public static FlexoActionType<ConvertToTraceAnalysisProject, FlexoProject, FlexoObject> actionType = new FlexoActionType<ConvertToTraceAnalysisProject, FlexoProject, FlexoObject>(
			"trace_analysis_project", FlexoActionType.convertMenu, FlexoActionType.defaultGroup, FlexoActionType.NORMAL_ACTION_TYPE) {

		/**
		 * Factory method
		 */
		@Override
		public ConvertToTraceAnalysisProject makeNewAction(FlexoProject focusedObject, Vector<FlexoObject> globalSelection,
				FlexoEditor editor) {
			return new ConvertToTraceAnalysisProject(focusedObject, globalSelection, editor);
		}

		@Override
		public boolean isVisibleForSelection(FlexoProject project, Vector<FlexoObject> globalSelection) {
			TraceAnalysisProjectNature nature = project.getServiceManager().getProjectNatureService()
					.getProjectNature(TraceAnalysisProjectNature.class);
			return !project.hasNature(nature);
		}

		@Override
		public boolean isEnabledForSelection(FlexoProject project, Vector<FlexoObject> globalSelection) {
			return isVisibleForSelection(project, globalSelection);
		}

	};

	static {
		FlexoObjectImpl.addActionForClass(ConvertToTraceAnalysisProject.actionType, FlexoProject.class);
	}

	ConvertToTraceAnalysisProject(FlexoProject focusedObject, Vector<FlexoObject> globalSelection, FlexoEditor editor) {
		super(actionType, focusedObject, globalSelection, editor);
	}

	@Override
	protected void doAction(Object context) throws SaveResourceException {

		logger.info("Convert to TraceAnalysisProject");

		getTraceAnalysisProjectNature().givesNature(getFocusedObject(), getEditor());

	}

	public TraceAnalysisProjectNature getTraceAnalysisProjectNature() {
		return getServiceManager().getProjectNatureService().getProjectNature(TraceAnalysisProjectNature.class);
	}

}
