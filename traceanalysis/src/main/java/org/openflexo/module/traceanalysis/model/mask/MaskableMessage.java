package org.openflexo.module.traceanalysis.model.mask;

import org.openflexo.foundation.FlexoObject;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessage;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessageReceive;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessageSend;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessageSynchro;
import org.openflexo.technologyadapter.trace.model.OBPTraceObject;

public class MaskableMessage extends MaskableElement{
			
		private final OBPTraceMessage message;

		public MaskableMessage(OBPTraceMessage message, MaskableTransition parent,TraceVirtualModelInstance trace) {
			super(parent,trace);
			this.message = message;
		}

		@Override
		public FlexoObject getType() {
			return null;
		}

		@Override
		public OBPTraceMessage getValue() {
			return message;
		}
		
		@Override 
		public String getName(){
			return getValue().getName();
		}

		@Override
		public String getFullStringRepresentation() {
			return getValue().getInformationLabel() + " = " +getValue().getDataValue();
		}

		public boolean isVisible(){
			return message instanceof OBPTraceMessageReceive || message instanceof OBPTraceMessageSend || message instanceof OBPTraceMessageSynchro;
		}
	}