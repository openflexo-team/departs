package org.openflexo.traceanalysis;

import java.util.ArrayList;
import java.util.List;

public class OBPTransitionImpl implements OBPTransition {

	private final OBPConfiguration previousConfiguration;
	private final OBPConfiguration nextConfiguration;
	private final List<Message> messages;

	public OBPTransitionImpl(OBPConfiguration previousConfiguration, OBPConfiguration nextConfiguration) {
		super();
		this.previousConfiguration = previousConfiguration;
		this.nextConfiguration = nextConfiguration;
		messages = new ArrayList<Message>();
	}

	@Override
	public OBPConfiguration getPreviousConfiguration() {
		return previousConfiguration;
	}

	@Override
	public OBPConfiguration getNextConfiguration() {
		return nextConfiguration;
	}

	@Override
	public String toString() {
		return "OBPTransition " + getPreviousConfiguration() + " - " + getNextConfiguration();
	}

	@Override
	public List<Message> getMessages() {
		return messages;
	}

	public void addToMessages(Message aMessage) {
		messages.add(aMessage);
	}

}
