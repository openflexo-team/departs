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

package org.openflexo.module.traceanalysis.controller;

import javax.swing.ImageIcon;

import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.FlexoProject;
import org.openflexo.foundation.technologyadapter.TechnologyAdapter;
import org.openflexo.foundation.viewpoint.VirtualModelTechnologyAdapter;
import org.openflexo.icon.IconLibrary;
import org.openflexo.icon.VPMIconLibrary;
import org.openflexo.module.FlexoModule;
import org.openflexo.selection.MouseSelectionManager;
import org.openflexo.technologyadapter.cdl.CDLTechnologyAdapter;
import org.openflexo.technologyadapter.fiacre.FiacreTechnologyAdapter;
import org.openflexo.technologyadapter.trace.TraceTechnologyAdapter;
import org.openflexo.view.FlexoMainPane;
import org.openflexo.view.controller.ControllerActionInitializer;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.TechnologyAdapterController;
import org.openflexo.view.menu.FlexoMenuBar;
import org.openflexo.module.traceanalysis.TAEIconLibrary;
import org.openflexo.module.traceanalysis.controller.TAESelectionManager;
import org.openflexo.module.traceanalysis.controller.action.TAEControllerActionInitializer;
import org.openflexo.module.traceanalysis.model.TAEProject;
import org.openflexo.module.traceanalysis.model.TraceAnalysis;
import org.openflexo.module.traceanalysis.model.ConfigurationMask;
import org.openflexo.module.traceanalysis.view.menu.TAEMenuBar;

public class TAEController extends FlexoController {

	public TAEPerspective TAE_PERSPECTIVE;
	
	public TAEController(FlexoModule module) {
		super(module);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initializePerspectives() {
		addToPerspectives(TAE_PERSPECTIVE = new TAEPerspective(this));
		
		for (TechnologyAdapter ta : getApplicationContext().getTechnologyAdapterService().getTechnologyAdapters()) {
			if (ta instanceof CDLTechnologyAdapter || ta instanceof FiacreTechnologyAdapter || ta instanceof TraceTechnologyAdapter) {
				TechnologyAdapterController<?> tac = getApplicationContext().getTechnologyAdapterControllerService()
						.getTechnologyAdapterController(ta);
				if (tac != null) {
					tac.installTechnologyPerspective(this);
				}
			}
		}
		
	}

	@Override
	protected MouseSelectionManager createSelectionManager() {
		return new TAESelectionManager(this);
	}

	@Override
	protected FlexoMenuBar createNewMenuBar() {
		return new TAEMenuBar(this);
	}

	@Override
	public FlexoObject getDefaultObjectToSelect(FlexoProject project) {
		return project;
	}

	@Override
	protected FlexoMainPane createMainPane() {
		return new FlexoMainPane(this);
	}
	
	@Override
	public ControllerActionInitializer createControllerActionInitializer() {
		return new TAEControllerActionInitializer(this);
	}

	/**
	 * Select the view representing supplied object, if this view exists. Try all to really display supplied object, even if required view
	 * is not the current displayed view
	 * 
	 * @param object
	 *            : the object to focus on
	 */
	@Override
	public void selectAndFocusObject(FlexoObject object) {
		if (object != null) {
			if (object instanceof FlexoObject) {
				setCurrentEditedObjectAsModuleView(object);
			}
			if (getCurrentPerspective() == TAE_PERSPECTIVE) {
				if (object instanceof TraceAnalysis) {
					TAE_PERSPECTIVE.focusOnTraceAnalysis((TraceAnalysis) object);
				}
				if (object instanceof ConfigurationMask) {
					TAE_PERSPECTIVE.focusOnConfigurationMask((ConfigurationMask) object);
				}
			}
			getSelectionManager().setSelectedObject(object);
		} 
	}
	
	@Override
	public ImageIcon iconForObject(Object object) {
		if (object instanceof TAEProject) {
			return IconLibrary.OPENFLEXO_NOTEXT_16;
		} else if (object instanceof TraceAnalysis) {
			return TAEIconLibrary.TAE_SMALL_ICON;
		} else if (object instanceof ConfigurationMask) {
			return TAEIconLibrary.MASK_SMALL_ICON;
		} 
		return super.iconForObject(object);
	}

	@Override
	public void updateEditor(FlexoEditor from, FlexoEditor to) {
		super.updateEditor(from, to);
		FlexoProject project = (to != null ? to.getProject() : null);
		TAE_PERSPECTIVE.setProject(project);
	}
}
