package org.openflexo.traceanalysis.view.routeview;

import org.openflexo.fge.Drawing;
import org.openflexo.fge.FGEModelFactory;
import org.openflexo.fge.swing.JDianaInteractiveEditor;
import org.openflexo.fge.swing.control.SwingToolFactory;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.traceanalysis.view.routeview.OBPRoute;

public class OBPRouteEditor extends JDianaInteractiveEditor<OBPRoute>{

	private final TraceVirtualModelInstance traceVmi;

	public OBPRouteEditor(Drawing<OBPRoute> aDrawing, FGEModelFactory factory,
			SwingToolFactory toolFactory, TraceVirtualModelInstance traceVmi) {
		super(aDrawing, factory, toolFactory);
		this.traceVmi = traceVmi;
		
	}
	
	public TraceVirtualModelInstance getTraceVmi() {
		return traceVmi;
	}

}
