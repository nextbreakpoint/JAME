/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.cfdg.shapeAdjustment.extension;

import net.sf.jame.contextfree.renderer.ContextFreeState;
import net.sf.jame.core.extension.ConfigurableExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public abstract class ShapeAdjustmentExtensionRuntime<T extends ShapeAdjustmentExtensionConfig> extends ConfigurableExtensionRuntime<T> {
	/**
	 * @param state
	 */
	public abstract void update(ContextFreeState state);

	/**
	 * @param state
	 * @param times
	 */
	public abstract void load(ContextFreeState state, int times);
}