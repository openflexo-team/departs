/*
 * (c) Copyright 2010-2011 AgileBirds
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
package org.openflexo.module.traceanalysis.test.init;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openflexo.foundation.OpenflexoTestCase;
import org.openflexo.foundation.resource.FlexoResourceCenterService;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterService;
import org.openflexo.foundation.viewpoint.VirtualModelTechnologyAdapter;
import org.openflexo.logging.FlexoLogger;
import org.openflexo.technologyadapter.cdl.CDLTechnologyAdapter;
import org.openflexo.technologyadapter.fiacre.FiacreTechnologyAdapter;
import org.openflexo.technologyadapter.trace.TraceTechnologyAdapter;
import org.openflexo.test.OrderedRunner;
import org.openflexo.test.TestOrder;

/**
 * Test instanciation of VirtualModelModelFactory<br>
 * Here the model factory is instanciated with all known technology adapters
 * 
 */
@RunWith(OrderedRunner.class)
public class TestLoadAllTechnologyAdapters extends OpenflexoTestCase {

	private static final Logger logger = FlexoLogger.getLogger(TestLoadAllTechnologyAdapters.class.getPackage().getName());

	private static TechnologyAdapterService taService;

	/**
	 * Instanciate test ServiceManager
	 */
	@Test
	@TestOrder(1)
	public void initializeServiceManager() {
		log("initializeServiceManager()");
		instanciateTestServiceManager();

		logger.info("services: " + serviceManager.getRegisteredServices());

		assertNotNull(serviceManager.getService(FlexoResourceCenterService.class));
		assertNotNull(serviceManager.getService(TechnologyAdapterService.class));

		taService = serviceManager.getTechnologyAdapterService();
		assertEquals(taService, serviceManager.getService(TechnologyAdapterService.class));

	}

	/**
	 * Check the presence of VirtualModelTechnologyAdapter
	 */
	@Test
	@TestOrder(2)
	public void checkVirtualModelTechnologyAdapter() {
		log("checkVirtualModelTechnologyAdapter()");

		assertNotNull(taService.getTechnologyAdapter(VirtualModelTechnologyAdapter.class));
	}

	@Test
	@TestOrder(3)
	public void checkCDLTechnologyAdapter() {
		log("checkCDLTechnologyAdapter()");

		assertNotNull(taService.getTechnologyAdapter(CDLTechnologyAdapter.class));
	}

	@Test
	@TestOrder(4)
	public void checkFiacreTechnologyAdapter() {
		log("checkFiacreTechnologyAdapter()");

		assertNotNull(taService.getTechnologyAdapter(FiacreTechnologyAdapter.class));
	}
	
	@Test
	@TestOrder(5)
	public void checkTraceTechnologyAdapter() {
		log("checkTraceTechnologyAdapter()");

		assertNotNull(taService.getTechnologyAdapter(TraceTechnologyAdapter.class));
	}

}
