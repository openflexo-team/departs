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
import org.openflexo.technologyadapter.cdl.model.CDLAltActivity;
import org.openflexo.technologyadapter.cdl.model.CDLCommunicationOPEvent;

@ModelEntity
@ImplementationClass(CDLCommunicationOpEventRole.CDLCommunicationOpEventRoleImpl.class)
@XMLElement
public interface CDLCommunicationOpEventRole extends FlexoRole<CDLCommunicationOPEvent> {

	public static abstract class CDLCommunicationOpEventRoleImpl extends FlexoRoleImpl<CDLCommunicationOPEvent> implements CDLCommunicationOpEventRole {

		@Override
		public Type getType() {
			return CDLCommunicationOPEvent.class;
		}

		@Override
		public String getPreciseType() {
			return CDLCommunicationOPEvent.class.getSimpleName();
		}

		@Override
		public ActorReference<CDLCommunicationOPEvent> makeActorReference(CDLCommunicationOPEvent object, FlexoConceptInstance epi) {
			VirtualModelInstanceModelFactory factory = epi.getFactory();
			ModelObjectActorReference<CDLCommunicationOPEvent> returned = factory.newInstance(ModelObjectActorReference.class);
			returned.setFlexoRole(this);
			returned.setFlexoConceptInstance(epi);
			returned.setModellingElement(object);
			return returned;
		}
	}
}