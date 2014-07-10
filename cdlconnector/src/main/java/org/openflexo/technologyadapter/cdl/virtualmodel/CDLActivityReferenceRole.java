/*
 * (c) Copyright 2013- Openflexo
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

package org.openflexo.technologyadapter.cdl.virtualmodel;

import java.lang.reflect.Type;

import org.openflexo.foundation.viewpoint.FlexoRole;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.cdl.model.CDLActivityReference;

@ModelEntity
@ImplementationClass(CDLActivityReferenceRole.CDLActivityReferenceRoleImpl.class)
@XMLElement
public interface CDLActivityReferenceRole extends FlexoRole<CDLActivityReference> {

	public static abstract class CDLActivityReferenceRoleImpl extends FlexoRoleImpl<CDLActivityReference> implements CDLActivityReferenceRole {

		@Override
		public Type getType() {
			return CDLActivityReference.class;
		}

		@Override
		public String getPreciseType() {
			return CDLActivityReference.class.getSimpleName();
		}

	}
}
