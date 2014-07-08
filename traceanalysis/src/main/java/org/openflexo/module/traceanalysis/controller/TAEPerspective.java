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
package org.openflexo.module.traceanalysis.controller;

import java.util.logging.Logger;

import javax.swing.ImageIcon;

import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.FlexoProject;
import org.openflexo.model.undo.CompoundEdit;
import org.openflexo.module.traceanalysis.TAEIconLibrary;
import org.openflexo.module.traceanalysis.model.TraceAnalysis;
import org.openflexo.module.traceanalysis.model.TraceExplorationMask;
import org.openflexo.module.traceanalysis.view.TraceAnalysisModuleView;
import org.openflexo.module.traceanalysis.view.TraceExplorationMaskModuleView;
import org.openflexo.module.traceanalysis.widget.FIBAnalyzeConceptsBrowser;
import org.openflexo.module.traceanalysis.widget.FIBTAEProjectBrowser;
import org.openflexo.view.ModuleView;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.model.FlexoPerspective;

public class TAEPerspective extends FlexoPerspective {

	protected static final Logger logger = Logger.getLogger(TAEPerspective.class.getPackage().getName());

	private FIBTAEProjectBrowser taeProjectBrowser = null;
	private FIBAnalyzeConceptsBrowser analyzeConceptsBrowser = null;

	/**
	 * Default constructor taking controller as argument
	 */
	public TAEPerspective(TAEController controller) {
		super("tae_perspective", controller);
		// _controller = controller;

		taeProjectBrowser = new FIBTAEProjectBrowser(controller.getProject(), controller);
		analyzeConceptsBrowser = new FIBAnalyzeConceptsBrowser(null, controller);
		setTopLeftView(taeProjectBrowser);
	}

	public ModuleView<?> getCurrentModuleView(FlexoController controller) {
		return controller.getCurrentModuleView();
	}

	/**
	 * Overrides getIcon
	 * 
	 * @see org.openflexo.view.controller.model.FlexoPerspective#getActiveIcon()
	 */
	@Override
	public ImageIcon getActiveIcon() {
		return TAEIconLibrary.TAE_SMALL_ICON;
	}

	@Override
	public String getWindowTitleforObject(FlexoObject object, FlexoController controller) {
		if (object instanceof TraceAnalysis) {
			return ((TraceAnalysis) object).getName();
		}
		if (object != null) {
			return object.toString();
		}
		return "null";
	}

	public void focusOnTraceAnalysis(TraceAnalysis traceAnalysis) {
		logger.info("focusOnTraceAnalysis " + traceAnalysis);
		analyzeConceptsBrowser.setTraceAnalysis(traceAnalysis);
		setBottomLeftView(analyzeConceptsBrowser);
	}

	@Override
	public void focusOnObject(FlexoObject object) {
		if (object instanceof TraceAnalysis) {
			focusOnTraceAnalysis((TraceAnalysis) object);
		}
	};

	@Override
	public void objectWasClicked(Object object, FlexoController controller) {
		// logger.info("ViewPointPerspective: object was clicked: " + object);
		if (object == null) {
			return;
		}
	}

	@Override
	public void objectWasRightClicked(Object object, FlexoController controller) {
		// logger.info("ViewPointPerspective: object was right-clicked: " + object);
	}

	@Override
	public void objectWasDoubleClicked(Object object, FlexoController controller) {
		// logger.info("ViewPointPerspective: object was double-clicked: " + object);
		if (object instanceof TraceAnalysis) {
			controller.selectAndFocusObject((TraceAnalysis) object);
		} else {
			super.objectWasDoubleClicked(object, controller);
		}
	}


	@Override
	public boolean hasModuleViewForObject(FlexoObject object) {
		if (object instanceof TraceAnalysis) {
			return true;
		}else if (object instanceof TraceExplorationMask) {
			return true;
		}
		return super.hasModuleViewForObject(object);
	}

	@Override
	public ModuleView<?> createModuleViewForObject(FlexoObject object, boolean editable) {
		if (object instanceof TraceAnalysis) {
			return new TraceAnalysisModuleView((TraceAnalysis) object,getController(), this);
		} else if(object instanceof TraceExplorationMask){
			return new TraceExplorationMaskModuleView((TraceExplorationMask) object,getController(), this);
		}
		return super.createModuleViewForObject(object, editable);
	}
	public void setProject(FlexoProject project) {
		taeProjectBrowser.setRootObject(project);
	}

}
