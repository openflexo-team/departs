package org.openflexo.technologyadapter.cdl.fml.action;

import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;

import org.openflexo.foundation.fml.editionaction.FetchRequest;
import org.openflexo.foundation.fml.rt.RunTimeEvaluationContext;
import org.openflexo.gina.annotation.FIBPanel;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.cdl.CDLModelSlot;
import org.openflexo.technologyadapter.cdl.model.CDLProperty;
import org.openflexo.technologyadapter.cdl.model.CDLUnit;

@FIBPanel("Fib/SelectCDLPropertiesPanel.fib")
@ModelEntity
@ImplementationClass(SelectCDLProperties.SelectCDLPropertiesImpl.class)
@XMLElement
public interface SelectCDLProperties extends FetchRequest<CDLModelSlot, CDLProperty> {

	public static abstract class SelectCDLPropertiesImpl extends FetchRequestImpl<CDLModelSlot, CDLProperty>implements SelectCDLProperties {

		private static final Logger logger = Logger.getLogger(SelectCDLProperties.class.getPackage().getName());

		public SelectCDLPropertiesImpl() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public Type getFetchedType() {
			return CDLProperty.class;
		}

		@Override
		public List<CDLProperty> execute(RunTimeEvaluationContext context) {

			if (getModelSlotInstance(context) == null) {
				logger.warning("Could not access model slot instance. Abort.");
				return null;
			}
			if (getModelSlotInstance(context).getResourceData() == null) {
				logger.warning("Could not access model adressed by model slot instance. Abort.");
				return null;
			}

			CDLUnit cdlUnit = (CDLUnit) getModelSlotInstance(context).getAccessedResourceData();

			return filterWithConditions(cdlUnit.getCDLProperties(), context);
		}
	}
}
