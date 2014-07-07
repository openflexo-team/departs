package org.openflexo.technologyadapter.fiacre.virtualmodel.actions;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.openflexo.foundation.view.action.FlexoBehaviourAction;
import org.openflexo.foundation.viewpoint.annotations.FIBPanel;
import org.openflexo.foundation.viewpoint.editionaction.FetchRequest;
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
public interface SelectFiacreProcess extends FetchRequest<FiacreProgramModelSlot, FiacreProcess> {

	public static abstract class SelectFiacreProcessImpl extends FetchRequestImpl<FiacreProgramModelSlot, FiacreProcess> implements SelectFiacreProcess {

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
		public List<FiacreProcess> performAction(FlexoBehaviourAction action) {

			if (getModelSlotInstance(action) == null) {
				logger.warning("Could not access model slot instance. Abort.");
				return null;
			}
			if (getModelSlotInstance(action).getResourceData() == null) {
				logger.warning("Could not access model adressed by model slot instance. Abort.");
				return null;
			}

			FiacreProgram fiacreProgram = (FiacreProgram) getModelSlotInstance(action).getAccessedResourceData();

			List<FiacreProcess> selectedFiacreProcesss = new ArrayList<FiacreProcess>(0);

			selectedFiacreProcesss.addAll(fiacreProgram.getFiacreProcesses());

			List<FiacreProcess> returned = filterWithConditions(selectedFiacreProcesss, action);

			return returned;
		}
	}
}
