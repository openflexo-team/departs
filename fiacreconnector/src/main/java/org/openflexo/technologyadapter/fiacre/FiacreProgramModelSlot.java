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

import org.openflexo.foundation.fml.FlexoRole;
import org.openflexo.foundation.fml.annotations.DeclareEditionAction;
import org.openflexo.foundation.fml.annotations.DeclareEditionActions;
import org.openflexo.foundation.fml.annotations.DeclareFetchRequest;
import org.openflexo.foundation.fml.annotations.DeclareFetchRequests;
import org.openflexo.foundation.fml.annotations.DeclareFlexoRole;
import org.openflexo.foundation.fml.annotations.DeclareFlexoRoles;
import org.openflexo.foundation.fml.rt.FreeModelSlotInstance;
import org.openflexo.foundation.fml.rt.action.CreateVirtualModelInstance;
import org.openflexo.foundation.ontology.IFlexoOntologyObject;
import org.openflexo.foundation.technologyadapter.FreeModelSlot;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.fiacre.model.FiacreProgram;
import org.openflexo.technologyadapter.fiacre.virtualmodel.FiacreComponentRole;
import org.openflexo.technologyadapter.fiacre.virtualmodel.FiacreFifoRole;
import org.openflexo.technologyadapter.fiacre.virtualmodel.FiacreProcessRole;
import org.openflexo.technologyadapter.fiacre.virtualmodel.FiacreStateRole;
import org.openflexo.technologyadapter.fiacre.virtualmodel.actions.AddFiacreComponent;
import org.openflexo.technologyadapter.fiacre.virtualmodel.actions.AddFiacreFifo;
import org.openflexo.technologyadapter.fiacre.virtualmodel.actions.AddFiacreProcess;
import org.openflexo.technologyadapter.fiacre.virtualmodel.actions.AddFiacreState;
import org.openflexo.technologyadapter.fiacre.virtualmodel.actions.SelectFiacreComponents;
import org.openflexo.technologyadapter.fiacre.virtualmodel.actions.SelectFiacreProcess;
import org.openflexo.technologyadapter.fiacre.virtualmodel.actions.SelectFiacreStates;

/**
 * Implementation of the ModelSlot class for the Fiacre technology adapter<br>
 * 
 * @author Vincent
 * 
 */
@ModelEntity
@ImplementationClass(FiacreProgramModelSlot.FiacreProgramSlotImpl.class)
@XMLElement
@DeclareFlexoRoles({ // All pattern roles available through this model slot
@DeclareFlexoRole(FML = "FiacreProcess", flexoRoleClass = FiacreProcessRole.class),
		@DeclareFlexoRole(FML = "FiacreComponent", flexoRoleClass = FiacreComponentRole.class),
		@DeclareFlexoRole(FML = "FiacreState", flexoRoleClass = FiacreStateRole.class),
		@DeclareFlexoRole(FML = "FiacreFifo", flexoRoleClass = FiacreFifoRole.class) })
@DeclareEditionActions({ // All edition actions available through this modelslot
@DeclareEditionAction(FML = "AddFiacreProcess", editionActionClass = AddFiacreProcess.class),
		@DeclareEditionAction(FML = "AddFiacreComponent", editionActionClass = AddFiacreComponent.class),
		@DeclareEditionAction(FML = "AddFiacreState", editionActionClass = AddFiacreState.class),
		@DeclareEditionAction(FML = "AddFiacreFifo", editionActionClass = AddFiacreFifo.class) })
@DeclareFetchRequests({ // All requests available through this model slot
		@DeclareFetchRequest(FML = "SelectFiacreProcess", fetchRequestClass = SelectFiacreProcess.class), // Sheet
		@DeclareFetchRequest(FML = "SelectFiacreComponents", fetchRequestClass = SelectFiacreComponents.class),
		@DeclareFetchRequest(FML = "SelectFiacreStates", fetchRequestClass = SelectFiacreStates.class) })
public interface FiacreProgramModelSlot extends FreeModelSlot<FiacreProgram> {

	@Override
	public FiacreTechnologyAdapter getModelSlotTechnologyAdapter();

	public static abstract class FiacreProgramSlotImpl extends FreeModelSlotImpl<FiacreProgram> implements FiacreProgramModelSlot {

		private static final Logger logger = Logger.getLogger(FiacreProgramModelSlot.class.getPackage().getName());

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
		public FiacreTechnologyAdapter getModelSlotTechnologyAdapter() {
			return (FiacreTechnologyAdapter) super.getModelSlotTechnologyAdapter();
		}

	}
}
