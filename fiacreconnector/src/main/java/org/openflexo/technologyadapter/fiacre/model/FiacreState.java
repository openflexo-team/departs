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

import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;

@ModelEntity
@ImplementationClass(FiacreState.FiacreStateImpl.class)
@XMLElement(xmlTag = "FiacreState")
public interface FiacreState extends FiacreObject {

	public static final String FIACRE_STATE_KEY = "FiacreState";
	public static final String FIACRE_PROCESS_KEY = "FiacreProcess";

	@Getter(value=FIACRE_STATE_KEY, ignoreType=true)
	public obp.fiacre.model.State getFiacreState();
	@Setter(FIACRE_STATE_KEY)
	public void setFiacreState(obp.fiacre.model.State fiacreState);

	@Getter(value=FIACRE_PROCESS_KEY, ignoreType=true, inverse= FiacreProcess.FIACRE_STATES_KEY)
	public FiacreProcess getFiacreProcess();
	@Setter(FIACRE_PROCESS_KEY)
	public void setFiacreProcess(FiacreProcess fiacreProcess);

	public static abstract class FiacreStateImpl extends FiacreObjectImpl implements FiacreState {

		public FiacreStateImpl() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getUri() {
			return getName();
		}
		
		@Override
		public String getName() {
			return getFiacreState().getName();
		}

	}

}