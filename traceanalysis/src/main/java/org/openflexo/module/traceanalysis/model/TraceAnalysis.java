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

import org.openflexo.foundation.DefaultFlexoObject;
import org.openflexo.foundation.InvalidArgumentException;
import org.openflexo.foundation.view.FlexoConceptInstance;
import org.openflexo.foundation.view.VirtualModelInstance;

public class TraceAnalysis extends DefaultFlexoObject implements PropertyChangeListener {

	private final VirtualModelInstance virtualModelInstance;
	private final TAEProject taeProject;


	public TraceAnalysis(VirtualModelInstance virtualModelInstance, TAEProject taeProject) throws InvalidArgumentException {
		super();
		this.taeProject = taeProject;
		this.virtualModelInstance = virtualModelInstance;
		virtualModelInstance.getPropertyChangeSupport().addPropertyChangeListener(this);
	}

	public TAEProject getTAEProject() {
		return taeProject;
	}

	public VirtualModelInstance getVirtualModelInstance() {
		return virtualModelInstance;
	}

	public TAEProjectNature getProjectNature() {
		return getTAEProject().getProjectNature();
	}

	public String getName() {
		return virtualModelInstance.getName();
	}

	public String getURI() {
		return taeProject.getProject().getURI() + "/" + getName();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource() == virtualModelInstance) {
		
		}
	}
	
	@Override
	public boolean delete() {
		super.delete();
		return true;
	}
}