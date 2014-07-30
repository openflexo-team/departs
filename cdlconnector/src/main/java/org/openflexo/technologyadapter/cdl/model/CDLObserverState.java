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
@ImplementationClass(CDLObserverState.CDLObserverStateImpl.class)
@XMLElement(xmlTag = "CDLObserverState")
public abstract interface CDLObserverState extends CDLObject {
	
	public static final String PROPERTY_KEY = "CDLObserverState";
	
	public static final String OBSERVER_KEY = "CDLObserver";
	
	@PropertyIdentifier(type = List.class)
	public static final String IN_TRANSITIONS_KEY = "inTransitions";
	
	@PropertyIdentifier(type = List.class)
	public static final String OUT_TRANSITIONS_KEY = "outTransitions";

	@Getter(value = PROPERTY_KEY, ignoreType=true)
	public State getCDLObserverState();

	@Setter(value = PROPERTY_KEY)
	public void setCDLObserverState(State state);
	
	@Getter(value = OBSERVER_KEY, inverse = CDLObserver.STATES_KEY)
	public CDLObserver getCDLObserver();

	@Setter(value = OBSERVER_KEY)
	public void setCDLObserver(CDLObserver cdlObserver);
	
	@Getter(value = IN_TRANSITIONS_KEY, cardinality = Cardinality.LIST , inverse = CDLObserverTransition.TO_STATE)
	public List<CDLObserverTransition> getCDLObserverInTransitions();

	@Setter(IN_TRANSITIONS_KEY)
	public void setCDLObserverInTransitions(List<CDLObserverTransition> cdlObserverInTransition);

	@Adder(IN_TRANSITIONS_KEY)
	public void addToCDLObserverInTransitions(CDLObserverTransition cdlObserverInTransition);

	@Remover(IN_TRANSITIONS_KEY)
	public void removeFromCDLObserverInTransitions(CDLObserverTransition cdlObserverInTransition);
	
	@Getter(value = OUT_TRANSITIONS_KEY, cardinality = Cardinality.LIST , inverse = CDLObserverTransition.FROM_STATE)
	public List<CDLObserverTransition> getCDLObserverOutTransitions();

	@Setter(OUT_TRANSITIONS_KEY)
	public void setCDLObserverOutTransitions(List<CDLObserverTransition> cdlObserverOutTransition);

	@Adder(OUT_TRANSITIONS_KEY)
	public void addToCDLObserverOutTransitions(CDLObserverTransition cdlObserverOutTransition);

	@Remover(OUT_TRANSITIONS_KEY)
	public void removeFromCDLObserverOutTransitions(CDLObserverTransition cdlObserverOutTransition);

	public static abstract class CDLObserverStateImpl extends CDLObjectImpl implements CDLObserverState {

		@Override
		public String getUri() {
			return getName();
		}

	}

}