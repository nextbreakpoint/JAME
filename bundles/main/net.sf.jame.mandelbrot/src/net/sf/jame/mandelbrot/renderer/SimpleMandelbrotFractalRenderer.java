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
package net.sf.jame.mandelbrot.renderer;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Map;

import net.sf.jame.core.math.Complex;
import net.sf.jame.core.util.Surface;
import net.sf.jame.mandelbrot.fractal.incolouring.IncolouringFormulaRuntimeElement;
import net.sf.jame.mandelbrot.fractal.outcolouring.OutcolouringFormulaRuntimeElement;

/**
 * @author Andrea Medeghini
 */
public final class SimpleMandelbrotFractalRenderer extends AbstractMandelbrotFractalRenderer {
	private final RenderingStrategy mandelbrotRenderingStrategy = new MandelbrotRenderingStrategy();
	private final RenderingStrategy juliaRenderingStrategy = new JuliaRenderingStrategy();
	private RendererData renderedData;
	private boolean isAborted = false;

	/**
	 * @param threadPriority
	 */
	public SimpleMandelbrotFractalRenderer(final int threadPriority) {
		super(threadPriority);
	}

	/**
	 * @see net.sf.jame.AbstractMandelbrotFractalRenderer.core.fractal.renderer.AbstractFractalRenderer#free()
	 */
	@Override
	protected void free() {
		super.free();
		if (renderedData != null) {
			renderedData.free();
		}
	}

	/**
	 * @see net.sf.jame.AbstractMandelbrotFractalRenderer.core.fractal.renderer.AbstractFractalRenderer#init()
	 */
	@Override
	protected void init() {
		super.init();
		renderedData = new RendererData();
		renderedData.reallocate(getBufferWidth(), getBufferHeight());
	}

	/**
	 * @see net.sf.jame.AbstractMandelbrotFractalRenderer.core.fractal.renderer.AbstractFractalRenderer#doFractal(boolean)
	 */
	@Override
	protected void doFractal(final boolean dynamic) {
		updateShift();
		updateRegion();
		updateTransform();
		renderingStrategy.updateParameters();
		if (fractal.getRenderingFormula().getFormulaRuntime() != null) {
			fractal.getRenderingFormula().getFormulaRuntime().prepareForRendering(fractal.getProcessingFormula().getFormulaRuntime(), fractal.getOrbitTrap().getOrbitTrapRuntime());
			if (fractal.getOrbitTrap().getOrbitTrapRuntime() != null) {
				fractal.getOrbitTrap().getOrbitTrapRuntime().prepareForProcessing(fractal.getOrbitTrap().getCenter());
			}
		}
		for (int i = 0; i < fractal.getOutcolouringFormulaCount(); i++) {
			if (fractal.getOutcolouringFormula(i).getFormulaRuntime() != null) {
				if (fractal.getOutcolouringFormula(i).isAutoIterations() && (fractal.getRenderingFormula().getFormulaRuntime() != null)) {
					fractal.getOutcolouringFormula(i).getFormulaRuntime().prepareForRendering(fractal.getRenderingFormula().getFormulaRuntime(), fractal.getRenderingFormula().getFormulaRuntime().getIterations());
				}
				else {
					fractal.getOutcolouringFormula(i).getFormulaRuntime().prepareForRendering(fractal.getRenderingFormula().getFormulaRuntime(), fractal.getOutcolouringFormula(i).getIterations());
				}
			}
		}
		for (int i = 0; i < fractal.getIncolouringFormulaCount(); i++) {
			if (fractal.getIncolouringFormula(i).getFormulaRuntime() != null) {
				if (fractal.getIncolouringFormula(i).isAutoIterations() && (fractal.getRenderingFormula().getFormulaRuntime() != null)) {
					fractal.getIncolouringFormula(i).getFormulaRuntime().prepareForRendering(fractal.getRenderingFormula().getFormulaRuntime(), fractal.getRenderingFormula().getFormulaRuntime().getIterations());
				}
				else {
					fractal.getIncolouringFormula(i).getFormulaRuntime().prepareForRendering(fractal.getRenderingFormula().getFormulaRuntime(), fractal.getIncolouringFormula(i).getIterations());
				}
			}
		}
		final Complex px = new Complex(0, 0);
		final Complex pw = new Complex(0, 0);
		final RenderedPoint p = new RenderedPoint();
		final double beginx = area.points[0].r;
		final double endx = area.points[1].r;
		final double beginy = area.points[0].i;
		final double endy = area.points[1].i;
		final int sizex = getBufferWidth();
		final int sizey = getBufferHeight();
		final double stepx = (endx - beginx) / (sizex - 1);
		final double stepy = (endy - beginy) / (sizey - 1);
		double posx = beginx;
		double posy = beginy;
		for (int x = 0; x < sizex; x++) {
			renderedData.positionX[x] = posx;
			posx += stepx;
		}
		for (int y = 0; y < sizey; y++) {
			renderedData.positionY[y] = posy;
			posy += stepy;
		}
		int offset = 0;
		for (int y = 0; y < sizey; y++) {
			for (int x = 0; x < sizex; x++) {
				px.r = renderedData.x0;
				px.i = renderedData.y0;
				pw.r = renderedData.positionX[x];
				pw.i = renderedData.positionY[y];
				p.pr = renderedData.positionX[x];
				p.pi = renderedData.positionY[y];
				renderedData.newRGB[offset] = renderingStrategy.renderPoint(p, px, pw);
				offset += 1;
			}
			if (y % 20 == 0) {
				percent = (int) ((y * 100f) / sizey);
				copy();
			}
			Thread.yield();
			if (isInterrupted()) {
				isAborted = true;
				break;
			}
		}
		copy();
		if (isAborted) {
			percent = 100;
		}
	}

	private void copy() {
		final Graphics2D g2d = getGraphics();
		g2d.setComposite(AlphaComposite.Src);
		g2d.drawImage(renderedData.newBuffer, 0, 0, null);
	}

	/**
	 * @see net.sf.jame.AbstractMandelbrotFractalRenderer.core.fractal.renderer.AbstractFractalRenderer#getMandelbrotRenderingStrategy()
	 */
	@Override
	protected RenderingStrategy getMandelbrotRenderingStrategy() {
		return mandelbrotRenderingStrategy;
	}

	/**
	 * @see net.sf.jame.AbstractMandelbrotFractalRenderer.core.fractal.renderer.AbstractFractalRenderer#createJuliaRenderingStrategy()
	 */
	@Override
	protected RenderingStrategy getJuliaRenderingStrategy() {
		return juliaRenderingStrategy;
	}

	/**
	 * @author Andrea Medeghini
	 */
	public static class RendererData {
		/**
		 * 
		 */
		public BufferedImage newBuffer;
		/**
		 * 
		 */
		public int[] newRGB;
		/**
		 * 
		 */
		public double[] positionX;
		/**
		 * 
		 */
		public double[] positionY;
		/**
		 * 
		 */
		public double x0 = 0;
		/**
		 * 
		 */
		public double y0 = 0;

		/**
		 * @see java.lang.Object#finalize()
		 */
		@Override
		public void finalize() throws Throwable {
			free();
			super.finalize();
		}

		/**
		 * 
		 */
		public void free() {
			positionX = null;
			positionY = null;
			if (newBuffer != null) {
				newBuffer.flush();
			}
			newBuffer = null;
			newRGB = null;
		}

		/**
		 * @param width
		 * @param height
		 */
		public void reallocate(final int width, final int height) {
			free();
			positionX = new double[width];
			positionY = new double[height];
			for (int i = 0; i < width; i++) {
				positionX[i] = 0;
			}
			for (int i = 0; i < height; i++) {
				positionY[i] = 0;
			}
			newBuffer = new BufferedImage(width, height, Surface.DEFAULT_TYPE);
			newRGB = ((DataBufferInt) newBuffer.getRaster().getDataBuffer()).getData();
		}
	}

	private class MandelbrotRenderingStrategy implements RenderingStrategy {
		/**
		 * @see net.sf.jame.mandelbrot.renderer.AbstractMandelbrotFractalRenderer.RenderingStrategy#isVerticalSymetrySupported()
		 */
		public boolean isVerticalSymetrySupported() {
			for (int i = 0; i < fractal.getOutcolouringFormulaCount(); i++) {
				final OutcolouringFormulaRuntimeElement outcolouringFormula = fractal.getOutcolouringFormula(i);
				if ((outcolouringFormula.getFormulaRuntime() != null) && !outcolouringFormula.getFormulaRuntime().isVerticalSymetryAllowed() && outcolouringFormula.isEnabled()) {
					return false;
				}
			}
			for (int i = 0; i < fractal.getIncolouringFormulaCount(); i++) {
				final IncolouringFormulaRuntimeElement incolouringFormula = fractal.getIncolouringFormula(i);
				if ((incolouringFormula.getFormulaRuntime() != null) && !incolouringFormula.getFormulaRuntime().isVerticalSymetryAllowed() && incolouringFormula.isEnabled()) {
					return false;
				}
			}
			return true;
		}

		/**
		 * @see net.sf.jame.mandelbrot.renderer.AbstractMandelbrotFractalRenderer.RenderingStrategy#isHorizontalSymetrySupported()
		 */
		public boolean isHorizontalSymetrySupported() {
			for (int i = 0; i < fractal.getOutcolouringFormulaCount(); i++) {
				final OutcolouringFormulaRuntimeElement outcolouringFormula = fractal.getOutcolouringFormula(i);
				if ((outcolouringFormula.getFormulaRuntime() != null) && !outcolouringFormula.getFormulaRuntime().isHorizontalSymetryAllowed()) {
					return false;
				}
			}
			for (int i = 0; i < fractal.getIncolouringFormulaCount(); i++) {
				final IncolouringFormulaRuntimeElement incolouringFormula = fractal.getIncolouringFormula(i);
				if ((incolouringFormula.getFormulaRuntime() != null) && !incolouringFormula.getFormulaRuntime().isHorizontalSymetryAllowed()) {
					return false;
				}
			}
			return true;
		}

		/**
		 * @see net.sf.jame.mandelbrot.renderer.AbstractMandelbrotFractalRenderer.RenderingStrategy#renderPoint(net.sf.jame.mandelbrot.renderer.RenderedPoint)
		 */
		public int renderPoint(final RenderedPoint p, final Complex px, final Complex pw) {
			if ((fractal.getRenderingFormula().getFormulaRuntime() != null) && (fractal.getTransformingFormula().getFormulaRuntime() != null)) {
				fractal.getTransformingFormula().getFormulaRuntime().renderPoint(pw);
				p.xr = px.r;
				p.xi = px.i;
				p.wr = pw.r;
				p.wi = pw.i;
				p.dr = 0;
				p.di = 0;
				p.tr = 0;
				p.ti = 0;
				return SimpleMandelbrotFractalRenderer.this.renderPoint(p);
			}
			return 0;
		}

		/**
		 * @see net.sf.jame.mandelbrot.renderer.AbstractMandelbrotFractalRenderer.RenderingStrategy#updateParameters()
		 */
		public void updateParameters() {
			if (fractal.getRenderingFormula().getFormulaRuntime() != null) {
				renderedData.x0 = fractal.getRenderingFormula().getFormulaRuntime().getInitialPoint().r;
				renderedData.y0 = fractal.getRenderingFormula().getFormulaRuntime().getInitialPoint().i;
			}
			else {
				renderedData.x0 = 0;
				renderedData.y0 = 0;
			}
		}
	}

	private class JuliaRenderingStrategy implements RenderingStrategy {
		/**
		 * @see net.sf.jame.mandelbrot.renderer.AbstractMandelbrotFractalRenderer.RenderingStrategy#isHorizontalSymetrySupported()
		 */
		public boolean isHorizontalSymetrySupported() {
			return false;
		}

		/**
		 * @see net.sf.jame.mandelbrot.renderer.AbstractMandelbrotFractalRenderer.RenderingStrategy#isVerticalSymetrySupported()
		 */
		public boolean isVerticalSymetrySupported() {
			return false;
		}

		/**
		 * @see net.sf.jame.mandelbrot.renderer.AbstractMandelbrotFractalRenderer.RenderingStrategy#renderPoint(net.sf.jame.mandelbrot.renderer.RenderedPoint)
		 */
		public int renderPoint(final RenderedPoint p, final Complex px, final Complex pw) {
			if ((fractal.getRenderingFormula().getFormulaRuntime() != null) && (fractal.getTransformingFormula().getFormulaRuntime() != null)) {
				fractal.getTransformingFormula().getFormulaRuntime().renderPoint(px);
				p.xr = pw.r;
				p.xi = pw.i;
				p.wr = px.r;
				p.wi = px.i;
				p.dr = 0;
				p.di = 0;
				p.tr = 0;
				p.ti = 0;
				return SimpleMandelbrotFractalRenderer.this.renderPoint(p);
			}
			return 0;
		}

		/**
		 * @see net.sf.jame.mandelbrot.renderer.AbstractMandelbrotFractalRenderer.RenderingStrategy#updateParameters()
		 */
		public void updateParameters() {
			renderedData.x0 = oldConstant.getX();
			renderedData.y0 = oldConstant.getY();
		}
	}
}
