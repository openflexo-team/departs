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

import org.openflexo.foundation.fml.FlexoRole;
import org.openflexo.foundation.fml.annotations.FML;
import org.openflexo.foundation.fml.rt.AbstractVirtualModelInstance;
import org.openflexo.foundation.fml.rt.FreeModelSlotInstance;
import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.technologyadapter.FreeModelSlot;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.trace.model.OBPTrace;

/**
 * Implementation of the ModelSlot class for the Trace technology adapter<br>
 * 
 * @author vleilde
 * 
 */
@ModelEntity
@ImplementationClass(TraceModelSlot.TraceModelSlotImpl.class)
@XMLElement
@FML("TraceModelSlot")
public interface TraceModelSlot extends FreeModelSlot<OBPTrace> {

	@Override
	public TraceTechnologyAdapter getModelSlotTechnologyAdapter();

	public static abstract class TraceModelSlotImpl extends FreeModelSlotImpl<OBPTrace>implements TraceModelSlot {

		private static final Logger logger = Logger.getLogger(TraceModelSlot.class.getPackage().getName());

		@Override
		public Class<TraceTechnologyAdapter> getTechnologyAdapterClass() {
			return TraceTechnologyAdapter.class;
		}

		/**
		 * Instanciate a new model slot instance configuration for this model slot
		 */
		@Override
		public TraceModelSlotInstanceConfiguration createConfiguration(AbstractVirtualModelInstance<?, ?> virtualModelInstance,
				FlexoResourceCenter<?> rc) {
			return new TraceModelSlotInstanceConfiguration(this, virtualModelInstance, rc);
		}

		@Override
		public <PR extends FlexoRole<?>> String defaultFlexoRoleName(Class<PR> patternRoleClass) {
			return null;
		}

		@Override
		public String getURIForObject(FreeModelSlotInstance<OBPTrace, ? extends FreeModelSlot<OBPTrace>> msInstance, Object o) {
			/*if (o instanceof IFlexoOntologyObject) {
				return ((IFlexoOntologyObject) o).getURI();
			}*/
			return null;
		}

		@Override
		public Object retrieveObjectWithURI(FreeModelSlotInstance<OBPTrace, ? extends FreeModelSlot<OBPTrace>> msInstance,
				String objectURI) {
			return msInstance.getResourceData().getObject(objectURI);
		}

		@Override
		public Type getType() {
			return OBPTrace.class;
		}

		@Override
		public TraceTechnologyAdapter getModelSlotTechnologyAdapter() {
			return (TraceTechnologyAdapter) super.getModelSlotTechnologyAdapter();
		}

	}
}
