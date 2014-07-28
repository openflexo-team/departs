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

public class MessageInRoute {

	private final OBPRoute route;
	private final Message message;

	public MessageInRoute(Message message, OBPRoute route) {
		this.route = route;
		this.message = message;
	}

	public OBPRoute getRoute() {
		return route;
	}

	public Message getMessage() {
		return message;
	}

	public ComponentInRoute getStartComponent() {
		return route.getComponentInRoute(message.getFromComponent());
	}

	public OBPConfigurationInRoute getStartConfiguration() {
		return route.getOBPConfigurationInRoute(message.getTransition().getPreviousConfiguration());
	}

	public ComponentInRoute getEndComponent() {
		return route.getComponentInRoute(message.getToComponent());
	}

	public OBPConfigurationInRoute getEndConfiguration() {
		return route.getOBPConfigurationInRoute(message.getTransition().getNextConfiguration());
	}

}
