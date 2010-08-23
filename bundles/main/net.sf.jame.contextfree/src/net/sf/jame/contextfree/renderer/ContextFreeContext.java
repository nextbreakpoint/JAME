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
package net.sf.jame.contextfree.renderer;

import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.sf.jame.contextfree.cfdg.CFDGRuntimeElement;
import net.sf.jame.contextfree.cfdg.figure.FigureRuntimeElement;

import org.apache.log4j.Logger;

public class ContextFreeContext {
	private static final double THRESHOLD = 0.0000000001;
	private static final Logger logger = Logger.getLogger(ContextFreeContext.class);
	public static final float MIN_SIZE = 0.5f;
	public static final float MAX_SIZE = 100000;
	private CFDGRuntimeElement runtime;
	private RuleMap ruleMap = new RuleMap();
	private PathMap pathMap = new PathMap();
	private Set<ContextFreeShape> renderSet = new TreeSet<ContextFreeShape>(new ShapeComparator());
	private Set<ContextFreeShape> expandSet = new HashSet<ContextFreeShape>();
	private Set<ContextFreeShape> createdSet = new HashSet<ContextFreeShape>();
	private Set<ContextFreeShape> queueSet = new HashSet<ContextFreeShape>();
	private Set<ContextFreeShape> recursiveSet = new HashSet<ContextFreeShape>();
	private Set<String> buildSet = new LinkedHashSet<String>();

	public ContextFreeContext(CFDGRuntimeElement runtime) {
		this.runtime = runtime;
	}

	public int getRenderCount() {
		return renderSet.size();
	}

	public int getExpandCount() {
		return expandSet.size();
	}
	
	public void renderShape(Graphics2D g2d, ContextFreeArea area) {
		for (ContextFreeShape shape : renderSet) {
			shape.render(g2d, area);
		}
	}

	public boolean expandShapes() {
		renderSet.addAll(createdSet);
		expandSet.addAll(recursiveSet);
		createdSet.clear();
		recursiveSet.clear();
		for (Iterator<ContextFreeShape> shapes = expandSet.iterator(); shapes.hasNext();) {
			ContextFreeShape next = shapes.next();
			next.expand();
			shapes.remove();
			renderSet.addAll(createdSet);
			queueSet.addAll(recursiveSet);
			createdSet.clear();
			recursiveSet.clear();
		}
		expandSet.addAll(queueSet);
		queueSet.clear();
		if (logger.isTraceEnabled()) {
			logger.trace("Expand shapes [" + recursiveSet.size() + "/" + createdSet.size() + "/" + renderSet.size() + "]");
		}
		return expandSet.size() > 0;
	}

	public void registerFigures() {
		for (int i = 0; i < runtime.getFigureElementCount(); i++) {
			FigureRuntimeElement figure = runtime.getFigureElement(i);
			figure.register(this);
		}
	}

	public void registerPath(ContextFreePath path) {
		pathMap.add(path);
	}

	public void registerRule(ContextFreeRule rule) {
		ruleMap.add(rule);
	}

	public void unregisterPath(ContextFreePath path) {
		pathMap.remove(path);
	}

	public void unregisterRule(ContextFreeRule rule) {
		ruleMap.remove(rule);
	}

	public void buildPathOrRule(ContextFreeState state, ContextFreeBounds globalBounds, ContextFreeBounds shapeBounds, String shape) {
		ContextFreePath path = pathMap.get(shape);
		if (path != null) {
			if (logger.isTraceEnabled()) {
				logger.trace("Path " + shape);
			}
			ContextFreeShape pathShape = path.createShape(this, state, globalBounds, shapeBounds);
			if (pathShape != null) {
				createdSet.add(pathShape);
			}
		} else {
			createShapes(state, globalBounds, shapeBounds, shape);
		}
	}
	
	private void createShapes(ContextFreeState state, ContextFreeBounds globalBounds, ContextFreeBounds shapeBounds, String shape) {
		if (!buildSet.contains(shape)) {
			if (logger.isTraceEnabled()) {
				logger.trace("Rule " + shape);
			}
			buildSet.add(shape);
			ContextFreeRule rule = ruleMap.get(shape);
			if (rule != null) {
				rule.createShapes(this, state, globalBounds, shapeBounds);
			}
			buildSet.remove(shape);
		} else {
			recursiveSet.add(new RecursiveShape(this, state, globalBounds, shapeBounds, shape));
		}
	}
	
	public void expandRule(ContextFreeShape oldShape, ContextFreeState state, ContextFreeBounds globalBounds, ContextFreeBounds prevShapeBounds, String shape) {
		if (logger.isTraceEnabled()) {
			logger.trace("Expanding " + shape);
		}
		ContextFreeBounds shapeBounds = new ContextFreeBounds(globalBounds.getWidth(), globalBounds.getHeight());
		createShapes(state, globalBounds, shapeBounds, shape);
		if (createdSet.size() > 0 && shapeBounds.isValid()) {
			double gsx = globalBounds.getSizeX();
			double gsy = globalBounds.getSizeY();
			double ssx = shapeBounds.getSizeX();
			double ssy = shapeBounds.getSizeY();
			double psx = prevShapeBounds.getSizeX();
			double psy = prevShapeBounds.getSizeY();
			int tw = globalBounds.getWidth();
			int th = globalBounds.getHeight();
			if (gsx >= THRESHOLD || gsy >= THRESHOLD) {
				double sx = (ssx / gsx) * tw;
				double sy = (ssy / gsy) * th;
				double qx = (psx / gsx) * tw;
				double qy = (psy / gsy) * th;
				if (logger.isTraceEnabled()) {
					logger.trace("sx = " + sx + ", sy = " + sy + ", qx = " + qx + ", qy = " + qy);
//					logger.trace("ssx = " + ssx + ", ssy = " + ssy + ", psx = " + psx + ", psy = " + psy);
				}
				if ((sx < MIN_SIZE && sy < MIN_SIZE) || (sx > MAX_SIZE && sy > MAX_SIZE) || (Math.abs(sx - qx) < 0.0005 && Math.abs(sy - qy) < 0.0005)) {
					if (logger.isTraceEnabled()) {
						logger.trace("Stop recursion for shape " + shape);
					}
					recursiveSet.clear();
				}
			} else {
				recursiveSet.clear();
			}
		} else {
			if (logger.isTraceEnabled()) {
				logger.trace("Shape invalid " + shape);
			}
			recursiveSet.clear();
		}
	}
	
	private class RuleMap {
		private Map<String, RuleEntry> map = new LinkedHashMap<String, RuleEntry>();

		public void add(ContextFreeRule rule) {
			RuleEntry entry = map.get(rule.getName());
			if (entry == null) {
				entry = new RuleEntry();
				map.put(rule.getName(), entry);
			}
			entry.add(rule);
		}
		
		public void remove(ContextFreeRule rule) {
			RuleEntry entry = map.get(rule.getName());
			if (entry != null) {
				entry.remove(rule);
				if (entry.runtimes.size() == 0) {
					map.remove(rule.getName());
				}
			}
		}

		public ContextFreeRule get(String ruleName) {
			RuleEntry entry = map.get(ruleName);
			if (entry != null) {
				return entry.get();
			}
			return null;
		} 
	}
	
	private class PathMap {
		private Map<String, ContextFreePath> map = new LinkedHashMap<String, ContextFreePath>();

		public void add(ContextFreePath path) {
			map.put(path.getName(), path);
		}

		public void remove(ContextFreePath path) {
			map.remove(path);
		}

		public ContextFreePath get(String pathName) {
			return map.get(pathName);
		} 
	}
	
	private class RuleEntry {
		private List<ContextFreeRule> runtimes = new LinkedList<ContextFreeRule>();
		private float total = 0;
		
		public void add(ContextFreeRule rule) {
			runtimes.add(rule);
			if (rule.getProbability() > 0) {
				total += rule.getProbability();
			} else {
				total += 1;
			}
		}
		
		public void remove(ContextFreeRule rule) {
			runtimes.remove(rule);
			if (rule.getProbability() > 0) {
				total -= rule.getProbability();
			} else {
				total -= 1;
			}
		}

		public ContextFreeRule get() {
			double number = runtime.random() * total;
			double offset = 0;
			for (ContextFreeRule runtime : runtimes) {
				offset += runtime.getProbability() > 0 ? runtime.getProbability() : 1f;
				if (number <= offset) {
					return runtime;
				}
			}
			return null;
		}
	}
}
