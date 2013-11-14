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
package net.sf.jame.core.tree;

/**
 * @author Andrea Medeghini
 */
public abstract class DefaultNode extends Node {
	/**
	 * Constructs a new node.
	 * 
	 * @param nodeId the nodeId.
	 */
	public DefaultNode(final String nodeId) {
		super(nodeId);
	}

	/**
	 * Constructs a new node.
	 * 
	 * @param nodeId the nodeId.
	 * @param value the node value.
	 */
	public DefaultNode(final String nodeId, final NodeValue<?> value) {
		this(nodeId);
		setNodeValue(value);
		accept();
	}

	/**
	 * @see net.sf.jame.core.tree.Node#isEditable()
	 */
	@Override
	public boolean isEditable() {
		return true;
	}

	/**
	 * Returns the node editor.
	 * 
	 * @return the node editor.
	 */
	@Override
	public NodeEditor getNodeEditor() {
		if (super.getNodeEditor() == null) {
			setNodeEditor(createNodeEditor());
		}
		return super.getNodeEditor();
	}

	/**
	 * Creates the node editor.
	 * 
	 * @return the node editor.
	 */
	protected abstract NodeEditor createNodeEditor();

	/**
	 * @see net.sf.jame.core.tree.Node#getValueAsString()
	 */
	@Override
	public String getValueAsString() {
		return getNodeValue() != null ? getNodeValue().toString() : "";
	}
}