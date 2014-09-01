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

import java.util.List;

import org.openflexo.foundation.FlexoObject;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.Setter;
import org.openflexo.module.traceanalysis.model.ConfigurationMask;
import org.openflexo.technologyadapter.trace.model.OBPTrace;
import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObject;
import org.openflexo.technologyadapter.trace.model.OBPTraceConfiguration;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessage;
import org.openflexo.toolbox.HasPropertyChangeSupport;

/**
 * Represents a OBP route, as a filtered {@link OBPTrace}
 * 
 * @author sylvain
 * 
 */
public interface OBPRoute extends FlexoObject {

	public OBPTrace getTrace();
	
	public boolean isVisible(OBPTraceConfiguration config);

	public int getIndex(OBPTraceConfiguration config);

	public int getIndex(OBPConfigurationInRoute configInRoute);

	public OBPConfigurationInRoute getOBPConfigurationInRoute(OBPTraceConfiguration configuration);

	public OBPConfigurationInRoute getPreviousVisibleConfiguration(OBPTraceConfiguration config);

	public OBPConfigurationInRoute getNextVisibleConfiguration(OBPTraceConfiguration config);

	public int getSize();

	public OBPConfigurationInRoute getConfiguration(int index);

	public void show(OBPTraceConfiguration config);

	public void hide(OBPTraceConfiguration config);

	public void showBehaviourObject(OBPTraceBehaviourObject behaviourObject);

	public void hideBehaviourObject(OBPTraceBehaviourObject behaviourObject);
	
	public AbstractTransitionArtefact getAbstractTransition(OBPConfigurationInRoute from, OBPConfigurationInRoute to);

	public interface AbstractTransitionArtefact {

		public OBPConfigurationInRoute getStartConfiguration();

		public OBPConfigurationInRoute getEndConfiguration();

	}

	public List<BehaviourObjectInRoute> getVisibleBehaviourObjects();

	public BehaviourObjectInRoute getBehaviourObjectInRoute(OBPTraceBehaviourObject behaviourObject);
	
	public OBPStateInRoute getOBPStateInRoute(BehaviourObjectInRoute behaviourObject, OBPConfigurationInRoute configuration);

	public MessageInRoute getMessageInRoute(OBPTraceMessage message);
	
	public void synchronizeWithMask();
	
	public List<OBPConfigurationInRoute> getAsyncMessageConfigurations(OBPTraceMessage sendMessage, OBPTraceMessage receiveMessage);
	
}
