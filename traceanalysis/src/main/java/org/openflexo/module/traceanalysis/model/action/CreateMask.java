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

import org.openflexo.ApplicationContext;
import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.FlexoObject.FlexoObjectImpl;
import org.openflexo.foundation.action.FlexoAction;
import org.openflexo.foundation.action.FlexoActionType;
import org.openflexo.foundation.resource.SaveResourceException;
import org.openflexo.localization.LocalizedDelegate;
import org.openflexo.module.traceanalysis.TraceAnalysisModule;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.module.traceanalysis.model.mask.Mask;
import org.openflexo.toolbox.StringUtils;

public class CreateMask extends FlexoAction<CreateMask, TraceVirtualModelInstance, FlexoObject> {

	private static final Logger logger = Logger.getLogger(CreateMask.class.getPackage().getName());

	public static FlexoActionType<CreateMask, TraceVirtualModelInstance, FlexoObject> actionType = new FlexoActionType<CreateMask, TraceVirtualModelInstance, FlexoObject>(
			"create_mask", FlexoActionType.newMenu, FlexoActionType.defaultGroup, FlexoActionType.ADD_ACTION_TYPE) {

		/**
		 * Factory method
		 */
		@Override
		public CreateMask makeNewAction(TraceVirtualModelInstance focusedObject, Vector<FlexoObject> globalSelection, FlexoEditor editor) {
			return new CreateMask(focusedObject, globalSelection, editor);
		}

		@Override
		public boolean isVisibleForSelection(TraceVirtualModelInstance object, Vector<FlexoObject> globalSelection) {
			return true;
		}

		@Override
		public boolean isEnabledForSelection(TraceVirtualModelInstance object, Vector<FlexoObject> globalSelection) {
			return true;
		}

	};

	static {
		FlexoObjectImpl.addActionForClass(CreateMask.actionType, TraceVirtualModelInstance.class);
	}

	CreateMask(TraceVirtualModelInstance focusedObject, Vector<FlexoObject> globalSelection, FlexoEditor editor) {
		super(actionType, focusedObject, globalSelection, editor);
	}

	@Override
	public LocalizedDelegate getLocales() {
		if (getServiceManager() instanceof ApplicationContext) {
			return ((ApplicationContext) getServiceManager()).getModuleLoader().getModule(TraceAnalysisModule.class)
					.getLoadedModuleInstance().getLocales();
		}
		return super.getLocales();
	}

	private Mask mask;

	private String errorMessage;

	private String maskName;

	private String maskDescription;

	@Override
	protected void doAction(Object context) throws SaveResourceException {

		logger.info("Create mask");
		mask = getFocusedObject().getNewMask();
		mask.setName(getMaskName());
	}

	public String getMaskName() {
		return maskName;
	}

	public void setMaskName(String maskName) {
		boolean wasValid = isValid();
		this.maskName = maskName;
		getPropertyChangeSupport().firePropertyChange("maskName", null, maskName);
		getPropertyChangeSupport().firePropertyChange("isValid", wasValid, isValid());
		getPropertyChangeSupport().firePropertyChange("errorMessage", null, getErrorMessage());
	}

	public String getErrorMessage() {
		isValid();
		// System.out.println("valid=" + isValid());
		// System.out.println("errorMessage=" + errorMessage);
		return errorMessage;
	}

	@Override
	public boolean isValid() {

		if (StringUtils.isEmpty(maskName)) {
			errorMessage = getLocales().localizedForKey("no_mask_name_defined");
			return false;
		}

		return true;
	}

	public String getMaskDescription() {
		return maskDescription;
	}

	public void setMaskDescription(String maskDescription) {
		boolean wasValid = isValid();
		this.maskDescription = maskDescription;
		getPropertyChangeSupport().firePropertyChange("maskDescription", null, maskDescription);
		getPropertyChangeSupport().firePropertyChange("isValid", wasValid, isValid());
		getPropertyChangeSupport().firePropertyChange("errorMessage", null, getErrorMessage());
	}

	public Mask getMask() {
		return mask;
	}

	public void setMask(Mask mask) {
		this.mask = mask;
	}

}
