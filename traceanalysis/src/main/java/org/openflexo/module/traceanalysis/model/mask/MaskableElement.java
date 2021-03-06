package org.openflexo.module.traceanalysis.model.mask;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openflexo.foundation.DefaultFlexoObject;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.module.traceanalysis.model.TraceVirtualModelInstance;
import org.openflexo.technologyadapter.trace.model.OBPTrace;
import org.openflexo.technologyadapter.trace.model.OBPTraceConfiguration;
import org.openflexo.technologyadapter.trace.model.OBPTraceObject;


/**
 * A Masked Element can be associated to several mask and can represents an object.
 * It support a mode, which is used to manage the element in different ways.
 * @author Vincent
 *
 */
public abstract class MaskableElement extends DefaultFlexoObject implements PropertyChangeListener{
	
	private static final Logger logger = Logger.getLogger(MaskableElement.class.getPackage().getName());

	public enum MaskMode {
		VISIBLE_IF_RELEVANT, ALWAYS_VISIBLE
	}

	public abstract FlexoObject getType();
	
	public abstract FlexoObject getValue();
	
	public abstract String getFullStringRepresentation();
	
	public abstract String getName();
	
	private List<MaskableElement> childs;
	
	private MaskableElement parent;
	
	private final TraceVirtualModelInstance trace;
	
	private MaskMode maskMode;
	
	private Mask mask;

	public MaskableElement(MaskableElement parent, TraceVirtualModelInstance trace) {
		this.parent = parent;
		this.trace = trace;
		setMaskMode(MaskMode.VISIBLE_IF_RELEVANT);
		trace.getPropertyChangeSupport().addPropertyChangeListener("selectedMask", this);
		childs = new ArrayList<MaskableElement>();
		trace.getMaskableElements().add(this);
	}

	public void addChild(MaskableElement element){
		if(!childs.contains(element)){
			childs.add(element);
			element.parent=this;
		}
	}

	public void removeChild(MaskableElement element){
		if(childs.contains(element)){
			childs.remove(element);
			element.parent=null;
		}
	}
	
	public List<MaskableElement> getChilds(){
		return childs;
	}
	
	public MaskableElement getParent() {
		return parent;
	}	
	
	public TraceVirtualModelInstance getTrace() {
		return trace;
	}
	
	public OBPTrace getOBPTrace(){
		return getTrace().getTraceOBP();
	}
	
	public MaskableElement getMaskableElement(OBPTraceObject object){
		for(MaskableElement child : getChilds()){
			if(child.getValue().equals(object)){
				return child;
			}else if(child.getMaskableElement(object)!=null){
				return child;
			}
		}
		//logger.log(Level.WARNING, "No maskable element found for obp trace object named " + object.getName());
		return null;
	}

	public Mask getMask() {
		return mask;
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		if(arg0.getPropertyName().equals("selectedMask")){
			if(getMask()==null){
				getPropertyChangeSupport().addPropertyChangeListener("maskMode", trace.getSelectedMask());
				setMask((Mask)arg0.getNewValue());
			}else if(getMask() != (Mask)arg0.getNewValue()){
				getPropertyChangeSupport().removePropertyChangeListener(getMask());
				getPropertyChangeSupport().addPropertyChangeListener("maskMode", trace.getSelectedMask());
				setMask((Mask)arg0.getNewValue());
			}
		}
	}
	
	public MaskMode getMaskMode() {
		return maskMode;
	}

	public void setMaskMode(MaskMode maskMode) {
		this.maskMode = maskMode;
		getPropertyChangeSupport().firePropertyChange("maskMode", null, null);
	}

	private void setMask(Mask mask) {
		this.mask = mask;
	}
	
	public boolean differsFromPreviousConfiguration(OBPTraceConfiguration current){
		if(getOBPTrace().getInitConfiguration().equals(current)){
			return true;
		}
		return differsFromPreviousConfiguration(current, getOBPTrace().getPreviousConfiguration(current));
	}
	
	public boolean differsFromPreviousConfiguration(OBPTraceConfiguration current, OBPTraceConfiguration previous){
		return false;
	}

}
