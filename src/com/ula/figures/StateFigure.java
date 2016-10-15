package com.ula.figures;

import java.io.ObjectInputStream.GetField;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.CompoundBorder;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.widgets.Display;

import com.ula.models.State;



public class StateFigure  extends Ellipse {

	public static final Image CIRCULO = new Image(Display.getCurrent(),
			StateFigure.class.getResourceAsStream("circulo.jpeg"));
	public static final Image CONNECTION_IMAGE = new Image(Display.getCurrent(),
			StateFigure.class.getResourceAsStream("connection.png"));
	public static int i=0;
	
	
	

	public StateFigure(State state) {
	
		
				
		setOpaque(true);
	
		if (state.getType() == State.NORMAL) {

			setBackgroundColor(ColorConstants.white);
		}
		if(state.getType() == State.FINAL){
			setBackgroundColor(ColorConstants.red);
		}
		if(state.getType() == State.INITIAL){
			setBackgroundColor(ColorConstants.black);
		}
	
		
		
		

		
		

	}
}
