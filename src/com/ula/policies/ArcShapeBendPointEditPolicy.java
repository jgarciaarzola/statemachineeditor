package com.ula.policies;


import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.BendpointEditPolicy;

import org.eclipse.gef.requests.BendpointRequest;

import com.ula.comands.BendPointCommand;
import com.ula.comands.CreateBendPointCommand;
import com.ula.comands.DeleteBendPointCommand;
import com.ula.comands.MoveBendPointCommand;
import com.ula.models.ArcShape;


public class ArcShapeBendPointEditPolicy  extends BendpointEditPolicy{
protected Command getCreateBendpointCommand(BendpointRequest request) {
		
		CreateBendPointCommand com = new CreateBendPointCommand();
		Point p = request.getLocation();
		Connection conn = getConnection();

		conn.translateToRelative(p);

		com.setLocation(p);
		Point ref1 = getConnection().getSourceAnchor().getReferencePoint();
		Point ref2 = getConnection().getTargetAnchor().getReferencePoint();

		conn.translateToRelative(ref1);
		conn.translateToRelative(ref2);

		com.setRelativeDimensions(p.getDifference(ref1), p.getDifference(ref2));
		com.setConexion((ArcShape) request.getSource().getModel());
		com.setIndex(request.getIndex());
		return com;
	}

	protected Command getMoveBendpointCommand(BendpointRequest request) {
		MoveBendPointCommand com = new MoveBendPointCommand();
		Point p = request.getLocation();
		Connection conn = getConnection();

		conn.translateToRelative(p);

		com.setLocation(p);

		Point ref1 = getConnection().getSourceAnchor().getReferencePoint();
		Point ref2 = getConnection().getTargetAnchor().getReferencePoint();

		conn.translateToRelative(ref1);
		conn.translateToRelative(ref2);

		com.setRelativeDimensions(p.getDifference(ref1), p.getDifference(ref2));
		com.setConexion((ArcShape) request.getSource().getModel());
		com.setIndex(request.getIndex());
		return com;
	}

	protected Command getDeleteBendpointCommand(BendpointRequest request) {
		BendPointCommand com = new DeleteBendPointCommand();
		Point p = request.getLocation();
		com.setLocation(p);
		com.setConexion((ArcShape) request.getSource().getModel());
		com.setIndex(request.getIndex());
		return com;
	}
		

}
