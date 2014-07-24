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
import org.openflexo.technologyadapter.cdl.model.CDLUnit;

public class ContextVirtualModelInstance extends TraceAnalysisVirtualModelInstance {

	public ContextVirtualModelInstance(VirtualModelInstance virtualModelInstance, TraceAnalysis traceAnalysis)
			throws InvalidArgumentException {
		super(virtualModelInstance, traceAnalysis);
		// TODO Auto-generated constructor stub
	}
	
	public List<CDLUnit> getCDLUnits(){
		List<CDLUnit> cdlUnits = new ArrayList<CDLUnit>();
		for(FlexoConceptInstance fciCDLUnit : getVirtualModelInstance().getFlexoConceptInstances("CDLUnit")){
			cdlUnits.add((CDLUnit) fciCDLUnit.getFlexoActor("cdlUnit"));
		}
		return cdlUnits;
	}
	
}
