package org.openflexo.technologyadapter.trace.model.io;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openflexo.model.ModelContextLibrary;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.model.factory.ModelFactory;
import org.openflexo.technologyadapter.trace.TraceTechnologyAdapter;
import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObjectInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObjectState;
import org.openflexo.technologyadapter.trace.model.OBPTraceComponent;
import org.openflexo.technologyadapter.trace.model.OBPTraceConfiguration;
import org.openflexo.technologyadapter.trace.model.OBPTraceContextInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceContextInstance.OBPTraceContextInstanceImpl;
import org.openflexo.technologyadapter.trace.model.OBPTraceContextState;
import org.openflexo.technologyadapter.trace.model.OBPTraceData;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessage;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessageInternal;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessageReceive;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessageSend;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessageSynchro;
import org.openflexo.technologyadapter.trace.model.OBPTraceProcessInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceProcessInstance.OBPTraceProcessInstanceImpl;
import org.openflexo.technologyadapter.trace.model.OBPTraceProcessState;
import org.openflexo.technologyadapter.trace.model.OBPTraceObject;
import org.openflexo.technologyadapter.trace.model.OBPTrace;
import org.openflexo.technologyadapter.trace.model.OBPTracePropertyInstance;
import org.openflexo.technologyadapter.trace.model.OBPTracePropertyInstance.OBPTracePropertyInstanceImpl;
import org.openflexo.technologyadapter.trace.model.OBPTracePropertyState;
import org.openflexo.technologyadapter.trace.model.OBPTraceState;
import org.openflexo.technologyadapter.trace.model.OBPTraceTransition;
import org.openflexo.technologyadapter.trace.model.OBPTraceVariable;

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
					OBPTraceContextState.class,
					OBPTraceData.class,
					OBPTraceProcessState.class,
					OBPTraceTransition.class,
					OBPTraceConfiguration.class,
					OBPTracePropertyState.class,
					OBPTraceMessageReceive.class,
					OBPTraceMessageInternal.class,
					OBPTraceMessageSend.class,
					OBPTraceMessageSynchro.class,
					OBPTraceMessage.class,
					OBPTraceState.class,
					OBPTraceBehaviourObjectInstance.class,
					OBPTraceBehaviourObjectState.class,
					OBPTraceContextInstance.class,
					OBPTraceProcessInstance.class,
					OBPTracePropertyInstance.class,
					OBPTraceVariable.class));
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
					message.setToBehaviourObject(flexoTraceOBP.getBehaviourObjectInstanceFromValue(nextMessage.getTargetProcName()));
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
					message.setFromBehaviourObject(flexoTraceOBP.getBehaviourObjectInstanceFromValue(previousMessage.getSourceProcName()));
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
		flexoMessage.setToBehaviourObject(traceOBP.getBehaviourObjectInstanceFromValue(flexoMessage.getTargetProcName()));
		flexoMessage.setTechnologyAdapter(technologyAdapter);
		traceObjects.put(transitionSequence, flexoMessage);
		return flexoMessage;
	}
	
	public OBPTraceMessage convertMessageSendAsynchro(TransitionSequenceSendAsynchro transitionSequence, OBPTrace traceOBP){
		OBPTraceMessageSend flexoMessage = factory.newInstance(OBPTraceMessageSend.class);
		flexoMessage.setTransitionSequence(transitionSequence);
		flexoMessage.setFromBehaviourObject(traceOBP.getBehaviourObjectInstanceFromValue(flexoMessage.getSourceProcName()));
		flexoMessage.setTechnologyAdapter(technologyAdapter);
		traceObjects.put(transitionSequence, flexoMessage);
		return flexoMessage;
	}
	
	public OBPTraceMessage convertMessageSynchro(TransitionSequenceSynchro transitionSequence, OBPTrace traceOBP){
		OBPTraceMessageSynchro flexoMessage = factory.newInstance(OBPTraceMessageSynchro.class);
		flexoMessage.setTransitionSequence(transitionSequence);
		flexoMessage.setToBehaviourObject(traceOBP.getBehaviourObjectInstanceFromValue(flexoMessage.getTargetProcName()));
		flexoMessage.setFromBehaviourObject(traceOBP.getBehaviourObjectInstanceFromValue(flexoMessage.getSourceProcName()));
		flexoMessage.setTechnologyAdapter(technologyAdapter);
		traceObjects.put(transitionSequence, flexoMessage);
		return flexoMessage;
	}
	
	public OBPTraceConfiguration convertConfigData(ConfigData configData, OBPTrace traceOBP){
		OBPTraceConfiguration flexoConfigData = factory.newInstance(OBPTraceConfiguration.class);
		for (Parser.Process process : configData.getProcessList()) {
			flexoConfigData.addToOBPTraceBehaviourObjectStates(convertProcess(process,traceOBP));
		}
		for (Parser.Property property : configData.getPropertyList()) {
			flexoConfigData.addToOBPTraceBehaviourObjectStates(convertProperty(property,traceOBP));
		}
		flexoConfigData.addToOBPTraceBehaviourObjectStates(convertContext(configData.context,traceOBP));
		flexoConfigData.setConfigData(configData);
		flexoConfigData.setTechnologyAdapter(technologyAdapter);
		flexoConfigData.setName(Integer.toString(configData.getNoConfig()));
		traceObjects.put(configData, flexoConfigData);
		return flexoConfigData;
	}
	
	public OBPTraceContextState convertContext(Parser.Context context, OBPTrace traceOBP){
		String contextValue = OBPTraceContextInstanceImpl.computeValueFromRowName(context.contextName);
		OBPTraceContextInstance instance = (OBPTraceContextInstance) traceOBP.getBehaviourObjectInstanceFromValue(contextValue);
		if(instance==null){
			instance = factory.newInstance(OBPTraceContextInstance.class);
			instance.setName(context.contextName);
			traceOBP.addToOBPTraceBehaviourObjectInstances(instance);
			instance.setTechnologyAdapter(technologyAdapter);
		}
		OBPTraceContextState flexoContext = factory.newInstance(OBPTraceContextState.class);
		flexoContext.setTraceOBPContext(context);
		flexoContext.setTechnologyAdapter(technologyAdapter);
		flexoContext.setState(convertState(context.getContextState(), traceOBP));
		flexoContext.setOBPTraceBehaviourObjectInstance(instance);
		traceObjects.put(context, flexoContext);
		return flexoContext;
	}
	
	public OBPTraceProcessState convertProcess(Parser.Process process, OBPTrace traceOBP){
		String processValue = OBPTraceProcessInstanceImpl.computeValueFromRowName(process.processName);
		OBPTraceProcessInstance instance = (OBPTraceProcessInstance) traceOBP.getBehaviourObjectInstanceFromValue(processValue);
		if(instance==null){
			instance = factory.newInstance(OBPTraceProcessInstance.class);
			instance.setName(process.processName);
			traceOBP.addToOBPTraceBehaviourObjectInstances(instance);
			instance.setTechnologyAdapter(technologyAdapter);
		}
		OBPTraceProcessState flexoProcess = factory.newInstance(OBPTraceProcessState.class);
		for(Donnee donnee : process.getDonneeList()){
			OBPTraceData data = convertData(donnee,traceOBP);
			flexoProcess.addToOBPTraceData(data);
			OBPTraceVariable var = instance.getVariableNamed(data.getName());
			if(var==null){
				var = factory.newInstance(OBPTraceVariable.class);
				var.setName(data.getName());
				instance.addToVariables(var);
				var.setTechnologyAdapter(technologyAdapter);
			}
			var.addToValues(data);
		}
		flexoProcess.setProcess(process);
		flexoProcess.setTechnologyAdapter(technologyAdapter);
		flexoProcess.setState(convertState(process.getProcessState(), traceOBP));
		flexoProcess.setOBPTraceBehaviourObjectInstance(instance);
		traceObjects.put(process, flexoProcess);
		return flexoProcess;
	}
	
	public OBPTracePropertyState convertProperty(Parser.Property property, OBPTrace traceOBP){
		String propertyValue = OBPTracePropertyInstanceImpl.computeValueFromRowName(property.propertyName);
		OBPTracePropertyInstance instance = (OBPTracePropertyInstance) traceOBP.getBehaviourObjectInstanceFromValue(propertyValue);
		if(instance==null){
			instance = factory.newInstance(OBPTracePropertyInstance.class);
			instance.setName(property.propertyName);
			traceOBP.addToOBPTraceBehaviourObjectInstances(instance);
			instance.setTechnologyAdapter(technologyAdapter);
		}
		OBPTracePropertyState flexoProperty = factory.newInstance(OBPTracePropertyState.class);
		flexoProperty.setProperty(property);
		flexoProperty.setTechnologyAdapter(technologyAdapter);
		flexoProperty.setState(convertState(property.getPropertyState(), traceOBP));
		flexoProperty.setOBPTraceBehaviourObjectInstance(instance);
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
