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
import org.openflexo.foundation.resource.FileFlexoIODelegate.FileFlexoIODelegateImpl;
import org.openflexo.foundation.resource.FileWritingLock;
import org.openflexo.foundation.resource.FlexoResourceImpl;
import org.openflexo.foundation.resource.ResourceLoadingCancelledException;
import org.openflexo.foundation.resource.SaveResourceException;
import org.openflexo.foundation.resource.SaveResourcePermissionDeniedException;
import org.openflexo.technologyadapter.cdl.model.CDLUnit;
import org.openflexo.technologyadapter.cdl.model.io.CDLModelConverter;
import org.openflexo.toolbox.IProgress;

import obp.util.CDLUtil;

public abstract class CDLUnitResourceImpl extends FlexoResourceImpl<CDLUnit>implements CDLUnitResource {
	private static final Logger logger = Logger.getLogger(CDLUnitResourceImpl.class.getPackage().getName());

	private CDLModelConverter converter;

	@Override
	public CDLModelConverter getConverter() {
		return converter;
	}

	public void setConverter(CDLModelConverter converter) {
		this.converter = converter;
	}

	@Override
	public CDLUnit loadResourceData(IProgress progress) throws ResourceLoadingCancelledException, FileNotFoundException, FlexoException {
		CDLUnit resourceData = null;
		obp.cdl.CDLUnit unit = null;

		if (getFlexoIODelegate().exists()) {
			try {
				unit = CDLUtil.readCDLAndResolveReferences(((File) getFlexoIODelegate().getSerializationArtefact()));
				if (converter == null) {
					converter = new CDLModelConverter();
					converter.setTechnologyAdapter(getTechnologyAdapter());
				}
				resourceData = converter.convertCDLUnit(unit);
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
			CDLUnit resourceData;
			e.printStackTrace();
			throw new SaveResourceException(getFlexoIODelegate());
		} catch (ResourceLoadingCancelledException e) {
			e.printStackTrace();
			throw new SaveResourceException(getFlexoIODelegate());
		} catch (FlexoException e) {
			e.printStackTrace();
			throw new SaveResourceException(getFlexoIODelegate());
		}
		CDLUnit resourceData = null;

		if (!getFlexoIODelegate().hasWritePermission()) {
			if (logger.isLoggable(Level.WARNING)) {
				logger.warning("Permission denied : " + ((File) getFlexoIODelegate().getSerializationArtefact()).getAbsolutePath());
			}
			throw new SaveResourcePermissionDeniedException(getFlexoIODelegate());
		}
		if (resourceData != null) {
			FileWritingLock lock = ((FileFlexoIODelegateImpl) getFlexoIODelegate()).willWriteOnDisk();
			writeToFile();
			((FileFlexoIODelegateImpl) getFlexoIODelegate()).hasWrittenOnDisk(lock);
			notifyResourceStatusChanged();
			resourceData.clearIsModified(false);
			if (logger.isLoggable(Level.INFO)) {
				logger.info("Succeeding to save Resource " + getURI() + " : "
						+ ((File) getFlexoIODelegate().getSerializationArtefact()).getName());
			}
		}
	}

	private void writeToFile() throws SaveResourceException {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(((File) getFlexoIODelegate().getSerializationArtefact()));
			StreamResult result = new StreamResult(out);
			TransformerFactory factory = TransformerFactory
					.newInstance("com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl", null);

			Transformer transformer = factory.newTransformer();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new SaveResourceException(getFlexoIODelegate());
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
			throw new SaveResourceException(getFlexoIODelegate());
		} finally {
			IOUtils.closeQuietly(out);
		}

		logger.info("Wrote " + (getFlexoIODelegate().getSerializationArtefact()));
	}

	@Override
	public Class<CDLUnit> getResourceDataClass() {
		return CDLUnit.class;
	}
}
