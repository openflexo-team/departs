package org.openflexo.technologyadapter.cdl.virtualmodel.action;

import java.lang.reflect.Type;
import java.util.logging.Logger;

import org.openflexo.foundation.view.FreeModelSlotInstance;
import org.openflexo.foundation.view.action.FlexoBehaviourAction;
import org.openflexo.foundation.viewpoint.annotations.FIBPanel;
import org.openflexo.foundation.viewpoint.editionaction.AssignableAction;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.cdl.CDLModelSlot;
import org.openflexo.technologyadapter.cdl.model.CDLProperty;
import org.openflexo.technologyadapter.cdl.model.CDLUnit;

@FIBPanel("Fib/AddCDLPropertyPanel.fib")
@ModelEntity
@ImplementationClass(AddCDLProperty.AddCDLPropertyImpl.class)
@XMLElement
public interface AddCDLProperty extends AssignableAction<CDLModelSlot, CDLProperty> {

	public static abstract class AddCDLPropertyImpl extends AssignableActionImpl<CDLModelSlot, CDLProperty> implements AddCDLProperty {

		private static final Logger logger = Logger.getLogger(AddCDLProperty.class.getPackage().getName());

		public AddCDLPropertyImpl() {
			super();
		}

		@Override
		public Type getAssignableType() {
			return CDLProperty.class;
		}

		@Override
		public CDLProperty performAction(FlexoBehaviourAction action) {

			CDLProperty cdlProperty = null;

			FreeModelSlotInstance<CDLUnit, CDLModelSlot> modelSlotInstance = getModelSlotInstance(action);
			if (modelSlotInstance.getResourceData() != null) {

			} else {
				logger.warning("Model slot not correctly initialised : model is null");
				return null;
			}

			return cdlProperty;
		}

		@Override
		public FreeModelSlotInstance<CDLUnit, CDLModelSlot> getModelSlotInstance(FlexoBehaviourAction action) {
			return (FreeModelSlotInstance<CDLUnit, CDLModelSlot>) super.getModelSlotInstance(action);
		}

	}
}
