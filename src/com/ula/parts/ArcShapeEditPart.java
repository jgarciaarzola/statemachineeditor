package com.ula.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;



import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.EllipseAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.ManhattanConnectionRouter;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.RelativeBendpoint;
import org.eclipse.draw2d.ShortestPathConnectionRouter;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;



import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gef.tools.SelectEditPartTracker;
import org.eclipse.swt.graphics.Color;

import com.ula.comands.DeleteConnectionCommand;
import com.ula.figures.LabelFigure;
import com.ula.listeners.ArcListener;
import com.ula.listeners.ElementListener;
import com.ula.listeners.LabelListener;
import com.ula.models.Canvas;
import com.ula.models.ArcShapeBendPoint;
import com.ula.models.ArcShape;
import com.ula.models.Element;
import com.ula.models.LabelBase;
import com.ula.models.State;
import com.ula.policies.ArcShapeBendPointEditPolicy;




public class ArcShapeEditPart extends AbstractConnectionEditPart implements ArcListener , PropertyChangeListener {

	private static final PointList ARROWHEAD = new PointList(new int[] { 0, 0,
			-2, 2, -2, 0, -2, -2, 0, 0 });

	
	
	
	
	public ArcShapeEditPart(ArcShape arc) {
		setModel(arc);
	}

	public ArcShape getModel() {
		return (ArcShape) super.getModel();

	}

	@Override
	protected IFigure createFigure() {
		
		return createFigureConnection(getModel());
	}
	
	public PolylineConnection getConnectionFigure(){
		return (PolylineConnection) getFigure();
	}
	
	
	public void activate() {
		if (!isActive()) {
			super.activate();
			((ArcShape) getModel()).addPropertyChangeListener(this);
		}
	}
	
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			((ArcShape) getModel()).removePropertyChangeListener(this);
		}
	}

	public static Connection createFigureConnection(ArcShape conn) {
		PolylineConnection connection = new PolylineConnection();
		PolygonDecoration decoration = new PolygonDecoration();
		decoration.setTemplate(ARROWHEAD);
		decoration.setBackgroundColor( ColorConstants.darkGray);
		connection.setForegroundColor(ColorConstants.gray);
		connection.setTargetDecoration(decoration);
		connection.setConnectionRouter(new BendpointConnectionRouter());
		
		
		return connection;
	}
	protected ConnectionAnchor getSourceConnectionAnchor() {
		if (getSource() instanceof StateEditPart) {
			StateEditPart editPart = (StateEditPart) getSource();
			return new EllipseAnchor(editPart.getFigure());
			
		}
		return super.getSourceConnectionAnchor();
	}
	protected ConnectionAnchor getTargetConnectionAnchor() {
		if (getTarget() instanceof StateEditPart) {
			StateEditPart editPart = (StateEditPart) getTarget();
			return new EllipseAnchor(editPart.getFigure());
		}
		return super.getTargetConnectionAnchor();
	}

	@Override
	protected void createEditPolicies() {
//		ConnectionEndpointEditPolicy selectionPolicy = new ConnectionEndpointEditPolicy();
//		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, selectionPolicy);
		
		
		refreshBendpointEditPolicy();
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ConnectionEditPolicy() {
			protected Command getDeleteCommand(GroupRequest request) {
				return new DeleteConnectionCommand(getModel(),getModel().getCanvas());
			}
		});
		

	}
	
	private void refreshBendpoints() {
		
		List modelConstraint = getModel().getBendPoints();
		List figureConstraint = new ArrayList();
		for (int i = 0; i < modelConstraint.size(); i++) {
			ArcShapeBendPoint cbp = (ArcShapeBendPoint) modelConstraint.get(i);
			RelativeBendpoint rbp = new RelativeBendpoint(getConnectionFigure());
			rbp.setRelativeDimensions(cbp.getFirstRelativeDimension(),
					cbp.getSecondRelativeDimension());
			rbp.setWeight((i + 1) / ((float) modelConstraint.size() + 1));
			figureConstraint.add(rbp);
		}
		getConnectionFigure().setRoutingConstraint(figureConstraint);
	}
	
	
	private void refreshBendpointEditPolicy() {

		installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE,
				new ArcShapeBendPointEditPolicy());

	}
	
	protected void refreshVisuals() {
		refreshBendpoints();
		
	}
	
	public void addNotify() {
		super.addNotify();
		getModel().addArcListener(this);
	}

	public void removeNotify() {
		getModel().removeArcListener(this);
		super.removeNotify();
	}
	

	@Override
	public void bendPointChangeListener() {
		refreshBendpoints();		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		String prop = evt.getPropertyName();
		
		
		if(prop.equals(ArcShape.ID_EVENT)){
			
		}
		
	}

	@Override
	public void validateEventsListener(boolean isEqual) {
		
		PolygonDecoration decoration = new PolygonDecoration();
		decoration.setTemplate(ARROWHEAD);
		

		if (isEqual) {
			decoration.setBackgroundColor(ColorConstants.red);
			getConnectionFigure().setOpaque(true);
			getConnectionFigure().setForegroundColor(ColorConstants.red);
			getConnectionFigure().setTargetDecoration(decoration);

		}
		if (!isEqual) {
			decoration.setBackgroundColor(ColorConstants.darkGray);
			getConnectionFigure().setOpaque(true);
			getConnectionFigure().setForegroundColor(ColorConstants.gray);
			getConnectionFigure().setTargetDecoration(decoration);

		}

	}

	@Override
	public void hadEventsListener() {
		PolygonDecoration decoration = new PolygonDecoration();
		decoration.setTemplate(ARROWHEAD);
		decoration.setBackgroundColor(ColorConstants.blue);
		getConnectionFigure().setOpaque(true);
		getConnectionFigure().setForegroundColor(ColorConstants.blue);
		getConnectionFigure().setTargetDecoration(decoration);
 
		
	}
	@Override
	public boolean isSelectable() {
		if(getSelected() == SELECTED_PRIMARY){
			for(Element e : getModel().getCanvas().getElements()){
				if(e instanceof State){
					State state = (State) e;
					for(ArcShape arc : state.getSourceConnections()){
						for(LabelListener l: arc.getLabelEvent().getListeners()){
							l.setSelection(false);
						}
					}
				}
			}
			
			for(LabelListener l : getModel().getLabelEvent().getListeners()){
				l.setSelection(true);
			}
			
		}
		return super.isSelectable();
	}

	
	
	
}
