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

package org.openflexo.technologyadapter.fiacre.model;

import java.util.List;
import java.util.Vector;

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

@ModelEntity
@ImplementationClass(FiacreComponent.FiacreComponentImpl.class)
@XMLElement(xmlTag = "FiacreComponent")
public interface FiacreComponent extends FiacreObject, ResourceData<FiacreComponent> {

	// @PropertyIdentifier(type = obp.cdl.FiacreComponent.class)
	public static final String FIACRE_COMPONENT_KEY = "FiacreComponent";
	@PropertyIdentifier(type = boolean.class)
	public static final String IS_ROOT_KEY = "isRoot";
	@PropertyIdentifier(type = FiacreProcess.class, cardinality = Cardinality.LIST)
	public static final String FIACRE_PROCESS_KEY = "FiacreProcesses";

	// @Getter(value = CDL_UNIT_KEY)
	public obp.fiacre.model.ComponentDecl getFiacreComponent();

	// @Setter(value = CDL_UNIT_KEY)
	public void setFiacreComponent(obp.fiacre.model.ComponentDecl fiacreComponent);

	@Getter(value = IS_ROOT_KEY, defaultValue = "false")
	public boolean getIsRoot();

	@Setter(IS_ROOT_KEY)
	public void setIsRoot(boolean isRoot);

	@Getter(value = FIACRE_PROCESS_KEY, cardinality = Cardinality.LIST)
	public List<FiacreProcess> getFiacreProcesses();

	@Setter(FIACRE_PROCESS_KEY)
	public void setFiacreProcesses(Vector<FiacreProcess> fiacreProcesses);

	@Adder(FIACRE_PROCESS_KEY)
	public void addToFiacreProcesses(FiacreProcess fiacreProcess);

	@Remover(FIACRE_PROCESS_KEY)
	public void removeFromFiacreProcesses(FiacreProcess fiacreProcess);

	public static abstract class FiacreComponentImpl extends FiacreObjectImpl implements FiacreComponent {

		public FiacreComponentImpl() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getFiacreCode() {
			return "Component " + getName() + " " + getIsRoot() + "is";
		}

		@Override
		public String getUri() {
			return getName();
		}

	}

}