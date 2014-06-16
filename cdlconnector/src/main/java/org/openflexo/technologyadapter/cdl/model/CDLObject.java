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

package org.openflexo.technologyadapter.cdl.model;

import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.technologyadapter.TechnologyObject;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.cdl.CDLTechnologyAdapter;

@ModelEntity
@ImplementationClass(CDLObject.CDLObjectImpl.class)
@XMLElement(xmlTag = "CDLObject")
public interface CDLObject extends FlexoObject, TechnologyObject<CDLTechnologyAdapter> {

	@PropertyIdentifier(type = String.class)
	public static final String NAME_KEY = "name";

	@PropertyIdentifier(type = String.class)
	public static final String URI_KEY = "uri";

	@Getter(value = NAME_KEY)
	public String getName();

	@Setter(value = NAME_KEY)
	public void setName(String name);

	@Getter(value = URI_KEY)
	public String getUri();

	@Setter(value = URI_KEY)
	public void setUri(String uri);

	@Override
	public CDLTechnologyAdapter getTechnologyAdapter();

	public void setTechnologyAdapter(CDLTechnologyAdapter technologyAdapter);

	public static abstract class CDLObjectImpl extends FlexoObjectImpl implements CDLObject {

		private CDLTechnologyAdapter technologyAdapter;

		@Override
		public void setTechnologyAdapter(CDLTechnologyAdapter technologyAdapter) {
			this.technologyAdapter = technologyAdapter;
		}

		@Override
		public CDLTechnologyAdapter getTechnologyAdapter() {
			return technologyAdapter;
		}

	}

}
