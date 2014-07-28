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

import org.openflexo.model.annotations.Adder;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Remover;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.model.annotations.Getter.Cardinality;

import Parser.ConfigData;

@ModelEntity
@ImplementationClass(FlexoTraceOBPConfigData.FlexoTraceOBPConfigDataImpl.class)
@XMLElement(xmlTag = "FlexoTraceOBPConfigData")
public interface FlexoTraceOBPConfigData extends FlexoTraceOBPObject{

	public static final String CONFIG_DATA_KEY = "configData";
	
	@PropertyIdentifier(type = List.class)
	public static final String PROCESS_KEY = "process";
	
	@Getter(value=CONFIG_DATA_KEY, ignoreType=true)
	public ConfigData getConfigData();
	@Setter(CONFIG_DATA_KEY)
	public void setConfigData(ConfigData configData);
	
	@Getter(value = PROCESS_KEY, cardinality = Cardinality.LIST)
	public List<FlexoTraceOBPProcess> getFlexoProcess();

	@Setter(PROCESS_KEY)
	public void setFlexoProcess(List<FlexoTraceOBPProcess> flexoProcess);

	@Adder(PROCESS_KEY)
	public void addToFlexoProcess(FlexoTraceOBPProcess flexoProcess);

	@Remover(PROCESS_KEY)
	public void removeFromFlexoProcess(FlexoTraceOBPProcess flexoProcess);

	public static abstract class FlexoTraceOBPConfigDataImpl extends FlexoTraceOBPObjectImpl implements FlexoTraceOBPConfigData {

		public FlexoTraceOBPConfigDataImpl() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getUri() {
			return getName();
		}

	}

}