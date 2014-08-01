package org.openflexo.module.traceanalysis.view.routeview.chronogram;

import java.awt.Color;
import java.util.List;

import org.openflexo.antar.binding.DataBinding;
import org.openflexo.fge.ColorGradientBackgroundStyle.ColorGradientDirection;
import org.openflexo.fge.DrawingGraphicalRepresentation;
import org.openflexo.fge.FGEConstants;
import org.openflexo.fge.FGEModelFactory;
import org.openflexo.fge.GRBinding.DrawingGRBinding;
import org.openflexo.fge.GRBinding.GraphGRBinding;
import org.openflexo.fge.GRProvider.DrawingGRProvider;
import org.openflexo.fge.GRProvider.ShapeGRProvider;
import org.openflexo.fge.GRStructureVisitor;
import org.openflexo.fge.GraphicalRepresentation.HorizontalTextAlignment;
import org.openflexo.fge.ShapeGraphicalRepresentation;
import org.openflexo.fge.graph.FGEDiscreteFunctionGraph;
import org.openflexo.fge.graph.FGEFunction;
import org.openflexo.fge.graph.FGEFunctionGraph.Orientation;
import org.openflexo.fge.graph.FGEGraph.GraphType;
import org.openflexo.fge.impl.DrawingImpl;
import org.openflexo.fge.shapes.ShapeSpecification.ShapeType;
import org.openflexo.module.traceanalysis.view.routeview.OBPRoute;
import org.openflexo.technologyadapter.trace.model.OBPTraceData;

public class ChronogramDrawing extends DrawingImpl<OBPRoute> {

	private FGEDiscreteFunctionGraph<OBPTraceData> graph;
	private FGEFunction<String> sizeFunction;

	private DrawingGraphicalRepresentation drawingRepresentation;
	private ShapeGraphicalRepresentation graphGR;

	public ChronogramDrawing(OBPRoute route, FGEModelFactory factory) {
		super(route, factory, PersistenceMode.SharedGraphicalRepresentations);
	}

	@Override
	public void init() {
		List<OBPTraceData> data = getModel().getTrace().getConfiguration(0).getOBPTraceProcesses().get(0).getOBPTraceData();

		graph = new FGEDiscreteFunctionGraph<OBPTraceData>();

		graph.setParameter("data", OBPTraceData.class);
		graph.setDiscreteValues(data);
		graph.setDiscreteValuesLabel(new DataBinding<String>("data.name"));
		graph.setParameterOrientation(Orientation.VERTICAL);

		sizeFunction = graph.addFunction("size", String.class, new DataBinding<String>("data.value"), GraphType.RECT_POLYLIN);
		//sizeFunction.setRange(0, 200);
		sizeFunction.setForegroundStyle(getFactory().makeForegroundStyle(Color.BLUE, 1.0f));
		sizeFunction.setBackgroundStyle(getFactory().makeColorGradientBackground(Color.BLUE, Color.WHITE,
				ColorGradientDirection.NORTH_SOUTH));

		drawingRepresentation = getFactory().makeDrawingGraphicalRepresentation();

		final DrawingGRBinding<OBPRoute> drawingBinding = bindDrawing(OBPRoute.class, "drawing", new DrawingGRProvider<OBPRoute>() {
			@Override
			public DrawingGraphicalRepresentation provideGR(OBPRoute drawable, FGEModelFactory factory) {
				return drawingRepresentation;
			}
		});

		graphGR = getFactory().makeShapeGraphicalRepresentation(ShapeType.RECTANGLE);
		graphGR.setText("Example of chronogram");
		graphGR.setX(50);
		graphGR.setY(50);
		graphGR.setWidth(700);
		graphGR.setHeight(60);
		graphGR.setAbsoluteTextX(20);
		graphGR.setAbsoluteTextY(5);
		graphGR.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
		graphGR.setTextStyle(getFactory().makeTextStyle(Color.BLACK, FGEConstants.DEFAULT_TEXT_FONT));
		graphGR.setShadowStyle(getFactory().makeNoneShadowStyle());
		graphGR.setBackground(getFactory().makeColorGradientBackground(Color.CYAN, Color.white,
				ColorGradientDirection.SOUTH_EAST_NORTH_WEST));
		graphGR.setForeground(getFactory().makeForegroundStyle(Color.ORANGE));
		// Very important: give some place for labels, legend and other informations
		graphGR.setBorder(getFactory().makeShapeBorder(20, 20, 20, 20));

		final GraphGRBinding<FGEDiscreteFunctionGraph> graphBinding = bindGraph(FGEDiscreteFunctionGraph.class, "graph",
				new ShapeGRProvider<FGEDiscreteFunctionGraph>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(FGEDiscreteFunctionGraph drawable, FGEModelFactory factory) {
						return graphGR;
					}
				});

		drawingBinding.addToWalkers(new GRStructureVisitor<OBPRoute>() {

			@Override
			public void visit(OBPRoute route) {
				drawGraph(graphBinding, graph);
			}
		});

	}
}
