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

import java.util.List;

/**
 * Represents a OBP trace, as a chained list of OBPConfiguration, from a given configuration to another configuration
 * 
 * @author sylvain
 * 
 */
public interface OBPTrace {

	public OBPConfiguration getInitConfiguration();

	public OBPConfiguration getLastConfiguration();

	public OBPConfiguration getNextConfiguration(OBPConfiguration config);

	public OBPConfiguration getPreviousConfiguration(OBPConfiguration config);

	public OBPTransition getTransition(OBPConfiguration from, OBPConfiguration to);

	public int getIndex(OBPConfiguration config);

	public int getSize();

	public OBPConfiguration getConfiguration(int index);

	public List<Component> getComponents();
}