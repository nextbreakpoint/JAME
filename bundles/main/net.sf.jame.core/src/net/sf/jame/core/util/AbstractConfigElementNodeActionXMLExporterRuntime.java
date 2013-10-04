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

import net.sf.jame.core.config.ConfigElement;
import net.sf.jame.core.tree.NodeActionValue;
import net.sf.jame.core.xml.XMLExportException;
import net.sf.jame.core.xml.XMLExporter;
import net.sf.jame.core.xml.XMLNodeBuilder;

import org.w3c.dom.Element;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractConfigElementNodeActionXMLExporterRuntime<T extends ConfigElement> extends AbstractActionXMLExporterRuntime {
	/**
	 * @see net.sf.jame.core.util.AbstractActionXMLExporterRuntime#exportParams(net.sf.jame.core.tree.NodeActionValue, org.w3c.dom.Element, net.sf.jame.core.xml.XMLNodeBuilder)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void exportParams(final NodeActionValue action, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		final XMLExporter<T> exporter = this.createExporter();
		final T configElement0 = (T) action.getActionParams()[0];
		final T configElement1 = (T) action.getActionParams()[1];
		element.appendChild(exporter.exportToElement(configElement0, builder));
		element.appendChild(exporter.exportToElement(configElement1, builder));
	}

	/**
	 * @return
	 */
	protected abstract XMLExporter<T> createExporter();
}
