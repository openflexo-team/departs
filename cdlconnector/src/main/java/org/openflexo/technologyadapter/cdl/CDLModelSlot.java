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

package org.openflexo.technologyadapter.cdl;

import java.lang.reflect.Type;
import java.util.logging.Logger;

import org.openflexo.foundation.ontology.IFlexoOntologyObject;
import org.openflexo.foundation.technologyadapter.DeclareEditionAction;
import org.openflexo.foundation.technologyadapter.DeclareEditionActions;
import org.openflexo.foundation.technologyadapter.DeclarePatternRole;
import org.openflexo.foundation.technologyadapter.DeclarePatternRoles;
import org.openflexo.foundation.technologyadapter.FreeModelSlot;
import org.openflexo.foundation.view.FreeModelSlotInstance;
import org.openflexo.foundation.view.action.CreateVirtualModelInstance;
import org.openflexo.foundation.viewpoint.FlexoRole;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.cdl.model.CDLUnit;
import org.openflexo.technologyadapter.cdl.virtualmodel.CDLActivityRole;
import org.openflexo.technologyadapter.cdl.virtualmodel.CDLEventRole;
import org.openflexo.technologyadapter.cdl.virtualmodel.CDLProcessIDRole;
import org.openflexo.technologyadapter.cdl.virtualmodel.CDLPropertyRole;
import org.openflexo.technologyadapter.cdl.virtualmodel.action.AddCDLActivity;
import org.openflexo.technologyadapter.cdl.virtualmodel.action.AddCDLEvent;
import org.openflexo.technologyadapter.cdl.virtualmodel.action.AddCDLProcessID;
import org.openflexo.technologyadapter.cdl.virtualmodel.action.AddCDLProperty;

/**
 * Implementation of the ModelSlot class for the CDL technology adapter<br>
 * We expect here to connect an CDL model conform to an CDLMetaModel
 * 
 * @author SomeOne
 * 
 */
@ModelEntity
@ImplementationClass(CDLModelSlot.CDLModelSlotImpl.class)
@XMLElement
@DeclarePatternRoles({ // All pattern roles available through this model slot
@DeclarePatternRole(FML = "CDLEvent", flexoRoleClass = CDLEventRole.class), // Sheet
		@DeclarePatternRole(FML = "CDLProcessID", flexoRoleClass = CDLProcessIDRole.class), // Sheet
		@DeclarePatternRole(FML = "CDLProperty", flexoRoleClass = CDLPropertyRole.class), // Row
		@DeclarePatternRole(FML = "CDLState", flexoRoleClass = CDLActivityRole.class) // Cell
})
@DeclareEditionActions({ // All edition actions available through this modelslot
@DeclareEditionAction(FML = "AddCDLActivity", editionActionClass = AddCDLActivity.class), // Add // cell
		@DeclareEditionAction(FML = "AddCDLProcessID", editionActionClass = AddCDLProcessID.class), // Add // row
		@DeclareEditionAction(FML = "AddCDLProperty", editionActionClass = AddCDLProperty.class), // Add // sheet
		@DeclareEditionAction(FML = "AddCDLEvent", editionActionClass = AddCDLEvent.class) // Add
})
public interface CDLModelSlot extends FreeModelSlot<CDLUnit> {

	@Override
	public CDLTechnologyAdapter getTechnologyAdapter();

	public static abstract class CDLModelSlotImpl extends FreeModelSlotImpl<CDLUnit> implements CDLModelSlot {

		private static final Logger logger = Logger.getLogger(CDLModelSlot.class.getPackage().getName());

		@Override
		public Class<CDLTechnologyAdapter> getTechnologyAdapterClass() {
			return CDLTechnologyAdapter.class;
		}

		/**
		 * Instanciate a new model slot instance configuration for this model slot
		 */
		@Override
		public CDLModelSlotInstanceConfiguration createConfiguration(CreateVirtualModelInstance action) {
			return new CDLModelSlotInstanceConfiguration(this, action);
		}

		@Override
		public <PR extends FlexoRole<?>> String defaultFlexoRoleName(Class<PR> patternRoleClass) {
			if (CDLProcessIDRole.class.isAssignableFrom(patternRoleClass)) {
				return "processID";
			}
			return null;
		}

		@Override
		public String getURIForObject(FreeModelSlotInstance<CDLUnit, ? extends FreeModelSlot<CDLUnit>> msInstance, Object o) {
			if (o instanceof IFlexoOntologyObject) {
				return ((IFlexoOntologyObject) o).getURI();
			}
			return null;
		}

		@Override
		public Object retrieveObjectWithURI(FreeModelSlotInstance<CDLUnit, ? extends FreeModelSlot<CDLUnit>> msInstance, String objectURI) {
			return msInstance.getResourceData().getObject(objectURI);
		}

		@Override
		public Type getType() {
			return CDLUnit.class;
		}

		@Override
		public CDLTechnologyAdapter getTechnologyAdapter() {
			return (CDLTechnologyAdapter) super.getTechnologyAdapter();
		}

	}
}
