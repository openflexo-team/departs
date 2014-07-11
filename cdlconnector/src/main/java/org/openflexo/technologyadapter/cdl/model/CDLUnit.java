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
import org.openflexo.technologyadapter.cdl.model.io.CDLModelConverter;

@ModelEntity
@ImplementationClass(CDLUnit.CDLUnitImpl.class)
@XMLElement(xmlTag = "CDLUnit")
public interface CDLUnit extends CDLObject, ResourceData<CDLUnit> {

	public static final String CDL_UNIT_KEY = "cdlUnit";
	@PropertyIdentifier(type = String.class)
	public static final String INITIAL_ACTIVITY_KEY = "initialActivity";
	@PropertyIdentifier(type = String.class)
	public static final String CDL_MAIN_KEY = "cdlMain";
	@PropertyIdentifier(type = List.class)
	public static final String PROPERTIES_KEY = "cdlProperties";
	@PropertyIdentifier(type = List.class)
	public static final String EVENTS_KEY = "events";
	@PropertyIdentifier(type = List.class)
	public static final String ACTIVITIES_KEY = "activities";

	// @Getter(value = CDL_UNIT_KEY)
	public obp.cdl.CDLUnit getCDLUnit();

	// @Setter(value = CDL_UNIT_KEY)
	public void setCDLUnit(obp.cdl.CDLUnit cdlUnit);

	@Getter(value = INITIAL_ACTIVITY_KEY)
	public CDLTopActivity getInitialActivity();

	@Setter(value = INITIAL_ACTIVITY_KEY)
	public void setInitialActivity(CDLTopActivity initialActivity);
	
	@Getter(value = CDL_MAIN_KEY)
	public CDLParActivity getCDLParActivity();

	@Setter(value = CDL_MAIN_KEY)
	public void setCDLParActivity(CDLParActivity cdlParActivity);

	@Getter(value = PROPERTIES_KEY, cardinality = Cardinality.LIST)
	public List<CDLProperty> getCDLProperties();

	@Setter(PROPERTIES_KEY)
	public void setCDLProperties(List<CDLProperty> cdlProperty);

	@Adder(PROPERTIES_KEY)
	public void addToCDLProperties(CDLProperty cdlProperty);

	@Remover(PROPERTIES_KEY)
	public void removeFromCDLProperties(CDLProperty cdlProperty);
	
	@Getter(value = EVENTS_KEY, cardinality = Cardinality.LIST)
	public List<CDLEvent> getCDLEvents();

	@Setter(EVENTS_KEY)
	public void setCDLEvents(List<CDLEvent> cdlEvent);

	@Adder(EVENTS_KEY)
	public void addToCDLEvents(CDLEvent cdlEvent);

	@Remover(EVENTS_KEY)
	public void removeFromCDLEvents(CDLEvent cdlEvent);

	@Getter(value = ACTIVITIES_KEY, cardinality = Cardinality.LIST)
	public List<CDLActivity> getCDLActivities();

	@Setter(ACTIVITIES_KEY)
	public void setCDLActivities(List<CDLActivity> cdlActivity);

	@Adder(ACTIVITIES_KEY)
	public void addToCDLActivities(CDLActivity cdlActivity);

	@Remover(ACTIVITIES_KEY)
	public void removeFromCDLActivities(CDLActivity cdlActivity);
	
	public String getCode();

	public CDLModelConverter getConverter();

	public void setConverter(CDLModelConverter converter);

	public CDLEvent createCDLEvent(CDLProcessID fromPID, CDLProcessID toPID, String message, String name);

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

	}

}