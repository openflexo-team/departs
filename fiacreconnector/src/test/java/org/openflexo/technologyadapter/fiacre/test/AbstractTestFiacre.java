/**
 * 
 * Copyright (c) 2014, Openflexo
 * 
 * This file is part of Excelconnector, a component of the software infrastructure 
 * developed at Openflexo.
 * 
 * 
 * Openflexo is dual-licensed under the European Union Public License (EUPL, either 
 * version 1.1 of the License, or any later version ), which is available at 
 * https://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 * and the GNU General Public License (GPL, either version 3 of the License, or any 
 * later version), which is available at http://www.gnu.org/licenses/gpl.html .
 * 
 * You can redistribute it and/or modify under the teFiacres of either of these licenses
 * 
 * If you choose to redistribute it and/or modify under the teFiacres of the GNU GPL, you
 * must include the following additional peFiacreission.
 *
 *          Additional peFiacreission under GNU GPL version 3 section 7
 *
 *          If you modify this Program, or any covered work, by linking or 
 *          combining it with software containing parts covered by the teFiacres 
 *          of EPL 1.0, the licensors of this Program grant you additional peFiacreission
 *          to convey the resulting work. * 
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE. 
 *
 * See http://www.openflexo.org/license.html for details.
 * 
 * 
 * Please contact Openflexo (openflexo-contacts@openflexo.org)
 * or visit www.openflexo.org if you need additional infoFiacreation.
 * 
 */

package org.openflexo.technologyadapter.fiacre.test;

import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.FlexoServiceManager;
import org.openflexo.foundation.OpenflexoProjectAtRunTimeTestCase;
import org.openflexo.foundation.resource.FlexoResource;
import org.openflexo.foundation.resource.ResourceLoadingCancelledException;
import org.openflexo.technologyadapter.fiacre.FiacreTechnologyAdapter;
import org.openflexo.technologyadapter.fiacre.model.FiacreProgram;
import org.openflexo.technologyadapter.fiacre.rm.FiacreProgramResource;


public abstract class AbstractTestFiacre extends OpenflexoProjectAtRunTimeTestCase {
	protected static final Logger logger = Logger.getLogger(AbstractTestFiacre.class.getPackage().getName());

	protected static FlexoServiceManager instanciateTestServiceManagerForFiacre() {
		serviceManager = instanciateTestServiceManager();
		FiacreTechnologyAdapter FiacreTA = serviceManager.getTechnologyAdapterService().getTechnologyAdapter(FiacreTechnologyAdapter.class);
		serviceManager.activateTechnologyAdapter(FiacreTA);
		return serviceManager;
	}

	protected FiacreProgramResource getFiacreProgramResource(String name) {

		String documentURI = resourceCenter.getDefaultBaseURI() + "/" + "TestResourceCenter" + "/" + "Fiacre" + "/" + name;
		System.out.println("Searching " + documentURI);

		FiacreProgramResource documentResource = (FiacreProgramResource) serviceManager.getResourceManager().getResource(documentURI, null,
				FiacreProgram.class);

		if (documentResource == null) {
			logger.warning("Cannot find document resource " + documentURI);
			for (FlexoResource<?> r : serviceManager.getResourceManager().getRegisteredResources()) {
				System.out.println("> " + r.getURI());
			}
		}

		assertNotNull(documentResource);

		return documentResource;

	}

	protected FiacreProgram getFiacreProgram(String documentName) {

		FiacreProgramResource documentResource = getFiacreProgramResource(documentName);
		assertNotNull(documentResource);

		FiacreProgram document = null;
		document = documentResource.getLoadedResourceData();
		
		assertNotNull(document);
		assertNotNull(document.getFiacreProgram());

		return document;
	}


}
