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

import org.openflexo.foundation.resource.ResourceData;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;

import Parser.ConfigData;

@ModelEntity
@ImplementationClass(FlexoProcess.FlexoProcessImpl.class)
@XMLElement(xmlTag = "FlexoProcess")
public interface FlexoProcess extends FlexoTraceObject, ResourceData<FlexoProcess> {

	public static final String PROCESS_KEY = "process";
	
	@PropertyIdentifier(type = String.class)
	public static final String PROCESS_STATE_KEY = "state";
	
	@Getter(value=PROCESS_KEY, ignoreType=true)
	public Parser.Process getProcess();
	@Setter(PROCESS_KEY)
	public void setProcess(Parser.Process process);
	
	@Getter(value = PROCESS_STATE_KEY)
	public String getState();
	@Setter(value = PROCESS_STATE_KEY)
	public void setState(String state);
	
	public String getProcessID();
	
	public String getProcessType();

	public static abstract class FlexoProcessImpl extends FlexoTraceObjectImpl implements FlexoProcess {

		public FlexoProcessImpl() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getUri() {
			return getName();
		}
		
		@Override
		public String getProcessID(){
			Pattern id = Pattern.compile("\\d+\\b");
			Matcher makeMatch = id.matcher(getName());
			makeMatch.find();
			return makeMatch.group();
		}
		
		@Override
		public String getProcessType(){
			Pattern id = Pattern.compile("\\D+");
			Matcher makeMatch = id.matcher(getName());
			makeMatch.find();
			return makeMatch.group();
		}

	}

}