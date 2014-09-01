package org.openflexo.module.traceanalysis.model;

import java.util.List;

import org.openflexo.foundation.view.FlexoConceptInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObject;

public class ConfigurationMask extends Mask{

	public ConfigurationMask(FlexoConceptInstance flexoConceptInstance,
			TraceAnalysisProject project, TraceVirtualModelInstance trace) {
		super(flexoConceptInstance, project, trace);
	}
	
	public List<OBPTraceBehaviourObject> getBehaviourObjects(){
		return (List<OBPTraceBehaviourObject>) getFilteredSelection(OBPTraceBehaviourObject.class);
	}
	
}


