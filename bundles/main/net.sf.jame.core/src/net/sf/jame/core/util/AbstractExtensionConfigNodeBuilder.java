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
package net.sf.jame.core.util;

import net.sf.jame.core.extension.ExtensionConfig;
import net.sf.jame.core.tree.Node;
import net.sf.jame.core.tree.NodeBuilder;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractExtensionConfigNodeBuilder<T extends ExtensionConfig> implements NodeBuilder {
	private final T config;

	/**
	 * @param config
	 */
	public AbstractExtensionConfigNodeBuilder(final T config) {
		this.config = config;
	}

	/**
	 * @return the config
	 */
	protected T getConfig() {
		return this.config;
	}

	/**
	 * @see net.sf.jame.core.tree.NodeBuilder#createNodes(Node)
	 */
	public void createNodes(final Node parentNode) {
	}
}
