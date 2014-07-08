package org.openflexo.module.traceanalysis.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.openflexo.foundation.DefaultFlexoObject;

public class TraceExplorationMask extends DefaultFlexoObject implements PropertyChangeListener {
	
	private final TraceAnalysis traceAnalysis;
	
	private String name;
	
	public TraceExplorationMask(TraceAnalysis traceAnalysis) {
		super();
		this.traceAnalysis = traceAnalysis;
		this.traceAnalysis.getTraceExplorationMasks().add(this);
		traceAnalysis.getPropertyChangeSupport().firePropertyChange("traceExplorationMasks", null, this);
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
}


