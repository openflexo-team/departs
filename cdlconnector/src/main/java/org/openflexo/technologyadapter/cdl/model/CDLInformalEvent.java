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

import obp.event.Informal;

import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;

@ModelEntity
@ImplementationClass(CDLInformalEvent.CDLInformalEventImpl.class)
@XMLElement(xmlTag = "CDLInformalEvent")
public interface CDLInformalEvent extends CDLEvent {

	public static final String INFORMAL_EVENT_KEY = "informalEvent";
	
	@Getter(value=INFORMAL_EVENT_KEY, ignoreType=true)
	public Informal getInformalEvent();
	@Setter(INFORMAL_EVENT_KEY)
	public void setInformalEvent(Informal nformalEvent);
	
	@PropertyIdentifier(type = CDLProcessID.class)
	public static final String PROCESS_ID_FROM_KEY = "processIDfrom";
	
	@PropertyIdentifier(type = String.class)
	public static final String TAG_KEY = "tag";

	@Getter(value = PROCESS_ID_FROM_KEY)
	public CDLProcessID getProcessIDFrom();

	@Setter(value = PROCESS_ID_FROM_KEY)
	public void setProcessIDFrom(CDLProcessID processIDFrom);

	@Getter(value = TAG_KEY)
	public String getTag();
	
	@Setter(value = TAG_KEY)
	public void setTag(String tag);

	public static abstract class CDLInformalEventImpl extends CDLEventImpl implements CDLInformalEvent {

		public CDLInformalEventImpl() {
			// TODO Auto-generated constructor stub
		}
		
	}

}