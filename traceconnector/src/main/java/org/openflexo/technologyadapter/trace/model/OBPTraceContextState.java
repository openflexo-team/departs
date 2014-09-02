/*
 * (c) Copyright 2013 Openflexo
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

package org.openflexo.technologyadapter.trace.model;

import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;

@ModelEntity
@ImplementationClass(OBPTraceContextState.OBPTraceContextStateImpl.class)
@XMLElement(xmlTag = "OBPTraceContextState")
public interface OBPTraceContextState extends OBPTraceBehaviourObjectState{

	public static final String CONTEXT_KEY = "context";
	
	@Getter(value=CONTEXT_KEY, ignoreType=true)
	public Parser.Context getTraceOBPContext();
	
	@Setter(CONTEXT_KEY)
	public void setTraceOBPContext(Parser.Context context);

	public static abstract class OBPTraceContextStateImpl extends OBPTraceBehaviourObjectStateImpl implements OBPTraceContextState {

		public OBPTraceContextStateImpl() {
			// TODO Auto-generated constructor stub
		}

	}

}