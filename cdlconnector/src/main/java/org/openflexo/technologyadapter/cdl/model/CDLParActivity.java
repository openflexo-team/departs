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
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;

@ModelEntity
@ImplementationClass(CDLParActivity.CDLParActivityImpl.class)
@XMLElement(xmlTag = "CDLParActivity")
public interface CDLParActivity extends CDLTopActivity {
	
	public static final String PAR_ACTIVITY_KEY = "ParActivity";
	
	@Getter(value = PAR_ACTIVITY_KEY, ignoreType=true)
	public obp.cdl.ParActivity getCDLParActivity();
	@Setter(PAR_ACTIVITY_KEY)
	public void setCDLParActivity(obp.cdl.ParActivity cdlParActivity);
	
	public static abstract class CDLParActivityImpl extends CDLTopActivityImpl implements CDLParActivity {
		
		public CDLParActivityImpl() {
			// TODO Auto-generated constructor stub
		}
	}

}