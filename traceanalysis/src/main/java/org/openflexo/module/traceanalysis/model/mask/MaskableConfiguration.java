package org.openflexo.module.traceanalysis.model.mask;

import org.openflexo.foundation.FlexoObject;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObjectInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceConfiguration;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessage;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessageReceive;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessageSend;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessageSynchro;
import org.openflexo.technologyadapter.trace.model.OBPTraceObject;
import org.openflexo.technologyadapter.trace.model.OBPTraceTransition;
import org.openflexo.technologyadapter.trace.model.OBPTraceVariable;

public class MaskableConfiguration extends MaskableElement{
			
		private final OBPTraceConfiguration value;

		public MaskableConfiguration(OBPTraceConfiguration value, MaskableFederatedElement parent,TraceVirtualModelInstance trace) {
			super(parent,trace);
			this.value = value;
		}

		@Override
		public FlexoObject getType() {
			return null;
		}

		@Override
		public OBPTraceConfiguration getValue() {
			return value;
		}
		
		@Override 
		public String getName(){
			return getValue().getName();
		}

		@Override
		public String getFullStringRepresentation() {
			return getName();
		}
		
		@Override
		public MaskableFederatedElement getMaskableElement(OBPTraceObject object) {
			return (MaskableFederatedElement)super.getMaskableElement(object);
		}
		
		public boolean differsFromPreviousConfiguration(OBPTraceConfiguration currentConfig,OBPTraceConfiguration nextConfig){
			for(OBPTraceBehaviourObjectInstance behaviourObject: getOBPTrace().getOBPTraceBehaviourObjectInstances()){
				if(behaviourDiffersFromPreviousConfiguration(currentConfig, behaviourObject )){
					return true;
				}
			}

			/*if(messageOccurs()){
				return true;
			}*/
			return false;
		}
		
		private boolean behaviourDiffersFromPreviousConfiguration(OBPTraceConfiguration currentConfig,OBPTraceBehaviourObjectInstance behaviourObject ){
			if(getMaskableElement(behaviourObject).isVisible(currentConfig)){
				return true;
			}
			for(MaskableElement element: getMaskableElement(behaviourObject).getChilds()){
				if(variableDiffersFromPreviousConfiguration(currentConfig,(MaskableVariable) element)){
					return true;
				}
			}
			return false;
		}
			
		private boolean variableDiffersFromPreviousConfiguration(OBPTraceConfiguration currentConfig,MaskableVariable variable ){
			return variable.isVisible(currentConfig);
		}		

		private boolean messageOccurs(){
			return messageWillBeSend() || messageHasBeenReceived();
		}
		
		private boolean messageWillBeSend(){
			OBPTraceTransition nextTransition = getOBPTrace().getTransition(value, getOBPTrace().getNextConfiguration(value));
			if(nextTransition != null){
				for(OBPTraceMessage message : nextTransition.getOBPTraceMessages()){
					if(message instanceof OBPTraceMessageReceive || message instanceof OBPTraceMessageSend || message instanceof OBPTraceMessageSynchro){
						return true;
					}
				}
			}
			return false;
		}
		
		private boolean messageHasBeenReceived(){
			OBPTraceTransition lastTransition = getOBPTrace().getTransition(value, getOBPTrace().getPreviousConfiguration(value));
			if(lastTransition != null){
				for(OBPTraceMessage message : lastTransition.getOBPTraceMessages()){
					if(message instanceof OBPTraceMessageReceive || message instanceof OBPTraceMessageSend || message instanceof OBPTraceMessageSynchro){
						return true;
					}
				}
			}
			
			return false;
		}
	}