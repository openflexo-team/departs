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

import Parser.TransitionSequence;

@ModelEntity
@ImplementationClass(OBPTraceMessage.OBPTraceMessageImpl.class)
@XMLElement(xmlTag = "OBPTraceMessage")
public interface OBPTraceMessage extends OBPTraceObject{
	
	public static final String TRANSITION_SEQUENCE_KEY = "transitionSequence";
	public static final String TRANSITION_KEY = "transition";
	public static final String FROM_BEHAVIOUR_OBJECT_KEY = "fromBehaviourObject";
	public static final String TO_BEHAVIOUR_OBJECT_KEY = "toBehaviourObject";
	
	@Getter(value = TRANSITION_SEQUENCE_KEY, ignoreType=true)
	public TransitionSequence getTransitionSequence();
	@Setter(value = TRANSITION_SEQUENCE_KEY)
	public void setTransitionSequence(TransitionSequence transitionSequence);
	
	@Getter(value = TRANSITION_KEY, inverse = OBPTraceTransition.MESSAGES_KEY)
	public OBPTraceTransition getOBPTraceTransition();
	@Setter(TRANSITION_KEY)
	public void setOBPTraceTransition(OBPTraceTransition transition);
	
	@Getter(value = FROM_BEHAVIOUR_OBJECT_KEY)
	public OBPTraceBehaviourObjectInstance getFromBehaviourObject();
	@Setter(FROM_BEHAVIOUR_OBJECT_KEY)
	public void setFromBehaviourObject(OBPTraceBehaviourObjectInstance behaviourObject);
	
	@Getter(value = TO_BEHAVIOUR_OBJECT_KEY)
	public OBPTraceBehaviourObjectInstance getToBehaviourObject();
	@Setter(TO_BEHAVIOUR_OBJECT_KEY)
	public void setToBehaviourObject(OBPTraceBehaviourObjectInstance behaviourObject);

	public String getInformationLabel();
	
	public final String PROC_REGEX = "(\\w*)\\}{1}(\\d)";
	
	public String getSourceProcName();
	
	public String getTargetProcName();
	
	public String getDataValue();
	
	public OBPTraceVariable getDataType();

	public static abstract class OBPTraceMessageImpl extends OBPTraceObjectImpl implements OBPTraceMessage {

		public OBPTraceMessageImpl() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getUri() {
			return getName();
		}
		
		@Override
		public String getValue() {
			return getName();
		}

		@Override
		public String getInformationLabel() {
			if(getTransitionSequence().getInformalLabel()!=null){
				return getTransitionSequence().getInformalLabel();
			}else{
				return getValue();
			}
		}
		
		@Override
		public String getDataValue(){
			return null;
		}
	}

}