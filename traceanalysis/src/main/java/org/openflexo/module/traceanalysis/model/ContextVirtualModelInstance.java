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
import org.openflexo.technologyadapter.cdl.model.CDLParActivity;

/**
 * A virtual model instance conform to the context virtual model.
 * @author Vincent
 *
 */
public class ContextVirtualModelInstance extends TraceAnalysisVirtualModelInstance {

	public ContextVirtualModelInstance(VirtualModelInstance virtualModelInstance, TraceAnalysisProject traceAnalysisProject)
			throws InvalidArgumentException {
		super(virtualModelInstance, traceAnalysisProject);
	}
	
	/**
	 * Return a set of parallel activities from this context
	 * @return
	 */
	public List<CDLParActivity> getCDLParActivities(){
		List<CDLParActivity> cdlParActivities = new ArrayList<CDLParActivity>();
		for(FlexoConceptInstance fciCDLParActivity : getVirtualModelInstance().getFlexoConceptInstances("ParActivity")){
			cdlParActivities.add((CDLParActivity) fciCDLParActivity.getFlexoActor("activity"));
		}
		return cdlParActivities;
	}
	
}
