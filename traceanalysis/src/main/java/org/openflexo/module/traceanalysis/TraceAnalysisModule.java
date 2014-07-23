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

package org.openflexo.module.traceanalysis;

import org.openflexo.module.Module;

public class TraceAnalysisModule extends Module<TAModule> {

	public static Module<TAModule> INSTANCE = null;

	public TraceAnalysisModule() {
			
		super(TAModule.TA_MODULE_NAME, TAModule.TA_MODULE_NAME, TAModule.class, TraceAnalysisPreferences.class, "",
				null , "TA", TraceAnalysisIconLibrary.TA_SMALL_ICON, TraceAnalysisIconLibrary.TA_MEDIUM_ICON, TraceAnalysisIconLibrary.TA_MEDIUM_ICON,
				TraceAnalysisIconLibrary.TA_BIG_ICON);

		INSTANCE = this;
	}


}


