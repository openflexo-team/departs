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
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Remover;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.model.annotations.Getter.Cardinality;

import Parser.ConfigData;

@ModelEntity
@ImplementationClass(OBPTraceConfiguration.OBPTraceConfigurationImpl.class)
@XMLElement(xmlTag = "OBPTraceConfiguration")
public interface OBPTraceConfiguration extends OBPTraceObject{

	public static final String CONFIGURATION_KEY = "configuration";
	
	public static final String TRACE_KEY = "trace";
	
	@PropertyIdentifier(type = List.class)
	public static final String BEHAVIOUR_OBJECT_STATES_KEY = "behaviourObjectStates";
	
	@Getter(value=CONFIGURATION_KEY, ignoreType=true)
	public ConfigData getConfigData();
	@Setter(CONFIGURATION_KEY)
	public void setConfigData(ConfigData configData);
	
	@Getter(value=TRACE_KEY, inverse = OBPTrace.CONFIGURATION_KEY)
	public OBPTrace getOBPTrace();
	@Setter(TRACE_KEY)
	public void setOBPTrace(OBPTrace trace);
	
	@Getter(value = BEHAVIOUR_OBJECT_STATES_KEY, cardinality = Cardinality.LIST, inverse=OBPTraceBehaviourObjectState.CONFIGURATION_KEY)
	public List<OBPTraceBehaviourObjectState> getOBPTraceBehaviourObjectStates();

	@Setter(BEHAVIOUR_OBJECT_STATES_KEY)
	public void setOBPTraceBehaviourObjectStates(List<OBPTraceBehaviourObjectState> behaviourObjectState);

	@Adder(BEHAVIOUR_OBJECT_STATES_KEY)
	public void addToOBPTraceBehaviourObjectStates(OBPTraceBehaviourObjectState behaviourObjectState);

	@Remover(BEHAVIOUR_OBJECT_STATES_KEY)
	public void removeFromOBPTraceBehaviourObjectStates(OBPTraceBehaviourObjectState behaviourObjectState);
	
	public OBPTraceContextState getOBPTraceContextState();
	
	public List<OBPTraceProcessState> getOBPTraceProcessState();
	
	public List<OBPTracePropertyState> getOBPTracePropertyState();
	
	/*@Getter(value=CONTEXT_KEY)
	public OBPTraceContextState getOBPTraceContext();
	
	@Setter(CONTEXT_KEY)
	public void setOBPTraceContext(OBPTraceContextState context);
	
	@Getter(value = PROCESS_KEY, cardinality = Cardinality.LIST)
	public List<OBPTraceProcessState> getOBPTraceProcesses();

	@Setter(PROCESS_KEY)
	public void setOBPTraceProcesses(List<OBPTraceProcessState> processes);

	@Adder(PROCESS_KEY)
	public void addToOBPTraceProcesses(OBPTraceProcessState process);

	@Remover(PROCESS_KEY)
	public void removeFromOBPTraceProcesses(OBPTraceProcessState process);
	
	@Getter(value = PROPERTY_KEY, cardinality = Cardinality.LIST)
	public List<OBPTracePropertyState> getOBPTraceProperties();

	@Setter(PROPERTY_KEY)
	public void setOBPTraceProperties(List<OBPTracePropertyState> properties);

	@Adder(PROPERTY_KEY)
	public void addToOBPTraceProperties(OBPTracePropertyState property);

	@Remover(PROPERTY_KEY)
	public void removeFromOBPTraceProperties(OBPTracePropertyState property);*/
	
	public int getIndex();
	
	public List<OBPTraceBehaviourObjectInstance> getBehaviourObjects();
	
	public static abstract class OBPTraceConfigurationImpl extends OBPTraceObjectImpl implements OBPTraceConfiguration {

		private List<OBPTraceBehaviourObjectInstance> behaviourObjects;
		
		private OBPTraceContextState contextState;
		
		private List<OBPTraceProcessState> processStates;
		
		private List<OBPTracePropertyState> propertyStates;
		
		public OBPTraceConfigurationImpl() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getUri() {
			return getName();
		}
		
		@Override
		public int getIndex(){
			return getConfigData().getNoConfig();
		}
		
		@Override
		public List<OBPTraceProcessState> getOBPTraceProcessState(){
			if(processStates==null){
				return processStates;
			}else{
				processStates = new ArrayList<OBPTraceProcessState>();
			}
			for(OBPTraceBehaviourObjectState object : getOBPTraceBehaviourObjectStates()){
				if(object instanceof OBPTraceProcessState){
					processStates.add((OBPTraceProcessState) object);
				}
			}
			return processStates;
		}
		
		@Override
		public List<OBPTracePropertyState> getOBPTracePropertyState(){
			if(propertyStates==null){
				return propertyStates;
			}else{
				propertyStates = new ArrayList<OBPTracePropertyState>();
			}
			for(OBPTraceBehaviourObjectState object : getOBPTraceBehaviourObjectStates()){
				if(object instanceof OBPTracePropertyState){
					propertyStates.add((OBPTracePropertyState) object);
				}
			}
			return propertyStates;
		}
		
		@Override
		public OBPTraceContextState getOBPTraceContextState(){
			if(contextState!=null){
				return contextState;
			}
			for(OBPTraceBehaviourObjectState object : getOBPTraceBehaviourObjectStates()){
				if(object instanceof OBPTraceContextState){
					return (OBPTraceContextState) object;
				}
			}
			return null;
		}
		
		/*@Override
		public List<OBPTraceBehaviourObjectInstance> getBehaviourObjects(){
			if(behaviourObjects==null){
				behaviourObjects = new ArrayList<OBPTraceBehaviourObjectInstance>();
				addBehaviourObjects(getOBPTraceContext());
				addBehaviourObjects(getOBPTraceProcesses());
				addBehaviourObjects(getOBPTraceProperties());
			}
			return behaviourObjects;
		}
		
		private void addBehaviourObjects(OBPTraceBehaviourObjectInstance behaviourObject){
			for(OBPTraceBehaviourObjectInstance object : behaviourObjects){
				if(behaviourObject.getName().equals(object.getName())){
					return;
				}
			}
			behaviourObjects.add(behaviourObject);
		}
		
		private void addBehaviourObjects(List<? extends OBPTraceBehaviourObjectInstance> behaviourObjects){
			for(OBPTraceBehaviourObjectInstance object : behaviourObjects){
				addBehaviourObjects(object);
			}
		}*/

	}

}