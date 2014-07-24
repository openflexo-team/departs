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
import org.openflexo.foundation.view.FlexoConceptInstance;
import org.openflexo.foundation.view.VirtualModelInstance;
import org.openflexo.technologyadapter.fiacre.model.FiacreComponent;
import org.openflexo.technologyadapter.fiacre.model.FiacreProcess;
import org.openflexo.technologyadapter.fiacre.model.FiacreVariable;

public class SystemVirtualModelInstance extends TraceAnalysisVirtualModelInstance {

	public SystemVirtualModelInstance(VirtualModelInstance virtualModelInstance, TraceAnalysis traceAnalysis)
			throws InvalidArgumentException {
		super(virtualModelInstance,traceAnalysis);
		// TODO Auto-generated constructor stub
	}
	
	public List<FiacreProcess> getFiacreProcesses(){
		List<FiacreProcess> fiacreProcesses = new ArrayList<FiacreProcess>();
		for(FlexoConceptInstance fciProcess : getVirtualModelInstance().getFlexoConceptInstances("Process")){
			fiacreProcesses.add((FiacreProcess) fciProcess.getFlexoActor("process"));
		}
		return fiacreProcesses;
	}
	
	public List<FiacreComponent> getFiacreComponents(){
		List<FiacreComponent> fiacreComponents = new ArrayList<FiacreComponent>();
		for(FlexoConceptInstance fciComponent : getVirtualModelInstance().getFlexoConceptInstances("Component")){
			fiacreComponents.add((FiacreComponent) fciComponent.getFlexoActor("component"));
		}
		return fiacreComponents;
	}
	
	public List<FiacreVariable> getFiacreVariable(FiacreProcess fiacreProcess){
		return fiacreProcess.getFiacreVariables();
	}
	
}
