package org.openflexo.module.traceanalysis.test.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoProject;
import org.openflexo.foundation.OpenflexoProjectAtRunTimeTestCase;
import org.openflexo.module.traceanalysis.model.TraceAnalysisProject;
import org.openflexo.module.traceanalysis.model.TraceAnalysisProjectNature;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.module.traceanalysis.model.mask.Mask;
import org.openflexo.technologyadapter.cdl.CDLTechnologyAdapter;
import org.openflexo.technologyadapter.fiacre.FiacreTechnologyAdapter;
import org.openflexo.technologyadapter.trace.TraceTechnologyAdapter;
import org.openflexo.test.OrderedRunner;
import org.openflexo.test.TestOrder;

/**
 * This unit test is intented to test TraceVirtualModelInstance creation in the context of TraceAnalysisProjectNature
 * 
 * @author vincent
 * 
 */
@RunWith(OrderedRunner.class)
public class TestCreateTraceVirtualModelInstance extends OpenflexoProjectAtRunTimeTestCase {

	static FlexoEditor editor;
	static FlexoProject project;
	static TraceAnalysisProject taProject;
	static TraceVirtualModelInstance traceVirtualModelInstance;
	static Mask mask;
	
	static CDLTechnologyAdapter cdlTa;
	static FiacreTechnologyAdapter fiacreTa;
	static TraceTechnologyAdapter traceTa;
	
	static String CDL_FILE = "/examples/calcul1_asynchro.cdl";
	static String FIACRE_FILE = "/examples/calcul1_asynchro.fcr";
	static String TRACE_FILE = "/examples/calcul1_asynchro.trace";

	/*@Test
	@TestOrder(1)
	public void testCreateTraceAnalysisProject() {

		instanciateTestServiceManager();
		
		cdlTa =getFlexoServiceManager().getTechnologyAdapterService().getTechnologyAdapter(CDLTechnologyAdapter.class);
		fiacreTa =getFlexoServiceManager().getTechnologyAdapterService().getTechnologyAdapter(FiacreTechnologyAdapter.class);
		traceTa =getFlexoServiceManager().getTechnologyAdapterService().getTechnologyAdapter(TraceTechnologyAdapter.class);
		assertNotNull(cdlTa);
		assertNotNull(fiacreTa);
		assertNotNull(traceTa);
		
		TraceAnalysisProjectNature TRACE_ANALYSIS_NATURE = serviceManager.getProjectNatureService().getProjectNature(
				TraceAnalysisProjectNature.class);
		assertNotNull(TRACE_ANALYSIS_NATURE);

		editor = createProject("TestTraceAnalysisProject", TRACE_ANALYSIS_NATURE);
		project = editor.getProject();
		System.out.println("Created project " + project.getProjectDirectory());
		assertTrue(project.getProjectDirectory().exists());
		assertTrue(project.getProjectDataResource().getFile().exists());
		assertTrue(project.hasNature(TRACE_ANALYSIS_NATURE));
		taProject = TRACE_ANALYSIS_NATURE.getTraceAnalysisProject(project);
		assertNotNull(taProject);
	}*/

	/*@Test
	@TestOrder(2)
	public void testCreateTraceAnalysis() {
		CreateTraceVirtualModelInstance action = CreateTraceVirtualModelInstance.actionType.makeNewAction(taProject, null, editor);
		action.setTraceAnalysisName("TraceAnalysis1");
		
		
		
		action.setCdl(cdlTa.retrieveCDLUnitResource((getResource(CDL_FILE))));
		action.setFiacre(fiacreTa.retrieveFiacreProgramResource(getResource(FIACRE_FILE)));
		action.setTrace(traceTa.retrieveTraceOBPResource(getResource(TRACE_FILE)));
		
		assertTrue(action.isValid());
		action.doAction();
		assertTrue(action.hasActionExecutionSucceeded());
		traceAnalysis = action.getTraceAnalysis();
		assertNotNull(traceAnalysis);
		assertEquals(traceAnalysis.getTraceAnalysisVirtualModelInstances().size(), 4);
	}*/

	/*@Test
	@TestOrder(3)
	public void testCreateConfigurationMask() {
		CreateConfigurationMask action = CreateConfigurationMask.actionType.makeNewAction(traceAnalysis, null, editor);
		action.setConfigurationMaskName("configurationMask1");
		assertTrue(action.isValid());
		action.doAction();
		assertTrue(action.hasActionExecutionSucceeded());
		configurationMask = action.getConfigurationMask();
		assertNotNull(configurationMask);
		
		try {
			traceAnalysis.getView().getResource().save(null);
		} catch (SaveResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	/**
	 * Reload project, check that everything is still ok
	 * 
	 * @throws FileNotFoundException
	 * @throws ResourceLoadingCancelledException
	 * @throws FlexoException
	 */
	/*@Test
	@TestOrder(4)
	public void testReloadProject() throws FileNotFoundException, ResourceLoadingCancelledException, FlexoException {

		instanciateTestServiceManager();
		editor = reloadProject(project.getDirectory());
		project = editor.getProject();
		assertNotNull(editor);
		assertNotNull(project);

		TraceAnalysisProjectNature TRACE_ANALYSIS_NATURE = serviceManager.getProjectNatureService().getProjectNature(
				TraceAnalysisProjectNature.class);
		assertNotNull(TRACE_ANALYSIS_NATURE);

		assertTrue(project.hasNature(TRACE_ANALYSIS_NATURE));
		taProject = TRACE_ANALYSIS_NATURE.getTraceAnalysisProject(project);
		assertNotNull(taProject);

		assertNotNull(taProject.getTraceVirtualModel());
		assertNotNull(taProject.getContextVirtualModel());
		assertNotNull(taProject.getSystemVirtualModel());
		assertNotNull(taProject.getTraceVirtualModel());

		traceAnalysis = taProject.getTraceAnalysis("TraceAnalysis1");
		assertNotNull(traceAnalysis);

		configurationMask = traceAnalysis.getTraceVirtualModelInstance().getConfigurationMask("configurationMask1");
		assertNotNull(configurationMask);
	}*/
}
