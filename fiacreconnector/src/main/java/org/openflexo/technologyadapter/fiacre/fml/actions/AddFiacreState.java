package org.openflexo.technologyadapter.fiacre.fml.actions;

import java.lang.reflect.Type;
import java.util.logging.Logger;

import org.openflexo.fib.annotation.FIBPanel;
import org.openflexo.foundation.fml.annotations.FML;
import org.openflexo.foundation.fml.editionaction.TechnologySpecificAction;
import org.openflexo.foundation.fml.rt.FreeModelSlotInstance;
import org.openflexo.foundation.fml.rt.action.FlexoBehaviourAction;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.fiacre.FiacreProgramModelSlot;
import org.openflexo.technologyadapter.fiacre.model.FiacreProgram;
import org.openflexo.technologyadapter.fiacre.model.FiacreState;

@FIBPanel("Fib/AddFiacreStatePanel.fib")
@ModelEntity
@ImplementationClass(AddFiacreState.AddFiacreStateImpl.class)
@XMLElement
@FML("AddFiacreState")
public interface AddFiacreState extends TechnologySpecificAction<FiacreProgramModelSlot, FiacreState> {

	public static abstract class AddFiacreStateImpl extends TechnologySpecificActionImpl<FiacreProgramModelSlot, FiacreState> implements
			AddFiacreState {

		private static final Logger logger = Logger.getLogger(AddFiacreState.class.getPackage().getName());

		public AddFiacreStateImpl() {
			super();
		}

		@Override
		public Type getAssignableType() {
			return FiacreState.class;
		}

		@Override
		public FiacreState execute(FlexoBehaviourAction action) {

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
