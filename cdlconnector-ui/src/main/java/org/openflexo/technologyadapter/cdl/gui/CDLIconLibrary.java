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

package org.openflexo.technologyadapter.cdl.gui;

import java.util.logging.Logger;

import javax.swing.ImageIcon;

import org.openflexo.foundation.technologyadapter.TechnologyObject;
import org.openflexo.rm.ResourceLocator;
import org.openflexo.technologyadapter.cdl.model.CDLActivity;
import org.openflexo.technologyadapter.cdl.model.CDLActivityReference;
import org.openflexo.technologyadapter.cdl.model.CDLEvent;
import org.openflexo.technologyadapter.cdl.model.CDLEventReference;
import org.openflexo.technologyadapter.cdl.model.CDLObserverState;
import org.openflexo.technologyadapter.cdl.model.CDLObserverStateCut;
import org.openflexo.technologyadapter.cdl.model.CDLObserverStateNormal;
import org.openflexo.technologyadapter.cdl.model.CDLObserverStateReject;
import org.openflexo.technologyadapter.cdl.model.CDLObserverStateSuccess;
import org.openflexo.technologyadapter.cdl.model.CDLObserverTransition;
import org.openflexo.technologyadapter.cdl.model.CDLProcessID;
import org.openflexo.technologyadapter.cdl.model.CDLProperty;
import org.openflexo.technologyadapter.cdl.model.CDLUnit;
import org.openflexo.toolbox.ImageIconResource;

public class CDLIconLibrary {
	private static final Logger logger = Logger.getLogger(CDLIconLibrary.class.getPackage().getName());

	public static final ImageIcon CDL_TECHNOLOGY_BIG_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/cdlfile.png"));
	public static final ImageIcon CDL_TECHNOLOGY_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/cdlsmall.png"));
	public static final ImageIcon CDL_FILE_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/cdlfile.png"));

	public static final ImageIcon CDL_ACTIVITY_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/cdl_activity.png"));
	public static final ImageIcon CDL_PROPERTY_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/cdl_property.png"));
	public static final ImageIcon CDL_EVENT_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/cdl_event.png"));
	public static final ImageIcon CDL_PROCESSID_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/cdl_processid.png"));
	public static final ImageIcon CDL_STATE_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/cdl_state.png"));
	public static final ImageIcon CDL_TRANSITION_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/cdl_transition.png"));
	public static final ImageIcon CDL_REF_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/cdl_ref.png"));

	public static final ImageIcon CDL_NORMAL_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/cdl_normal.png"));
	public static final ImageIcon CDL_CUT_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/cdl_cut.png"));
	public static final ImageIcon CDL_SUCCESS_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/cdl_success.png"));
	public static final ImageIcon CDL_REJECT_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/cdl_reject.png"));

	
	public static ImageIcon iconForObject(Class<? extends TechnologyObject> objectClass) {
		if (CDLUnit.class.isAssignableFrom(objectClass)) {
			return CDL_TECHNOLOGY_ICON;
		} else if (CDLObserverStateNormal.class.isAssignableFrom(objectClass)) {
			return CDL_NORMAL_ICON;
		} else if (CDLObserverStateReject.class.isAssignableFrom(objectClass)) {
			return CDL_REJECT_ICON;
		} else if (CDLObserverStateSuccess.class.isAssignableFrom(objectClass)) {
			return CDL_SUCCESS_ICON;
		} else if (CDLObserverStateCut.class.isAssignableFrom(objectClass)) {
			return CDL_CUT_ICON;
		} else if (CDLObserverTransition.class.isAssignableFrom(objectClass)) {
			return CDL_TRANSITION_ICON;
		} else if (CDLActivityReference.class.isAssignableFrom(objectClass)) {
			return CDL_REF_ICON;
		} else if (CDLEventReference.class.isAssignableFrom(objectClass)) {
			return CDL_REF_ICON;
		} else if (CDLEvent.class.isAssignableFrom(objectClass)) {
			return CDL_EVENT_ICON;
		} else if (CDLActivity.class.isAssignableFrom(objectClass)) {
			return CDL_ACTIVITY_ICON;
		} else if (CDLProperty.class.isAssignableFrom(objectClass)) {
			return CDL_PROPERTY_ICON;
		} else if (CDLProcessID.class.isAssignableFrom(objectClass)) {
			return CDL_PROCESSID_ICON;
		}
		return null;
	}
}
