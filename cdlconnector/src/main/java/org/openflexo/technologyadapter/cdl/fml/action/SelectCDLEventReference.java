package org.openflexo.technologyadapter.cdl.fml.action;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.openflexo.fib.annotation.FIBPanel;
import org.openflexo.foundation.fml.editionaction.FetchRequest;
import org.openflexo.foundation.fml.rt.action.FlexoBehaviourAction;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.cdl.CDLModelSlot;
import org.openflexo.technologyadapter.cdl.model.CDLActivity;
import org.openflexo.technologyadapter.cdl.model.CDLEventReference;
import org.openflexo.technologyadapter.cdl.model.CDLSeqActivity;
import org.openflexo.technologyadapter.cdl.model.CDLUnit;

@FIBPanel("Fib/SelectCDLEventReferencePanel.fib")
@ModelEntity
@ImplementationClass(SelectCDLEventReference.SelectCDLEventReferenceImpl.class)
@XMLElement
public interface SelectCDLEventReference extends FetchRequest<CDLModelSlot, CDLEventReference> {

	public static abstract class SelectCDLEventReferenceImpl extends FetchRequestImpl<CDLModelSlot, CDLEventReference> implements
			SelectCDLEventReference {

		private static final Logger logger = Logger.getLogger(SelectCDLEventReference.class.getPackage().getName());

		public SelectCDLEventReferenceImpl() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public Type getFetchedType() {
			return CDLSeqActivity.class;
		}

		@Override
		public List<CDLEventReference> execute(FlexoBehaviourAction action) {

			if (getModelSlotInstance(action) == null) {
				logger.warning("Could not access model slot instance. Abort.");
				return null;
			}
			if (getModelSlotInstance(action).getResourceData() == null) {
				logger.warning("Could not access model adressed by model slot instance. Abort.");
				return null;
			}

			CDLUnit cdlUnit = (CDLUnit) getModelSlotInstance(action).getAccessedResourceData();

			List<CDLEventReference> selectedCDLSeqActivities = new ArrayList<CDLEventReference>();
			for (CDLActivity activity : cdlUnit.getCDLActivities()) {
				if (activity instanceof CDLEventReference) {
					selectedCDLSeqActivities.add((CDLEventReference) activity);
				}
			}

			List<CDLEventReference> returned = filterWithConditions(selectedCDLSeqActivities, action);

			return returned;
		}
	}
}
