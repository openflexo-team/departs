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
import obp.event.GammaEvent;
import obp.event.Informal;
import obp.event.Input;
import obp.event.Output;
import obp.event.PredicateEvent;
import obp.event.Synchronous;

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
import org.openflexo.technologyadapter.cdl.model.CDLParActivity;
import org.openflexo.technologyadapter.cdl.model.CDLPredicateEvent;
import org.openflexo.technologyadapter.cdl.model.CDLProcessID;
import org.openflexo.technologyadapter.cdl.model.CDLProperty;
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
			if (declaration instanceof obp.cdl.PropertyDeclaration) {
				convertCDLProperty((PropertyDeclaration) declaration, flexoCDLUnit);
			}
		}
		for (Declaration declaration : cdlUnit.getDeclarationList()) {
			if (declaration instanceof obp.cdl.ActivityDeclaration) {
				CDLActivity activity = convertCDLActivity(((ActivityDeclaration) declaration).getIs(), flexoCDLUnit);
				activity.setName(declaration.getName());
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
				cdlActivity = factory.newInstance(CDLActivityReference.class);
				ActivityReference activityReference = (ActivityReference)activity;
				CDLActivityReference cdlActivityReference = (CDLActivityReference) cdlActivity;
				cdlActivityReference.setCDLActivityReference(activityReference);
			}else if(activity instanceof ParActivity){
				cdlActivity = factory.newInstance(CDLParActivity.class);
				ParActivity parActivity = (ParActivity)activity;
				CDLParActivity cdlParActivity = (CDLParActivity) cdlActivity;
				cdlParActivity.setCDLParActivity(parActivity);
			}else if(activity instanceof SeqActivity){
				cdlActivity = factory.newInstance(CDLSeqActivity.class);
				SeqActivity seqActivity = (SeqActivity)activity;
				CDLSeqActivity cdlSeqActivity = (CDLSeqActivity) cdlActivity;
				cdlSeqActivity.setSeqActivity(seqActivity);
			}else if(activity instanceof AltActivity){
				cdlActivity = factory.newInstance(CDLAltActivity.class);
				AltActivity altActivity = (AltActivity)activity;
				CDLAltActivity cdlAltActivity = (CDLAltActivity) cdlActivity;
				cdlAltActivity.setCDLAltActivity(altActivity);
			}else if(activity instanceof EventReference){
				cdlActivity = factory.newInstance(CDLEventReference.class);
				CDLEventReference cdlEventReference = (CDLEventReference) cdlActivity;
				EventReference activityReference = (EventReference)activity;
				cdlEventReference.setReferencedEvent((CDLEvent) cdlObjects.get(activityReference));
				cdlEventReference.setCDLEventReference(activityReference);
			}
			cdlActivity.setTechnologyAdapter(technologyAdapter);
			cdlObjects.put(activity, cdlActivity);
		} else {
			cdlActivity = (CDLActivity) getCDLObjects().get(activity);
		}
		cdlUnit.addToCDLActivities(cdlActivity);
		return cdlActivity;
	}

	public CDLProperty convertCDLProperty(PropertyDeclaration property, CDLUnit cdlUnit) {
		CDLProperty cdlProperty = null;
		if (!getCDLObjects().containsKey(property)) {
			cdlProperty = factory.newInstance(CDLProperty.class);
			cdlProperty.setName(property.getName());
			cdlProperty.setTechnologyAdapter(technologyAdapter);
			getCDLObjects().put(property, cdlProperty);
		} else {
			cdlProperty = (CDLProperty) getCDLObjects().get(property);
		}
		cdlUnit.addToCDLProperties(cdlProperty);
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
