/*
 * JAME 6.1 
 * http://jame.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
 *
 * This file is part of JAME.
 *
 * JAME is an application for creating fractals and other graphics artifacts.
 *
 * JAME is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JAME is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JAME.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package net.sf.jame.core.media;

import java.awt.geom.Point2D;

public final class EngineMouseEvent extends EngineEvent {
	public static final int RELEASED = 0;
	public static final int PRESSED = 1;
	public static final int DRAGGED = 2;
	public static final int MOVED = 3;
	Point2D.Float mouse;
	int event;

	public EngineMouseEvent(final int event, final Point2D.Float mouse) {
		this.mouse = mouse;
		this.event = event;
	}

	@Override
	public String toString() {
		return "EngineMouseEvent[" + event + "," + mouse.x + "," + mouse.y + "]";
	}

	/**
	 * @return the mouse
	 */
	public Point2D.Float getMouse() {
		return mouse;
	}

	/**
	 * @return the event
	 */
	public int getEvent() {
		return event;
	}
}