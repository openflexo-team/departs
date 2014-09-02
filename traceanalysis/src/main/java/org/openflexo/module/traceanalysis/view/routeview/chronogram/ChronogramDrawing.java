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
import org.openflexo.fge.graph.FGENumericFunction;
import org.openflexo.fge.impl.DrawingImpl;
import org.openflexo.fge.shapes.ShapeSpecification.ShapeType;
import org.openflexo.foundation.viewpoint.rm.ViewPointResource;
import org.openflexo.module.traceanalysis.view.routeview.BehaviourObjectInRoute;
import org.openflexo.module.traceanalysis.view.routeview.OBPRoute;
import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObjectInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceData;
import org.openflexo.technologyadapter.trace.model.OBPTraceVariable;

public class ChronogramDrawing extends DrawingImpl<OBPRoute> {

	private Map<FGEDiscreteFunctionGraph<OBPTraceData>,GraphGRBinding> chronograms;
	
	private DrawingGraphicalRepresentation drawingRepresentation;
	
	private int width = 900;
	
	public ChronogramDrawing(OBPRoute route, FGEModelFactory factory) {
		super(route, factory, PersistenceMode.SharedGraphicalRepresentations);
	}

	@Override
	public void init() {
		chronograms = new HashMap<FGEDiscreteFunctionGraph<OBPTraceData>,GraphGRBinding>();
		int y = 50;
		for(BehaviourObjectInRoute object : getModel().getVisibleBehaviourObjects()){
			
			OBPTraceBehaviourObjectInstance instance = object.getBehaviourObject();
			
			for(OBPTraceVariable var : instance.getVariables()){
				List<OBPTraceData> values = var.getValues();
				FGEDiscreteFunctionGraph<OBPTraceData> dataChronogram = new FGEDiscreteFunctionGraph<OBPTraceData>();

				dataChronogram.setParameter("data", OBPTraceData.class);
				dataChronogram.setDiscreteValues(values);
				dataChronogram.setDiscreteValuesLabel(new DataBinding<String>("data.behaviourObjectState.oBPTraceConfiguration.name"));
				dataChronogram.setParameterOrientation(Orientation.HORIZONTAL);
	
				FGENumericFunction<Integer> functionChronogram = dataChronogram.addNumericFunction("values", Integer.class, new DataBinding<Integer>("data.getIntegerValue()"), GraphType.RECT_POLYLIN);
				//functionChronogram.setRange(0, 50);
				functionChronogram.setForegroundStyle(getFactory().makeForegroundStyle(Color.BLUE, 1.0f));
				functionChronogram.setBackgroundStyle(getFactory().makeColorGradientBackground(Color.BLUE, Color.WHITE,
						ColorGradientDirection.NORTH_SOUTH));
				
				final ShapeGraphicalRepresentation chronogramGR = getFactory().makeShapeGraphicalRepresentation(ShapeType.RECTANGLE);
				chronogramGR.setText(var.getName());
				chronogramGR.setX(50);
				chronogramGR.setY(y);
				chronogramGR.setWidth(width);
				chronogramGR.setHeight(50);
				chronogramGR.setAbsoluteTextX(500);
				chronogramGR.setAbsoluteTextY(3);
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
				y= y +85;
			}
			y = y+20;
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	
}
