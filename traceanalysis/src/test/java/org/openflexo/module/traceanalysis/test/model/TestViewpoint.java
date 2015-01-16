/*
 * (c) Copyright 2010-2011 AgileBirds
 *
 * This file is part of OpenFlexo.
 *
 * OpenFlexo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenFlexo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenFlexo. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.openflexo.module.traceanalysis.test.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openflexo.foundation.OpenflexoTestCase;
import org.openflexo.foundation.fml.ViewPoint;
import org.openflexo.foundation.fml.rm.ViewPointResource;
import org.openflexo.test.OrderedRunner;
import org.openflexo.test.TestOrder;

@RunWith(OrderedRunner.class)
public class TestViewpoint extends OpenflexoTestCase {

	protected static final Logger logger = Logger.getLogger(TestViewpoint.class.getPackage().getName());

	/**
	 * Instantiate test resource center
	 */
	@Test
	@TestOrder(1)
	public void test0InstantiateResourceCenter() {

		log("test0InstantiateResourceCenter()");

		// TODO: create a project where all those tests don't need a manual import of projects
		// TODO: copy all test VP in tmp dir and work with those VP instead of polling GIT workspace
		instanciateTestServiceManager();

	}

	private ViewPoint testLoadViewPoint(String viewPointURI) {

		log("Testing ViewPoint loading: " + viewPointURI);

		System.out.println("resourceCenter=" + resourceCenter);
		System.out.println("resourceCenter.getViewPointRepository()=" + resourceCenter.getViewPointRepository());

		ViewPointResource vpRes = resourceCenter.getViewPointRepository().getResource(viewPointURI);

		assertNotNull(vpRes);
		assertFalse(vpRes.isLoaded());

		ViewPoint vp = vpRes.getViewPoint();
		assertTrue(vpRes.isLoaded());

		return vp;

	}

	@Test
	@TestOrder(2)
	public void testDepartViewpoint() {
		log("testDepartViewpoint()");

		ViewPoint viewpoint = testLoadViewPoint("http://depart.v1");
		assertNotNull(viewpoint);
		System.out.println("Read resource " + ((ViewPointResource) viewpoint.getResource()).getFlexoIODelegate().toString());

		assertViewPointIsValid(viewpoint);

	}
}
