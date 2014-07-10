/*
 * (c) Copyright 2013 Openflexo
 *
 * This file is Seqt of OpenFlexo.
 *
 * OpenFlexo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenFlexo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A SeqTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenFlexo. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.openflexo.technologyadapter.cdl.model;

import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;

@ModelEntity
@ImplementationClass(CDLSeqActivity.CDLSeqActivityImpl.class)
@XMLElement(xmlTag = "CDLSeqActivity")
public interface CDLSeqActivity extends CDLTopActivity {
	
	public static final String SEQ_ACTIVITY_KEY = "SeqActivity";
	
	@Getter(value=SEQ_ACTIVITY_KEY , ignoreType = true)
	public obp.cdl.SeqActivity getSeqActivity();
	@Setter(SEQ_ACTIVITY_KEY)
	public void setSeqActivity(obp.cdl.SeqActivity seqActivity);
	
	public static abstract class CDLSeqActivityImpl extends CDLTopActivityImpl implements CDLSeqActivity {
		
		public CDLSeqActivityImpl() {
			// TODO Auto-generated constructor stub
		}
		
	}

}