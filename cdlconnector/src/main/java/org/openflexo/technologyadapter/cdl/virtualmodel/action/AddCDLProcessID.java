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
import org.openflexo.technologyadapter.cdl.model.CDLProcessID;
import org.openflexo.technologyadapter.cdl.model.CDLUnit;

@FIBPanel("Fib/AddCDLProcessIDPanel.fib")
@ModelEntity
@ImplementationClass(AddCDLProcessID.AddCDLProcessIDImpl.class)
@XMLElement
public interface AddCDLProcessID extends AssignableAction<CDLModelSlot, CDLProcessID> {

	public static abstract class AddCDLProcessIDImpl extends AssignableActionImpl<CDLModelSlot, CDLProcessID> implements AddCDLProcessID {

		private static final Logger logger = Logger.getLogger(AddCDLProcessID.class.getPackage().getName());

		public AddCDLProcessIDImpl() {
			super();
		}

		@Override
		public Type getAssignableType() {
			return CDLProcessID.class;
		}

		@Override
		public CDLProcessID performAction(FlexoBehaviourAction action) {

			CDLProcessID cdlProcessID = null;

			FreeModelSlotInstance<CDLUnit, CDLModelSlot> modelSlotInstance = getModelSlotInstance(action);
			if (modelSlotInstance.getResourceData() != null) {

			} else {
				logger.warning("Model slot not correctly initialised : model is null");
				return null;
			}

			return cdlProcessID;
		}

		@Override
		public FreeModelSlotInstance<CDLUnit, CDLModelSlot> getModelSlotInstance(FlexoBehaviourAction action) {
			return (FreeModelSlotInstance<CDLUnit, CDLModelSlot>) super.getModelSlotInstance(action);
		}

	}
}
