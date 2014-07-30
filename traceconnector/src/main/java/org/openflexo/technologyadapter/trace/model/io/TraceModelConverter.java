package org.openflexo.technologyadapter.trace.model.io;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.openflexo.model.ModelContextLibrary;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.model.factory.ModelFactory;
import org.openflexo.technologyadapter.trace.TraceTechnologyAdapter;
import org.openflexo.technologyadapter.trace.model.OBPTraceComponent;
import org.openflexo.technologyadapter.trace.model.OBPTraceConfiguration;
import org.openflexo.technologyadapter.trace.model.OBPTraceContext;
import org.openflexo.technologyadapter.trace.model.OBPTraceData;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessage;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessageInternal;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessageReceive;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessageSend;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessageSynchro;
import org.openflexo.technologyadapter.trace.model.OBPTraceProcess;
import org.openflexo.technologyadapter.trace.model.OBPTraceObject;
import org.openflexo.technologyadapter.trace.model.OBPTrace;
import org.openflexo.technologyadapter.trace.model.OBPTraceProperty;
import org.openflexo.technologyadapter.trace.model.OBPTraceState;
import org.openflexo.technologyadapter.trace.model.OBPTraceTransition;

import Parser.ConfigData;
import Parser.Donnee;
import Parser.TraceOBP;
import Parser.Transition;
import Parser.TransitionSequence;
import Parser.TransitionSequenceInternal;
import Parser.TransitionSequenceReceiveAsynchro;
import Parser.TransitionSequenceSendAsynchro;
import Parser.TransitionSequenceSynchro;

public class TraceModelConverter {

	private static final Logger logger = Logger.getLogger(TraceModelConverter.class.getPackage().getName());

	protected final Map<Object, OBPTraceObject> traceObjects = new HashMap<Object, OBPTraceObject>();

	private ModelFactory factory;
	private TraceTechnologyAdapter technologyAdapter;
	
	/**
	 * Constructor.
	 */
	public TraceModelConverter() {
		try {
			factory = new ModelFactory(ModelContextLibrary.getCompoundModelContext(
					OBPTraceObject.class, 
					OBPTrace.class,
					OBPTraceComponent.class,
					OBPTraceContext.class,
					OBPTraceData.class,
					OBPTraceProcess.class,
					OBPTraceTransition.class,
					OBPTraceConfiguration.class,
					OBPTraceProperty.class,
					OBPTraceMessageReceive.class,
					OBPTraceMessageInternal.class,
					OBPTraceMessageSend.class,
					OBPTraceMessageSynchro.class,
					OBPTraceMessage.class,
					OBPTraceState.class));
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
	
	public OBPTrace convertTraceOBP(TraceOBP traceOBP) {

		OBPTrace flexoTraceOBP = factory.newInstance(OBPTrace.class);
		flexoTraceOBP.setTechnologyAdapter(technologyAdapter);
		traceObjects.put(traceOBP, flexoTraceOBP);
		
		for (ConfigData configData : traceOBP.getConfigDataList()) {
			flexoTraceOBP.addToConfigurations(convertConfigData(configData,flexoTraceOBP));
		}
		for (Transition transition : traceOBP.getTransitionList()) {
			flexoTraceOBP.addToTransitions(convertTransition(transition,flexoTraceOBP));
		}
		for (OBPTraceTransition transition : flexoTraceOBP.getTransitions()) {
			for(OBPTraceMessage message: transition.getOBPTraceMessages()){
				updateMessages(message, transition,flexoTraceOBP);
			}
		}
		
		return flexoTraceOBP;
	}
	
	private void updateMessages(OBPTraceMessage message, OBPTraceTransition transition,OBPTrace flexoTraceOBP){
		if(message instanceof OBPTraceMessageSend){
			OBPTraceConfiguration config = transition.getTargetOBPTraceConfiguration();
			OBPTraceConfiguration nextConfig = flexoTraceOBP.getNextConfiguration(config);
			OBPTraceTransition nextTransition = flexoTraceOBP.getTransition(config, nextConfig);
			for(OBPTraceMessage nextMessage : nextTransition.getOBPTraceMessages()){
				if(nextMessage instanceof OBPTraceMessageReceive){
					message.setToBehaviourObject(flexoTraceOBP.getBehaviourObject(nextMessage.getTargetProcName()));
					((OBPTraceMessageSend) message).setOBPTraceMessageReceive((OBPTraceMessageReceive) nextMessage);
				}
			}
		}
		if(message instanceof OBPTraceMessageReceive){
			OBPTraceConfiguration config = transition.getSourceOBPTraceConfiguration();
			OBPTraceConfiguration previousConfig = flexoTraceOBP.getPreviousConfiguration(config);
			OBPTraceTransition previousTransition = flexoTraceOBP.getTransition(previousConfig, config);
			for(OBPTraceMessage previousMessage : previousTransition.getOBPTraceMessages()){
				if(previousMessage instanceof OBPTraceMessageSend){
					message.setFromBehaviourObject(flexoTraceOBP.getBehaviourObject(previousMessage.getSourceProcName()));
					((OBPTraceMessageReceive) message).setOBPTraceMessageSend((OBPTraceMessageSend) previousMessage);
				}
			}
		}
	}
	
	public OBPTraceTransition convertTransition(Transition transition, OBPTrace traceOBP){
		OBPTraceTransition flexoTransition = factory.newInstance(OBPTraceTransition.class);
		flexoTransition.setTransition(transition);
		flexoTransition.setSourceOBPTraceConfiguration(traceOBP.getConfiguration(transition.getNoConfigSource()));
		flexoTransition.setTargetOBPTraceConfiguration(traceOBP.getConfiguration(transition.getNoConfigTarget()));
		for(TransitionSequence transitionSequence : transition.transitionSequenceList){
			flexoTransition.addToOBPTraceMessages(convertMessage(transitionSequence, traceOBP));
		}
		flexoTransition.setTechnologyAdapter(technologyAdapter);
		flexoTransition.setName(Integer.toString(transition.getNoTransition()));
		traceObjects.put(transition, flexoTransition);
		return flexoTransition;
	}
	
	public OBPTraceMessage convertMessage(TransitionSequence transitionSequence, OBPTrace traceOBP){
		OBPTraceMessage flexoMessage = null;
		if(transitionSequence instanceof TransitionSequenceInternal){
			flexoMessage = convertMessageInternal((TransitionSequenceInternal) transitionSequence,traceOBP);	
		} else if(transitionSequence instanceof TransitionSequenceSynchro){
			flexoMessage = convertMessageSynchro((TransitionSequenceSynchro) transitionSequence,traceOBP);
		} else if(transitionSequence instanceof TransitionSequenceSendAsynchro){
			flexoMessage = convertMessageSendAsynchro((TransitionSequenceSendAsynchro) transitionSequence,traceOBP);
		} else if(transitionSequence instanceof TransitionSequenceReceiveAsynchro){
			flexoMessage = convertMessageReceiveAsynchro((TransitionSequenceReceiveAsynchro) transitionSequence,traceOBP);
		}
		return flexoMessage;
	}
	
	public OBPTraceMessage convertMessageInternal(TransitionSequenceInternal transitionSequence, OBPTrace traceOBP){
		OBPTraceMessageInternal flexoMessage = factory.newInstance(OBPTraceMessageInternal.class);
		flexoMessage.setTransitionSequence(transitionSequence);
		flexoMessage.setTechnologyAdapter(technologyAdapter);
		traceObjects.put(transitionSequence, flexoMessage);
		return flexoMessage;
	}
	
	public OBPTraceMessage convertMessageReceiveAsynchro(TransitionSequenceReceiveAsynchro transitionSequence, OBPTrace traceOBP){
		OBPTraceMessageReceive flexoMessage = factory.newInstance(OBPTraceMessageReceive.class);
		flexoMessage.setTransitionSequence(transitionSequence);
		flexoMessage.setToBehaviourObject(traceOBP.getBehaviourObject(flexoMessage.getTargetProcName()));
		flexoMessage.setTechnologyAdapter(technologyAdapter);
		traceObjects.put(transitionSequence, flexoMessage);
		return flexoMessage;
	}
	
	public OBPTraceMessage convertMessageSendAsynchro(TransitionSequenceSendAsynchro transitionSequence, OBPTrace traceOBP){
		OBPTraceMessageSend flexoMessage = factory.newInstance(OBPTraceMessageSend.class);
		flexoMessage.setTransitionSequence(transitionSequence);
		flexoMessage.setFromBehaviourObject(traceOBP.getBehaviourObject(flexoMessage.getSourceProcName()));
		flexoMessage.setTechnologyAdapter(technologyAdapter);
		traceObjects.put(transitionSequence, flexoMessage);
		return flexoMessage;
	}
	
	public OBPTraceMessage convertMessageSynchro(TransitionSequenceSynchro transitionSequence, OBPTrace traceOBP){
		OBPTraceMessageSynchro flexoMessage = factory.newInstance(OBPTraceMessageSynchro.class);
		flexoMessage.setTransitionSequence(transitionSequence);
		flexoMessage.setToBehaviourObject(traceOBP.getBehaviourObject(flexoMessage.getTargetProcName()));
		flexoMessage.setFromBehaviourObject(traceOBP.getBehaviourObject(flexoMessage.getSourceProcName()));
		flexoMessage.setTechnologyAdapter(technologyAdapter);
		traceObjects.put(transitionSequence, flexoMessage);
		return flexoMessage;
	}
	
	public OBPTraceConfiguration convertConfigData(ConfigData configData, OBPTrace traceOBP){
		OBPTraceConfiguration flexoConfigData = factory.newInstance(OBPTraceConfiguration.class);
		for (Parser.Process process : configData.getProcessList()) {
			flexoConfigData.addToOBPTraceProcesses(convertProcess(process,traceOBP));
		}
		for (Parser.Property property : configData.getPropertyList()) {
			flexoConfigData.addToOBPTraceProperties(convertProperty(property,traceOBP));
		}
		flexoConfigData.setOBPTraceContext(convertContext(configData.context,traceOBP));
		flexoConfigData.setConfigData(configData);
		flexoConfigData.setTechnologyAdapter(technologyAdapter);
		flexoConfigData.setName(Integer.toString(configData.getNoConfig()));
		traceObjects.put(configData, flexoConfigData);
		return flexoConfigData;
	}
	
	public OBPTraceContext convertContext(Parser.Context context, OBPTrace traceOBP){
		OBPTraceContext flexoContext = factory.newInstance(OBPTraceContext.class);
		flexoContext.setTraceOBPContext(context);
		flexoContext.setTechnologyAdapter(technologyAdapter);
		flexoContext.setState(convertState(context.getContextState(), traceOBP));
		traceObjects.put(context, flexoContext);
		return flexoContext;
	}
	
	public OBPTraceProcess convertProcess(Parser.Process process, OBPTrace traceOBP){
		OBPTraceProcess flexoProcess = factory.newInstance(OBPTraceProcess.class);
		for(Donnee donnee : process.getDonneeList()){
			flexoProcess.addToOBPTraceData(convertData(donnee,traceOBP));
		}
		flexoProcess.setProcess(process);
		flexoProcess.setTechnologyAdapter(technologyAdapter);
		flexoProcess.setState(convertState(process.getProcessState(), traceOBP));
		traceObjects.put(process, flexoProcess);
		return flexoProcess;
	}
	
	public OBPTraceProperty convertProperty(Parser.Property property, OBPTrace traceOBP){
		OBPTraceProperty flexoProperty = factory.newInstance(OBPTraceProperty.class);
		flexoProperty.setProperty(property);
		flexoProperty.setTechnologyAdapter(technologyAdapter);
		flexoProperty.setState(convertState(property.getPropertyState(), traceOBP));
		traceObjects.put(property, flexoProperty);
		return flexoProperty;
	}
	
	public OBPTraceState convertState(String state, OBPTrace traceOBP){
		OBPTraceState flexoState = factory.newInstance(OBPTraceState.class);
		flexoState.setName(state);
		flexoState.setTechnologyAdapter(technologyAdapter);
		traceObjects.put(state, flexoState);
		return flexoState;
	}
	
	public OBPTraceData convertData(Parser.Donnee donnee, OBPTrace traceOBP){
		OBPTraceData flexoData = factory.newInstance(OBPTraceData.class);
		flexoData.setDonnee(donnee);
		flexoData.setTechnologyAdapter(technologyAdapter);
		traceObjects.put(donnee, flexoData);
		return flexoData;
	}
	
	public Map<Object, OBPTraceObject> getTraceObjects() {
		return traceObjects;
	}

	private Object getTraceObjectFromFlexoTraceObject(OBPTraceObject object){
		for (Entry entry : traceObjects.entrySet()) {
			Object key = entry.getKey();
		    if(object.equals(traceObjects.get(key))){
		    	return key;
		    }
		}return null;
	}
	
}
