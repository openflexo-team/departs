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
 * You should have a copy of the GNU General Public License
 * along with OpenFlexo. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.openflexo.technologyadapter.trace.model;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;

import Parser.TransitionSequenceReceiveAsynchro;
import Parser.TransitionSequenceSynchro;

@ModelEntity
@ImplementationClass(OBPTraceMessageSynchro.OBPTraceMessageSynchroImpl.class)
@XMLElement(xmlTag = "OBPTraceMessageSynchro")
public interface OBPTraceMessageSynchro extends OBPTraceMessage{
	
	public static abstract class OBPTraceMessageSynchroImpl extends OBPTraceMessageImpl implements OBPTraceMessageSynchro {

		public OBPTraceMessageSynchroImpl() {
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
		public String getTargetProcName(){
			Pattern id = Pattern.compile(PROC_REGEX);
			Matcher makeMatch = id.matcher(((TransitionSequenceSynchro)getTransitionSequence()).getNomProcTarget());
			makeMatch.find();
			return "{" + makeMatch.group(1) + "}" + makeMatch.group(2);
		}
		
		@Override
		public String getSourceProcName(){
			Pattern id = Pattern.compile(PROC_REGEX);
			Matcher makeMatch = id.matcher(((TransitionSequenceSynchro)getTransitionSequence()).getNomProcSource());
			makeMatch.find();
			return "{" + makeMatch.group(1) + "}" + makeMatch.group(2);
		}
		
		@Override
		public String getDataValue(){
			return ((TransitionSequenceSynchro)getTransitionSequence()).port_data;
		}
		
		@Override
		public OBPTraceVariable getDataType(){
			return getToBehaviourObject().getVariableNamed(getDataValue());
		}
	}

}