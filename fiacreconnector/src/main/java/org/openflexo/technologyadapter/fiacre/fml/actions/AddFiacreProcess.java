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
import org.openflexo.technologyadapter.fiacre.model.FiacreProcess;
import org.openflexo.technologyadapter.fiacre.model.FiacreProgram;

@FIBPanel("Fib/AddFiacreProcessPanel.fib")
@ModelEntity
@ImplementationClass(AddFiacreProcess.AddFiacreProcessImpl.class)
@XMLElement
@FML("AddFiacreProcess")
public interface AddFiacreProcess extends TechnologySpecificAction<FiacreProgramModelSlot, FiacreProcess> {

	public static abstract class AddFiacreProcessImpl extends TechnologySpecificActionImpl<FiacreProgramModelSlot, FiacreProcess>
			implements AddFiacreProcess {

		private static final Logger logger = Logger.getLogger(AddFiacreProcess.class.getPackage().getName());

		public AddFiacreProcessImpl() {
			super();
		}

		@Override
		public Type getAssignableType() {
			return FiacreProcess.class;
		}

		@Override
		public FiacreProcess execute(RunTimeEvaluationContext context) {

			FiacreProcess fiacreProcess = null;

			FreeModelSlotInstance<FiacreProgram, FiacreProgramModelSlot> modelSlotInstance = getModelSlotInstance(context);
			if (modelSlotInstance.getResourceData() != null) {

			}
			else {
				logger.warning("Model slot not correctly initialised : model is null");
				return null;
			}

			return fiacreProcess;
		}

		@Override
		public FreeModelSlotInstance<FiacreProgram, FiacreProgramModelSlot> getModelSlotInstance(RunTimeEvaluationContext context) {
			return (FreeModelSlotInstance<FiacreProgram, FiacreProgramModelSlot>) super.getModelSlotInstance(context);
		}

	}
}
