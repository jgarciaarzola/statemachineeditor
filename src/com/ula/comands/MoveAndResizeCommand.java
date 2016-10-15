package com.ula.comands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import com.ula.models.ArcShape;
import com.ula.models.Element;
import com.ula.models.LabelBase;
import com.ula.models.State;


public class MoveAndResizeCommand extends Command {
	private final Element element;
	private final Rectangle box;

	private Rectangle oldBox;

	public MoveAndResizeCommand(Element element, Rectangle box) {
		this.element = element;
		this.box = box.getCopy();

	}

	public void execute() {
		oldBox = new Rectangle(element.getX(), element.getY(), element.getW(),
				element.getH());
		

		element.setLocation(box.x, box.y);
//		System.err.println("moviendo a "+ box.x+" " +box.y);
		element.setSize(box.width, box.height);

		if (element instanceof State) {
			State aux = (State) element;
			((State) element).getLabelState().setLocation(box.x, (box.y - 10));
			
			for (ArcShape c : aux.getSourceConnections()) {
					if(c.getBendPoints().size()==0){	
					c.getLabelEvent().setLocation(
							((c.getSource().getX() + c.getTarget().getX()) / 2),
							(c.getSource().getY() + c.getTarget().getY()) / 2);
					}
				
			}
			for (ArcShape c : aux.getTargetConnections()) {
					if(c.getBendPoints().size() ==0){
						
					
					c.getLabelEvent().setLocation(
							((c.getSource().getX() + c.getTarget().getX()) / 2),
							(c.getSource().getY() + c.getTarget().getY()) / 2);
					}
			}

		} else if(element.getClass().getSuperclass() == LabelBase.class) {
			LabelBase note = (LabelBase) element;
			int lenght = note.getText().length();
			note.setSize(lenght * 10, 20);
		}
		
	}

	public void undo() {

		element.setLocation(oldBox.x, oldBox.y);
		if(element instanceof State){
			((State) element).getLabelState().setLocation(oldBox.x, (oldBox.y-10));
			for (ArcShape c : ((State) element).getSourceConnections()) {
				
				c.getLabelEvent().setLocation(
						((c.getSource().getX() + c.getTarget().getX()) / 2),
						(c.getSource().getY() + c.getTarget().getY()) / 2);
			
		}
		for (ArcShape c : ((State) element).getTargetConnections()) {
			
				c.getLabelEvent().setLocation(
						((c.getSource().getX() + c.getTarget().getX()) / 2),
						(c.getSource().getY() + c.getTarget().getY()) / 2);
			
		}
		}
		element.setSize(oldBox.width, oldBox.height);
	}

}
