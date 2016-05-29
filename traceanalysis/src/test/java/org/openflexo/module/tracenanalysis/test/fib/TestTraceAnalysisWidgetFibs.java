package org.openflexo.module.tracenanalysis.test.fib;

import org.junit.Test;
import org.openflexo.gina.utils.GenericFIBTestCase;
import org.openflexo.rm.FileResourceImpl;
import org.openflexo.rm.ResourceLocator;

public class TestTraceAnalysisWidgetFibs extends GenericFIBTestCase {

	public static void main(String[] args) {
		System.out.println(
				generateFIBTestCaseClass(((FileResourceImpl) ResourceLocator.locateResource("Fib/Dialog")).getFile(), "Fib/Widget/"));
	}

	@Test
	public void testFIBConfigurationMaskWidget() {
		validateFIB("Fib/Widget/FIBConfigurationMask.fib");
	}

	@Test
	public void testFIBTraceAnalysisProjectBrowserWidget() {
		validateFIB("Fib/Widget/FIBTraceAnalysisProjectBrowser.fib");
	}

	@Test
	public void testFIBMaskableElementWidget() {
		validateFIB("Fib/Widget/FIBMaskableElement.fib");
	}

}
