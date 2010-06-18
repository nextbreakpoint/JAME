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
package net.sf.jame.contextfree.extensions.pathAdjustment;

import net.sf.jame.contextfree.cfdg.pathAdjustment.extension.PathAdjustmentExtensionRuntime;
import net.sf.jame.contextfree.renderer.ContextFreeState;

/**
 * @author Andrea Medeghini
 *
 */
public class TargetHuePathAdjustmentRuntime extends PathAdjustmentExtensionRuntime<TargetHuePathAdjustmentConfig> {
	/**
	 * @see net.sf.jame.contextfree.cfdg.pathAdjustment.extension.PathAdjustmentExtensionRuntime#configureState(net.sf.jame.contextfree.renderer.ContextFreeState, int)
	 */
	@Override
	public void configureState(ContextFreeState state, int times) {
		state.setCurrentHue(getConfig().getValue().floatValue());
		// TODO Auto-generated method stub
	}

	/**
	 * @see net.sf.jame.contextfree.cfdg.pathAdjustment.extension.PathAdjustmentExtensionRuntime#updateState(net.sf.jame.contextfree.renderer.ContextFreeState, int)
	 */
	@Override
	public void updateState(ContextFreeState state, int time) {
		// TODO Auto-generated method stub
		
	}
}