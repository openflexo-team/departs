package org.openflexo.traceanalysis;

public class OBPTransitionImpl implements OBPTransition {

	private final OBPConfiguration previousConfiguration;
	private final OBPConfiguration nextConfiguration;

	public OBPTransitionImpl(OBPConfiguration previousConfiguration, OBPConfiguration nextConfiguration) {
		super();
		this.previousConfiguration = previousConfiguration;
		this.nextConfiguration = nextConfiguration;
	}

	@Override
	public OBPConfiguration getPreviousConfiguration() {
		return previousConfiguration;
	}

	@Override
	public OBPConfiguration getNextConfiguration() {
		return nextConfiguration;
	}

}
