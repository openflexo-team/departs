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

import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.InvalidArgumentException;
import org.openflexo.foundation.view.FreeModelSlotInstance;
import org.openflexo.foundation.view.ModelSlotInstance;
import org.openflexo.foundation.view.VirtualModelInstance;
import org.openflexo.technologyadapter.fiacre.model.FiacreProcess;
import org.openflexo.technologyadapter.fiacre.model.FiacreState;
import org.openflexo.technologyadapter.trace.model.FlexoConfigData;
import org.openflexo.technologyadapter.trace.model.FlexoProcess;
import org.openflexo.technologyadapter.trace.model.FlexoTraceOBP;

import Parser.Donnee;
import Parser.TraceOBP;

public class TraceVirtualModelInstance extends TraceAnalysisVirtualModelInstance {

	public enum ParamKind {
		STATE
	}
	
	public TraceVirtualModelInstance(VirtualModelInstance virtualModelInstance)
			throws InvalidArgumentException {
		super(virtualModelInstance);
		// TODO Auto-generated constructor stub
	}

	
	public String getValue(FlexoObject object, int config){
		FreeModelSlotInstance msi = (FreeModelSlotInstance) getVirtualModelInstance().getModelSlotInstance("traceModelSlot");
		FlexoTraceOBP traceOBP = (FlexoTraceOBP) msi.getAccessedResourceData();
		FlexoConfigData configData = traceOBP.getFlexoConfigData().get(config);
		
		if(object instanceof FiacreProcess){
			for(FlexoProcess process : configData.getFlexoProcess()){
				if(process.getProcessType().equals(((FiacreProcess)object).getName())){
					return process.getState();
				}
			}
		}
		
		return null;
	}

}
