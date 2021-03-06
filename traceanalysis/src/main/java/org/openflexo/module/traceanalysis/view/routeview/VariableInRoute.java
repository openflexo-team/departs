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

import org.openflexo.technologyadapter.trace.model.OBPTraceVariable;
import org.openflexo.toolbox.HasPropertyChangeSupport;

public class VariableInRoute extends BehaviourObjectInRoute<OBPTraceVariable> implements HasPropertyChangeSupport {

	private final Chronogram chronogram;
	
	private final ProcessInRoute process;
	
	public VariableInRoute(OBPTraceVariable behaviourObject, OBPRoute route, ProcessInRoute process) {
		super(behaviourObject, route);
		this.process = process;
		this.chronogram = new Chronogram(this);
	}

	public Chronogram getChronogram(){
		return chronogram;
	}

	public ProcessInRoute getProcessInRoute() {
		return process;
	}
	
}
