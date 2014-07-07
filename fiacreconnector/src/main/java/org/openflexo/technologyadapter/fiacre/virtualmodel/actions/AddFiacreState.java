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
import org.openflexo.technologyadapter.fiacre.model.FiacreState;
import org.openflexo.technologyadapter.fiacre.model.FiacreProgram;

@FIBPanel("Fib/AddFiacreStatePanel.fib")
@ModelEntity
@ImplementationClass(AddFiacreState.AddFiacreStateImpl.class)
@XMLElement
public interface AddFiacreState extends AssignableAction<FiacreProgramModelSlot, FiacreState> {

	public static abstract class AddFiacreStateImpl extends AssignableActionImpl<FiacreProgramModelSlot, FiacreState> implements AddFiacreState {

		private static final Logger logger = Logger.getLogger(AddFiacreState.class.getPackage().getName());

		public AddFiacreStateImpl() {
			super();
		}

		@Override
		public Type getAssignableType() {
			return FiacreState.class;
		}

		@Override
		public FiacreState performAction(FlexoBehaviourAction action) {

			FiacreState fiacreState = null;

			FreeModelSlotInstance<FiacreProgram, FiacreProgramModelSlot> modelSlotInstance = getModelSlotInstance(action);
			if (modelSlotInstance.getResourceData() != null) {

			} else {
				logger.warning("Model slot not correctly initialised : model is null");
				return null;
			}

			return fiacreState;
		}

		@Override
		public FreeModelSlotInstance<FiacreProgram, FiacreProgramModelSlot> getModelSlotInstance(FlexoBehaviourAction action) {
			return (FreeModelSlotInstance<FiacreProgram, FiacreProgramModelSlot>) super.getModelSlotInstance(action);
		}

	}
}
