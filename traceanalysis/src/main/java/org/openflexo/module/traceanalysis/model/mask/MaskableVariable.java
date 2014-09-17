package org.openflexo.module.traceanalysis.model.mask;

import org.openflexo.foundation.FlexoObject;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceConfiguration;
import org.openflexo.technologyadapter.trace.model.OBPTraceVariable;

public class MaskableVariable extends MaskableFederatedElement{

	private final OBPTraceVariable value;
	
	public MaskableVariable(FlexoObject type, OBPTraceVariable value,
			MaskableFederatedElement parent, TraceVirtualModelInstance trace) {
		super(type, value, parent, trace);
		this.value = value;
	}

	public String getValueForConfiguration(OBPTraceConfiguration configuration){
		return value.getStringValueForConfiguration(configuration);
	}
	
	@Override
	public OBPTraceVariable getValue(){
		return value;
	}
	
	@Override
	public boolean differsFromPreviousConfiguration(OBPTraceConfiguration current, OBPTraceConfiguration previous){
		return !(value.getStringValueForConfiguration(current).equals(value.getStringValueForConfiguration(previous)));
	}
}
