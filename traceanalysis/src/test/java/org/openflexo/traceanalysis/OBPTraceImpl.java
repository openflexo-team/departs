package org.openflexo.traceanalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OBPTraceImpl implements OBPTrace {

	private final List<OBPConfiguration> configurations;
	private final Map<OBPConfiguration, OBPTransition> transitions;
	private final List<Component> components;

	public OBPTraceImpl() {
		configurations = new ArrayList<OBPConfiguration>();
		transitions = new HashMap<OBPConfiguration, OBPTransition>();
		components = new ArrayList<Component>();
	}

	@Override
	public OBPConfiguration getInitConfiguration() {
		if (getSize() > 0) {
			return getConfiguration(0);
		}
		return null;
	}

	@Override
	public OBPConfiguration getLastConfiguration() {
		if (getSize() > 0) {
			return getConfiguration(getSize() - 1);
		}
		return null;
	}

	@Override
	public int getIndex(OBPConfiguration config) {
		return configurations.indexOf(config);
	}

	@Override
	public OBPConfiguration getPreviousConfiguration(OBPConfiguration config) {
		int i = getIndex(config);
		if (i > 0) {
			return configurations.get(i - 1);
		}
		return null;
	}

	@Override
	public OBPConfiguration getNextConfiguration(OBPConfiguration config) {
		int i = getIndex(config);
		if (i > -1 && i + 1 < configurations.size()) {
			return configurations.get(i + 1);
		}
		return null;
	}

	@Override
	public int getSize() {
		return configurations.size();
	}

	@Override
	public OBPConfiguration getConfiguration(int index) {
		return configurations.get(index);
	}

	@Override
	public OBPTransition getTransition(OBPConfiguration from, OBPConfiguration to) {
		return transitions.get(from);
	}

	@Override
	public List<Component> getComponents() {
		return components;
	}

	public void addToComponents(Component aComponent) {
		components.add(aComponent);
	}

	// Debug method to be removed
	public OBPTransition addConfiguration(int id) {
		OBPConfiguration previousConfig = (configurations.size() > 0 ? configurations.get(configurations.size() - 1) : null);
		OBPConfiguration newConfig = new OBPConfigurationImpl(id);
		configurations.add(newConfig);
		if (previousConfig != null) {
			OBPTransition newTransition = new OBPTransitionImpl(previousConfig, newConfig);
			transitions.put(previousConfig, newTransition);
			return newTransition;
		}
		return null;

	}
}
