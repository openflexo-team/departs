package org.openflexo.technologyadapter.fiacre.test;

import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.resource.ResourceLoadingCancelledException;
import org.openflexo.technologyadapter.fiacre.FiacreTechnologyAdapter;
import org.openflexo.technologyadapter.fiacre.model.FiacreProgram;
import org.openflexo.technologyadapter.fiacre.rm.FiacreProgramRepository;
import org.openflexo.technologyadapter.fiacre.rm.FiacreProgramResource;
import org.openflexo.test.OrderedRunner;
import org.openflexo.test.TestOrder;


@RunWith(OrderedRunner.class)
public class TestLoadFiacreProgram extends AbstractTestFiacre {
	protected static final Logger logger = Logger.getLogger(TestLoadFiacreProgram.class.getPackage().getName());

	static protected FiacreTechnologyAdapter technologicalAdapter;
	static protected FiacreProgramRepository fiacreRep;
	
	@AfterClass
	public static void tearDownClass() {
		deleteProject();
		deleteTestResourceCenters();
		unloadServiceManager();
	}


	@Test
	@TestOrder(1)
	public void testInitializeServiceManager() throws Exception {

		instanciateTestServiceManagerForFiacre();

		technologicalAdapter = serviceManager.getTechnologyAdapterService().getTechnologyAdapter(FiacreTechnologyAdapter.class);
		fiacreRep = technologicalAdapter.getFiacreProgramRepository(resourceCenter);
		_editor = new FlexoTestEditor(null, serviceManager);

		assertNotNull(serviceManager);
		assertNotNull(technologicalAdapter);
		assertNotNull(resourceCenter);
		assertNotNull(fiacreRep);

		System.out.println("URI" + resourceCenter.getDefaultBaseURI());
	}

	@Test
	@TestOrder(2)
	public void testFiacreLoading() {
		Collection<FiacreProgramResource> documents = fiacreRep.getAllResources();
		for (FiacreProgramResource docResource : documents) {
			try {
				docResource.loadResourceData(null);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ResourceLoadingCancelledException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FlexoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertNotNull(docResource.getLoadedResourceData());
			System.out.println("URI of document: " + docResource.getURI());
		}
	}

	@Test
	@TestOrder(3)
	public void testProgram1Loading() {

		FiacreProgram sc1 = getFiacreProgram("printed_test1.fcr");
		System.out.println("printed_test1.fcr:\n" + sc1);

	}
}
