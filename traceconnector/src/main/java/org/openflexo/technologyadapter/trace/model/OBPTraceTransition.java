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

import java.util.List;

import org.openflexo.model.annotations.Adder;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.Remover;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.model.annotations.Getter.Cardinality;

import Parser.Transition;

@ModelEntity
@ImplementationClass(OBPTraceTransition.OBPTraceTransitionImpl.class)
@XMLElement(xmlTag = "OBPTraceTransition")
public interface OBPTraceTransition extends OBPTraceObject{

	public static final String TRANSITION_KEY = "Transition";
	
	public static final String SOURCE_CONFIG_KEY = "SourceConfig";
	
	public static final String TARGET_CONFIG_KEY = "TargetConfig";
	
	public static final String MESSAGES_KEY = "messages";
	
	@Getter(value=TRANSITION_KEY, ignoreType=true)
	public Transition getTransition();
	@Setter(TRANSITION_KEY)
	public void setTransition(Transition transition);
	
	@Getter(value=SOURCE_CONFIG_KEY)
	public OBPTraceConfiguration getSourceOBPTraceConfiguration();
	@Setter(SOURCE_CONFIG_KEY)
	public void setSourceOBPTraceConfiguration(OBPTraceConfiguration configuration);
	
	@Getter(value=TARGET_CONFIG_KEY)
	public OBPTraceConfiguration getTargetOBPTraceConfiguration();
	@Setter(TARGET_CONFIG_KEY)
	public void setTargetOBPTraceConfiguration(OBPTraceConfiguration configuration);
	
	@Getter(value = MESSAGES_KEY, cardinality = Cardinality.LIST, inverse = OBPTraceMessage.TRANSITION_KEY)
	public List<OBPTraceMessage> getOBPTraceMessages();
	@Setter(MESSAGES_KEY)
	public void setOBPTraceMessages(List<OBPTraceMessage> messages);
	@Adder(MESSAGES_KEY)
	public void addToOBPTraceMessages(OBPTraceMessage message);
	@Remover(MESSAGES_KEY)
	public void removeFromOBPTraceMessages(OBPTraceMessage message);

	public static abstract class OBPTraceTransitionImpl extends OBPTraceObjectImpl implements OBPTraceTransition {

		public OBPTraceTransitionImpl() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getUri() {
			return getName();
		}

	}

}