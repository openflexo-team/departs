package org.openflexo.module.traceanalysis.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.openflexo.foundation.DefaultFlexoObject;
import org.openflexo.foundation.InvalidArgumentException;
import org.openflexo.foundation.view.FlexoConceptInstance;
import org.openflexo.foundation.view.VirtualModelInstance;
import org.openflexo.foundation.viewpoint.FlexoConcept;

public class TAEObject extends DefaultFlexoObject implements PropertyChangeListener {
	
	private final VirtualModelInstance virtualModelInstance;
	
	public TAEObject(VirtualModelInstance virtualModelInstance) throws InvalidArgumentException {
		super();
		this.virtualModelInstance = virtualModelInstance;
		virtualModelInstance.getPropertyChangeSupport().addPropertyChangeListener(this);
	}
	
	public VirtualModelInstance getVirtualModelInstance() {
		return virtualModelInstance;
	}

	public String getName() {
		return virtualModelInstance.getName();
	}
	
	public List<FlexoConcept> getFlexoConcepts(){
		return virtualModelInstance.getVirtualModel().getFlexoConcepts();
	}
	
	public List<FlexoConceptInstance> getFlexoConceptInstances(FlexoConcept flexoConcept){
		return virtualModelInstance.getFlexoConceptInstances(flexoConcept);
	}
	
	@Override
	public boolean delete() {
		super.delete();
		return true;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

}
