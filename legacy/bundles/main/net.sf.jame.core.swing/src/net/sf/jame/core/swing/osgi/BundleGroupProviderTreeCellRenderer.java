/*
 * JAME 6.2
 * http://jame.sourceforge.net
 *
 * Copyright 2001, 2015 Andrea Medeghini
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
package net.sf.jame.core.swing.osgi;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.eclipse.core.runtime.IBundleGroup;
import org.eclipse.core.runtime.IBundleGroupProvider;

/**
 * A cell renderer for bundle group providers trees.
 * 
 * @author Andrea Medeghini
 */
public class BundleGroupProviderTreeCellRenderer extends DefaultTreeCellRenderer {
	private static final long serialVersionUID = 1L;

	/**
	 * @see javax.swing.tree.TreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
	 */
	@Override
	public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean selected, final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		if (node.getUserObject() != null) {
			if (node.getUserObject() instanceof IBundleGroupProvider) {
				return super.getTreeCellRendererComponent(tree, ((IBundleGroupProvider) node.getUserObject()).getName(), selected, expanded, leaf, row, hasFocus);
			}
			else if (node.getUserObject() instanceof IBundleGroup) {
				return super.getTreeCellRendererComponent(tree, ((IBundleGroup) node.getUserObject()).getName(), selected, expanded, leaf, row, hasFocus);
			}
			else if (node.getUserObject() != null) {
				return super.getTreeCellRendererComponent(tree, node.getUserObject().toString(), selected, expanded, leaf, row, hasFocus);
			}
		}
		return super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
	}
}