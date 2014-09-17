package org.openflexo.module.traceanalysis.model.mask;

import org.openflexo.foundation.FlexoObject;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObjectInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceConfiguration;

public class MaskableBehaviourObject extends MaskableFederatedElement{

	private final OBPTraceBehaviourObjectInstance value;
	
	public MaskableBehaviourObject(FlexoObject type, OBPTraceBehaviourObjectInstance value, MaskableFederatedElement maskableElement,
			TraceVirtualModelInstance trace) {
		super(type, value, maskableElement,trace);
		this.value =value;
	}

	@Override
	public OBPTraceBehaviourObjectInstance getValue(){
		return value;
	}
	
	public String getValueForConfiguration(OBPTraceConfiguration configuration){
		return value.getName() + "@"+ value.getOBPTraceBehaviourObjectStateInConfig(configuration).getState().getName();
	}
	
	@Override
	public boolean differsFromPreviousConfiguration(OBPTraceConfiguration current, OBPTraceConfiguration previous){
		return (!value.getOBPTraceBehaviourObjectStateInConfig(current).getState().getName().equals(value.getOBPTraceBehaviourObjectStateInConfig(previous).getState().getName()));
	}
	
}
