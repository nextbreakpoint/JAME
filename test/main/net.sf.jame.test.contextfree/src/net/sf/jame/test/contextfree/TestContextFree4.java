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
package net.sf.jame.test.contextfree;

import static junit.framework.Assert.fail;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;

import javax.imageio.ImageIO;

import net.sf.jame.contextfree.ContextFreeConfig;
import net.sf.jame.contextfree.ContextFreeConfigNodeBuilder;
import net.sf.jame.contextfree.ContextFreeRuntime;
import net.sf.jame.contextfree.parser.ContextFreeParser;
import net.sf.jame.contextfree.renderer.ContextFreeRenderer;
import net.sf.jame.contextfree.renderer.DefaultContextFreeRenderer;
import net.sf.jame.core.log4j.Configurator;
import net.sf.jame.core.tree.RootNode;
import net.sf.jame.core.tree.Tree;
import net.sf.jame.core.util.IntegerVector2D;
import net.sf.jame.core.util.Surface;
import net.sf.jame.core.util.Tile;

import org.junit.Test;

public class TestContextFree4 {
	private static final int IMAGE_HEIGHT = 200;
	private static final int IMAGE_WIDTH = 200;

	@Test
	public void parse() {
		try {
			Configurator.configure();
			String text = "" +
			"startshape Foo\n" +
			"background { b -1 }\n" +
			"rule Foo 1 {\n" +
			"SQUARE { size 1 1 x 1.3 y 1 }\n" +
			"Foo { s 1 x 1 }\n" +
			"}\n" +
			"rule Foo 0.01 {\n" +
			"Bar { s 1.1 }\n" +
			"}\n" +
			"rule Bar 1 {\n" +
			"CIRCLE { size 1 1 x 1.3 y 1 }\n" +
			"Foo { s 0.8 x 1 }\n" +
			"}\n" +
			"";
			System.out.println(text);
			ContextFreeParser parser = new ContextFreeParser();
			ContextFreeConfig config = parser.parseConfig(new File(System.getProperty("user.home")), text);
			RootNode rootNode = new RootNode("contextfree");
			ContextFreeConfigNodeBuilder nodeBuilder = new ContextFreeConfigNodeBuilder(config);
			nodeBuilder.createNodes(rootNode);
			Tree tree = new Tree(rootNode);
			System.out.println(tree);
			ContextFreeRuntime runtime = new ContextFreeRuntime(config);
			ContextFreeRenderer renderer = new DefaultContextFreeRenderer(Thread.MIN_PRIORITY);
			IntegerVector2D imageSize = new IntegerVector2D(IMAGE_WIDTH, IMAGE_HEIGHT);
			IntegerVector2D nullSize = new IntegerVector2D(0, 0);
			Tile tile = new Tile(imageSize, imageSize, nullSize, nullSize);
			renderer.setTile(tile);
			IntegerVector2D bufferSize = new IntegerVector2D(tile.getTileSize().getX() + tile.getTileBorder().getX() * 2, tile.getTileSize().getY() + tile.getTileBorder().getY() * 2);
			Surface surface = new Surface(bufferSize.getX(), bufferSize.getY());
			renderer.setRuntime(runtime);
			renderer.start();
			try {
				renderer.startRenderer();
				renderer.joinRenderer();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			Graphics2D g2d = surface.getGraphics2D();
			renderer.drawImage(g2d);
			g2d.setColor(Color.WHITE);
			g2d.drawRect(0, 0, surface.getWidth() - 1, surface.getHeight() - 1);
			ImageIO.write(surface.getImage(), "png", new File("test2.png"));
			renderer.stop();
			renderer.dispose();
			rootNode.dispose();
			runtime.dispose();
			config.dispose();
			surface.dispose();
		}
		catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
