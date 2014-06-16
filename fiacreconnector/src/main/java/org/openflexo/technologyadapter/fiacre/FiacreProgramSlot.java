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

package org.openflexo.technologyadapter.fiacre;

import java.lang.reflect.Type;
import java.util.logging.Logger;

import org.openflexo.foundation.ontology.IFlexoOntologyObject;
import org.openflexo.foundation.technologyadapter.DeclareEditionActions;
import org.openflexo.foundation.technologyadapter.DeclarePatternRoles;
import org.openflexo.foundation.technologyadapter.FreeModelSlot;
import org.openflexo.foundation.view.FreeModelSlotInstance;
import org.openflexo.foundation.view.action.CreateVirtualModelInstance;
import org.openflexo.foundation.viewpoint.FlexoRole;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.fiacre.model.FiacreProgram;

/**
 * Implementation of the ModelSlot class for the Fiacre technology adapter<br>
 * We expect here to connect an Fiacre model conform to an FiacreMetaModel
 * 
 * @author SomeOne
 * 
 */
@ModelEntity
@ImplementationClass(FiacreProgramSlot.FiacreProgramSlotImpl.class)
@XMLElement
@DeclarePatternRoles({ // All pattern roles available through this model slot

})
@DeclareEditionActions({ // All edition actions available through this modelslot
})
public interface FiacreProgramSlot extends FreeModelSlot<FiacreProgram> {

	@Override
	public FiacreTechnologyAdapter getTechnologyAdapter();

	public static abstract class FiacreProgramSlotImpl extends FreeModelSlotImpl<FiacreProgram> implements FiacreProgramSlot {

		private static final Logger logger = Logger.getLogger(FiacreProgramSlot.class.getPackage().getName());

		@Override
		public Class<FiacreTechnologyAdapter> getTechnologyAdapterClass() {
			return FiacreTechnologyAdapter.class;
		}

		/**
		 * Instanciate a new model slot instance configuration for this model slot
		 */
		@Override
		public FiacreProgramSlotInstanceConfiguration createConfiguration(CreateVirtualModelInstance action) {
			return new FiacreProgramSlotInstanceConfiguration(this, action);
		}

		@Override
		public <PR extends FlexoRole<?>> String defaultFlexoRoleName(Class<PR> patternRoleClass) {
			return null;
		}

		@Override
		public String getURIForObject(FreeModelSlotInstance<FiacreProgram, ? extends FreeModelSlot<FiacreProgram>> msInstance, Object o) {
			if (o instanceof IFlexoOntologyObject) {
				return ((IFlexoOntologyObject) o).getURI();
			}
			return null;
		}

		@Override
		public Object retrieveObjectWithURI(FreeModelSlotInstance<FiacreProgram, ? extends FreeModelSlot<FiacreProgram>> msInstance,
				String objectURI) {
			return msInstance.getResourceData().getObject(objectURI);
		}

		@Override
		public Type getType() {
			return FiacreProgram.class;
		}

		@Override
		public FiacreTechnologyAdapter getTechnologyAdapter() {
			return (FiacreTechnologyAdapter) super.getTechnologyAdapter();
		}

	}
}
