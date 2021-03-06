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

import org.openflexo.foundation.fml.FlexoRole;
import org.openflexo.foundation.fml.annotations.DeclareEditionActions;
import org.openflexo.foundation.fml.annotations.DeclareFetchRequests;
import org.openflexo.foundation.fml.annotations.DeclareFlexoRoles;
import org.openflexo.foundation.fml.annotations.FML;
import org.openflexo.foundation.fml.rt.FreeModelSlotInstance;
import org.openflexo.foundation.fml.rt.action.CreateVirtualModelInstance;
import org.openflexo.foundation.ontology.IFlexoOntologyObject;
import org.openflexo.foundation.technologyadapter.FreeModelSlot;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.cdl.fml.CDLAltActivityRole;
import org.openflexo.technologyadapter.cdl.fml.CDLCommunicationOpEventRole;
import org.openflexo.technologyadapter.cdl.fml.CDLEventActivityRole;
import org.openflexo.technologyadapter.cdl.fml.CDLGammaEventRole;
import org.openflexo.technologyadapter.cdl.fml.CDLInformalEventRole;
import org.openflexo.technologyadapter.cdl.fml.CDLParActivityRole;
import org.openflexo.technologyadapter.cdl.fml.CDLPredicateEventRole;
import org.openflexo.technologyadapter.cdl.fml.CDLProcessIDRole;
import org.openflexo.technologyadapter.cdl.fml.CDLPropertyRole;
import org.openflexo.technologyadapter.cdl.fml.CDLSeqActivityRole;
import org.openflexo.technologyadapter.cdl.fml.action.AddCDLProcessID;
import org.openflexo.technologyadapter.cdl.fml.action.AddCDLProperty;
import org.openflexo.technologyadapter.cdl.fml.action.SelectCDLAltActivity;
import org.openflexo.technologyadapter.cdl.fml.action.SelectCDLEventReference;
import org.openflexo.technologyadapter.cdl.fml.action.SelectCDLParActivity;
import org.openflexo.technologyadapter.cdl.fml.action.SelectCDLProperties;
import org.openflexo.technologyadapter.cdl.model.CDLUnit;

/**
 * Implementation of the ModelSlot class for the CDL technology adapter<br>
 * 
 * @author vleilde
 * 
 */
@ModelEntity
@ImplementationClass(CDLModelSlot.CDLModelSlotImpl.class)
@XMLElement
@DeclareFlexoRoles({ CDLCommunicationOpEventRole.class, CDLGammaEventRole.class, CDLInformalEventRole.class, CDLPredicateEventRole.class,
		CDLAltActivityRole.class, CDLParActivityRole.class, CDLSeqActivityRole.class, CDLEventActivityRole.class, CDLProcessIDRole.class,
		CDLPropertyRole.class })
@DeclareEditionActions({ AddCDLProcessID.class, AddCDLProperty.class })
@DeclareFetchRequests({ SelectCDLAltActivity.class, SelectCDLParActivity.class, SelectCDLEventReference.class, SelectCDLProperties.class })
@FML("CDLModelSlot")
public interface CDLModelSlot extends FreeModelSlot<CDLUnit> {

	@Override
	public CDLTechnologyAdapter getModelSlotTechnologyAdapter();

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
		public CDLTechnologyAdapter getModelSlotTechnologyAdapter() {
			return (CDLTechnologyAdapter) super.getModelSlotTechnologyAdapter();
		}

	}
}
