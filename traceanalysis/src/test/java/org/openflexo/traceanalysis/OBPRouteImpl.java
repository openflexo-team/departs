/*
 * (c) Copyright 2014- Openflexo
 *
 * This file is part of OpenFlexo.
 *
 * OpenFlexo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenFlexo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenFlexo. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.openflexo.traceanalysis;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OBPRouteImpl implements OBPRoute {

	private final OBPTrace trace;

	private final Map<OBPConfiguration, OBPConfigurationInRoute> configMap;
	private final List<OBPConfigurationInRoute> visibleConfigurations;

	private final Map<Component, ComponentInRoute> componentMap;
	private final List<ComponentInRoute> visibleComponents;

	private final Map<Message, MessageInRoute> messageMap;

	private final PropertyChangeSupport pcSupport;

	public OBPRouteImpl(OBPTrace trace) {
		pcSupport = new PropertyChangeSupport(this);
		this.trace = trace;
		visibleConfigurations = new ArrayList<OBPConfigurationInRoute>();
		configMap = new HashMap<OBPConfiguration, OBPConfigurationInRoute>();
		visibleConfigurations.add(getOBPConfigurationInRoute(trace.getInitConfiguration()));
		visibleConfigurations.add(getOBPConfigurationInRoute(trace.getLastConfiguration()));
		visibleComponents = new ArrayList<ComponentInRoute>();
		componentMap = new HashMap<Component, ComponentInRoute>();
		for (Component c : trace.getComponents()) {
			visibleComponents.add(getComponentInRoute(c));
		}
		messageMap = new HashMap<Message, MessageInRoute>();
	}

	@Override
	public PropertyChangeSupport getPropertyChangeSupport() {
		return pcSupport;
	}

	@Override
	public String getDeletedProperty() {
		return null;
	}

	@Override
	public OBPTrace getTrace() {
		return trace;
	}

	@Override
	public OBPConfigurationInRoute getOBPConfigurationInRoute(OBPConfiguration configuration) {
		OBPConfigurationInRoute returned = configMap.get(configuration);
		if (returned == null) {
			returned = new OBPConfigurationInRouteImpl(configuration, this);
			configMap.put(configuration, returned);
		}
		return returned;
	}

	@Override
	public boolean isVisible(OBPConfiguration config) {
		return visibleConfigurations.contains(getOBPConfigurationInRoute(config));
	}

	@Override
	public int getIndex(OBPConfiguration config) {
		return visibleConfigurations.indexOf(getOBPConfigurationInRoute(config));
	}

	@Override
	public int getIndex(OBPConfigurationInRoute configInRoute) {
		return visibleConfigurations.indexOf(configInRoute);
	}

	@Override
	public OBPConfigurationInRoute getPreviousVisibleConfiguration(OBPConfiguration config) {
		int i = getIndex(config);
		if (i == -1) {
			// This config is not visible
			OBPConfiguration pConfig = trace.getPreviousConfiguration(config);
			if (pConfig != null) {
				if (getIndex(pConfig) > -1) {
					return getOBPConfigurationInRoute(pConfig);
				}
				return getPreviousVisibleConfiguration(pConfig);
			}
			return null;
		}
		if (i > 0) {
			return visibleConfigurations.get(i - 1);
		}
		return null;
	}

	@Override
	public OBPConfigurationInRoute getNextVisibleConfiguration(OBPConfiguration config) {
		int i = getIndex(config);
		if (i == -1) {
			// This config is not visible
			OBPConfiguration nConfig = trace.getNextConfiguration(config);
			if (nConfig != null) {
				if (getIndex(nConfig) > -1) {
					return getOBPConfigurationInRoute(nConfig);
				}
				return getNextVisibleConfiguration(nConfig);
			}
			return null;
		}
		if (i > -1 && i + 1 < visibleConfigurations.size()) {
			return visibleConfigurations.get(i + 1);
		}
		return null;
	}

	@Override
	public void show(OBPConfiguration config) {
		System.out.println("show " + config);
		// System.out.println("visibleConfigurations=" + visibleConfigurations);

		OBPConfigurationInRoute previousVisible = getPreviousVisibleConfiguration(config);

		int oldSize = getSize();

		// System.out.println("previous visible = " + previousVisible);
		// System.out.println("index = " + getIndex(previousVisible));

		visibleConfigurations.add(getIndex(previousVisible) + 1, getOBPConfigurationInRoute(config));

		// System.out.println("NOW visibleConfigurations=" + visibleConfigurations);

		getPropertyChangeSupport().firePropertyChange("visibleConfigurations", null, getOBPConfigurationInRoute(config));

		for (OBPConfigurationInRoute c : visibleConfigurations) {
			c.getPropertyChangeSupport().firePropertyChange("visibleIndex", null, c.getVisibleIndex());
		}

		getPropertyChangeSupport().firePropertyChange("size", oldSize, getSize());

	}

	@Override
	public void hide(OBPConfiguration config) {
		if (config == getTrace().getInitConfiguration() || config == getTrace().getLastConfiguration()) {
			return;
		}

		int oldSize = getSize();

		System.out.println("hide " + config);
		visibleConfigurations.remove(getOBPConfigurationInRoute(config));

		getPropertyChangeSupport().firePropertyChange("visibleConfigurations", null, getOBPConfigurationInRoute(config));

		for (OBPConfigurationInRoute c : visibleConfigurations) {
			c.getPropertyChangeSupport().firePropertyChange("visibleIndex", null, c.getVisibleIndex());
		}

		getPropertyChangeSupport().firePropertyChange("size", oldSize, getSize());

	}

	@Override
	public int getSize() {
		return visibleConfigurations.size();
	}

	@Override
	public OBPConfigurationInRoute getConfiguration(int index) {
		return visibleConfigurations.get(index);
	}

	public class AbstractTransitionArtefactImpl implements AbstractTransitionArtefact {

		private final OBPConfigurationInRoute startConfigInRoute;
		private final OBPConfigurationInRoute endConfigInRoute;

		public AbstractTransitionArtefactImpl(OBPConfigurationInRoute startConfigInRoute, OBPConfigurationInRoute endConfigInRoute) {
			super();
			this.startConfigInRoute = startConfigInRoute;
			this.endConfigInRoute = endConfigInRoute;
		}

		@Override
		public OBPConfigurationInRoute getStartConfiguration() {
			return startConfigInRoute;
		}

		@Override
		public OBPConfigurationInRoute getEndConfiguration() {
			return endConfigInRoute;
		}

	}

	private final Map<OBPConfigurationInRoute, Map<OBPConfigurationInRoute, AbstractTransitionArtefact>> abstractTransitions = new HashMap<OBPConfigurationInRoute, Map<OBPConfigurationInRoute, AbstractTransitionArtefact>>();

	@Override
	public AbstractTransitionArtefact getAbstractTransition(OBPConfigurationInRoute from, OBPConfigurationInRoute to) {
		Map<OBPConfigurationInRoute, AbstractTransitionArtefact> map = abstractTransitions.get(from);
		if (map == null) {
			map = new HashMap<OBPConfigurationInRoute, AbstractTransitionArtefact>();
			abstractTransitions.put(from, map);
		}
		AbstractTransitionArtefact returned = map.get(to);
		if (returned == null) {
			returned = new AbstractTransitionArtefactImpl(from, to);
			map.put(to, returned);
		}
		return returned;
	}

	@Override
	public List<ComponentInRoute> getVisibleComponents() {
		return visibleComponents;
	}

	@Override
	public ComponentInRoute getComponentInRoute(Component component) {
		ComponentInRoute returned = componentMap.get(component);
		if (returned == null) {
			returned = new ComponentInRoute(component, this);
			componentMap.put(component, returned);
		}
		return returned;
	}

	@Override
	public MessageInRoute getMessageInRoute(Message message) {
		MessageInRoute returned = messageMap.get(message);
		if (returned == null) {
			returned = new MessageInRoute(message, this);
			messageMap.put(message, returned);
		}
		return returned;
	}

}
