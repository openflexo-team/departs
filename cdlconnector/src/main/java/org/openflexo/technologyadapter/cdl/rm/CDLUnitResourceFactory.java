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

package org.openflexo.technologyadapter.cdl.rm;

import java.util.logging.Logger;

import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.FlexoResourceFactory;
import org.openflexo.foundation.technologyadapter.TechnologyContextManager;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.technologyadapter.cdl.CDLTechnologyAdapter;
import org.openflexo.technologyadapter.cdl.model.CDLUnit;

/**
 * Implementation of ResourceFactory for {@link CDLUnitResource}
 * 
 * @author sylvain
 *
 */
public class CDLUnitResourceFactory extends FlexoResourceFactory<CDLUnitResource, CDLUnit, CDLTechnologyAdapter> {

	private static final Logger logger = Logger.getLogger(CDLUnitResourceFactory.class.getPackage().getName());

	private static String CDL_FILE_EXTENSION = ".cdl";

	public CDLUnitResourceFactory() throws ModelDefinitionException {
		super(CDLUnitResource.class);
	}

	@Override
	public CDLUnit makeEmptyResourceData(CDLUnitResource resource) {
		// return new CDLUnit(resource.getTechnologyAdapter());
		// TODO
		return null;
	}

	@Override
	public <I> boolean isValidArtefact(I serializationArtefact, FlexoResourceCenter<I> resourceCenter) {
		return resourceCenter.retrieveName(serializationArtefact).endsWith(CDL_FILE_EXTENSION);
	}

	@Override
	protected <I> CDLUnitResource registerResource(CDLUnitResource resource, FlexoResourceCenter<I> resourceCenter,
			TechnologyContextManager<CDLTechnologyAdapter> technologyContextManager) {
		super.registerResource(resource, resourceCenter, technologyContextManager);

		// Register the resource in the CDLUnitRepository of supplied resource center
		registerResourceInResourceRepository(resource,
				technologyContextManager.getTechnologyAdapter().getCDLUnitRepository(resourceCenter));

		return resource;
	}

	/*
	public static CDLUnitResource makeCDLUnitResource(String modelURI, File modelFile,
			CDLTechnologyContextManager technologyContextManager) {
		try {
			ModelFactory factory = new ModelFactory(
					ModelContextLibrary.getCompoundModelContext(FileFlexoIODelegate.class, CDLUnitResource.class));
			CDLUnitResourceImpl returned = (CDLUnitResourceImpl) factory.newInstance(CDLUnitResource.class);
			FileFlexoIODelegate fileIODelegate = factory.newInstance(FileFlexoIODelegate.class);
			returned.setFlexoIODelegate(fileIODelegate);
			fileIODelegate.setFile(modelFile);
			returned.initName(modelFile.getName());
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
	
	public static CDLUnitResource retrieveCDLUnitResource(File cdlFile, CDLTechnologyContextManager technologyContextManager) {
		try {
			ModelFactory factory = new ModelFactory(
					ModelContextLibrary.getCompoundModelContext(FileFlexoIODelegate.class, CDLUnitResource.class));
			CDLUnitResourceImpl returned = (CDLUnitResourceImpl) factory.newInstance(CDLUnitResource.class);
			FileFlexoIODelegate fileIODelegate = factory.newInstance(FileFlexoIODelegate.class);
			returned.setFlexoIODelegate(fileIODelegate);
			fileIODelegate.setFile(cdlFile);
			returned.initName(cdlFile.getName());
			returned.setURI(cdlFile.toURI().toString());
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
	*/

}
