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
package net.sf.jame.core.extensions.creator;

import net.sf.jame.core.scripting.JSException;
import net.sf.jame.core.scripting.extension.CreatorExtensionRuntime;
import net.sf.jame.core.util.Color32bit;

/**
 * @author Andrea Medeghini
 */
public class ColorCreatorRuntime extends CreatorExtensionRuntime {
	/**
	 * @see net.sf.jame.core.scripting.extension.CreatorExtensionRuntime#create(java.lang.Object[])
	 */
	@Override
	public Object create(final Object... args) throws JSException {
		try {
			return Color32bit.valueOf((String) args[0]);
		}
		catch (Exception e) {
			throw new JSException("Color creator requires one arguments: #1 of type String", e);
		}
	}
}
