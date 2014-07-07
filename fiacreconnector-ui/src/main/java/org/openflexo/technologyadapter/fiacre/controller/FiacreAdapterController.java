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

package org.openflexo.technologyadapter.fiacre.controller;

import java.util.logging.Logger;

import javax.swing.ImageIcon;

import org.openflexo.foundation.technologyadapter.TechnologyObject;
import org.openflexo.foundation.viewpoint.FlexoRole;
import org.openflexo.foundation.viewpoint.editionaction.EditionAction;
import org.openflexo.icon.IconFactory;
import org.openflexo.icon.IconLibrary;
import org.openflexo.rm.ResourceLocator;
import org.openflexo.technologyadapter.fiacre.FiacreTechnologyAdapter;
import org.openflexo.technologyadapter.fiacre.gui.FiacreIconLibrary;
import org.openflexo.technologyadapter.fiacre.gui.view.FIBFiacreView;
import org.openflexo.technologyadapter.fiacre.model.FiacreComponent;
import org.openflexo.technologyadapter.fiacre.model.FiacreFifo;
import org.openflexo.technologyadapter.fiacre.model.FiacreProcess;
import org.openflexo.technologyadapter.fiacre.model.FiacreProgram;
import org.openflexo.technologyadapter.fiacre.model.FiacreState;
import org.openflexo.technologyadapter.fiacre.virtualmodel.FiacreComponentRole;
import org.openflexo.technologyadapter.fiacre.virtualmodel.FiacreFifoRole;
import org.openflexo.technologyadapter.fiacre.virtualmodel.FiacreProcessRole;
import org.openflexo.technologyadapter.fiacre.virtualmodel.FiacreStateRole;
import org.openflexo.technologyadapter.fiacre.virtualmodel.actions.AddFiacreComponent;
import org.openflexo.technologyadapter.fiacre.virtualmodel.actions.AddFiacreFifo;
import org.openflexo.technologyadapter.fiacre.virtualmodel.actions.AddFiacreProcess;
import org.openflexo.technologyadapter.fiacre.virtualmodel.actions.AddFiacreState;
import org.openflexo.view.EmptyPanel;
import org.openflexo.view.ModuleView;
import org.openflexo.view.controller.ControllerActionInitializer;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.TechnologyAdapterController;
import org.openflexo.view.controller.model.FlexoPerspective;

public class FiacreAdapterController extends TechnologyAdapterController<FiacreTechnologyAdapter> {
	static final Logger logger = Logger.getLogger(FiacreAdapterController.class.getPackage().getName());

	@Override
	public Class<FiacreTechnologyAdapter> getTechnologyAdapterClass() {
		return FiacreTechnologyAdapter.class;
	}

	@Override
	public void initializeActions(ControllerActionInitializer actionInitializer) {
		actionInitializer.getController().getModuleInspectorController()
				.loadDirectory(ResourceLocator.locateResource("src/main/resources/Inspectors/Fiacre"));
	}

	@Override
	public ImageIcon getTechnologyBigIcon() {
		return FiacreIconLibrary.FIACRE_TECHNOLOGY_BIG_ICON;
	}

	@Override
	public ImageIcon getTechnologyIcon() {
		return FiacreIconLibrary.FIACRE_TECHNOLOGY_ICON;
	}

	@Override
	public ImageIcon getModelIcon() {
		return FiacreIconLibrary.FIACRE_FILE_ICON;
	}

	@Override
	public ImageIcon getMetaModelIcon() {
		return FiacreIconLibrary.FIACRE_FILE_ICON;
	}

	@Override
	public ImageIcon getIconForTechnologyObject(Class<? extends TechnologyObject<FiacreTechnologyAdapter>> objectClass) {
		return FiacreIconLibrary.iconForObject(objectClass);
	}

	@Override
	public ImageIcon getIconForPatternRole(Class<? extends FlexoRole<?>> arg0) {
		if (FiacreProcessRole.class.isAssignableFrom(arg0)) {
			return getIconForTechnologyObject(FiacreProcess.class);
		}
		if (FiacreComponentRole.class.isAssignableFrom(arg0)) {
			return getIconForTechnologyObject(FiacreComponent.class);
		}
		if (FiacreStateRole.class.isAssignableFrom(arg0)) {
			return getIconForTechnologyObject(FiacreState.class);
		}
		if (FiacreFifoRole.class.isAssignableFrom(arg0)) {
			return getIconForTechnologyObject(FiacreFifo.class);
		}
		return null;
	}
	
	/**
	 * Return icon representing supplied edition action
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public ImageIcon getIconForEditionAction(Class<? extends EditionAction<?, ?>> editionActionClass) {
		if (AddFiacreProcess.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(getIconForTechnologyObject(FiacreProcess.class), IconLibrary.DUPLICATE);
		} else if (AddFiacreComponent.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(getIconForTechnologyObject(FiacreComponent.class), IconLibrary.DUPLICATE);
		} else if (AddFiacreState.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(getIconForTechnologyObject(FiacreState.class), IconLibrary.DUPLICATE);
		} else if (AddFiacreFifo.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(getIconForTechnologyObject(FiacreFifo.class), IconLibrary.DUPLICATE);
		}
		return super.getIconForEditionAction(editionActionClass);
	}

	@Override
	public ModuleView<?> createModuleViewForObject(TechnologyObject<FiacreTechnologyAdapter> arg0, FlexoController arg1,
			FlexoPerspective arg2) {
		if (arg0 instanceof FiacreProgram) {
			return new FIBFiacreView((FiacreProgram) arg0, arg1);
		}
		return new EmptyPanel<TechnologyObject<FiacreTechnologyAdapter>>(arg1, arg2, arg0);
	}

	@Override
	public String getWindowTitleforObject(TechnologyObject<FiacreTechnologyAdapter> arg0, FlexoController arg1) {
		if (arg0 instanceof FiacreProgram) {
			return ((FiacreProgram) arg0).getName();
		}
		return arg0.toString();
	}

	@Override
	public boolean hasModuleViewForObject(TechnologyObject<FiacreTechnologyAdapter> arg0, FlexoController arg1) {
		if (arg0 instanceof FiacreProgram) {
			return true;
		}
		return false;
	}

}
