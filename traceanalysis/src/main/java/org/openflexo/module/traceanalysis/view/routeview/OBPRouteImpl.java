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

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openflexo.foundation.DefaultFlexoObject;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance.FederatedElement;
import org.openflexo.module.traceanalysis.model.mask.Mask;
import org.openflexo.module.traceanalysis.model.mask.MaskedConfiguration;
import org.openflexo.module.traceanalysis.model.mask.MaskedTransition;
import org.openflexo.technologyadapter.trace.model.OBPTrace;
import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObjectInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceConfiguration;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessage;
import org.openflexo.technologyadapter.trace.model.OBPTraceObject;
import org.openflexo.technologyadapter.trace.model.OBPTraceTransition;

public class OBPRouteImpl extends DefaultFlexoObject implements OBPRoute {

	private Map<OBPTraceConfiguration, OBPConfigurationInRoute> configMap;
	private List<OBPConfigurationInRoute> visibleConfigurations;

	private Map<OBPTraceBehaviourObjectInstance, BehaviourObjectInRoute> behaviourObjectsMap;
	private List<BehaviourObjectInRoute> visibleBehaviourObjects;

	private Map<OBPTraceMessage, MessageInRoute> messageMap;
	
	private List<OBPStateInRoute> visibleStates;
	private List<OBPStateInRoute> allStates;

	private PropertyChangeSupport pcSupport;
	
	private TraceVirtualModelInstance traceVirtualModelInstance;
	private OBPTrace trace;
	
	public OBPRouteImpl(TraceVirtualModelInstance traceVM) {
		pcSupport = new PropertyChangeSupport(this);
		this.trace = traceVM.getTraceOBP();
		this.traceVirtualModelInstance = traceVM;
		visibleConfigurations = new ArrayList<OBPConfigurationInRoute>();
		configMap = new HashMap<OBPTraceConfiguration, OBPConfigurationInRoute>();
		visibleBehaviourObjects = new ArrayList<BehaviourObjectInRoute>();
		visibleStates = new ArrayList<OBPStateInRoute>();
		allStates = new ArrayList<OBPStateInRoute>();
		behaviourObjectsMap = new HashMap<OBPTraceBehaviourObjectInstance, BehaviourObjectInRoute>();
		messageMap = new HashMap<OBPTraceMessage, MessageInRoute>();
		initializeVisibleElements();
	}

	private void initializeVisibleElements(){
		visibleConfigurations.clear();
		visibleBehaviourObjects.clear();
		visibleStates.clear();
		visibleConfigurations.add(getOBPConfigurationInRoute(trace.getInitConfiguration()));
		visibleConfigurations.add(getOBPConfigurationInRoute(trace.getLastConfiguration()));
		for (OBPTraceBehaviourObjectInstance c : trace.getOBPTraceBehaviourObjectInstances()) {
			visibleBehaviourObjects.add(getBehaviourObjectInRoute(c));
		}
		for(BehaviourObjectInRoute behaviourObject : visibleBehaviourObjects){
			for(OBPConfigurationInRoute configuration : visibleConfigurations){
				visibleStates.add(getOBPStateInRoute(behaviourObject, configuration));
			}
		}
	}
	
	@Override
	public PropertyChangeSupport getPropertyChangeSupport() {
		return pcSupport;
	}

	@Override
	public OBPTrace getTrace() {
		return trace;
	}

	@Override
	public OBPConfigurationInRoute getOBPConfigurationInRoute(OBPTraceConfiguration configuration) {
		OBPConfigurationInRoute returned = configMap.get(configuration);
		if (returned == null) {
			returned = new OBPConfigurationInRouteImpl(configuration, this);
			configMap.put(configuration, returned);
		}
		return returned;
	}

	@Override
	public boolean isVisible(OBPTraceConfiguration config) {
		return visibleConfigurations.contains(getOBPConfigurationInRoute(config));
	}

	@Override
	public int getIndex(OBPTraceConfiguration config) {
		return visibleConfigurations.indexOf(getOBPConfigurationInRoute(config));
	}

	@Override
	public int getIndex(OBPConfigurationInRoute configInRoute) {
		return visibleConfigurations.indexOf(configInRoute);
	}
	
	public int getIndex(BehaviourObjectInRoute behaviourInRoute){
		return visibleBehaviourObjects.indexOf(behaviourInRoute);
	}

	@Override
	public OBPConfigurationInRoute getPreviousVisibleConfiguration(OBPTraceConfiguration config) {
		int i = getIndex(config);
		if (i == -1) {
			// This config is not visible
			OBPTraceConfiguration pConfig = getTrace().getPreviousConfiguration(config);
			if (pConfig != null) {
				if (getIndex(pConfig) > -1) {
					return getOBPConfigurationInRoute(pConfig);
				}
				return getPreviousVisibleConfiguration(pConfig);
			}
			return null;
		}
		if (i > 0) {
			return visibleConfigurations.get(i - 1);
		}
		return null;
	}

	@Override
	public OBPConfigurationInRoute getNextVisibleConfiguration(OBPTraceConfiguration config) {
		int i = getIndex(config);
		if (i == -1) {
			// This config is not visible
			OBPTraceConfiguration nConfig = getTrace().getNextConfiguration(config);
			if (nConfig != null) {
				if (getIndex(nConfig) > -1) {
					return getOBPConfigurationInRoute(nConfig);
				}
				return getNextVisibleConfiguration(nConfig);
			}
			return null;
		}
		if (i > -1 && i + 1 < visibleConfigurations.size()) {
			return visibleConfigurations.get(i + 1);
		}
		return null;
	}

	@Override
	public void show(OBPTraceConfiguration config) {
		System.out.println("show " + config);
		// System.out.println("visibleConfigurations=" + visibleConfigurations);

		OBPConfigurationInRoute previousVisible = getPreviousVisibleConfiguration(config);

		int oldSize = getSize();

		// System.out.println("previous visible = " + previousVisible);
		// System.out.println("index = " + getIndex(previousVisible));

		visibleConfigurations.add(getIndex(previousVisible) + 1, getOBPConfigurationInRoute(config));

		// System.out.println("NOW visibleConfigurations=" + visibleConfigurations);

		getPropertyChangeSupport().firePropertyChange("visibleConfigurations", null, getOBPConfigurationInRoute(config));

		for (OBPConfigurationInRoute c : visibleConfigurations) {
			c.getPropertyChangeSupport().firePropertyChange("visibleIndex", null, c.getVisibleIndex());
		}

		getPropertyChangeSupport().firePropertyChange("size", oldSize, getSize());

	}

	@Override
	public void hide(OBPTraceConfiguration config) {
		if (config == getTrace().getInitConfiguration() || config == getTrace().getLastConfiguration()) {
			return;
		}

		int oldSize = getSize();

		System.out.println("hide " + config);
		visibleConfigurations.remove(getOBPConfigurationInRoute(config));

		getPropertyChangeSupport().firePropertyChange("visibleConfigurations", null, getOBPConfigurationInRoute(config));

		for (OBPConfigurationInRoute c : visibleConfigurations) {
			c.getPropertyChangeSupport().firePropertyChange("visibleIndex", null, c.getVisibleIndex());
		}

		getPropertyChangeSupport().firePropertyChange("size", oldSize, getSize());

	}

	@Override
	public void showBehaviourObject(OBPTraceBehaviourObjectInstance behaviourObject) {
		BehaviourObjectInRoute oldValue = getBehaviourObjectInRoute(behaviourObject);
		visibleBehaviourObjects.add(getBehaviourObjectInRoute(behaviourObject));

		//getPropertyChangeSupport().firePropertyChange("visibleBehaviourObjects", null, getBehaviourObjectInRoute(behaviourObject));

		for (BehaviourObjectInRoute c : visibleBehaviourObjects) {
			c.getPropertyChangeSupport().firePropertyChange("index", null, c.getIndex());
		}

	}

	@Override
	public void hideBehaviourObject(OBPTraceBehaviourObjectInstance behaviourObject) {
		BehaviourObjectInRoute oldValue = getBehaviourObjectInRoute(behaviourObject);
		visibleBehaviourObjects.remove(getBehaviourObjectInRoute(behaviourObject));

		//getPropertyChangeSupport().firePropertyChange("visibleBehaviourObjects", oldValue, null);

		for (BehaviourObjectInRoute c : visibleBehaviourObjects) {
			c.getPropertyChangeSupport().firePropertyChange("index", null, c.getIndex());
		}

	}

	
	@Override
	public int getSize() {
		return visibleConfigurations.size();
	}

	@Override
	public OBPConfigurationInRoute getConfiguration(int index) {
		return visibleConfigurations.get(index);
	}

	public class AbstractTransitionArtefactImpl implements AbstractTransitionArtefact {

		private final OBPConfigurationInRoute startConfigInRoute;
		private final OBPConfigurationInRoute endConfigInRoute;

		public AbstractTransitionArtefactImpl(OBPConfigurationInRoute startConfigInRoute, OBPConfigurationInRoute endConfigInRoute) {
			super();
			this.startConfigInRoute = startConfigInRoute;
			this.endConfigInRoute = endConfigInRoute;
		}

		@Override
		public OBPConfigurationInRoute getStartConfiguration() {
			return startConfigInRoute;
		}

		@Override
		public OBPConfigurationInRoute getEndConfiguration() {
			return endConfigInRoute;
		}

	}

	private final Map<OBPConfigurationInRoute, Map<OBPConfigurationInRoute, AbstractTransitionArtefact>> abstractTransitions = new HashMap<OBPConfigurationInRoute, Map<OBPConfigurationInRoute, AbstractTransitionArtefact>>();

	@Override
	public AbstractTransitionArtefact getAbstractTransition(OBPConfigurationInRoute from, OBPConfigurationInRoute to) {
		Map<OBPConfigurationInRoute, AbstractTransitionArtefact> map = abstractTransitions.get(from);
		if (map == null) {
			map = new HashMap<OBPConfigurationInRoute, AbstractTransitionArtefact>();
			abstractTransitions.put(from, map);
		}
		AbstractTransitionArtefact returned = map.get(to);
		if (returned == null) {
			returned = new AbstractTransitionArtefactImpl(from, to);
			map.put(to, returned);
		}
		return returned;
	}

	@Override
	public List<BehaviourObjectInRoute> getVisibleBehaviourObjects() {
		return visibleBehaviourObjects;
	}

	@Override
	public BehaviourObjectInRoute getBehaviourObjectInRoute(OBPTraceBehaviourObjectInstance behaviourObject) {
		BehaviourObjectInRoute returned = behaviourObjectsMap.get(behaviourObject);
		if (returned == null) {
			returned = new BehaviourObjectInRoute(behaviourObject, this);
			behaviourObjectsMap.put(behaviourObject, returned);
		}
		return returned;
	}

	@Override
	public MessageInRoute getMessageInRoute(OBPTraceMessage message) {
		MessageInRoute returned = messageMap.get(message);
		if (returned == null) {
			returned = new MessageInRoute(message, this);
			messageMap.put(message, returned);
		}
		return returned;
	}
	
	@Override
	public List<OBPConfigurationInRoute> getAsyncMessageConfigurations(OBPTraceMessage sendMessage, OBPTraceMessage receiveMessage){
		List<OBPConfigurationInRoute> returned = new ArrayList<OBPConfigurationInRoute>();
		OBPConfigurationInRoute send = getOBPConfigurationInRoute(sendMessage.getOBPTraceTransition().getSourceOBPTraceConfiguration());
		OBPConfigurationInRoute receive = getOBPConfigurationInRoute(receiveMessage.getOBPTraceTransition().getTargetOBPTraceConfiguration());
		addNextConfiguration(send, receive, returned);
		return returned;
	}
	
	private void addNextConfiguration(OBPConfigurationInRoute send, OBPConfigurationInRoute receive, List<OBPConfigurationInRoute> configs){
		if(!send.getConfiguration().equals(receive.getConfiguration())){
			configs.add(receive);
			addNextConfiguration(getOBPConfigurationInRoute(send.getNextConfigurationArtefact().getConfiguration()), receive, configs);
		}
	}

	@Override
	public OBPStateInRoute getOBPStateInRoute(BehaviourObjectInRoute behaviourObjectInRoute,
			OBPConfigurationInRoute configuration) {
		for(OBPStateInRoute state : allStates){
			if(state.getBehaviourObjectInRoute().equals(behaviourObjectInRoute)&&
					state.getConfigurationInRoute().equals(configuration)){
				return state;
			}
		}
		
		OBPStateInRoute returned = new OBPStateInRoute(configuration, behaviourObjectInRoute);
		allStates.add(returned);

		return returned;
	}

	public void synchronizeWithMask(){
		Mask mask = (Mask) traceVirtualModelInstance.getSelectedMask();
		if(mask!=null){
			List<OBPTraceBehaviourObjectInstance> objectsToHide = new ArrayList<OBPTraceBehaviourObjectInstance>();
			for(Object object : mask.getBehaviourObjects()){
				OBPTraceBehaviourObjectInstance visibleObject = (OBPTraceBehaviourObjectInstance)object;
				if(!visibleBehaviourObjects.contains(getBehaviourObjectInRoute(visibleObject))){
					showBehaviourObject(visibleObject);
				}
			}
			for(BehaviourObjectInRoute object : visibleBehaviourObjects ){
				if(!mask.getBehaviourObjects().contains(object.getBehaviourObject())){
					objectsToHide.add(object.getBehaviourObject());
				}
			}
			for(OBPTraceBehaviourObjectInstance objectToHide :objectsToHide ){
				hideBehaviourObject(objectToHide);
			}
		}	
	}
	
	@Override
	public TraceVirtualModelInstance getTraceVirtualModelInstance() {
		// TODO Auto-generated method stub
		return traceVirtualModelInstance;
	}
	
	
	public MaskedConfiguration getMaskedElement(OBPTraceConfiguration object){
		return (MaskedConfiguration) getTraceVirtualModelInstance().getMaskedElement(object);
	}
	
	public MaskedTransition getMaskedElement(OBPTraceTransition object){
		return (MaskedTransition) getTraceVirtualModelInstance().getMaskedElement(object);
	}
	
}
