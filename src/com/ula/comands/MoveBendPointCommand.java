package com.ula.comands;


import org.eclipse.draw2d.Bendpoint;

import com.ula.models.ArcShapeBendPoint;


public class MoveBendPointCommand extends BendPointCommand {

	private Bendpoint oldBendpoint;

	public void execute() {
		ArcShapeBendPoint bp = new ArcShapeBendPoint();
		bp.setRelativeDimensions(getFirstRelativeDimension(),
				getSecondRelativeDimension());
		
		setOldBendpoint((Bendpoint) getTransitionShape().getBendPoints().get(getIndex()));
		
		getTransitionShape().setBendpoint(getIndex(), bp);
		
		super.execute();
	}

	protected Bendpoint getOldBendpoint() {
		return oldBendpoint;
	}

	public void setOldBendpoint(Bendpoint bp) {
		oldBendpoint = bp;
	}

	public void undo() {
		super.undo();
		
		getTransitionShape().setBendpoint(getIndex(), getOldBendpoint());
	}

}
