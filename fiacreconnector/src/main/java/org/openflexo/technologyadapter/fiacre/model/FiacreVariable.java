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
@ImplementationClass(FiacreVariable.FiacreVariableImpl.class)
@XMLElement(xmlTag = "FiacreVariable")
public interface FiacreVariable extends FiacreObject {

	public static final String FIACRE_VARIABLE_KEY = "FiacreVariable";
	
	public static final String FIACRE_PROCESS_KEY = "FiacreProcess";

	@Getter(value=FIACRE_VARIABLE_KEY, ignoreType=true)
	public obp.fiacre.model.LocalVariable getFiacreVariable();
	@Setter(FIACRE_VARIABLE_KEY)
	public void setFiacreVariable(obp.fiacre.model.LocalVariable fiacreVariable);
	
	@Getter(value=FIACRE_PROCESS_KEY, inverse=FiacreProcess.FIACRE_VARIABLES_KEY)
	public FiacreProcess getFiacreProcess();
	
	@Setter(FIACRE_PROCESS_KEY)
	public void setFiacreProcess(FiacreProcess fiacreProcess);
	
	public String getType();
	
	public static abstract class FiacreVariableImpl extends FiacreObjectImpl implements FiacreVariable {

		public FiacreVariableImpl() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getUri() {
			return getName();
		}
		
		@Override
		public String getName() {
			return getFiacreVariable().getName();
		}

		@Override
		public String getType() {
			return getFiacreVariable().getType().toString();
		}
		
	}

}