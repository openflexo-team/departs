/*
 * (c) Copyright 2010-2011 AgileBirds
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
package org.openflexo.module.traceanalysis.widget;

import java.util.logging.Logger;

import org.openflexo.module.traceanalysis.controller.TraceAnalysisController;
import org.openflexo.module.traceanalysis.model.ConfigurationMask;
import org.openflexo.rm.Resource;
import org.openflexo.rm.ResourceLocator;
import org.openflexo.view.FIBBrowserView;

@SuppressWarnings("serial")
public class FIBConfigurationMaskBrowser extends FIBBrowserView<ConfigurationMask> {
	static final Logger logger = Logger.getLogger(FIBConfigurationMaskBrowser.class.getPackage().getName());

	public static final Resource FIB_FILE = ResourceLocator.locateResource("Fib/Widget/FIBConfigurationMaskBrowser.fib");

	public FIBConfigurationMaskBrowser(ConfigurationMask configurationMask, TraceAnalysisController controller) {
		super(configurationMask, controller, FIB_FILE);
		// System.out.println("Showing browser with " + project);
	}

	public void setConfigurationMask(ConfigurationMask configurationMask) {
		setDataObject(configurationMask);
	}
}