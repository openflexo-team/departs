package org.openflexo.module.traceanalysis.model.mask;

import java.util.List;

import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance.FederatedElement;
import org.openflexo.technologyadapter.trace.model.OBPTraceObject;

public class MaskedFederatedElement extends MaskedElement<FederatedElement>{


	public MaskedFederatedElement(FederatedElement maskedElement, Mask mask) {
		super(maskedElement, mask);
	}

	@Override
	public void applyMask() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<OBPTraceObject> getElements() {
		return null;
	}
	

	@Override
	public boolean isMasked() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
