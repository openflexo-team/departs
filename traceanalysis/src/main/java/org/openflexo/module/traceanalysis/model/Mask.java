package org.openflexo.module.traceanalysis.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.openflexo.foundation.DefaultFlexoObject;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.view.FlexoConceptInstance;
import org.openflexo.foundation.viewpoint.PrimitiveRole;
import org.openflexo.technologyadapter.trace.model.OBPTraceConfiguration;
import org.openflexo.technologyadapter.trace.model.OBPTraceObject;

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
	
	public boolean isTraceOBPObjectFiltered(OBPTraceObject obpTraceObject){
		for(FlexoObject object : selection){
			List<OBPTraceObject> traceOBPObjects = trace.getOBPTraceObjects(object);
			if(traceOBPObjects!=null && traceOBPObjects.contains(obpTraceObject)){
				return true;
			}
		}
		return false;
	}
}


