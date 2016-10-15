package com.ula.comands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;

import com.ula.models.Canvas;
import com.ula.models.ArcShape;
import com.ula.models.Element;
import com.ula.models.State;

public class DeleteElementCommand extends Command {
	private Element element;
	private Canvas canvas;

	private List<ArcShape> sourceConnection = new ArrayList<ArcShape>();
	private List<ArcShape> targetConnection = new ArrayList<ArcShape>();

	private State state;
	private boolean initial = false;

	private List<ArcShape> transitionAux = new ArrayList<ArcShape>();

	public DeleteElementCommand(Canvas canvas, Element element) {
		this.canvas = canvas;
		this.element = element;

	}

	public void removeConnection(List<ArcShape> connections) {

		for (ArcShape c : connections) {
			transitionAux.add(c);
		}
		for (ArcShape c : transitionAux) {
			c.disconnect();
			canvas.removeElement(c.getLabelEvent());

		}
		transitionAux.clear();

	}

	@Override
	public void execute() {

		state = (State) element;
		if (element instanceof State) {
			canvas.removeElement(element);
			canvas.removeElement(((State) element).getLabelState());
		}
		if (!initial) {
			for (ArcShape c : state.getSourceConnections()) {
				sourceConnection.add(c);
			}
			for (ArcShape c : state.getTargetConnections()) {
				targetConnection.add(c);
			}
			initial = true;
		}

		this.removeConnection(state.getSourceConnections());
		this.removeConnection(state.getTargetConnections());

	}

	@Override
	public void undo() {
		
		List<ArcShape> loopArc = new ArrayList<ArcShape>();

		if (element instanceof State) {
			canvas.addElement(((State) element).getLabelState());
			canvas.addElement(element);
			for (ArcShape c : sourceConnection) {
				c.reconnect();
				canvas.addElement(c.getLabelEvent());
				if(c.getSource().equals(c.getTarget())){
					loopArc.add(c);
				}

			}
			for (ArcShape c : targetConnection) {
				if(!loopArc.isEmpty()){
					for(ArcShape loop : loopArc){
						if(!loop.equals(c)){
							c.reconnect();
							canvas.addElement(c.getLabelEvent());
						}
						
					}
				}
				else{
					c.reconnect();
					canvas.addElement(c.getLabelEvent());
					
				}
				
				

			}

		}

	}
}
