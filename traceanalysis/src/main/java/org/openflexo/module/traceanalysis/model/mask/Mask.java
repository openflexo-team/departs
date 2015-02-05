package org.openflexo.module.traceanalysis.model.mask;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.openflexo.connie.type.TypeUtils;
import org.openflexo.foundation.DefaultFlexoObject;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.fml.PrimitiveRole;
import org.openflexo.foundation.fml.rt.FlexoConceptInstance;
import org.openflexo.module.traceanalysis.model.TraceAnalysisProject;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObjectInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceConfiguration;
import org.openflexo.technologyadapter.trace.model.OBPTraceObject;
import org.openflexo.technologyadapter.trace.model.OBPTraceVariable;

/**
 * A Mask reference a selection of masked elements that should be filterer.
 * 
 * @author Vincent
 *
 */
public class Mask extends DefaultFlexoObject implements PropertyChangeListener {

	private final FlexoConceptInstance flexoConceptInstance;
	private final String name;
	private static String NAME = "name";
	private final List<MaskableElement> selection;
	private final TraceAnalysisProject project;
	private final TraceVirtualModelInstance trace;
	private List<OBPTraceConfiguration> relevantConfigs;
	private boolean computingRequired = true;

	public Mask(FlexoConceptInstance flexoConceptInstance, TraceAnalysisProject project, TraceVirtualModelInstance trace) {
		super();
		this.flexoConceptInstance = flexoConceptInstance;
		name = flexoConceptInstance.getFlexoActor(NAME);
		selection = new ArrayList<MaskableElement>();
		this.project = project;
		this.trace = trace;
	}

	public TraceAnalysisProject getTraceAnalysisProject() {
		return project;
	}

	public TraceVirtualModelInstance getTrace() {
		return trace;
	}

	public String getName() {
		return flexoConceptInstance.getFlexoActor(NAME);
	}

	public void setName(String name) {
		flexoConceptInstance.setFlexoActor(name, (PrimitiveRole) flexoConceptInstance.getFlexoConcept().getFlexoRole(NAME));
	}

	public FlexoConceptInstance getFlexoConceptInstance() {
		return flexoConceptInstance;
	}

	public List<MaskableElement> getSelection() {
		return selection;
	}

	public List<? extends MaskableElement> getFilteredSelection(Class<? extends MaskableElement> objectKind) {
		List<MaskableElement> filteredSelection = new ArrayList<MaskableElement>();
		for (MaskableElement object : selection) {
			if (TypeUtils.isAssignableTo(object, objectKind)) {
				filteredSelection.add(object);
			}
		}
		return filteredSelection;
	}

	public void updateSelection(MaskableElement selected) {
		if (selection.contains(selected)) {
			selection.remove(selected);
		} else {
			selection.add(selected);
		}
		// selected.mask();
		/*MaskableElement parent = selected.getParent();
		if(parent!=null && parent instanceof MaskableFederatedElement){
			parent.setInMask(true);
		}*/
		computingRequired = true;
		getPropertyChangeSupport().firePropertyChange("selection", null, selected);
	}

	public List<OBPTraceObject> getVisibleOBPTraceObjects() {
		List<OBPTraceObject> visibleOBPTraceObjects = new ArrayList<OBPTraceObject>();
		for (MaskableElement maskableElement : getSelection()) {
			visibleOBPTraceObjects.add((OBPTraceObject) maskableElement.getValue());
		}
		return visibleOBPTraceObjects;
	}

	public boolean isContainedInSelection(Object object) {
		for (FlexoObject selectedObject : getSelection()) {
			if (selectedObject.equals(object)) {
				return true;
			}
		}
		return false;
	}

	private void computeRelevantConfiguration() {
		relevantConfigs.clear();
		for (MaskableConfiguration conf : getTrace().getMaskedConfigurations()) {
			if (conf.differsFromPreviousConfiguration(conf.getValue())) {
				relevantConfigs.add(conf.getValue());
			}
		}
	}

	// All configurations for which a changes happenned
	public List<OBPTraceConfiguration> getRelevantConfigurations() {
		if (relevantConfigs == null) {
			relevantConfigs = new ArrayList<OBPTraceConfiguration>();
		}
		/*for(MaskableFederatedElement maskableElement : (List<MaskableFederatedElement>)getFilteredSelection(MaskableFederatedElement.class)){
			for(OBPTraceConfiguration config : getTrace().getConfigurations()){
				if(maskableElement.isVisible(config)){
					relevantConfigs.add(config);
				}
			}
		}*/
		if (computingRequired) {
			computeRelevantConfiguration();
			computingRequired = false;
		}
		return relevantConfigs;
	}

	// All configurations for which a changes happenned
	public List<OBPTraceConfiguration> getRelevantConfigurationsForObject(OBPTraceObject object) {
		List<OBPTraceConfiguration> configs = new ArrayList<OBPTraceConfiguration>();
		for (OBPTraceConfiguration config : getRelevantConfigurations()) {
			if (isRelevant(config, object)) {
				configs.add(config);
			}
		}
		return configs;
	}

	public boolean isRelevant(OBPTraceConfiguration configuration, OBPTraceObject object) {
		MaskableFederatedElement maskableElement = getTrace().getMaskableElement(object);
		// if(maskableElement.getMaskMode().equals(MaskMode.ALWAYS_VISIBLE)){
		// return true;
		// }else {
		// OBPTraceConfiguration previous = getTrace().getTraceOBP().getPreviousConfiguration(configuration);
		// return maskableElement.isRelevant(configuration, previous);
		// }
		return maskableElement.isVisible(configuration);
	}

	public List<OBPTraceBehaviourObjectInstance> getConfigurations() {
		List<MaskableFederatedElement> elements = getTrace().getFilteredFederatedElement(OBPTraceBehaviourObjectInstance.class);
		return (List<OBPTraceBehaviourObjectInstance>) getOBPTraceObjectsFromFederatedElements(elements);
	}

	public List<OBPTraceBehaviourObjectInstance> getBehaviourObjects() {
		List<MaskableFederatedElement> elements = getTrace().getFilteredFederatedElement(OBPTraceBehaviourObjectInstance.class);
		return (List<OBPTraceBehaviourObjectInstance>) getOBPTraceObjectsFromFederatedElements(elements);
	}

	public List<OBPTraceVariable> getVariables() {
		List<MaskableFederatedElement> elements = getTrace().getFilteredFederatedElement(OBPTraceVariable.class);
		return (List<OBPTraceVariable>) getOBPTraceObjectsFromFederatedElements(elements);
	}

	public List<? extends OBPTraceObject> getOBPTraceObjectsFromFederatedElements(List<MaskableFederatedElement> elements) {
		List<OBPTraceObject> returned = new ArrayList<OBPTraceObject>();
		for (MaskableFederatedElement federatedElement : elements) {
			if (isContainedInSelection(federatedElement)) {
				returned.add(federatedElement.getValue());
			}
		}
		return returned;
	}

	// Get the set of maskable elements
	public List<MaskableElement> getMaskableElements() {
		return getTrace().getMaskableElements();
	}

	// Get the set of parent maskable elements
	public List<MaskableFederatedElement> getParentMaskableElements() {
		return getTrace().getFilteredFederatedElement(OBPTraceBehaviourObjectInstance.class);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("maskMode")) {
			getPropertyChangeSupport().firePropertyChange("selection", null, selection);
		}
		computingRequired = true;
	}

}
