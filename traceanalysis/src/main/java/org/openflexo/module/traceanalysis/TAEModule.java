/*
 * (c) Copyright 2014- Openflexo
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


import java.util.logging.Logger;

import org.openflexo.ApplicationContext;
import org.openflexo.components.ProgressWindow;
import org.openflexo.localization.FlexoLocalization;
import org.openflexo.module.FlexoModule;
import org.openflexo.module.Module;
import org.openflexo.module.traceanalysis.controller.TAEController;
import org.openflexo.view.controller.FlexoController;

/**
 * DbEditor module
 * 
 * @author vincent
 */
public class TAEModule extends FlexoModule<TAEModule> {

	private static final Logger logger = Logger.getLogger(TAEModule.class.getPackage().getName());

	public static final String TAE_MODULE_SHORT_NAME = "TAE";
	public static final String TAE_MODULE_NAME = "traceAnalysis";

	public TAEModule(ApplicationContext applicationContext) {
		super(applicationContext);
		// TODO Auto-generated constructor stub
		ProgressWindow.setProgressInstance(FlexoLocalization.localizedForKey("build_editor"));
	}

	@Override
	public Module<TAEModule> getModule() {
		// TODO Auto-generated method stub
		return TraceAnalysisModule.INSTANCE;
	}

	@Override
	protected FlexoController createControllerForModule() {
		// TODO Auto-generated method stub
		return new TAEController(this);
	}
}
