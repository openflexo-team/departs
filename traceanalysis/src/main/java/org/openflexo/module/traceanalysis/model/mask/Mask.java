package org.openflexo.module.traceanalysis.model.mask;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.openflexo.foundation.DefaultFlexoObject;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.view.FlexoConceptInstance;
import org.openflexo.foundation.viewpoint.PrimitiveRole;
import org.openflexo.module.traceanalysis.model.TraceAnalysisProject;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.module.traceanalysis.model.mask.MaskableElement.MaskMode;
import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObjectInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceConfiguration;
import org.openflexo.technologyadapter.trace.model.OBPTraceObject;
import org.openflexo.technologyadapter.trace.model.OBPTraceVariable;


/**
 * A Mask reference a selection of masked elements that should be filterer.
 * @author Vincent
 *
 */
public class Mask extends DefaultFlexoObject implements PropertyChangeListener {
	
	private final FlexoConceptInstance flexoConceptInstance;
	private String name;
	private static String NAME = "name";
	private List<MaskableElement> selection;
	private final TraceAnalysisProject project;
	private final TraceVirtualModelInstance trace;

	public Mask(FlexoConceptInstance flexoConceptInstance,TraceAnalysisProject project, TraceVirtualModelInstance trace) {
		super();
		this.flexoConceptInstance = flexoConceptInstance;
		name = flexoConceptInstance.getFlexoActor(NAME);
		selection = new ArrayList<MaskableElement>();
		this.project = project;
		this.trace = trace;
	}
	
	public TraceAnalysisProject getTraceAnalysisProject(){
		return project;
	}
	
	public TraceVirtualModelInstance getTrace() {
		return trace;
	}

	public String getName() {
		return flexoConceptInstance.getFlexoActor(NAME);
	}

	public void setName(String name) {
		flexoConceptInstance.setFlexoActor(name, (PrimitiveRole)flexoConceptInstance.getFlexoConcept().getFlexoRole(NAME));
	}
	
	public FlexoConceptInstance getFlexoConceptInstance(){
		return flexoConceptInstance;
	}
	
	public List<MaskableElement> getSelection() {
		return selection;
	}
	
	public void updateSelection(MaskableElement selected){
		if(selection.contains(selected)){
			selection.remove(selected);
		}else{
			selection.add(selected);
		}
		//selected.mask();
		/*MaskableElement parent = selected.getParent();
		if(parent!=null && parent instanceof MaskableFederatedElement){
			parent.setInMask(true);
		}*/
		getPropertyChangeSupport().firePropertyChange("selection", null, selection);
	}
	
	public List<OBPTraceObject> getVisibleOBPTraceObjects(){
		List<OBPTraceObject> visibleOBPTraceObjects = new ArrayList<OBPTraceObject>();
		for(MaskableElement maskableElement : getSelection()){
			visibleOBPTraceObjects.add((OBPTraceObject) maskableElement.getValue());
		}
		return visibleOBPTraceObjects;
	}
	
	public boolean isContainedInSelection(Object object){
		for(FlexoObject selectedObject : getSelection()){
			if(selectedObject.equals(object)){
				return true;
			}
		}
		return false;
	}
	
	// All configurations for which a changes happenned
	public List<OBPTraceConfiguration> getRelevantConfigurations(){
		List<OBPTraceConfiguration> relevantConfigs = new ArrayList<OBPTraceConfiguration>();
		for(MaskableElement maskableElement : getMaskableElements()){
			if(maskableElement instanceof MaskableFederatedElement){
				MaskableFederatedElement element = (MaskableFederatedElement)maskableElement;
				if(isContainedInSelection(element)){
					for(OBPTraceConfiguration config : getTrace().getConfigurations()){
						if(element.isRelevant(config, getTrace().getTraceOBP().getPreviousConfiguration(config))){
							relevantConfigs.add(config);
						}
					}
				}
				
			}
		}
		return relevantConfigs;
	}
	
	public boolean isRelevant(OBPTraceConfiguration configuration, OBPTraceObject object){
		MaskableFederatedElement maskableElement = getTrace().getMaskableElement(object);
		if(maskableElement.getMaskMode().equals(MaskMode.ALWAYS_VISIBLE)){
			return true;
		}else {
			OBPTraceConfiguration previous = getTrace().getTraceOBP().getPreviousConfiguration(configuration);
			return maskableElement.isRelevant(configuration, previous);
		}
	}
	
	public List<OBPTraceBehaviourObjectInstance> getConfigurations(){
		List<MaskableFederatedElement> elements = getTrace().getFilteredFederatedElement(OBPTraceBehaviourObjectInstance.class);
		return (List<OBPTraceBehaviourObjectInstance>)getOBPTraceObjectsFromFederatedElements(elements);
	}
	
	public List<OBPTraceBehaviourObjectInstance> getBehaviourObjects(){
		List<MaskableFederatedElement> elements = getTrace().getFilteredFederatedElement(OBPTraceBehaviourObjectInstance.class);
		return (List<OBPTraceBehaviourObjectInstance>)getOBPTraceObjectsFromFederatedElements(elements);
	}
	
	public List<OBPTraceVariable> getVariables(){
		List<MaskableFederatedElement> elements = getTrace().getFilteredFederatedElement(OBPTraceVariable.class);
		return (List<OBPTraceVariable>)getOBPTraceObjectsFromFederatedElements(elements);
	}

	public List<? extends OBPTraceObject> getOBPTraceObjectsFromFederatedElements(List<MaskableFederatedElement> elements){
		List<OBPTraceObject> returned = new ArrayList<OBPTraceObject>();
		for(MaskableFederatedElement federatedElement : elements){
			if(isContainedInSelection(federatedElement)){
				returned.add((OBPTraceObject) federatedElement.getValue());
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
		// TODO Auto-generated method stub
		
	}
	
}


