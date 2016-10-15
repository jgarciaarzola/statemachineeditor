package com.ula.parts;



import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.ula.models.Canvas;
import com.ula.models.ArcShape;
import com.ula.models.LabelBase;
import com.ula.models.State;

public class EditorEditPartFactory implements EditPartFactory {

	public EditPart createEditPart(EditPart arg0, Object model) {
		
//		System.err.println("Factory para: " + model.getClass());
		
		if(model instanceof State){
			
			return new StateEditPart((State) model);
		}

		if(model instanceof Canvas){
			return new CanvasEditPart((Canvas) model);
		}
				
		if(model instanceof ArcShape){
			return new ArcShapeEditPart((ArcShape)model);
		}
		if(model instanceof LabelBase){
			return new LabelBaseEditPart((LabelBase) model);
		}
		
		throw new IllegalStateException("No EditPart for " + model.getClass());
	}

}
