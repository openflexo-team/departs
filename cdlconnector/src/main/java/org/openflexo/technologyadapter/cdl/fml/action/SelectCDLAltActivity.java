package org.openflexo.technologyadapter.cdl.fml.action;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.openflexo.foundation.fml.annotations.FIBPanel;
import org.openflexo.foundation.fml.editionaction.FetchRequest;
import org.openflexo.foundation.fml.rt.action.FlexoBehaviourAction;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.cdl.CDLModelSlot;
import org.openflexo.technologyadapter.cdl.model.CDLActivity;
import org.openflexo.technologyadapter.cdl.model.CDLAltActivity;
import org.openflexo.technologyadapter.cdl.model.CDLUnit;

@FIBPanel("Fib/SelectCDLAltActivityPanel.fib")
@ModelEntity
@ImplementationClass(SelectCDLAltActivity.SelectCDLAltActivityImpl.class)
@XMLElement
public interface SelectCDLAltActivity extends FetchRequest<CDLModelSlot, CDLAltActivity> {

	public static abstract class SelectCDLAltActivityImpl extends FetchRequestImpl<CDLModelSlot, CDLAltActivity> implements
			SelectCDLAltActivity {

		private static final Logger logger = Logger.getLogger(SelectCDLAltActivity.class.getPackage().getName());

		public SelectCDLAltActivityImpl() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public Type getFetchedType() {
			return CDLAltActivity.class;
		}

		@Override
		public List<CDLAltActivity> execute(FlexoBehaviourAction action) {

			if (getModelSlotInstance(action) == null) {
				logger.warning("Could not access model slot instance. Abort.");
				return null;
			}
			if (getModelSlotInstance(action).getResourceData() == null) {
				logger.warning("Could not access model adressed by model slot instance. Abort.");
				return null;
			}

			CDLUnit cdlUnit = (CDLUnit) getModelSlotInstance(action).getAccessedResourceData();

			List<CDLAltActivity> selectedCDLAltActivities = new ArrayList<CDLAltActivity>();
			for (CDLActivity activity : cdlUnit.getCDLActivities()) {
				if (activity instanceof CDLAltActivity) {
					selectedCDLAltActivities.add((CDLAltActivity) activity);
				}
			}

			List<CDLAltActivity> returned = filterWithConditions(selectedCDLAltActivities, action);

			return returned;
		}
	}
}
