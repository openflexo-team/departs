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

import org.openflexo.model.annotations.Adder;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.Remover;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.model.annotations.Getter.Cardinality;

@ModelEntity
@ImplementationClass(FiacreProcess.FiacreProcessImpl.class)
@XMLElement(xmlTag = "FiacreProcess")
public interface FiacreProcess extends FiacreObject{

	public static final String FIACRE_PROCESS_KEY = "FiacreProcess";
	
	public static final String FIACRE_FIFOS_KEY = "FiacreFifos";
	
	public static final String FIACRE_STATES_KEY = "FiacreStates";

	public obp.fiacre.model.ProcessDecl getFiacreProcess();

	public void setFiacreProcess(obp.fiacre.model.ProcessDecl fiacreProcess);
	
	@Getter(value = FIACRE_FIFOS_KEY, cardinality = Cardinality.LIST)
	public List<FiacreFifo> getFiacreFifos();

	@Setter(FIACRE_FIFOS_KEY)
	public void setFiacreFifos(Vector<FiacreFifo> fiacreFifos);

	@Adder(FIACRE_FIFOS_KEY)
	public void addToFiacreFifos(FiacreFifo fiacreFifo);

	@Remover(FIACRE_FIFOS_KEY)
	public void removeFromFiacreFifos(FiacreFifo fiacreFifo);
	
	@Getter(value = FIACRE_STATES_KEY, cardinality = Cardinality.LIST)
	public List<FiacreState> getFiacreStates();

	@Setter(FIACRE_STATES_KEY)
	public void setFiacreStates(Vector<FiacreState> fiacreStates);

	@Adder(FIACRE_STATES_KEY)
	public void addToFiacreStates(FiacreState fiacreState);

	@Remover(FIACRE_STATES_KEY)
	public void removeFromFiacreStates(FiacreState fiacreState);

	public static abstract class FiacreProcessImpl extends FiacreObjectImpl implements FiacreProcess {

		public FiacreProcessImpl() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getFiacreCode() {
			return "Process" + getName();
		}

		@Override
		public String getUri() {
			return getName();
		}

	}

}