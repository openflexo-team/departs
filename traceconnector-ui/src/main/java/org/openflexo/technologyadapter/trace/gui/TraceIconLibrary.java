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

package org.openflexo.technologyadapter.trace.gui;

import java.util.logging.Logger;

import javax.swing.ImageIcon;

import org.openflexo.foundation.technologyadapter.TechnologyObject;
import org.openflexo.rm.ResourceLocator;
import org.openflexo.technologyadapter.trace.model.OBPTrace;
import org.openflexo.technologyadapter.trace.model.OBPTraceComponent;
import org.openflexo.technologyadapter.trace.model.OBPTraceConfiguration;
import org.openflexo.technologyadapter.trace.model.OBPTraceContextInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceProcessInstance;
import org.openflexo.technologyadapter.trace.model.OBPTracePropertyInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceState;
import org.openflexo.technologyadapter.trace.model.OBPTraceTransition;
import org.openflexo.technologyadapter.trace.model.OBPTraceVariable;
import org.openflexo.toolbox.ImageIconResource;

public class TraceIconLibrary {
	private static final Logger logger = Logger.getLogger(TraceIconLibrary.class.getPackage().getName());

	public static final ImageIcon TRACE_TECHNOLOGY_BIG_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/trace_big.png"));
	public static final ImageIcon TRACE_TECHNOLOGY_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/trace_small.png"));
	public static final ImageIcon TRACE_FILE_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/trace_file.png"));

	public static final ImageIcon TRACE_CONFIG_DATA_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/trace_config_data.png"));
	public static final ImageIcon TRACE_TRANSITIONS_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/trace_transitions.png"));
	public static final ImageIcon VARIABLE_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/variable16x16.png"));
	public static final ImageIcon PROCESS_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/process16x16.png"));
	public static final ImageIcon COMPONENT_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/component16x16.png"));
	public static final ImageIcon STATE_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/state16x16.png"));
	public static final ImageIcon FIFO_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/fifo16x16.png"));
	
	public static final ImageIcon CDL_ACTIVITY_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/cdl_activity.png"));
	public static final ImageIcon CDL_PROPERTY_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/cdl_property.png"));
	public static final ImageIcon CDL_STATE_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/cdl_state.png"));
	
	public static final ImageIcon CDL_NORMAL_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/cdl_normal.png"));
	public static final ImageIcon CDL_CUT_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/cdl_cut.png"));
	public static final ImageIcon CDL_SUCCESS_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/cdl_success.png"));
	public static final ImageIcon CDL_REJECT_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/cdl_reject.png"));

	
	
	public static ImageIcon iconForObject(Class<? extends TechnologyObject> objectClass) {
		if (OBPTrace.class.isAssignableFrom(objectClass)) {
			return TRACE_TECHNOLOGY_ICON;
		}  else if (OBPTraceTransition.class.isAssignableFrom(objectClass)) {
			return TRACE_TRANSITIONS_ICON;
		} else if (OBPTraceConfiguration.class.isAssignableFrom(objectClass)) {
			return TRACE_CONFIG_DATA_ICON;
		}else if (OBPTraceProcessInstance.class.isAssignableFrom(objectClass)) {
			return PROCESS_ICON;
		} else if (OBPTraceComponent.class.isAssignableFrom(objectClass)) {
			return COMPONENT_ICON;
		} else if (OBPTraceState.class.isAssignableFrom(objectClass)) {
			return STATE_ICON;
		} else if (OBPTraceVariable.class.isAssignableFrom(objectClass)) {
			return VARIABLE_ICON;
		} else if (OBPTraceContextInstance.class.isAssignableFrom(objectClass)) {
			return CDL_ACTIVITY_ICON;
		} else if (OBPTracePropertyInstance.class.isAssignableFrom(objectClass)) {
			return CDL_PROPERTY_ICON;
		} 
		return null;
	}
}
