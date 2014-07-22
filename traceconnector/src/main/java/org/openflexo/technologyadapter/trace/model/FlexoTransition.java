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

import Parser.Transition;

@ModelEntity
@ImplementationClass(FlexoTransition.FlexoTransitionImpl.class)
@XMLElement(xmlTag = "FlexoTransition")
public interface FlexoTransition extends FlexoTraceObject, ResourceData<FlexoTransition> {

	public static final String TRANSITION_KEY = "Transition";
	
	@Getter(value=TRANSITION_KEY, ignoreType=true)
	public Transition getTransition();
	@Setter(TRANSITION_KEY)
	public void setTransition(Transition transition);

	public static abstract class FlexoTransitionImpl extends FlexoTraceObjectImpl implements FlexoTransition {

		public FlexoTransitionImpl() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getUri() {
			return getName();
		}

	}

}