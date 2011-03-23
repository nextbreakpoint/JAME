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
package net.sf.jame.mandelbrot.extensions.fractal.rendering;

import net.sf.jame.core.xml.XMLExportException;
import net.sf.jame.core.xml.XMLExporter;
import net.sf.jame.core.xml.XMLNodeBuilder;
import net.sf.jame.core.xml.extension.ExtensionConfigXMLExporterExtensionRuntime;
import net.sf.jame.mandelbrot.common.ExponentElementXMLExporter;

import org.w3c.dom.Element;

/**
 * @author Andrea Medeghini
 */
public class ZNConfigXMLExporterRuntime extends ExtensionConfigXMLExporterExtensionRuntime {
	/**
	 * @see net.sf.jame.core.xml.extension.ExtensionConfigXMLExporterExtensionRuntime#createXMLExporter()
	 */
	@Override
	public XMLExporter<ZNConfig> createXMLExporter() {
		return new ZNConfigXMLExporter();
	}

	private class ZNConfigXMLExporter extends AbstractRenderingFormulaConfigXMLExporter<ZNConfig> {
		/**
		 * @see net.sf.jame.mandelbrot.extensions.fractal.rendering.AbstractRenderingFormulaConfigXMLExporter#exportProperties(net.sf.jame.mandelbrot.fractal.rendering.extension.RenderingFormulaExtensionConfig, org.w3c.dom.Element, net.sf.jame.core.xml.XMLNodeBuilder)
		 */
		@Override
		protected void exportProperties(final ZNConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			super.exportProperties(config, element, builder);
			exportExponent(config, createProperty(builder, element, "exponent"), builder);
		}

		/**
		 * @param config
		 * @param element
		 * @param builder
		 * @throws XMLExportException
		 */
		protected void exportExponent(final ZNConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new ExponentElementXMLExporter().exportToElement(config.getExponentElement(), builder));
		}

		/**
		 * @see net.sf.jame.mandelbrot.extensions.fractal.rendering.AbstractRenderingFormulaConfigXMLExporter#getConfigElementClassId()
		 */
		@Override
		protected String getConfigElementClassId() {
			return "ZNConfig";
		}
	}
}
