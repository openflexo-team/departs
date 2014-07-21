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

import org.openflexo.foundation.resource.ResourceData;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;

import Parser.ConfigData;

@ModelEntity
@ImplementationClass(FlexoConfigData.FlexoConfigDataImpl.class)
@XMLElement(xmlTag = "FlexoConfigData")
public interface FlexoConfigData extends FlexoTraceObject, ResourceData<FlexoConfigData> {

	public static final String CONFIG_DATA_KEY = "configData";
	
	@Getter(value=CONFIG_DATA_KEY, ignoreType=true)
	public ConfigData getConfigData();
	@Setter(CONFIG_DATA_KEY)
	public void setConfigData(ConfigData configData);

	public static abstract class FlexoConfigDataImpl extends FlexoTraceObjectImpl implements FlexoConfigData {

		public FlexoConfigDataImpl() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getUri() {
			return getName();
		}

	}

}