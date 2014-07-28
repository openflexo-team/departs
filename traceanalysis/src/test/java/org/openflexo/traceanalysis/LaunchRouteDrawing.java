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
package org.openflexo.traceanalysis;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import org.openflexo.fge.FGEModelFactory;
import org.openflexo.fge.FGEModelFactoryImpl;
import org.openflexo.fge.swing.JDianaInteractiveEditor;
import org.openflexo.fge.swing.JDianaInteractiveViewer;
import org.openflexo.fge.swing.SwingViewFactory;
import org.openflexo.fge.swing.control.SwingToolFactory;
import org.openflexo.fge.swing.control.tools.JDianaScaleSelector;
import org.openflexo.fib.utils.FlexoLoggingViewer;
import org.openflexo.logging.FlexoLogger;
import org.openflexo.logging.FlexoLoggingManager;
import org.openflexo.model.exceptions.ModelDefinitionException;

public class LaunchRouteDrawing {

	private static final Logger logger = FlexoLogger.getLogger(LaunchRouteDrawing.class.getPackage().getName());

	public static void main(String[] args) {
		try {
			FlexoLoggingManager.initialize(-1, true, null, Level.INFO, null);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		showPanel();
	}

	public static class TestDrawingController extends JDianaInteractiveEditor<OBPRoute> {
		private final JPopupMenu contextualMenu;
		private final JDianaScaleSelector scaleSelector;

		public TestDrawingController(RouteDrawing aDrawing) {
			super(aDrawing, aDrawing.getFactory(), SwingViewFactory.INSTANCE, SwingToolFactory.DEFAULT);
			scaleSelector = (JDianaScaleSelector) getToolFactory().makeDianaScaleSelector(this);
			contextualMenu = new JPopupMenu();
			contextualMenu.add(new JMenuItem("Item"));
		}

		/*@Override
		public void addToSelectedObjects(DrawingTreeNode<?,?> anObject) {
			super.addToSelectedObjects(anObject);
			if (getSelectedObjects().size() == 1) {
				setChanged();
				notifyObservers(new UniqueSelection(getSelectedObjects().get(0), null));
			} else {
				setChanged();
				notifyObservers(new MultipleSelection());
			}
		}

		@Override
		public void removeFromSelectedObjects(DrawingTreeNode<?,?> anObject) {
			super.removeFromSelectedObjects(anObject);
			if (getSelectedObjects().size() == 1) {
				setChanged();
				notifyObservers(new UniqueSelection(getSelectedObjects().get(0), null));
			} else {
				setChanged();
				notifyObservers(new MultipleSelection());
			}
		}

		@Override
		public void clearSelection() {
			super.clearSelection();
			notifyObservers(new EmptySelection());
		}

		@Override
		public void selectDrawing() {
			super.selectDrawing();
			setChanged();
			notifyObservers(new UniqueSelection(getDrawingGraphicalRepresentation(), null));
		}
		 */

		/*@Override
		public JDrawingView<Graph> makeDrawingView() {
			JDrawingView<Graph> returned = super.makeDrawingView();
			returned.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if (e.isPopupTrigger() || e.getButton() == MouseEvent.BUTTON3) {
						logger.info("Display contextual menu");
					}
				}
			});
			return returned;
		}*/

	}

	public static void showPanel() {
		final JDialog dialog = new JDialog((Frame) null, false);

		JPanel panel = new JPanel(new BorderLayout());

		// final TestInspector inspector = new TestInspector();

		final RouteDrawing d = makeDrawing();
		final TestDrawingController dc = new TestDrawingController(d);
		// dc.disablePaintingCache();
		dc.getDrawingView().setName("[NO_CACHE]");
		panel.add(new JScrollPane(dc.getDrawingView()), BorderLayout.CENTER);
		panel.add(dc.scaleSelector.getComponent(), BorderLayout.NORTH);

		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
				System.exit(0);
			}
		});

		JButton inspectButton = new JButton("Inspect");
		inspectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// inspector.getWindow().setVisible(true);
			}
		});

		JButton logButton = new JButton("Logs");
		logButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FlexoLoggingViewer.showLoggingViewer(FlexoLoggingManager.instance(), dialog);
			}
		});

		JButton screenshotButton = new JButton("Screenshot");
		screenshotButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final BufferedImage screenshot = dc.getDelegate().makeScreenshot(dc.getDrawing().getRoot());
				JDialog screenshotDialog = new JDialog((Frame) null, false);
				screenshotDialog.getContentPane().add(new JPanel() {
					@Override
					public void paint(java.awt.Graphics g) {
						super.paint(g);
						g.drawImage(screenshot, 0, 0, screenshot.getWidth(), screenshot.getHeight(), null);
					}
				});
				screenshotDialog.setPreferredSize(new Dimension(400, 400));
				screenshotDialog.setLocation(500, 500);
				screenshotDialog.validate();
				screenshotDialog.pack();
				screenshotDialog.setVisible(true);
			}
		});

		JPanel controlPanel = new JPanel(new FlowLayout());
		controlPanel.add(closeButton);
		controlPanel.add(inspectButton);
		controlPanel.add(logButton);
		controlPanel.add(screenshotButton);

		panel.add(controlPanel, BorderLayout.SOUTH);

		dialog.setPreferredSize(new Dimension(550, 600));
		dialog.getContentPane().add(panel);
		dialog.validate();
		dialog.pack();

		dialog.setVisible(true);

		JDianaInteractiveViewer<OBPRoute> dc2 = new JDianaInteractiveViewer<OBPRoute>(d, d.getFactory(), SwingToolFactory.DEFAULT);
		final JDialog dialog2 = new JDialog((Frame) null, false);
		dialog2.getContentPane().add(new JScrollPane(dc2.getDrawingView()));
		dialog2.setPreferredSize(new Dimension(400, 400));
		dialog2.setLocation(800, 100);
		dialog2.validate();
		dialog2.pack();
		dialog2.setVisible(true);
		// dc2.enablePaintingCache();

		// dc.addObserver(inspector);
		// inspector.getWindow().setVisible(true);
	}

	public static RouteDrawing makeDrawing() {
		FGEModelFactory factory = null;
		try {
			factory = new FGEModelFactoryImpl();
		} catch (ModelDefinitionException e) {
			e.printStackTrace();
		}
		OBPRoute route = makeExampleRoute();
		RouteDrawing returned = new RouteDrawing(route, factory);
		returned.printGraphicalObjectHierarchy();
		return returned;
	}

	public static OBPRoute makeExampleRoute() {

		OBPTrace trace = makeExampleTrace();
		OBPRoute returned = new OBPRouteImpl(trace);
		return returned;

	}

	public static OBPTrace makeExampleTrace() {

		Component c1 = new Component("Component1");
		Component c2 = new Component("Component2");
		Component c3 = new Component("Component3");

		OBPTraceImpl trace = new OBPTraceImpl();
		trace.addToComponents(c1);
		trace.addToComponents(c2);
		trace.addToComponents(c3);

		trace.addConfiguration(0);
		OBPTransitionImpl t1 = (OBPTransitionImpl) trace.addConfiguration(7);
		OBPTransitionImpl t2 = (OBPTransitionImpl) trace.addConfiguration(12);
		OBPTransitionImpl t3 = (OBPTransitionImpl) trace.addConfiguration(23);
		OBPTransitionImpl t4 = (OBPTransitionImpl) trace.addConfiguration(25);
		OBPTransitionImpl t5 = (OBPTransitionImpl) trace.addConfiguration(432);
		OBPTransitionImpl t6 = (OBPTransitionImpl) trace.addConfiguration(1027);
		OBPTransitionImpl t7 = (OBPTransitionImpl) trace.addConfiguration(1029);

		t1.addToMessages(new Message("m1", c1, c2, t1));
		t2.addToMessages(new Message("m2", c2, c1, t2));
		t2.addToMessages(new Message("m3", c2, c3, t2));
		t3.addToMessages(new Message("m4", c1, c2, t3));
		t4.addToMessages(new Message("m5", c2, c3, t4));
		t5.addToMessages(new Message("m6", c3, c1, t5));
		t5.addToMessages(new Message("m7", c3, c2, t5));
		t6.addToMessages(new Message("m8", c1, c2, t6));
		t6.addToMessages(new Message("m9", c2, c3, t6));
		t7.addToMessages(new Message("m10", c3, c2, t7));

		return trace;
	}
}
