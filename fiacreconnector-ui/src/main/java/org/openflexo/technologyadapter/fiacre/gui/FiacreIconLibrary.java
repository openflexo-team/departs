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
import org.openflexo.technologyadapter.fiacre.model.FiacreProgram;
import org.openflexo.toolbox.ImageIconResource;

public class FiacreIconLibrary {
	private static final Logger logger = Logger.getLogger(FiacreIconLibrary.class.getPackage().getName());

	public static final ImageIcon FIACRE_TECHNOLOGY_BIG_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/fiacre_big.png"));
	public static final ImageIcon FIACRE_TECHNOLOGY_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/fiacre_small.png"));
	public static final ImageIcon FIACRE_FILE_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/fiacre_small.png"));

	public static ImageIcon iconForObject(Class<? extends TechnologyObject> objectClass) {
		if (FiacreProgram.class.isAssignableFrom(objectClass)) {
			return FIACRE_TECHNOLOGY_ICON;
		}
		return null;
	}
}
