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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.resource.FileFlexoIODelegate;
import org.openflexo.foundation.resource.FileFlexoIODelegate.FileFlexoIODelegateImpl;
import org.openflexo.foundation.resource.FileWritingLock;
import org.openflexo.foundation.resource.FlexoResourceImpl;
import org.openflexo.foundation.resource.ResourceLoadingCancelledException;
import org.openflexo.foundation.resource.SaveResourceException;
import org.openflexo.foundation.resource.SaveResourcePermissionDeniedException;
import org.openflexo.technologyadapter.trace.model.OBPTrace;
import org.openflexo.technologyadapter.trace.model.io.TraceModelConverter;
import org.openflexo.toolbox.IProgress;

import Parser.TraceExploObpParser;
import Parser.TraceOBP;

public abstract class TraceOBPResourceImpl extends FlexoResourceImpl<OBPTrace>implements TraceOBPResource {
	private static final Logger logger = Logger.getLogger(TraceOBPResourceImpl.class.getPackage().getName());

	private TraceModelConverter converter;

	// private ResourceData resourceData;

	@Override
	public TraceModelConverter getConverter() {
		return converter;
	}

	public void setConverter(TraceModelConverter converter) {
		this.converter = converter;
	}

	@Override
	public OBPTrace loadResourceData(IProgress progress) throws ResourceLoadingCancelledException, FileNotFoundException, FlexoException {
		OBPTrace resourceData = null;
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
			OBPTrace resourceData;
			e.printStackTrace();
			throw new SaveResourceException(getFileFlexoIODelegate());
		} catch (ResourceLoadingCancelledException e) {
			e.printStackTrace();
			throw new SaveResourceException(getFileFlexoIODelegate());
		} catch (FlexoException e) {
			e.printStackTrace();
			throw new SaveResourceException(getFileFlexoIODelegate());
		}
		OBPTrace resourceData = null;

		if (!getFileFlexoIODelegate().hasWritePermission()) {
			if (logger.isLoggable(Level.WARNING)) {
				logger.warning("Permission denied : " + getFile().getAbsolutePath());
			}
			throw new SaveResourcePermissionDeniedException(getFileFlexoIODelegate());
		}
		if (resourceData != null) {
			FileWritingLock lock = ((FileFlexoIODelegateImpl) getFlexoIODelegate()).willWriteOnDisk();
			writeToFile();
			((FileFlexoIODelegateImpl) getFlexoIODelegate()).hasWrittenOnDisk(lock);
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
	public Class<OBPTrace> getResourceDataClass() {
		return OBPTrace.class;
	}

	private File getFile() {
		return getFileFlexoIODelegate().getFile();
	}

	private FileFlexoIODelegate getFileFlexoIODelegate() {
		return (FileFlexoIODelegate) getFlexoIODelegate();
	}
}
