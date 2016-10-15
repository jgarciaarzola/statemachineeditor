package com.ula.dialogs;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.ula.models.Canvas;

public class DialogEventDescriptor extends PropertyDescriptor {
	
	private Canvas canvas;

  public DialogEventDescriptor(Object id, String displayName, Canvas c) {
    super(id, displayName);
    canvas = c;
  }

  @Override
public CellEditorEvents createPropertyEditor(Composite parent) {
    CellEditorEvents editor = new CellEditorEvents(parent);
    if (getValidator() != null) {
      editor.setValidator(getValidator());
    }
    editor.setCanvas(canvas);
    
    return editor;
    
  }
  
  
}
