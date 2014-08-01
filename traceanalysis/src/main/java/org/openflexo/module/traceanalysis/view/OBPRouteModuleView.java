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
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.openflexo.fge.FGEModelFactory;
import org.openflexo.fge.FGEModelFactoryImpl;
import org.openflexo.fge.swing.control.SwingToolFactory;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.module.traceanalysis.view.routeview.OBPRoute;
import org.openflexo.module.traceanalysis.view.routeview.OBPRouteEditor;
import org.openflexo.module.traceanalysis.view.routeview.OBPRouteImpl;
import org.openflexo.module.traceanalysis.view.routeview.chronogram.ChronogramDrawing;
import org.openflexo.module.traceanalysis.view.routeview.sequencediagram.SequenceDiagramDrawing;
import org.openflexo.module.traceanalysis.view.routeview.sequencediagram.SequenceDiagramDrawing.RouteLayout;
import org.openflexo.technologyadapter.trace.model.OBPTrace;
import org.openflexo.view.ModuleView;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.model.FlexoPerspective;

@SuppressWarnings("serial")
public class OBPRouteModuleView extends JPanel implements ModuleView<TraceVirtualModelInstance>, PropertyChangeListener {

	private final FlexoPerspective perspective;
    private final TraceVirtualModelInstance traceVirtualModelInstance;
    private final FlexoController controller;
	
	public OBPRouteModuleView(TraceVirtualModelInstance traceVirtualModelInstance, FlexoController controller, FlexoPerspective perspective) {
		super(new GridBagLayout());
		
		this.perspective = perspective;
		this.controller = controller;
		this.traceVirtualModelInstance = traceVirtualModelInstance;
		
		FGEModelFactory factory = null;
		try {
			factory = new FGEModelFactoryImpl();
		} catch (ModelDefinitionException e) {
			e.printStackTrace();
		}
	
		
		OBPRoute route = new OBPRouteImpl(traceVirtualModelInstance.getTraceOBP());
		final SequenceDiagramDrawing sequenceDiagram = new SequenceDiagramDrawing(route, factory, RouteLayout.VERTICAL);
		
		ChronogramDrawing chronogram = new ChronogramDrawing(route, factory);
		OBPRouteEditor sequence = new OBPRouteEditor(sequenceDiagram,factory, SwingToolFactory.DEFAULT);
		OBPRouteEditor chrono = new OBPRouteEditor(chronogram,factory, SwingToolFactory.DEFAULT);

		// Test layout
		JButton switchLayout = new JButton("Change Layout");
		switchLayout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(sequenceDiagram.getLayout()==RouteLayout.VERTICAL){
					sequenceDiagram.setLayout(RouteLayout.HORIZONTAL);
				}
				else{
					sequenceDiagram.setLayout(RouteLayout.VERTICAL);
				}
			}
		});
		
		
		GridBagConstraints c1 = new GridBagConstraints();
		c1.fill = GridBagConstraints.HORIZONTAL;
		c1.weightx = 1;
		c1.gridx = 0;
		c1.gridy = 0;
		
		GridBagConstraints c2 = new GridBagConstraints();
		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.weightx = 1;
		c2.gridx = 0;
		c2.gridy = 1;
		
		add(sequence.getDrawingView(),c1);
		add(chrono.getDrawingView(),c2);
		add(switchLayout);
		
		sequenceDiagram.printGraphicalObjectHierarchy();
		chronogram.printGraphicalObjectHierarchy();
		
		//getRepresentedObject().getPropertyChangeSupport().addPropertyChangeListener(getRepresentedObject().getDeletedProperty(), this);
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TraceVirtualModelInstance getRepresentedObject() {
		// TODO Auto-generated method stub
		return traceVirtualModelInstance;
	}

	@Override
	public void deleteModuleView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FlexoPerspective getPerspective() {
		// TODO Auto-generated method stub
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
