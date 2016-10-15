package com.ula.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.EllipseAnchor;

import org.eclipse.draw2d.IFigure;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;

import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;

import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import com.ula.comands.ConnectionCreateCommand;
import com.ula.comands.DeleteElementCommand;
import com.ula.figures.StateFigure;


import com.ula.listeners.LabelListener;
import com.ula.listeners.StateListener;
import com.ula.models.Canvas;
import com.ula.models.ArcShape;
import com.ula.models.Element;
import com.ula.models.State;

public class StateEditPart extends ElementEditPart implements NodeEditPart,
		 StateListener,PropertyChangeListener {

	public StateEditPart(State state) {
		setModel(state);
	}

	public State getModel() {
		return (State) super.getModel();
	}

	protected IFigure createFigure() {
		State state = getModel();
		return new StateFigure(state);
	}

	public void addNotify() {
		super.addNotify();
		getModel().addStateListener(this);
	}

	public void removeNotify() {
		getModel().removeStateListener(this);
		super.removeNotify();
	}

	public void activate() {
		if (!isActive()) {
			super.activate();
			((State) getModel()).addPropertyChangeListener(this);
		}
	}

	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			((State) getModel()).removePropertyChangeListener(this);
		}
	}

	protected List<ArcShape> getModelSourceConnections() {
		return getModel().getSourceConnections();
	}

	protected List<ArcShape> getModelTargetConnections() {
		return getModel().getTargetConnections();
	}

	@Override
	protected void createEditPolicies() {
		

		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentEditPolicy() {
			protected Command createDeleteCommand(GroupRequest request) {
				Canvas canvas = (Canvas) getParent().getModel();
				return new DeleteElementCommand(canvas, (State) getModel());
			}
		});
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new GraphicalNodeEditPolicy() {

					protected Command getConnectionCompleteCommand(
							CreateConnectionRequest request) {
						ConnectionCreateCommand cmd = (ConnectionCreateCommand) request
								.getStartCommand();
						cmd.setTarget((State) getHost().getModel());
						return cmd;
					}

					protected Command getConnectionCreateCommand(
							CreateConnectionRequest request) {
						State source = (State) getHost().getModel();
						ConnectionCreateCommand cmd = new ConnectionCreateCommand(
								source, source.getCanvas());
						request.setStartCommand(cmd);
						return cmd;
					}

					protected Command getReconnectSourceCommand(
							ReconnectRequest request) {
						// Connection conn = (Connection) request
						// .getConnectionEditPart().getModel();
						// Shape newSource = (Shape) getHost().getModel();
						// ConnectionReconnectCommand cmd = new
						// ConnectionReconnectCommand(
						// conn);
						// cmd.setNewSource(newSource);
						// return cmd;
						return null;
					}

					protected Command getReconnectTargetCommand(
							ReconnectRequest request) {
						// Connection conn = (Connection) request
						// .getConnectionEditPart().getModel();
						// Shape newTarget = (Shape) getHost().getModel();
						// ConnectionReconnectCommand cmd = new
						// ConnectionReconnectCommand(
						// conn);
						// cmd.setNewTarget(newTarget);
						// return cmd;
						return null;
					}
				});

	}

	public StateFigure getStateFigure() {
		return (StateFigure) getFigure();
	}

	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) {

		return new EllipseAnchor(getFigure());
	}

	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) {
		return new EllipseAnchor(getFigure());

	}

	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		if (request instanceof ReconnectRequest) {
			EditPart part = ((ReconnectRequest) request)
					.getConnectionEditPart();
			if (!(part instanceof ArcShapeEditPart))
				return null;
			ArcShapeEditPart connPart = (ArcShapeEditPart) part;
			return new EllipseAnchor(getFigure());
		}
		return new EllipseAnchor(getFigure());

	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		if (request instanceof CreateConnectionRequest) {
			Command cmd = ((CreateConnectionRequest) request).getStartCommand();
			if (!(cmd instanceof ConnectionCreateCommand))
				return null;

			return new EllipseAnchor(getFigure());
		}
		if (request instanceof ReconnectRequest) {
			EditPart part = ((ReconnectRequest) request)
					.getConnectionEditPart();
			if (!(part instanceof ArcShapeEditPart))
				return null;
			return new EllipseAnchor(getFigure());
		}
		return null;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		
		if (prop.equals(State.NAME_PROP)) {
			// refreshVisuals();

		}
		if (prop.equals(State.ID_ESTATE)) {
			changeState(getModel().getType());
		}

	}

	@Override
	public void addSourceListener(State source) {
		
		refreshSourceConnections();

	}

	@Override
	public void addTargetListener(State target) {
		
		refreshTargetConnections();
	}

	public void removeTargetConnectionListener(ArcShape conexion) {
		//
		refreshSourceConnections();

	}

	@Override
	public void removeSourceConnectionListener(ArcShape conexion) {
		//
		refreshTargetConnections();

	}

	@Override
	public void addLoopListener(State source) {
		
		refreshSourceConnections();
		refreshTargetConnections();

	}

	public void changeState(int type) {
		if (type == State.NORMAL) {
			getStateFigure().setBackgroundColor(ColorConstants.white);
		}
		if (type == State.FINAL) {
			getStateFigure().setBackgroundColor(ColorConstants.red);
		}
		if (type == State.INITIAL) {
			getStateFigure().setBackgroundColor(ColorConstants.black);
		}

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
		}
		return super.isSelectable();
	}

}
