/*
 * (c) Copyright 2013- Openflexo
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

package org.openflexo.technologyadapter.trace;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;

import org.openflexo.foundation.fml.annotations.DeclareModelSlots;
import org.openflexo.foundation.fml.annotations.DeclareRepositoryType;
import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.FlexoResourceCenterService;
import org.openflexo.foundation.resource.RepositoryFolder;
import org.openflexo.foundation.technologyadapter.TechnologyAdapter;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterBindingFactory;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterInitializationException;
import org.openflexo.foundation.technologyadapter.TechnologyContextManager;
import org.openflexo.technologyadapter.trace.rm.TraceOBPRepository;
import org.openflexo.technologyadapter.trace.rm.TraceOBPResource;
import org.openflexo.technologyadapter.trace.rm.TraceOBPResourceImpl;
import org.openflexo.technologyadapter.trace.virtualmodel.bindings.TraceBindingFactory;

/**
 * This class defines and implements the Trace technology adapter
 * 
 * @author SomeOne
 * 
 */

@DeclareModelSlots({ TraceModelSlot.class })
@DeclareRepositoryType({ TraceOBPRepository.class })
public class TraceTechnologyAdapter extends TechnologyAdapter {
	private static String TRACE_FILE_EXTENSION = ".trace";

	protected static final Logger logger = Logger.getLogger(TraceTechnologyAdapter.class.getPackage().getName());

	private static final TraceBindingFactory BINDING_FACTORY = new TraceBindingFactory();

	public TraceTechnologyAdapter() throws TechnologyAdapterInitializationException {
	}

	@Override
	public String getName() {
		return new String("Trace Technology Adapter");
	}

	@Override
	public String getLocalizationDirectory() {
		return "FlexoLocalization/TraceTechnologyAdapter";
	}

	@Override
	public String getIdentifier() {
		return "OBP-TRACE";
	}

	@Override
	public TechnologyContextManager createTechnologyContextManager(FlexoResourceCenterService service) {
		return new TraceTechnologyContextManager(this, service);
	}

	@Override
	public TechnologyAdapterBindingFactory getTechnologyAdapterBindingFactory() {
		return BINDING_FACTORY;
	}

	/**
	 * Initialize the supplied resource center with the technology<br>
	 * ResourceCenter is scanned, ResourceRepositories are created and new technology-specific resources are build and registered.
	 * 
	 * @param resourceCenter
	 */
	@Override
	public <I> void performInitializeResourceCenter(FlexoResourceCenter<I> resourceCenter) {
		TraceOBPRepository TraceOBPRepository = resourceCenter.getRepository(TraceOBPRepository.class, this);
		if (TraceOBPRepository == null) {
			TraceOBPRepository = createTraceOBPRepository(resourceCenter);
		}

		Iterator<I> it = resourceCenter.iterator();

		while (it.hasNext()) {
			I item = it.next();
			if (item instanceof File) {
				// System.out.println("searching " + item);
				File candidateFile = (File) item;
				tryToLookupTraceOBPs(resourceCenter, candidateFile);
			}
		}
		// Call it to update the current repositories
		notifyRepositoryStructureChanged();
	}

	protected TraceOBPResource tryToLookupTraceOBPs(FlexoResourceCenter<?> resourceCenter, File candidateFile) {
		if (isValidTraceFile(candidateFile)) {
			TraceOBPResource TraceOBPRes = retrieveTraceOBPResource(candidateFile);
			TraceOBPRepository TraceOBPRepository = resourceCenter.getRepository(TraceOBPRepository.class, this);
			if (TraceOBPRes != null) {
				RepositoryFolder<TraceOBPResource> folder;
				try {
					folder = TraceOBPRepository.getRepositoryFolder(candidateFile, true);
					TraceOBPRepository.registerResource(TraceOBPRes, folder);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				referenceResource(TraceOBPRes, resourceCenter);
				return TraceOBPRes;
			}
		}
		return null;
	}

	/**
	 * Instantiate new workbook resource stored in supplied model file<br>
	 * *
	 */
	public TraceOBPResource retrieveTraceOBPResource(File TraceFile) {
		TraceOBPResource TraceOBPResource = null;

		// TODO: try to look-up already found file
		TraceOBPResource = TraceOBPResourceImpl.retrieveTraceOBPResource(TraceFile, getTechnologyContextManager());

		return TraceOBPResource;
	}

	/**
	 * 
	 * Create a Trace OBP repository for current {@link TechnologyAdapter} and supplied {@link FlexoResourceCenter}
	 * 
	 */
	public TraceOBPRepository createTraceOBPRepository(FlexoResourceCenter<?> resourceCenter) {
		TraceOBPRepository returned = new TraceOBPRepository(this, resourceCenter);
		resourceCenter.registerRepository(returned, TraceOBPRepository.class, this);
		return returned;
	}

	/**
	 * Return flag indicating if supplied file appears as a valid trace
	 * 
	 * @param candidateFile
	 * 
	 * @return
	 */
	public boolean isValidTraceFile(File candidateFile) {
		return candidateFile.getName().endsWith(TRACE_FILE_EXTENSION);
	}

	@Override
	public <I> boolean isIgnorable(FlexoResourceCenter<I> resourceCenter, I contents) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <I> boolean contentsAdded(FlexoResourceCenter<I> resourceCenter, I contents) {
		TraceOBPResource newResource = null;
		if (contents instanceof File) {
			File candidateFile = (File) contents;
			newResource = tryToLookupTraceOBPs(resourceCenter, candidateFile);
		}
		// Call it to update the current repositories
		notifyRepositoryStructureChanged();
		return newResource != null;
	}

	@Override
	public <I> boolean contentsModified(FlexoResourceCenter<I> resourceCenter, I contents) {
		return false;
	}

	@Override
	public <I> boolean contentsDeleted(FlexoResourceCenter<I> resourceCenter, I contents) {
		return false;
	}

	@Override
	public <I> boolean contentsRenamed(FlexoResourceCenter<I> resourceCenter, I contents, String oldName, String newName) {
		return false;
	}

	@Override
	public TraceTechnologyContextManager getTechnologyContextManager() {
		// TODO Auto-generated method stub
		return (TraceTechnologyContextManager) super.getTechnologyContextManager();
	}
}
