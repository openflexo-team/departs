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

import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;

@ModelEntity
@ImplementationClass(CDLCommunicationOPEvent.CDLCommunicationOPEventImpl.class)
@XMLElement(xmlTag = "CDLCommunicationOPEvent")
public interface CDLCommunicationOPEvent extends CDLEvent {

	// @PropertyIdentifier(type = obp.event.CommunicationOp.class)
	public static final String COMMUNICATION_OP_KEY = "communicationOp";
	@PropertyIdentifier(type = CDLProcessID.class)
	public static final String PROCESS_ID_FROM_KEY = "processIDfrom";
	@PropertyIdentifier(type = CDLProcessID.class)
	public static final String PROCESS_ID_TO_KEY = "processIDto";

	// @Getter(value = COMMUNICATION_OP_KEY)
	public obp.event.CommunicationOp getCommunicationOp();

	// @Setter(value = COMMUNICATION_OP_KEY)
	public void setCommunicationOp(obp.event.CommunicationOp communicationOp);

	@Getter(value = PROCESS_ID_FROM_KEY)
	public CDLProcessID getProcessIDFrom();

	@Setter(value = PROCESS_ID_FROM_KEY)
	public void setProcessIDFrom(CDLProcessID processIDFrom);

	@Getter(value = PROCESS_ID_TO_KEY)
	public CDLProcessID getProcessIDTo();

	@Setter(value = PROCESS_ID_TO_KEY)
	public void setProcessIDTo(CDLProcessID processIDTo);

	public static abstract class CDLCommunicationOPEventImpl extends CDLEventImpl implements CDLCommunicationOPEvent {

		@Override
		public String getUri() {
			return getName();
		}

	}

}