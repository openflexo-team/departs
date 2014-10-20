package org.openflexo.technologyadapter.fiacre.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoProject;
import org.openflexo.foundation.OpenflexoProjectAtRunTimeTestCase;
import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.technologyadapter.fiacre.FiacreTechnologyAdapter;
import org.openflexo.technologyadapter.fiacre.rm.FiacreProgramRepository;
import org.openflexo.technologyadapter.fiacre.rm.FiacreProgramResource;
import org.openflexo.test.OrderedRunner;
import org.openflexo.test.TestOrder;

@RunWith(OrderedRunner.class)
public class TestLoadFiacreProgram extends OpenflexoProjectAtRunTimeTestCase {
	protected static final Logger logger = Logger.getLogger(TestLoadFiacreProgram.class.getPackage().getName());

	private static FlexoEditor editor;
	private static FlexoProject project;
	
	@Test
	@TestOrder(1)
	public void testInitializeServiceManager() throws Exception {
		instanciateTestServiceManager();
	}
	
	@Test
	@TestOrder(2)
	public void testCreateProject() {
		editor = createProject("TestProject");
		project = editor.getProject();
		System.out.println("Created project " + project.getProjectDirectory());
		assertTrue(project.getProjectDirectory().exists());
		assertTrue(project.getProjectDataResource().getFlexoIODelegate().exists());
	}
	
	@Test
	@TestOrder(3)
	public void testLoadFiacrePrograms() {
		FiacreTechnologyAdapter technologicalAdapter = serviceManager.getTechnologyAdapterService().getTechnologyAdapter(
				FiacreTechnologyAdapter.class);

		for (FlexoResourceCenter<?> resourceCenter : serviceManager.getResourceCenterService().getResourceCenters()) {
			FiacreProgramRepository fiacreRepository = resourceCenter.getRepository(FiacreProgramRepository.class, technologicalAdapter);
			assertNotNull(fiacreRepository);
			Collection<FiacreProgramResource> fiacreResources = fiacreRepository.getAllResources();
			for (FiacreProgramResource fiacreProgramResource : fiacreResources) {
				assertNotNull(fiacreProgramResource.getLoadedResourceData());
			}
		}
	}
}