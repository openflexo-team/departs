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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openflexo.foundation.resource.ResourceData;
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
@ImplementationClass(FlexoTraceOBPProcess.FlexoTraceOBPProcessImpl.class)
@XMLElement(xmlTag = "FlexoTraceOBPProcess")
public interface FlexoTraceOBPProcess extends FlexoTraceOBPObject{

	public static final String PROCESS_KEY = "process";
	
	@PropertyIdentifier(type = List.class)
	public static final String DATA_KEY = "data";
	
	@PropertyIdentifier(type = FlexoTraceOBPState.class)
	public static final String PROCESS_STATE_KEY = "state";
	
	@Getter(value=PROCESS_KEY, ignoreType=true)
	public Parser.Process getProcess();
	@Setter(PROCESS_KEY)
	public void setProcess(Parser.Process process);
	
	@Getter(value = PROCESS_STATE_KEY, inverse = FlexoTraceOBPState.PROCESS_KEY)
	public FlexoTraceOBPState getState();
	@Setter(PROCESS_STATE_KEY)
	public void setState(FlexoTraceOBPState state);
	
	@Getter(value = DATA_KEY, cardinality = Cardinality.LIST)
	public List<FlexoTraceOBPData> getFlexoData();

	@Setter(DATA_KEY)
	public void setFlexoData(List<FlexoTraceOBPData> flexoData);

	@Adder(DATA_KEY)
	public void addToFlexoData(FlexoTraceOBPData flexoData);

	@Remover(DATA_KEY)
	public void removeFromFlexoData(FlexoTraceOBPData flexoData);
	
	public String getProcessID();
	
	public String getProcessType();

	public static abstract class FlexoTraceOBPProcessImpl extends FlexoTraceOBPObjectImpl implements FlexoTraceOBPProcess {

		public FlexoTraceOBPProcessImpl() {
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
		
		@Override
		public String getName() {
			return getProcess().getProcessName();
		}
		
		@Override
		public String getValue() {
			return getProcessType()+"{"+getProcessID()+"}";
		}

	}

}