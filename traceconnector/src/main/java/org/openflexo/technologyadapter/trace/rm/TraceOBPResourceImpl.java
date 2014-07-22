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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.resource.FlexoFileResourceImpl;
import org.openflexo.foundation.resource.ResourceLoadingCancelledException;
import org.openflexo.foundation.resource.SaveResourceException;
import org.openflexo.foundation.resource.SaveResourcePermissionDeniedException;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.model.factory.ModelFactory;
import org.openflexo.technologyadapter.trace.TraceTechnologyContextManager;
import org.openflexo.technologyadapter.trace.model.FlexoTraceOBP;
import org.openflexo.technologyadapter.trace.model.io.TraceModelConverter;
import org.openflexo.toolbox.IProgress;

import Parser.TraceExploObpParser;
import Parser.TraceOBP;

public abstract class TraceOBPResourceImpl extends FlexoFileResourceImpl<FlexoTraceOBP> implements TraceOBPResource {
	private static final Logger logger = Logger.getLogger(TraceOBPResourceImpl.class.getPackage().getName());

	private TraceModelConverter converter;

	public TraceModelConverter getConverter() {
		return converter;
	}

	public void setConverter(TraceModelConverter converter) {
		this.converter = converter;
	}

	public static TraceOBPResource makeTraceOBPResource(String modelURI, File modelFile, TraceTechnologyContextManager technologyContextManager) {
		try {
			ModelFactory factory = new ModelFactory(TraceOBPResource.class);
			TraceOBPResourceImpl returned = (TraceOBPResourceImpl) factory.newInstance(TraceOBPResource.class);
			returned.setName(modelFile.getName());
			returned.setFile(modelFile);
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

	public static TraceOBPResource retrieveTraceOBPResource(File TraceFile, TraceTechnologyContextManager technologyContextManager) {
		try {
			ModelFactory factory = new ModelFactory(TraceOBPResource.class);
			TraceOBPResourceImpl returned = (TraceOBPResourceImpl) factory.newInstance(TraceOBPResource.class);
			returned.setName(TraceFile.getName());
			returned.setFile(TraceFile);
			returned.setURI(TraceFile.toURI().toString());
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

	@Override
	public FlexoTraceOBP loadResourceData(IProgress progress) throws ResourceLoadingCancelledException, FileNotFoundException, FlexoException {
		FlexoTraceOBP resourceData = null;
		TraceOBP traceObp = null;
		TraceExploObpParser parser = new TraceExploObpParser();
		
		if (getFile().exists()) {
			try {
				traceObp = parser.parserTraceOBP(getFile().getAbsolutePath());
				if (converter == null) {
					converter = new TraceModelConverter();
					converter.setTechnologyAdapter(getTechnologyAdapter());
				}
				resourceData = converter.convertTraceOBP(traceObp);
				resourceData.setResource(this);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		setResourceData(resourceData);
		return resourceData;
	}

	@Override
	public void save(IProgress progress) throws SaveResourceException {
		try {
			resourceData = getResourceData(progress);
		} catch (FileNotFoundException e) {
			FlexoTraceOBP resourceData;
			e.printStackTrace();
			throw new SaveResourceException(this);
		} catch (ResourceLoadingCancelledException e) {
			e.printStackTrace();
			throw new SaveResourceException(this);
		} catch (FlexoException e) {
			e.printStackTrace();
			throw new SaveResourceException(this);
		}
		FlexoTraceOBP resourceData = null;

		if (!hasWritePermission()) {
			if (logger.isLoggable(Level.WARNING)) {
				logger.warning("Permission denied : " + getFile().getAbsolutePath());
			}
			throw new SaveResourcePermissionDeniedException(this);
		}
		if (resourceData != null) {
			FlexoFileResourceImpl.FileWritingLock lock = willWriteOnDisk();
			writeToFile();
			hasWrittenOnDisk(lock);
			notifyResourceStatusChanged();
			resourceData.clearIsModified(false);
			if (logger.isLoggable(Level.INFO)) {
				logger.info("Succeeding to save Resource " + getURI() + " : " + getFile().getName());
			}
		}
	}

	private void writeToFile() throws SaveResourceException {
		logger.info("Wrote " + getFile());
	}

	@Override
	public Class<FlexoTraceOBP> getResourceDataClass() {
		return FlexoTraceOBP.class;
	}
}
