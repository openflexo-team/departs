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
import java.util.HashMap;
import java.util.Map;

public class OBPConfigurationInRouteImpl implements OBPConfigurationInRoute {

	private final OBPRoute route;
	private final OBPConfiguration configuration;
	private final Map<OBPConfiguration, RelativeConfigurationArtefact> relativeConfigurationArtefacts;

	private final PropertyChangeSupport pcSupport;

	public OBPConfigurationInRouteImpl(OBPConfiguration configuration, OBPRoute route) {
		super();
		pcSupport = new PropertyChangeSupport(this);
		this.route = route;
		this.configuration = configuration;
		relativeConfigurationArtefacts = new HashMap<OBPConfiguration, RelativeConfigurationArtefact>();
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
	public OBPRoute getRoute() {
		return route;
	}

	@Override
	public OBPConfiguration getConfiguration() {
		return configuration;
	}

	@Override
	public double getLocation() {
		// System.out.println("Pour la config " + getConfiguration() + " l'index est " + route.getIndex(getConfiguration()));
		return route.getIndex(getConfiguration()) * RouteDrawing.ROW_HEIGHT;
	}

	@Override
	public boolean nextTransitionIsAbstract() {
		return !getRoute().isVisible(getRoute().getTrace().getNextConfiguration(getConfiguration()));
	}

	@Override
	public RelativeConfigurationArtefact getPreviousConfigurationArtefact() {

		System.out.println("Pour " + this + " le precedent c'est " + getRoute().getTrace().getPreviousConfiguration(getConfiguration()));

		return getRelativeConfigurationArtefact(getRoute().getTrace().getPreviousConfiguration(getConfiguration()));
	}

	@Override
	public RelativeConfigurationArtefact getNextConfigurationArtefact() {

		System.out.println("Pour " + this + " le suivant c'est " + getRoute().getTrace().getNextConfiguration(getConfiguration()));

		return getRelativeConfigurationArtefact(getRoute().getTrace().getNextConfiguration(getConfiguration()));
	}

	private RelativeConfigurationArtefact getRelativeConfigurationArtefact(OBPConfiguration config) {
		RelativeConfigurationArtefact returned = relativeConfigurationArtefacts.get(config);
		if (returned == null) {
			returned = new RelativeConfigurationArtefactImpl(config);
			relativeConfigurationArtefacts.put(config, returned);
		}
		return returned;
	}

	public class RelativeConfigurationArtefactImpl implements RelativeConfigurationArtefact {

		private final OBPConfiguration config;

		public RelativeConfigurationArtefactImpl(OBPConfiguration config) {
			this.config = config;
		}

		@Override
		public OBPConfiguration getConfiguration() {
			return config;
		}

		@Override
		public OBPConfigurationInRoute getConfigurationInRoute() {
			return OBPConfigurationInRouteImpl.this;
		}

		@Override
		public String toString() {
			return "RelativeConfigurationArtefact referencing " + getConfiguration() + " from " + getConfigurationInRoute();
		}

	}

	@Override
	public String toString() {
		return "OBPConfigurationInRoute " + getConfiguration().getIdentifier();
	}
}
