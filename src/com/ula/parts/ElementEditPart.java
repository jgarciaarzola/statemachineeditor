package com.ula.parts;

import java.util.Map;



import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import com.ula.comands.DeleteElementCommand;
import com.ula.listeners.ElementListener;
import com.ula.models.Canvas;
import com.ula.models.ArcShape;
import com.ula.models.Element;
import com.ula.models.State;





public abstract class ElementEditPart extends AbstractGraphicalEditPart implements
ElementListener {
	
	
	
	protected void refreshVisuals() {

		Element c = (Element) getModel();
		Rectangle bounds = new Rectangle(c.getX(), c.getY(), c.getW(), c.getH());
		((GraphicalEditPart) getParent()).setLayoutConstraint(this,
				getFigure(), bounds);
	
	}

	public void changeLocationListener(int x, int y) {
		
		refreshVisuals();
		}

	public void changeSizeListener(int w, int h) {
		
		refreshVisuals();

	}
//	protected ConnectionEditPart findConnection(State l, State t) {
//		if (l == null || t == null)
//			return null;
//		Map<?, ?> registry = getViewer().getEditPartRegistry();
//		Object conn = new TransitionShape(l, t);
//		return (ConnectionEditPart) registry.get(conn);
//		
//	}
	
	
//	protected ConnectionEditPart createOrFindConnection(State l,State t) {
//		Object conn = new TransitionShape(l, t);
//		return createOrFindConnection(conn);
//		
//	}
	@Override
	protected void createEditPolicies() {
			
	}



}
