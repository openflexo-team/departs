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

import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;

@ModelEntity
@ImplementationClass(OBPTraceData.OBPTraceDataImpl.class)
@XMLElement(xmlTag = "OBPTraceData")
public interface OBPTraceData extends OBPTraceObject{

	public static final String DATA_KEY = "data";
	
	public static final String VARIABLE_KEY = "variable";
	
	public static final String BEHAVIOUR_OBECT_STATE_KEY = "behaviourObjectState";
	
	
	@Getter(value=DATA_KEY, ignoreType=true)
	public Parser.Donnee getDonnee();
	
	@Setter(DATA_KEY)
	public void setDonnee(Parser.Donnee donnee);
	
	@Getter(value = VARIABLE_KEY, inverse = OBPTraceVariable.VALUES_KEY)
	public OBPTraceVariable getVariable();
	
	@Setter(VARIABLE_KEY)
	public void setVariable(OBPTraceVariable variable);
	
	@Getter(value = BEHAVIOUR_OBECT_STATE_KEY, inverse = OBPTraceBehaviourObjectState.DATA_KEY)
	public OBPTraceBehaviourObjectState getBehaviourObjectState();
	
	@Setter(BEHAVIOUR_OBECT_STATE_KEY)
	public void setBehaviourObjectState(OBPTraceBehaviourObjectState behaviourObjectState);
	
	public Integer getIntegerValue();
	
	public static abstract class OBPTraceDataImpl extends OBPTraceObjectImpl implements OBPTraceData {

		public OBPTraceDataImpl() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getUri() {
			return getName();
		}

		@Override
		public String getName() {
			return getDonnee().varName;
		}
		
		@Override
		public String getValue() {
			return getDonnee().varValue.toString();
		}
		
		@Override
		public Integer getIntegerValue() {
			try {
				
				return Integer.parseInt(getValue());
			} catch (Exception e) {
				return 0;
			}
		}
		
		
	}

}