package org.openflexo.technologyadapter.fiacre.fml.actions;

import java.lang.reflect.Type;
import java.util.logging.Logger;

import org.openflexo.foundation.fml.annotations.FIBPanel;
import org.openflexo.foundation.fml.annotations.FML;
import org.openflexo.foundation.fml.editionaction.TechnologySpecificAction;
import org.openflexo.foundation.fml.rt.FreeModelSlotInstance;
import org.openflexo.foundation.fml.rt.action.FlexoBehaviourAction;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.fiacre.FiacreProgramModelSlot;
import org.openflexo.technologyadapter.fiacre.model.FiacreComponent;
import org.openflexo.technologyadapter.fiacre.model.FiacreProgram;

@FIBPanel("Fib/AddFiacreComponentPanel.fib")
@ModelEntity
@ImplementationClass(AddFiacreComponent.AddFiacreComponentImpl.class)
@XMLElement
@FML("AddFiacreComponent")
public interface AddFiacreComponent extends TechnologySpecificAction<FiacreProgramModelSlot, FiacreComponent> {

	public static abstract class AddFiacreComponentImpl extends TechnologySpecificActionImpl<FiacreProgramModelSlot, FiacreComponent>
			implements AddFiacreComponent {

		private static final Logger logger = Logger.getLogger(AddFiacreComponent.class.getPackage().getName());

		public AddFiacreComponentImpl() {
			super();
		}

		@Override
		public Type getAssignableType() {
			return FiacreComponent.class;
		}

		@Override
		public FiacreComponent execute(FlexoBehaviourAction action) {

			FiacreComponent fiacreComponent = null;

			FreeModelSlotInstance<FiacreProgram, FiacreProgramModelSlot> modelSlotInstance = getModelSlotInstance(action);
			if (modelSlotInstance.getResourceData() != null) {

			} else {
				logger.warning("Model slot not correctly initialised : model is null");
				return null;
			}

			return fiacreComponent;
		}

		@Override
		public FreeModelSlotInstance<FiacreProgram, FiacreProgramModelSlot> getModelSlotInstance(FlexoBehaviourAction action) {
			return (FreeModelSlotInstance<FiacreProgram, FiacreProgramModelSlot>) super.getModelSlotInstance(action);
		}

	}
}
