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
@ImplementationClass(CDLActivityReference.CDLActivityReferenceImpl.class)
@XMLElement(xmlTag = "CDLActivityReference")
public interface CDLActivityReference extends CDLActivity {
	
	public static final String ACTIVITY_REFERENCE_KEY = "ActivityReference";
	
	@Getter(value=ACTIVITY_REFERENCE_KEY, ignoreType=true)
	public obp.cdl.ActivityReference getCDLActivityReference();
	@Setter(ACTIVITY_REFERENCE_KEY)
	public void setCDLActivityReference(obp.cdl.ActivityReference cdlActivityReference);
	
	@PropertyIdentifier(type = String.class)
	public static final String REFERENCED_ACTIVITY_KEY = "referencedActivity";
	
	@Getter(value = REFERENCED_ACTIVITY_KEY)
	public CDLActivity getReferencedActivity();

	@Setter(value = REFERENCED_ACTIVITY_KEY)
	public void setReferencedActivity(CDLActivity initialActivity);
	
	public static abstract class CDLActivityReferenceImpl extends CDLObjectImpl implements CDLActivityReference {
		
		public CDLActivityReferenceImpl() {
			// TODO Auto-generated constructor stub
		}
		
	}

}