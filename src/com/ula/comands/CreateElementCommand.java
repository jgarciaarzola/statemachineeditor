package com.ula.comands;



import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import com.ula.models.Canvas;
import com.ula.models.Element;
import com.ula.models.State;

public class CreateElementCommand extends Command
{
	private final Canvas canvas;
	private final Element element;
	private final Rectangle box;
	
	
	public CreateElementCommand(Canvas canvas, Element elemento1, Rectangle box) {
		
		this.canvas = canvas;
		this.element = elemento1;
		this.box = box;
		
		
	}
	

	public void execute() {
		element.setLocation(box.x, box.y);

		element.setSize(box.width, box.height);
		if(element instanceof State){
			((State) element).getLabelState().setLocation(box.x, box.y - 10);
			int lenght = ((State) element).getLabelState().getText().length();
			((State) element).getLabelState().setSize(lenght * 10, 20);
			canvas.addElement(((State) element).getLabelState());
			canvas.addElement(element);
		}
		else{		
		canvas.addElement(element);
		}

	}
	

	public void undo() {
		if(element instanceof State){
			canvas.removeElement(element);
			canvas.removeElement(((State) element).getLabelState());
		}
		
		
	}
	
	

}