package org.openflexo.technologyadapter.cdl.model.io;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import obp.cdl.ActivityDeclaration;
import obp.cdl.Declaration;
import obp.cdl.EventDeclaration;
import obp.cdl.PropertyDeclaration;

import org.openflexo.model.ModelContext;
import org.openflexo.model.ModelContextLibrary;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.model.factory.ModelFactory;
import org.openflexo.technologyadapter.cdl.CDLTechnologyAdapter;
import org.openflexo.technologyadapter.cdl.model.CDLActivity;
import org.openflexo.technologyadapter.cdl.model.CDLCommunicationOPEvent;
import org.openflexo.technologyadapter.cdl.model.CDLEvent;
import org.openflexo.technologyadapter.cdl.model.CDLObject;
import org.openflexo.technologyadapter.cdl.model.CDLProcessID;
import org.openflexo.technologyadapter.cdl.model.CDLProperty;
import org.openflexo.technologyadapter.cdl.model.CDLUnit;

public class CDLModelConverter {

	private static final Logger logger = Logger.getLogger(CDLModelConverter.class.getPackage().getName());

	protected final Map<Object, CDLObject> cdlObjects = new HashMap<Object, CDLObject>();

	private ModelFactory factory;
	private ModelContext modelContext;

	/**
	 * Constructor.
	 */
	public CDLModelConverter() {
		try {
			factory = new ModelFactory(ModelContextLibrary.getCompoundModelContext(CDLProcessID.class, CDLObject.class, CDLUnit.class,
					CDLActivity.class, CDLProperty.class, CDLEvent.class, CDLCommunicationOPEvent.class));
			modelContext = new ModelContext(CDLActivity.class);
		} catch (ModelDefinitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public CDLUnit convertCDLUnit(obp.cdl.CDLUnit cdlUnit, CDLTechnologyAdapter technologyAdapter) {

		CDLUnit flexoCDLUnit = factory.newInstance(CDLUnit.class);
		flexoCDLUnit.setTechnologyAdapter(technologyAdapter);
		cdlObjects.put(cdlUnit, flexoCDLUnit);
		for (Declaration declaration : cdlUnit.getDeclarationList()) {
			if (declaration instanceof obp.cdl.EventDeclaration) {
				CDLEvent flexoCDLEvent = convertCDLEvent((EventDeclaration) declaration, flexoCDLUnit, technologyAdapter);
				flexoCDLUnit.addToCDLEvents(flexoCDLEvent);
			}
			if (declaration instanceof obp.cdl.ActivityDeclaration) {
				CDLActivity flexoCDLActivity = convertCDLActivity((ActivityDeclaration) declaration, flexoCDLUnit, technologyAdapter);
				flexoCDLUnit.addToCDLActivities(flexoCDLActivity);
			}
			if (declaration instanceof obp.cdl.PropertyDeclaration) {
				CDLProperty flexoCDLProperty = convertCDLProperty((PropertyDeclaration) declaration, flexoCDLUnit, technologyAdapter);
				flexoCDLUnit.addToCDLProperties(flexoCDLProperty);
			}
		}
		return flexoCDLUnit;
	}

	public CDLEvent convertCDLEvent(EventDeclaration event, CDLUnit cdlUnit, CDLTechnologyAdapter technologyAdapter) {
		CDLEvent cdlEvent = null;
		if (!getCDLObjects().containsKey(event)) {
			cdlEvent = factory.newInstance(CDLEvent.class);
			cdlEvent.setName(event.getName());
			cdlEvent.setTechnologyAdapter(technologyAdapter);
			getCDLObjects().put(event, cdlEvent);
		} else {
			cdlEvent = (CDLEvent) getCDLObjects().get(event);
		}
		return cdlEvent;
	}

	public CDLActivity convertCDLActivity(ActivityDeclaration activity, CDLUnit cdlUnit, CDLTechnologyAdapter technologyAdapter) {
		CDLActivity cdlActivity = null;
		if (!getCDLObjects().containsKey(activity)) {
			cdlActivity = factory.newInstance(CDLActivity.class);
			cdlActivity.setName(activity.getName());
			cdlActivity.setTechnologyAdapter(technologyAdapter);
			getCDLObjects().put(activity, cdlActivity);
		} else {
			cdlActivity = (CDLActivity) getCDLObjects().get(activity);
		}
		return cdlActivity;
	}

	public CDLProperty convertCDLProperty(PropertyDeclaration property, CDLUnit cdlUnit, CDLTechnologyAdapter technologyAdapter) {
		CDLProperty cdlProperty = null;
		if (!getCDLObjects().containsKey(property)) {
			cdlProperty = factory.newInstance(CDLProperty.class);
			cdlProperty.setName(property.getName());
			cdlProperty.setTechnologyAdapter(technologyAdapter);
			getCDLObjects().put(property, cdlProperty);
		} else {
			cdlProperty = (CDLProperty) getCDLObjects().get(property);
		}
		return cdlProperty;
	}

	public Map<Object, CDLObject> getCDLObjects() {
		return cdlObjects;
	}

}
