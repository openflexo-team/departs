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

import org.openflexo.foundation.view.ActorReference;
import org.openflexo.foundation.view.FlexoConceptInstance;
import org.openflexo.foundation.view.ModelObjectActorReference;
import org.openflexo.foundation.view.VirtualModelInstanceModelFactory;
import org.openflexo.foundation.viewpoint.FlexoRole;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.cdl.model.CDLCommunicationOPEvent;
import org.openflexo.technologyadapter.cdl.model.CDLEventReference;

@ModelEntity
@ImplementationClass(CDLEventActivityRole.CDLEventActivityRoleImpl.class)
@XMLElement
public interface CDLEventActivityRole extends FlexoRole<CDLEventReference> {

	public static abstract class CDLEventActivityRoleImpl extends FlexoRoleImpl<CDLEventReference> implements CDLEventActivityRole {

		@Override
		public Type getType() {
			return CDLEventReference.class;
		}

		@Override
		public String getPreciseType() {
			return CDLEventReference.class.getSimpleName();
		}

		@Override
		public ActorReference<CDLEventReference> makeActorReference(CDLEventReference object, FlexoConceptInstance epi) {
			VirtualModelInstanceModelFactory factory = epi.getFactory();
			ModelObjectActorReference<CDLEventReference> returned = factory.newInstance(ModelObjectActorReference.class);
			returned.setFlexoRole(this);
			returned.setFlexoConceptInstance(epi);
			returned.setModellingElement(object);
			return returned;
		}
	}
}