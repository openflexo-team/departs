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
import org.openflexo.technologyadapter.fiacre.model.io.FiacreProgramConverter;

@ModelEntity
@ImplementationClass(FiacreProgram.FiacreProgramImpl.class)
@XMLElement(xmlTag = "FiacreProgram")
public interface FiacreProgram extends FiacreObject, ResourceData<FiacreProgram> {

	public static final String FIACRE_PROGRAM_KEY = "FiacreProgram";
	@PropertyIdentifier(type = FiacreProcess.class, cardinality = Cardinality.LIST)
	public static final String FIACRE_PROCESS_KEY = "FiacreProcesses";
	@PropertyIdentifier(type = FiacreComponent.class, cardinality = Cardinality.LIST)
	public static final String FIACRE_COMPONENTS_KEY = "FiacreComponents";

	public obp.fiacre.model.Program getFiacreProgram();

	public void setFiacreProgram(obp.fiacre.model.Program fiacreProgram);

	@Getter(value = FIACRE_PROCESS_KEY, cardinality = Cardinality.LIST)
	public List<FiacreProcess> getFiacreProcesses();

	@Setter(FIACRE_PROCESS_KEY)
	public void setFiacreProcesses(Vector<FiacreProcess> fiacreProcesses);

	@Adder(FIACRE_PROCESS_KEY)
	public void addToFiacreProcesses(FiacreProcess fiacreProcess);

	@Remover(FIACRE_PROCESS_KEY)
	public void removeFromFiacreProcesses(FiacreProcess fiacreProcess);
	
	@Getter(value = FIACRE_COMPONENTS_KEY, cardinality = Cardinality.LIST)
	public List<FiacreComponent> getFiacreComponents();

	@Setter(FIACRE_COMPONENTS_KEY)
	public void setFiacreComponents(Vector<FiacreComponent> fiacreComponents);

	@Adder(FIACRE_COMPONENTS_KEY)
	public void addToFiacreComponents(FiacreComponent fiacreComponent);

	@Remover(FIACRE_COMPONENTS_KEY)
	public void removeFromFiacreComponents(FiacreComponent fiacreComponent);

	public FiacreProgramConverter getConverter();

	public void setConverter(FiacreProgramConverter converter);

	public static abstract class FiacreProgramImpl extends FiacreObjectImpl implements FiacreProgram {

		public FiacreProgramImpl() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getFiacreCode() {
			return "// Program " + getName();
		}

		@Override
		public String getUri() {
			return getName();
		}

	}

}