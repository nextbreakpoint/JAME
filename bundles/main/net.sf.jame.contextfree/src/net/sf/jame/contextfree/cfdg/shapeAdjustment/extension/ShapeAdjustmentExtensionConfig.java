/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.cfdg.shapeAdjustment.extension;

import java.util.ArrayList;
import java.util.List;
import net.sf.jame.core.config.ConfigElement;
import net.sf.jame.core.extension.ExtensionConfig;


/**
 * @author Andrea Medeghini
 */
public abstract class ShapeAdjustmentExtensionConfig extends ExtensionConfig {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
	}

	/**
	 * @see net.sf.jame.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		return elements;
	}


	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		final ShapeAdjustmentExtensionConfig other = (ShapeAdjustmentExtensionConfig) obj;
		return true;
	}

}
