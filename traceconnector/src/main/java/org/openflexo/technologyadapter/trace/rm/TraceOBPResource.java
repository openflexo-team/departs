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

package org.openflexo.technologyadapter.trace.rm;

import org.openflexo.foundation.technologyadapter.TechnologyAdapterResource;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.Setter;
import org.openflexo.technologyadapter.trace.TraceTechnologyAdapter;
import org.openflexo.technologyadapter.trace.TraceTechnologyContextManager;
import org.openflexo.technologyadapter.trace.model.OBPTrace;
import org.openflexo.technologyadapter.trace.model.io.TraceModelConverter;

@ModelEntity
@ImplementationClass(TraceOBPResourceImpl.class)
public abstract interface TraceOBPResource extends TechnologyAdapterResource<OBPTrace, TraceTechnologyAdapter> {
	public static final String TECHNOLOGY_CONTEXT_MANAGER = "technologyContextManager";

	@Getter(value = "technologyContextManager", ignoreType = true)
	public abstract TraceTechnologyContextManager getTechnologyContextManager();

	@Setter("technologyContextManager")
	public abstract void setTechnologyContextManager(TraceTechnologyContextManager paramTraceTechnologyContextManager);

	public abstract TraceModelConverter getConverter();
}
