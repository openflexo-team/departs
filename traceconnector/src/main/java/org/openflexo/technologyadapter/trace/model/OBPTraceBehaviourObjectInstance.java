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

import java.util.ArrayList;
import java.util.List;

import org.openflexo.model.annotations.Adder;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.Getter.Cardinality;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Remover;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;

@ModelEntity
@ImplementationClass(OBPTraceBehaviourObjectInstance.OBPTraceBehaviourObjectInstanceImpl.class)
@XMLElement(xmlTag = "OBPTraceBehaviourObjectInstance")
public interface OBPTraceBehaviourObjectInstance extends OBPTraceObject {
	
	@PropertyIdentifier(type = OBPTraceBehaviourObjectState.class)
	public static final String BEHAVIOUR_OBJECT_STATES_KEY = "behaviourObjectStates";
	
	@PropertyIdentifier(type = OBPTraceVariable.class)
	public static final String VARIABLES_KEY = "variables";
	
	@Getter(value = BEHAVIOUR_OBJECT_STATES_KEY, cardinality = Cardinality.LIST, inverse=OBPTraceBehaviourObjectState.BEHAVIOUR_OBJECT_INSTANCE)
	public List<OBPTraceBehaviourObjectState> getOBPTraceBehaviourObjectStates();

	@Setter(BEHAVIOUR_OBJECT_STATES_KEY)
	public void setOBPTraceBehaviourObjectStates(List<OBPTraceBehaviourObjectState> behaviourObjectStates);

	@Adder(BEHAVIOUR_OBJECT_STATES_KEY)
	public void addToOBPTraceBehaviourObjectStates(OBPTraceBehaviourObjectState behaviourObjectStates);

	@Remover(BEHAVIOUR_OBJECT_STATES_KEY)
	public void removeFromOBPTraceBehaviourObjectStates(OBPTraceBehaviourObjectState behaviourObjectStates);
	
	@Getter(value = VARIABLES_KEY, cardinality = Cardinality.LIST)
	public List<OBPTraceVariable> getVariables();

	@Setter(VARIABLES_KEY)
	public void setValues(List<OBPTraceVariable> variables);

	@Adder(VARIABLES_KEY)
	public void addToVariables(OBPTraceVariable variable);

	@Remover(VARIABLES_KEY)
	public void removeFromVariables(OBPTraceVariable variable);
	
	public String getID();
	
	public String getType();
	
	public OBPTraceBehaviourObjectState getOBPTraceBehaviourObjectStateInConfig(OBPTraceConfiguration config);
	
	public OBPTraceVariable getVariableNamed(String name);
	
	/*public List<String> getOBPTraceDataTypes();
	
	public List<OBPTraceData> getOBPTraceData();
	
	public List<OBPTraceData> getOBPTraceDataFromType(String type);
	
	public List<String> getOBPTraceDataValuesFromType(String type);
	
	public OBPTraceData getDataValue(OBPTraceConfiguration config, String type);
	
	*/
	
	public static abstract class OBPTraceBehaviourObjectInstanceImpl extends OBPTraceObjectImpl implements OBPTraceBehaviourObjectInstance {

		/*private List<String> traceDataTypes;
		
		private List<OBPTraceData> traceData;
		
		private List<OBPTraceData> traceDataFromType;
		
		private List<String> traceDataValueFromType;*/
		
		public OBPTraceBehaviourObjectInstanceImpl() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getUri() {
			return getName();
		}
		
		@Override
		public OBPTraceVariable getVariableNamed(String name){
			for(OBPTraceVariable variable :getVariables()){
				if(variable.getName().equals(name)){
					return variable;
				}
			}
			return null;
		}
		
		/*@Override
		public List<String> getOBPTraceDataTypes(){
			if(traceDataTypes==null){
				traceDataTypes = new ArrayList<String>();
			}
			for(OBPTraceData data : getOBPTraceData()){
				if(!traceDataTypes.contains(data.getName())){
					traceDataTypes.add(data.getName());
				}
			}
			return traceDataTypes;
		}
		
		@Override
		public List<OBPTraceData> getOBPTraceData(){
			if(traceData==null){
				traceData = new ArrayList<OBPTraceData>();
			}
			for(OBPTraceBehaviourObjectState state : getOBPTraceBehaviourObjectStates()){
				for(OBPTraceData data : state.getOBPTraceData()){
					traceData.add(data);
				}
			}
			return traceData;
		}
		
		@Override
		public List<OBPTraceData> getOBPTraceDataFromType(String type){
			if(traceDataFromType==null){
				traceDataFromType = new ArrayList<OBPTraceData>();
			}
			for(OBPTraceData data : getOBPTraceData()){
				if(data.getName().equals(type)){
					traceDataFromType.add(data);
				}
			}
			return traceDataFromType;
		}
		
		@Override
		public List<String> getOBPTraceDataValuesFromType(String type){
			if(traceDataValueFromType==null){
				traceDataValueFromType = new ArrayList<String>();
			}
			for(OBPTraceData data : getOBPTraceDataFromType(type)){
				traceDataValueFromType.add(data.getValue());
			}
			return traceDataValueFromType;
		}*/
		
		
		@Override
		public OBPTraceBehaviourObjectState getOBPTraceBehaviourObjectStateInConfig(OBPTraceConfiguration config){
			for(OBPTraceBehaviourObjectState state : getOBPTraceBehaviourObjectStates()){
				if(state.getOBPTraceConfiguration().equals(config)){
					return state;
				}
			}
			return null;
		}
		
		/*@Override
		public OBPTraceData getDataValue(OBPTraceConfiguration config, String type){
			return getOBPTraceBehaviourObjectStateInConfig(config).getOBPTraceDataFromType(type);
		}*/
		
	}

}