package com.ula.models;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

public class ArcShapeBendPoint implements Bendpoint {

	private float weight = 0.5f;
	private Dimension d1, d2;
	
	public Dimension getFirstRelativeDimension() {
		return d1;
	}
	
	public Dimension getSecondRelativeDimension() {
		return d2;
	}
	
	public void setRelativeDimensions(Dimension dim1, Dimension dim2) {
		d1 = dim1;
		d2 = dim2;
	}
	
	public void setWeight(float w) {
		weight = w;
	}
	
	public Point getLocation() {
		
		return null;
	}

}
