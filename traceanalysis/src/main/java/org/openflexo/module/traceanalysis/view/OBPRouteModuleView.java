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
package org.openflexo.module.traceanalysis.view;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JScrollPane;

import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.traceanalysis.view.routeview.OBPRouteEditor;
import org.openflexo.view.ModuleView;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.model.FlexoPerspective;

@SuppressWarnings("serial")
public class OBPRouteModuleView extends JScrollPane implements ModuleView<TraceVirtualModelInstance>, PropertyChangeListener {

    private final FlexoPerspective perspective;
    private final TraceVirtualModelInstance traceVirtualModelInstance;
    private final FlexoController controller;
	
	public OBPRouteModuleView(OBPRouteEditor editor, FlexoController controller, FlexoPerspective perspective) {
		super(editor.getDrawingView());

		this.perspective = perspective;
		this.controller = controller;
		this.traceVirtualModelInstance = editor.getTraceVmi();

		getRepresentedObject().getPropertyChangeSupport().addPropertyChangeListener(getRepresentedObject().getDeletedProperty(), this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TraceVirtualModelInstance getRepresentedObject() {
		return traceVirtualModelInstance;
	}

	@Override
	public void deleteModuleView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FlexoPerspective getPerspective() {
		return perspective;
	}

	@Override
	public void willShow() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void willHide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show(FlexoController controller, FlexoPerspective perspective) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAutoscrolled() {
		// TODO Auto-generated method stub
		return false;
	}

	
}
