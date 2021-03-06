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

package org.openflexo.technologyadapter.cdl.controller;

import java.util.logging.Logger;

import javax.swing.ImageIcon;

import org.openflexo.fib.utils.InspectorGroup;
import org.openflexo.foundation.fml.FlexoRole;
import org.openflexo.foundation.fml.editionaction.EditionAction;
import org.openflexo.foundation.technologyadapter.TechnologyObject;
import org.openflexo.icon.IconFactory;
import org.openflexo.icon.IconLibrary;
import org.openflexo.technologyadapter.cdl.CDLTechnologyAdapter;
import org.openflexo.technologyadapter.cdl.fml.CDLAltActivityRole;
import org.openflexo.technologyadapter.cdl.fml.CDLCommunicationOpEventRole;
import org.openflexo.technologyadapter.cdl.fml.CDLEventActivityRole;
import org.openflexo.technologyadapter.cdl.fml.CDLGammaEventRole;
import org.openflexo.technologyadapter.cdl.fml.CDLParActivityRole;
import org.openflexo.technologyadapter.cdl.fml.CDLPredicateEventRole;
import org.openflexo.technologyadapter.cdl.fml.CDLProcessIDRole;
import org.openflexo.technologyadapter.cdl.fml.CDLPropertyRole;
import org.openflexo.technologyadapter.cdl.fml.CDLSeqActivityRole;
import org.openflexo.technologyadapter.cdl.fml.action.AddCDLProcessID;
import org.openflexo.technologyadapter.cdl.fml.action.AddCDLProperty;
import org.openflexo.technologyadapter.cdl.fml.action.SelectCDLAltActivity;
import org.openflexo.technologyadapter.cdl.fml.action.SelectCDLEventReference;
import org.openflexo.technologyadapter.cdl.fml.action.SelectCDLParActivity;
import org.openflexo.technologyadapter.cdl.fml.action.SelectCDLProperties;
import org.openflexo.technologyadapter.cdl.gui.CDLIconLibrary;
import org.openflexo.technologyadapter.cdl.gui.view.FIBCDLView;
import org.openflexo.technologyadapter.cdl.model.CDLActivity;
import org.openflexo.technologyadapter.cdl.model.CDLAltActivity;
import org.openflexo.technologyadapter.cdl.model.CDLEvent;
import org.openflexo.technologyadapter.cdl.model.CDLEventReference;
import org.openflexo.technologyadapter.cdl.model.CDLParActivity;
import org.openflexo.technologyadapter.cdl.model.CDLProcessID;
import org.openflexo.technologyadapter.cdl.model.CDLProperty;
import org.openflexo.technologyadapter.cdl.model.CDLUnit;
import org.openflexo.view.EmptyPanel;
import org.openflexo.view.ModuleView;
import org.openflexo.view.controller.ControllerActionInitializer;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.TechnologyAdapterController;
import org.openflexo.view.controller.model.FlexoPerspective;

public class CDLAdapterController extends TechnologyAdapterController<CDLTechnologyAdapter> {
	static final Logger logger = Logger.getLogger(CDLAdapterController.class.getPackage().getName());

	@Override
	public Class<CDLTechnologyAdapter> getTechnologyAdapterClass() {
		return CDLTechnologyAdapter.class;
	}

	/**
	 * Initialize inspectors for supplied module using supplied {@link FlexoController}
	 * 
	 * @param controller
	 */
	@Override
	protected void initializeInspectors(FlexoController controller) {

		cdlInspectorGroup = controller.loadInspectorGroup("CDL", getFMLTechnologyAdapterInspectorGroup());
		// actionInitializer.getController().getModuleInspectorController()
		// .loadDirectory(ResourceLocator.locateResource("src/main/resources/Inspectors/CDL"));
	}

	private InspectorGroup cdlInspectorGroup;

	/**
	 * Return inspector group for this technology
	 * 
	 * @return
	 */
	@Override
	public InspectorGroup getTechnologyAdapterInspectorGroup() {
		return cdlInspectorGroup;
	}

	@Override
	protected void initializeActions(ControllerActionInitializer actionInitializer) {
	}

	@Override
	public ImageIcon getTechnologyBigIcon() {
		return CDLIconLibrary.CDL_TECHNOLOGY_BIG_ICON;
	}

	@Override
	public ImageIcon getTechnologyIcon() {
		return CDLIconLibrary.CDL_TECHNOLOGY_ICON;
	}

	@Override
	public ImageIcon getModelIcon() {
		return CDLIconLibrary.CDL_FILE_ICON;
	}

	@Override
	public ImageIcon getMetaModelIcon() {
		return CDLIconLibrary.CDL_FILE_ICON;
	}

	@Override
	public ImageIcon getIconForTechnologyObject(Class<? extends TechnologyObject<?>> objectClass) {
		return CDLIconLibrary.iconForObject(objectClass);
	}

	/**
	 * Return icon representing supplied edition action
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public ImageIcon getIconForEditionAction(Class<? extends EditionAction> editionActionClass) {
		if (AddCDLProcessID.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(getIconForTechnologyObject(CDLProcessID.class), IconLibrary.DUPLICATE);
		} else if (AddCDLProperty.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(getIconForTechnologyObject(CDLProperty.class), IconLibrary.DUPLICATE);
		} else if (SelectCDLAltActivity.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(getIconForTechnologyObject(CDLAltActivity.class), IconLibrary.IMPORT);
		} else if (SelectCDLEventReference.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(getIconForTechnologyObject(CDLEventReference.class), IconLibrary.IMPORT);
		} else if (SelectCDLParActivity.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(getIconForTechnologyObject(CDLParActivity.class), IconLibrary.IMPORT);
		} else if (SelectCDLProperties.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(getIconForTechnologyObject(CDLProperty.class), IconLibrary.IMPORT);
		}
		return super.getIconForEditionAction(editionActionClass);
	}

	@Override
	public ModuleView<?> createModuleViewForObject(TechnologyObject<CDLTechnologyAdapter> arg0, FlexoController arg1, FlexoPerspective arg2) {
		if (arg0 instanceof CDLUnit) {
			return new FIBCDLView((CDLUnit) arg0, arg1);
		}
		return new EmptyPanel<TechnologyObject<CDLTechnologyAdapter>>(arg1, arg2, arg0);
	}

	@Override
	public ImageIcon getIconForPatternRole(Class<? extends FlexoRole<?>> arg0) {
		if (CDLAltActivityRole.class.isAssignableFrom(arg0)) {
			return getIconForTechnologyObject(CDLActivity.class);
		}
		if (CDLEventActivityRole.class.isAssignableFrom(arg0)) {
			return getIconForTechnologyObject(CDLActivity.class);
		}
		if (CDLParActivityRole.class.isAssignableFrom(arg0)) {
			return getIconForTechnologyObject(CDLActivity.class);
		}
		if (CDLSeqActivityRole.class.isAssignableFrom(arg0)) {
			return getIconForTechnologyObject(CDLActivity.class);
		}
		if (CDLCommunicationOpEventRole.class.isAssignableFrom(arg0)) {
			return getIconForTechnologyObject(CDLEvent.class);
		}
		if (CDLGammaEventRole.class.isAssignableFrom(arg0)) {
			return getIconForTechnologyObject(CDLEvent.class);
		}
		if (CDLPredicateEventRole.class.isAssignableFrom(arg0)) {
			return getIconForTechnologyObject(CDLEvent.class);
		}
		if (CDLPropertyRole.class.isAssignableFrom(arg0)) {
			return getIconForTechnologyObject(CDLProperty.class);
		}
		if (CDLProcessIDRole.class.isAssignableFrom(arg0)) {
			return getIconForTechnologyObject(CDLProcessID.class);
		}
		return null;
	}

	@Override
	public String getWindowTitleforObject(TechnologyObject<CDLTechnologyAdapter> arg0, FlexoController arg1) {
		if (arg0 instanceof CDLUnit) {
			return ((CDLUnit) arg0).getName();
		}
		return arg0.toString();
	}

	@Override
	public boolean hasModuleViewForObject(TechnologyObject<CDLTechnologyAdapter> arg0, FlexoController arg1) {
		if (arg0 instanceof CDLUnit) {
			return true;
		}
		return false;
	}

}
