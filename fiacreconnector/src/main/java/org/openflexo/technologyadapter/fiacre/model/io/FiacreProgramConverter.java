package org.openflexo.technologyadapter.fiacre.model.io;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.openflexo.model.ModelContext;
import org.openflexo.model.ModelContextLibrary;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.model.factory.ModelFactory;
import org.openflexo.technologyadapter.fiacre.FiacreTechnologyAdapter;
import org.openflexo.technologyadapter.fiacre.model.FiacreObject;
import org.openflexo.technologyadapter.fiacre.model.FiacreProgram;

public class FiacreProgramConverter {

	private static final Logger logger = Logger.getLogger(FiacreProgramConverter.class.getPackage().getName());

	protected final Map<Object, FiacreObject> fiacreObjects = new HashMap<Object, FiacreObject>();

	private ModelFactory factory;
	private ModelContext modelContext;

	/**
	 * Constructor.
	 */
	public FiacreProgramConverter() {
		try {
			factory = new ModelFactory(ModelContextLibrary.getCompoundModelContext(FiacreObject.class, FiacreProgram.class));
		} catch (ModelDefinitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public FiacreProgram convertFiacreProgram(obp.fiacre.model.Program cdlUnit, FiacreTechnologyAdapter technologyAdapter) {

		FiacreProgram flexoFiacreProgram = factory.newInstance(FiacreProgram.class);
		flexoFiacreProgram.setTechnologyAdapter(technologyAdapter);

		return flexoFiacreProgram;
	}

	public Map<Object, FiacreObject> getFiacreObjects() {
		return fiacreObjects;
	}

}
