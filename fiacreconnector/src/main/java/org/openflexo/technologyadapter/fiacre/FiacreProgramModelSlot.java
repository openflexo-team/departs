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

import org.openflexo.foundation.FlexoProject;
import org.openflexo.foundation.fml.FlexoRole;
import org.openflexo.foundation.fml.annotations.DeclareEditionActions;
import org.openflexo.foundation.fml.annotations.DeclareFetchRequests;
import org.openflexo.foundation.fml.annotations.DeclareFlexoRoles;
import org.openflexo.foundation.fml.rt.AbstractVirtualModelInstance;
import org.openflexo.foundation.fml.rt.FreeModelSlotInstance;
import org.openflexo.foundation.technologyadapter.FreeModelSlot;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.fiacre.fml.FiacreComponentRole;
import org.openflexo.technologyadapter.fiacre.fml.FiacreFifoRole;
import org.openflexo.technologyadapter.fiacre.fml.FiacreProcessRole;
import org.openflexo.technologyadapter.fiacre.fml.FiacreStateRole;
import org.openflexo.technologyadapter.fiacre.fml.actions.AddFiacreComponent;
import org.openflexo.technologyadapter.fiacre.fml.actions.AddFiacreFifo;
import org.openflexo.technologyadapter.fiacre.fml.actions.AddFiacreProcess;
import org.openflexo.technologyadapter.fiacre.fml.actions.AddFiacreState;
import org.openflexo.technologyadapter.fiacre.fml.actions.SelectFiacreComponents;
import org.openflexo.technologyadapter.fiacre.fml.actions.SelectFiacreProcess;
import org.openflexo.technologyadapter.fiacre.fml.actions.SelectFiacreStates;
import org.openflexo.technologyadapter.fiacre.model.FiacreProgram;

/**
 * Implementation of the ModelSlot class for the Fiacre technology adapter<br>
 * 
 * @author Vincent
 * 
 */
@ModelEntity
@ImplementationClass(FiacreProgramModelSlot.FiacreProgramSlotImpl.class)
@XMLElement
@DeclareFlexoRoles({ FiacreProcessRole.class, FiacreComponentRole.class, FiacreStateRole.class, FiacreFifoRole.class })
@DeclareEditionActions({ AddFiacreProcess.class, AddFiacreComponent.class, AddFiacreState.class, AddFiacreFifo.class })
@DeclareFetchRequests({ SelectFiacreProcess.class, SelectFiacreComponents.class, SelectFiacreStates.class })
public interface FiacreProgramModelSlot extends FreeModelSlot<FiacreProgram> {

	@Override
	public FiacreTechnologyAdapter getModelSlotTechnologyAdapter();

	public static abstract class FiacreProgramSlotImpl extends FreeModelSlotImpl<FiacreProgram>implements FiacreProgramModelSlot {

		private static final Logger logger = Logger.getLogger(FiacreProgramModelSlot.class.getPackage().getName());

		@Override
		public Class<FiacreTechnologyAdapter> getTechnologyAdapterClass() {
			return FiacreTechnologyAdapter.class;
		}

		/**
		 * Instanciate a new model slot instance configuration for this model slot
		 */
		@Override
		public FiacreProgramSlotInstanceConfiguration createConfiguration(AbstractVirtualModelInstance<?, ?> virtualModelInstance,
				FlexoProject project) {
			return new FiacreProgramSlotInstanceConfiguration(this, virtualModelInstance, project);
		}

		@Override
		public <PR extends FlexoRole<?>> String defaultFlexoRoleName(Class<PR> patternRoleClass) {
			return null;
		}

		@Override
		public String getURIForObject(FreeModelSlotInstance<FiacreProgram, ? extends FreeModelSlot<FiacreProgram>> msInstance, Object o) {
			/*if (o instanceof IFlexoOntologyObject) {
				return ((IFlexoOntologyObject) o).getURI();
			}*/
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
		public FiacreTechnologyAdapter getModelSlotTechnologyAdapter() {
			return (FiacreTechnologyAdapter) super.getModelSlotTechnologyAdapter();
		}

	}
}
