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

package org.openflexo.traceanalysis;

public class Message {

	private final String messageLabel;
	private final Component fromComponent, toComponent;
	private final OBPTransition transition;

	public Message(String messageLabel, Component from, Component to, OBPTransition transition) {
		this.messageLabel = messageLabel;
		this.fromComponent = from;
		this.toComponent = to;
		this.transition = transition;
	}

	public String getMessageLabel() {
		return messageLabel;
	}

	public OBPTransition getTransition() {
		return transition;
	}

	public Component getFromComponent() {
		return fromComponent;
	}

	public Component getToComponent() {
		return toComponent;
	}

}
