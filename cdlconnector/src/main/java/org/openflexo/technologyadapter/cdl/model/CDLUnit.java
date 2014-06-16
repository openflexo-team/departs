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

import java.util.List;

import obp.cdl.EventDeclaration;
import obp.event.Event;
import obp.event.Input;
import obp.event.Output;
import obp.event.Synchronous;

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
import org.openflexo.technologyadapter.cdl.model.CDLEvent.EventKind;
import org.openflexo.technologyadapter.cdl.model.io.CDLModelConverter;
import org.openflexo.technologyadapter.cdl.rm.CDLUnitResource;

@ModelEntity
@ImplementationClass(CDLUnit.CDLUnitImpl.class)
@XMLElement(xmlTag = "CDLUnit")
public interface CDLUnit extends CDLObject, ResourceData<CDLUnit> {

	// @PropertyIdentifier(type = obp.cdl.CDLUnit.class)
	public static final String CDL_UNIT_KEY = "cdlUnit";
	@PropertyIdentifier(type = String.class)
	public static final String INITIAL_ACTIVITY_KEY = "initialActivity";
	@PropertyIdentifier(type = List.class)
	public static final String ACTIVITIES_KEY = "activities";
	@PropertyIdentifier(type = List.class)
	public static final String EVENTS_KEY = "events";
	@PropertyIdentifier(type = List.class)
	public static final String PROPERTIES_KEY = "properties";

	// @Getter(value = CDL_UNIT_KEY)
	public obp.cdl.CDLUnit getCDLUnit();

	// @Setter(value = CDL_UNIT_KEY)
	public void setCDLUnit(obp.cdl.CDLUnit cdlUnit);

	@Getter(value = INITIAL_ACTIVITY_KEY)
	public CDLActivity getInitialActivity();

	@Setter(value = INITIAL_ACTIVITY_KEY)
	public void setInitialActivity(CDLActivity initialActivity);

	@Getter(value = ACTIVITIES_KEY, cardinality = Cardinality.LIST)
	public List<CDLActivity> getCDLActivities();

	@Setter(ACTIVITIES_KEY)
	public void setCDLActivities(List<CDLActivity> cdlActivity);

	@Adder(ACTIVITIES_KEY)
	public void addToCDLActivities(CDLActivity cdlActivity);

	@Remover(ACTIVITIES_KEY)
	public void removeFromCDLActivities(CDLActivity cdlActivity);

	@Getter(value = EVENTS_KEY, cardinality = Cardinality.LIST)
	public List<CDLEvent> getCDLEvents();

	@Setter(EVENTS_KEY)
	public void setCDLEvents(List<CDLEvent> cdlEvent);

	@Adder(EVENTS_KEY)
	public void addToCDLEvents(CDLEvent cdlEvent);

	@Remover(EVENTS_KEY)
	public void removeFromCDLEvents(CDLEvent cdlEvent);

	@Getter(value = PROPERTIES_KEY, cardinality = Cardinality.LIST)
	public List<CDLProperty> getCDLProperties();

	@Setter(PROPERTIES_KEY)
	public void setCDLProperties(List<CDLProperty> cdlProperty);

	@Adder(PROPERTIES_KEY)
	public void addToCDLProperties(CDLProperty cdlProperty);

	@Remover(PROPERTIES_KEY)
	public void removeFromCDLPorperties(CDLProperty cdlProperty);

	public String getCode();

	public CDLModelConverter getConverter();

	public void setConverter(CDLModelConverter converter);

	public CDLEvent createCDLEvent(EventKind evtKind, CDLProcessID fromPID, CDLProcessID toPID, String message, String name);

	public static abstract class CDLUnitImpl extends CDLObjectImpl implements CDLUnit {

		public CDLUnitImpl() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getCode() {
			return "test";
		}

		@Override
		public String getUri() {
			return getName();
		}

		@Override
		public CDLEvent createCDLEvent(EventKind evtKind, CDLProcessID fromPID, CDLProcessID toPID, String message, String name) {
			CDLEvent cdlEvent = null;
			EventDeclaration evDecl = new EventDeclaration();
			Event event = null;
			switch (evtKind) {
			case INPUT:
				Input input = new Input();
				input.setFrom(fromPID.getCDLProcessID());
				input.setTo(toPID.getCDLProcessID());
				event = input;
				break;
			case OUTPUT:
				Output output = new Output();
				output.setFrom(fromPID.getCDLProcessID());
				output.setTo(toPID.getCDLProcessID());
				event = output;
				break;
			case SYNC:
				Synchronous sync = new Synchronous();
				sync.setFrom(fromPID.getCDLProcessID());
				sync.setTo(toPID.getCDLProcessID());
				event = sync;
				break;
			case GAMMA:
			case INFORMAL:
			case PREDICATE:
			}
			if (name != null) {
				evDecl.setIs(event);
				evDecl.setName(name);
			}

			this.getCDLUnit().addDeclaration(evDecl);
			cdlEvent = ((CDLUnitResource) this.getResource()).getConverter().convertCDLEvent(evDecl, this, getTechnologyAdapter());

			return cdlEvent;
		}

	}

}