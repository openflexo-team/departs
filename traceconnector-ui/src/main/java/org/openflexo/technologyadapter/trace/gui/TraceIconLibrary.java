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
import org.openflexo.technologyadapter.trace.model.FlexoTraceOBPConfigData;
import org.openflexo.technologyadapter.trace.model.FlexoTraceOBPTransition;
import org.openflexo.technologyadapter.trace.model.FlexoTraceOBP;
import org.openflexo.toolbox.ImageIconResource;

public class TraceIconLibrary {
	private static final Logger logger = Logger.getLogger(TraceIconLibrary.class.getPackage().getName());

	public static final ImageIcon TRACE_TECHNOLOGY_BIG_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/trace_big.png"));
	public static final ImageIcon TRACE_TECHNOLOGY_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/trace_small.png"));
	public static final ImageIcon TRACE_FILE_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/trace_file.png"));

	public static final ImageIcon TRACE_CONFIG_DATA_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/trace_config_data.png"));
	public static final ImageIcon TRACE_TRANSITIONS_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/trace_transitions.png"));
	
	public static ImageIcon iconForObject(Class<? extends TechnologyObject> objectClass) {
		if (FlexoTraceOBP.class.isAssignableFrom(objectClass)) {
			return TRACE_TECHNOLOGY_ICON;
		}  else if (FlexoTraceOBPTransition.class.isAssignableFrom(objectClass)) {
			return TRACE_TRANSITIONS_ICON;
		} else if (FlexoTraceOBPConfigData.class.isAssignableFrom(objectClass)) {
			return TRACE_CONFIG_DATA_ICON;
		}
		return null;
	}
}
