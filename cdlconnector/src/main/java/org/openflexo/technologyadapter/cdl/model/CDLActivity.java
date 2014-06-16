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
@ImplementationClass(CDLActivity.CDLStateImpl.class)
@XMLElement(xmlTag = "CDLActivity")
public interface CDLActivity extends CDLObject {

	// @PropertyIdentifier(type = obp.cdl.Activity.class)
	public static final String ACTIVITY_KEY = "activity";

	// @Getter(value = ACTIVITY_KEY)
	public obp.cdl.Activity getCDLActivity();

	// @Setter(value = ACTIVITY_KEY)
	public void setCDLActivity(obp.cdl.Activity cdlActivity);

	public static abstract class CDLStateImpl extends CDLObjectImpl implements CDLActivity {

		@Override
		public String getUri() {
			return getName();
		}

	}

}