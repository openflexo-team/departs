package org.openflexo.module.traceanalysis.view.routeview;

import java.awt.Color;
import java.util.List;

import org.openflexo.antar.binding.DataBinding;
import org.openflexo.fge.ColorGradientBackgroundStyle.ColorGradientDirection;
import org.openflexo.fge.FGEModelFactory;
import org.openflexo.fge.FGEModelFactoryImpl;
import org.openflexo.fge.graph.FGEDiscreteFunctionGraph;
import org.openflexo.fge.graph.FGENumericFunction;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.technologyadapter.trace.model.OBPTraceData;
import org.openflexo.technologyadapter.trace.model.OBPTraceVariable;

public class Chronogram extends FGEDiscreteFunctionGraph{
	
	// A Chonogram represent a set of data from a behaviourObject
	private final ProcessInRoute processInRoute;
	
	// A Chronogram represent a variable from a behaviour object
	private final OBPTraceVariable variable;
	
	// A Chronogram represent a set of data for a corresponding to the value of a variable along the configurations
	private List<OBPTraceData> data;
	
	private FGEModelFactory factory;
	
	public Chronogram(ProcessInRoute processInRoute, OBPTraceVariable variable) {
		super();
		this.processInRoute = processInRoute;
		this.variable = variable;
		data = variable.getValues();
		setParameter("data", OBPTraceData.class);
		setDiscreteValues(data);
		setDiscreteValuesLabel(new DataBinding<String>("data.behaviourObjectState.oBPTraceConfiguration.name"));
		setParameterOrientation(Orientation.HORIZONTAL);

		FGENumericFunction<Integer> functionChronogram = addNumericFunction("values", Integer.class, new DataBinding<Integer>("data.getIntegerValue()"), GraphType.RECT_POLYLIN);
		//functionChronogram.setRange(0, 10);
		
		functionChronogram.setForegroundStyle(getFactory().makeForegroundStyle(Color.BLUE, 1.0f));
		functionChronogram.setBackgroundStyle(getFactory().makeColorGradientBackground(Color.BLUE, Color.WHITE,
				ColorGradientDirection.NORTH_SOUTH));
	}

	public double getIndex() {
		return getProcessInRoute().getChronogramIndex(this);
	}
	
	public ProcessInRoute getProcessInRoute() {
		return processInRoute;
	}
	
	// TEMP
	private FGEModelFactory getFactory(){
		if(factory==null){
			try {
				factory = new FGEModelFactoryImpl();
			} catch (ModelDefinitionException e) {
				e.printStackTrace();
			}
		}
		return factory;
	}
	
	
	
}
