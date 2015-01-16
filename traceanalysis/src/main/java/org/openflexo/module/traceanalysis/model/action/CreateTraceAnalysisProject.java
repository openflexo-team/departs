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
import org.openflexo.foundation.FlexoObject.FlexoObjectImpl;
import org.openflexo.foundation.action.FlexoAction;
import org.openflexo.foundation.action.FlexoActionType;
import org.openflexo.foundation.fml.rm.ViewPointResource;
import org.openflexo.foundation.fml.rt.FMLRTModelSlotInstanceConfiguration;
import org.openflexo.foundation.fml.rt.VirtualModelInstance;
import org.openflexo.foundation.fml.rt.action.CreateBasicVirtualModelInstance;
import org.openflexo.foundation.fml.rt.action.CreateView;
import org.openflexo.foundation.fml.rt.action.ModelSlotInstanceConfiguration.DefaultModelSlotInstanceConfigurationOption;
import org.openflexo.foundation.fml.rt.rm.VirtualModelInstanceResource;
import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.SaveResourceException;
import org.openflexo.foundation.technologyadapter.FreeModelSlotInstanceConfiguration;
import org.openflexo.foundation.technologyadapter.TechnologyAdapter;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterResource;
import org.openflexo.localization.FlexoLocalization;
import org.openflexo.module.traceanalysis.model.TraceAnalysisProject;
import org.openflexo.technologyadapter.cdl.CDLModelSlot;
import org.openflexo.technologyadapter.cdl.CDLTechnologyAdapter;
import org.openflexo.technologyadapter.fiacre.FiacreProgramModelSlot;
import org.openflexo.technologyadapter.fiacre.FiacreTechnologyAdapter;

public class CreateTraceAnalysisProject extends FlexoAction<CreateTraceAnalysisProject, TraceAnalysisProject, FlexoObject> {

	private static final Logger logger = Logger.getLogger(CreateTraceAnalysisProject.class.getPackage().getName());

	public static FlexoActionType<CreateTraceAnalysisProject, TraceAnalysisProject, FlexoObject> actionType = new FlexoActionType<CreateTraceAnalysisProject, TraceAnalysisProject, FlexoObject>(
			"create_trace_analysis_project", FlexoActionType.newMenu, FlexoActionType.defaultGroup, FlexoActionType.ADD_ACTION_TYPE) {

		/**
		 * Factory method
		 */
		@Override
		public CreateTraceAnalysisProject makeNewAction(TraceAnalysisProject focusedObject, Vector<FlexoObject> globalSelection,
				FlexoEditor editor) {
			return new CreateTraceAnalysisProject(focusedObject, globalSelection, editor);
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
		FlexoObjectImpl.addActionForClass(CreateTraceAnalysisProject.actionType, TraceAnalysisProject.class);
	}

	CreateTraceAnalysisProject(TraceAnalysisProject focusedObject, Vector<FlexoObject> globalSelection, FlexoEditor editor) {
		super(actionType, focusedObject, globalSelection, editor);
	}

	protected FlexoResourceCenter<?> resourceCenter;
	private TechnologyAdapterResource fiacre;
	private TechnologyAdapterResource cdl;
	private String errorMessage;
	private String projectName;
	private String traceAnalysisDescription;

	@Override
	protected void doAction(Object context) throws SaveResourceException {

		logger.info("Create trace analysis");
		setProjectName(getFocusedObject().getName());
		// Create the view
		CreateView createView = CreateView.actionType.makeNewAction(getEditor().getProject().getViewLibrary().getRootFolder(), null,
				getEditor());
		createView.setNewViewName(getProjectName() + "_view");
		createView.setNewViewTitle("View for " + getProjectName());
		createView.setViewpointResource((ViewPointResource) getFocusedObject().getTraceAnaylsisViewPoint().getResource());
		createView.doAction();

		// Create Virtual Model instance for system
		VirtualModelInstance systemVM = createSystemVirtualModelInstance(createView);

		// Create Virtual Model instance for context
		VirtualModelInstance contextVM = createContextVirtualModelInstance(createView, systemVM);

		// Create Virtual Model instance for observer
		VirtualModelInstance observerVM = createObserverVirtualModelInstance(createView, contextVM, systemVM);

		/*createView.getNewView().addToVirtualModelInstances(systemVM);
		createView.getNewView().addToVirtualModelInstances(contextVM);
		createView.getNewView().addToVirtualModelInstances(observerVM);*/
		getFocusedObject().setTraceAnalysisView(createView.getNewView());
	}

	public TechnologyAdapter getFiacreTechnologyAdapter() {
		return getServiceManager().getTechnologyAdapterService().getTechnologyAdapter(FiacreTechnologyAdapter.class);
	}

	public TechnologyAdapter getCdlTechnologyAdapter() {
		return getServiceManager().getTechnologyAdapterService().getTechnologyAdapter(CDLTechnologyAdapter.class);
	}

	public TechnologyAdapterResource getFiacre() {
		return fiacre;
	}

	public void setFiacre(TechnologyAdapterResource fiacre) {
		boolean wasValid = isValid();
		this.fiacre = fiacre;
		getPropertyChangeSupport().firePropertyChange("fiacre", null, fiacre);
		getPropertyChangeSupport().firePropertyChange("isValid", wasValid, isValid());
		getPropertyChangeSupport().firePropertyChange("errorMessage", null, getErrorMessage());
	}

	public TechnologyAdapterResource getCdl() {
		return cdl;
	}

	public void setCdl(TechnologyAdapterResource cdl) {
		boolean wasValid = isValid();
		this.cdl = cdl;
		getPropertyChangeSupport().firePropertyChange("cdl", null, cdl);
		getPropertyChangeSupport().firePropertyChange("isValid", wasValid, isValid());
		getPropertyChangeSupport().firePropertyChange("errorMessage", null, getErrorMessage());
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getErrorMessage() {
		isValid();
		// System.out.println("valid=" + isValid());
		// System.out.println("errorMessage=" + errorMessage);
		return errorMessage;
	}

	@Override
	public boolean isValid() {

		/*if (StringUtils.isEmpty(traceAnalysisName)) {
			errorMessage = FlexoLocalization.localizedForKey("no_trace_analysis_name_defined");
			return false;
		}*/
		if (cdl == null) {
			errorMessage = FlexoLocalization.localizedForKey("no_cdl_file");
			return false;
		}
		if (fiacre == null) {
			errorMessage = FlexoLocalization.localizedForKey("no_fiacre_file");
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

	private VirtualModelInstance createSystemVirtualModelInstance(CreateView createView) {
		CreateBasicVirtualModelInstance createSystem = CreateBasicVirtualModelInstance.actionType.makeNewEmbeddedAction(
				createView.getNewView(), null, this);
		createSystem.setNewVirtualModelInstanceName(projectName);
		createSystem.setNewVirtualModelInstanceTitle(projectName);
		createSystem.setVirtualModel(getFocusedObject().getSystemVirtualModel());
		FreeModelSlotInstanceConfiguration<?, ?> systemConf = (FreeModelSlotInstanceConfiguration<?, ?>) createSystem
				.getModelSlotInstanceConfiguration(getFocusedObject().getSystemVirtualModel().getModelSlots(FiacreProgramModelSlot.class)
						.get(0));
		systemConf.setOption(DefaultModelSlotInstanceConfigurationOption.SelectExistingResource);
		systemConf.setResource(getFiacre());
		createSystem.doAction();
		return createSystem.getNewVirtualModelInstance();
	}

	private VirtualModelInstance createContextVirtualModelInstance(CreateView createView, VirtualModelInstance systemVM) {
		CreateBasicVirtualModelInstance createContext = CreateBasicVirtualModelInstance.actionType.makeNewEmbeddedAction(
				createView.getNewView(), null, this);
		createContext.setNewVirtualModelInstanceName(projectName);
		createContext.setNewVirtualModelInstanceTitle(projectName);
		createContext.setVirtualModel(getFocusedObject().getContextVirtualModel());
		FreeModelSlotInstanceConfiguration<?, ?> contextsystemConf = (FreeModelSlotInstanceConfiguration<?, ?>) createContext
				.getModelSlotInstanceConfiguration(getFocusedObject().getContextVirtualModel().getModelSlots(CDLModelSlot.class).get(0));
		contextsystemConf.setOption(DefaultModelSlotInstanceConfigurationOption.SelectExistingResource);
		contextsystemConf.setResource(getCdl());
		FMLRTModelSlotInstanceConfiguration contextsystemConf2 = (FMLRTModelSlotInstanceConfiguration) createContext
				.getModelSlotInstanceConfiguration(getFocusedObject().getContextVirtualModel().getModelSlot("systemModelSlot"));
		contextsystemConf2.setOption(DefaultModelSlotInstanceConfigurationOption.SelectExistingVirtualModel);
		contextsystemConf2.setAddressedVirtualModelInstanceResource((VirtualModelInstanceResource) systemVM.getResource());
		createContext.doAction();
		return createContext.getNewVirtualModelInstance();
	}

	private VirtualModelInstance createObserverVirtualModelInstance(CreateView createView, VirtualModelInstance contextVM,
			VirtualModelInstance systemVM) {
		CreateBasicVirtualModelInstance createObserver = CreateBasicVirtualModelInstance.actionType.makeNewEmbeddedAction(
				createView.getNewView(), null, this);
		createObserver.setNewVirtualModelInstanceName(projectName);
		createObserver.setNewVirtualModelInstanceTitle(projectName);
		createObserver.setVirtualModel(getFocusedObject().getObserverVirtualModel());
		FreeModelSlotInstanceConfiguration<?, ?> observerConf1 = (FreeModelSlotInstanceConfiguration<?, ?>) createObserver
				.getModelSlotInstanceConfiguration(getFocusedObject().getObserverVirtualModel().getModelSlots(CDLModelSlot.class).get(0));
		observerConf1.setOption(DefaultModelSlotInstanceConfigurationOption.SelectExistingResource);
		observerConf1.setResource(getCdl());
		FMLRTModelSlotInstanceConfiguration observerConf2 = (FMLRTModelSlotInstanceConfiguration) createObserver
				.getModelSlotInstanceConfiguration(getFocusedObject().getObserverVirtualModel().getModelSlot("systemModelSlot"));
		observerConf2.setOption(DefaultModelSlotInstanceConfigurationOption.SelectExistingVirtualModel);
		observerConf2.setAddressedVirtualModelInstanceResource((VirtualModelInstanceResource) systemVM.getResource());
		createObserver.doAction();
		return createObserver.getNewVirtualModelInstance();
	}

}
