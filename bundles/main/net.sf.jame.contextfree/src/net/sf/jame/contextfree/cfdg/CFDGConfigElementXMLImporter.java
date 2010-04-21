/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.cfdg;

import java.util.List;

import net.sf.jame.contextfree.cfdg.figure.FigureConfigElement;
import net.sf.jame.contextfree.cfdg.figure.FigureConfigElementXMLImporter;
import net.sf.jame.core.common.ColorElement;
import net.sf.jame.core.common.ColorElementXMLImporter;
import net.sf.jame.core.common.FloatElement;
import net.sf.jame.core.common.FloatElementXMLImporter;
import net.sf.jame.core.common.StringElement;
import net.sf.jame.core.common.StringElementXMLImporter;
import net.sf.jame.core.extension.ExtensionException;
import net.sf.jame.core.xml.XMLImportException;
import net.sf.jame.core.xml.XMLImporter;

import org.w3c.dom.Element;

public class CFDGConfigElementXMLImporter extends XMLImporter<CFDGConfigElement> {
	/**
	 * @see net.sf.jame.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
	 */
	@Override
	public CFDGConfigElement importFromElement(final Element element) throws XMLImportException {
		checkClassId(element, CFDGConfigElement.CLASS_ID);
		final CFDGConfigElement configElement = new CFDGConfigElement();
		final List<Element> propertyElements = getProperties(element);
		if (propertyElements.size() == 9) {
			try {
				importProperties(configElement, propertyElements);
			}
			catch (final ExtensionException e) {
				throw new XMLImportException(e);
			}
		}
		return configElement;
	}

	/**
	 * @param configElement
	 * @param propertyElements
	 * @throws ExtensionException
	 * @throws XMLImportException
	 */
	protected void importProperties(final CFDGConfigElement configElement, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
		importStartshape(configElement, propertyElements.get(0));
		importX(configElement, propertyElements.get(1));
		importY(configElement, propertyElements.get(2));
		importWidth(configElement, propertyElements.get(3));
		importHeight(configElement, propertyElements.get(4));
		importTileWidth(configElement, propertyElements.get(5));
		importTileHeight(configElement, propertyElements.get(6));
		importBackground(configElement, propertyElements.get(7));
		importFigureListElement(configElement, propertyElements.get(8));
	}

	private void importStartshape(final CFDGConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> startshapeElements = this.getElements(element, StringElement.CLASS_ID);
		if (startshapeElements.size() == 1) {
			configElement.setStartshape(new StringElementXMLImporter().importFromElement(startshapeElements.get(0)).getValue());
		}
	}
	private void importX(final CFDGConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> xElements = this.getElements(element, FloatElement.CLASS_ID);
		if (xElements.size() == 1) {
			configElement.setX(new FloatElementXMLImporter().importFromElement(xElements.get(0)).getValue());
		}
	}
	private void importY(final CFDGConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> yElements = this.getElements(element, FloatElement.CLASS_ID);
		if (yElements.size() == 1) {
			configElement.setY(new FloatElementXMLImporter().importFromElement(yElements.get(0)).getValue());
		}
	}
	private void importWidth(final CFDGConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> widthElements = this.getElements(element, FloatElement.CLASS_ID);
		if (widthElements.size() == 1) {
			configElement.setWidth(new FloatElementXMLImporter().importFromElement(widthElements.get(0)).getValue());
		}
	}
	private void importHeight(final CFDGConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> heightElements = this.getElements(element, FloatElement.CLASS_ID);
		if (heightElements.size() == 1) {
			configElement.setHeight(new FloatElementXMLImporter().importFromElement(heightElements.get(0)).getValue());
		}
	}
	private void importTileWidth(final CFDGConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> tileWidthElements = this.getElements(element, FloatElement.CLASS_ID);
		if (tileWidthElements.size() == 1) {
			configElement.setTileWidth(new FloatElementXMLImporter().importFromElement(tileWidthElements.get(0)).getValue());
		}
	}
	private void importTileHeight(final CFDGConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> tileHeightElements = this.getElements(element, FloatElement.CLASS_ID);
		if (tileHeightElements.size() == 1) {
			configElement.setTileHeight(new FloatElementXMLImporter().importFromElement(tileHeightElements.get(0)).getValue());
		}
	}
	private void importBackground(final CFDGConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> backgroundElements = this.getElements(element, ColorElement.CLASS_ID);
		if (backgroundElements.size() == 1) {
			configElement.setBackground(new ColorElementXMLImporter().importFromElement(backgroundElements.get(0)).getValue());
		}
	}
	private void importFigureListElement(final CFDGConfigElement configElement, final Element element) throws XMLImportException {
		final FigureConfigElementXMLImporter figureImporter = new FigureConfigElementXMLImporter();
		final List<Element> figureElements = this.getElements(element, FigureConfigElement.CLASS_ID);
		for (int i = 0; i < figureElements.size(); i++) {
			configElement.appendFigureConfigElement(figureImporter.importFromElement(figureElements.get(i)));
		}
	}
}
