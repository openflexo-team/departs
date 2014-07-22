package org.openflexo.module.traceanalysis.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.openflexo.foundation.DefaultFlexoObject;
import org.openflexo.foundation.view.FlexoConceptInstance;

public class ConfigurationMask extends DefaultFlexoObject implements PropertyChangeListener {
	
	private final TraceAnalysis traceAnalysis;
	
	private final FlexoConceptInstance flexoConceptInstance;
	
	private String name;

	private List<Object> selection;

	public ConfigurationMask(TraceAnalysis traceAnalysis,FlexoConceptInstance flexoConceptInstance) {
		super();
		this.traceAnalysis = traceAnalysis;
		this.flexoConceptInstance = flexoConceptInstance;
		selection = new ArrayList<Object>();
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public TraceAnalysis getTraceAnalysis() {
		return traceAnalysis;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public FlexoConceptInstance getFlexoConceptInstance(){
		return flexoConceptInstance;
	}
	
	public List<Object> getSelection() {
		return selection;
	}

	public void setSelection(List<Object> selection) {
		this.selection = selection;
	}
	
	public void updateSelection(Object selected){
		if(selection.contains(selected)){
			selection.remove(selected);
		} else{
			selection.add(selected);
		}
		getPropertyChangeSupport().firePropertyChange("selection", null, selection);
	}
	
}


