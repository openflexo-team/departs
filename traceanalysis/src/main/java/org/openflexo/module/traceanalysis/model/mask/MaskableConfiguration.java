package org.openflexo.module.traceanalysis.model.mask;

import org.openflexo.foundation.FlexoObject;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObjectInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceConfiguration;
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
		
		public boolean differsFromPreviousConfiguration(OBPTraceConfiguration currentConfig, OBPTraceConfiguration previousConfig){
			for(OBPTraceBehaviourObjectInstance behaviourObject: currentConfig.getBehaviourObjects()){
				if(((MaskableFederatedElement)getMaskableElement(behaviourObject)).differsFromPreviousConfiguration(currentConfig, previousConfig)){
					return true;
				}
				for(OBPTraceVariable variable: behaviourObject.getVariables()){
					if(((MaskableFederatedElement)getMaskableElement(variable)).differsFromPreviousConfiguration(currentConfig, previousConfig)){
						return true;
					}
				}
			}
			return false;
		}
	}