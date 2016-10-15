package com.ula.comands;

import org.eclipse.gef.commands.Command;

import com.ula.models.Canvas;
import com.ula.models.ArcShape;

public class DeleteConnectionCommand extends Command {
	private final ArcShape conn;
	private Canvas canvas;

	public DeleteConnectionCommand(ArcShape conn, Canvas canvas) {
		super("Delete Connection");
		this.conn = conn;
		this.canvas = canvas;
	}

	public void execute() {
		conn.disconnect();

		canvas.removeElement(conn.getLabelEvent());

		// canvas.removeElement(conn.getLabelTransition());

	}

	public void undo() {
		conn.reconnect();
		canvas.addElement(conn.getLabelEvent());

		// canvas.addElement(conn.getLabelTransition());

	}

}
