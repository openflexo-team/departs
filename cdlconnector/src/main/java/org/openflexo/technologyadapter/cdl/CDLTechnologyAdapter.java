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

package org.openflexo.technologyadapter.cdl;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;

import org.openflexo.foundation.fml.annotations.DeclareModelSlot;
import org.openflexo.foundation.fml.annotations.DeclareModelSlots;
import org.openflexo.foundation.fml.annotations.DeclareRepositoryType;
import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.FlexoResourceCenterService;
import org.openflexo.foundation.resource.RepositoryFolder;
import org.openflexo.foundation.technologyadapter.TechnologyAdapter;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterBindingFactory;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterInitializationException;
import org.openflexo.foundation.technologyadapter.TechnologyContextManager;
import org.openflexo.technologyadapter.cdl.rm.CDLUnitRepository;
import org.openflexo.technologyadapter.cdl.rm.CDLUnitResource;
import org.openflexo.technologyadapter.cdl.rm.CDLUnitResourceImpl;
import org.openflexo.technologyadapter.cdl.virtualmodel.bindings.CDLBindingFactory;

/**
 * This class defines and implements the CDL technology adapter
 * 
 * @author SomeOne
 * 
 */

@DeclareModelSlots({ // ModelSlot(s) declaration
@DeclareModelSlot(FML = "CDLTypeAwareModelSlot", modelSlotClass = CDLModelSlot.class), })
@DeclareRepositoryType({ CDLUnitRepository.class })
public class CDLTechnologyAdapter extends TechnologyAdapter {
	private static String CDL_FILE_EXTENSION = ".cdl";

	protected static final Logger logger = Logger.getLogger(CDLTechnologyAdapter.class.getPackage().getName());

	private static final CDLBindingFactory BINDING_FACTORY = new CDLBindingFactory();

	public CDLTechnologyAdapter() throws TechnologyAdapterInitializationException {
	}

	@Override
	public String getName() {
		return new String("CDL Technology Adapter");
	}

	@Override
	public TechnologyContextManager createTechnologyContextManager(FlexoResourceCenterService service) {
		return new CDLTechnologyContextManager(this, service);
	}

	@Override
	public TechnologyAdapterBindingFactory getTechnologyAdapterBindingFactory() {
		return BINDING_FACTORY;
	}

	@Override
	public <I> void initializeResourceCenter(FlexoResourceCenter<I> resourceCenter) {
		CDLUnitRepository cdlUnitRepository = resourceCenter.getRepository(CDLUnitRepository.class, this);
		if (cdlUnitRepository == null) {
			cdlUnitRepository = createCDLUnitRepository(resourceCenter);
		}

		Iterator<I> it = resourceCenter.iterator();

		while (it.hasNext()) {
			I item = it.next();
			if (item instanceof File) {
				// System.out.println("searching " + item);
				File candidateFile = (File) item;
				tryToLookupCDLUnits(resourceCenter, candidateFile);
			}
		}
		// Call it to update the current repositories
		getPropertyChangeSupport().firePropertyChange("getAllRepositories()", null, resourceCenter);
	}

	protected CDLUnitResource tryToLookupCDLUnits(FlexoResourceCenter<?> resourceCenter, File candidateFile) {
		if (isValidCDLFile(candidateFile)) {
			CDLUnitResource cdlUnitRes = retrieveCDLUnitResource(candidateFile);
			CDLUnitRepository cdlUnitRepository = resourceCenter.getRepository(CDLUnitRepository.class, this);
			if (cdlUnitRes != null) {
				RepositoryFolder<CDLUnitResource> folder;
				try {
					folder = cdlUnitRepository.getRepositoryFolder(candidateFile, true);
					cdlUnitRepository.registerResource(cdlUnitRes, folder);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				referenceResource(cdlUnitRes, resourceCenter);
				return cdlUnitRes;
			}
		}
		return null;
	}

	/**
	 * Instantiate new workbook resource stored in supplied model file<br>
	 * *
	 */
	public CDLUnitResource retrieveCDLUnitResource(File cdlFile) {
		CDLUnitResource cdlUnitResource = null;

		// TODO: try to look-up already found file
		cdlUnitResource = CDLUnitResourceImpl.retrieveCDLUnitResource(cdlFile, getTechnologyContextManager());

		return cdlUnitResource;
	}

	/**
	 * 
	 * Create a cdl unit repository for current {@link TechnologyAdapter} and supplied {@link FlexoResourceCenter}
	 * 
	 */
	public CDLUnitRepository createCDLUnitRepository(FlexoResourceCenter<?> resourceCenter) {
		CDLUnitRepository returned = new CDLUnitRepository(this, resourceCenter);
		resourceCenter.registerRepository(returned, CDLUnitRepository.class, this);
		return returned;
	}

	/**
	 * Return flag indicating if supplied file appears as a valid cdl
	 * 
	 * @param candidateFile
	 * 
	 * @return
	 */
	public boolean isValidCDLFile(File candidateFile) {
		return candidateFile.getName().endsWith(CDL_FILE_EXTENSION);
	}

	@Override
	public <I> boolean isIgnorable(FlexoResourceCenter<I> resourceCenter, I contents) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <I> void contentsAdded(FlexoResourceCenter<I> resourceCenter, I contents) {
		if (contents instanceof File) {
			File candidateFile = (File) contents;
			tryToLookupCDLUnits(resourceCenter, candidateFile);
		}
		// Call it to update the current repositories
		getPropertyChangeSupport().firePropertyChange("getAllRepositories()", null, resourceCenter);
	}

	@Override
	public <I> void contentsDeleted(FlexoResourceCenter<I> resourceCenter, I contents) {
		// TODO Auto-generated method stub

	}

	@Override
	public CDLTechnologyContextManager getTechnologyContextManager() {
		// TODO Auto-generated method stub
		return (CDLTechnologyContextManager) super.getTechnologyContextManager();
	}

}
