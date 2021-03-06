package org.openflexo.technologyadapter.cdl.fml.bindings;

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
import org.openflexo.technologyadapter.cdl.model.CDLActivity;
import org.openflexo.technologyadapter.cdl.model.CDLEvent;
import org.openflexo.technologyadapter.cdl.model.CDLProperty;
import org.openflexo.technologyadapter.cdl.model.CDLUnit;

/**
 * This class represent the {@link BindingFactory} dedicated to handle CDL technology-specific binding elements
 * 
 * @author sylvain, vincent
 * 
 */
public final class CDLBindingFactory extends TechnologyAdapterBindingFactory {
	static final Logger logger = Logger.getLogger(CDLBindingFactory.class.getPackage().getName());

	public CDLBindingFactory() {
		super();
	}

	@Override
	protected SimplePathElement makeSimplePathElement(Object object, BindingPathElement parent) {
		logger.warning("Unexpected " + object);
		return null;
	}

	@Override
	public boolean handleType(TechnologySpecificType<?> technologySpecificType) {
		if (technologySpecificType instanceof CDLUnit) {
			return true;
		}
		if (technologySpecificType instanceof CDLActivity) {
			return true;
		}
		if (technologySpecificType instanceof CDLProperty) {
			return true;
		}
		if (technologySpecificType instanceof CDLEvent) {
			return true;
		}
		return true;
	}

	@Override
	public List<? extends SimplePathElement> getAccessibleSimplePathElements(BindingPathElement parent) {
		List<SimplePathElement> returned = new ArrayList<SimplePathElement>();
		if (parent instanceof CDLUnit) {
			for (CDLActivity activity : ((CDLUnit) parent).getCDLActivities()) {
				returned.add(getSimplePathElement(activity, parent));
			}
		}
		return returned;
	}

	@Override
	public List<? extends FunctionPathElement> getAccessibleFunctionPathElements(BindingPathElement parent) {
		// TODO
		return Collections.emptyList();
	}

}