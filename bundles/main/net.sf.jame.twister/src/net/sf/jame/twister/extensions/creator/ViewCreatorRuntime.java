/*
 * JAME 6.2
 * http://jame.sourceforge.net
 *
 * Copyright 2001, 2015 Andrea Medeghini
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
package net.sf.jame.twister.extensions.creator;

import net.sf.jame.core.creator.extension.CreatorExtensionRuntime;
import net.sf.jame.core.scripting.JSException;
import net.sf.jame.core.util.DoubleVector4D;
import net.sf.jame.core.util.IntegerVector4D;
import net.sf.jame.twister.util.View;

/**
 * @author Andrea Medeghini
 */
public class ViewCreatorRuntime extends CreatorExtensionRuntime {
	/**
	 * @see net.sf.jame.core.creator.extension.CreatorExtensionRuntime#create(java.lang.Object[])
	 */
	@Override
	public Object create(final Object... args) throws JSException {
		try {
			return new View((IntegerVector4D) args[0], (DoubleVector4D) args[1], (DoubleVector4D) args[2]);
		}
		catch (Exception e) {
			throw new JSException("View creator requires 3 arguments: #1 of type IntegerVector4D[], #2 of type DoubleVector4D[], #3 of type DoubleVector4D[]", e);
		}
	}
}
