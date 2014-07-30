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
@ImplementationClass(OBPTraceContext.OBPTraceContextImpl.class)
@XMLElement(xmlTag = "OBPTraceContext")
public interface OBPTraceContext extends OBPTraceBehaviourObject{

	public static final String CONTEXT_KEY = "context";
	
	@Getter(value=CONTEXT_KEY, ignoreType=true)
	public Parser.Context getTraceOBPContext();
	
	@Setter(CONTEXT_KEY)
	public void setTraceOBPContext(Parser.Context context);
	
	public String getContextID();
	
	public String getContextType();

	public static abstract class OBPTraceContextImpl extends OBPTraceBehaviourObjectImpl implements OBPTraceContext {

		private String REGEX = "\\{{1}(\\w+)\\}{1}(\\d+)";
		
		public OBPTraceContextImpl() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getUri() {
			return getName();
		}

		@Override
		public String getContextID(){
			Pattern id = Pattern.compile(REGEX);
			Matcher makeMatch = id.matcher(getName());
			makeMatch.find();
			return makeMatch.group(2);
		}
		
		@Override
		public String getContextType(){
			Pattern id = Pattern.compile(REGEX);
			Matcher makeMatch = id.matcher(getName());
			makeMatch.find();
			return makeMatch.group(1);
		}
		
		@Override
		public String getName() {
			return getTraceOBPContext().getContextName();
		}
		
		@Override
		public String getValue() {
			return "{"+getContextType()+"}"+getContextID();
		}
	}

}