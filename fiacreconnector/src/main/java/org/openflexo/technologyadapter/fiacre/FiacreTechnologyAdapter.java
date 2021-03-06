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

package org.openflexo.technologyadapter.fiacre;

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
import org.openflexo.technologyadapter.fiacre.fml.bindings.FiacreBindingFactory;
import org.openflexo.technologyadapter.fiacre.rm.FiacreProgramRepository;
import org.openflexo.technologyadapter.fiacre.rm.FiacreProgramResource;
import org.openflexo.technologyadapter.fiacre.rm.FiacreProgramResourceImpl;

/**
 * This class defines and implements the Fiacre technology adapter
 * 
 * @author SomeOne
 * 
 */

@DeclareModelSlots({ FiacreProgramModelSlot.class })
@DeclareRepositoryType({ FiacreProgramRepository.class })
public class FiacreTechnologyAdapter extends TechnologyAdapter {
	private static String FIACRE_FILE_EXTENSION = ".fcr";

	protected static final Logger logger = Logger.getLogger(FiacreTechnologyAdapter.class.getPackage().getName());

	private static final FiacreBindingFactory BINDING_FACTORY = new FiacreBindingFactory();

	public FiacreTechnologyAdapter() throws TechnologyAdapterInitializationException {
	}

	@Override
	public String getName() {
		return new String("Fiacre Technology Adapter");
	}

	@Override
	public TechnologyContextManager createTechnologyContextManager(FlexoResourceCenterService service) {
		return new FiacreTechnologyContextManager(this, service);
	}

	@Override
	public TechnologyAdapterBindingFactory getTechnologyAdapterBindingFactory() {
		return BINDING_FACTORY;
	}

	@Override
	public <I> void initializeResourceCenter(FlexoResourceCenter<I> resourceCenter) {
		FiacreProgramRepository FiacreProgramRepository = resourceCenter.getRepository(FiacreProgramRepository.class, this);
		if (FiacreProgramRepository == null) {
			FiacreProgramRepository = createFiacreProgramRepository(resourceCenter);
		}

		Iterator<I> it = resourceCenter.iterator();

		while (it.hasNext()) {
			I item = it.next();
			if (item instanceof File) {
				// System.out.println("searching " + item);
				File candidateFile = (File) item;
				tryToLookupFiacrePrograms(resourceCenter, candidateFile);
			}
		}
		// Call it to update the current repositories
		getPropertyChangeSupport().firePropertyChange("getAllRepositories()", null, resourceCenter);
	}

	protected FiacreProgramResource tryToLookupFiacrePrograms(FlexoResourceCenter<?> resourceCenter, File candidateFile) {
		if (isValidFiacreFile(candidateFile)) {
			FiacreProgramResource FiacreProgramRes = retrieveFiacreProgramResource(candidateFile);
			FiacreProgramRepository FiacreProgramRepository = resourceCenter.getRepository(FiacreProgramRepository.class, this);
			if (FiacreProgramRes != null) {
				RepositoryFolder<FiacreProgramResource> folder;
				try {
					folder = FiacreProgramRepository.getRepositoryFolder(candidateFile, true);
					FiacreProgramRepository.registerResource(FiacreProgramRes, folder);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				referenceResource(FiacreProgramRes, resourceCenter);
				return FiacreProgramRes;
			}
		}
		return null;
	}

	/**
	 * Instantiate new workbook resource stored in supplied model file<br>
	 * *
	 */
	public FiacreProgramResource retrieveFiacreProgramResource(File FiacreFile) {
		FiacreProgramResource FiacreProgramResource = null;

		// TODO: try to look-up already found file
		FiacreProgramResource = FiacreProgramResourceImpl.retrieveFiacreProgramResource(FiacreFile, getTechnologyContextManager());

		return FiacreProgramResource;
	}

	/**
	 * 
	 * Create a Fiacre unit repository for current {@link TechnologyAdapter} and supplied {@link FlexoResourceCenter}
	 * 
	 */
	public FiacreProgramRepository createFiacreProgramRepository(FlexoResourceCenter<?> resourceCenter) {
		FiacreProgramRepository returned = new FiacreProgramRepository(this, resourceCenter);
		resourceCenter.registerRepository(returned, FiacreProgramRepository.class, this);
		return returned;
	}

	/**
	 * Return flag indicating if supplied file appears as a valid fiacre
	 * 
	 * @param candidateFile
	 * 
	 * @return
	 */
	public boolean isValidFiacreFile(File candidateFile) {
		return candidateFile.getName().endsWith(FIACRE_FILE_EXTENSION);
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
			tryToLookupFiacrePrograms(resourceCenter, candidateFile);
		}
		// Call it to update the current repositories
		getPropertyChangeSupport().firePropertyChange("getAllRepositories()", null, resourceCenter);
	}

	@Override
	public <I> void contentsDeleted(FlexoResourceCenter<I> resourceCenter, I contents) {
		// TODO Auto-generated method stub

	}

	@Override
	public FiacreTechnologyContextManager getTechnologyContextManager() {
		// TODO Auto-generated method stub
		return (FiacreTechnologyContextManager) super.getTechnologyContextManager();
	}

}
