/*
 * (c) Copyright 2013 Openflexo
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

package org.openflexo.technologyadapter.fiacre.rm;

import java.util.logging.Logger;

import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.FlexoResourceFactory;
import org.openflexo.foundation.technologyadapter.TechnologyContextManager;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.technologyadapter.fiacre.FiacreTechnologyAdapter;
import org.openflexo.technologyadapter.fiacre.model.FiacreProgram;

/**
 * Implementation of ResourceFactory for {@link FiacreProgramResource}
 * 
 * @author sylvain
 *
 */
public class FiacreProgramResourceFactory extends FlexoResourceFactory<FiacreProgramResource, FiacreProgram, FiacreTechnologyAdapter> {

	private static final Logger logger = Logger.getLogger(FiacreProgramResourceFactory.class.getPackage().getName());

	private static String FIACRE_FILE_EXTENSION = ".fcr";

	public FiacreProgramResourceFactory() throws ModelDefinitionException {
		super(FiacreProgramResource.class);
	}

	@Override
	public FiacreProgram makeEmptyResourceData(FiacreProgramResource resource) {
		// return new FiacreProgram(resource.getTechnologyAdapter());
		// TODO
		return null;
	}

	@Override
	public <I> boolean isValidArtefact(I serializationArtefact, FlexoResourceCenter<I> resourceCenter) {
		return resourceCenter.retrieveName(serializationArtefact).endsWith(FIACRE_FILE_EXTENSION);
	}

	@Override
	protected <I> FiacreProgramResource registerResource(FiacreProgramResource resource, FlexoResourceCenter<I> resourceCenter,
			TechnologyContextManager<FiacreTechnologyAdapter> technologyContextManager) {
		super.registerResource(resource, resourceCenter, technologyContextManager);

		// Register the resource in the FiacreProgramRepository of supplied resource center
		registerResourceInResourceRepository(resource,
				technologyContextManager.getTechnologyAdapter().getFiacreProgramRepository(resourceCenter));

		return resource;
	}

	/*public static FiacreProgramResource makeFiacreProgramResource(String modelURI, File modelFile,
			FiacreTechnologyContextManager technologyContextManager) {
		try {
			ModelFactory factory = new ModelFactory(
					ModelContextLibrary.getCompoundModelContext(FileFlexoIODelegate.class, FiacreProgramResource.class));
			FiacreProgramResourceImpl returned = (FiacreProgramResourceImpl) factory.newInstance(FiacreProgramResource.class);
			returned.initName(modelFile.getName());
			FileFlexoIODelegate fileIODelegate = factory.newInstance(FileFlexoIODelegate.class);
			returned.setFlexoIODelegate(fileIODelegate);
			fileIODelegate.setFile(modelFile);
			returned.setURI(modelURI);
			returned.setServiceManager(technologyContextManager.getTechnologyAdapter().getTechnologyAdapterService().getServiceManager());
			returned.setTechnologyAdapter(technologyContextManager.getTechnologyAdapter());
			returned.setTechnologyContextManager(technologyContextManager);
			technologyContextManager.registerResource(returned);
	
			return returned;
		} catch (ModelDefinitionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static FiacreProgramResource retrieveFiacreProgramResource(File FiacreFile,
			FiacreTechnologyContextManager technologyContextManager) {
		try {
			ModelFactory factory = new ModelFactory(
					ModelContextLibrary.getCompoundModelContext(FileFlexoIODelegate.class, FiacreProgramResource.class));
			FiacreProgramResourceImpl returned = (FiacreProgramResourceImpl) factory.newInstance(FiacreProgramResource.class);
			returned.initName(FiacreFile.getName());
			FileFlexoIODelegate fileIODelegate = factory.newInstance(FileFlexoIODelegate.class);
			returned.setFlexoIODelegate(fileIODelegate);
			fileIODelegate.setFile(FiacreFile);
			returned.setURI(FiacreFile.toURI().toString());
			returned.setServiceManager(technologyContextManager.getTechnologyAdapter().getTechnologyAdapterService().getServiceManager());
			returned.setTechnologyAdapter(technologyContextManager.getTechnologyAdapter());
			returned.setTechnologyContextManager(technologyContextManager);
			technologyContextManager.registerResource(returned);
			return returned;
		} catch (ModelDefinitionException e) {
			e.printStackTrace();
		}
		return null;
	}*/

}
