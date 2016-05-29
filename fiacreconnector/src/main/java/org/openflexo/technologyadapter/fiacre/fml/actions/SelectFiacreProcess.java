package org.openflexo.technologyadapter.fiacre.fml.actions;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.openflexo.foundation.fml.annotations.FML;
import org.openflexo.foundation.fml.editionaction.FetchRequest;
import org.openflexo.foundation.fml.rt.RunTimeEvaluationContext;
import org.openflexo.gina.annotation.FIBPanel;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.fiacre.FiacreProgramModelSlot;
import org.openflexo.technologyadapter.fiacre.model.FiacreProcess;
import org.openflexo.technologyadapter.fiacre.model.FiacreProgram;

@FIBPanel("Fib/SelectFiacreProcessPanel.fib")
@ModelEntity
@ImplementationClass(SelectFiacreProcess.SelectFiacreProcessImpl.class)
@XMLElement
@FML("SelectFiacreProcess")
public interface SelectFiacreProcess extends FetchRequest<FiacreProgramModelSlot, FiacreProcess> {

	public static abstract class SelectFiacreProcessImpl extends FetchRequestImpl<FiacreProgramModelSlot, FiacreProcess>
			implements SelectFiacreProcess {

		private static final Logger logger = Logger.getLogger(SelectFiacreProcess.class.getPackage().getName());

		public SelectFiacreProcessImpl() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public Type getFetchedType() {
			return FiacreProcess.class;
		}

		@Override
		public List<FiacreProcess> execute(RunTimeEvaluationContext context) {

			if (getModelSlotInstance(context) == null) {
				logger.warning("Could not access model slot instance. Abort.");
				return null;
			}
			if (getModelSlotInstance(context).getResourceData() == null) {
				logger.warning("Could not access model adressed by model slot instance. Abort.");
				return null;
			}

			FiacreProgram fiacreProgram = (FiacreProgram) getModelSlotInstance(context).getAccessedResourceData();

			List<FiacreProcess> selectedFiacreProcesss = new ArrayList<FiacreProcess>(0);

			selectedFiacreProcesss.addAll(fiacreProgram.getFiacreProcesses());

			List<FiacreProcess> returned = filterWithConditions(selectedFiacreProcesss, context);

			return returned;
		}
	}
}
