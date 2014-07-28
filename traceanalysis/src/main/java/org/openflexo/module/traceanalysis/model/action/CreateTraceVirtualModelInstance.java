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
import org.openflexo.foundation.view.View;
import org.openflexo.foundation.view.VirtualModelInstance;
import org.openflexo.foundation.view.action.CreateBasicVirtualModelInstance;
import org.openflexo.foundation.view.action.CreateView;
import org.openflexo.foundation.view.action.ModelSlotInstanceConfiguration.DefaultModelSlotInstanceConfigurationOption;
import org.openflexo.foundation.view.rm.VirtualModelInstanceResource;
import org.openflexo.foundation.viewpoint.VirtualModelModelSlotInstanceConfiguration;
import org.openflexo.foundation.viewpoint.rm.ViewPointResource;
import org.openflexo.localization.FlexoLocalization;
import org.openflexo.module.traceanalysis.model.TraceAnalysisProject;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.technologyadapter.cdl.CDLModelSlot;
import org.openflexo.technologyadapter.cdl.CDLTechnologyAdapter;
import org.openflexo.technologyadapter.fiacre.FiacreProgramModelSlot;
import org.openflexo.technologyadapter.fiacre.FiacreTechnologyAdapter;
import org.openflexo.technologyadapter.trace.TraceModelSlot;
import org.openflexo.technologyadapter.trace.TraceTechnologyAdapter;
import org.openflexo.toolbox.StringUtils;

public class CreateTraceVirtualModelInstance extends FlexoAction<CreateTraceVirtualModelInstance, TraceAnalysisProject, FlexoObject> {

	private static final Logger logger = Logger.getLogger(CreateTraceVirtualModelInstance.class.getPackage().getName());

	public static FlexoActionType<CreateTraceVirtualModelInstance, TraceAnalysisProject, FlexoObject> actionType = new FlexoActionType<CreateTraceVirtualModelInstance, TraceAnalysisProject, FlexoObject>(
			"create_trace_analysis", FlexoActionType.newMenu, FlexoActionType.defaultGroup, FlexoActionType.ADD_ACTION_TYPE) {

		/**
		 * Factory method
		 */
		@Override
		public CreateTraceVirtualModelInstance makeNewAction(TraceAnalysisProject focusedObject, Vector<FlexoObject> globalSelection, FlexoEditor editor) {
			return new CreateTraceVirtualModelInstance(focusedObject, globalSelection, editor);
		}

		@Override
		public boolean isVisibleForSelection(TraceAnalysisProject object, Vector<FlexoObject> globalSelection) {
			return true;
		}

		@Override
		public boolean isEnabledForSelection(TraceAnalysisProject object, Vector<FlexoObject> globalSelection) {
			return true;
		}

	};

	static {
		FlexoObjectImpl.addActionForClass(CreateTraceVirtualModelInstance.actionType, TraceAnalysisProject.class);
	}

	CreateTraceVirtualModelInstance(TraceAnalysisProject focusedObject, Vector<FlexoObject> globalSelection, FlexoEditor editor) {
		super(actionType, focusedObject, globalSelection, editor);
	}
	
	protected FlexoResourceCenter<?> resourceCenter;
	private TechnologyAdapterResource trace;
	private String errorMessage;
	private String traceAnalysisName;
	private String traceAnalysisDescription;
	private TraceVirtualModelInstance traceVirtualModelInstance;
	

	@Override
	protected void doAction(Object context) throws SaveResourceException {

		logger.info("Create trace virtual model instance");
		
		
		
		// Create virtual model for trace
		VirtualModelInstance traceVM = createTraceVirtualModelInstance();
		
		traceVirtualModelInstance = getFocusedObject().getTraceVirtualModelInstance(traceVM);
	}
	
	public TechnologyAdapter getFiacreTechnologyAdapter(){
		return getServiceManager().getTechnologyAdapterService().getTechnologyAdapter(FiacreTechnologyAdapter.class);	
	}
	
	public TechnologyAdapter getCdlTechnologyAdapter(){
		return getServiceManager().getTechnologyAdapterService().getTechnologyAdapter(CDLTechnologyAdapter.class);	
	}
	
	public TechnologyAdapter getTraceTechnologyAdapter(){
		return getServiceManager().getTechnologyAdapterService().getTechnologyAdapter(TraceTechnologyAdapter.class);	
	}
	
	public TechnologyAdapterResource getTrace() {
		return trace;
	}

	public void setTrace(TechnologyAdapterResource trace) {
		this.trace = trace;
	}
	
	public String getTraceAnalysisName() {
		return traceAnalysisName;
	}

	public void setTraceAnalysisName(String traceAnalysisName) {
		boolean wasValid = isValid();
		this.traceAnalysisName = traceAnalysisName;
		getPropertyChangeSupport().firePropertyChange("traceAnalysisName", null, traceAnalysisName);
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

		if (StringUtils.isEmpty(traceAnalysisName)) {
			errorMessage = FlexoLocalization.localizedForKey("no_trace_analysis_name_defined");
			return false;
		}

		return true;
	}

	public String getTraceAnalysisDescription() {
		return traceAnalysisDescription;
	}

	public void setTraceAnalysisDescription(String traceAnalysisDescription) {
		boolean wasValid = isValid();
		this.traceAnalysisDescription = traceAnalysisDescription;
		getPropertyChangeSupport().firePropertyChange("traceAnalysisDescription", null, traceAnalysisDescription);
		getPropertyChangeSupport().firePropertyChange("isValid", wasValid, isValid());
		getPropertyChangeSupport().firePropertyChange("errorMessage", null, getErrorMessage());
	}

	public TraceVirtualModelInstance getTraceVirtualModelInstance() {
		return traceVirtualModelInstance;
	}

	public void setTraceVirtualModelInstance(TraceVirtualModelInstance traceVirtualModelInstance) {
		this.traceVirtualModelInstance = traceVirtualModelInstance;
	}

	private VirtualModelInstance createTraceVirtualModelInstance(){
		CreateBasicVirtualModelInstance createTrace = CreateBasicVirtualModelInstance.actionType.makeNewEmbeddedAction(getFocusedObject().getTraceAnalysisView(), null, this);
		createTrace.setNewVirtualModelInstanceName(getTraceAnalysisName());
		createTrace.setNewVirtualModelInstanceTitle(getTraceAnalysisName());
		createTrace.setVirtualModel(getFocusedObject().getTraceVirtualModel());
		
		VirtualModelModelSlotInstanceConfiguration contConf = (VirtualModelModelSlotInstanceConfiguration) createTrace.getModelSlotInstanceConfiguration(getFocusedObject().getTraceVirtualModel().getModelSlot("contextVirtualModel"));
		contConf.setOption(DefaultModelSlotInstanceConfigurationOption.SelectExistingVirtualModel);
		contConf.setAddressedVirtualModelInstanceResource((VirtualModelInstanceResource)getFocusedObject().getContextVirtualModelInstance().getVirtualModelInstance().getResource());
		VirtualModelModelSlotInstanceConfiguration sysConf = (VirtualModelModelSlotInstanceConfiguration) createTrace.getModelSlotInstanceConfiguration(getFocusedObject().getTraceVirtualModel().getModelSlot("systemVirtualModel"));
		sysConf.setOption(DefaultModelSlotInstanceConfigurationOption.SelectExistingVirtualModel);
		sysConf.setAddressedVirtualModelInstanceResource((VirtualModelInstanceResource)getFocusedObject().getSystemVirtualModelInstance().getVirtualModelInstance().getResource());
		VirtualModelModelSlotInstanceConfiguration obsConf = (VirtualModelModelSlotInstanceConfiguration) createTrace.getModelSlotInstanceConfiguration(getFocusedObject().getTraceVirtualModel().getModelSlot("observerVirtualModel"));
		obsConf.setOption(DefaultModelSlotInstanceConfigurationOption.SelectExistingVirtualModel);
		obsConf.setAddressedVirtualModelInstanceResource((VirtualModelInstanceResource)getFocusedObject().getObserverVirtualModelInstance().getVirtualModelInstance().getResource());
		FreeModelSlotInstanceConfiguration<?, ?> traceConf = (FreeModelSlotInstanceConfiguration<?, ?>) createTrace.getModelSlotInstanceConfiguration(getFocusedObject().getTraceVirtualModel().getModelSlots(TraceModelSlot.class).get(0));
		traceConf.setOption(DefaultModelSlotInstanceConfigurationOption.SelectExistingResource);
		traceConf.setResource(getTrace());
		createTrace.doAction();
		return createTrace.getNewVirtualModelInstance();
	}
	
}
