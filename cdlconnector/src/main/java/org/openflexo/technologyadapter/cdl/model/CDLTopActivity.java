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

import org.openflexo.model.annotations.Adder;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Remover;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.model.annotations.Getter.Cardinality;

@ModelEntity
@ImplementationClass(CDLTopActivity.CDLTopActivityImpl.class)
@XMLElement(xmlTag = "CDLTopActivity")
public interface CDLTopActivity extends CDLActivity {
	
	@PropertyIdentifier(type = List.class)
	public static final String ACTIVITIES_KEY = "activities";
	
	@Getter(value = ACTIVITIES_KEY, cardinality = Cardinality.LIST)
	public List<CDLActivity> getCDLActivities();

	@Setter(ACTIVITIES_KEY)
	public void setCDLActivities(List<CDLActivity> cdlActivity);

	@Adder(ACTIVITIES_KEY)
	public void addToCDLActivities(CDLActivity cdlActivity);

	@Remover(ACTIVITIES_KEY)
	public void removeFromCDLActivities(CDLActivity cdlActivity);
	
	public static abstract class CDLTopActivityImpl extends CDLActivityImpl implements CDLTopActivity {

	}

}