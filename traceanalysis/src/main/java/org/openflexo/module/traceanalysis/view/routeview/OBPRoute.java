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
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.module.traceanalysis.model.mask.Mask;
import org.openflexo.technologyadapter.trace.model.OBPTrace;
import org.openflexo.technologyadapter.trace.model.OBPTraceConfiguration;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessage;
import org.openflexo.technologyadapter.trace.model.OBPTraceObject;

/**
 * Represents a OBP route, as a filtered {@link OBPTrace}
 * 
 * @author sylvain
 * 
 */
public interface OBPRoute extends FlexoObject {

	public OBPTrace getTrace();
	
	public TraceVirtualModelInstance getTraceVirtualModelInstance();
	
	public boolean isVisible(OBPTraceConfiguration config);
	
	public boolean isVisible(OBPTraceObject config);

	public int getIndex(OBPTraceConfiguration config);

	public int getIndex(OBPConfigurationInRoute configInRoute);
	
	public int getIndex(BehaviourObjectInRoute<?> behaviourInRoute);
	
	public int getVirtualIndex(BehaviourObjectInRoute<?> behaviourInRoute);
	
	public List<OBPConfigurationInRoute> getVisibleConfigurations();

	public OBPConfigurationInRoute getOBPConfigurationInRoute(OBPTraceConfiguration configuration);

	public OBPConfigurationInRoute getPreviousVisibleConfiguration(OBPTraceConfiguration config);

	public OBPConfigurationInRoute getNextVisibleConfiguration(OBPTraceConfiguration config);

	public int getSize();
	
	public void reset();

	public OBPConfigurationInRoute getConfiguration(int index);

	public void show(OBPTraceObject object);

	public void hide(OBPTraceObject object);
	
	public AbstractTransitionArtefact getAbstractTransition(OBPConfigurationInRoute from, OBPConfigurationInRoute to);

	public interface AbstractTransitionArtefact {

		public OBPConfigurationInRoute getStartConfiguration();

		public OBPConfigurationInRoute getEndConfiguration();

	}

	public List<BehaviourObjectInRoute<?>> getVisibleBehaviourObjects();

	public BehaviourObjectInRoute<?> getBehaviourObjectInRoute(OBPTraceObject behaviourObject);
	
	public OBPStateInRoute getOBPStateInRoute(BehaviourObjectInRoute<?> behaviourObject, OBPConfigurationInRoute configuration);

	public MessageInRoute getMessageInRoute(OBPTraceMessage message);
	
	public void synchronizeAllWithMask();
	
	public List<OBPConfigurationInRoute> getAsyncMessageConfigurations(OBPTraceMessage sendMessage, OBPTraceMessage receiveMessage);
	
	public Mask getSelectedMask();
	
	public void addToForcedVisibleConfiguration(OBPConfigurationInRoute object);
	public void removeFromForcedVisibleConfiguration(OBPConfigurationInRoute object);
	public void addToForcedHidedConfiguration(OBPConfigurationInRoute object);
	public void removeFromForcedHidedConfiguration(OBPConfigurationInRoute object);
	
}
