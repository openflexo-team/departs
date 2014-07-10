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
import org.openflexo.technologyadapter.cdl.virtualmodel.CDLActivityReferenceRole;
import org.openflexo.technologyadapter.cdl.virtualmodel.CDLAltActivityRole;
import org.openflexo.technologyadapter.cdl.virtualmodel.CDLCommunicationOpEventRole;
import org.openflexo.technologyadapter.cdl.virtualmodel.CDLEventActivityRole;
import org.openflexo.technologyadapter.cdl.virtualmodel.CDLGammaEventRole;
import org.openflexo.technologyadapter.cdl.virtualmodel.CDLInformalEventRole;
import org.openflexo.technologyadapter.cdl.virtualmodel.CDLParActivityRole;
import org.openflexo.technologyadapter.cdl.virtualmodel.CDLPredicateEventRole;
import org.openflexo.technologyadapter.cdl.virtualmodel.CDLProcessIDRole;
import org.openflexo.technologyadapter.cdl.virtualmodel.CDLPropertyRole;
import org.openflexo.technologyadapter.cdl.virtualmodel.CDLSeqActivityRole;
import org.openflexo.technologyadapter.cdl.virtualmodel.action.AddCDLActivity;
import org.openflexo.technologyadapter.cdl.virtualmodel.action.AddCDLEvent;
import org.openflexo.technologyadapter.cdl.virtualmodel.action.AddCDLProcessID;
import org.openflexo.technologyadapter.cdl.virtualmodel.action.AddCDLProperty;

/**
 * Implementation of the ModelSlot class for the CDL technology adapter<br>
 * We expect here to connect an CDL model conform to an CDLMetaModel
 * 
 * @author vleilde
 * 
 */
@ModelEntity
@ImplementationClass(CDLModelSlot.CDLModelSlotImpl.class)
@XMLElement
@DeclarePatternRoles({ // All pattern roles available through this model slot
		@DeclarePatternRole(FML = "CDLCommunicationOPEvent", flexoRoleClass = CDLCommunicationOpEventRole.class),
		@DeclarePatternRole(FML = "CDLGammaEvent", flexoRoleClass = CDLGammaEventRole.class),
		@DeclarePatternRole(FML = "CDLInformalEvent", flexoRoleClass = CDLInformalEventRole.class),
		@DeclarePatternRole(FML = "CDLPredicateEvent", flexoRoleClass = CDLPredicateEventRole.class),
		@DeclarePatternRole(FML = "CDLActivityReference", flexoRoleClass = CDLActivityReferenceRole.class),
		@DeclarePatternRole(FML = "CDLAltActivity", flexoRoleClass = CDLAltActivityRole.class),
		@DeclarePatternRole(FML = "CDLParActivity", flexoRoleClass = CDLParActivityRole.class),
		@DeclarePatternRole(FML = "CDLSeqActivity", flexoRoleClass = CDLSeqActivityRole.class),
		@DeclarePatternRole(FML = "CDLEventActivity", flexoRoleClass = CDLEventActivityRole.class),
		@DeclarePatternRole(FML = "CDLProcessID", flexoRoleClass = CDLProcessIDRole.class),
		@DeclarePatternRole(FML = "CDLProperty", flexoRoleClass = CDLPropertyRole.class)
})
@DeclareEditionActions({ // All edition actions available through this modelslot
		@DeclareEditionAction(FML = "AddCDLProcessID", editionActionClass = AddCDLProcessID.class), 
		@DeclareEditionAction(FML = "AddCDLProperty", editionActionClass = AddCDLProperty.class) 
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
