package org.openflexo.module.traceanalysis.test.model;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openflexo.foundation.OpenflexoProjectAtRunTimeTestCase;
import org.openflexo.module.traceanalysis.model.TraceAnalysisProjectNature;
import org.openflexo.test.OrderedRunner;
import org.openflexo.test.TestOrder;

/**
 * This unit test is intented to test project creation in the context of TestTraceAnalysisProjectNature
 * 
 * @author vincent
 * 
 */
@RunWith(OrderedRunner.class)
public class TestTraceAnalysisProjectNature extends OpenflexoProjectAtRunTimeTestCase {

	@Test
	@TestOrder(1)
	public void testRetrieveNatureFromClass() {

		instanciateTestServiceManager();

		TraceAnalysisProjectNature TRACE_ANALYSIS_NATURE = serviceManager.getProjectNatureService().getProjectNature(
				TraceAnalysisProjectNature.class);
		assertNotNull(TRACE_ANALYSIS_NATURE);

	}

	@Test
	@TestOrder(2)
	public void testRetrieveNatureFromClassName() {

		TraceAnalysisProjectNature TRACE_ANALYSIS_NATURE = (TraceAnalysisProjectNature) serviceManager.getProjectNatureService()
				.getProjectNature("org.openflexo.module.traceanalysis.model.TraceAnalysisProjectNature");
		assertNotNull(TRACE_ANALYSIS_NATURE);

	}

}
