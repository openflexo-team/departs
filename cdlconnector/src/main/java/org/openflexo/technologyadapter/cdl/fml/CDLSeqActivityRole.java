/*
 * (c) Copyright 2013- Openflexo
 *
 * This file is Seqt of OpenFlexo.
 *
 * OpenFlexo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenFlexo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A SeqTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenFlexo. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.openflexo.technologyadapter.cdl.fml;

import java.lang.reflect.Type;

import org.openflexo.foundation.fml.FlexoRole;
import org.openflexo.foundation.fml.annotations.FML;
import org.openflexo.foundation.fml.rt.AbstractVirtualModelInstanceModelFactory;
import org.openflexo.foundation.fml.rt.ActorReference;
import org.openflexo.foundation.fml.rt.FlexoConceptInstance;
import org.openflexo.foundation.fml.rt.ModelObjectActorReference;
import org.openflexo.foundation.technologyadapter.TechnologyAdapter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.cdl.CDLTechnologyAdapter;
import org.openflexo.technologyadapter.cdl.model.CDLSeqActivity;

@ModelEntity
@ImplementationClass(CDLSeqActivityRole.CDLSeqActivityRoleImpl.class)
@XMLElement
@FML("CDLSeqActivityRole")
public interface CDLSeqActivityRole extends FlexoRole<CDLSeqActivity> {

	public static abstract class CDLSeqActivityRoleImpl extends FlexoRoleImpl<CDLSeqActivity>implements CDLSeqActivityRole {

		@Override
		public Type getType() {
			return CDLSeqActivity.class;
		}

		@Override
		public String getTypeDescription() {
			return CDLSeqActivity.class.getSimpleName();
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
		public ActorReference<CDLSeqActivity> makeActorReference(CDLSeqActivity object, FlexoConceptInstance epi) {
			AbstractVirtualModelInstanceModelFactory<?> factory = epi.getFactory();
			ModelObjectActorReference<CDLSeqActivity> returned = factory.newInstance(ModelObjectActorReference.class);
			returned.setFlexoRole(this);
			returned.setFlexoConceptInstance(epi);
			returned.setModellingElement(object);
			return returned;
		}

		@Override
		public Class<? extends TechnologyAdapter> getRoleTechnologyAdapterClass() {
			return CDLTechnologyAdapter.class;
		}

	}
}
