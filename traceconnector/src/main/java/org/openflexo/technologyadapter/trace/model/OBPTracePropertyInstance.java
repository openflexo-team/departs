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

import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;

@ModelEntity
@ImplementationClass(OBPTracePropertyInstance.OBPTracePropertyInstanceImpl.class)
@XMLElement(xmlTag = "OBPTracePropertyInstance")
public interface OBPTracePropertyInstance extends OBPTraceBehaviourObjectInstance {

	public static abstract class OBPTracePropertyInstanceImpl extends OBPTraceBehaviourObjectInstanceImpl implements OBPTracePropertyInstance {

		public OBPTracePropertyInstanceImpl() {
			// TODO Auto-generated constructor stub
		}
		
		static private String REGEX = "\\{{1}(\\w+)\\}{1}(\\d+)";
		
		static public String computeValueFromRowName(String name){
			String idValue =null;
			Pattern id = Pattern.compile(REGEX);
			Matcher makeMatchId = id.matcher(name);
			makeMatchId.find();
			idValue = makeMatchId.group(2);
			
			String typeValue =null;
			Pattern type = Pattern.compile(REGEX);
			Matcher makeMatchType = type.matcher(name);
			makeMatchType.find();
			typeValue = makeMatchType.group(1);
			
			return "{"+typeValue+"}"+idValue;
		}

		@Override
		public String getUri() {
			return getName();
		}

		@Override
		public String getValue() {
			return "{"+getType()+"}"+getID();
		}
		
		@Override
		public String getID(){
			Pattern id = Pattern.compile(REGEX);
			Matcher makeMatch = id.matcher(getName());
			makeMatch.find();
			return makeMatch.group(2);
		}
		
		@Override
		public String getType(){
			Pattern id = Pattern.compile(REGEX);
			Matcher makeMatch = id.matcher(getName());
			makeMatch.find();
			return makeMatch.group(1);
		}
	}

}