package com.ula.editors;

import java.util.List;

import com.ula.models.ArcShapeBendPoint;
import com.ula.models.ArcShape;
import com.ula.xml.Arc;
import com.ula.xml.BendPoints;
import com.ula.xml.EventArc;
import com.ula.xml.Initial;
import com.ula.xml.LabelState;
import com.ula.xml.Source;
import com.ula.xml.State;
import com.ula.xml.Target;


public class GEFTOXMLUTILS {
	
	public static State createXmlState(String name,int type,int x,int y,int w,int h,int labelX,int labelY){
		State xmlState = new State();
		LabelState labelState = new LabelState();
		labelState.setName(name);
		labelState.setX(labelX);
		labelState.setY(labelY);
		if(type == 0){
			xmlState.setType("Normal");
		}
		if(type == 1){
			xmlState.setType("Final");
		}
		if(type == 2){
			xmlState.setType("Initial");
		}
		
		xmlState.setX(x);
		xmlState.setY(y);
		xmlState.setW(w);
		xmlState.setH(h);
		xmlState.setLabelState(labelState);
		return xmlState;
	}
	
	public static Initial createXmlInitialState(String name,int type,int x,int y,int w,int h){
		Initial xmlInitialState = new Initial();
		xmlInitialState.setName(name);
		if(type == 0){
			xmlInitialState.setType("Normal");
		}
		if(type == 1){
			xmlInitialState.setType("Final");
		}
		if(type == 2){
			xmlInitialState.setType("Initial");
		}
		
		xmlInitialState.setX(x);
		xmlInitialState.setY(y);
		xmlInitialState.setW(w);
		xmlInitialState.setH(h);
		return xmlInitialState;

	}
	
	public static Arc saveConnection(ArcShape c) {
		Arc xmlArc = new Arc();
		EventArc eventArc = new EventArc();
		eventArc.setName(c.getLabelEvent().getText());
		eventArc.setX(c.getLabelEvent().getX());
		eventArc.setY(c.getLabelEvent().getY());

		Source xmlSource = new Source();
		Target xmlTarget = new Target();
		
		com.ula.xml.State xmlStateSource = GEFTOXMLUTILS.createXmlState(c
				.getSource().getLabelState().getText(),
				c.getSource().getType(), c.getSource().getX(), c.getSource()
						.getY(), c.getSource().getW(), c.getSource().getH(), c
						.getSource().getLabelState().getX(), c.getSource()
						.getLabelState().getY());
		
		com.ula.xml.State xmlStateTarget = GEFTOXMLUTILS.createXmlState(c
				.getTarget().getLabelState().getText(),
				c.getTarget().getType(), c.getTarget().getX(), c.getTarget()
						.getY(), c.getTarget().getW(), c.getTarget().getH(), c
						.getTarget().getLabelState().getX(), c.getTarget()
						.getY());
		
		xmlSource.setState(xmlStateSource);
		xmlTarget.setState(xmlStateTarget);
		xmlArc.setSource(xmlSource);
		xmlArc.setTarget(xmlTarget);
		// xmlTransition.setName(c.getLabelTransition().getText());
		xmlArc.setEventArc(eventArc);
		for(ArcShapeBendPoint bendPoint: c.getBendPoints()){
			BendPoints xmlBendPoint = new BendPoints();
			xmlBendPoint.setD1W(bendPoint.getFirstRelativeDimension().width);
			xmlBendPoint.setD1H(bendPoint.getFirstRelativeDimension().height);
			xmlBendPoint.setD2W(bendPoint.getSecondRelativeDimension().width);
			xmlBendPoint.setD2H(bendPoint.getSecondRelativeDimension().height);
			xmlArc.getBendPoints().add(xmlBendPoint);
		}
		

		return xmlArc;
	}
	
	public static com.ula.models.State createState(State xmlState) {
		com.ula.models.State state = new com.ula.models.State(
				xmlState.getLabelState().getName(), xmlState.getX(), xmlState.getY(),
				xmlState.getW(), xmlState.getH());
		if (xmlState.getType().equals("Normal")) {
			state.setType(com.ula.models.State.NORMAL);
		}
		if (xmlState.getType().equals("Final")) {
			state.setType(com.ula.models.State.FINAL);
		}
		if (xmlState.getType().equals("Initial")) {
			state.setType(com.ula.models.State.INITIAL);
		}
		state.getLabelState().setLocation(xmlState.getLabelState().getX(),xmlState.getLabelState().getY());
		int lenght = state.getLabelState().getText().length();
		state.getLabelState().setSize(lenght * 10, 20);		
		
		return state;
	}
	

}
