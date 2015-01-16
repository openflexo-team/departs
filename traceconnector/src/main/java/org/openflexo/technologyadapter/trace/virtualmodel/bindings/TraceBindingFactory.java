package org.openflexo.technologyadapter.trace.virtualmodel.bindings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.openflexo.antar.binding.BindingFactory;
import org.openflexo.antar.binding.BindingPathElement;
import org.openflexo.antar.binding.FunctionPathElement;
import org.openflexo.antar.binding.SimplePathElement;
import org.openflexo.foundation.fml.TechnologySpecificCustomType;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterBindingFactory;
import org.openflexo.technologyadapter.trace.model.OBPTrace;

/**
 * This class represent the {@link BindingFactory} dedicated to handle trace technology-specific binding elements
 * 
 * @author vincent
 * 
 */
public final class TraceBindingFactory extends TechnologyAdapterBindingFactory {
	static final Logger logger = Logger.getLogger(TraceBindingFactory.class.getPackage().getName());

	public TraceBindingFactory() {
		super();
	}

	@Override
	protected SimplePathElement makeSimplePathElement(Object object, BindingPathElement parent) {
		logger.warning("Unexpected " + object);
		return null;
	}

	@Override
	public boolean handleType(TechnologySpecificCustomType technologySpecificType) {
		if (technologySpecificType instanceof OBPTrace) {
			return true;
		}
		return true;
	}

	@Override
	public List<? extends SimplePathElement> getAccessibleSimplePathElements(BindingPathElement parent) {
		List<SimplePathElement> returned = new ArrayList<SimplePathElement>();
		return returned;
	}

	@Override
	public List<? extends FunctionPathElement> getAccessibleFunctionPathElements(BindingPathElement parent) {
		// TODO
		return Collections.emptyList();
	}

}