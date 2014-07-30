package org.openflexo.module.tracenanalysis.test.fib;

import org.junit.Test;
import org.openflexo.fib.utils.GenericFIBInspectorTestCase;
import org.openflexo.rm.FileResourceImpl;
import org.openflexo.rm.ResourceLocator;

public class TestTraceAnalysisInspectorFibs extends GenericFIBInspectorTestCase {

	public static void main(String[] args) {
		System.out.println(generateInspectorTestCaseClass(((FileResourceImpl) ResourceLocator.locateResource("Inspectors/TRACEANALYSIS")).getFile(),
				"Inspectors/TRACEANALYSIS/"));
	}

	@Test
	public void testTraceAnalysisInspector() {
		validateFIB("Inspectors/TRACEANALYSIS/FIBTraceVirtualModelInstance.inspector");
	}

}
