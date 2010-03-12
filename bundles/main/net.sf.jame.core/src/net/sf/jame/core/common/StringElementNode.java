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
package net.sf.jame.core.common;

import net.sf.jame.core.config.ValueChangeEvent;
import net.sf.jame.core.config.ValueChangeListener;
import net.sf.jame.core.config.ValueConfigElement;
import net.sf.jame.core.tree.AttributeNode;
import net.sf.jame.core.tree.Node;
import net.sf.jame.core.tree.NodeAction;
import net.sf.jame.core.tree.NodeEditor;
import net.sf.jame.core.tree.NodeSession;
import net.sf.jame.core.tree.NodeValue;

/**
 * @author Andrea Medeghini
 */
public abstract class StringElementNode extends AttributeNode {
	public static final String NODE_CLASS = "node.class.StringElement";
	private final ConfigElementListener listener;
	private final ValueConfigElement<String> configElement;

	/**
	 * @param nodeId
	 * @param configElement
	 */
	public StringElementNode(final String nodeId, final ValueConfigElement<String> configElement) {
		super(nodeId);
		setNodeClass(StringElementNode.NODE_CLASS);
		this.configElement = configElement;
		listener = new ConfigElementListener();
		setNodeValue(new StringElementNodeValue(configElement.getValue()));
	}

	/**
	 * @see net.sf.jame.core.tree.Node#dispose()
	 */
	@Override
	public void dispose() {
		if (configElement != null) {
			configElement.removeChangeListener(listener);
		}
		super.dispose();
	}

	/**
	 * @see net.sf.jame.core.tree.Node#setSession(net.sf.jame.core.tree.NodeSession)
	 */
	@Override
	public void setSession(final NodeSession session) {
		if (session != null) {
			configElement.addChangeListener(listener);
		}
		else {
			configElement.removeChangeListener(listener);
		}
		super.setSession(session);
	}

	/**
	 * @see net.sf.jame.core.tree.Node#nodeAdded()
	 */
	@Override
	protected void nodeAdded() {
		setNodeValue(new StringElementNodeValue(configElement.getValue()));
	}

	/**
	 * @see net.sf.jame.core.tree.Node#nodeRemoved()
	 */
	@Override
	protected void nodeRemoved() {
	}

	/**
	 * @see net.sf.jame.core.tree.Node#isEditable()
	 */
	@Override
	public boolean isEditable() {
		return true;
	}

	/**
	 * @see net.sf.jame.core.tree.DefaultNode#getValueAsString()
	 */
	@Override
	public String getValueAsString() {
		if (getNodeValue() != null) {
			String s = (String) getNodeValue().getValue();
			if (s.length() > 30) {
				s = s.substring(0, 20) + "...";
			}
			return s;
		}
		else {
			return "";
		}
	}

	/**
	 * @see net.sf.jame.core.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new StringNodeEditor(this);
	}

	protected class StringNodeEditor extends NodeEditor {
		/**
		 * @param node
		 */
		public StringNodeEditor(final AttributeNode node) {
			super(node);
		}

		/**
		 * @see net.sf.jame.core.tree.NodeEditor#doSetValue(java.lang.NodeValue)
		 */
		@Override
		protected void doSetValue(final NodeValue<?> value) {
			configElement.removeChangeListener(listener);
			configElement.setValue(((StringElementNodeValue) value).getValue());
			configElement.addChangeListener(listener);
		}

		/**
		 * @see net.sf.jame.core.tree.NodeEditor#createChildNode(net.sf.jame.core.tree.NodeValue)
		 */
		@Override
		protected Node createChildNode(final NodeValue<?> value) {
			return null;
		}

		/**
		 * @see net.sf.jame.core.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return StringElementNodeValue.class;
		}

		/**
		 * @see net.sf.jame.core.tree.NodeEditor#createNodeValue(Object)
		 */
		@Override
		public NodeValue<?> createNodeValue(final Object value) {
			return new StringElementNodeValue((String) value);
		}
	}

	protected class ConfigElementListener implements ValueChangeListener {
		public void valueChanged(final ValueChangeEvent e) {
			cancel();
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setNodeValue(new StringElementNodeValue((String) e.getParams()[0]));
					getSession().appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_SET_VALUE, e.getTimestamp(), getNodePath(), e.getParams()[0], e.getParams()[1]));
					break;
				}
				default: {
					break;
				}
			}
		}
	}
}
