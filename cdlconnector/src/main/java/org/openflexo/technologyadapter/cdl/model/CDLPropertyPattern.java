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

package org.openflexo.technologyadapter.cdl.model;

import java.util.List;

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
@ImplementationClass(CDLPropertyPattern.CDLPropertyImpl.class)
@XMLElement(xmlTag = "CDLPropertyPattern")
public abstract interface CDLPropertyPattern extends CDLProperty {
	
	@PropertyIdentifier(type = List.class)
	public static final String EVENT_REFERENCE_KEY = "eventReferences";
	
	@Getter(value = EVENT_REFERENCE_KEY, cardinality = Cardinality.LIST)
	public List<CDLEventReference> getCDLEventReferences();

	@Setter(EVENT_REFERENCE_KEY)
	public void setCDLEventReferences(List<CDLEventReference> cdlEventReference);

	@Adder(EVENT_REFERENCE_KEY)
	public void addToCDLEventReferences(CDLEventReference cdlEventReference);

	@Remover(EVENT_REFERENCE_KEY)
	public void removeFromCDLEventReferences(CDLEventReference cdlEventReference);

	public static abstract class CDLPropertyPatternImpl extends CDLPropertyImpl implements CDLPropertyPattern {

		@Override
		public String getUri() {
			return getName();
		}

	}

}