/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.extensions.shapeReplacement;

import net.sf.jame.contextfree.cfdg.shapeAdjustment.ShapeAdjustmentConfigElement;
import net.sf.jame.contextfree.cfdg.shapeAdjustment.ShapeAdjustmentRuntimeElement;
import net.sf.jame.contextfree.cfdg.shapeReplacement.extension.ShapeReplacementExtensionRuntime;
import net.sf.jame.contextfree.renderer.ContextFreeContext;
import net.sf.jame.contextfree.renderer.ContextFreeLimits;
import net.sf.jame.contextfree.renderer.ContextFreeNode;
import net.sf.jame.contextfree.renderer.ContextFreeState;
import net.sf.jame.core.config.ListConfigElement;
import net.sf.jame.core.config.ListRuntimeElement;
import net.sf.jame.core.config.ValueChangeEvent;
import net.sf.jame.core.config.ValueChangeListener;
import net.sf.jame.core.config.ValueConfigElement;

/**
 * @author Andrea Medeghini
 */
public class SingleShapeReplacementRuntime<T extends SingleShapeReplacementConfig> extends ShapeReplacementExtensionRuntime<T> {
	private String shape;
	private ShapeListener shapeListener;
	private ListRuntimeElement<ShapeAdjustmentRuntimeElement> shapeAdjustmentListElement;
	private ShapeAdjustmentListElementListener shapeAdjustmentListElementListener;

	/**
	 * @see net.sf.jame.core.extension.ConfigurableExtensionRuntime#configReloaded()
	 */
	@Override
	public void configReloaded() {
		setShape(getConfig().getShape());
		shapeListener = new ShapeListener();
		getConfig().getShapeElement().addChangeListener(shapeListener);
		shapeAdjustmentListElement = new ListRuntimeElement<ShapeAdjustmentRuntimeElement>();
		for (int i = 0; i < getConfig().getShapeAdjustmentConfigElementCount(); i++) {
			shapeAdjustmentListElement.appendElement(new ShapeAdjustmentRuntimeElement(getConfig().getShapeAdjustmentConfigElement(i)));
		}
		shapeAdjustmentListElementListener = new ShapeAdjustmentListElementListener();
		getConfig().getShapeAdjustmentListElement().addChangeListener(shapeAdjustmentListElementListener);
	}

	@Override
	public void dispose() {
		if ((getConfig() != null) && (shapeListener != null)) {
			getConfig().getShapeElement().removeChangeListener(shapeListener);
		}
		shapeListener = null;
		if ((getConfig() != null) && (shapeAdjustmentListElementListener != null)) {
			getConfig().getShapeAdjustmentListElement().removeChangeListener(shapeAdjustmentListElementListener);
		}
		shapeAdjustmentListElementListener = null;
		super.dispose();
	}
	
	/**
	 * @return the shape.
	 */
	public String getShape() {
		return shape;
	}

	private void setShape(final String shape) {
		this.shape = shape;
	}
	
	private class ShapeListener implements ValueChangeListener {
		/**
		 * @see net.sf.jame.core.config.ValueChangeListener#valueChanged(net.sf.jame.core.config.ValueChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	/**
	 * Returns a shapeAdjustment element.
	 * 
	 * @param index the shapeAdjustment index.
	 * @return the shapeAdjustment.
	 */
	public ShapeAdjustmentRuntimeElement getShapeAdjustmentElement(final int index) {
		return shapeAdjustmentListElement.getElement(index);
	}

	/**
	 * Returns a shapeAdjustment element index.
	 * 
	 * @param shapeAdjustmentElement the shapeAdjustment element.
	 * @return the index.
	 */
	public int indexOfShapeAdjustmentElement(final ShapeAdjustmentRuntimeElement shapeAdjustmentElement) {
		return shapeAdjustmentListElement.indexOfElement(shapeAdjustmentElement);
	}

	/**
	 * Returns the number of shapeAdjustment elements.
	 * 
	 * @return the number of shapeAdjustment elements.
	 */
	public int getShapeAdjustmentElementCount() {
		return shapeAdjustmentListElement.getElementCount();
	}

	private void setShapeAdjustmentElement(final int index, ShapeAdjustmentRuntimeElement element) {
		shapeAdjustmentListElement.setElement(index, element);
	}

	private void appendShapeAdjustmentElement(final ShapeAdjustmentRuntimeElement shapeAdjustmentElement) {
		shapeAdjustmentListElement.appendElement(shapeAdjustmentElement);
	}

	private void insertShapeAdjustmentElementAfter(final int index, final ShapeAdjustmentRuntimeElement shapeAdjustmentElement) {
		shapeAdjustmentListElement.insertElementAfter(index, shapeAdjustmentElement);
	}

	private void insertShapeAdjustmentElementBefore(final int index, final ShapeAdjustmentRuntimeElement shapeAdjustmentElement) {
		shapeAdjustmentListElement.insertElementBefore(index, shapeAdjustmentElement);
	}

	private void removeShapeAdjustmentElement(final int index) {
		shapeAdjustmentListElement.removeElement(index);
	}

	private void moveUpShapeAdjustmentElement(final int index) {
		shapeAdjustmentListElement.moveElementUp(index);
	}

	private void moveDownShapeAdjustmentElement(final int index) {
		shapeAdjustmentListElement.moveElementDown(index);
	}
	
	private class ShapeAdjustmentListElementListener implements ValueChangeListener {
		/**
		 * @see net.sf.jame.core.config.ValueChangeListener#valueChanged(net.sf.jame.core.config.ValueChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ListConfigElement.ELEMENT_ADDED: {
					appendShapeAdjustmentElement(new ShapeAdjustmentRuntimeElement ((ShapeAdjustmentConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_AFTER: {
					insertShapeAdjustmentElementAfter(((Integer) e.getParams()[1]).intValue(), new ShapeAdjustmentRuntimeElement ((ShapeAdjustmentConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_BEFORE: {
					insertShapeAdjustmentElementBefore(((Integer) e.getParams()[1]).intValue(), new ShapeAdjustmentRuntimeElement ((ShapeAdjustmentConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_REMOVED: {
					removeShapeAdjustmentElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_UP: {
					moveUpShapeAdjustmentElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_DOWN: {
					moveDownShapeAdjustmentElement(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_CHANGED: {
					setShapeAdjustmentElement(((Integer) e.getParams()[1]).intValue(), new ShapeAdjustmentRuntimeElement ((ShapeAdjustmentConfigElement) e.getParams()[0]));
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	
	public ContextFreeNode buildNode(ContextFreeContext context, ContextFreeState state, ContextFreeLimits limits) {
		return new ReplacementContextFreeNode(context, state, limits);
	}
	
	private class ReplacementContextFreeNode extends ContextFreeNode {
		public ReplacementContextFreeNode(ContextFreeContext context, ContextFreeState state, ContextFreeLimits limits) {
			ContextFreeState nodeState = state.clone(); 
			for (int i = 0; i < shapeAdjustmentListElement.getElementCount(); i++) {
				ShapeAdjustmentRuntimeElement shapeAdjustmentRuntime = shapeAdjustmentListElement.getElement(i);
				shapeAdjustmentRuntime.configureState(nodeState, 0);
			}
			ContextFreeNode child = context.buildPathOrRuleNode(nodeState, limits, shape);
			if (child != null) {
				addChild(child);
			}
		}
	}
}