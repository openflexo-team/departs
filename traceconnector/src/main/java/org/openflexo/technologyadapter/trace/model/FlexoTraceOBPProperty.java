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

import org.openflexo.foundation.resource.ResourceData;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;

@ModelEntity
@ImplementationClass(FlexoTraceOBPProperty.FlexoTraceOBPPropertyImpl.class)
@XMLElement(xmlTag = "FlexoTraceOBPProperty")
public interface FlexoTraceOBPProperty extends FlexoTraceOBPObject {

	public static final String PROPERTY_KEY = "property";
	
	@Getter(value=PROPERTY_KEY, ignoreType=true)
	public Parser.Property getProperty();
	
	@Setter(PROPERTY_KEY)
	public void setProperty(Parser.Property property);

	public static abstract class FlexoTraceOBPPropertyImpl extends FlexoTraceOBPObjectImpl implements FlexoTraceOBPProperty {

		public FlexoTraceOBPPropertyImpl() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getUri() {
			return getName();
		}

	}

}