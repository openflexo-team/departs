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
package org.openflexo.module.traceanalysis.model.action;

import java.util.Vector;
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.InvalidArgumentException;
import org.openflexo.foundation.FlexoObject.FlexoObjectImpl;
import org.openflexo.foundation.action.FlexoAction;
import org.openflexo.foundation.action.FlexoActionType;
import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.SaveResourceException;
import org.openflexo.foundation.technologyadapter.FreeModelSlotInstanceConfiguration;
import org.openflexo.foundation.technologyadapter.TechnologyAdapter;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterResource;
import org.openflexo.foundation.view.VirtualModelInstance;
import org.openflexo.foundation.view.action.CreateBasicVirtualModelInstance;
import org.openflexo.foundation.view.action.CreateView;
import org.openflexo.foundation.view.action.ModelSlotInstanceConfiguration.DefaultModelSlotInstanceConfigurationOption;
import org.openflexo.foundation.view.rm.ViewResource;
import org.openflexo.foundation.view.rm.VirtualModelInstanceResource;
import org.openflexo.foundation.viewpoint.VirtualModelModelSlotInstanceConfiguration;
import org.openflexo.foundation.viewpoint.rm.ViewPointResource;
import org.openflexo.localization.FlexoLocalization;
import org.openflexo.module.traceanalysis.model.TAETrace;
import org.openflexo.module.traceanalysis.model.TAEContext;
import org.openflexo.module.traceanalysis.model.TAEObserver;
import org.openflexo.module.traceanalysis.model.TAESystem;
import org.openflexo.module.traceanalysis.model.TraceAnalysis;
import org.openflexo.module.traceanalysis.model.TAEProject;
import org.openflexo.module.traceanalysis.model.ConfigurationMask;
import org.openflexo.technologyadapter.cdl.CDLModelSlot;
import org.openflexo.technologyadapter.cdl.CDLTechnologyAdapter;
import org.openflexo.technologyadapter.fiacre.FiacreProgramModelSlot;
import org.openflexo.technologyadapter.fiacre.FiacreTechnologyAdapter;
import org.openflexo.toolbox.StringUtils;

public class CreateConfigurationMask extends FlexoAction<CreateConfigurationMask, TraceAnalysis, FlexoObject> {

	private static final Logger logger = Logger.getLogger(CreateConfigurationMask.class.getPackage().getName());

	public static FlexoActionType<CreateConfigurationMask, TraceAnalysis, FlexoObject> actionType = new FlexoActionType<CreateConfigurationMask, TraceAnalysis, FlexoObject>(
			"create_configuration_mask", FlexoActionType.newMenu, FlexoActionType.defaultGroup, FlexoActionType.ADD_ACTION_TYPE) {

		/**
		 * Factory method
		 */
		@Override
		public CreateConfigurationMask makeNewAction(TraceAnalysis focusedObject, Vector<FlexoObject> globalSelection, FlexoEditor editor) {
			return new CreateConfigurationMask(focusedObject, globalSelection, editor);
		}

		@Override
		public boolean isVisibleForSelection(TraceAnalysis object, Vector<FlexoObject> globalSelection) {
			return true;
		}

		@Override
		public boolean isEnabledForSelection(TraceAnalysis object, Vector<FlexoObject> globalSelection) {
			return true;
		}

	};

	static {
		FlexoObjectImpl.addActionForClass(CreateConfigurationMask.actionType, TraceAnalysis.class);
	}

	CreateConfigurationMask(TraceAnalysis focusedObject, Vector<FlexoObject> globalSelection, FlexoEditor editor) {
		super(actionType, focusedObject, globalSelection, editor);
	}

	@Override
	protected void doAction(Object context) throws SaveResourceException {

		logger.info("Create trace exploration mask");
		configurationMask = getFocusedObject().getNewConfigurationMask();
		configurationMask.setName(getConfigurationMaskName());
	}
	
	private ConfigurationMask configurationMask;
	
	public String getConfigurationMaskName() {
		return configurationMaskName;
	}

	public void setConfigurationMaskName(String configurationMaskName) {
		boolean wasValid = isValid();
		this.configurationMaskName = configurationMaskName;
		getPropertyChangeSupport().firePropertyChange("configurationMaskName", null, configurationMaskName);
		getPropertyChangeSupport().firePropertyChange("isValid", wasValid, isValid());
		getPropertyChangeSupport().firePropertyChange("errorMessage", null, getErrorMessage());
	}

	private String errorMessage;
	
	private String configurationMaskName;
	
	private String configurationMaskDescription;

	public String getErrorMessage() {
		isValid();
		// System.out.println("valid=" + isValid());
		// System.out.println("errorMessage=" + errorMessage);
		return errorMessage;
	}

	@Override
	public boolean isValid() {

		if (StringUtils.isEmpty(configurationMaskName)) {
			errorMessage = FlexoLocalization.localizedForKey("no_configurationMask_name_defined");
			return false;
		}

		return true;
	}

	public String getConfigurationMaskDescription() {
		return configurationMaskDescription;
	}

	public void setConfigurationMaskDescription(String configurationMaskDescription) {
		boolean wasValid = isValid();
		this.configurationMaskDescription = configurationMaskDescription;
		getPropertyChangeSupport().firePropertyChange("configurationMaskDescription", null, configurationMaskDescription);
		getPropertyChangeSupport().firePropertyChange("isValid", wasValid, isValid());
		getPropertyChangeSupport().firePropertyChange("errorMessage", null, getErrorMessage());
	}

	public ConfigurationMask getConfigurationMask() {
		return configurationMask;
	}

	public void setConfigurationMask(ConfigurationMask configurationMask) {
		this.configurationMask = configurationMask;
	}

}
