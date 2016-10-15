package com.ula.parts;

import java.util.ArrayList;
import java.util.List;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;




import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;


import com.ula.comands.CreateElementCommand;
import com.ula.comands.MoveAndResizeCommand;
import com.ula.listeners.CanvasListener;
import com.ula.listeners.LabelListener;
import com.ula.models.ArcShape;
import com.ula.models.Canvas;
import com.ula.models.Element;
import com.ula.models.LabelEvent;
import com.ula.models.LabelState;
import com.ula.models.LabelBase;
import com.ula.models.State;







public class CanvasEditPart extends AbstractGraphicalEditPart
 implements CanvasListener , PropertyChangeListener 
{
	public CanvasEditPart(Canvas circuloBase) {
		setModel(circuloBase);
		 circuloBase.addCanvasListener(this);
	}

	public Canvas getModel() {
		return (Canvas) super.getModel();
	}

	protected IFigure createFigure() {
		Figure figure = new FreeformLayer();
		figure.setBorder(new MarginBorder(3));
		figure.setLayoutManager(new FreeformLayout());
		figure.setOpaque(true);
		figure.setBackgroundColor(ColorConstants.white);
		return figure;
	}

	
	protected List<Element> getModelChildren() {
		
		List<Element> allObjects = new ArrayList<Element>();
		List<State> stateList = new ArrayList<State>();
		List<LabelEvent> listEventLabel = new ArrayList<LabelEvent>();
		List<LabelState> listNameState = new ArrayList<LabelState>();
		
		
		for(Element ele: getModel().getElements()){
			if(ele instanceof State){
				stateList.add((State)ele);
			}			
			if(ele instanceof LabelEvent){
				listEventLabel.add((LabelEvent)ele);
			}
			if(ele instanceof LabelState){
				listNameState.add((LabelState)ele);
			}
			
		}
		
		
		allObjects.addAll(stateList);
		allObjects.addAll(listNameState);
		allObjects.addAll(listEventLabel);
		
		
		return allObjects;
	}



	@Override
	protected void createEditPolicies() {
		
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		
		
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new XYLayoutEditPolicy() {
			protected Command getCreateCommand(CreateRequest request) {
				Object type = request.getNewObjectType();
				Rectangle box = (Rectangle) getConstraintFor(request);

				box.setWidth(50);
				box.setHeight(50);
				

				Canvas elemento = getModel();
				if (type == State.class) {
					State circulo1 = (State) request.getNewObject();
					return new CreateElementCommand(elemento,circulo1,box);
				}
			
				
				

				return null;
			}

			
			protected Command createChangeConstraintCommand(
					ChangeBoundsRequest request, EditPart child, Object constraint
				) {
		
				
					Object type = child.getModel();
		
					
					Rectangle box = (Rectangle) constraint;
					box.setWidth(50);
					box.setHeight(50);
					if (type.getClass() == State.class) {
		
						State elem = (State) child.getModel();
								
					return new MoveAndResizeCommand(elem, box);
					}
										
					if(type.getClass().getSuperclass() == LabelBase.class){
						LabelBase elem = (LabelBase) child.getModel();
						return new MoveAndResizeCommand(elem, box);
					}
					
				
				return super.createChangeConstraintCommand(request, child, constraint);
			}
		
		});
	}
	
	
	
	
	public void elementAddedListener(Element c) {
			addChild(createChild(c), 0);
	}

	
	public void elementRemovedListenr(Element c) {
		elementoRemovedAux(c);

	}
	private void elementoRemovedAux(Element elem) {
		Object part = getViewer().getEditPartRegistry().get(elem);
		if (part instanceof EditPart)
			removeChild((EditPart) part);
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		String prop = evt.getPropertyName();
		
		if( prop.equals(Canvas.NAME_PROP)  ||  prop.equals(Canvas.PATH_PACKAGE_PROP) ){
			refreshVisuals();
		}
		
	}
	
	@Override
	public boolean isSelectable() {
		if (getSelected() == SELECTED_PRIMARY) {
			for (Element e : getModel().getElements()) {
				if (e instanceof State) {
					State state = (State) e;
					for (ArcShape arc : state.getSourceConnections()) {
						for (LabelListener l : arc.getLabelEvent()
								.getListeners()) {
							l.setSelection(false);
						}
					}
				}
			}
		}
	
		return super.isSelectable();
	}
	
	

	
	
}