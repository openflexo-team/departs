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
import org.openflexo.technologyadapter.fiacre.model.FiacreFifo;
import org.openflexo.technologyadapter.fiacre.model.FiacreProgram;

@FIBPanel("Fib/AddFiacreFifoPanel.fib")
@ModelEntity
@ImplementationClass(AddFiacreFifo.AddFiacreFifoImpl.class)
@XMLElement
public interface AddFiacreFifo extends AssignableAction<FiacreProgramModelSlot, FiacreFifo> {

	public static abstract class AddFiacreFifoImpl extends AssignableActionImpl<FiacreProgramModelSlot, FiacreFifo> implements AddFiacreFifo {

		private static final Logger logger = Logger.getLogger(AddFiacreFifo.class.getPackage().getName());

		public AddFiacreFifoImpl() {
			super();
		}

		@Override
		public Type getAssignableType() {
			return FiacreFifo.class;
		}

		@Override
		public FiacreFifo performAction(FlexoBehaviourAction action) {

			FiacreFifo fiacreFifo = null;

			FreeModelSlotInstance<FiacreProgram, FiacreProgramModelSlot> modelSlotInstance = getModelSlotInstance(action);
			if (modelSlotInstance.getResourceData() != null) {

			} else {
				logger.warning("Model slot not correctly initialised : model is null");
				return null;
			}

			return fiacreFifo;
		}

		@Override
		public FreeModelSlotInstance<FiacreProgram, FiacreProgramModelSlot> getModelSlotInstance(FlexoBehaviourAction action) {
			return (FreeModelSlotInstance<FiacreProgram, FiacreProgramModelSlot>) super.getModelSlotInstance(action);
		}

	}
}
