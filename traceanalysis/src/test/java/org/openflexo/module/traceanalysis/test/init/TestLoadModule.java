package org.openflexo.module.traceanalysis.test.init;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openflexo.OpenflexoTestCaseWithGUI;
import org.openflexo.foundation.resource.FlexoResourceCenterService;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterService;
import org.openflexo.module.FlexoModule;
import org.openflexo.module.ModuleLoader;
import org.openflexo.module.ModuleLoadingException;
import org.openflexo.module.traceanalysis.TAModule;
import org.openflexo.module.traceanalysis.TraceAnalysisModule;
import org.openflexo.prefs.PreferencesService;
import org.openflexo.project.ProjectLoader;
import org.openflexo.test.OrderedRunner;
import org.openflexo.test.TestOrder;
import org.openflexo.view.controller.TechnologyAdapterControllerService;

@RunWith(OrderedRunner.class)
public class TestLoadModule extends OpenflexoTestCaseWithGUI {

	protected static final Logger logger = Logger.getLogger(TestLoadModule.class.getPackage().getName());

	private static ModuleLoader moduleLoader;

	/**
	 * Instanciate test ApplicationContext
	 */
	@Test
	@TestOrder(1)
	public void test0UseTestApplicationContext() {
		log("test0UseTestApplicationContext()");
		instanciateTestServiceManager();

		logger.info("services: " + serviceManager.getRegisteredServices());

		assertNotNull(serviceManager.getService(ProjectLoader.class));
		assertNotNull(serviceManager.getService(ModuleLoader.class));
		assertNotNull(serviceManager.getService(FlexoResourceCenterService.class));
		assertNotNull(serviceManager.getService(TechnologyAdapterService.class));
		assertNotNull(serviceManager.getService(TechnologyAdapterControllerService.class));
		assertNotNull(serviceManager.getService(PreferencesService.class));

		moduleLoader = serviceManager.getModuleLoader();
		assertEquals(moduleLoader, serviceManager.getService(ModuleLoader.class));

	}

	/**
	 * Try to load module
	 */
	@Test
	@TestOrder(2)
	public void testModuleLoading() {
		log("testModuleLoading()");

		try {
			FlexoModule<TAModule> loadedModule = moduleLoader.getModuleInstance(TraceAnalysisModule.INSTANCE);
			if (loadedModule == null) {
				fail();
			}
			// This module is not in the classpath, normal
		} catch (ModuleLoadingException e) {
			fail();
		}

		assertNotNull(serviceManager.getService(TechnologyAdapterControllerService.class));

	}

}
