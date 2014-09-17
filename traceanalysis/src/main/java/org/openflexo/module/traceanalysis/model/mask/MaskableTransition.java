package org.openflexo.module.traceanalysis.model.mask;

import org.openflexo.foundation.FlexoObject;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceTransition;

public class MaskableTransition extends MaskableElement{
		
		private final OBPTraceTransition value;
	
		public MaskableTransition(OBPTraceTransition value, MaskableElement parent,TraceVirtualModelInstance trace) {
				super(parent, trace);
				this.value = value;
		}

	
		@Override
		public FlexoObject getType() {
			return null;
		}

		@Override
		public OBPTraceTransition getValue() {
			return value;
		}


		@Override
		public String getFullStringRepresentation() {
			// TODO Auto-generated method stub
			return getValue().getName();
		}


		@Override 
		public String getName(){
			return getValue().getName();
		}
	
	}