package org.openflexo.module.tracenanalysis.test.fib;

import org.junit.Test;
import org.openflexo.fib.utils.GenericFIBTestCase;
import org.openflexo.rm.FileResourceImpl;
import org.openflexo.rm.ResourceLocator;

public class TestTraceAnalysisDialogFibs extends GenericFIBTestCase {

	public static void main(String[] args) {
		System.out.println(generateFIBTestCaseClass(((FileResourceImpl) ResourceLocator.locateResource("Fib/Dialog")).getFile(),
				"Fib/Dialog/"));
	}

	@Test
	public void testCreateConfigurationMaskDialog() {
		validateFIB("Fib/Dialog/CreateConfigurationMaskDialog.fib");
	}

	@Test
	public void testCreateTraceAnalysisDialog() {
		validateFIB("Fib/Dialog/CreateTraceAnalysisDialog.fib");
	}
}
