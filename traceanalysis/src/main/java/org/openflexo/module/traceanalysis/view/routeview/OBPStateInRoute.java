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
import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObjectInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObjectState;
import org.openflexo.technologyadapter.trace.model.OBPTracePropertyState;
import org.openflexo.technologyadapter.trace.model.OBPTraceState;
import org.openflexo.technologyadapter.trace.model.OBPTraceVariable;

public class OBPStateInRoute {
	
	public final static String SUCCESS = "success";
	public final static String REJECT = "reject";
	public final static String CUT = "cut";

	private final OBPConfigurationInRoute configuration;
	private final BehaviourObjectInRoute behaviourObjectInRoute;
	
	public OBPStateInRoute(OBPConfigurationInRoute configuration,  BehaviourObjectInRoute behaviourObjectInRoute) {
		super();
		this.behaviourObjectInRoute = behaviourObjectInRoute;
		this.configuration = configuration;
	}

	public OBPConfigurationInRoute getConfigurationInRoute(){
		return configuration;
	}
	
	public BehaviourObjectInRoute getBehaviourObjectInRoute(){
		return behaviourObjectInRoute;
	}
	
	public boolean isVisible(){
		OBPRoute route = configuration.getRoute();
		if(route.getSelectedMask()!=null){
			return route.getSelectedMask().isRelevant(configuration.getConfiguration(), behaviourObjectInRoute.getBehaviourObject());
		}else{
			return true;
		}
		
	}
	
	public String getValue(){
		if(behaviourObjectInRoute.getBehaviourObject() instanceof OBPTraceBehaviourObjectInstance){
			OBPTraceBehaviourObjectInstance behaviourObject = ((OBPTraceBehaviourObjectInstance)behaviourObjectInRoute.getBehaviourObject());
			return behaviourObject.getOBPTraceBehaviourObjectStateInConfig(getConfigurationInRoute().getConfiguration()).getState().getName();
		}else if(behaviourObjectInRoute.getBehaviourObject() instanceof OBPTraceVariable){
			OBPTraceVariable behaviourObject = ((OBPTraceVariable)behaviourObjectInRoute.getBehaviourObject());
			return behaviourObject.getStringValueForConfiguration(getConfigurationInRoute().getConfiguration());
		}
		return null;
	}
	
	public OBPTraceState getState(){
		for(OBPTraceBehaviourObjectState behaviourObjectState : getConfigurationInRoute().getConfiguration().getOBPTraceBehaviourObjectStates()){
			if(behaviourObjectState.getOBPTraceBehaviourObjectInstance().equals(behaviourObjectInRoute.getBehaviourObject())){
				return behaviourObjectState.getState();
			}
		}
		return null;
	}

	public boolean isSuccess(){
		if(getState() !=null && getState().getOBPTraceBehaviourObjectState() instanceof OBPTracePropertyState 
				&& getState().getName()!=null 
				&& getState().getName().equals(SUCCESS)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isReject(){
		if(getState() !=null && getState().getOBPTraceBehaviourObjectState() instanceof OBPTracePropertyState
				&& getState().getName().equals(REJECT)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isCut(){
		if(getState() !=null && getState().getOBPTraceBehaviourObjectState() instanceof OBPTracePropertyState 
				&& getState().getName().equals(CUT)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isNormal(){
		if(getState() !=null && getState().getOBPTraceBehaviourObjectState() instanceof OBPTracePropertyState){
			return true;
		}else{
			return false;
		}
	}
	
}
