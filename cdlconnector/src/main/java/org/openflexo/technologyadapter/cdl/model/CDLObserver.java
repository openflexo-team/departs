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

import obp.obs.State;
import obp.property.PropertyObserver;

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
@ImplementationClass(CDLObserver.CDLObserverImpl.class)
@XMLElement(xmlTag = "CDLObserver")
public abstract interface CDLObserver extends CDLProperty {
	
	public static final String PROPERTY_KEY = "cdlObserver";
	
	@PropertyIdentifier(type = List.class)
	public static final String STATES_KEY = "states";
	
	@PropertyIdentifier(type = State.class)
	public static final String START_STATE_KEY = "startState";

	@Getter(value = PROPERTY_KEY, ignoreType=true)
	public PropertyObserver getCDLPropertyObserver();

	@Setter(value = PROPERTY_KEY)
	public void setCDLPropertyObserver(PropertyObserver propertyObserver);
	
	@Getter(value = STATES_KEY, cardinality = Cardinality.LIST, inverse = CDLObserverState.OBSERVER_KEY)
	public List<CDLObserverState> getCDLObserverStates();

	@Setter(STATES_KEY)
	public void setCDLObserverStates(List<CDLObserverState> cdlObserverState);

	@Adder(STATES_KEY)
	public void addToCDLObserverStates(CDLObserverState cdlObserverState);

	@Remover(STATES_KEY)
	public void removeFromCDLObserverStates(CDLObserverState cdlObserverState);
	
	@Getter(value = START_STATE_KEY)
	public CDLObserverState getStartState();

	@Setter(value = START_STATE_KEY)
	public void setStartState(CDLObserverState cdlObserverState);

	public static abstract class CDLObserverImpl extends CDLPropertyImpl implements CDLObserver {

		@Override
		public String getUri() {
			return getName();
		}

	}

}