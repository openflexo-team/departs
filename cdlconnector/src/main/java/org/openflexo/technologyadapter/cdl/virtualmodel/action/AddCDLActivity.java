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
import org.openflexo.technologyadapter.cdl.model.CDLActivity;
import org.openflexo.technologyadapter.cdl.model.CDLUnit;

@FIBPanel("Fib/AddCDLActivityPanel.fib")
@ModelEntity
@ImplementationClass(AddCDLActivity.AddCDLActivityImpl.class)
@XMLElement
public interface AddCDLActivity extends AssignableAction<CDLModelSlot, CDLActivity> {

	public static abstract class AddCDLActivityImpl extends AssignableActionImpl<CDLModelSlot, CDLActivity> implements AddCDLActivity {

		private static final Logger logger = Logger.getLogger(AddCDLActivity.class.getPackage().getName());

		public AddCDLActivityImpl() {
			super();
		}

		@Override
		public Type getAssignableType() {
			return CDLActivity.class;
		}

		@Override
		public CDLActivity performAction(FlexoBehaviourAction action) {

			CDLActivity cdlActivity = null;

			FreeModelSlotInstance<CDLUnit, CDLModelSlot> modelSlotInstance = getModelSlotInstance(action);
			if (modelSlotInstance.getResourceData() != null) {

			} else {
				logger.warning("Model slot not correctly initialised : model is null");
				return null;
			}

			return cdlActivity;
		}

		@Override
		public FreeModelSlotInstance<CDLUnit, CDLModelSlot> getModelSlotInstance(FlexoBehaviourAction action) {
			return (FreeModelSlotInstance<CDLUnit, CDLModelSlot>) super.getModelSlotInstance(action);
		}

	}
}
