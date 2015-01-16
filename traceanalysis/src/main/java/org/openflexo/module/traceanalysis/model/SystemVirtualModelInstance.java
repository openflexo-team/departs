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

import java.util.ArrayList;
import java.util.List;
import org.openflexo.foundation.InvalidArgumentException;
import org.openflexo.foundation.fml.rt.FlexoConceptInstance;
import org.openflexo.foundation.fml.rt.VirtualModelInstance;
import org.openflexo.technologyadapter.fiacre.model.FiacreComponent;
import org.openflexo.technologyadapter.fiacre.model.FiacreProcess;
import org.openflexo.technologyadapter.fiacre.model.FiacreVariable;

/**
 * A virtual model instance conform to the system virtual model.
 * @author Vincent
 *
 */
public class SystemVirtualModelInstance extends TraceAnalysisVirtualModelInstance {

	public SystemVirtualModelInstance(VirtualModelInstance virtualModelInstance, TraceAnalysisProject traceAnalysisProject)
			throws InvalidArgumentException {
		super(virtualModelInstance,traceAnalysisProject);
	}
	
	/**
	 * Return fiacre processes from their respective flexo concept instance
	 * @return
	 */
	public List<FiacreProcess> getFiacreProcesses(){
		List<FiacreProcess> fiacreProcesses = new ArrayList<FiacreProcess>();
		for(FlexoConceptInstance fciProcess : getVirtualModelInstance().getFlexoConceptInstances("Process")){
			fiacreProcesses.add((FiacreProcess) fciProcess.getFlexoActor("process"));
		}
		return fiacreProcesses;
	}
	
	/**
	 * Return fiacre components from their respective flexo concept instance
	 * @return
	 */
	public List<FiacreComponent> getFiacreComponents(){
		List<FiacreComponent> fiacreComponents = new ArrayList<FiacreComponent>();
		for(FlexoConceptInstance fciComponent : getVirtualModelInstance().getFlexoConceptInstances("Component")){
			fiacreComponents.add((FiacreComponent) fciComponent.getFlexoActor("component"));
		}
		return fiacreComponents;
	}
	
	/**
	 * Return fiacre variables for a fiacre process
	 * @return
	 */
	public List<FiacreVariable> getFiacreVariable(FiacreProcess fiacreProcess){
		return fiacreProcess.getFiacreVariables();
	}
	
}
