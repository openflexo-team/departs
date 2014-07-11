package org.openflexo.technologyadapter.cdl.model.io;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import obp.cdl.Activity;
import obp.cdl.ActivityDeclaration;
import obp.cdl.ActivityReference;
import obp.cdl.AltActivity;
import obp.cdl.CDLDeclaration;
import obp.cdl.Declaration;
import obp.cdl.EventDeclaration;
import obp.cdl.EventReference;
import obp.cdl.ParActivity;
import obp.cdl.PropertyDeclaration;
import obp.cdl.SeqActivity;
import obp.cdl.TopActivity;
import obp.event.CommunicationOp;
import obp.event.Event;
import obp.event.GammaEvent;
import obp.event.Informal;
import obp.event.Input;
import obp.event.Output;
import obp.event.PredicateEvent;
import obp.event.Synchronous;
import obp.obs.Observer;
import obp.obs.State;
import obp.obs.Transition;
import obp.property.Property;
import obp.property.PropertyAbsence;
import obp.property.PropertyExistence;
import obp.property.PropertyObserver;
import obp.property.PropertyPrecedence;
import obp.property.PropertyResponse;

import org.openflexo.foundation.technologyadapter.TechnologyAdapter;
import org.openflexo.model.ModelContext;
import org.openflexo.model.ModelContextLibrary;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.model.factory.ModelFactory;
import org.openflexo.technologyadapter.cdl.CDLTechnologyAdapter;
import org.openflexo.technologyadapter.cdl.model.CDLActivity;
import org.openflexo.technologyadapter.cdl.model.CDLActivityReference;
import org.openflexo.technologyadapter.cdl.model.CDLAltActivity;
import org.openflexo.technologyadapter.cdl.model.CDLCommunicationOPEvent;
import org.openflexo.technologyadapter.cdl.model.CDLEvent;
import org.openflexo.technologyadapter.cdl.model.CDLEventReference;
import org.openflexo.technologyadapter.cdl.model.CDLGammaEvent;
import org.openflexo.technologyadapter.cdl.model.CDLInformalEvent;
import org.openflexo.technologyadapter.cdl.model.CDLObject;
import org.openflexo.technologyadapter.cdl.model.CDLObserver;
import org.openflexo.technologyadapter.cdl.model.CDLObserverState;
import org.openflexo.technologyadapter.cdl.model.CDLObserverStateCut;
import org.openflexo.technologyadapter.cdl.model.CDLObserverStateNormal;
import org.openflexo.technologyadapter.cdl.model.CDLObserverStateReject;
import org.openflexo.technologyadapter.cdl.model.CDLObserverStateSuccess;
import org.openflexo.technologyadapter.cdl.model.CDLObserverTransition;
import org.openflexo.technologyadapter.cdl.model.CDLParActivity;
import org.openflexo.technologyadapter.cdl.model.CDLPredicateEvent;
import org.openflexo.technologyadapter.cdl.model.CDLProcessID;
import org.openflexo.technologyadapter.cdl.model.CDLProperty;
import org.openflexo.technologyadapter.cdl.model.CDLPropertyAbsence;
import org.openflexo.technologyadapter.cdl.model.CDLPropertyExistence;
import org.openflexo.technologyadapter.cdl.model.CDLPropertyPattern;
import org.openflexo.technologyadapter.cdl.model.CDLPropertyPrecedence;
import org.openflexo.technologyadapter.cdl.model.CDLPropertyResponse;
import org.openflexo.technologyadapter.cdl.model.CDLSeqActivity;
import org.openflexo.technologyadapter.cdl.model.CDLTopActivity;
import org.openflexo.technologyadapter.cdl.model.CDLUnit;
import org.openflexo.technologyadapter.cdl.model.CDLCommunicationOPEvent.EventKind;

public class CDLModelConverter {

	private static final Logger logger = Logger.getLogger(CDLModelConverter.class.getPackage().getName());

	protected final Map<Object, CDLObject> cdlObjects = new HashMap<Object, CDLObject>();

	private ModelFactory factory;
	private ModelContext modelContext;
	private CDLTechnologyAdapter technologyAdapter;

	/**
	 * Constructor.
	 */
	public CDLModelConverter() {
		try {
			factory = new ModelFactory(ModelContextLibrary.getCompoundModelContext(
					CDLProcessID.class, 
					CDLObject.class, 
					CDLUnit.class,
					CDLAltActivity.class,
					CDLActivityReference.class,
					CDLEventReference.class,
					CDLParActivity.class,
					CDLPredicateEvent.class,
					CDLSeqActivity.class,
					CDLGammaEvent.class,
					CDLInformalEvent.class,
					CDLProperty.class,
					CDLPropertyAbsence.class,
					CDLObserver.class,
					CDLProperty.class,
					CDLPropertyResponse.class,
					CDLPropertyExistence.class,
					CDLObserverState.class,
					CDLObserverStateNormal.class,
					CDLObserverStateReject.class,
					CDLObserverStateSuccess.class,
					CDLObserverStateCut.class,
					CDLObserverTransition.class,
					CDLPropertyResponse.class,
					CDLPropertyPattern.class,
					CDLEvent.class,
					CDLCommunicationOPEvent.class));
			modelContext = new ModelContext(CDLActivity.class);
		} catch (ModelDefinitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public CDLTechnologyAdapter getTechnologyAdapter() {
		return technologyAdapter;
	}

	public void setTechnologyAdapter(CDLTechnologyAdapter technologyAdapter) {
		this.technologyAdapter = technologyAdapter;
	}
	
	public CDLUnit convertCDLUnit(obp.cdl.CDLUnit cdlUnit) {

		CDLUnit flexoCDLUnit = factory.newInstance(CDLUnit.class);
		flexoCDLUnit.setTechnologyAdapter(technologyAdapter);
		cdlObjects.put(cdlUnit, flexoCDLUnit);
		System.out.println("Create "+flexoCDLUnit.getName());
		CDLDeclaration cdlDeclaration = null;
		for (Declaration declaration : cdlUnit.getDeclarationList()) {
			if (declaration instanceof obp.cdl.EventDeclaration) {
				convertCDLEvent((EventDeclaration) declaration, flexoCDLUnit);
			}
		}
		for (Declaration declaration : cdlUnit.getDeclarationList()) {
			if (declaration instanceof obp.cdl.ActivityDeclaration) {
				CDLActivity activity = convertCDLActivity(((ActivityDeclaration) declaration).getIs(), flexoCDLUnit);
				activity.setName(declaration.getName());
			}
		}
		for (Declaration declaration : cdlUnit.getDeclarationList()) {
			if (declaration instanceof obp.cdl.PropertyDeclaration) {
				CDLProperty cdlProperty = convertCDLProperty(((PropertyDeclaration) declaration).getIs(), flexoCDLUnit);
				cdlProperty.setName((((PropertyDeclaration) declaration)).getName());
			}
			if (declaration instanceof obp.cdl.CDLDeclaration) {
				cdlDeclaration = (CDLDeclaration) declaration;
			}
		}
		
		
		if(cdlDeclaration!=null){
			if(cdlDeclaration.getInit()!=null){
				flexoCDLUnit.setInitialActivity((CDLTopActivity)cdlObjects.get(cdlDeclaration.getInit()));
			}
			if(cdlDeclaration.getMain()!=null){
				CDLParActivity cdlParActivity = factory.newInstance(CDLParActivity.class);
				cdlParActivity.setCDLParActivity(cdlDeclaration.getMain());
				flexoCDLUnit.setCDLParActivity(cdlParActivity);
				cdlParActivity.setName("main");
				cdlParActivity.setTechnologyAdapter(technologyAdapter);
				flexoCDLUnit.addToCDLActivities(cdlParActivity);
				getCDLObjects().put(cdlDeclaration.getMain(), cdlParActivity);
				System.out.println("Create "+cdlParActivity.getName());
			}
		}
		for (CDLActivity activity : flexoCDLUnit.getCDLActivities()) {
			if (activity instanceof CDLTopActivity) {
				setActivities((TopActivity) getCDLObjectFromFlexoCDLObject(activity), flexoCDLUnit);
			}
		}
		return flexoCDLUnit;
	}
	
	public void setActivities(TopActivity topActivity, CDLUnit cdlUnit){
		CDLTopActivity cdlTopActivity = (CDLTopActivity) cdlObjects.get(topActivity);
		if(topActivity!=null && topActivity.getActivityList()!=null){
			for(Activity activity : topActivity.getActivityList()){
				if(cdlObjects.get(activity)==null){
					if(activity instanceof EventReference){
						EventReference eventReference = (EventReference)activity;
						CDLEventReference cdlActivity = factory.newInstance(CDLEventReference.class);
						CDLEvent cdlEvent = (CDLEvent) cdlObjects.get(eventReference.getReference().getIs());
						if(cdlEvent==null){
							cdlEvent = convertCDLEvent(eventReference.getReference(), cdlUnit);
						}
						cdlActivity.setReferencedEvent(cdlEvent);
						cdlActivity.setCDLEventReference(eventReference);
						cdlActivity.setName(eventReference.getName());
						cdlActivity.setTechnologyAdapter(technologyAdapter);
						cdlObjects.put(eventReference, cdlActivity);
					}
					if(activity instanceof ActivityReference){
						ActivityReference activityReference = (ActivityReference)activity;
						CDLActivityReference cdlActivityReference = factory.newInstance(CDLActivityReference.class);
						cdlActivityReference.setCDLActivityReference(activityReference);
						cdlActivityReference.setReferencedActivity((CDLActivity) cdlObjects.get(activityReference.getReference().getIs()));
						cdlActivityReference.setName(activityReference.getName());
						cdlActivityReference.setTechnologyAdapter(technologyAdapter);
						cdlObjects.put(activityReference, cdlActivityReference);
					}
				}
				
				if(activity instanceof TopActivity){
					setActivities((TopActivity) activity,cdlUnit);
				}
				CDLActivity cdlActivity = (CDLActivity) cdlObjects.get(activity);
				if(cdlActivity!=null && cdlTopActivity!=null){
					cdlTopActivity.addToCDLActivities(cdlActivity);
				}
			}
		}
		
	}

	public CDLEvent convertCDLEvent(EventDeclaration event, CDLUnit cdlUnit) {
		CDLEvent cdlEvent = null;
		if (!getCDLObjects().containsKey(event)) {
			if(event.getIs() instanceof CommunicationOp){
				cdlEvent = factory.newInstance(CDLCommunicationOPEvent.class);
				CDLCommunicationOPEvent cdlCommunicationOp = (CDLCommunicationOPEvent) cdlEvent;
				cdlCommunicationOp.setCommunicationOp((CommunicationOp) event.getIs());
				
				if(((CommunicationOp)event.getIs()).getFrom()!=null){
					CDLProcessID from = factory.newInstance(CDLProcessID.class);
					from.setTechnologyAdapter(technologyAdapter);
					from.setCDLProcessID(((CommunicationOp)event.getIs()).getFrom());
					from.setName(((CommunicationOp)event.getIs()).getFrom().getName());
					cdlCommunicationOp.setProcessIDFrom(from);
				}
				if(((CommunicationOp)event.getIs()).getTo()!=null){
					CDLProcessID to = factory.newInstance(CDLProcessID.class);
					to.setTechnologyAdapter(technologyAdapter);
					to.setCDLProcessID(((CommunicationOp)event.getIs()).getTo());
					to.setName(((CommunicationOp)event.getIs()).getTo().getName());
					cdlCommunicationOp.setProcessIDTo(to);
				}
				if(event.getIs() instanceof Input){
					cdlCommunicationOp.setEventKind(EventKind.INPUT);
				} else if(event.getIs() instanceof Output){
					cdlCommunicationOp.setEventKind(EventKind.OUTPUT);
				} else if(event.getIs() instanceof Synchronous){
					cdlCommunicationOp.setEventKind(EventKind.SYNC);
				}
			}else if(event.getIs() instanceof Informal){
				cdlEvent = factory.newInstance(CDLInformalEvent.class);
				CDLInformalEvent cdlInformalEvent = (CDLInformalEvent) cdlEvent;
				cdlInformalEvent.setInformalEvent((Informal) event.getIs());
				CDLProcessID from = factory.newInstance(CDLProcessID.class);
				from.setCDLProcessID(((Informal)event.getIs()).getFrom());
				from.setTechnologyAdapter(technologyAdapter);
				cdlInformalEvent.setProcessIDFrom(from);
			}else if(event.getIs() instanceof GammaEvent){
				cdlEvent = factory.newInstance(CDLGammaEvent.class);
				CDLGammaEvent cdlGammaEvent = (CDLGammaEvent) cdlEvent;
				cdlGammaEvent.setGammaEvent((GammaEvent) event.getIs());
			}else if(event.getIs() instanceof PredicateEvent){
				cdlEvent = factory.newInstance(CDLPredicateEvent.class);
				CDLPredicateEvent cdlPredicateEvent = (CDLPredicateEvent) cdlEvent;
				cdlPredicateEvent.setPredicateEvent((PredicateEvent) event.getIs());
			}
			cdlEvent.setName(event.getName());
			cdlEvent.setTechnologyAdapter(technologyAdapter);
			getCDLObjects().put(event.getIs(), cdlEvent);
			System.out.println("Create "+event.getIs().getName());
		} else {
			cdlEvent = (CDLEvent) getCDLObjects().get(event);
		}
		cdlUnit.addToCDLEvents(cdlEvent);
		return cdlEvent;
	}

	public CDLActivity convertCDLActivity(Activity activity, CDLUnit cdlUnit) {
		CDLActivity cdlActivity = null;
		if (!getCDLObjects().containsKey(activity)) {
			if(activity instanceof ActivityReference){
				cdlActivity = convertActivityReference((ActivityReference) activity, cdlUnit);
			}else if(activity instanceof ParActivity){
				cdlActivity = convertParActivity((ParActivity) activity, cdlUnit);
			}else if(activity instanceof SeqActivity){
				cdlActivity = convertSeqActivity((SeqActivity) activity, cdlUnit);
			}else if(activity instanceof AltActivity){
				cdlActivity = convertAltActivity((AltActivity) activity, cdlUnit);
			}else if(activity instanceof EventReference){
				cdlActivity = convertEventReference((EventReference) activity, cdlUnit);
			}
			cdlObjects.put(activity, cdlActivity);
		} else {
			cdlActivity = (CDLActivity) getCDLObjects().get(activity);
		}
		cdlUnit.addToCDLActivities(cdlActivity);
		return cdlActivity;
	}
	
	public CDLActivityReference convertActivityReference(ActivityReference activityReference, CDLUnit cdlUnit){
		CDLActivityReference cdlActivityReference = factory.newInstance(CDLActivityReference.class);
		cdlActivityReference.setCDLActivityReference(activityReference);
		cdlActivityReference.setTechnologyAdapter(technologyAdapter);
		cdlObjects.put(activityReference, cdlActivityReference);
		return cdlActivityReference;
	}
	
	public CDLParActivity convertParActivity(ParActivity parActivity, CDLUnit cdlUnit){
		CDLParActivity cdlParActivity = factory.newInstance(CDLParActivity.class);
		cdlParActivity.setCDLParActivity(parActivity);
		cdlParActivity.setTechnologyAdapter(technologyAdapter);
		cdlObjects.put(parActivity, cdlParActivity);
		return cdlParActivity;
	}
	
	public CDLSeqActivity convertSeqActivity(SeqActivity seqActivity, CDLUnit cdlUnit){
		CDLSeqActivity cdlSeqActivity = factory.newInstance(CDLSeqActivity.class);
		cdlSeqActivity.setSeqActivity(seqActivity);
		cdlSeqActivity.setTechnologyAdapter(technologyAdapter);
		cdlObjects.put(seqActivity, cdlSeqActivity);
		return cdlSeqActivity;
	}
	
	public CDLAltActivity convertAltActivity(AltActivity altActivity, CDLUnit cdlUnit){
		CDLAltActivity cdlAltActivity = factory.newInstance(CDLAltActivity.class);
		cdlAltActivity.setCDLAltActivity(altActivity);
		cdlAltActivity.setTechnologyAdapter(technologyAdapter);
		cdlObjects.put(altActivity, cdlAltActivity);
		return cdlAltActivity;
	}
	
	public CDLEventReference convertEventReference(EventReference eventReference, CDLUnit cdlUnit){
		CDLEventReference cdlEventReference = factory.newInstance(CDLEventReference.class);
		cdlEventReference.setReferencedEvent((CDLEvent) cdlObjects.get(eventReference.getReference().getIs()));
		cdlEventReference.setCDLEventReference(eventReference);
		cdlEventReference.setTechnologyAdapter(technologyAdapter);
		cdlObjects.put(eventReference, cdlEventReference);
		return cdlEventReference;
	}

	public CDLProperty convertCDLProperty(Property property, CDLUnit cdlUnit) {
		CDLProperty cdlProperty = null;
		if (!getCDLObjects().containsKey(property)) {
			if(property instanceof PropertyResponse){
				cdlProperty = convertPropertyResponse((PropertyResponse) property, cdlUnit);
			}else if(property instanceof PropertyExistence){
				cdlProperty = convertPropertyExistence((PropertyExistence) property, cdlUnit);
			}else if(property instanceof PropertyPrecedence){
				cdlProperty = convertPropertyPrecedence((PropertyPrecedence) property, cdlUnit);
			}else if(property instanceof PropertyAbsence){
				cdlProperty = convertPropertyAbsence((PropertyAbsence) property, cdlUnit);
			}else if(property instanceof PropertyObserver){
				cdlProperty = convertPropertyObserver((PropertyObserver) property, cdlUnit);
			}
		} else {
			cdlProperty = (CDLProperty) getCDLObjects().get(property);
		}
		return cdlProperty;
	}
	
	public CDLObserver convertPropertyObserver(PropertyObserver property, CDLUnit cdlUnit){
		CDLObserver cdlProperty = factory.newInstance(CDLObserver.class);
		Observer observer = property.getObserver();
		for(State state:observer.getStateList()){
			cdlProperty.addToCDLObserverStates(convertState(state, cdlUnit));
		}
		cdlProperty.setStartState((CDLObserverState) cdlObjects.get(observer.getStartState()));
		for(Transition transition:observer.getTransitionList()){
			convertTransition(transition, cdlUnit);
		}	
		cdlProperty.setTechnologyAdapter(technologyAdapter);
		getCDLObjects().put(property, cdlProperty);
		cdlUnit.addToCDLProperties(cdlProperty);
		return cdlProperty;
	}
	
	public CDLObserverTransition convertTransition(Transition transition,CDLUnit cdlUnit){
		CDLObserverTransition cdlTransition = factory.newInstance(CDLObserverTransition.class);
		cdlTransition.setTechnologyAdapter(technologyAdapter);
		if(transition.getEvent()!=null){
			CDLEventReference eventReference = convertEventReference(transition.getEvent(),cdlUnit);
			cdlTransition.setCDLEventReference(eventReference);
		}
		CDLObserverState fromState = (CDLObserverState) cdlObjects.get(transition.getSource());
		CDLObserverState toState = (CDLObserverState) cdlObjects.get(transition.getTarget());
		cdlTransition.setCDLFromObserverState(fromState);
		cdlTransition.setCDLToObserverState(toState);
		fromState.addToCDLObserverOutTransitions(cdlTransition);
		toState.addToCDLObserverInTransitions(cdlTransition);
		
		getCDLObjects().put(transition, cdlTransition);
		return cdlTransition;
	}
	
	public CDLObserverState convertState(State state,CDLUnit cdlUnit){
		CDLObserverState cdlState = null;
		switch (state.getType().getValue()){
			case 0: cdlState = factory.newInstance(CDLObserverStateNormal.class);
			break;
			case 1:cdlState = factory.newInstance(CDLObserverStateReject.class);
			break;
			case 2:cdlState = factory.newInstance(CDLObserverStateSuccess.class);
			break;
			case 3:cdlState = factory.newInstance(CDLObserverStateCut.class);
			break;
		}
		
		cdlState.setName(state.getName());
		cdlState.setTechnologyAdapter(technologyAdapter);
		getCDLObjects().put(state, cdlState);
		return cdlState;
	}
	
	public CDLPropertyResponse convertPropertyResponse(PropertyResponse property, CDLUnit cdlUnit){
		CDLPropertyResponse cdlProperty = factory.newInstance(CDLPropertyResponse.class);
		for(EventReference eventReference : ((PropertyResponse) property).getEventList()){
			((CDLPropertyResponse)cdlProperty).addToCDLEventReferences(convertEventReference(eventReference,cdlUnit));
		}
		cdlProperty.setTechnologyAdapter(technologyAdapter);
		getCDLObjects().put(property, cdlProperty);
		cdlUnit.addToCDLProperties(cdlProperty);
		return cdlProperty;
	}
	
	public CDLPropertyAbsence convertPropertyAbsence(PropertyAbsence property, CDLUnit cdlUnit){
		CDLPropertyAbsence cdlProperty = factory.newInstance(CDLPropertyAbsence.class);
		for(EventReference eventReference : ((PropertyAbsence) property).getEventList()){
			((CDLPropertyAbsence)cdlProperty).addToCDLEventReferences(convertEventReference(eventReference,cdlUnit));
		}
		cdlProperty.setTechnologyAdapter(technologyAdapter);
		getCDLObjects().put(property, cdlProperty);
		cdlUnit.addToCDLProperties(cdlProperty);
		return cdlProperty;
	}
	
	public CDLPropertyExistence convertPropertyExistence(PropertyExistence property, CDLUnit cdlUnit){
		CDLPropertyExistence cdlProperty = factory.newInstance(CDLPropertyExistence.class);
		for(EventReference eventReference : ((PropertyExistence) property).getEventList()){
			((CDLPropertyExistence)cdlProperty).addToCDLEventReferences(convertEventReference(eventReference,cdlUnit));
		}
		cdlProperty.setTechnologyAdapter(technologyAdapter);
		getCDLObjects().put(property, cdlProperty);
		cdlUnit.addToCDLProperties(cdlProperty);
		return cdlProperty;
	}
	
	public CDLPropertyPrecedence convertPropertyPrecedence(PropertyPrecedence property, CDLUnit cdlUnit){
		CDLPropertyPrecedence cdlProperty = factory.newInstance(CDLPropertyPrecedence.class);
		for(EventReference eventReference : ((PropertyPrecedence) property).getEventList()){
			((CDLPropertyPrecedence)cdlProperty).addToCDLEventReferences(convertEventReference(eventReference,cdlUnit));
		}
		cdlProperty.setTechnologyAdapter(technologyAdapter);
		getCDLObjects().put(property, cdlProperty);
		return cdlProperty;
	}

	public Map<Object, CDLObject> getCDLObjects() {
		return cdlObjects;
	}

	private Object getCDLObjectFromFlexoCDLObject(CDLObject object){
		for (Entry entry : cdlObjects.entrySet()) {
			Object key = entry.getKey();
		    if(object.equals(cdlObjects.get(key))){
		    	return key;
		    }
		}return null;
	}
	
}
