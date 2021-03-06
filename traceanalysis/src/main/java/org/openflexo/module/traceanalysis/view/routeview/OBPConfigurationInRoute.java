/*
 * (c) Copyright 2014- Openflexo
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

package org.openflexo.module.traceanalysis.view.routeview;

import org.openflexo.foundation.FlexoObject;
import org.openflexo.module.traceanalysis.model.mask.MaskableConfiguration;
import org.openflexo.technologyadapter.trace.model.OBPTraceConfiguration;

/**
 * Reification of the link between a configuration and a route
 * 
 * @author sylvain
 * 
 */
public interface OBPConfigurationInRoute extends FlexoObject {

	public OBPRoute getRoute();

	public OBPTraceConfiguration getConfiguration();

	public double getVisibleIndex();

	public boolean nextTransitionIsAbstract();

	public RelativeConfigurationArtefact getPreviousConfigurationArtefact();

	public RelativeConfigurationArtefact getNextConfigurationArtefact();
	
	public boolean isVisible();
	
	public MaskableConfiguration getMaskableConfiguration();

	public interface RelativeConfigurationArtefact {

		/**
		 * Return the configuration in route where this relative artefact is defined
		 * 
		 * @return
		 */
		public OBPConfigurationInRoute getConfigurationInRoute();

		/**
		 * Return the represented configuration
		 * 
		 * @return
		 */
		public OBPTraceConfiguration getConfiguration();

	}

}
