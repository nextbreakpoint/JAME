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
package net.sf.jame.mandelbrot.extensions.fractal.orbittrap;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;

import net.sf.jame.core.util.DoubleVector2D;
import net.sf.jame.mandelbrot.common.CriteriaElement;
import net.sf.jame.mandelbrot.renderer.RenderedPoint;

/**
 * @author Andrea Medeghini
 */
public class TriangleTrapRuntime extends AbstractOrbitTrapRuntime<TriangleTrapConfig> {
	private double ti;
	private double tr;
	private double w;
	private double h;
	private double a;
	private int c;

	/**
	 * @see net.sf.jame.mandelbrot.extensions.fractal.orbittrap.AbstractOrbitTrapRuntime#prepareForProcessing(net.sf.jame.core.util.DoubleVector2D)
	 */
	@Override
	public void prepareForProcessing(final DoubleVector2D center) {
		super.prepareForProcessing(center);
		w = getConfig().getWidth() / 2d;
		h = getConfig().getHeight();
		a = (getConfig().getRotation() * Math.PI) / 180d;
		c = getConfig().getCriteria();
		if (w < 0) {
			w = 0;
		}
		if (h < 0) {
			h = 0;
		}
	}

	/**
	 * @see net.sf.jame.mandelbrot.fractal.orbittrap.extension.OrbitTrapExtensionRuntime#processPoint(net.sf.jame.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	public boolean processPoint(final RenderedPoint cp) {
		cp.dr = cp.zr - center.getX();
		cp.di = cp.zi + center.getY();
		tr = +cp.dr * Math.cos(a) - cp.di * Math.sin(a);
		ti = -cp.di * Math.cos(a) - cp.dr * Math.sin(a);
		if (c == CriteriaElement.FIRST_OUT) {
			return (Math.abs(tr) > w) || (ti > h) || (ti < 0) || (ti > (h - h / w * Math.abs(tr)));
		}
		else {
			return (Math.abs(tr) < w) && (ti > 0) && (ti < (h - h / w * Math.abs(tr)));
		}
	}

	/**
	 * @see net.sf.jame.mandelbrot.fractal.orbittrap.extension.OrbitTrapExtensionRuntime#renderPoint(net.sf.jame.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	public void renderPoint(final RenderedPoint cp) {
		cp.tr = cp.dr;
		cp.ti = cp.di;
	}

	/**
	 * @see net.sf.jame.mandelbrot.fractal.orbittrap.extension.OrbitTrapExtensionRuntime#renderOrbitTrap(double, double)
	 */
	@Override
	public Shape renderOrbitTrap(final double sx, final double sy, final double theta) {
		GeneralPath path = new GeneralPath();
		double ca = Math.cos(a + theta);
		double sa = Math.sin(a + theta);
		double tr1 = (-h * sa) * sx;
		double ti1 = (-h * ca) * sy;
		double tr2 = (+w * ca) * sx;
		double ti2 = (-w * sa) * sy;
		double tr3 = (-w * ca) * sx;
		double ti3 = (+w * sa) * sy;
		Line2D.Double line1 = new Line2D.Double(tr1, ti1, tr2, ti2);
		Line2D.Double line2 = new Line2D.Double(tr2, ti2, tr3, ti3);
		Line2D.Double line3 = new Line2D.Double(tr3, ti3, tr1, ti1);
		path.append(line1, false);
		path.append(line2, false);
		path.append(line3, false);
		return path;
	}
}
