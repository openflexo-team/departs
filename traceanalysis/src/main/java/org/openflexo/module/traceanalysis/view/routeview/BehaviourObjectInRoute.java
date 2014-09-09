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

import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObjectInstance;
import org.openflexo.toolbox.HasPropertyChangeSupport;

/**
 * A Behaviour object is an OBP traced object that owns an FSM(context, observers, processes and components)
 * @author Vincent
 *
 */
public class BehaviourObjectInRoute implements HasPropertyChangeSupport {

	private final OBPRoute route;
	private final OBPTraceBehaviourObjectInstance behaviourObject;
	private final PropertyChangeSupport pcSupport;

	public BehaviourObjectInRoute(OBPTraceBehaviourObjectInstance behaviourObject, OBPRoute route) {
		this.route = route;
		this.behaviourObject = behaviourObject;
		pcSupport = new PropertyChangeSupport(this);
	}

	@Override
	public PropertyChangeSupport getPropertyChangeSupport() {
		return pcSupport;
	}
	
	@Override
	public String getDeletedProperty() {
		return null;
	}
	
	public OBPRoute getRoute() {
		return route;
	}

	public OBPTraceBehaviourObjectInstance getBehaviourObject() {
		return behaviourObject;
	}

	public int getIndex() {
		return route.getIndex(this);
		//return route.getTrace().getOBPTraceBehaviourObjectInstances().indexOf(behaviourObject);
	}
}
