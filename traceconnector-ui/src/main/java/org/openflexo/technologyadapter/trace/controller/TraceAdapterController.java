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

package org.openflexo.technologyadapter.trace.controller;

import java.util.logging.Logger;

import javax.swing.ImageIcon;

import org.openflexo.foundation.fml.FlexoRole;
import org.openflexo.foundation.fml.editionaction.EditionAction;
import org.openflexo.foundation.technologyadapter.TechnologyObject;
import org.openflexo.gina.utils.InspectorGroup;
import org.openflexo.technologyadapter.trace.TraceTechnologyAdapter;
import org.openflexo.technologyadapter.trace.gui.TraceIconLibrary;
import org.openflexo.technologyadapter.trace.gui.view.FIBTraceView;
import org.openflexo.technologyadapter.trace.model.OBPTrace;
import org.openflexo.view.EmptyPanel;
import org.openflexo.view.ModuleView;
import org.openflexo.view.controller.ControllerActionInitializer;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.TechnologyAdapterController;
import org.openflexo.view.controller.model.FlexoPerspective;

public class TraceAdapterController extends TechnologyAdapterController<TraceTechnologyAdapter> {
	static final Logger logger = Logger.getLogger(TraceAdapterController.class.getPackage().getName());

	@Override
	public Class<TraceTechnologyAdapter> getTechnologyAdapterClass() {
		return TraceTechnologyAdapter.class;
	}

	/**
	 * Initialize inspectors for supplied module using supplied {@link FlexoController}
	 * 
	 * @param controller
	 */
	@Override
	protected void initializeInspectors(FlexoController controller) {

		traceInspectorGroup = controller.loadInspectorGroup("Trace", getFMLTechnologyAdapterInspectorGroup());
		// actionInitializer.getController().getModuleInspectorController()
		// .loadDirectory(ResourceLocator.locateResource("src/main/resources/Inspectors/Trace"));
	}

	private InspectorGroup traceInspectorGroup;

	/**
	 * Return inspector group for this technology
	 * 
	 * @return
	 */
	@Override
	public InspectorGroup getTechnologyAdapterInspectorGroup() {
		return traceInspectorGroup;
	}

	@Override
	protected void initializeActions(ControllerActionInitializer actionInitializer) {
	}

	@Override
	public ImageIcon getTechnologyBigIcon() {
		return TraceIconLibrary.TRACE_TECHNOLOGY_BIG_ICON;
	}

	@Override
	public ImageIcon getTechnologyIcon() {
		return TraceIconLibrary.TRACE_TECHNOLOGY_ICON;
	}

	@Override
	public ImageIcon getModelIcon() {
		return TraceIconLibrary.TRACE_FILE_ICON;
	}

	@Override
	public ImageIcon getMetaModelIcon() {
		return TraceIconLibrary.TRACE_FILE_ICON;
	}

	@Override
	public ImageIcon getIconForTechnologyObject(Class<? extends TechnologyObject<?>> objectClass) {
		return TraceIconLibrary.iconForObject(objectClass);
	}

	/**
	 * Return icon representing supplied edition action
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public ImageIcon getIconForEditionAction(Class<? extends EditionAction> editionActionClass) {
		return super.getIconForEditionAction(editionActionClass);
	}

	@Override
	public ModuleView<?> createModuleViewForObject(TechnologyObject<TraceTechnologyAdapter> arg0, FlexoController arg1,
			FlexoPerspective arg2) {
		if (arg0 instanceof OBPTrace) {
			return new FIBTraceView((OBPTrace) arg0, arg1);
		}
		return new EmptyPanel<TechnologyObject<TraceTechnologyAdapter>>(arg1, arg2, arg0);
	}

	@Override
	public ImageIcon getIconForFlexoRole(Class<? extends FlexoRole<?>> arg0) {
		return null;
	}

	@Override
	public String getWindowTitleforObject(TechnologyObject<TraceTechnologyAdapter> arg0, FlexoController arg1) {
		if (arg0 instanceof OBPTrace) {
			return ((OBPTrace) arg0).getName();
		}
		return arg0.toString();
	}

	@Override
	public boolean hasModuleViewForObject(TechnologyObject<TraceTechnologyAdapter> arg0, FlexoController arg1) {
		if (arg0 instanceof OBPTrace) {
			return true;
		}
		return false;
	}

}
