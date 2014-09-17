package org.openflexo.module.traceanalysis.model.mask;

import java.util.ArrayList;

import org.openflexo.foundation.FlexoObject;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.module.traceanalysis.model.mask.MaskableElement.MaskMode;
import org.openflexo.technologyadapter.trace.model.OBPTrace;
import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObjectInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceConfiguration;
import org.openflexo.technologyadapter.trace.model.OBPTraceObject;
import org.openflexo.technologyadapter.trace.model.OBPTraceVariable;

public class MaskableFederatedElement extends MaskableElement{
		
		private final FlexoObject type;
		
		private final OBPTraceObject value;

		public MaskableFederatedElement(FlexoObject type, OBPTraceObject value, MaskableElement parent,TraceVirtualModelInstance trace) {
			super(parent,trace);
			this.type = type;
			this.value = value;
		}

		@Override
		public FlexoObject getType() {
			return type;
		}

		@Override
		public OBPTraceObject getValue() {
			return value;
		}

		
		@Override 
		public String getName(){
			return getValue().getName();
		}
		
		public boolean isRelevant(OBPTraceConfiguration current, OBPTraceConfiguration previous){
			if(getTrace().getTraceOBP().getInitConfiguration().equals(current)){
				return true;
			}else if(differsFromPreviousConfiguration(current,previous) && getMaskMode().equals(MaskMode.VISIBLE_IF_RELEVANT)){
				return true;
			}
			return false;
		}
		
		public boolean differsFromPreviousConfiguration(OBPTraceConfiguration current, OBPTraceConfiguration previous){
			return false;
		}
		
		public String getFullStringRepresentation(){
			String color;
			if(getMaskMode().equals(MaskMode.VISIBLE_IF_RELEVANT)){
				color = "#DAA520";
			}else{
				color = "#FF7F50";
			}
			 
			return "<html>"+ getName() + " " + "<font color=\""+color+"\"><i>["+ getMaskMode().name().toLowerCase() + "]</i></font>" + "</html>";
		}
	}