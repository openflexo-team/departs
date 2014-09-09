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
@ImplementationClass(OBPTraceVariable.OBPTraceVariableImpl.class)
@XMLElement(xmlTag = "OBPTraceVariable")
public interface OBPTraceVariable extends OBPTraceObject{
	
	@PropertyIdentifier(type = OBPTraceData.class)
	public static final String VALUES_KEY = "values";
	
	@Getter(value = VALUES_KEY, cardinality = Cardinality.LIST, inverse=OBPTraceData.VARIABLE_KEY)
	public List<OBPTraceData> getValues();

	@Setter(VALUES_KEY)
	public void setValues(List<OBPTraceData> values);

	@Adder(VALUES_KEY)
	public void addToValues(OBPTraceData value);

	@Remover(VALUES_KEY)
	public void removeFromValues(OBPTraceData value);
	
	public OBPTraceData getOBPTraceDataForConfiguration(OBPTraceConfiguration configuration);
	
	public String getStringValueForConfiguration(OBPTraceConfiguration configuration);
	
	public static abstract class OBPTraceVariableImpl extends OBPTraceObjectImpl implements OBPTraceVariable {

		public OBPTraceVariableImpl() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getUri() {
			return getName();
		}
		
		public OBPTraceData getOBPTraceDataForConfiguration(OBPTraceConfiguration configuration){
			for(OBPTraceData data :getValues()){
				if(data.getBehaviourObjectState().getOBPTraceConfiguration().equals(configuration)){
					return data;
				}
			}
			return null;
		}
		
		@Override
		public String getStringValueForConfiguration(OBPTraceConfiguration configuration){
			return getName() + " =" + getOBPTraceDataForConfiguration(configuration).getValue();
		}
	}

}