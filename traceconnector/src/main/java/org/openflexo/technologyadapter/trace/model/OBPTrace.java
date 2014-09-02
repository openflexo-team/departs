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

import org.openflexo.foundation.resource.ResourceData;
import org.openflexo.model.annotations.Adder;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.Getter.Cardinality;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Remover;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.trace.model.io.TraceModelConverter;

import Parser.TraceOBP;

@ModelEntity
@ImplementationClass(OBPTrace.OBPTraceImpl.class)
@XMLElement(xmlTag = "OBPTrace")
public interface OBPTrace extends OBPTraceObject, ResourceData<OBPTrace> {
	
	@PropertyIdentifier(type = List.class)
	public static final String CONFIGURATION_KEY = "configuration";
	
	@PropertyIdentifier(type = List.class)
	public static final String TRANSITIONS_KEY = "transitions";
	
	@PropertyIdentifier(type = OBPTraceBehaviourObjectInstance.class)
	public static final String BEHAVIOUR_OBJECTS_KEY = "behaviourObjects";

	@Getter(value = CONFIGURATION_KEY, cardinality = Cardinality.LIST)
	public List<OBPTraceConfiguration> getConfigurations();

	@Setter(CONFIGURATION_KEY)
	public void setConfigurations(List<OBPTraceConfiguration> configurations);

	@Adder(CONFIGURATION_KEY)
	public void addToConfigurations(OBPTraceConfiguration configuration);

	@Remover(CONFIGURATION_KEY)
	public void removeFromConfigurations(OBPTraceConfiguration configuration);

	@Getter(value = TRANSITIONS_KEY, cardinality = Cardinality.LIST)
	public List<OBPTraceTransition> getTransitions();

	@Setter(TRANSITIONS_KEY)
	public void setTransitions(List<OBPTraceTransition> transitions);

	@Adder(TRANSITIONS_KEY)
	public void addToTransitions(OBPTraceTransition transition);

	@Remover(TRANSITIONS_KEY)
	public void removeFromTransitions(OBPTraceTransition transition);
	
	@Getter(value = BEHAVIOUR_OBJECTS_KEY, cardinality = Cardinality.LIST)
	public List<OBPTraceBehaviourObjectInstance> getOBPTraceBehaviourObjectInstances();

	@Setter(BEHAVIOUR_OBJECTS_KEY)
	public void setOBPTraceBehaviourObjectInstances(List<OBPTraceBehaviourObjectInstance> behaviourObjectInstances);

	@Adder(BEHAVIOUR_OBJECTS_KEY)
	public void addToOBPTraceBehaviourObjectInstances(OBPTraceBehaviourObjectInstance behaviourObjectInstance);

	@Remover(BEHAVIOUR_OBJECTS_KEY)
	public void removeFromOBPTraceBehaviourObjectInstances(OBPTraceBehaviourObjectInstance behaviourObjectInstance);
	
	public TraceOBP getOBPTrace();

	public void setOBPTrace(TraceOBP obpTrace);
	
	public OBPTraceBehaviourObjectInstance getBehaviourObjectInstanceFromValue(String name);
	
	public List<OBPTraceBehaviourObjectInstance> getBehaviourObjectInstancesFromType(String type);
	
	public OBPTraceConfiguration getInitConfiguration();

	public OBPTraceConfiguration getLastConfiguration();

	public OBPTraceConfiguration getNextConfiguration(OBPTraceConfiguration config);

	public OBPTraceConfiguration getPreviousConfiguration(OBPTraceConfiguration config);

	public OBPTraceTransition getTransition(OBPTraceConfiguration from, OBPTraceConfiguration to);

	public int getIndex(OBPTraceConfiguration config);

	public int getSize();

	public OBPTraceConfiguration getConfiguration(int index);
	
	
	//public getDataForBehaviourObjectInstance()
	
	//public getDataForBehaviourObjectInstance(String dataName)

	public TraceModelConverter getConverter();

	public void setConverter(TraceModelConverter converter);

	public static abstract class OBPTraceImpl extends OBPTraceObjectImpl implements OBPTrace {

		//private List<OBPTraceBehaviourObjectInstance> behaviourObjectInstances;
		
		public OBPTraceImpl() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getUri() {
			return getName();
		}

		@Override
		public OBPTraceBehaviourObjectInstance getBehaviourObjectInstanceFromValue(String value){
			for(OBPTraceBehaviourObjectInstance behaviourObject : getOBPTraceBehaviourObjectInstances()){
				if(behaviourObject.getValue().equals(value)){
					return behaviourObject;
				}
			}
			return null;
		}
		
		@Override
		public List<OBPTraceBehaviourObjectInstance> getBehaviourObjectInstancesFromType(String type){
			ArrayList<OBPTraceBehaviourObjectInstance> instances = new ArrayList<OBPTraceBehaviourObjectInstance>();
			for(OBPTraceBehaviourObjectInstance behaviourObject : getOBPTraceBehaviourObjectInstances()){
				if(behaviourObject.getType().equals(type)){
					instances.add(behaviourObject);
				}
			}
			return instances;
		}
		
		@Override
		public OBPTraceConfiguration getInitConfiguration() {
			if (getSize() > 0) {
				return getConfigurations().get(0);
			}
			return null;
		}

		@Override
		public OBPTraceConfiguration getLastConfiguration() {
			if (getSize() > 0) {
				return getConfigurations().get(getSize() - 1);
			}
			return null;
		}

		@Override
		public int getIndex(OBPTraceConfiguration config) {
			return getConfigurations().indexOf(config);
		}

		@Override
		public OBPTraceConfiguration getPreviousConfiguration(OBPTraceConfiguration config) {
			int i = getIndex(config);
			if (i > 0) {
				return getConfigurations().get(i - 1);
			}
			return null;
		}

		@Override
		public OBPTraceConfiguration getNextConfiguration(OBPTraceConfiguration config) {
			int i = getIndex(config);
			if (i > -1 && i + 1 < getConfigurations().size()) {
				return getConfigurations().get(i + 1);
			}
			return null;
		}

		@Override
		public int getSize() {
			return getConfigurations().size();
		}

		@Override
		public OBPTraceConfiguration getConfiguration(int index) {
			for(OBPTraceConfiguration configuration : getConfigurations()){
				if(configuration.getIndex() == index){
					return configuration;
				}
			}
			return null;
		}

		@Override
		public OBPTraceTransition getTransition(OBPTraceConfiguration from, OBPTraceConfiguration to) {
			for(OBPTraceTransition transition : getTransitions()){
				if(transition.getSourceOBPTraceConfiguration().equals(from)&&
						transition.getTargetOBPTraceConfiguration().equals(to)){
					return transition;
				}
			}
			return null;
		}

		
		/*@Override
		public List<OBPTraceBehaviourObjectInstance> getBehaviourObjectInstances(){
			if(behaviourObjectInstances==null){
				behaviourObjectInstances = new ArrayList<OBPTraceBehaviourObjectInstance>();
				for(OBPTraceConfiguration configuration :getConfigurations()){
					addBehaviourObject(configuration.getOBPTraceContext());
					addBehaviourObjects(configuration.getOBPTraceProcesses());
					addBehaviourObjects(configuration.getOBPTraceProperties());
				}
			}
			return behaviourObjectInstances;
		}
		
		private void addBehaviourObject(OBPTraceBehaviourObjectInstance behaviourObject){
			for(OBPTraceBehaviourObjectInstance object : behaviourObjectInstances){
				if(behaviourObject.getName().equals(object.getName())){
					return;
				}
			}
			behaviourObjectInstances.add(behaviourObject);
		}
		
		private void addBehaviourObjects(List<? extends OBPTraceBehaviourObjectInstance> behaviourObjects){
			for(OBPTraceBehaviourObjectInstance object : behaviourObjects){
				addBehaviourObject(object);
			}
		}*/
		
	}

}