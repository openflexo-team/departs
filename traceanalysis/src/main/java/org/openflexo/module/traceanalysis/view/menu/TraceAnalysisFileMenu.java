/*
 * (c) Copyright 2014- Openflexo
 *
 * This file is part of OpenFlexo.
 *
 * OpenFlexo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenFlexo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenFlexo. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.openflexo.module.traceanalysis.view.menu;


import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import org.openflexo.FlexoCst;
import org.openflexo.components.NewProjectComponent;
import org.openflexo.fib.FIBLibrary;
import org.openflexo.fib.controller.FIBDialog;
import org.openflexo.fib.model.FIBComponent;
import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.nature.ProjectNature;
import org.openflexo.foundation.utils.ProjectInitializerException;
import org.openflexo.icon.IconLibrary;
import org.openflexo.view.FlexoFrame;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.FlexoFIBController;
import org.openflexo.view.menu.FileMenu;
import org.openflexo.view.menu.FlexoMenuItem;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import org.openflexo.module.NatureSpecificModule;
import org.openflexo.module.traceanalysis.TraceAnalysisCst;
import org.openflexo.module.traceanalysis.controller.TraceAnalysisController;
import org.openflexo.module.traceanalysis.controller.action.ImportAction;
import org.openflexo.module.traceanalysis.model.TraceAnalysisProject;
import org.openflexo.module.traceanalysis.model.action.CreateTraceAnalysisProject;


/**
 * 'File' menu for this Module
 * 
 * @author vincent leilde
 */

public class TraceAnalysisFileMenu extends FileMenu {

	private static final Logger logger = Logger.getLogger(TraceAnalysisFileMenu.class.getPackage().getName());

	public TraceAnalysisFileMenu(TraceAnalysisController controller) {
		super(controller);
	}

	public TraceAnalysisController getTraceAnalysisController() {
		return (TraceAnalysisController) getController();
	}

	@Override
	public void addSpecificItems() {
		add(new SaveModifiedItem());
		addSeparator();
		add(new ImportItem());
		addSeparator();
	}

	@Override
	public void quit() {
		if (getTraceAnalysisController().getApplicationContext().getResourceManager().getUnsavedResources().size() == 0) {
			super.quit();
		} else if (getTraceAnalysisController().reviewModifiedResources()) {
			super.quit();
		}

	}

	public class ImportItem extends FlexoMenuItem {
		public ImportItem() {
			super(new ImportAction(), "Import", KeyStroke.getKeyStroke(KeyEvent.VK_I, FlexoCst.META_MASK), getController(), true);
			setIcon(IconLibrary.SAVE_ICON);
		}
	}
	
	public class SaveModifiedItem extends FlexoMenuItem {
		public SaveModifiedItem() {
			super(new SaveModifiedAction(), "save", KeyStroke.getKeyStroke(KeyEvent.VK_S, FlexoCst.META_MASK), getController(), true);
			setIcon(IconLibrary.SAVE_ICON);
		}
	}
	
	public class SaveModifiedAction extends AbstractAction {
		public SaveModifiedAction() {
			super();
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			getTraceAnalysisController().reviewModifiedResources();
		}
	}

}