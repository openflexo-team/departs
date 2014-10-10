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

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import org.openflexo.module.traceanalysis.TraceAnalysisIconLibrary;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.module.traceanalysis.view.routeview.OBPRoute;
import org.openflexo.module.traceanalysis.view.routeview.OBPRouteEditor;
import org.openflexo.module.traceanalysis.view.routeview.OBPRouteImpl;
import org.openflexo.module.traceanalysis.view.routeview.sequencediagram.SequenceDiagramDrawing;
import org.openflexo.module.traceanalysis.view.routeview.sequencediagram.SequenceDiagramDrawing.RouteLayout;
import org.openflexo.view.ModuleView;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.model.FlexoPerspective;

@SuppressWarnings("serial")
public class OBPRouteModuleView extends JPanel implements ModuleView<TraceVirtualModelInstance>, PropertyChangeListener {

	private final FlexoPerspective perspective;
    private final TraceVirtualModelInstance traceVirtualModelInstance;
    private final FlexoController controller;
    private final JButton switchLayout;
   // private final JButton applyMask;
    private final JButton resetMask;
    private final JPanel boutonsPanel;
    private final SequenceDiagramDrawing sequenceDiagram;
    final JScrollPane scrollPan;
    final FGEModelFactory factory;
    
	public OBPRouteModuleView(final TraceVirtualModelInstance traceVirtualModelInstance, FlexoController controller, FlexoPerspective perspective) {
		super(new GridBagLayout());
		
		this.perspective = perspective;
		this.controller = controller;
		this.traceVirtualModelInstance = traceVirtualModelInstance;
		
		factory = createFGEModelFactory();
		
		OBPRoute route = new OBPRouteImpl(traceVirtualModelInstance);
		sequenceDiagram = new SequenceDiagramDrawing(route, factory, RouteLayout.VERTICAL);
		OBPRouteEditor sequence = new OBPRouteEditor(sequenceDiagram,controller.getSelectionManager(),factory, SwingToolFactory.DEFAULT);

		boutonsPanel = new JPanel();
		FlowLayout flow = new FlowLayout(FlowLayout.CENTER);
		boutonsPanel.setLayout(flow);
		
		GridBagConstraints c1 = new GridBagConstraints();
		c1.fill = GridBagConstraints.HORIZONTAL;
		c1.gridx = 0;
		c1.gridy = 1;
		c1.weighty = 1;
		c1.gridwidth = 2;
		
		GridBagConstraints c2 = new GridBagConstraints();
		c2.weighty = 0.01;
		c2.gridx = 0;
		c2.gridy = 0;
		c2.fill =GridBagConstraints.HORIZONTAL;
		
		sequenceDiagram.printGraphicalObjectHierarchy();
		scrollPan = new JScrollPane(sequence.getDrawingView());
		scrollPan.setPreferredSize(computeMaxDimension());
		
		// Layout
		switchLayout = createLayoutButton();
		
		// Reset currrent mask
		resetMask = createResetMaskButton();
		
		boutonsPanel.add(switchLayout);
		//boutonsPanel.add(applyMask);
		boutonsPanel.add(resetMask);
		
		add(scrollPan, c1);
		add(boutonsPanel, c2);
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
		getRepresentedObject().getPropertyChangeSupport().removePropertyChangeListener(getRepresentedObject().getDeletedProperty(), this);
		controller.removeModuleView(this);
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

	
	private JButton createLayoutButton(){
		JButton button = new JButton();
		button.setIcon(TraceAnalysisIconLibrary.LAYOUT_HORIZONTAL_MEDIUM_ICON);
		button.setPreferredSize(new Dimension(40,40));
		button.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(sequenceDiagram.getLayout()==RouteLayout.VERTICAL){
					sequenceDiagram.setLayout(RouteLayout.HORIZONTAL);
					switchLayout.setIcon(TraceAnalysisIconLibrary.LAYOUT_HORIZONTAL_MEDIUM_ICON);
				}
				else{
					sequenceDiagram.setLayout(RouteLayout.VERTICAL);
					switchLayout.setIcon(TraceAnalysisIconLibrary.LAYOUT_HORIZONTAL_MEDIUM_ICON);
				}
			}
		});
		return button;
	}
	
	private JButton createResetMaskButton(){
		JButton button = new JButton();
		button.setPreferredSize(new Dimension(40,40));
		button.setIcon(TraceAnalysisIconLibrary.RESTART_MEDIUM_ICON);
		button.addActionListener(new ActionListener() {
							
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sequenceDiagram.resetMask();
			}
		});
		return button;
	}
	
	private Dimension computeMaxDimension(){
		int numberOfElements = traceVirtualModelInstance.getFederatedElements().size();
		int numberOfConfigurations = traceVirtualModelInstance.getConfigurations().size();
		int maxSize = (int) SequenceDiagramDrawing.LIFE_LINE_WIDTH;
		if(numberOfElements > numberOfConfigurations){
			return new Dimension(numberOfElements*maxSize, numberOfElements*maxSize);
		}
		else{
			return new Dimension(numberOfConfigurations*maxSize, numberOfConfigurations*maxSize);
		}
	}
	
	private FGEModelFactory createFGEModelFactory(){
		FGEModelFactory factory = null;
		try {
			factory = new FGEModelFactoryImpl();
		} catch (ModelDefinitionException e) {
			e.printStackTrace();
		}
		return factory;
	}
}
