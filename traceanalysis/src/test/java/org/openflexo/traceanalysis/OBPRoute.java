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

package org.openflexo.traceanalysis;

import org.openflexo.toolbox.HasPropertyChangeSupport;

/**
 * Represents a OBP route, as a filtered {@link OBPTrace}
 * 
 * @author sylvain
 * 
 */
public interface OBPRoute extends HasPropertyChangeSupport {

	public OBPTrace getTrace();

	public boolean isVisible(OBPConfiguration config);

	public int getIndex(OBPConfiguration config);

	public int getIndex(OBPConfigurationInRoute configInRoute);

	public OBPConfigurationInRoute getOBPConfigurationInRoute(OBPConfiguration configuration);

	public OBPConfigurationInRoute getPreviousVisibleConfiguration(OBPConfiguration config);

	public OBPConfigurationInRoute getNextVisibleConfiguration(OBPConfiguration config);

	public int getSize();

	public OBPConfigurationInRoute getConfiguration(int index);

	public void show(OBPConfiguration config);

	public void hide(OBPConfiguration config);

	public AbstractTransitionArtefact getAbstractTransition(OBPConfigurationInRoute from, OBPConfigurationInRoute to);

	public interface AbstractTransitionArtefact {

		public OBPConfigurationInRoute getStartConfiguration();

		public OBPConfigurationInRoute getEndConfiguration();

	}

}
