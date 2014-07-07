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
import org.openflexo.technologyadapter.fiacre.model.FiacreComponent;
import org.openflexo.technologyadapter.fiacre.model.FiacreProgram;

@FIBPanel("Fib/SelectFiacreComponentsPanel.fib")
@ModelEntity
@ImplementationClass(SelectFiacreComponents.SelectFiacreComponentImpl.class)
@XMLElement
public interface SelectFiacreComponents extends FetchRequest<FiacreProgramModelSlot, FiacreComponent> {

	public static abstract class SelectFiacreComponentImpl extends FetchRequestImpl<FiacreProgramModelSlot, FiacreComponent> implements SelectFiacreComponents {

		private static final Logger logger = Logger.getLogger(SelectFiacreComponents.class.getPackage().getName());

		public SelectFiacreComponentImpl() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public Type getFetchedType() {
			return FiacreComponent.class;
		}

		@Override
		public List<FiacreComponent> performAction(FlexoBehaviourAction action) {

			if (getModelSlotInstance(action) == null) {
				logger.warning("Could not access model slot instance. Abort.");
				return null;
			}
			if (getModelSlotInstance(action).getResourceData() == null) {
				logger.warning("Could not access model adressed by model slot instance. Abort.");
				return null;
			}

			FiacreProgram fiacreProgram = (FiacreProgram) getModelSlotInstance(action).getAccessedResourceData();

			List<FiacreComponent> selectedFiacreComponents = new ArrayList<FiacreComponent>(0);

			selectedFiacreComponents.addAll(fiacreProgram.getFiacreComponents());

			List<FiacreComponent> returned = filterWithConditions(selectedFiacreComponents, action);

			return returned;
		}
	}
}
