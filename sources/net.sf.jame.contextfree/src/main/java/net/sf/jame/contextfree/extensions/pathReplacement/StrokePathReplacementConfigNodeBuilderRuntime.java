/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.extensions.pathReplacement;

import net.sf.jame.contextfree.common.StrokeCapElementNode;
import net.sf.jame.contextfree.common.StrokeJoinElementNode;
import net.sf.jame.contextfree.common.StrokeWidthElementNode;
import net.sf.jame.contextfree.extensions.ContextFreeExtensionResources;
import net.sf.jame.contextfree.pathAdjustment.PathAdjustmentConfigElement;
import net.sf.jame.contextfree.pathAdjustment.PathAdjustmentConfigElementNode;
import net.sf.jame.core.extension.ExtensionConfig;
import net.sf.jame.core.nodeBuilder.extension.NodeBuilderExtensionRuntime;
import net.sf.jame.core.tree.Node;
import net.sf.jame.core.tree.NodeBuilder;
import net.sf.jame.core.tree.NodeValue;
import net.sf.jame.core.util.AbstractConfigElementListNode;
import net.sf.jame.core.util.AbstractConfigElementNode;
import net.sf.jame.core.util.AbstractExtensionConfigNodeBuilder;
import net.sf.jame.core.util.ConfigElementListNodeValue;

/**
 * @author Andrea Medeghini
 */
public class StrokePathReplacementConfigNodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see net.sf.jame.core.nodeBuilder.extension.NodeBuilderExtensionRuntime#createNodeBuilder(net.sf.jame.core.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((StrokePathReplacementConfig) config);
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<StrokePathReplacementConfig> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final StrokePathReplacementConfig config) {
			super(config);
		}

		/**
		 * @see net.sf.jame.core.util.AbstractExtensionConfigNodeBuilder#createNodes(net.sf.jame.core.tree.Node)
		 */
		@Override
		public void createNodes(final Node parentNode) {
			parentNode.appendChildNode(new WidthElementNode(getConfig()));
			parentNode.appendChildNode(new CapElementNode(getConfig()));
			parentNode.appendChildNode(new JoinElementNode(getConfig()));
			parentNode.appendChildNode(new PathAdjustmentListElementNode(getConfig()));
		}

		private class WidthElementNode extends StrokeWidthElementNode {
			/**
			 * @param config
			 */
			public WidthElementNode(final StrokePathReplacementConfig config) {
				super(config.getExtensionId() + ".width", config.getWidthElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.WidthElement"));
			}
		}
		private class CapElementNode extends StrokeJoinElementNode {
			/**
			 * @param config
			 */
			public CapElementNode(final StrokePathReplacementConfig config) {
				super(config.getExtensionId() + ".cap", config.getCapElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.CapElement"));
			}
		}
		private class JoinElementNode extends StrokeCapElementNode {
			/**
			 * @param config
			 */
			public JoinElementNode(final StrokePathReplacementConfig config) {
				super(config.getExtensionId() + ".join", config.getJoinElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.JoinElement"));
			}
		}
		private class PathAdjustmentListElementNode extends AbstractConfigElementListNode<PathAdjustmentConfigElement> {
			public static final String NODE_CLASS = "node.class.PathAdjustmentListElement";
			
			/**
			 * @param config
			 */
			public PathAdjustmentListElementNode(final StrokePathReplacementConfig config) {
				super(config.getExtensionId() + ".pathAdjustmentList", config.getPathAdjustmentListElement());
				setNodeClass(PathAdjustmentListElementNode.NODE_CLASS);
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.PathAdjustmentListElement"));
			}

			/**
			 * @see net.sf.jame.core.util.AbstractConfigElementListNode#createChildNode(net.sf.jame.core.config.ConfigElement)
			 */
			@Override
			protected AbstractConfigElementNode<PathAdjustmentConfigElement> createChildNode(final PathAdjustmentConfigElement value) {
				return new PathAdjustmentConfigElementNode(value);
			}
	
			/**
			 * @see net.sf.jame.core.util.AbstractConfigElementListNode#getChildValueType()
			 */
			@Override
			public Class<?> getChildValueType() {
				return PathAdjustmentConfigElementNodeValue.class;
			}
	
			/**
			 * @see net.sf.jame.core.util.AbstractConfigElementListNode#createNodeValue(Object)
			 */
			@Override
			public NodeValue<PathAdjustmentConfigElement> createNodeValue(final Object value) {
				return new PathAdjustmentConfigElementNodeValue((PathAdjustmentConfigElement) value);
			}
	
			private class PathAdjustmentConfigElementNodeValue extends ConfigElementListNodeValue<PathAdjustmentConfigElement> {
				private static final long serialVersionUID = 1L;
	
				/**
				 * @param value
				 */
				public PathAdjustmentConfigElementNodeValue(final PathAdjustmentConfigElement value) {
					super(value);
				}
			}
		}
	}
}