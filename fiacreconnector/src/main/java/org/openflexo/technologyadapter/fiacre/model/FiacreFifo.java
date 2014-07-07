/*
 * (c) Copyright 2013 Openflexo
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

package org.openflexo.technologyadapter.fiacre.model;

import org.openflexo.foundation.resource.ResourceData;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;

@ModelEntity
@ImplementationClass(FiacreFifo.FiacreFifoImpl.class)
@XMLElement(xmlTag = "FiacreFifo")
public interface FiacreFifo extends FiacreObject, ResourceData<FiacreFifo> {

	public static final String FIACRE_FIFO_KEY = "FiacreFifo";

	public obp.fiacre.model.Queue getFiacreFifo();

	public void setFiacreFifo(obp.fiacre.model.Queue fiacreFifo);

	public static abstract class FiacreFifoImpl extends FiacreObjectImpl implements FiacreFifo {

		public FiacreFifoImpl() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public String getFiacreCode() {
			return "Fifo" + getName();
		}

		@Override
		public String getUri() {
			return getName();
		}

	}

}