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
import org.openflexo.module.traceanalysis.TraceAnalysisCst;
import org.openflexo.module.traceanalysis.TraceAnalysisIconLibrary;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.module.traceanalysis.model.action.CreateMask;
import org.openflexo.view.controller.ActionInitializer;
import org.openflexo.view.controller.ControllerActionInitializer;

public class MaskInitializer extends ActionInitializer<CreateMask, TraceVirtualModelInstance, FlexoObject> {

	private static final Logger logger = Logger.getLogger(ControllerActionInitializer.class.getPackage().getName());

	MaskInitializer(TraceAnalysisControllerActionInitializer actionInitializer) {
		super(CreateMask.actionType, actionInitializer);
	}

	@Override
	protected TraceAnalysisControllerActionInitializer getControllerActionInitializer() {
		return (TraceAnalysisControllerActionInitializer) super.getControllerActionInitializer();
	}

	@Override
	protected FlexoActionInitializer<CreateMask> getDefaultInitializer() {
		return new FlexoActionInitializer<CreateMask>() {
			@Override
			public boolean run(EventObject e, CreateMask action) {
				return instanciateAndShowDialog(action, TraceAnalysisCst.CREATE_MASK_DIALOG_FIB);
			}
		};
	}

	@Override
	protected FlexoActionFinalizer<CreateMask> getDefaultFinalizer() {
		return new FlexoActionFinalizer<CreateMask>() {
			@Override
			public boolean run(EventObject e, CreateMask action) {
				//getController().selectAndFocusObject(action.getMask());
				return true;
			}
		};
	}

	@Override
	protected Icon getEnabledIcon() {
		return TraceAnalysisIconLibrary.MASK_SMALL_ICON;
	}

}
