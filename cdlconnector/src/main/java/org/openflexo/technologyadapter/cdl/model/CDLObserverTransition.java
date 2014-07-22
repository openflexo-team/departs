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

import obp.obs.Transition;

import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;

@ModelEntity
@ImplementationClass(CDLObserverTransition.CDLObserverTransitionImpl.class)
@XMLElement(xmlTag = "CDLObserverTransition")
public abstract interface CDLObserverTransition extends CDLObject {
	
	public static final String PROPERTY_KEY = "CDLObserverTransition";
	
	public static final String EVENT_REFERENCE = "CDLEventReference";
	
	public static final String FROM_STATE = "fromState";
	
	public static final String TO_STATE = "toState";

	@Getter(value = PROPERTY_KEY, ignoreType=true)
	public Transition getCDLObserverTransition();

	@Setter(value = PROPERTY_KEY)
	public void setCDLObserverTransition(Transition Transition);
	
	@Getter(value = EVENT_REFERENCE)
	public CDLEventReference getCDLEventReference();

	@Setter(value = EVENT_REFERENCE)
	public void setCDLEventReference(CDLEventReference cdlEventReference);
	
	@Getter(value = FROM_STATE, inverse = CDLObserverState.OUT_TRANSITIONS_KEY)
	public CDLObserverState getCDLFromObserverState();

	@Setter(value = FROM_STATE)
	public void setCDLFromObserverState(CDLObserverState cdlObserverState);
	
	@Getter(value = TO_STATE, inverse = CDLObserverState.IN_TRANSITIONS_KEY)
	public CDLObserverState getCDLToObserverState();

	@Setter(value = TO_STATE)
	public void setCDLToObserverState(CDLObserverState cdlObserverState);

	public static abstract class CDLObserverTransitionImpl extends CDLObjectImpl implements CDLObserverTransition {

		@Override
		public String getUri() {
			return getName();
		}

	}

}