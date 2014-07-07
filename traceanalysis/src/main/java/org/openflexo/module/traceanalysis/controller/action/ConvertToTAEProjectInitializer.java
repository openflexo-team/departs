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
import org.openflexo.module.traceanalysis.TAEIconLibrary;
import org.openflexo.module.traceanalysis.controller.TAEPerspective;
import org.openflexo.module.traceanalysis.model.action.ConvertToTAEProject;
import org.openflexo.view.controller.ActionInitializer;
import org.openflexo.view.controller.ControllerActionInitializer;

public class ConvertToTAEProjectInitializer extends
		ActionInitializer<ConvertToTAEProject, FlexoProject, FlexoObject> {

	private static final Logger logger = Logger.getLogger(ControllerActionInitializer.class.getPackage().getName());

	ConvertToTAEProjectInitializer(TAEControllerActionInitializer actionInitializer) {
		super(ConvertToTAEProject.actionType, actionInitializer);
	}

	@Override
	protected TAEControllerActionInitializer getControllerActionInitializer() {
		return (TAEControllerActionInitializer) super.getControllerActionInitializer();
	}

	@Override
	protected FlexoActionInitializer<ConvertToTAEProject> getDefaultInitializer() {
		return new FlexoActionInitializer<ConvertToTAEProject>() {
			@Override
			public boolean run(EventObject e, ConvertToTAEProject action) {
				return true;
			}
		};
	}

	@Override
	protected FlexoActionFinalizer<ConvertToTAEProject> getDefaultFinalizer() {
		return new FlexoActionFinalizer<ConvertToTAEProject>() {
			@Override
			public boolean run(EventObject e, ConvertToTAEProject action) {
				if (getController().getCurrentPerspective() instanceof TAEPerspective) {
					((TAEPerspective) getController().getCurrentPerspective()).setProject(action.getFocusedObject());
				}
				getController().selectAndFocusObject(
						action.getTAEProjectNature().getTAEProject(action.getFocusedObject()));
				return true;
			}
		};
	}

	@Override
	protected Icon getEnabledIcon() {
		return TAEIconLibrary.TAE_SMALL_ICON;
	}

}
