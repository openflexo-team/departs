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

package org.openflexo.traceanalysis.view.routeview;
import java.awt.Color;

import org.openflexo.fge.BackgroundStyle;
import org.openflexo.fge.FGEModelFactory;
import org.openflexo.fge.FGEModelFactoryImpl;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObject;
import org.openflexo.technologyadapter.trace.model.OBPTraceProperty;
import org.openflexo.technologyadapter.trace.model.OBPTraceState;

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
	
	public OBPTraceState getState(){
		for(OBPTraceBehaviourObject object : getConfigurationInRoute().getConfiguration().getBehaviourObjects()){
			if(getBehaviourObjectInRoute().getBehaviourObject().getName().equals(object.getName())){
				return object.getState();
			}
		}
		return null;
	}

	public boolean isSuccess(){
		if(getState().getOBPTraceBehaviourObject() instanceof OBPTraceProperty && getState().getName().equals(SUCCESS)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isReject(){
		if(getState().getOBPTraceBehaviourObject() instanceof OBPTraceProperty && getState().getName().equals(REJECT)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isCut(){
		if(getState().getOBPTraceBehaviourObject() instanceof OBPTraceProperty && getState().getName().equals(CUT)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isNormal(){
		if(getState().getOBPTraceBehaviourObject() instanceof OBPTraceProperty){
			return true;
		}else{
			return false;
		}
	}
	
}
