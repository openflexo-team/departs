package org.openflexo.technologyadapter.cdl.fml.action;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.openflexo.foundation.fml.editionaction.FetchRequest;
import org.openflexo.foundation.fml.rt.RunTimeEvaluationContext;
import org.openflexo.gina.annotation.FIBPanel;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.cdl.CDLModelSlot;
import org.openflexo.technologyadapter.cdl.model.CDLActivity;
import org.openflexo.technologyadapter.cdl.model.CDLParActivity;
import org.openflexo.technologyadapter.cdl.model.CDLUnit;

@FIBPanel("Fib/SelectCDLParActivityPanel.fib")
@ModelEntity
@ImplementationClass(SelectCDLParActivity.SelectCDLParActivityImpl.class)
@XMLElement
public interface SelectCDLParActivity extends FetchRequest<CDLModelSlot, CDLParActivity> {

	public static abstract class SelectCDLParActivityImpl extends FetchRequestImpl<CDLModelSlot, CDLParActivity>
			implements SelectCDLParActivity {

		private static final Logger logger = Logger.getLogger(SelectCDLParActivity.class.getPackage().getName());

		public SelectCDLParActivityImpl() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public Type getFetchedType() {
			return CDLParActivity.class;
		}

		@Override
		public List<CDLParActivity> execute(RunTimeEvaluationContext context) {

			if (getModelSlotInstance(context) == null) {
				logger.warning("Could not access model slot instance. Abort.");
				return null;
			}
			if (getModelSlotInstance(context).getResourceData() == null) {
				logger.warning("Could not access model adressed by model slot instance. Abort.");
				return null;
			}

			CDLUnit cdlUnit = (CDLUnit) getModelSlotInstance(context).getAccessedResourceData();

			List<CDLParActivity> selectedCDLParActivities = new ArrayList<CDLParActivity>();
			for (CDLActivity activity : cdlUnit.getCDLActivities()) {
				if (activity instanceof CDLParActivity) {
					selectedCDLParActivities.add((CDLParActivity) activity);
				}
			}

			List<CDLParActivity> returned = filterWithConditions(selectedCDLParActivities, context);

			return returned;
		}
	}
}
