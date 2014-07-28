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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import obp.fiacre.util.FiacreUtil;

import org.apache.commons.io.IOUtils;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.resource.FlexoFileResourceImpl;
import org.openflexo.foundation.resource.ResourceLoadingCancelledException;
import org.openflexo.foundation.resource.SaveResourceException;
import org.openflexo.foundation.resource.SaveResourcePermissionDeniedException;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.model.factory.ModelFactory;
import org.openflexo.technologyadapter.fiacre.FiacreTechnologyContextManager;
import org.openflexo.technologyadapter.fiacre.model.FiacreProgram;
import org.openflexo.technologyadapter.fiacre.model.io.FiacreProgramConverter;
import org.openflexo.toolbox.IProgress;

public abstract class FiacreProgramResourceImpl extends FlexoFileResourceImpl<FiacreProgram> implements FiacreProgramResource {
	private static final Logger logger = Logger.getLogger(FiacreProgramResourceImpl.class.getPackage().getName());

	private FiacreProgramConverter converter;

	@Override
	public FiacreProgramConverter getConverter() {
		return converter;
	}

	public void setConverter(FiacreProgramConverter converter) {
		this.converter = converter;
	}

	public static FiacreProgramResource makeFiacreProgramResource(String modelURI, File modelFile,
			FiacreTechnologyContextManager technologyContextManager) {
		try {
			ModelFactory factory = new ModelFactory(FiacreProgramResource.class);
			FiacreProgramResourceImpl returned = (FiacreProgramResourceImpl) factory.newInstance(FiacreProgramResource.class);
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

	public static FiacreProgramResource retrieveFiacreProgramResource(File FiacreFile,
			FiacreTechnologyContextManager technologyContextManager) {
		try {
			ModelFactory factory = new ModelFactory(FiacreProgramResource.class);
			FiacreProgramResourceImpl returned = (FiacreProgramResourceImpl) factory.newInstance(FiacreProgramResource.class);
			returned.setName(FiacreFile.getName());
			returned.setFile(FiacreFile);
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
	}

	@Override
	public FiacreProgram loadResourceData(IProgress progress) throws ResourceLoadingCancelledException, FileNotFoundException,
			FlexoException {
		FiacreProgram resourceData = null;
		obp.fiacre.model.Program Program = null;

		if (getFile().exists()) {
			try {
				Program = FiacreUtil.loadProgram(getFile());
				if (converter == null) {
					converter = new FiacreProgramConverter();
					converter.setTechnologyAdapter(getTechnologyAdapter());
				}
				resourceData = converter.convertFiacreProgram(Program, getTechnologyAdapter());
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
			FiacreProgram resourceData;
			e.printStackTrace();
			throw new SaveResourceException(this);
		} catch (ResourceLoadingCancelledException e) {
			e.printStackTrace();
			throw new SaveResourceException(this);
		} catch (FlexoException e) {
			e.printStackTrace();
			throw new SaveResourceException(this);
		}
		FiacreProgram resourceData = null;

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
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(getFile());
			StreamResult result = new StreamResult(out);
			TransformerFactory factory = TransformerFactory.newInstance(
					"com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl", null);

			Transformer transformer = factory.newTransformer();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new SaveResourceException(this);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
			throw new SaveResourceException(this);
		} finally {
			IOUtils.closeQuietly(out);
		}

		logger.info("Wrote " + getFile());
	}

	@Override
	public Class<FiacreProgram> getResourceDataClass() {
		return FiacreProgram.class;
	}
}
