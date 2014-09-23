package org.openflexo.module.traceanalysis.model.mask;

import org.openflexo.foundation.FlexoObject;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceConfiguration;
import org.openflexo.technologyadapter.trace.model.OBPTraceObject;

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
		
		/**
		 * Return true if the element is visible.
		 * The element is visible if is always visible and selected
		 * Or if it is relevant, selected and differs from the last configuration 
		 */
		public boolean isVisible(OBPTraceConfiguration current){
			if(isRelevant()){
				return isSelected() && differsFromPreviousConfiguration(current);
			}else{
				return isSelected();
			}
		}
		
		public boolean isSelected(){
			return getMask().isContainedInSelection(this);
		}
		
		public boolean isRelevant(){
			return getMaskMode().equals(MaskMode.VISIBLE_IF_RELEVANT);
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