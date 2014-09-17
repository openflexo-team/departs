package org.openflexo.module.traceanalysis.view.routeview;

import org.openflexo.fge.Drawing;
import org.openflexo.fge.Drawing.DrawingTreeNode;
import org.openflexo.fge.FGEModelFactory;
import org.openflexo.fge.swing.control.SwingToolFactory;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.selection.SelectionManager;
import org.openflexo.selection.SelectionManagingDianaEditor;
import org.openflexo.technologyadapter.trace.model.OBPTraceTransition;

public class OBPRouteEditor extends SelectionManagingDianaEditor<OBPRoute>{

	public OBPRouteEditor(Drawing<OBPRoute> drawing,
			SelectionManager selectionManager, FGEModelFactory factory,
			SwingToolFactory toolFactory) {
		super(drawing, selectionManager, factory, toolFactory);
	}
	
	@Override
	protected FlexoObject getDrawableForDrawingTreeNode(
			DrawingTreeNode<?, ?> node) {
		if (node.getDrawable() instanceof OBPConfigurationInRoute) {
			OBPConfigurationInRoute configInRoute = (OBPConfigurationInRoute)node.getDrawable();
			return getDrawing().getModel().getTraceVirtualModelInstance().getMaskableElement(configInRoute.getConfiguration());
		}else if (node.getDrawable() instanceof OBPTraceTransition){
			return getDrawing().getModel().getTraceVirtualModelInstance().getMaskableElement((OBPTraceTransition) node.getDrawable());
		}else{
			return super.getDrawableForDrawingTreeNode(node);
		}
	}
}
