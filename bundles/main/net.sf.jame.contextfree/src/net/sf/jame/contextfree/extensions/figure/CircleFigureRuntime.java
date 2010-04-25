/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.extensions.figure;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import net.sf.jame.contextfree.cfdg.figure.extension.FigureExtensionRuntime;
import net.sf.jame.contextfree.renderer.ContextFreeContext;
import net.sf.jame.contextfree.renderer.ContextFreePath;

/**
 * @author Andrea Medeghini
 */
public class CircleFigureRuntime<T extends CircleFigureConfig> extends FigureExtensionRuntime<T> implements ContextFreePath {
	private Shape shape = createShape();
	
	private Shape createShape() {
		return new Ellipse2D.Float(norm(-0.5f), norm(-0.5f), norm(1), norm(1));
	}
	
	private float norm(float i) {
		return i;
	}

	/**
	 * @see net.sf.jame.core.extension.ConfigurableExtensionRuntime#configReloaded()
	 */
	@Override
	public void configReloaded() {
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void draw(ContextFreeContext contextFreeContext) {
		contextFreeContext.drawShape(shape);
	}

	@Override
	public void prepare(ContextFreeContext contextFreeContext) {
	}

	@Override
	public void registerFigure(ContextFreeContext contextFreeContext) {
		contextFreeContext.registerPath(this);
	}

	public String getName() {
		return "circle";
	}
}
