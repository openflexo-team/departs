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

package org.openflexo.technologyadapter.fiacre.gui;

import java.util.logging.Logger;

import javax.swing.ImageIcon;

import org.openflexo.foundation.technologyadapter.TechnologyObject;
import org.openflexo.rm.ResourceLocator;
import org.openflexo.technologyadapter.fiacre.model.FiacreComponent;
import org.openflexo.technologyadapter.fiacre.model.FiacreFifo;
import org.openflexo.technologyadapter.fiacre.model.FiacreProcess;
import org.openflexo.technologyadapter.fiacre.model.FiacreProgram;
import org.openflexo.technologyadapter.fiacre.model.FiacreState;
import org.openflexo.technologyadapter.fiacre.model.FiacreVariable;
import org.openflexo.toolbox.ImageIconResource;

public class FiacreIconLibrary {
	private static final Logger logger = Logger.getLogger(FiacreIconLibrary.class.getPackage().getName());

	public static final ImageIcon FIACRE_TECHNOLOGY_BIG_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/fiacre_big.png"));
	public static final ImageIcon FIACRE_TECHNOLOGY_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/fiacre_small.png"));
	public static final ImageIcon FIACRE_FILE_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/fiacre_small.png"));

	public static final ImageIcon FIACRE_PROGRAM_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/program16x16.png"));
	public static final ImageIcon FIACRE_VARIABLE_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/variable16x16.png"));
	public static final ImageIcon FIACRE_PROCESS_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/process16x16.png"));
	public static final ImageIcon FIACRE_COMPONENT_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/component16x16.png"));
	public static final ImageIcon FIACRE_STATE_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/state16x16.png"));
	public static final ImageIcon FIACRE_FIFO_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/fifo16x16.png"));
	
	public static ImageIcon iconForObject(Class<? extends TechnologyObject> objectClass) {
		if (FiacreProgram.class.isAssignableFrom(objectClass)) {
			return FIACRE_TECHNOLOGY_ICON;
		} else if (FiacreProcess.class.isAssignableFrom(objectClass)) {
			return FIACRE_PROCESS_ICON;
		} else if (FiacreComponent.class.isAssignableFrom(objectClass)) {
			return FIACRE_COMPONENT_ICON;
		} else if (FiacreState.class.isAssignableFrom(objectClass)) {
			return FIACRE_STATE_ICON;
		} else if (FiacreFifo.class.isAssignableFrom(objectClass)) {
			return FIACRE_FIFO_ICON;
		}else if (FiacreVariable.class.isAssignableFrom(objectClass)) {
			return FIACRE_VARIABLE_ICON;
		} else if (FiacreProgram.class.isAssignableFrom(objectClass)) {
			return FIACRE_PROGRAM_ICON;
		}
		return null;
	}
}
