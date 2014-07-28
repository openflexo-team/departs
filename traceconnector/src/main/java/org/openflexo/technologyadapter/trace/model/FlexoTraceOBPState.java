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
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;

@ModelEntity
@ImplementationClass(FlexoTraceOBPState.FlexoTraceOBPStateImpl.class)
@XMLElement(xmlTag = "FlexoTraceOBPState")
public interface FlexoTraceOBPState extends FlexoTraceOBPObject{
	
	@PropertyIdentifier(type = FlexoTraceOBPProcess.class)
	public static final String PROCESS_KEY = "process";
	
	@Getter(value = PROCESS_KEY, inverse = FlexoTraceOBPProcess.PROCESS_STATE_KEY)
	public FlexoTraceOBPProcess getProcess();
	@Setter(PROCESS_KEY)
	public void setProcess(FlexoTraceOBPProcess process);

	public static abstract class FlexoTraceOBPStateImpl extends FlexoTraceOBPObjectImpl implements FlexoTraceOBPState {

		public FlexoTraceOBPStateImpl() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getUri() {
			return getName();
		}
		
		@Override
		public String getValue() {
			return getName();
		}

	}

}