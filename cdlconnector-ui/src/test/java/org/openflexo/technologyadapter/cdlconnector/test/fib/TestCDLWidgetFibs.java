package org.openflexo.technologyadapter.cdlconnector.test.fib;

import org.junit.Test;
import org.openflexo.gina.utils.GenericFIBTestCase;
import org.openflexo.rm.FileResourceImpl;
import org.openflexo.rm.ResourceLocator;

public class TestCDLWidgetFibs extends GenericFIBTestCase {

	public static void main(String[] args) {
		System.out.println(generateFIBTestCaseClass(((FileResourceImpl) ResourceLocator.locateResource("Fib")).getFile(), "Fib"));
	}

	@Test
	public void testFIBCDLUnitWidget() {
		validateFIB("Fib/CDLUnitView.fib");
	}

}
