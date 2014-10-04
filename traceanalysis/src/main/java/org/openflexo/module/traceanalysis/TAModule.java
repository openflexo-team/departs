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


import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openflexo.ApplicationContext;
import org.openflexo.FlexoMainLocalizer;
import org.openflexo.components.ProgressWindow;
import org.openflexo.foundation.viewpoint.rm.ViewPointResource;
import org.openflexo.localization.FlexoLocalization;
import org.openflexo.module.FlexoModule;
import org.openflexo.module.Module;
import org.openflexo.module.traceanalysis.controller.TraceAnalysisController;
import org.openflexo.module.traceanalysis.model.TraceAnalysisProjectNature;
import org.openflexo.module.traceanalysis.utils.ZipResourceCenter;
import org.openflexo.rm.ResourceLocator;
import org.openflexo.view.controller.FlexoController;

/**
 * Trace Analysis module
 * 
 * @author vincent
 */
public class TAModule extends FlexoModule<TAModule> {

	private static final Logger logger = Logger.getLogger(TAModule.class.getPackage().getName());

	public static final String TA_MODULE_SHORT_NAME = "TA";
	public static final String TA_MODULE_NAME = "traceAnalysis";

	public TAModule(ApplicationContext applicationContext) {
		super(applicationContext);
		// TODO Auto-generated constructor stub
		logger.log(Level.FINE, "Load Trace analysis module");
		ProgressWindow.setProgressInstance(FlexoLocalization.localizedForKey("build_editor"));
		logger.log(Level.FINE, "Init Localizations");
		FlexoLocalization.initWith(ResourceLocator.locateResource("TraceAnalysisLocalized"));
		logger.log(Level.FINE, "Find Viewpoint Resource");
		ViewPointResource traceAnalsyisViewPointResource = getApplicationContext().getViewPointLibrary().getViewPointResource(TraceAnalysisProjectNature.TRACE_ANALYSIS_VIEWPOINT_RELATIVE_URI);
		if(traceAnalsyisViewPointResource==null){
			logger.log(Level.WARNING, "No Viewpoint Resource Found, retrieve from Jar file");
			File jarFile = ZipResourceCenter.getClassPathFile("departs-1.1.jar");
			logger.info("Instanciate new resource center for the viewpoint resource");
			ZipResourceCenter newRC = ZipResourceCenter.instanciateNewZipResourceCenter(jarFile);
			getApplicationContext().getResourceCenterService().addToResourceCenters(newRC);
			traceAnalsyisViewPointResource = getApplicationContext().getViewPointLibrary().getViewPointResource(TraceAnalysisProjectNature.TRACE_ANALYSIS_VIEWPOINT_RELATIVE_URI);
		}
	}

	@Override
	public Module<TAModule> getModule() {
		// TODO Auto-generated method stub
		return TraceAnalysisModule.INSTANCE;
	}

	@Override
	protected FlexoController createControllerForModule() {
		// TODO Auto-generated method stub
		return new TraceAnalysisController(this);
	}
}
