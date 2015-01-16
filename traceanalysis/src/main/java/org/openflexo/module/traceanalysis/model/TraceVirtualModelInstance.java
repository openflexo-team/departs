/*
 * (c) Copyright 2013-2014 Openflexo
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
package org.openflexo.module.traceanalysis.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openflexo.antar.binding.TypeUtils;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.InvalidArgumentException;
import org.openflexo.foundation.fml.FlexoConcept;
import org.openflexo.foundation.fml.rt.FlexoConceptInstance;
import org.openflexo.foundation.fml.rt.FreeModelSlotInstance;
import org.openflexo.foundation.fml.rt.VirtualModelInstance;
import org.openflexo.module.traceanalysis.model.mask.Mask;
import org.openflexo.module.traceanalysis.model.mask.MaskableBehaviourObject;
import org.openflexo.module.traceanalysis.model.mask.MaskableConfiguration;
import org.openflexo.module.traceanalysis.model.mask.MaskableElement;
import org.openflexo.module.traceanalysis.model.mask.MaskableFederatedElement;
import org.openflexo.module.traceanalysis.model.mask.MaskableMessage;
import org.openflexo.module.traceanalysis.model.mask.MaskableTransition;
import org.openflexo.module.traceanalysis.model.mask.MaskableVariable;
import org.openflexo.technologyadapter.cdl.model.CDLObserverState;
import org.openflexo.technologyadapter.cdl.model.CDLParActivity;
import org.openflexo.technologyadapter.cdl.model.CDLProperty;
import org.openflexo.technologyadapter.fiacre.model.FiacreProcess;
import org.openflexo.technologyadapter.fiacre.model.FiacreState;
import org.openflexo.technologyadapter.fiacre.model.FiacreVariable;
import org.openflexo.technologyadapter.trace.model.OBPTrace;
import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObjectInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceConfiguration;
import org.openflexo.technologyadapter.trace.model.OBPTraceContextState;
import org.openflexo.technologyadapter.trace.model.OBPTraceData;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessage;
import org.openflexo.technologyadapter.trace.model.OBPTraceObject;
import org.openflexo.technologyadapter.trace.model.OBPTraceProcessState;
import org.openflexo.technologyadapter.trace.model.OBPTracePropertyState;
import org.openflexo.technologyadapter.trace.model.OBPTraceState;
import org.openflexo.technologyadapter.trace.model.OBPTraceTransition;
import org.openflexo.technologyadapter.trace.model.OBPTraceVariable;

public class TraceVirtualModelInstance extends TraceAnalysisVirtualModelInstance {

	private static final Logger logger = Logger.getLogger(TraceVirtualModelInstance.class.getPackage().getName());

	// The trace is a set of configurations and transitions
	private OBPTrace trace;
	// A Set of elements came from diffents sources and which are here federated
	private List<MaskableFederatedElement> federatedElements;
	// A Set of configurations and transitions
	private List<MaskableConfiguration> configurationElements;
	private List<MaskableTransition> transitionElements;
	// A list of masks
	private List<Mask> masks;
	// A selected mask
	private Mask selectedMask;

	private List<MaskableElement> maskableElements;

	public TraceVirtualModelInstance(VirtualModelInstance virtualModelInstance, TraceAnalysisProject traceAnalysisProject)
			throws InvalidArgumentException {
		super(virtualModelInstance, traceAnalysisProject);

		maskableElements = new ArrayList<MaskableElement>();

		// Connect the system/context/observer elements with trace elements, throught federated maskable elements
		federate();

		// Create maskable elements for configuration
		for (OBPTraceConfiguration configuration : getConfigurations()) {
			maskableElements.add(getMaskableElement(configuration));
		}
		// Create maskable elements for transition
		for (OBPTraceTransition transition : getTransitions()) {
			maskableElements.add(getMaskableElement(transition));
		}
		// Retrive the masks for this trace analysis
		for (FlexoConceptInstance fciMask : getVirtualModelInstance().getFlexoConceptInstances(getMaskFlexoConcept())) {
			Mask mask = new Mask(fciMask, traceAnalysisProject, this);
			getMasks().add(mask);
		}
	}

	// Get the trace from the model slot
	public OBPTrace getTraceOBP() {
		if (trace == null) {
			FreeModelSlotInstance msi = (FreeModelSlotInstance) getVirtualModelInstance().getModelSlotInstance("traceModelSlot");
			trace = (OBPTrace) msi.getAccessedResourceData();
		}
		return trace;
	}

	// Return a list of MaskableFederated Elements only
	public List<MaskableFederatedElement> getFederatedElements() {
		if (federatedElements == null) {
			federatedElements = new ArrayList<MaskableFederatedElement>();
			for (MaskableElement element : maskableElements) {
				if (element instanceof MaskableFederatedElement) {
					federatedElements.add((MaskableFederatedElement) element);
				}
			}
		}
		return federatedElements;
	}

	// Return a list of Maskable Configurations only
	public List<MaskableConfiguration> getMaskedConfigurations() {
		if (configurationElements == null) {
			configurationElements = new ArrayList<MaskableConfiguration>();
			for (MaskableElement element : maskableElements) {
				if (element instanceof MaskableConfiguration) {
					configurationElements.add((MaskableConfiguration) element);
				}
			}
		}
		return configurationElements;
	}

	// Return a list of Maskable Transition only
	public List<MaskableTransition> getMaskableTransitions() {
		if (transitionElements == null) {
			transitionElements = new ArrayList<MaskableTransition>();
			for (MaskableElement element : maskableElements) {
				if (element instanceof MaskableTransition) {
					transitionElements.add((MaskableTransition) element);
				}
			}
		}
		return transitionElements;
	}

	// Return a MaskableConfiguration for a given configuration, or create it if not exists
	public MaskableConfiguration getMaskableElement(OBPTraceConfiguration object) {
		for (MaskableElement element : getMaskableElements()) {
			if (element.getValue().equals(object)) {
				return (MaskableConfiguration) element;
			}
		}
		return createMaskableConfiguration(object);
	}

	// Return a MaskableTransition for a given transition, or create it if not exists
	public MaskableTransition getMaskableElement(OBPTraceTransition object) {
		for (MaskableElement element : getMaskableElements()) {
			if (element.getValue().equals(object)) {
				return (MaskableTransition) element;
			}
		}
		return createMaskableTransition(object);
	}

	// Return a MaskableFederatedElement for a given object, or create it if not exists
	public MaskableFederatedElement getMaskableElement(OBPTraceObject object) {
		for (MaskableElement maskableElement : getMaskableElements()) {
			if (maskableElement instanceof MaskableFederatedElement && maskableElement.getValue().equals(object)) {
				return (MaskableFederatedElement) maskableElement;
			}
		}
		return null;
	}

	// Return a List of MaskableFederatedElement for a given type
	public List<MaskableFederatedElement> getFilteredFederatedElement(Class<?> objectKind) {
		List<MaskableFederatedElement> filteredElements = new ArrayList<MaskableFederatedElement>();
		for (MaskableFederatedElement object : getFederatedElements()) {
			if (TypeUtils.isAssignableTo(object.getValue(), objectKind)) {
				filteredElements.add(object);
			} else if (TypeUtils.isAssignableTo(object.getType(), objectKind)) {
				filteredElements.add(object);
			}
		}
		return filteredElements;
	}

	// Get the whole set of Transitions
	public List<OBPTraceTransition> getTransitions() {
		return getTraceOBP().getTransitions();
	}

	// Get the whole set of Configurations
	public List<OBPTraceConfiguration> getConfigurations() {
		return getTraceOBP().getConfigurations();
	}

	// Get the configuration from its ID
	public OBPTraceConfiguration getConfiguration(int id) {
		return getTraceOBP().getConfigurations().get(id);
	}

	// Get the value of a configuration's object
	public String getConfigurationValue(FlexoObject object, int config) {
		OBPTraceConfiguration configuration = getTraceOBP().getConfigurations().get(config);
		return getConfigurationValue(object, configuration);
	}

	// Get the value of a configuration's object
	public String getConfigurationValue(FlexoObject object, OBPTraceConfiguration configuration) {
		if (getConfigurationObject(object, configuration) != null) {
			return getConfigurationObject(object, configuration).getValue();
		} else {
			return null;
		}
	}

	// Get the object of a configuration
	public OBPTraceObject getConfigurationObject(FlexoObject object, int config) {
		OBPTraceConfiguration configuration = getTraceOBP().getConfigurations().get(config);
		return getConfigurationObject(object, configuration);
	}

	// Get the object of a configuration
	public OBPTraceObject getConfigurationObject(FlexoObject object, OBPTraceConfiguration configuration) {
		if (object instanceof FiacreProcess) {
			return getProcessObject((FiacreProcess) object, configuration);
		} else if (object instanceof FiacreVariable) {
			return getVariableObject((FiacreVariable) object, configuration);
		} else if (object instanceof FiacreState) {
			return getStateObject((FiacreState) object, configuration);
		} else if (object instanceof CDLProperty) {
			return getPropertyObject((CDLProperty) object, configuration);
		} else if (object instanceof CDLParActivity) {
			return getContextObject((CDLParActivity) object, configuration);
		} else if (object instanceof CDLObserverState) {
			return getObserverStateObject((CDLObserverState) object, configuration);
		}
		logger.log(Level.WARNING, "No value found for object " + object.toString());
		return null;
	}

	public OBPTraceContextState getContextObject(CDLParActivity cdlParActivity, OBPTraceConfiguration configuration) {
		OBPTraceContextState context = configuration.getOBPTraceContextState();
		if (context == null) {
			logger.log(Level.WARNING, "No value found for context " + context.getName());
			return null;
		} else {
			return context;
		}
	}

	public OBPTracePropertyState getPropertyObject(CDLProperty property, OBPTraceConfiguration configuration) {
		for (OBPTracePropertyState traceProperty : configuration.getOBPTracePropertyState()) {
			if (traceProperty.getOBPTraceBehaviourObjectInstance().getType().equals(property.getName())) {
				return traceProperty;
			}
		}
		logger.log(Level.WARNING, "No value found for property " + property.getName());
		return null;
	}

	public OBPTraceProcessState getProcessObject(FiacreProcess process, OBPTraceConfiguration configuration) {
		for (OBPTraceProcessState traceProcess : configuration.getOBPTraceProcessState()) {
			if (traceProcess.getOBPTraceBehaviourObjectInstance().getType().equals(process.getName())) {
				return traceProcess;
			}
		}
		logger.log(Level.WARNING, "No value found for process " + process.getName());
		return null;
	}

	public OBPTraceData getVariableObject(FiacreVariable variable, OBPTraceConfiguration configuration) {
		for (OBPTraceData traceData : getProcessObject(variable.getFiacreProcess(), configuration).getOBPTraceData()) {
			if (traceData.getName().equals(variable.getName())) {
				return traceData;
			}
		}
		logger.log(Level.WARNING, "No value found for variable " + variable.getName());
		return null;
	}

	public OBPTraceState getStateObject(FiacreState state, OBPTraceConfiguration configuration) {
		if (getProcessObject(state.getFiacreProcess(), configuration).getState() != null
				&& getProcessObject(state.getFiacreProcess(), configuration).getState().getName().equals(state.getName())) {
			return getProcessObject(state.getFiacreProcess(), configuration).getState();
		}
		logger.log(Level.WARNING, "No value found for state " + state.getName());
		return null;
	}

	public OBPTraceState getObserverStateObject(CDLObserverState state, OBPTraceConfiguration configuration) {
		if (getPropertyObject(state.getCDLObserver(), configuration).getState() != null
				&& getPropertyObject(state.getCDLObserver(), configuration).getState().getName().equals(state.getName())) {
			return getPropertyObject(state.getCDLObserver(), configuration).getState();
		}
		logger.log(Level.WARNING, "No value found for state " + state.getName());
		return null;
	}

	public List<Mask> getMasks() {
		if (masks == null) {
			masks = new ArrayList<Mask>();
		}
		return masks;
	}

	public Mask getMask(String name) {
		for (Mask mask : masks) {
			if (mask.getName().equals(name)) {
				return mask;
			}
		}
		logger.log(Level.SEVERE, "No mask named " + name + " for trace " + getName());
		return null;
	}

	public void setMasks(List<Mask> masks) {
		this.masks = masks;
	}

	public Mask getNewMask() {
		FlexoConceptInstance newFlexoConceptInstance = getVirtualModelInstance().makeNewFlexoConceptInstance(getMaskFlexoConcept());
		return getMask(newFlexoConceptInstance);
	}

	public Mask getMask(FlexoConceptInstance flexoConceptInstance) {
		for (Mask mask : getMasks()) {
			if (mask.getFlexoConceptInstance().equals(flexoConceptInstance)) {
				return mask;
			}
		}
		Mask mask = new Mask(flexoConceptInstance, getTraceAnalysisProject(), this);
		getMasks().add(mask);
		getPropertyChangeSupport().firePropertyChange("masks", null, this);
		return mask;
	}

	public Mask getSelectedMask() {
		return selectedMask;
	}

	public void setSelectedMask(Mask selectedMask) {
		this.selectedMask = selectedMask;
		getPropertyChangeSupport().firePropertyChange("selectedMask", null, selectedMask);
	}

	public List<MaskableElement> getMaskableElements() {
		return maskableElements;
	}

	public void setMaskableElements(List<MaskableElement> maskableElements) {
		this.maskableElements = maskableElements;
	}

	private void federate() {
		federateBehaviourObjects();
	}

	/**
	 * Create associations among all OBP resources which would'nt be effecient through viewpoints
	 */
	private void federateBehaviourObjects() {
		// Associate BehaviourObject instance with their corresponding System or context element
		for (OBPTraceBehaviourObjectInstance behaviourObjectInstance : getTraceOBP().getOBPTraceBehaviourObjectInstances()) {
			MaskableBehaviourObject element = new MaskableBehaviourObject(
					getFlexoBehaviourObjectObjectNamed(behaviourObjectInstance.getType()), behaviourObjectInstance, null, this);
			// Associate Data Types with their corresponding System or context element
			federateVariables(element);
		}
	}

	private void federateVariables(MaskableFederatedElement element) {
		for (OBPTraceVariable variable : ((OBPTraceBehaviourObjectInstance) element.getValue()).getVariables()) {
			FlexoObject object = getVariableFlexoObjectNamed(variable.getName(), element.getType());
			MaskableVariable var = new MaskableVariable(object, variable, element, this);
			element.addChild(var);
		}
	}

	private MaskableConfiguration createMaskableConfiguration(OBPTraceConfiguration object) {
		MaskableConfiguration newMaskedElement = new MaskableConfiguration(object, null, this);
		for (MaskableFederatedElement element : getFederatedElements()) {
			if (element.getValue() instanceof OBPTraceBehaviourObjectInstance) {
				newMaskedElement.addChild(element);
			}
		}
		return newMaskedElement;
	}

	private MaskableTransition createMaskableTransition(OBPTraceTransition object) {
		MaskableTransition newMaskedElement = new MaskableTransition(object, null, this);
		newMaskedElement.setSourceConfiguration(getMaskableElement(object.getSourceOBPTraceConfiguration()));
		newMaskedElement.setTargetConfiguration(getMaskableElement(object.getTargetOBPTraceConfiguration()));
		for (OBPTraceMessage message : object.getOBPTraceMessages()) {
			newMaskedElement.addChild(createMaskableMessage(message));
		}

		return newMaskedElement;
	}

	private MaskableMessage createMaskableMessage(OBPTraceMessage object) {
		MaskableMessage newMaskedElement = new MaskableMessage(object, null, this);
		return newMaskedElement;
	}

	private FlexoObject getFlexoBehaviourObjectObjectNamed(String name) {
		if (name.equals("env")) {
			return getTraceAnalysisProject().getContextVirtualModelInstance();
		}
		for (CDLProperty property : getTraceAnalysisProject().getObserverVirtualModelInstance().getCDLProperties()) {
			if (property != null && property.getName().equals(name)) {
				return property;
			}
		}
		for (FiacreProcess process : getTraceAnalysisProject().getSystemVirtualModelInstance().getFiacreProcesses()) {
			if (process != null && process.getName().equals(name)) {
				return process;
			}
		}
		logger.log(Level.WARNING, "No object named " + name + " found");
		return null;
	}

	private FlexoConcept getMaskFlexoConcept() {
		return getVirtualModelInstance().getVirtualModel().getFlexoConcept("ConfigurationMask");
	}

	private FlexoObject getVariableFlexoObjectNamed(String name, FlexoObject object) {
		if (object instanceof FiacreProcess) {
			return ((FiacreProcess) object).getFiacreVariableNamed(name);
		}
		// TODO (context...)
		return null;
	}

}
