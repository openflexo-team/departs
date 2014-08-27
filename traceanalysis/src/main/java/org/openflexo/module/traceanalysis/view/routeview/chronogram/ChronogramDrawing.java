package org.openflexo.module.traceanalysis.view.routeview.chronogram;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javassist.bytecode.Descriptor.Iterator;

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
import org.openflexo.foundation.viewpoint.rm.ViewPointResource;
import org.openflexo.module.traceanalysis.view.routeview.BehaviourObjectInRoute;
import org.openflexo.module.traceanalysis.view.routeview.OBPRoute;
import org.openflexo.technologyadapter.trace.model.OBPTraceData;

public class ChronogramDrawing extends DrawingImpl<OBPRoute> {

	private Map<FGEDiscreteFunctionGraph<OBPTraceData>,GraphGRBinding> chronograms;
	
	private DrawingGraphicalRepresentation drawingRepresentation;
	
	public ChronogramDrawing(OBPRoute route, FGEModelFactory factory) {
		super(route, factory, PersistenceMode.SharedGraphicalRepresentations);
	}

	@Override
	public void init() {
		chronograms = new HashMap<FGEDiscreteFunctionGraph<OBPTraceData>,GraphGRBinding>();
		int y = 50;
		for(BehaviourObjectInRoute object : getModel().getVisibleBehaviourObjects()){
			for(OBPTraceData data : object.getBehaviourObject().getOBPTraceData()){
				FGEDiscreteFunctionGraph dataChronogram = new FGEDiscreteFunctionGraph<OBPTraceData>();

				dataChronogram.setParameter("data", OBPTraceData.class);
				dataChronogram.setDiscreteValues(object.getBehaviourObject().getOBPTraceData());
				dataChronogram.setDiscreteValuesLabel(new DataBinding<String>("data.value"));
				dataChronogram.setParameterOrientation(Orientation.VERTICAL);

				FGEFunction<String> functionChronogram = dataChronogram.addFunction("data.name", String.class, new DataBinding<String>("data.name"), GraphType.RECT_POLYLIN);
				//sizeFunction.setRange(0, 200);
				functionChronogram.setForegroundStyle(getFactory().makeForegroundStyle(Color.BLUE, 1.0f));
				functionChronogram.setBackgroundStyle(getFactory().makeColorGradientBackground(Color.BLUE, Color.WHITE,
						ColorGradientDirection.NORTH_SOUTH));
				
				final ShapeGraphicalRepresentation chronogramGR = getFactory().makeShapeGraphicalRepresentation(ShapeType.RECTANGLE);
				chronogramGR.setText("Example of chronogram");
				chronogramGR.setX(50);
				chronogramGR.setY(y);
				chronogramGR.setWidth(700);
				chronogramGR.setHeight(60);
				chronogramGR.setAbsoluteTextX(20);
				chronogramGR.setAbsoluteTextY(5);
				chronogramGR.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
				chronogramGR.setTextStyle(getFactory().makeTextStyle(Color.BLACK, FGEConstants.DEFAULT_TEXT_FONT));
				chronogramGR.setShadowStyle(getFactory().makeNoneShadowStyle());
				chronogramGR.setBackground(getFactory().makeColoredBackground(Color.WHITE));
				chronogramGR.setForeground(getFactory().makeForegroundStyle(Color.BLACK));
				// Very important: give some place for labels, legend and other informations
				chronogramGR.setBorder(getFactory().makeShapeBorder(20, 20, 20, 20));

				final GraphGRBinding<FGEDiscreteFunctionGraph> chronogramBinding = bindGraph(FGEDiscreteFunctionGraph.class, "graph",
						new ShapeGRProvider<FGEDiscreteFunctionGraph>() {
							@Override
							public ShapeGraphicalRepresentation provideGR(FGEDiscreteFunctionGraph drawable, FGEModelFactory factory) {
								return chronogramGR;
							}
						});
				
				chronograms.put(dataChronogram, chronogramBinding);
				y= y +30;
			}
			
		}
		 
		drawingRepresentation = getFactory().makeDrawingGraphicalRepresentation();

		final DrawingGRBinding<OBPRoute> drawingBinding = bindDrawing(OBPRoute.class, "drawing", new DrawingGRProvider<OBPRoute>() {
			@Override
			public DrawingGraphicalRepresentation provideGR(OBPRoute drawable, FGEModelFactory factory) {
				return drawingRepresentation;
			}
		});

		

		drawingBinding.addToWalkers(new GRStructureVisitor<OBPRoute>() {

			@Override
			public void visit(OBPRoute route) {
				for (Entry<FGEDiscreteFunctionGraph<OBPTraceData>, GraphGRBinding> entry : chronograms.entrySet()) {	
					drawGraph(entry.getValue(),entry.getKey());
				}
			}
		});

	}

	
}
