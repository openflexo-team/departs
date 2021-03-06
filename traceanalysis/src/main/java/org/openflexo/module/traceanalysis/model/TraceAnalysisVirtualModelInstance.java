package org.openflexo.module.traceanalysis.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.openflexo.foundation.DefaultFlexoObject;
import org.openflexo.foundation.InvalidArgumentException;
import org.openflexo.foundation.fml.FlexoConcept;
import org.openflexo.foundation.fml.rt.FlexoConceptInstance;
import org.openflexo.foundation.fml.rt.VirtualModelInstance;

public abstract class TraceAnalysisVirtualModelInstance extends DefaultFlexoObject implements PropertyChangeListener {

	private final VirtualModelInstance virtualModelInstance;

	private final TraceAnalysisProject traceAnalysisProject;

	public TraceAnalysisVirtualModelInstance(VirtualModelInstance virtualModelInstance, TraceAnalysisProject traceAnalysisProject)
			throws InvalidArgumentException {
		super();
		this.virtualModelInstance = virtualModelInstance;
		this.traceAnalysisProject = traceAnalysisProject;
		virtualModelInstance.getPropertyChangeSupport().addPropertyChangeListener(this);
	}

	public VirtualModelInstance getVirtualModelInstance() {
		return virtualModelInstance;
	}

	public String getName() {
		return virtualModelInstance.getName();
	}

	public List<FlexoConcept> getFlexoConcepts() {
		return virtualModelInstance.getVirtualModel().getFlexoConcepts();
	}

	public List<FlexoConceptInstance> getFlexoConceptInstances(FlexoConcept flexoConcept) {
		return virtualModelInstance.getFlexoConceptInstances(flexoConcept);
	}

	public List<FlexoConceptInstance> getFlexoConceptInstances(String name) {
		return virtualModelInstance.getFlexoConceptInstances(name);
	}

	public TraceAnalysisProject getTraceAnalysisProject() {
		return traceAnalysisProject;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}

}
