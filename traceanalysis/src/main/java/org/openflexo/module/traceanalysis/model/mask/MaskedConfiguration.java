package org.openflexo.module.traceanalysis.model.mask;

import java.util.ArrayList;
import java.util.List;

import org.openflexo.antar.binding.TypeUtils;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance.FederatedElement;
import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObjectInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceConfiguration;
import org.openflexo.technologyadapter.trace.model.OBPTraceObject;
import org.openflexo.technologyadapter.trace.model.OBPTraceVariable;

public class MaskedConfiguration extends MaskedElement<OBPTraceConfiguration>{

	private List<OBPTraceObject> elements;
	
	public MaskedConfiguration(OBPTraceConfiguration maskedElement, Mask mask) {
		super(maskedElement, mask);
		elements = new ArrayList<OBPTraceObject>();
	}

	@Override
	public void applyMask() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<OBPTraceObject> getElements() {
		elements.clear();
		elements.addAll(getMask().getBehaviourObjects());
		elements.addAll(getMask().getVariables());
		return elements;
	}
	

	
	@Override
	public boolean isMasked() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
