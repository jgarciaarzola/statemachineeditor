package com.ula.parts;



import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.Request;
import org.eclipse.gef.tools.SelectEditPartTracker;

import com.ula.figures.LabelFigure;
import com.ula.listeners.LabelListener;
import com.ula.models.ArcShape;
import com.ula.models.LabelBase;
import com.ula.models.State;





public class LabelBaseEditPart extends ElementEditPart implements  LabelListener {

	public LabelBaseEditPart(LabelBase note) {
		setModel(note);
		note.addLabelListener(this);

	}

	public LabelBase getModel() {
		return (LabelBase) super.getModel();
	}

	protected IFigure createFigure() {
		LabelBase c = getModel();
		return new LabelFigure(c.getText());

	}
	
	private LabelFigure getNoteFigure(){
		return (LabelFigure)getFigure();
		
	}

	public void addNotify() {
		super.addNotify();
		getModel().addLabelListener(this);
	}

	public void removeNotify() {
		getModel().removeLabelListener(this);
		super.removeNotify();
	}

	@Override
	protected void createEditPolicies() {
//		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new LugarPolicy(getModel()));
		
	}

	

	@Override
	public void textChangedListener(String text) {
		getNoteFigure().setLabel(text);
		
	}

	@Override
	public void setSelection(boolean isSelected) {
		if (isSelected) {
			setFocus(true);

		} else {
			setFocus(false);
		}

	}

}
