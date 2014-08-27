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

import org.openflexo.fge.FGEModelFactory;
import org.openflexo.fge.FGEModelFactoryImpl;
import org.openflexo.fge.swing.control.SwingToolFactory;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.FlexoProject;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.module.traceanalysis.TraceAnalysisIconLibrary;
import org.openflexo.module.traceanalysis.model.ConfigurationMask;
import org.openflexo.module.traceanalysis.model.TraceAnalysisProject;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.module.traceanalysis.model.TransitionMask;
import org.openflexo.module.traceanalysis.view.ConfigurationMaskModuleView;
import org.openflexo.module.traceanalysis.view.OBPRouteModuleView;
import org.openflexo.module.traceanalysis.view.TransitionMaskModuleView;
import org.openflexo.module.traceanalysis.view.routeview.OBPRoute;
import org.openflexo.module.traceanalysis.view.routeview.OBPRouteEditor;
import org.openflexo.module.traceanalysis.view.routeview.OBPRouteImpl;
import org.openflexo.module.traceanalysis.view.routeview.sequencediagram.SequenceDiagramDrawing;
import org.openflexo.module.traceanalysis.widget.FIBTraceAnalysisProjectConceptBrowser;
import org.openflexo.module.traceanalysis.widget.FIBTraceAnalysisProjectBrowser;
import org.openflexo.technologyadapter.trace.model.OBPTrace;
import org.openflexo.view.ModuleView;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.model.FlexoPerspective;

public class TraceAnalysisPerspective extends FlexoPerspective {

	protected static final Logger logger = Logger.getLogger(TraceAnalysisPerspective.class.getPackage().getName());

	private FIBTraceAnalysisProjectBrowser taProjectBrowser = null;
	private FIBTraceAnalysisProjectConceptBrowser analyzeConceptsBrowser = null;


	/**
	 * Default constructor taking controller as argument
	 */
	public TraceAnalysisPerspective(TraceAnalysisController controller) {
		super("ta_perspective", controller);
		// _controller = controller;

		taProjectBrowser = new FIBTraceAnalysisProjectBrowser(controller.getProject(), controller);
		analyzeConceptsBrowser = new FIBTraceAnalysisProjectConceptBrowser(null, controller);
		setTopLeftView(taProjectBrowser);
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
		return TraceAnalysisIconLibrary.TA_SMALL_ICON;
	}

	@Override
	public String getWindowTitleforObject(FlexoObject object, FlexoController controller) {
		if (object instanceof TraceVirtualModelInstance) {
			return ((TraceVirtualModelInstance) object).getName();
		}else if (object instanceof ConfigurationMask) {
			return ((ConfigurationMask) object).getName();
		}
		if (object != null) {
			return object.toString();
		}
		return "null";
	}

	public void focusOnTraceAnalysisProject(TraceAnalysisProject traceAnalysisProject) {
		logger.info("focusOnTraceVirtualModelInstance " + traceAnalysisProject);
		analyzeConceptsBrowser.setTraceAnalysisProject(traceAnalysisProject);
		setBottomLeftView(analyzeConceptsBrowser);
	}

	@Override
	public void focusOnObject(FlexoObject object) {
		if (object instanceof TraceAnalysisProject) {
			focusOnTraceAnalysisProject((TraceAnalysisProject) object);
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
		if (object instanceof TraceVirtualModelInstance) {
			controller.selectAndFocusObject((TraceVirtualModelInstance) object);
		} 
		else if (object instanceof ConfigurationMask) {
			controller.selectAndFocusObject((ConfigurationMask) object);
		} else if (object instanceof TransitionMask) {
			controller.selectAndFocusObject((TransitionMask) object);
		}
		else {
			super.objectWasDoubleClicked(object, controller);
		}
	}


	@Override
	public boolean hasModuleViewForObject(FlexoObject object) {
		if (object instanceof TraceVirtualModelInstance) {
			return true;
		}else if (object instanceof ConfigurationMask) {
			return true;
		}else if (object instanceof TransitionMask) {
			return true;
		} 
		return super.hasModuleViewForObject(object);
	}

	@Override
	public ModuleView<?> createModuleViewForObject(FlexoObject object, boolean editable) {
		if (object instanceof TraceVirtualModelInstance) {
			TraceVirtualModelInstance traceVirtualModelInstance = (TraceVirtualModelInstance) object;
			//SequenceDiagramDrawing routeDrawing = makeRouteDrawing(traceVirtualModelInstance.getTraceOBP());
			//OBPRouteEditor editor = new OBPRouteEditor(routeDrawing,new FGEModelFactoryImpl(),SwingToolFactory.DEFAULT,traceVirtualModelInstance );
			return new OBPRouteModuleView(traceVirtualModelInstance,getController(), this);
		} else if(object instanceof ConfigurationMask){
			return new ConfigurationMaskModuleView((ConfigurationMask) object,getController(), this);
		} else if(object instanceof TransitionMask){
			return new TransitionMaskModuleView((TransitionMask) object,getController(), this);
		}
		return super.createModuleViewForObject(object, editable);
	}
	
	public void setProject(FlexoProject project) {
		taProjectBrowser.setRootObject(project);
	}

}
