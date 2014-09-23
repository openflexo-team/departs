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

import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;

import Parser.TransitionSequenceSendAsynchro;

@ModelEntity
@ImplementationClass(OBPTraceMessageSend.OBPTraceMessageSendImpl.class)
@XMLElement(xmlTag = "OBPTraceMessageSend")
public interface OBPTraceMessageSend extends OBPTraceMessage{
	
	public static final String RECEIVE_KEY = "receive";
	
	@Getter(value = RECEIVE_KEY, inverse=OBPTraceMessageReceive.SEND_KEY)
	public OBPTraceMessageReceive getOBPTraceMessageReceive();
	@Setter(value = RECEIVE_KEY)
	public void setOBPTraceMessageReceive(OBPTraceMessageReceive obpTraceMessageReceive);
	
	public static abstract class OBPTraceMessageSendImpl extends OBPTraceMessageImpl implements OBPTraceMessageSend {

		public OBPTraceMessageSendImpl() {
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
		public String getSourceProcName(){
			Pattern id = Pattern.compile(PROC_REGEX);
			Matcher makeMatch = id.matcher(((TransitionSequenceSendAsynchro)getTransitionSequence()).getNomProcSource());
			makeMatch.find();
			return "{" + makeMatch.group(1) + "}" + makeMatch.group(2);
		}
		
		@Override
		public String getDataValue(){
			return ((TransitionSequenceSendAsynchro)getTransitionSequence()).getNomData();
		}
		
		@Override
		public OBPTraceVariable getDataType(){
			return getFromBehaviourObject().getVariableNamed(getDataValue());
		}

	}

}