/*
 * $Id:$
 *
 */
package net.sf.jame.contextfree.extensions.figure;

import net.sf.jame.contextfree.cfdg.figure.extension.FigureExtensionRuntime;
import net.sf.jame.contextfree.renderer.ContextFreeBounds;
import net.sf.jame.contextfree.renderer.ContextFreeContext;
import net.sf.jame.contextfree.renderer.ContextFreeNode;
import net.sf.jame.contextfree.renderer.ContextFreePath;
import net.sf.jame.contextfree.renderer.ContextFreeState;
import net.sf.jame.contextfree.renderer.FigureContextFreeNode;

/**
 * @author Andrea Medeghini
 */
public class TriangleFigureRuntime<T extends TriangleFigureConfig> extends FigureExtensionRuntime<T> implements ContextFreePath {
	@Override
	public void register(ContextFreeContext contextFreeContext) {
		contextFreeContext.registerPath(this);
	}

	public String getName() {
		return "TRIANGLE";
	}

	public ContextFreeNode buildNode(ContextFreeContext context, ContextFreeState state, ContextFreeBounds bounds) {
		float[] p = new float[] { -0.5f, (float) (-0.5 * Math.tan(Math.PI / 6.0)), +0.5f, (float) (-0.5 * Math.tan(Math.PI / 6.0)), 0, (float) (0.5 * Math.tan(Math.PI / 3.0) - 0.5 * Math.tan(Math.PI / 6.0)) };
		float[] q = new float[p.length];
		state.transform(p, q);
		for (int i = 0; i < q.length; i += 2) {
			float x = q[i + 0];
			float y = q[i + 1];
			bounds.addPoint(x, y);
		}
		return new FigureContextFreeNode(state, q);
	}
}
