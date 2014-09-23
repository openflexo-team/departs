package org.openflexo.module.traceanalysis.test.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoProject;
import org.openflexo.foundation.OpenflexoProjectAtRunTimeTestCase;
import org.openflexo.module.traceanalysis.model.TraceAnalysisProjectNature;
import org.openflexo.test.TestOrder;

/**
 * This unit test is intented to test project creation in the context of TraceAnalysisProjectNature
 * 
 * @author vincent
 * 
 */
public class TestCreateTraceAnalysisProject extends OpenflexoProjectAtRunTimeTestCase {

	/*@Test
	@TestOrder(1)
	public void testCreateTraceAnalysisProject() {

		instanciateTestServiceManager();

		TraceAnalysisProjectNature TRACE_ANALYSIS_NATURE = serviceManager.getProjectNatureService().getProjectNature(
				TraceAnalysisProjectNature.class);
		assertNotNull(TRACE_ANALYSIS_NATURE);

		FlexoEditor editor = createProject("TestTraceAnalysisProject", TRACE_ANALYSIS_NATURE);
		FlexoProject project = editor.getProject();
		System.out.println("Created project " + project.getProjectDirectory());
		assertTrue(project.getProjectDirectory().exists());
		assertTrue(project.getProjectDataResource().getFile().exists());
		assertTrue(project.hasNature(TRACE_ANALYSIS_NATURE));
	}*/

}
