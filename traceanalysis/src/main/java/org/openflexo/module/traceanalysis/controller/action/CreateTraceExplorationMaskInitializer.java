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
import org.openflexo.foundation.action.FlexoActionFinalizer;
import org.openflexo.foundation.action.FlexoActionInitializer;
import org.openflexo.module.traceanalysis.TAECst;
import org.openflexo.module.traceanalysis.TAEIconLibrary;
import org.openflexo.module.traceanalysis.model.TAEProject;
import org.openflexo.module.traceanalysis.model.TraceAnalysis;
import org.openflexo.module.traceanalysis.model.action.CreateTraceAnalysis;
import org.openflexo.module.traceanalysis.model.action.CreateTraceExplorationMask;
import org.openflexo.view.controller.ActionInitializer;
import org.openflexo.view.controller.ControllerActionInitializer;

public class CreateTraceExplorationMaskInitializer extends ActionInitializer<CreateTraceExplorationMask, TraceAnalysis, FlexoObject> {

	private static final Logger logger = Logger.getLogger(ControllerActionInitializer.class.getPackage().getName());

	CreateTraceExplorationMaskInitializer(TAEControllerActionInitializer actionInitializer) {
		super(CreateTraceExplorationMask.actionType, actionInitializer);
	}

	@Override
	protected TAEControllerActionInitializer getControllerActionInitializer() {
		return (TAEControllerActionInitializer) super.getControllerActionInitializer();
	}

	@Override
	protected FlexoActionInitializer<CreateTraceExplorationMask> getDefaultInitializer() {
		return new FlexoActionInitializer<CreateTraceExplorationMask>() {
			@Override
			public boolean run(EventObject e, CreateTraceExplorationMask action) {
				return instanciateAndShowDialog(action, TAECst.CREATE_TRACE_EXPLORATION_MASK_DIALOG_FIB);
			}
		};
	}

	@Override
	protected FlexoActionFinalizer<CreateTraceExplorationMask> getDefaultFinalizer() {
		return new FlexoActionFinalizer<CreateTraceExplorationMask>() {
			@Override
			public boolean run(EventObject e, CreateTraceExplorationMask action) {
				return true;
			}
		};
	}

	@Override
	protected Icon getEnabledIcon() {
		return TAEIconLibrary.MASK_SMALL_ICON;
	}

}
