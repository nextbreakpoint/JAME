/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.cfdg.pathAdjustment;

import java.util.List;

import net.sf.jame.contextfree.ContextFreeRegistry;
import net.sf.jame.contextfree.cfdg.pathAdjustment.extension.PathAdjustmentExtensionConfig;
import net.sf.jame.core.common.ConfigurableExtensionReferenceElement;
import net.sf.jame.core.common.ConfigurableExtensionReferenceElementXMLImporter;
import net.sf.jame.core.extension.ExtensionException;
import net.sf.jame.core.xml.XMLImportException;
import net.sf.jame.core.xml.XMLImporter;

import org.w3c.dom.Element;

/**
 * @author Andrea Medeghini
 */
public class PathAdjustmentConfigElementXMLImporter extends XMLImporter<PathAdjustmentConfigElement> {
	/**
	 * @see net.sf.jame.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
	 */
	@Override
	public PathAdjustmentConfigElement importFromElement(final Element element) throws XMLImportException {
		checkClassId(element, PathAdjustmentConfigElement.CLASS_ID);
		final PathAdjustmentConfigElement configElement = new PathAdjustmentConfigElement();
		final List<Element> propertyElements = getProperties(element);
		if (propertyElements.size() == 1) {
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
	protected void importProperties(final PathAdjustmentConfigElement configElement, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
		importExtension(configElement, propertyElements.get(0));
	}

	private void importExtension(final PathAdjustmentConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> extensionElements = this.getElements(element, ConfigurableExtensionReferenceElement.CLASS_ID);
		if (extensionElements.size() == 1) {
			configElement.setExtensionReference(new ConfigurableExtensionReferenceElementXMLImporter<PathAdjustmentExtensionConfig>(ContextFreeRegistry.getInstance().getPathAdjustmentRegistry()).importFromElement(extensionElements.get(0)).getReference());
		}
	}
}
