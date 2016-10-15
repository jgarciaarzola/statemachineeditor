package com.ula.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;


public class LabelFigure extends Label{


	public LabelFigure(String note) {
		super(note);
		final FlowLayout layout = new FlowLayout();
		setLayoutManager(layout);
		setOpaque(false);
	}

	protected void paintFigure(Graphics graphics) {
		graphics.setBackgroundColor(ColorConstants.white);
		Rectangle b = getBounds();
		graphics.fillRectangle(b.x , b.y, b.width, 50);
		graphics.fillRectangle(b.x, b.y , b.width, b.height );
		super.paintFigure(graphics);
	}
	public void setLabel(String s){
		setText(s);
	}

}
