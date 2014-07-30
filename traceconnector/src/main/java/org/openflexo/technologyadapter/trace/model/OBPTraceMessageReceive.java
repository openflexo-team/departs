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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;
import Parser.TransitionSequenceReceiveAsynchro;

@ModelEntity
@ImplementationClass(OBPTraceMessageReceive.OBPTraceMessageReceiveImpl.class)
@XMLElement(xmlTag = "OBPTraceMessageReceive")
public interface OBPTraceMessageReceive extends OBPTraceMessage{
	
	public static final String SEND_KEY = "send";
	
	@Getter(value = SEND_KEY, inverse=OBPTraceMessageSend.RECEIVE_KEY)
	public OBPTraceMessageSend getOBPTraceMessageSend();
	@Setter(value = SEND_KEY)
	public void setOBPTraceMessageSend(OBPTraceMessageSend obpTraceMessageSend);
	
	public static abstract class OBPTraceMessageReceiveImpl extends OBPTraceMessageImpl implements OBPTraceMessageReceive {

		public OBPTraceMessageReceiveImpl() {
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
			Matcher makeMatch = id.matcher(((TransitionSequenceReceiveAsynchro)getTransitionSequence()).getNomProcTarget());
			makeMatch.find();
			return "{" + makeMatch.group(1) + "}" + makeMatch.group(2);
		}

	}

}