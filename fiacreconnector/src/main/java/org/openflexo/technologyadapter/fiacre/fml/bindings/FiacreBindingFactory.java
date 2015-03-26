package org.openflexo.technologyadapter.fiacre.fml.bindings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.openflexo.connie.BindingFactory;
import org.openflexo.connie.binding.BindingPathElement;
import org.openflexo.connie.binding.FunctionPathElement;
import org.openflexo.connie.binding.SimplePathElement;
import org.openflexo.foundation.fml.TechnologySpecificType;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterBindingFactory;
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
	public boolean handleType(TechnologySpecificType<?> technologySpecificType) {
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