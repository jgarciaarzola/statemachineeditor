
package com.ula.comands;



import org.eclipse.gef.commands.Command;

import com.ula.models.Canvas;
import com.ula.models.ArcShape;
import com.ula.models.State;

public class ConnectionCreateCommand extends Command {

	private ArcShape connection;
	private final State source;
	private State target;
	private Canvas canvas;

	public ConnectionCreateCommand(State source, Canvas canvas) {
		if (source == null) {
			throw new IllegalArgumentException();
		}
		setLabel("connection creation");
		this.source = source;
		this.canvas = canvas;

	}

	public void execute() {

		connection = new ArcShape(source, target);
		connection.setCanvas(canvas);
     	

	}

	public void redo() {

		connection.reconnect();
		

	}

	public void setTarget(State target) {
		if (target == null) {
			throw new IllegalArgumentException();
		}
		this.target = target;
	}

	public void undo() {
		canvas.removeElement(connection.getLabelEvent());
		connection.disconnect();

	}

}
