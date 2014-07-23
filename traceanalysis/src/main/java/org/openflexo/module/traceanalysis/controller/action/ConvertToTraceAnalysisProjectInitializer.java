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
package org.openflexo.module.traceanalysis.controller.action;

import java.util.EventObject;
import java.util.logging.Logger;

import javax.swing.Icon;

import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.FlexoProject;
import org.openflexo.foundation.action.FlexoActionFinalizer;
import org.openflexo.foundation.action.FlexoActionInitializer;
import org.openflexo.module.traceanalysis.TraceAnalysisIconLibrary;
import org.openflexo.module.traceanalysis.controller.TraceAnalysisPerspective;
import org.openflexo.module.traceanalysis.model.action.ConvertToTraceAnalysisProject;
import org.openflexo.view.controller.ActionInitializer;
import org.openflexo.view.controller.ControllerActionInitializer;

public class ConvertToTraceAnalysisProjectInitializer extends
		ActionInitializer<ConvertToTraceAnalysisProject, FlexoProject, FlexoObject> {

	private static final Logger logger = Logger.getLogger(ControllerActionInitializer.class.getPackage().getName());

	ConvertToTraceAnalysisProjectInitializer(TraceAnalysisControllerActionInitializer actionInitializer) {
		super(ConvertToTraceAnalysisProject.actionType, actionInitializer);
	}

	@Override
	protected TraceAnalysisControllerActionInitializer getControllerActionInitializer() {
		return (TraceAnalysisControllerActionInitializer) super.getControllerActionInitializer();
	}

	@Override
	protected FlexoActionInitializer<ConvertToTraceAnalysisProject> getDefaultInitializer() {
		return new FlexoActionInitializer<ConvertToTraceAnalysisProject>() {
			@Override
			public boolean run(EventObject e, ConvertToTraceAnalysisProject action) {
				return true;
			}
		};
	}

	@Override
	protected FlexoActionFinalizer<ConvertToTraceAnalysisProject> getDefaultFinalizer() {
		return new FlexoActionFinalizer<ConvertToTraceAnalysisProject>() {
			@Override
			public boolean run(EventObject e, ConvertToTraceAnalysisProject action) {
				if (getController().getCurrentPerspective() instanceof TraceAnalysisPerspective) {
					((TraceAnalysisPerspective) getController().getCurrentPerspective()).setProject(action.getFocusedObject());
				}
				getController().selectAndFocusObject(
						action.getTraceAnalysisProjectNature().getTraceAnalysisProject(action.getFocusedObject()));
				return true;
			}
		};
	}

	@Override
	protected Icon getEnabledIcon() {
		return TraceAnalysisIconLibrary.TA_SMALL_ICON;
	}

}
