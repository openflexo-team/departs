/*
 * (c) Copyright 2013- Openflexo
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

package org.openflexo.technologyadapter.cdl.virtualmodel;

import java.lang.reflect.Type;

import org.openflexo.foundation.fml.rt.ActorReference;
import org.openflexo.foundation.fml.rt.FlexoConceptInstance;
import org.openflexo.foundation.fml.rt.ModelObjectActorReference;
import org.openflexo.foundation.fml.rt.VirtualModelInstanceModelFactory;
import org.openflexo.foundation.fml.FlexoRole;
import org.openflexo.foundation.fml.FlexoRole.RoleCloningStrategy;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.cdl.model.CDLGammaEvent;
import org.openflexo.technologyadapter.cdl.model.CDLInformalEvent;

@ModelEntity
@ImplementationClass(CDLInformalEventRole.CDLInformalEventRoleImpl.class)
@XMLElement
public interface CDLInformalEventRole extends FlexoRole<CDLInformalEvent> {

	public static abstract class CDLInformalEventRoleImpl extends FlexoRoleImpl<CDLInformalEvent> implements CDLInformalEventRole {

		@Override
		public Type getType() {
			return CDLInformalEvent.class;
		}
		
		/**
		 * Encodes the default cloning strategy
		 * 
		 * @return
		 */
		@Override
		public RoleCloningStrategy defaultCloningStrategy() {
			return RoleCloningStrategy.Clone;
		}

		@Override
		public String getPreciseType() {
			return CDLInformalEvent.class.getSimpleName();
		}
		
		@Override
		public ActorReference<CDLInformalEvent> makeActorReference(CDLInformalEvent object, FlexoConceptInstance epi) {
			VirtualModelInstanceModelFactory factory = epi.getFactory();
			ModelObjectActorReference<CDLInformalEvent> returned = factory.newInstance(ModelObjectActorReference.class);
			returned.setFlexoRole(this);
			returned.setFlexoConceptInstance(epi);
			returned.setModellingElement(object);
			return returned;
		}

	}
}
