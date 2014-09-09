package org.openflexo.module.traceanalysis.model.mask;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.openflexo.foundation.DefaultFlexoObject;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.view.FlexoConceptInstance;
import org.openflexo.foundation.viewpoint.PrimitiveRole;
import org.openflexo.module.traceanalysis.model.TraceAnalysisProject;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance.FederatedElement;
import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObjectInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceConfiguration;
import org.openflexo.technologyadapter.trace.model.OBPTraceObject;
import org.openflexo.technologyadapter.trace.model.OBPTraceVariable;

public class Mask extends DefaultFlexoObject implements PropertyChangeListener {
	
	private final FlexoConceptInstance flexoConceptInstance;
	private String name;
	private static String NAME = "name";
	private List<FlexoObject> selection;
	private final TraceAnalysisProject project;
	private final TraceVirtualModelInstance trace;
	
	
	public Mask(FlexoConceptInstance flexoConceptInstance,TraceAnalysisProject project, TraceVirtualModelInstance trace) {
		super();
		this.flexoConceptInstance = flexoConceptInstance;
		name = flexoConceptInstance.getFlexoActor(NAME);
		selection = new ArrayList<FlexoObject>();
		this.project = project;
		this.trace = trace;
		for(FederatedElement element : trace.getFederatedElements()){
			selection.add(element.getType());
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public TraceAnalysisProject getTraceAnalysisProject(){
		return project;
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
	
	public List<FlexoObject> getSelection() {
		return selection;
	}

	public void setSelection(List<FlexoObject> selection) {
		this.selection = selection;
	}
	
	public void updateSelection(FlexoObject selected){
		if(selection.contains(selected)){
			selection.remove(selected);
		} else{
			selection.add(selected);
		}
		getPropertyChangeSupport().firePropertyChange("selection", null, selection);
	}
	
	public List<?> getFilteredSelection(Class<?> objectKind){
		List<FlexoObject> filteredSelection = new ArrayList<FlexoObject>();
		for(FlexoObject object : selection){
			if(object.getClass().isAssignableFrom(objectKind)){
				filteredSelection.add(object);
			}
		}
		return filteredSelection;
	}
	
	public List<?> getFilteredSelection(List<?> elements){
		List<Object> filteredSelection = new ArrayList<Object>();
		for(Object object : selection){
			if(elements.contains(object)){
				filteredSelection.add(object);
			}
		}
		return filteredSelection;
	}
	
	
	public boolean isContainedInSelection(Object object){
		for(FlexoObject selectedObject : selection){
			if(selectedObject.equals(object)){
				return true;
			}
		}
		return false;
	}

	public TraceVirtualModelInstance getTrace() {
		return trace;
	}
	
	public List<OBPTraceBehaviourObjectInstance> getBehaviourObjects(){
		List<FederatedElement> elements = getTrace().getFilteredFederatedElement(OBPTraceBehaviourObjectInstance.class);
		return (List<OBPTraceBehaviourObjectInstance>)getOBPTraceObjectsFromFederatedElements(elements);
	}
	
	public List<OBPTraceVariable> getVariables(){
		List<FederatedElement> elements = getTrace().getFilteredFederatedElement(OBPTraceVariable.class);
		return (List<OBPTraceVariable>)getOBPTraceObjectsFromFederatedElements(elements);
	}

	public List<?> getOBPTraceObjectsFromFederatedElements(List<FederatedElement> elements){
		List<OBPTraceObject> returned = new ArrayList<OBPTraceObject>();
		for(FederatedElement federatedElement : elements){
			if(isContainedInSelection(federatedElement.getType())){
				returned.add((OBPTraceObject) federatedElement.getValue());
			}
		}
		return returned;
	}
	
}


