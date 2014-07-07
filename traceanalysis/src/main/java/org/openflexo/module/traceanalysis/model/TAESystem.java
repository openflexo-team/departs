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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.openflexo.foundation.DefaultFlexoObject;
import org.openflexo.foundation.InvalidArgumentException;
import org.openflexo.foundation.view.FlexoConceptInstance;
import org.openflexo.foundation.view.VirtualModelInstance;
import org.openflexo.foundation.viewpoint.FlexoConcept;

public class TAESystem extends DefaultFlexoObject implements PropertyChangeListener {

	private final VirtualModelInstance virtualModelInstance;

	public TAESystem(VirtualModelInstance virtualModelInstance) throws InvalidArgumentException {
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
