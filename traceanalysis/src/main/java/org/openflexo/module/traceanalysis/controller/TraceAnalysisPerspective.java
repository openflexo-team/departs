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
import org.openflexo.module.traceanalysis.TraceAnalysisIconLibrary;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.module.traceanalysis.model.mask.Mask;
import org.openflexo.module.traceanalysis.view.OBPRouteModuleView;
import org.openflexo.module.traceanalysis.widget.FIBConfigurationMask;
import org.openflexo.module.traceanalysis.widget.FIBTraceAnalysisProjectBrowser;
import org.openflexo.view.ModuleView;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.model.FlexoPerspective;

public class TraceAnalysisPerspective extends FlexoPerspective {

	protected static final Logger logger = Logger.getLogger(TraceAnalysisPerspective.class.getPackage().getName());

	private FIBTraceAnalysisProjectBrowser taProjectBrowser = null;
	private FIBConfigurationMask configurationMasks = null;


	/**
	 * Default constructor taking controller as argument
	 */
	public TraceAnalysisPerspective(TraceAnalysisController controller) {
		super("ta_perspective", controller);
		// _controller = controller;

		taProjectBrowser = new FIBTraceAnalysisProjectBrowser(controller.getProject(), controller);
		configurationMasks = new FIBConfigurationMask(null, controller);
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
		}else if (object instanceof Mask) {
			return ((Mask) object).getName();
		} 
		if (object != null) {
			return object.toString();
		}
		return "null";
	}

	public void focusOnTraceVirtualModelInstance(TraceVirtualModelInstance TraceVirtualModelInstance) {
		
	}

	@Override
	public void focusOnObject(FlexoObject object) {
		if (object instanceof TraceVirtualModelInstance) {
			logger.info("focusOnTraceVirtualModelInstance " + object);
			configurationMasks.setTraceVirtualModelInstance((TraceVirtualModelInstance) object);
			setBottomLeftView(configurationMasks);
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
			focusOnObject((TraceVirtualModelInstance)object);
		} 
		else if (object instanceof Mask) {
			controller.selectAndFocusObject((Mask) object);
		} 
		else {
			super.objectWasDoubleClicked(object, controller);
		}
	}


	@Override
	public boolean hasModuleViewForObject(FlexoObject object) {
		if (object instanceof TraceVirtualModelInstance) {
			return true;
		}
		return super.hasModuleViewForObject(object);
	}

	@Override
	public ModuleView<?> createModuleViewForObject(FlexoObject object, boolean editable) {
		if (object instanceof TraceVirtualModelInstance) {
			TraceVirtualModelInstance traceVirtualModelInstance = (TraceVirtualModelInstance) object;
			return new OBPRouteModuleView(traceVirtualModelInstance,getController(), this);
		} 
		return super.createModuleViewForObject(object, editable);
	}
	
	public void setProject(FlexoProject project) {
		taProjectBrowser.setRootObject(project);
	}

}
