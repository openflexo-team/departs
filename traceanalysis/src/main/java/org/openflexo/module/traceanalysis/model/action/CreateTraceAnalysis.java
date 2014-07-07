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

import java.io.File;
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
import org.openflexo.foundation.technologyadapter.ModelSlot;
import org.openflexo.foundation.technologyadapter.TechnologyAdapter;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterResource;
import org.openflexo.foundation.technologyadapter.TypeAwareModelSlot;
import org.openflexo.foundation.view.VirtualModelInstance;
import org.openflexo.foundation.view.VirtualModelModelSlotInstance;
import org.openflexo.foundation.view.action.CreateBasicVirtualModelInstance;
import org.openflexo.foundation.view.action.ModelSlotInstanceConfiguration;
import org.openflexo.foundation.view.action.ModelSlotInstanceConfiguration.DefaultModelSlotInstanceConfigurationOption;
import org.openflexo.foundation.view.rm.VirtualModelInstanceResource;
import org.openflexo.foundation.viewpoint.VirtualModelModelSlot;
import org.openflexo.foundation.viewpoint.VirtualModelModelSlotInstanceConfiguration;
import org.openflexo.localization.FlexoLocalization;
import org.openflexo.module.traceanalysis.model.TraceAnalysis;
import org.openflexo.module.traceanalysis.model.TAEProject;
import org.openflexo.technologyadapter.cdl.CDLModelSlot;
import org.openflexo.technologyadapter.cdl.CDLTechnologyAdapter;
import org.openflexo.technologyadapter.fiacre.FiacreProgramSlot;
import org.openflexo.technologyadapter.fiacre.FiacreTechnologyAdapter;
import org.openflexo.technologyadapter.fiacre.virtualmodel.bindings.FiacreBindingFactory;
import org.openflexo.toolbox.StringUtils;

public class CreateTraceAnalysis extends FlexoAction<CreateTraceAnalysis, TAEProject, FlexoObject> {

	private static final Logger logger = Logger.getLogger(CreateTraceAnalysis.class.getPackage().getName());

	public static FlexoActionType<CreateTraceAnalysis, TAEProject, FlexoObject> actionType = new FlexoActionType<CreateTraceAnalysis, TAEProject, FlexoObject>(
			"create_trace_analysis", FlexoActionType.newMenu, FlexoActionType.defaultGroup, FlexoActionType.ADD_ACTION_TYPE) {

		/**
		 * Factory method
		 */
		@Override
		public CreateTraceAnalysis makeNewAction(TAEProject focusedObject, Vector<FlexoObject> globalSelection, FlexoEditor editor) {
			return new CreateTraceAnalysis(focusedObject, globalSelection, editor);
		}

		@Override
		public boolean isVisibleForSelection(TAEProject object, Vector<FlexoObject> globalSelection) {
			return true;
		}

		@Override
		public boolean isEnabledForSelection(TAEProject object, Vector<FlexoObject> globalSelection) {
			return true;
		}

	};

	static {
		FlexoObjectImpl.addActionForClass(CreateTraceAnalysis.actionType, TAEProject.class);
	}

	CreateTraceAnalysis(TAEProject focusedObject, Vector<FlexoObject> globalSelection, FlexoEditor editor) {
		super(actionType, focusedObject, globalSelection, editor);
	}

	@Override
	protected void doAction(Object context) throws SaveResourceException {

		logger.info("Create trace analysis");
		
		// Create Virtual Model instance for system
		CreateBasicVirtualModelInstance createSystem = CreateBasicVirtualModelInstance.actionType.makeNewEmbeddedAction(getFocusedObject().getTraceAnalysisView(), null, this);
		createSystem.setNewVirtualModelInstanceName(getTraceAnalysisName());
		createSystem.setNewVirtualModelInstanceTitle(getTraceAnalysisName());
		createSystem.setVirtualModel(getFocusedObject().getSystemVirtualModel());
						
		FreeModelSlotInstanceConfiguration<?, ?> systemConf = (FreeModelSlotInstanceConfiguration<?, ?>) createSystem.getModelSlotInstanceConfiguration(getFocusedObject().getSystemVirtualModel().getModelSlots(FiacreProgramSlot.class).get(0));
		systemConf.setOption(DefaultModelSlotInstanceConfigurationOption.SelectExistingResource);
		systemConf.setResource(getFiacre());	
		createSystem.doAction();
		VirtualModelInstance systemVM = createSystem.getNewVirtualModelInstance();
		
		// Create Virtual Model instance for context
		CreateBasicVirtualModelInstance createContext = CreateBasicVirtualModelInstance.actionType.makeNewEmbeddedAction(getFocusedObject().getTraceAnalysisView(), null, this);
		createContext.setNewVirtualModelInstanceName(getTraceAnalysisName());
		createContext.setNewVirtualModelInstanceTitle(getTraceAnalysisName());
		createContext.setVirtualModel(getFocusedObject().getContextVirtualModel());
		
		FreeModelSlotInstanceConfiguration<?, ?> contextsystemConf = (FreeModelSlotInstanceConfiguration<?, ?>) createContext.getModelSlotInstanceConfiguration(getFocusedObject().getContextVirtualModel().getModelSlots(CDLModelSlot.class).get(0));
		contextsystemConf.setOption(DefaultModelSlotInstanceConfigurationOption.SelectExistingResource);
		contextsystemConf.setResource(getCdl());
		VirtualModelModelSlotInstanceConfiguration contextsystemConf2 = (VirtualModelModelSlotInstanceConfiguration) createContext.getModelSlotInstanceConfiguration(getFocusedObject().getContextVirtualModel().getModelSlot("systemModelSlot"));
		contextsystemConf2.setOption(DefaultModelSlotInstanceConfigurationOption.SelectExistingVirtualModel);
		contextsystemConf2.setAddressedVirtualModelInstanceResource((VirtualModelInstanceResource)systemVM.getResource());
		createContext.doAction();
		VirtualModelInstance contextVM = createContext.getNewVirtualModelInstance();
		
		// Create Virtual Model instance for observer
		CreateBasicVirtualModelInstance createObserver = CreateBasicVirtualModelInstance.actionType.makeNewEmbeddedAction(getFocusedObject().getTraceAnalysisView(), null, this);
		createObserver.setNewVirtualModelInstanceName(getTraceAnalysisName());
		createObserver.setNewVirtualModelInstanceTitle(getTraceAnalysisName());
		createObserver.setVirtualModel(getFocusedObject().getObserverVirtualModel());
		
		FreeModelSlotInstanceConfiguration<?, ?> observerConf1 = (FreeModelSlotInstanceConfiguration<?, ?>) createObserver.getModelSlotInstanceConfiguration(getFocusedObject().getObserverVirtualModel().getModelSlots(CDLModelSlot.class).get(0));
		observerConf1.setOption(DefaultModelSlotInstanceConfigurationOption.SelectExistingResource);
		observerConf1.setResource(getCdl());
		VirtualModelModelSlotInstanceConfiguration observerConf2 = (VirtualModelModelSlotInstanceConfiguration) createObserver.getModelSlotInstanceConfiguration(getFocusedObject().getObserverVirtualModel().getModelSlot("systemModelSlot"));
		observerConf2.setOption(DefaultModelSlotInstanceConfigurationOption.SelectExistingVirtualModel);
		observerConf2.setAddressedVirtualModelInstanceResource((VirtualModelInstanceResource)systemVM.getResource());
		createObserver.doAction();
		VirtualModelInstance observerVM = createObserver.getNewVirtualModelInstance();
		
		CreateBasicVirtualModelInstance action = CreateBasicVirtualModelInstance.actionType.makeNewEmbeddedAction(getFocusedObject().getTraceAnalysisView(), null, this);
		action.setNewVirtualModelInstanceName(getTraceAnalysisName());
		action.setNewVirtualModelInstanceTitle(getTraceAnalysisName());
		action.setVirtualModel(getFocusedObject().getAnalyzeVirtualModel());
		
		VirtualModelModelSlotInstanceConfiguration contConf = (VirtualModelModelSlotInstanceConfiguration) action.getModelSlotInstanceConfiguration(getFocusedObject().getAnalyzeVirtualModel().getModelSlot("contextModelSlot"));
		contConf.setOption(DefaultModelSlotInstanceConfigurationOption.SelectExistingVirtualModel);
		contConf.setAddressedVirtualModelInstanceResource((VirtualModelInstanceResource)contextVM.getResource());
		VirtualModelModelSlotInstanceConfiguration sysConf = (VirtualModelModelSlotInstanceConfiguration) action.getModelSlotInstanceConfiguration(getFocusedObject().getAnalyzeVirtualModel().getModelSlot("systemModelSlot"));
		sysConf.setOption(DefaultModelSlotInstanceConfigurationOption.SelectExistingVirtualModel);
		sysConf.setAddressedVirtualModelInstanceResource((VirtualModelInstanceResource)systemVM.getResource());
		VirtualModelModelSlotInstanceConfiguration obsConf = (VirtualModelModelSlotInstanceConfiguration) action.getModelSlotInstanceConfiguration(getFocusedObject().getAnalyzeVirtualModel().getModelSlot("observerModelSlot"));
		obsConf.setOption(DefaultModelSlotInstanceConfigurationOption.SelectExistingVirtualModel);
		obsConf.setAddressedVirtualModelInstanceResource((VirtualModelInstanceResource)observerVM.getResource());
		action.doAction();
		VirtualModelInstance newVirtualModelInstance = action.getNewVirtualModelInstance();
		// Wrap into FreeModel
		try {
			traceAnalysis = getFocusedObject().getTraceAnalysis(newVirtualModelInstance);
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
	}
	
	public TechnologyAdapter getFiacreTechnologyAdapter(){
		return getServiceManager().getTechnologyAdapterService().getTechnologyAdapter(FiacreTechnologyAdapter.class);	
	}
	
	public TechnologyAdapter getCdlTechnologyAdapter(){
		return getServiceManager().getTechnologyAdapterService().getTechnologyAdapter(CDLTechnologyAdapter.class);	
	}
	
	protected FlexoResourceCenter<?> resourceCenter;
	private TechnologyAdapterResource fiacre;
	private TechnologyAdapterResource cdl;

	public TechnologyAdapterResource getFiacre() {
		return fiacre;
	}

	public void setFiacre(TechnologyAdapterResource fiacre) {
		this.fiacre = fiacre;
	}
	
	public TechnologyAdapterResource getCdl() {
		return cdl;
	}

	public void setCdl(TechnologyAdapterResource cdl) {
		this.cdl = cdl;
	}
	
	private TraceAnalysis traceAnalysis;
	
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

	private String errorMessage;
	
	private String traceAnalysisName;
	
	private String traceAnalysisDescription;

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

	public TraceAnalysis getTraceAnalysis() {
		return traceAnalysis;
	}

	public void setTraceAnalysis(TraceAnalysis traceAnalysis) {
		this.traceAnalysis = traceAnalysis;
	}

}
