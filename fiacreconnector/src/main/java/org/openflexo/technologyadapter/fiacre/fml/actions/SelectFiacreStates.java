package org.openflexo.technologyadapter.fiacre.fml.actions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.openflexo.connie.DataBinding;
import org.openflexo.connie.exception.NullReferenceException;
import org.openflexo.connie.exception.TypeMismatchException;
import org.openflexo.fib.annotation.FIBPanel;
import org.openflexo.foundation.fml.annotations.FML;
import org.openflexo.foundation.fml.editionaction.FetchRequest;
import org.openflexo.foundation.fml.rt.action.FlexoBehaviourAction;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLAttribute;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.technologyadapter.fiacre.FiacreProgramModelSlot;
import org.openflexo.technologyadapter.fiacre.model.FiacreProcess;
import org.openflexo.technologyadapter.fiacre.model.FiacreState;

@FIBPanel("Fib/SelectFiacreStatesPanel.fib")
@ModelEntity
@ImplementationClass(SelectFiacreStates.SelectFiacreStateImpl.class)
@XMLElement
@FML("SelectFiacreStates")
public interface SelectFiacreStates extends FetchRequest<FiacreProgramModelSlot, FiacreState> {

	@PropertyIdentifier(type = DataBinding.class)
	public static final String FIACRE_PROCESS_KEY = "fiacreProcess";

	@Getter(value = FIACRE_PROCESS_KEY)
	@XMLAttribute
	public DataBinding<FiacreProcess> getFiacreProcess();

	@Setter(FIACRE_PROCESS_KEY)
	public void setFiacreProcess(DataBinding<FiacreProcess> fiacreProcess);

	public static abstract class SelectFiacreStateImpl extends FetchRequestImpl<FiacreProgramModelSlot, FiacreState> implements
			SelectFiacreStates {

		private static final Logger logger = Logger.getLogger(SelectFiacreStates.class.getPackage().getName());

		private DataBinding<FiacreProcess> fiacreProcess;

		public SelectFiacreStateImpl() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public Type getFetchedType() {
			return FiacreState.class;
		}

		@Override
		public List<FiacreState> execute(FlexoBehaviourAction action) {

			if (getModelSlotInstance(action) == null) {
				logger.warning("Could not access model slot instance. Abort.");
				return null;
			}
			if (getModelSlotInstance(action).getResourceData() == null) {
				logger.warning("Could not access model adressed by model slot instance. Abort.");
				return null;
			}

			List<FiacreState> selectedFiacreStates = new ArrayList<FiacreState>(0);

			try {
				selectedFiacreStates.addAll(getFiacreProcess().getBindingValue(action).getFiacreStates());
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

			List<FiacreState> returned = filterWithConditions(selectedFiacreStates, action);

			return returned;
		}

		@Override
		public DataBinding<FiacreProcess> getFiacreProcess() {
			if (fiacreProcess == null) {
				fiacreProcess = new DataBinding<FiacreProcess>(this, FiacreProcess.class, DataBinding.BindingDefinitionType.GET);
				fiacreProcess.setBindingName("fiacreProcess");
			}
			return fiacreProcess;
		}

		@Override
		public void setFiacreProcess(DataBinding<FiacreProcess> fiacreProcess) {
			if (fiacreProcess != null) {
				fiacreProcess.setOwner(this);
				fiacreProcess.setDeclaredType(FiacreProcess.class);
				fiacreProcess.setBindingDefinitionType(DataBinding.BindingDefinitionType.GET);
				fiacreProcess.setBindingName("fiacreProcess");
			}
			this.fiacreProcess = fiacreProcess;
		}
	}
}
