/*
 * (c) Copyright 2013 Openflexo
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

package org.openflexo.technologyadapter.trace.model;

import java.util.List;

import org.openflexo.foundation.resource.ResourceData;
import org.openflexo.model.annotations.Adder;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.Getter.Cardinality;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Remover;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.trace.model.io.TraceModelConverter;

import Parser.TraceOBP;

@ModelEntity
@ImplementationClass(FlexoTraceOBP.FlexoTraceOBPImpl.class)
@XMLElement(xmlTag = "FlexoTraceOBP")
public interface FlexoTraceOBP extends FlexoTraceOBPObject, ResourceData<FlexoTraceOBP> {

	public static final String TRACE_OBP_KEY = "traceOBP";
	
	@PropertyIdentifier(type = List.class)
	public static final String CONFIG_DATA_KEY = "configData";
	@PropertyIdentifier(type = List.class)
	public static final String TRANSITIONS_KEY = "transitions";

	public TraceOBP getTraceOBP();

	public void setTraceOBP(TraceOBP traceOBP);

	@Getter(value = CONFIG_DATA_KEY, cardinality = Cardinality.LIST)
	public List<FlexoTraceOBPConfigData> getFlexoConfigData();

	@Setter(CONFIG_DATA_KEY)
	public void setFlexoConfigData(List<FlexoTraceOBPConfigData> flexoConfigData);

	@Adder(CONFIG_DATA_KEY)
	public void addToFlexoConfigData(FlexoTraceOBPConfigData flexoConfigData);

	@Remover(CONFIG_DATA_KEY)
	public void removeFromFlexoConfigData(FlexoTraceOBPConfigData flexoConfigData);

	@Getter(value = TRANSITIONS_KEY, cardinality = Cardinality.LIST)
	public List<FlexoTraceOBPTransition> getFlexoTransitions();

	@Setter(TRANSITIONS_KEY)
	public void setFlexoTransitions(List<FlexoTraceOBPTransition> flexoTransition);

	@Adder(TRANSITIONS_KEY)
	public void addToFlexoTransitions(FlexoTraceOBPTransition flexoTransition);

	@Remover(TRANSITIONS_KEY)
	public void removeFromFlexoTransitions(FlexoTraceOBPTransition flexoTransition);

	public TraceModelConverter getConverter();

	public void setConverter(TraceModelConverter converter);

	public static abstract class FlexoTraceOBPImpl extends FlexoTraceOBPObjectImpl implements FlexoTraceOBP {

		public FlexoTraceOBPImpl() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getUri() {
			return getName();
		}

	}

}