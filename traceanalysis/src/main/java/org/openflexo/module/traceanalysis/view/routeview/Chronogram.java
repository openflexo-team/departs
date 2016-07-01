package org.openflexo.module.traceanalysis.view.routeview;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.openflexo.connie.DataBinding;
import org.openflexo.fge.ColorGradientBackgroundStyle.ColorGradientDirection;
import org.openflexo.fge.FGEModelFactory;
import org.openflexo.fge.FGEModelFactoryImpl;
import org.openflexo.fge.graph.FGEDiscreteSimpleFunctionGraph;
import org.openflexo.fge.graph.FGEFunction.GraphType;
import org.openflexo.fge.graph.FGENumericFunction;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObjectState;
import org.openflexo.technologyadapter.trace.model.OBPTraceConfiguration;
import org.openflexo.technologyadapter.trace.model.OBPTraceData;
import org.openflexo.technologyadapter.trace.model.OBPTraceVariable;

public class Chronogram extends FGEDiscreteSimpleFunctionGraph implements PropertyChangeListener {

	@PropertyIdentifier(type = List.class)
	public static final String DATA_ID = "data";

	// A Chonogram represent a set of data from a behaviourObject
	private final VariableInRoute variableInRoute;

	// A Chronogram represent a variable from a behaviour object
	private final OBPTraceVariable variable;

	// A Chronogram represent a set of data for a corresponding to the value of a variable along the configurations
	private List<OBPTraceData> data;

	// A set of visible data values, visible if corresponding configurations are visible
	private List<OBPTraceData> visibleData;

	private FGEModelFactory factory;

	public Chronogram(VariableInRoute variableInRoute) {
		super();
		this.variableInRoute = variableInRoute;
		this.variable = variableInRoute.getBehaviourObject();
		data = variable.getValues();
		setParameter("data", OBPTraceData.class);
		setDiscreteValues(data);
		setDiscreteValuesLabel(new DataBinding<String>("data.behaviourObjectState.oBPTraceConfiguration.name"));
		setParameterOrientation(Orientation.HORIZONTAL);
		FGENumericFunction<Integer> functionChronogram = addNumericFunction("values", Integer.class,
				new DataBinding<Integer>("data.getIntegerValue()"), GraphType.RECT_POLYLIN);
		functionChronogram.setForegroundStyle(getFactory().makeForegroundStyle(Color.BLUE, 1.0f));
		functionChronogram
				.setBackgroundStyle(getFactory().makeColorGradientBackground(Color.BLUE, Color.WHITE, ColorGradientDirection.NORTH_SOUTH));

		getRoute().getPropertyChangeSupport().addPropertyChangeListener("visibleConfigurations", this);
	}

	public void updateChronogram() {
		setDiscreteValues(getVisibleData());
	}

	public String getName() {
		return variable.getName();
	}

	public ProcessInRoute getProcessInRoute() {
		return getVariableInRoute().getProcessInRoute();
	}

	public double getIndex() {
		return getProcessInRoute().getChronogramIndex(this);
	}

	public VariableInRoute getVariableInRoute() {
		return variableInRoute;
	}

	public List<OBPTraceData> getData() {
		return data;
	}

	public void setData(List<OBPTraceData> data) {
		this.data = data;
	}

	public List<OBPTraceData> getVisibleData() {
		if (visibleData == null) {
			visibleData = new ArrayList<OBPTraceData>();
		}
		visibleData.clear();
		for (OBPTraceData value : data) {
			OBPConfigurationInRoute confInRoute = getRoute().getOBPConfigurationInRoute(getConfigurationForData(value));

			if (getRoute().getVisibleConfigurations().contains(confInRoute)) {
				visibleData.add(value);
			}
		}
		return visibleData;
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		if (arg0.getPropertyName().equals("visibleConfigurations")) {
			updateChronogram();
		}
	}

	private OBPTraceConfiguration getConfigurationForData(OBPTraceData value) {
		return getStateForData(value).getOBPTraceConfiguration();
	}

	private OBPTraceBehaviourObjectState getStateForData(OBPTraceData value) {
		return value.getBehaviourObjectState();
	}

	private OBPRoute getRoute() {
		return getProcessInRoute().getRoute();
	}

	// TEMP
	private FGEModelFactory getFactory() {
		if (factory == null) {
			try {
				factory = new FGEModelFactoryImpl();
			} catch (ModelDefinitionException e) {
				e.printStackTrace();
			}
		}
		return factory;
	}

}
