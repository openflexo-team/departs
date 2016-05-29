package org.openflexo.technologyadapter.fiacre.fml.actions;

import java.lang.reflect.Type;
import java.util.logging.Logger;

import org.openflexo.foundation.fml.annotations.FML;
import org.openflexo.foundation.fml.editionaction.TechnologySpecificAction;
import org.openflexo.foundation.fml.rt.FreeModelSlotInstance;
import org.openflexo.foundation.fml.rt.RunTimeEvaluationContext;
import org.openflexo.gina.annotation.FIBPanel;
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
@FML("AddFiacreFifo")
public interface AddFiacreFifo extends TechnologySpecificAction<FiacreProgramModelSlot, FiacreFifo> {

	public static abstract class AddFiacreFifoImpl extends TechnologySpecificActionImpl<FiacreProgramModelSlot, FiacreFifo>
			implements AddFiacreFifo {

		private static final Logger logger = Logger.getLogger(AddFiacreFifo.class.getPackage().getName());

		public AddFiacreFifoImpl() {
			super();
		}

		@Override
		public Type getAssignableType() {
			return FiacreFifo.class;
		}

		@Override
		public FiacreFifo execute(RunTimeEvaluationContext context) {

			FiacreFifo fiacreFifo = null;

			FreeModelSlotInstance<FiacreProgram, FiacreProgramModelSlot> modelSlotInstance = getModelSlotInstance(context);
			if (modelSlotInstance.getResourceData() != null) {

			}
			else {
				logger.warning("Model slot not correctly initialised : model is null");
				return null;
			}

			return fiacreFifo;
		}

		@Override
		public FreeModelSlotInstance<FiacreProgram, FiacreProgramModelSlot> getModelSlotInstance(RunTimeEvaluationContext context) {
			return (FreeModelSlotInstance<FiacreProgram, FiacreProgramModelSlot>) super.getModelSlotInstance(context);
		}

	}
}
