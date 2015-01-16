package org.openflexo.technologyadapter.fiacre.virtualmodel.bindings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.openflexo.antar.binding.BindingFactory;
import org.openflexo.antar.binding.BindingPathElement;
import org.openflexo.antar.binding.FunctionPathElement;
import org.openflexo.antar.binding.SimplePathElement;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterBindingFactory;
import org.openflexo.foundation.fml.TechnologySpecificCustomType;
import org.openflexo.technologyadapter.fiacre.model.FiacreProgram;

/**
 * This class represent the {@link BindingFactory} dedicated to handle Fiacre technology-specific binding elements
 * 
 * @author vincent
 * 
 */
public final class FiacreBindingFactory extends TechnologyAdapterBindingFactory {
	static final Logger logger = Logger.getLogger(FiacreBindingFactory.class.getPackage().getName());

	public FiacreBindingFactory() {
		super();
	}

	@Override
	protected SimplePathElement makeSimplePathElement(Object object, BindingPathElement parent) {
		logger.warning("Unexpected " + object);
		return null;
	}

	@Override
	public boolean handleType(TechnologySpecificCustomType technologySpecificType) {
		if (technologySpecificType instanceof FiacreProgram) {
			return true;
		}
		return true;
	}

	@Override
	public List<? extends SimplePathElement> getAccessibleSimplePathElements(BindingPathElement parent) {
		List<SimplePathElement> returned = new ArrayList<SimplePathElement>();
		if (parent instanceof FiacreProgram) {

		}
		return returned;
	}

	@Override
	public List<? extends FunctionPathElement> getAccessibleFunctionPathElements(BindingPathElement parent) {
		// TODO
		return Collections.emptyList();
	}

}