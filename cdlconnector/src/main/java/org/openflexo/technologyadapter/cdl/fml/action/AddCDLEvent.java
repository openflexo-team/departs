package org.openflexo.technologyadapter.cdl.fml.action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.logging.Logger;

import org.openflexo.antar.binding.DataBinding;
import org.openflexo.antar.expr.NullReferenceException;
import org.openflexo.antar.expr.TypeMismatchException;
import org.openflexo.foundation.fml.annotations.FIBPanel;
import org.openflexo.foundation.fml.editionaction.TechnologySpecificAction;
import org.openflexo.foundation.fml.rt.FreeModelSlotInstance;
import org.openflexo.foundation.fml.rt.action.FlexoBehaviourAction;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLAttribute;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.cdl.CDLModelSlot;
import org.openflexo.technologyadapter.cdl.model.CDLEvent;
import org.openflexo.technologyadapter.cdl.model.CDLProcessID;
import org.openflexo.technologyadapter.cdl.model.CDLUnit;

@FIBPanel("Fib/AddCDLEventPanel.fib")
@ModelEntity
@ImplementationClass(AddCDLEvent.AddCDLEventImpl.class)
@XMLElement
public interface AddCDLEvent extends TechnologySpecificAction<CDLModelSlot, CDLEvent> {

	@PropertyIdentifier(type = DataBinding.class)
	public static final String EVENT_NAME_KEY = "eventName";
	@PropertyIdentifier(type = DataBinding.class)
	public static final String EVENT_VALUE_KEY = "eventValue";
	@PropertyIdentifier(type = DataBinding.class)
	public static final String FROM_PROCESS_ID__KEY = "fromProcessID";
	@PropertyIdentifier(type = DataBinding.class)
	public static final String TO_PROCESS_ID__KEY = "toProcessID";

	@Getter(value = EVENT_NAME_KEY)
	@XMLAttribute
	public DataBinding<String> getEventName();

	@Setter(EVENT_NAME_KEY)
	public void setEventName(DataBinding<String> name);

	@Getter(value = EVENT_VALUE_KEY)
	@XMLAttribute
	public DataBinding<String> getEventValue();

	@Setter(EVENT_VALUE_KEY)
	public void setEventValue(DataBinding<String> value);

	@Getter(value = FROM_PROCESS_ID__KEY)
	@XMLAttribute
	public DataBinding<CDLProcessID> getFromProcessID();

	@Setter(FROM_PROCESS_ID__KEY)
	public void setFromProcessID(DataBinding<CDLProcessID> fromProcessID);

	@Getter(value = TO_PROCESS_ID__KEY)
	@XMLAttribute
	public DataBinding<CDLProcessID> getToProcessID();

	@Setter(TO_PROCESS_ID__KEY)
	public void setToProcessID(DataBinding<CDLProcessID> toProcessID);

	public static abstract class AddCDLEventImpl extends TechnologySpecificActionImpl<CDLModelSlot, CDLEvent> implements AddCDLEvent {

		private static final Logger logger = Logger.getLogger(AddCDLEvent.class.getPackage().getName());

		private DataBinding<String> eventName;

		private DataBinding<String> eventValue;

		private DataBinding<CDLProcessID> fromProcessID;

		private DataBinding<CDLProcessID> toProcessID;

		public AddCDLEventImpl() {
			super();
		}

		@Override
		public Type getAssignableType() {
			return CDLEvent.class;
		}

		@Override
		public CDLEvent execute(FlexoBehaviourAction action) {

			CDLEvent cdlEvent = null;

			FreeModelSlotInstance<CDLUnit, CDLModelSlot> modelSlotInstance = getModelSlotInstance(action);
			if (modelSlotInstance.getResourceData() != null) {
				CDLUnit cdlUnit = modelSlotInstance.getAccessedResourceData();

				try {
					CDLProcessID fromPID = getFromProcessID().getBindingValue(action);
					CDLProcessID toPID = getToProcessID().getBindingValue(action);
					String eventName = getEventName().getBindingValue(action);
					String eventValue = getEventValue().getBindingValue(action);

					cdlEvent = cdlUnit.createCDLEvent(fromPID, toPID, eventValue, eventName);
					modelSlotInstance.getResourceData().setIsModified();

				} catch (TypeMismatchException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NullReferenceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				logger.warning("Model slot not correctly initialised : model is null");
				return null;
			}

			return cdlEvent;
		}

		@Override
		public FreeModelSlotInstance<CDLUnit, CDLModelSlot> getModelSlotInstance(FlexoBehaviourAction action) {
			return (FreeModelSlotInstance<CDLUnit, CDLModelSlot>) super.getModelSlotInstance(action);
		}

		@Override
		public DataBinding<String> getEventName() {
			if (eventName == null) {
				eventName = new DataBinding<String>(this, String.class, DataBinding.BindingDefinitionType.GET);
				eventName.setBindingName("eventName");
			}
			return eventName;
		}

		@Override
		public void setEventName(DataBinding<String> eventName) {
			if (eventName != null) {
				eventName.setOwner(this);
				eventName.setDeclaredType(String.class);
				eventName.setBindingDefinitionType(DataBinding.BindingDefinitionType.GET);
				eventName.setBindingName("eventName");
			}
			this.eventName = eventName;
		}

		@Override
		public DataBinding<String> getEventValue() {
			if (eventValue == null) {
				eventValue = new DataBinding<String>(this, String.class, DataBinding.BindingDefinitionType.GET);
				eventValue.setBindingName("eventValue");
			}
			return eventValue;
		}

		@Override
		public void setEventValue(DataBinding<String> eventValue) {
			if (eventValue != null) {
				eventValue.setOwner(this);
				eventValue.setDeclaredType(String.class);
				eventValue.setBindingDefinitionType(DataBinding.BindingDefinitionType.GET);
				eventValue.setBindingName("eventValue");
			}
			this.eventValue = eventValue;
		}

		@Override
		public DataBinding<CDLProcessID> getFromProcessID() {
			if (fromProcessID == null) {
				fromProcessID = new DataBinding<CDLProcessID>(this, CDLProcessID.class, DataBinding.BindingDefinitionType.GET);
				fromProcessID.setBindingName("fromProcessID");
			}
			return fromProcessID;
		}

		@Override
		public void setFromProcessID(DataBinding<CDLProcessID> fromProcessID) {
			if (fromProcessID != null) {
				fromProcessID.setOwner(this);
				fromProcessID.setDeclaredType(CDLProcessID.class);
				fromProcessID.setBindingDefinitionType(DataBinding.BindingDefinitionType.GET);
				fromProcessID.setBindingName("fromProcessID");
			}
			this.fromProcessID = fromProcessID;
		}

		@Override
		public DataBinding<CDLProcessID> getToProcessID() {
			if (toProcessID == null) {
				toProcessID = new DataBinding<CDLProcessID>(this, CDLProcessID.class, DataBinding.BindingDefinitionType.GET);
				toProcessID.setBindingName("toProcessID");
			}
			return toProcessID;
		}

		@Override
		public void setToProcessID(DataBinding<CDLProcessID> toProcessID) {
			if (toProcessID != null) {
				toProcessID.setOwner(this);
				toProcessID.setDeclaredType(CDLProcessID.class);
				toProcessID.setBindingDefinitionType(DataBinding.BindingDefinitionType.GET);
				toProcessID.setBindingName("toProcessID");
			}
			this.toProcessID = toProcessID;
		}

	}
}
