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

package org.openflexo.module.traceanalysis.view.routeview;

import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

import org.openflexo.foundation.DefaultFlexoObject;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.technologyadapter.trace.model.OBPTraceConfiguration;

public class OBPConfigurationInRouteImpl extends DefaultFlexoObject implements OBPConfigurationInRoute {

	private final OBPRoute route;
	private final OBPTraceConfiguration configuration;
	private final Map<OBPTraceConfiguration, RelativeConfigurationArtefact> relativeConfigurationArtefacts;

	private final PropertyChangeSupport pcSupport;

	public OBPConfigurationInRouteImpl(OBPTraceConfiguration configuration, OBPRoute route) {
		super();
		pcSupport = new PropertyChangeSupport(this);
		this.route = route;
		this.configuration = configuration;
		relativeConfigurationArtefacts = new HashMap<OBPTraceConfiguration, RelativeConfigurationArtefact>();
	}

	@Override
	public PropertyChangeSupport getPropertyChangeSupport() {
		return pcSupport;
	}


	@Override
	public OBPRoute getRoute() {
		return route;
	}

	@Override
	public OBPTraceConfiguration getConfiguration() {
		return configuration;
	}

	@Override
	public double getVisibleIndex() {
		// System.out.println("Pour la config " + getConfiguration() + " l'index est " + route.getIndex(getConfiguration()));
		return route.getIndex(getConfiguration());
	}

	@Override
	public boolean nextTransitionIsAbstract() {
		return !getRoute().isVisible(getRoute().getTrace().getNextConfiguration(getConfiguration()));
	}

	@Override
	public RelativeConfigurationArtefact getPreviousConfigurationArtefact() {
		return getRelativeConfigurationArtefact(getRoute().getTrace().getPreviousConfiguration(getConfiguration()));
	}

	@Override
	public RelativeConfigurationArtefact getNextConfigurationArtefact() {
		return getRelativeConfigurationArtefact(getRoute().getTrace().getNextConfiguration(getConfiguration()));
	}

	private RelativeConfigurationArtefact getRelativeConfigurationArtefact(OBPTraceConfiguration config) {
		RelativeConfigurationArtefact returned = relativeConfigurationArtefacts.get(config);
		if (returned == null) {
			returned = new RelativeConfigurationArtefactImpl(config);
			relativeConfigurationArtefacts.put(config, returned);
		}
		return returned;
	}

	public class RelativeConfigurationArtefactImpl implements RelativeConfigurationArtefact {

		private final OBPTraceConfiguration config;

		public RelativeConfigurationArtefactImpl(OBPTraceConfiguration config) {
			this.config = config;
		}

		@Override
		public OBPTraceConfiguration getConfiguration() {
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
		return "OBPConfigurationInRoute " + getConfiguration().getIndex();
	}
}
