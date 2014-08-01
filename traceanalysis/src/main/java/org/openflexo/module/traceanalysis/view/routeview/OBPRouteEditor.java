package org.openflexo.module.traceanalysis.view.routeview;

import org.openflexo.fge.Drawing;
import org.openflexo.fge.FGEModelFactory;
import org.openflexo.fge.swing.JDianaInteractiveEditor;
import org.openflexo.fge.swing.control.SwingToolFactory;
import org.openflexo.module.traceanalysis.view.routeview.OBPRoute;

public class OBPRouteEditor extends JDianaInteractiveEditor<OBPRoute>{
	public OBPRouteEditor(Drawing<OBPRoute> aDrawing, FGEModelFactory factory,
			SwingToolFactory toolFactory) {
		super(aDrawing, factory, toolFactory);
		
	}

}
