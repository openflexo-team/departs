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

package org.openflexo.technologyadapter.trace;

import java.lang.reflect.Type;
import java.util.logging.Logger;

import org.openflexo.foundation.ontology.IFlexoOntologyObject;
import org.openflexo.foundation.technologyadapter.FreeModelSlot;
import org.openflexo.foundation.view.FreeModelSlotInstance;
import org.openflexo.foundation.view.action.CreateVirtualModelInstance;
import org.openflexo.foundation.viewpoint.FlexoRole;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.trace.model.FlexoTraceOBP;

/**
 * Implementation of the ModelSlot class for the Trace technology adapter<br>
 * 
 * @author vleilde
 * 
 */
@ModelEntity
@ImplementationClass(TraceModelSlot.TraceModelSlotImpl.class)
@XMLElement
public interface TraceModelSlot extends FreeModelSlot<FlexoTraceOBP> {

	@Override
	public TraceTechnologyAdapter getTechnologyAdapter();

	public static abstract class TraceModelSlotImpl extends FreeModelSlotImpl<FlexoTraceOBP> implements TraceModelSlot {

		private static final Logger logger = Logger.getLogger(TraceModelSlot.class.getPackage().getName());

		@Override
		public Class<TraceTechnologyAdapter> getTechnologyAdapterClass() {
			return TraceTechnologyAdapter.class;
		}

		/**
		 * Instanciate a new model slot instance configuration for this model slot
		 */
		@Override
		public TraceModelSlotInstanceConfiguration createConfiguration(CreateVirtualModelInstance action) {
			return new TraceModelSlotInstanceConfiguration(this, action);
		}

		@Override
		public <PR extends FlexoRole<?>> String defaultFlexoRoleName(Class<PR> patternRoleClass) {
			return null;
		}

		@Override
		public String getURIForObject(FreeModelSlotInstance<FlexoTraceOBP, ? extends FreeModelSlot<FlexoTraceOBP>> msInstance, Object o) {
			if (o instanceof IFlexoOntologyObject) {
				return ((IFlexoOntologyObject) o).getURI();
			}
			return null;
		}

		@Override
		public Object retrieveObjectWithURI(FreeModelSlotInstance<FlexoTraceOBP, ? extends FreeModelSlot<FlexoTraceOBP>> msInstance, String objectURI) {
			return msInstance.getResourceData().getObject(objectURI);
		}

		@Override
		public Type getType() {
			return FlexoTraceOBP.class;
		}

		@Override
		public TraceTechnologyAdapter getTechnologyAdapter() {
			return (TraceTechnologyAdapter) super.getTechnologyAdapter();
		}

	}
}
