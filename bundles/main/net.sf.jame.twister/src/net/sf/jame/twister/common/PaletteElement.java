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
package net.sf.jame.twister.common;

import net.sf.jame.core.config.ConfigElement;
import net.sf.jame.core.config.ValueConfigElement;
import net.sf.jame.core.util.Palette;

/**
 * @author Andrea Medeghini
 */
public class PaletteElement<T extends Palette> extends ValueConfigElement<T> {
	private static final long serialVersionUID = 1L;

	/**
	 * @param defaultValue
	 */
	public PaletteElement(final String classId, final T defaultValue) {
		super(classId, defaultValue);
	}

	/**
	 * @see net.sf.jame.core.config.ValueConfigElement#clone()
	 */
	@Override
	public PaletteElement<T> clone() {
		return new PaletteElement<T>(getClassId(), getValue());
	}

	/**
	 * @see net.sf.jame.core.config.ConfigElement#copyFrom(net.sf.jame.core.config.ConfigElement)
	 */
	@SuppressWarnings("unchecked")
	public void copyFrom(ConfigElement source) {
		PaletteElement<T> element = (PaletteElement<T>) source;
		setValue(element.getValue());
	}
}
