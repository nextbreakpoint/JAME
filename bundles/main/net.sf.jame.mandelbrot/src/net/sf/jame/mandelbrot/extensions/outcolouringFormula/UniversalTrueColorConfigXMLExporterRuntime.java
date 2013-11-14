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
package net.sf.jame.mandelbrot.extensions.outcolouringFormula;

import net.sf.jame.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRuntime;
import net.sf.jame.core.xml.XMLExportException;
import net.sf.jame.core.xml.XMLExporter;
import net.sf.jame.core.xml.XMLNodeBuilder;
import net.sf.jame.mandelbrot.colorRenderer.ColorRendererConfigElementXMLExporter;

import org.w3c.dom.Element;

/**
 * @author Andrea Medeghini
 */
public class UniversalTrueColorConfigXMLExporterRuntime extends ExtensionConfigXMLExporterExtensionRuntime {
	/**
	 * @see net.sf.jame.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRuntime#createXMLExporter()
	 */
	@Override
	public XMLExporter<UniversalTrueColorConfig> createXMLExporter() {
		return new UniversalTrueColorConfigXMLExporter();
	}

	private class UniversalTrueColorConfigXMLExporter extends AbstractOutcolouringFormulaConfigXMLExporter<UniversalTrueColorConfig> {
		/**
		 * @see net.sf.jame.mandelbrot.extensions.outcolouringFormula.AbstractOutcolouringFormulaConfigXMLExporter#getConfigElementClassId()
		 */
		@Override
		protected String getConfigElementClassId() {
			return "UniversalTrueColorConfig";
		}

		/**
		 * @see net.sf.jame.mandelbrot.extensions.outcolouringFormula.AbstractOutcolouringFormulaConfigXMLExporter#exportProperties(net.sf.jame.mandelbrot.outcolouringFormula.extension.OutcolouringFormulaExtensionConfig, org.w3c.dom.Element, net.sf.jame.core.xml.XMLNodeBuilder)
		 */
		@Override
		protected void exportProperties(final UniversalTrueColorConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			exportColorRenderers(config, createProperty(builder, element, "colorRenderers"), builder);
		}

		/**
		 * @param config
		 * @param element
		 * @param builder
		 * @throws XMLExportException
		 */
		protected void exportColorRenderers(final UniversalTrueColorConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new ColorRendererConfigElementXMLExporter().exportToElement(config.getColorRendererElement(0), builder));
			element.appendChild(new ColorRendererConfigElementXMLExporter().exportToElement(config.getColorRendererElement(1), builder));
			element.appendChild(new ColorRendererConfigElementXMLExporter().exportToElement(config.getColorRendererElement(2), builder));
			element.appendChild(new ColorRendererConfigElementXMLExporter().exportToElement(config.getColorRendererElement(3), builder));
		}
	}
}