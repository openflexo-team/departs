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

import java.util.ArrayList;
import java.util.List;

import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObjectInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceVariable;
import org.openflexo.toolbox.HasPropertyChangeSupport;

/**
 * A Behaviour object is an OBP traced object that owns an FSM(context, observers, processes and components)
 * @author Vincent
 *
 */
public class ProcessInRoute extends BehaviourObjectInRoute<OBPTraceBehaviourObjectInstance> implements HasPropertyChangeSupport {

	private List<Chronogram> chronograms;
	
	public ProcessInRoute(OBPTraceBehaviourObjectInstance behaviourObject,
			OBPRoute route) {
		super(behaviourObject, route);
		
		/*for(OBPTraceVariable variable: behaviourObject.getVariables()){
			chronograms.add(new Chronogram(this, variable));
		}*/
	}

	public List<Chronogram> getChronograms() {
		if(chronograms==null){
			chronograms = new ArrayList<Chronogram>();
			for(OBPTraceVariable variable : getBehaviourObject().getVariables()){
				VariableInRoute varInRoute = (VariableInRoute) getRoute().getBehaviourObjectInRoute(variable);
				chronograms.add(varInRoute.getChronogram());
			}
		}
		
		return chronograms;
	}

	public int getChronogramIndex(Chronogram chronogram){
		return getChronograms().indexOf(chronogram);
	}
}
