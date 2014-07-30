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
package org.openflexo.technologyadapter.trace.gui.view;

import org.openflexo.technologyadapter.trace.controller.TraceCst;
import org.openflexo.technologyadapter.trace.model.OBPTrace;
import org.openflexo.view.FIBModuleView;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.model.FlexoPerspective;

/**
 * Widget allowing to edit/view a Trace.<br>
 * 
 * @author vleilde
 * 
 */
@SuppressWarnings("serial")
public class FIBTraceView extends FIBModuleView<OBPTrace> {

	public FIBTraceView(OBPTrace traceOBP, FlexoController controller) {
		super(traceOBP, controller, TraceCst.TRACE_OBP_VIEW_FIB);
	}

	@Override
	public FlexoPerspective getPerspective() {
		return getFlexoController().getCurrentPerspective();
	}

}
