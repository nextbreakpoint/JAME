/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.extensions.pathReplacement;

import java.util.List;
import net.sf.jame.contextfree.cfdg.pathAdjustment.PathAdjustmentConfigElement;
import net.sf.jame.contextfree.cfdg.pathAdjustment.PathAdjustmentConfigElementXMLImporter;
import net.sf.jame.contextfree.common.StrokeCapElement;
import net.sf.jame.contextfree.common.StrokeCapElementXMLImporter;
import net.sf.jame.contextfree.common.StrokeJoinElement;
import net.sf.jame.contextfree.common.StrokeJoinElementXMLImporter;
import net.sf.jame.contextfree.common.StrokeWidthElement;
import net.sf.jame.contextfree.common.StrokeWidthElementXMLImporter;
import net.sf.jame.core.extension.ExtensionException;
import net.sf.jame.core.xml.XMLImportException;
import net.sf.jame.core.xml.XMLImporter;
import net.sf.jame.core.xml.extension.ExtensionConfigXMLImporterExtensionRuntime;
import org.w3c.dom.Element;

/**
 * @author Andrea Medeghini
 */
public class StrokePathReplacementConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see net.sf.jame.core.xml.extension.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<StrokePathReplacementConfig> createXMLImporter() {
		return new StrokePathReplacementConfigXMLImporter();
	}

	private class StrokePathReplacementConfigXMLImporter extends XMLImporter<StrokePathReplacementConfig> {
		protected StrokePathReplacementConfig createExtensionConfig() {
			return new StrokePathReplacementConfig();
		}

		protected String getConfigElementClassId() {
			return "StrokePathReplacementConfig";
		}
		
		/**
		 * @see net.sf.jame.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public StrokePathReplacementConfig importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, this.getConfigElementClassId());
			final StrokePathReplacementConfig extensionConfig = this.createExtensionConfig();
			final List<Element> propertyElements = getProperties(element);
			if (propertyElements.size() == 4) {
				try {
					importProperties(extensionConfig, propertyElements);
				}
				catch (final ExtensionException e) {
					throw new XMLImportException(e);
				}
			}
			return extensionConfig;
		}
	
		/**
		 * @param extensionConfig
		 * @param propertyElements
		 * @throws ExtensionException
		 * @throws XMLImportException
		 */
		protected void importProperties(final StrokePathReplacementConfig extensionConfig, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
			importWidth(extensionConfig, propertyElements.get(0));
			importCap(extensionConfig, propertyElements.get(1));
			importJoin(extensionConfig, propertyElements.get(2));
			importPathAdjustmentListElement(extensionConfig, propertyElements.get(3));
		}
	
		private void importWidth(final StrokePathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> widthElements = this.getElements(element, StrokeWidthElement.CLASS_ID);
			if (widthElements.size() == 1) {
				extensionConfig.setWidth(new StrokeWidthElementXMLImporter().importFromElement(widthElements.get(0)).getValue());
			}
		}
		private void importCap(final StrokePathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> capElements = this.getElements(element, StrokeJoinElement.CLASS_ID);
			if (capElements.size() == 1) {
				extensionConfig.setCap(new StrokeJoinElementXMLImporter().importFromElement(capElements.get(0)).getValue());
			}
		}
		private void importJoin(final StrokePathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> joinElements = this.getElements(element, StrokeCapElement.CLASS_ID);
			if (joinElements.size() == 1) {
				extensionConfig.setJoin(new StrokeCapElementXMLImporter().importFromElement(joinElements.get(0)).getValue());
			}
		}
		private void importPathAdjustmentListElement(final StrokePathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final PathAdjustmentConfigElementXMLImporter pathAdjustmentImporter = new PathAdjustmentConfigElementXMLImporter();
			final List<Element> pathAdjustmentElements = this.getElements(element, PathAdjustmentConfigElement.CLASS_ID);
			for (int i = 0; i < pathAdjustmentElements.size(); i++) {
				extensionConfig.appendPathAdjustmentConfigElement(pathAdjustmentImporter.importFromElement(pathAdjustmentElements.get(i)));
			}
		}
	}
}
