package org.openflexo.technologyadapter.cdl.virtualmodel.action;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.openflexo.foundation.view.action.FlexoBehaviourAction;
import org.openflexo.foundation.viewpoint.annotations.FIBPanel;
import org.openflexo.foundation.viewpoint.editionaction.FetchRequest;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.cdl.CDLModelSlot;
import org.openflexo.technologyadapter.cdl.model.CDLActivity;
import org.openflexo.technologyadapter.cdl.model.CDLObserver;
import org.openflexo.technologyadapter.cdl.model.CDLParActivity;
import org.openflexo.technologyadapter.cdl.model.CDLProperty;
import org.openflexo.technologyadapter.cdl.model.CDLUnit;

@FIBPanel("Fib/SelectCDLPropertiesPanel.fib")
@ModelEntity
@ImplementationClass(SelectCDLProperties.SelectCDLPropertiesImpl.class)
@XMLElement
public interface SelectCDLProperties extends FetchRequest<CDLModelSlot, CDLProperty> {

	public static abstract class SelectCDLPropertiesImpl extends FetchRequestImpl<CDLModelSlot, CDLProperty> implements SelectCDLProperties {

		private static final Logger logger = Logger.getLogger(SelectCDLProperties.class.getPackage().getName());

		public SelectCDLPropertiesImpl() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public Type getFetchedType() {
			return CDLProperty.class;
		}

		@Override
		public List<CDLProperty> performAction(FlexoBehaviourAction action) {

			if (getModelSlotInstance(action) == null) {
				logger.warning("Could not access model slot instance. Abort.");
				return null;
			}
			if (getModelSlotInstance(action).getResourceData() == null) {
				logger.warning("Could not access model adressed by model slot instance. Abort.");
				return null;
			}

			CDLUnit cdlUnit = (CDLUnit) getModelSlotInstance(action).getAccessedResourceData();

			return filterWithConditions(cdlUnit.getCDLProperties(), action);
		}
	}
}
