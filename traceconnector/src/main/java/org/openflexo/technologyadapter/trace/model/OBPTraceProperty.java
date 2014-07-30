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

@ModelEntity
@ImplementationClass(OBPTraceProperty.OBPTracePropertyImpl.class)
@XMLElement(xmlTag = "OBPTraceProperty")
public interface OBPTraceProperty extends OBPTraceBehaviourObject {

	public static final String PROPERTY_KEY = "property";
	
	@Getter(value=PROPERTY_KEY, ignoreType=true)
	public Parser.Property getProperty();
	
	@Setter(PROPERTY_KEY)
	public void setProperty(Parser.Property property);
	
	public String getPropertyID();
	
	public String getPropertyType();

	public static abstract class OBPTracePropertyImpl extends OBPTraceBehaviourObjectImpl implements OBPTraceProperty {

		private String REGEX = "\\{{1}(\\w+)\\}{1}(\\d+)";
		
		public OBPTracePropertyImpl() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getUri() {
			return getName();
		}
		
		@Override
		public String getName() {
			return getProperty().getPropertyName();
		}

		@Override
		public String getValue() {
			return "{"+getPropertyID()+"}";
		}
		
		@Override
		public String getPropertyID(){
			Pattern id = Pattern.compile(REGEX);
			Matcher makeMatch = id.matcher(getName());
			makeMatch.find();
			return makeMatch.group(2);
		}
		
		@Override
		public String getPropertyType(){
			Pattern id = Pattern.compile(REGEX);
			Matcher makeMatch = id.matcher(getName());
			makeMatch.find();
			return makeMatch.group(1);
		}

	}

}