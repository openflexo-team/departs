package org.openflexo.technologyadapter.fiacre.model.io;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import obp.fiacre.model.ComponentDecl;
import obp.fiacre.model.Declaration;
import obp.fiacre.model.Instance;
import obp.fiacre.model.InterfacedComp;
import obp.fiacre.model.LocalVariable;
import obp.fiacre.model.Par;
import obp.fiacre.model.ProcessDecl;
import obp.fiacre.model.State;

import org.openflexo.model.ModelContext;
import org.openflexo.model.ModelContextLibrary;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.model.factory.ModelFactory;
import org.openflexo.technologyadapter.fiacre.FiacreTechnologyAdapter;
import org.openflexo.technologyadapter.fiacre.model.FiacreComponent;
import org.openflexo.technologyadapter.fiacre.model.FiacreObject;
import org.openflexo.technologyadapter.fiacre.model.FiacreProcess;
import org.openflexo.technologyadapter.fiacre.model.FiacreProgram;
import org.openflexo.technologyadapter.fiacre.model.FiacreState;
import org.openflexo.technologyadapter.fiacre.model.FiacreVariable;

public class FiacreProgramConverter {

	private static final Logger logger = Logger.getLogger(FiacreProgramConverter.class.getPackage().getName());

	protected final Map<Object, FiacreObject> fiacreObjects = new HashMap<Object, FiacreObject>();

	private ModelFactory factory;
	
	private FiacreTechnologyAdapter technologyAdapter;

	/**
	 * Constructor.
	 */
	public FiacreProgramConverter() {
		try {
			factory = new ModelFactory(ModelContextLibrary.getCompoundModelContext(FiacreObject.class, FiacreProcess.class,FiacreProgram.class, FiacreVariable.class,FiacreComponent.class ));
		} catch (ModelDefinitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FiacreTechnologyAdapter getTechnologyAdapter() {
		return technologyAdapter;
	}

	public void setTechnologyAdapter(FiacreTechnologyAdapter technologyAdapter) {
		this.technologyAdapter = technologyAdapter;
	}

	public FiacreProgram convertFiacreProgram(obp.fiacre.model.Program fiacreProgram, FiacreTechnologyAdapter technologyAdapter) {

		FiacreProgram flexoFiacreProgram = factory.newInstance(FiacreProgram.class);
		flexoFiacreProgram.setTechnologyAdapter(technologyAdapter);
		flexoFiacreProgram.setFiacreProgram(fiacreProgram);
		fiacreObjects.put(fiacreProgram, flexoFiacreProgram);
		for (Declaration declaration : fiacreProgram.getDeclarationList()) {
			if (declaration instanceof ProcessDecl) {
				FiacreProcess fiacreProcess = convertFiacreProcess((ProcessDecl) declaration, flexoFiacreProgram);
				flexoFiacreProgram.addToFiacreProcesses(fiacreProcess);
			}
		}
		for (Declaration declaration : fiacreProgram.getDeclarationList()) {
			if (declaration instanceof ComponentDecl) {
				FiacreComponent fiacreComponent = convertFiacreComponent((ComponentDecl) declaration, flexoFiacreProgram);
				flexoFiacreProgram.addToFiacreComponents(fiacreComponent);
			}
		}
		return flexoFiacreProgram;
	}
	
	public FiacreProcess convertFiacreProcess(ProcessDecl process, FiacreProgram flexoFiacreProgram) {
		FiacreProcess fiacreProcess = null;
		if (!getFiacreObjects().containsKey(process)) {
			fiacreProcess = factory.newInstance(FiacreProcess.class);
			fiacreProcess.setFiacreProcess(process);
			for(State state:process.getStateList()){
				fiacreProcess.addToFiacreStates(convertFiacreState(state,flexoFiacreProgram));
			}
			for(LocalVariable var:process.getVarList()){
				fiacreProcess.addToFiacreVariables(convertFiacreVariable(var,flexoFiacreProgram));
			}
			fiacreProcess.setTechnologyAdapter(technologyAdapter);
			getFiacreObjects().put(process, fiacreProcess);
		} else {
			fiacreProcess = (FiacreProcess) getFiacreObjects().get(process);
		}
		return fiacreProcess;
	}
	
	public FiacreVariable convertFiacreVariable(LocalVariable var, FiacreProgram flexoFiacreProgram) {
		FiacreVariable fiacreVariable = null;
		if (!getFiacreObjects().containsKey(var)) {
			fiacreVariable = factory.newInstance(FiacreVariable.class);
			fiacreVariable.setFiacreVariable(var);
			fiacreVariable.setTechnologyAdapter(technologyAdapter);
			getFiacreObjects().put(var, fiacreVariable);
		} else {
			fiacreVariable = (FiacreVariable) getFiacreObjects().get(var);
		}
		return fiacreVariable;
	}
	
	public FiacreComponent convertFiacreComponent(ComponentDecl component, FiacreProgram flexoFiacreProgram) {
		FiacreComponent fiacreComponent = null;
		if (!getFiacreObjects().containsKey(component)) {
			fiacreComponent = factory.newInstance(FiacreComponent.class);
			fiacreComponent.setFiacreComponent(component);
			if(component.getBody()!=null){
				if(component.getBody() instanceof Par){
					Par body = (Par)component.getBody();
					for(InterfacedComp comp :body.getArgList()){
						if(comp.getComposition()!=null && comp.getComposition() instanceof Instance){
							Instance instance = (Instance)comp.getComposition();
							if(instance.getType() instanceof ProcessDecl){
								ProcessDecl process =  (ProcessDecl) instance.getType();
								fiacreComponent.addToFiacreProcesses((FiacreProcess) getFiacreObjects().get(process));
							}
						}
					}
				}
			}
			fiacreComponent.setTechnologyAdapter(technologyAdapter);
			getFiacreObjects().put(component, fiacreComponent);
		} else {
			fiacreComponent = (FiacreComponent) getFiacreObjects().get(component);
		}
		return fiacreComponent;
	}

	public FiacreState convertFiacreState(State state, FiacreProgram flexoFiacreProgram) {
		FiacreState fiacreState = null;
		if (!getFiacreObjects().containsKey(state)) {
			fiacreState = factory.newInstance(FiacreState.class);
			fiacreState.setTechnologyAdapter(technologyAdapter);
			fiacreState.setFiacreState(state);
			getFiacreObjects().put(state, fiacreState);
		} else {
			fiacreState = (FiacreState) getFiacreObjects().get(state);
		}
		return fiacreState;
	}
	
	public Map<Object, FiacreObject> getFiacreObjects() {
		return fiacreObjects;
	}

}
