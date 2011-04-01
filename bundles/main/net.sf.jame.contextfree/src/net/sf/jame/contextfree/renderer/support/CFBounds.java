package net.sf.jame.contextfree.renderer.support;

public class CFBounds {
	private double minX = Double.MAX_VALUE;
	private double minY = Double.MAX_VALUE;
	private double maxX = Double.MIN_VALUE;
	private double maxY = Double.MIN_VALUE;

	public CFBounds() {
	}

	public double getMinX() {
		return minX;
	}

	public double getMinY() {
		return minY;
	}

	public double getMaxX() {
		return maxX;
	}

	public double getMaxY() {
		return maxY;
	}

	public double getSizeX() {
		return maxX - minX;
	}

	public double getSizeY() {
		return maxY - minY;
	}
	
	public void addPoint(double x, double y) {
		minX = Math.min(x, minX); 
		minY = Math.min(y, minY); 
		maxX = Math.max(x, maxX); 
		maxY = Math.max(y, maxY); 
	}

	public boolean isValid() {
		return (maxX - minX) > 0 || (maxY - minY) > 0;
	}

	@Override
	public String toString() {
		return "CFBounds [minX=" + minX + ", maxX=" + maxX + ", minY=" + minY + ", maxY=" + maxY + "]";
	}
}
