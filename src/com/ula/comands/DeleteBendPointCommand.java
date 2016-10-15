package com.ula.comands;

import org.eclipse.draw2d.Bendpoint;


public class DeleteBendPointCommand extends BendPointCommand {

	private Bendpoint bendpoint;

	public void execute() {
		bendpoint = (Bendpoint) getTransitionShape().getBendPoints().get(getIndex());
		getTransitionShape().removeBendpoint(getIndex());
		super.execute();
	}

	public void undo() {
		super.undo();
		getTransitionShape().insertBendpoint(getIndex(), bendpoint);
	}

}