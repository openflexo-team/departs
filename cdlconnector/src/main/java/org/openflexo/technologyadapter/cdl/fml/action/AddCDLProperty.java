package org.openflexo.technologyadapter.cdl.fml.action;

import java.lang.reflect.Type;
import java.util.logging.Logger;

import org.openflexo.foundation.fml.annotations.FIBPanel;
import org.openflexo.foundation.fml.editionaction.TechnologySpecificAction;
import org.openflexo.foundation.fml.editionaction.TechnologySpecificAction.TechnologySpecificActionImpl;
import org.openflexo.foundation.fml.rt.FreeModelSlotInstance;
import org.openflexo.foundation.fml.rt.action.FlexoBehaviourAction;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.cdl.CDLModelSlot;
import org.openflexo.technologyadapter.cdl.model.CDLProperty;
import org.openflexo.technologyadapter.cdl.model.CDLUnit;

@FIBPanel("Fib/AddCDLPropertyPanel.fib")
@ModelEntity
@ImplementationClass(AddCDLProperty.AddCDLPropertyImpl.class)
@XMLElement
public interface AddCDLProperty extends TechnologySpecificAction<CDLModelSlot, CDLProperty> {

	public static abstract class AddCDLPropertyImpl extends TechnologySpecificActionImpl<CDLModelSlot, CDLProperty> implements
			AddCDLProperty {

		private static final Logger logger = Logger.getLogger(AddCDLProperty.class.getPackage().getName());

		public AddCDLPropertyImpl() {
			super();
		}

		@Override
		public Type getAssignableType() {
			return CDLProperty.class;
		}

		@Override
		public CDLProperty execute(FlexoBehaviourAction action) {

			CDLProperty cdlProperty = null;

			FreeModelSlotInstance<CDLUnit, CDLModelSlot> modelSlotInstance = getModelSlotInstance(action);
			if (modelSlotInstance.getResourceData() != null) {

			} else {
				logger.warning("Model slot not correctly initialised : model is null");
				return null;
			}

			return cdlProperty;
		}

		@Override
		public FreeModelSlotInstance<CDLUnit, CDLModelSlot> getModelSlotInstance(FlexoBehaviourAction action) {
			return (FreeModelSlotInstance<CDLUnit, CDLModelSlot>) super.getModelSlotInstance(action);
		}

	}
}
