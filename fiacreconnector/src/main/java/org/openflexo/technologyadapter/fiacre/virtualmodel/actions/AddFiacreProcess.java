package org.openflexo.technologyadapter.fiacre.virtualmodel.actions;

import java.lang.reflect.Type;
import java.util.logging.Logger;

import org.openflexo.foundation.view.FreeModelSlotInstance;
import org.openflexo.foundation.view.action.FlexoBehaviourAction;
import org.openflexo.foundation.viewpoint.annotations.FIBPanel;
import org.openflexo.foundation.viewpoint.editionaction.AssignableAction;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.fiacre.FiacreProgramModelSlot;
import org.openflexo.technologyadapter.fiacre.model.FiacreProcess;
import org.openflexo.technologyadapter.fiacre.model.FiacreProgram;

@FIBPanel("Fib/AddFiacreProcessPanel.fib")
@ModelEntity
@ImplementationClass(AddFiacreProcess.AddFiacreProcessImpl.class)
@XMLElement
public interface AddFiacreProcess extends AssignableAction<FiacreProgramModelSlot, FiacreProcess> {

	public static abstract class AddFiacreProcessImpl extends AssignableActionImpl<FiacreProgramModelSlot, FiacreProcess> implements AddFiacreProcess {

		private static final Logger logger = Logger.getLogger(AddFiacreProcess.class.getPackage().getName());

		public AddFiacreProcessImpl() {
			super();
		}

		@Override
		public Type getAssignableType() {
			return FiacreProcess.class;
		}

		@Override
		public FiacreProcess performAction(FlexoBehaviourAction action) {

			FiacreProcess fiacreProcess = null;

			FreeModelSlotInstance<FiacreProgram, FiacreProgramModelSlot> modelSlotInstance = getModelSlotInstance(action);
			if (modelSlotInstance.getResourceData() != null) {

			} else {
				logger.warning("Model slot not correctly initialised : model is null");
				return null;
			}

			return fiacreProcess;
		}

		@Override
		public FreeModelSlotInstance<FiacreProgram, FiacreProgramModelSlot> getModelSlotInstance(FlexoBehaviourAction action) {
			return (FreeModelSlotInstance<FiacreProgram, FiacreProgramModelSlot>) super.getModelSlotInstance(action);
		}

	}
}
