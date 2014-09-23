package org.openflexo.module.traceanalysis.model.mask;

import org.openflexo.foundation.FlexoObject;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceTransition;

public class MaskableTransition extends MaskableElement{
		
		private final OBPTraceTransition value;
		
		private MaskableConfiguration sourceConfiguration;
		
		private MaskableConfiguration targetConfiguration;
	
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

		public MaskableConfiguration getSourceConfiguration() {
			return sourceConfiguration;
		}


		public void setSourceConfiguration(MaskableConfiguration sourceConfiguration) {
			this.sourceConfiguration = sourceConfiguration;
		}


		public MaskableConfiguration getTargetConfiguration() {
			return targetConfiguration;
		}


		public void setTargetConfiguration(MaskableConfiguration targetConfiguration) {
			this.targetConfiguration = targetConfiguration;
		}

		public boolean differsFromPreviousConfiguration(MaskableFederatedElement element){
			return element.isSelected() && element.differsFromPreviousConfiguration(sourceConfiguration.getValue(), targetConfiguration.getValue());
		}
		
		public boolean isVisible(MaskableFederatedElement element){
			return element.isVisible(targetConfiguration.getValue());
		}
		
		@Override
		public String getFullStringRepresentation() {
			return sourceConfiguration.getName() + "- " + targetConfiguration.getName(); 
		}


		@Override 
		public String getName(){
			return getValue().getName();
		}
		
	}