package com.ula.comands;




import com.ula.models.ArcShapeBendPoint;

public class CreateBendPointCommand extends BendPointCommand {

	public void execute() {
		
		ArcShapeBendPoint cbp = new ArcShapeBendPoint();
		cbp.setRelativeDimensions(getFirstRelativeDimension(),getSecondRelativeDimension());
		getTransitionShape().insertBendpoint(getIndex(), cbp);
		super.execute();
	}

	public void undo() {
		
		super.undo();
		getTransitionShape().removeBendpoint(getIndex());
	}

}