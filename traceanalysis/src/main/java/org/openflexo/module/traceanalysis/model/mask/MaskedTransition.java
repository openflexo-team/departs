package org.openflexo.module.traceanalysis.model.mask;

import java.util.ArrayList;
import java.util.List;

import org.openflexo.technologyadapter.trace.model.OBPTraceObject;
import org.openflexo.technologyadapter.trace.model.OBPTraceTransition;

public class MaskedTransition extends MaskedElement<OBPTraceTransition>{

	private List<OBPTraceObject> elements;
	
	public MaskedTransition(OBPTraceTransition maskedElement, Mask mask) {
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
