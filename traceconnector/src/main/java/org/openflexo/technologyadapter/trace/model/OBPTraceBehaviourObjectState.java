/*
 * (c) Copyright 2013 Openflexo
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

package org.openflexo.technologyadapter.trace.model;

import java.util.List;

import org.openflexo.model.annotations.Adder;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Remover;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.model.annotations.Getter.Cardinality;

@ModelEntity
@ImplementationClass(OBPTraceBehaviourObjectState.OBPTraceBehaviourObjectStateImpl.class)
@XMLElement(xmlTag = "OBPTraceBehaviourObjectState")
public interface OBPTraceBehaviourObjectState extends OBPTraceObject {
	
	@PropertyIdentifier(type = OBPTraceState.class)
	public static final String STATE_KEY = "state";
	
	@PropertyIdentifier(type = OBPTraceConfiguration.class)
	public static final String CONFIGURATION_KEY = "configuration";
	
	@PropertyIdentifier(type = List.class)
	public static final String DATA_KEY = "data";
	
	@PropertyIdentifier(type = OBPTraceBehaviourObjectInstance.class)
	public static final String BEHAVIOUR_OBJECT_INSTANCE = "behaviourObjectInstance";
	
	@Getter(value = BEHAVIOUR_OBJECT_INSTANCE, inverse = OBPTraceBehaviourObjectInstance.BEHAVIOUR_OBJECT_STATES_KEY)
	public OBPTraceBehaviourObjectInstance getOBPTraceBehaviourObjectInstance();
	@Setter(BEHAVIOUR_OBJECT_INSTANCE)
	public void setOBPTraceBehaviourObjectInstance(OBPTraceBehaviourObjectInstance behaviourObjectInstance);
	
	@Getter(value = STATE_KEY, inverse = OBPTraceState.BEHAVIOUR_OBJECT_STATE_KEY)
	public OBPTraceState getState();
	@Setter(STATE_KEY)
	public void setState(OBPTraceState state);
	
	@Getter(value = CONFIGURATION_KEY, inverse = OBPTraceConfiguration.BEHAVIOUR_OBJECT_STATES_KEY)
	public OBPTraceConfiguration getOBPTraceConfiguration();
	@Setter(CONFIGURATION_KEY)
	public void setOBPTraceConfiguration(OBPTraceConfiguration configuration);
	
	@Getter(value = DATA_KEY, cardinality = Cardinality.LIST , inverse=OBPTraceData.BEHAVIOUR_OBECT_STATE_KEY)
	public List<OBPTraceData> getOBPTraceData();

	@Setter(DATA_KEY)
	public void setOBPTraceData(List<OBPTraceData> data);

	@Adder(DATA_KEY)
	public void addToOBPTraceData(OBPTraceData data);

	@Remover(DATA_KEY)
	public void removeFromOBPTraceData(OBPTraceData data);

	public OBPTraceData getOBPTraceDataFromType(String type);
	
	public static abstract class OBPTraceBehaviourObjectStateImpl extends OBPTraceObjectImpl implements OBPTraceBehaviourObjectState {

		public OBPTraceBehaviourObjectStateImpl() {
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public OBPTraceData getOBPTraceDataFromType(String type){
			for(OBPTraceData data : getOBPTraceData()){
				if(data.getName().equals(type)){
					return data;
				}
			}
			return null;
		}

	}

}