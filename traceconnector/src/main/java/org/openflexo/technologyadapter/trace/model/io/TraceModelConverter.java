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
import org.openflexo.technologyadapter.trace.model.FlexoTraceOBPComponent;
import org.openflexo.technologyadapter.trace.model.FlexoTraceOBPConfigData;
import org.openflexo.technologyadapter.trace.model.FlexoTraceOBPData;
import org.openflexo.technologyadapter.trace.model.FlexoTraceOBPProcess;
import org.openflexo.technologyadapter.trace.model.FlexoTraceOBPObject;
import org.openflexo.technologyadapter.trace.model.FlexoTraceOBP;
import org.openflexo.technologyadapter.trace.model.FlexoTraceOBPState;
import org.openflexo.technologyadapter.trace.model.FlexoTraceOBPTransition;

import Parser.ConfigData;
import Parser.Donnee;
import Parser.TraceOBP;
import Parser.Transition;

public class TraceModelConverter {

	private static final Logger logger = Logger.getLogger(TraceModelConverter.class.getPackage().getName());

	protected final Map<Object, FlexoTraceOBPObject> traceObjects = new HashMap<Object, FlexoTraceOBPObject>();

	private ModelFactory factory;
	private TraceTechnologyAdapter technologyAdapter;
	
	/**
	 * Constructor.
	 */
	public TraceModelConverter() {
		try {
			factory = new ModelFactory(ModelContextLibrary.getCompoundModelContext(
					FlexoTraceOBPObject.class, 
					FlexoTraceOBP.class,
					FlexoTraceOBPComponent.class,
					FlexoTraceOBPData.class,
					FlexoTraceOBPProcess.class,
					FlexoTraceOBPTransition.class,
					FlexoTraceOBPConfigData.class,
					FlexoTraceOBPState.class));
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
	
	public FlexoTraceOBPTransition convertTransition(Transition transition, FlexoTraceOBP traceOBP){
		FlexoTraceOBPTransition flexoTransition = factory.newInstance(FlexoTraceOBPTransition.class);
		flexoTransition.setTransition(transition);
		flexoTransition.setTechnologyAdapter(technologyAdapter);
		flexoTransition.setName(Integer.toString(transition.getNoTransition()));
		traceObjects.put(transition, flexoTransition);
		return flexoTransition;
	}
	
	public FlexoTraceOBPConfigData convertConfigData(ConfigData configData, FlexoTraceOBP traceOBP){
		FlexoTraceOBPConfigData flexoConfigData = factory.newInstance(FlexoTraceOBPConfigData.class);
		for (Parser.Process process : configData.getProcessList()) {
			flexoConfigData.addToFlexoProcess(convertProcess(process,traceOBP));
		}
		flexoConfigData.setConfigData(configData);
		flexoConfigData.setTechnologyAdapter(technologyAdapter);
		flexoConfigData.setName(Integer.toString(configData.getNoConfig()));
		traceObjects.put(configData, flexoConfigData);
		return flexoConfigData;
	}
	
	public FlexoTraceOBPProcess convertProcess(Parser.Process process, FlexoTraceOBP traceOBP){
		FlexoTraceOBPProcess flexoProcess = factory.newInstance(FlexoTraceOBPProcess.class);
		for(Donnee donnee : process.getDonneeList()){
			flexoProcess.addToFlexoData(convertData(donnee,traceOBP));
		}
		flexoProcess.setProcess(process);
		flexoProcess.setTechnologyAdapter(technologyAdapter);
		flexoProcess.setState(convertState(process.getProcessState(), traceOBP));
		traceObjects.put(process, flexoProcess);
		return flexoProcess;
	}
	
	public FlexoTraceOBPState convertState(String state, FlexoTraceOBP traceOBP){
		FlexoTraceOBPState flexoState = factory.newInstance(FlexoTraceOBPState.class);
		flexoState.setName(state);
		flexoState.setTechnologyAdapter(technologyAdapter);
		traceObjects.put(state, flexoState);
		return flexoState;
	}
	
	public FlexoTraceOBPData convertData(Parser.Donnee donnee, FlexoTraceOBP traceOBP){
		FlexoTraceOBPData flexoData = factory.newInstance(FlexoTraceOBPData.class);
		flexoData.setDonnee(donnee);
		flexoData.setTechnologyAdapter(technologyAdapter);
		traceObjects.put(donnee, flexoData);
		return flexoData;
	}
	
	public Map<Object, FlexoTraceOBPObject> getTraceObjects() {
		return traceObjects;
	}

	private Object getTraceObjectFromFlexoTraceObject(FlexoTraceOBPObject object){
		for (Entry entry : traceObjects.entrySet()) {
			Object key = entry.getKey();
		    if(object.equals(traceObjects.get(key))){
		    	return key;
		    }
		}return null;
	}
	
}
