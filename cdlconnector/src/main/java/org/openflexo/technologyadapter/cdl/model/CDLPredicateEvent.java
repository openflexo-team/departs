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

import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;

@ModelEntity
@ImplementationClass(CDLPredicateEvent.CDLPredicateEventImpl.class)
@XMLElement(xmlTag = "CDLPredicateEvent")
public interface CDLPredicateEvent extends CDLEvent {

	public static final String PREDICATE_EVENT_KEY = "predicateEvent";
	
	@Getter(value=PREDICATE_EVENT_KEY, ignoreType=true)
	public obp.event.PredicateEvent getPredicateEvent();
	@Setter(PREDICATE_EVENT_KEY)
	public void setPredicateEvent(obp.event.PredicateEvent predicateEvent);

	public static abstract class CDLPredicateEventImpl extends CDLEventImpl implements CDLPredicateEvent {

		public CDLPredicateEventImpl() {
			// TODO Auto-generated constructor stub
		}
		
	}

}