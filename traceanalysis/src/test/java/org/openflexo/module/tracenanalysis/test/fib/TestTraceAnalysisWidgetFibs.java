package org.openflexo.module.tracenanalysis.test.fib;

import org.junit.Test;
import org.openflexo.fib.utils.GenericFIBTestCase;
import org.openflexo.rm.FileResourceImpl;
import org.openflexo.rm.ResourceLocator;

public class TestTraceAnalysisWidgetFibs extends GenericFIBTestCase {

	public static void main(String[] args) {
		System.out.println(generateFIBTestCaseClass(((FileResourceImpl) ResourceLocator.locateResource("Fib/Dialog")).getFile(),
				"Fib/Widget/"));
	}

	@Test
	public void testFIBAnalyzeConceptsBrowserWidget() {
		validateFIB("Fib/Widget/FIBAnalyzeConceptsBrowser.fib");
	}

	@Test
	public void testFIBConfigurationMaskBrowserWidget() {
		validateFIB("Fib/Widget/FIBConfigurationMaskModuleView.fib");
	}

	@Test
	public void testFIBTraceAnalysisModuleViewWidget() {
		validateFIB("Fib/Widget/FIBTraceAnalysisModuleView.fib");
	}

	@Test
	public void testFIBTraceAnalysisProjectBrowserWidget() {
		validateFIB("Fib/Widget/FIBTraceAnalysisProjectBrowser.fib");
	}

}
