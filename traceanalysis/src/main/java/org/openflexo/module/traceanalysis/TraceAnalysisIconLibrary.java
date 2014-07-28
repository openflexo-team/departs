/*
 * (c) Copyright 2010-2011 AgileBirds
 * (c) Copyright 2012-2013 Openflexo
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
package org.openflexo.module.traceanalysis;

import javax.swing.ImageIcon;

import org.openflexo.rm.ResourceLocator;
import org.openflexo.toolbox.ImageIconResource;

public class TraceAnalysisIconLibrary {
	

	// Module icons
	public static final ImageIcon TA_SMALL_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/ta_small.png"));
	public static final ImageIcon TA_MEDIUM_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/ta_medium.png"));
	public static final ImageIcon TA_BIG_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/ta_big.png"));
	
	public static final ImageIcon MASK_SMALL_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/mask16x16.png"));
	public static final ImageIcon MASK_MEDIUM_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/mask32x32.png"));
	public static final ImageIcon MASK_LARGE_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/mask64x64.png"));
}
