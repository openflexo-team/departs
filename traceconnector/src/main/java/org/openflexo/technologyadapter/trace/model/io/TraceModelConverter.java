package org.openflexo.technologyadapter.trace.model.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.openflexo.foundation.technologyadapter.TechnologyAdapter;
import org.openflexo.model.ModelContext;
import org.openflexo.model.ModelContextLibrary;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.model.factory.ModelFactory;
import org.openflexo.technologyadapter.trace.TraceTechnologyAdapter;
import org.openflexo.technologyadapter.trace.model.FlexoComponent;
import org.openflexo.technologyadapter.trace.model.FlexoConfigData;
import org.openflexo.technologyadapter.trace.model.FlexoData;
import org.openflexo.technologyadapter.trace.model.FlexoProcess;
import org.openflexo.technologyadapter.trace.model.FlexoTraceObject;
import org.openflexo.technologyadapter.trace.model.FlexoTraceOBP;
import org.openflexo.technologyadapter.trace.model.FlexoTransition;

import Parser.ConfigData;
import Parser.TraceOBP;
import Parser.Transition;

public class TraceModelConverter {

	private static final Logger logger = Logger.getLogger(TraceModelConverter.class.getPackage().getName());

	protected final Map<Object, FlexoTraceObject> traceObjects = new HashMap<Object, FlexoTraceObject>();

	private ModelFactory factory;
	private ModelContext modelContext;
	private TraceTechnologyAdapter technologyAdapter;
	
	/**
	 * Constructor.
	 */
	public TraceModelConverter() {
		try {
			factory = new ModelFactory(ModelContextLibrary.getCompoundModelContext(
					FlexoTraceObject.class, 
					FlexoTraceOBP.class,
					FlexoComponent.class,
					FlexoData.class,
					FlexoProcess.class,
					FlexoTransition.class,
					FlexoConfigData.class));
		} catch (ModelDefinitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public TraceTechnologyAdapter getTechnologyAdapter() {
		return technologyAdapter;
	}

	public void setTechnologyAdapter(TraceTechnologyAdapter technologyAdapter) {
		this.technologyAdapter = technologyAdapter;
	}
	
	public FlexoTraceOBP convertTraceOBP(TraceOBP traceOBP) {

		FlexoTraceOBP flexoTraceOBP = factory.newInstance(FlexoTraceOBP.class);
		flexoTraceOBP.setTechnologyAdapter(technologyAdapter);
		traceObjects.put(traceOBP, flexoTraceOBP);
		
		for (Transition transition : traceOBP.getTransitionList()) {
			flexoTraceOBP.addToFlexoTransitions(convertTransition(transition,flexoTraceOBP));
		}
		for (ConfigData configData : traceOBP.getConfigDataList()) {
			flexoTraceOBP.addToFlexoConfigData(convertConfigData(configData,flexoTraceOBP));
		}
		
		return flexoTraceOBP;
	}
	
	public FlexoTransition convertTransition(Transition transition, FlexoTraceOBP traceOBP){
		FlexoTransition flexoTransition = factory.newInstance(FlexoTransition.class);
		flexoTransition.setTransition(transition);
		flexoTransition.setTechnologyAdapter(technologyAdapter);
		flexoTransition.setName(Integer.toString(transition.getNoTransition()));
		traceObjects.put(transition, flexoTransition);
		return flexoTransition;
	}
	
	public FlexoConfigData convertConfigData(ConfigData configData, FlexoTraceOBP traceOBP){
		FlexoConfigData flexoConfigData = factory.newInstance(FlexoConfigData.class);
		flexoConfigData.setConfigData(configData);
		flexoConfigData.setTechnologyAdapter(technologyAdapter);
		flexoConfigData.setName(Integer.toString(configData.getNoConfig()));
		traceObjects.put(configData, flexoConfigData);
		return flexoConfigData;
	}
	

	public Map<Object, FlexoTraceObject> getTraceObjects() {
		return traceObjects;
	}

	private Object getTraceObjectFromFlexoTraceObject(FlexoTraceObject object){
		for (Entry entry : traceObjects.entrySet()) {
			Object key = entry.getKey();
		    if(object.equals(traceObjects.get(key))){
		    	return key;
		    }
		}return null;
	}
	
}
