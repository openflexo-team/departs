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
package org.openflexo.module.traceanalysis.view;

import org.openflexo.module.traceanalysis.model.ConfigurationMask;
import org.openflexo.rm.Resource;
import org.openflexo.rm.ResourceLocator;
import org.openflexo.view.FIBModuleView;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.model.FlexoPerspective;

@SuppressWarnings("serial")
public class InspectedConfigurationModuleView extends FIBModuleView<ConfigurationMask>{

	private final FlexoPerspective perspective;
	
	public static final Resource FIB_FILE = ResourceLocator.locateResource("Fib/Widget/FIBInspectedConfiguration.fib");
	
	public InspectedConfigurationModuleView(ConfigurationMask configurationMask, FlexoController controller, FlexoPerspective perspective) {
		super(configurationMask, controller, FIB_FILE);
		this.perspective = perspective;
	}

	@Override
	public void show(final FlexoController controller, FlexoPerspective perspective) {
		revalidate();
		repaint();
	}

	@Override
	public FlexoPerspective getPerspective() {
		return perspective;
	}
}
