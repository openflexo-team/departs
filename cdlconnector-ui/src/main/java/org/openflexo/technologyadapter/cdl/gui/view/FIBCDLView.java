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
package org.openflexo.technologyadapter.cdl.gui.view;

import org.openflexo.technologyadapter.cdl.controller.CDLCst;
import org.openflexo.technologyadapter.cdl.model.CDLUnit;
import org.openflexo.view.FIBModuleView;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.model.FlexoPerspective;

/**
 * Widget allowing to edit/view a CDL Unit.<br>
 * 
 * @author vleilde
 * 
 */
@SuppressWarnings("serial")
public class FIBCDLView extends FIBModuleView<CDLUnit> {

	public FIBCDLView(CDLUnit cdlUnit, FlexoController controller) {
		super(cdlUnit, controller, CDLCst.CDL_UNIT_VIEW_FIB);
	}

	@Override
	public FlexoPerspective getPerspective() {
		return getFlexoController().getCurrentPerspective();
	}

}
