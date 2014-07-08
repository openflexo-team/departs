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
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.menu.FlexoMenuBar;
import org.openflexo.module.traceanalysis.controller.TAEController;
import org.openflexo.module.traceanalysis.TraceAnalysisModule;

/**
 * Class representing menus related to traceAnalysis window
 * 
 * @author vincent leilde
 */
public class TAEMenuBar extends FlexoMenuBar {

	private TAEFileMenu _fileMenu;

	public TAEMenuBar(TAEController controller) {
		super(controller, TraceAnalysisModule.INSTANCE);
	}
	
	/**
	 * Build if required and return ${moduleTrigam} 'File' menu. This method overrides the default one defined on superclass
	 * 
	 * @param controller
	 * @return a FileMenu instance
	 */
	@Override
	public TAEFileMenu getFileMenu(FlexoController controller) {
		if (_fileMenu == null) {
			_fileMenu = new TAEFileMenu((TAEController) controller);
		}
		return _fileMenu;
	}


}
