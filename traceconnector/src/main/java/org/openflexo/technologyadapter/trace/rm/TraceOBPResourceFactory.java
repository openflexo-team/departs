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

package org.openflexo.technologyadapter.trace.rm;

import java.util.logging.Logger;

import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.FlexoResourceFactory;
import org.openflexo.foundation.technologyadapter.TechnologyContextManager;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.technologyadapter.trace.TraceTechnologyAdapter;
import org.openflexo.technologyadapter.trace.model.OBPTrace;

/**
 * Implementation of ResourceFactory for {@link TraceOBPResource}
 * 
 * @author sylvain
 *
 */
public class TraceOBPResourceFactory extends FlexoResourceFactory<TraceOBPResource, OBPTrace, TraceTechnologyAdapter> {

	private static final Logger logger = Logger.getLogger(TraceOBPResourceFactory.class.getPackage().getName());

	private static String TRACE_FILE_EXTENSION = ".trace";

	public TraceOBPResourceFactory() throws ModelDefinitionException {
		super(TraceOBPResource.class);
	}

	@Override
	public OBPTrace makeEmptyResourceData(TraceOBPResource resource) {
		// return new TraceOBP(resource.getTechnologyAdapter());
		// TODO
		return null;
	}

	@Override
	public <I> boolean isValidArtefact(I serializationArtefact, FlexoResourceCenter<I> resourceCenter) {
		return resourceCenter.retrieveName(serializationArtefact).endsWith(TRACE_FILE_EXTENSION);
	}

	@Override
	protected <I> TraceOBPResource registerResource(TraceOBPResource resource, FlexoResourceCenter<I> resourceCenter,
			TechnologyContextManager<TraceTechnologyAdapter> technologyContextManager) {
		super.registerResource(resource, resourceCenter, technologyContextManager);

		// Register the resource in the TraceOBPRepository of supplied resource center
		registerResourceInResourceRepository(resource,
				technologyContextManager.getTechnologyAdapter().getTraceOBPRepository(resourceCenter));

		return resource;
	}

	/*
	public static TraceOBPResource makeTraceOBPResource(String modelURI, File modelFile,
			TraceTechnologyContextManager technologyContextManager) {
		try {
			ModelFactory factory = new ModelFactory(
					ModelContextLibrary.getCompoundModelContext(FileFlexoIODelegate.class, TraceOBPResource.class));
			TraceOBPResourceImpl returned = (TraceOBPResourceImpl) factory.newInstance(TraceOBPResource.class);
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
	
	public static TraceOBPResource retrieveTraceOBPResource(File traceFile, TraceTechnologyContextManager technologyContextManager) {
		try {
			ModelFactory factory = new ModelFactory(
					ModelContextLibrary.getCompoundModelContext(FileFlexoIODelegate.class, TraceOBPResource.class));
			TraceOBPResourceImpl returned = (TraceOBPResourceImpl) factory.newInstance(TraceOBPResource.class);
			returned.initName(traceFile.getName());
			FileFlexoIODelegate fileIODelegate = factory.newInstance(FileFlexoIODelegate.class);
			returned.setFlexoIODelegate(fileIODelegate);
			fileIODelegate.setFile(traceFile);
			returned.setURI(traceFile.toURI().toString());
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
