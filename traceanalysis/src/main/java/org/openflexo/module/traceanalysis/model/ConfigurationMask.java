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

public class ConfigurationMask extends DefaultFlexoObject implements PropertyChangeListener {
	
	private final FlexoConceptInstance flexoConceptInstance;
	private String name;
	private static String NAME = "name";
	private LinkedHashSet<FlexoObject> selection;
	private final TraceAnalysisProject project;
	
	
	public ConfigurationMask(FlexoConceptInstance flexoConceptInstance,TraceAnalysisProject project) {
		super();
		this.flexoConceptInstance = flexoConceptInstance;
		name = flexoConceptInstance.getFlexoActor(NAME);
		selection = new LinkedHashSet<FlexoObject>();
		this.project = project;
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
	
	public LinkedHashSet<FlexoObject> getSelection() {
		return selection;
	}

	public void setSelection(LinkedHashSet<FlexoObject> selection) {
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
	
	public LinkedHashSet<FlexoObject> getFilteredSelection(Class<?> objectKind){
		LinkedHashSet<FlexoObject> filteredSelection = new LinkedHashSet<FlexoObject>();
		for(FlexoObject object : selection){
			if(object.getClass().isAssignableFrom(objectKind)){
				filteredSelection.add(object);
			}
		}
		return filteredSelection;
	}
	
}


