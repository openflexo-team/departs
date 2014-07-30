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
	
	@PropertyIdentifier(type = List.class)
	public static final String PROCESS_KEY = "process";
	
	@PropertyIdentifier(type = List.class)
	public static final String PROPERTY_KEY = "properties";
	
	public static final String CONTEXT_KEY = "context";
	
	@Getter(value=CONFIGURATION_KEY, ignoreType=true)
	public ConfigData getConfigData();
	@Setter(CONFIGURATION_KEY)
	public void setConfigData(ConfigData configData);
	
	@Getter(value=CONTEXT_KEY)
	public OBPTraceContext getOBPTraceContext();
	
	@Setter(CONTEXT_KEY)
	public void setOBPTraceContext(OBPTraceContext context);
	
	@Getter(value = PROCESS_KEY, cardinality = Cardinality.LIST)
	public List<OBPTraceProcess> getOBPTraceProcesses();

	@Setter(PROCESS_KEY)
	public void setOBPTraceProcesses(List<OBPTraceProcess> processes);

	@Adder(PROCESS_KEY)
	public void addToOBPTraceProcesses(OBPTraceProcess process);

	@Remover(PROCESS_KEY)
	public void removeFromOBPTraceProcesses(OBPTraceProcess process);
	
	@Getter(value = PROPERTY_KEY, cardinality = Cardinality.LIST)
	public List<OBPTraceProperty> getOBPTraceProperties();

	@Setter(PROPERTY_KEY)
	public void setOBPTraceProperties(List<OBPTraceProperty> properties);

	@Adder(PROPERTY_KEY)
	public void addToOBPTraceProperties(OBPTraceProperty property);

	@Remover(PROPERTY_KEY)
	public void removeFromOBPTraceProperties(OBPTraceProperty property);
	
	public int getIndex();
	
	public List<OBPTraceBehaviourObject> getBehaviourObjects();
	
	public static abstract class OBPTraceConfigurationImpl extends OBPTraceObjectImpl implements OBPTraceConfiguration {

		private List<OBPTraceBehaviourObject> behaviourObjects;
		
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
		public List<OBPTraceBehaviourObject> getBehaviourObjects(){
			if(behaviourObjects==null){
				behaviourObjects = new ArrayList<OBPTraceBehaviourObject>();
				addBehaviourObjects(getOBPTraceContext());
				addBehaviourObjects(getOBPTraceProcesses());
				addBehaviourObjects(getOBPTraceProperties());
			}
			return behaviourObjects;
		}
		
		private void addBehaviourObjects(OBPTraceBehaviourObject behaviourObject){
			for(OBPTraceBehaviourObject object : behaviourObjects){
				if(behaviourObject.getName().equals(object.getName())){
					return;
				}
			}
			behaviourObjects.add(behaviourObject);
		}
		
		private void addBehaviourObjects(List<? extends OBPTraceBehaviourObject> behaviourObjects){
			for(OBPTraceBehaviourObject object : behaviourObjects){
				addBehaviourObjects(object);
			}
		}

	}

}