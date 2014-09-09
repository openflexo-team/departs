package org.openflexo.module.traceanalysis.model.mask;

import java.util.List;

import org.openflexo.foundation.DefaultFlexoObject;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObjectInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceObject;
import org.openflexo.technologyadapter.trace.model.OBPTraceVariable;

public abstract class MaskedElement<FlexoObject> extends DefaultFlexoObject{

	private Mask mask;
	
	private final FlexoObject maskedElement;
	
	public abstract void applyMask();
	
	public abstract List<OBPTraceObject> getElements();
	
	abstract public boolean isMasked();

	public MaskedElement(FlexoObject maskedElement,Mask mask) {
		super();
		this.mask = mask;
		this.maskedElement = maskedElement;
	}

	public Mask getMask() {
		return mask;
	}

	public void setMask(Mask mask) {
		this.mask = mask;
	}

	public FlexoObject getMaskedElement() {
		return maskedElement;
	}

}
