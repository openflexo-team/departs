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

import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;

@ModelEntity
@ImplementationClass(CDLProcessID.CDLProcessIDImpl.class)
@XMLElement(xmlTag = "CDLProcessID")
public interface CDLProcessID extends CDLObject {

	// @PropertyIdentifier(type = obp.cdl.ProcessId.class)
	public static final String PROCESS_ID_KEY = "processID";

	// @Getter(value = PROCESS_ID_KEY)
	public obp.cdl.ProcessId getCDLProcessID();

	// @Setter(value = PROCESS_ID_KEY)
	public void setCDLProcessID(obp.cdl.ProcessId cdlProcessID);

	public static abstract class CDLProcessIDImpl extends CDLObjectImpl implements CDLProcessID {

		@Override
		public String getUri() {
			return getName();
		}

	}

}