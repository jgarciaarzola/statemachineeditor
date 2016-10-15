package com.ula.dialogs;

import java.util.ArrayList;

import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

import com.ula.models.Canvas;
import com.ula.models.Event;



public class CellEditorEvents extends DialogCellEditor {

  private Composite composite;

  private Image image;

  private Canvas canvas;
  
  private class DocSectionCellLayout extends Layout {
    @Override
	public Point computeSize(Composite editor, int wHint, int hHint, boolean force) {
      return new Point(wHint, hHint);
    }

    @Override
	public void layout(Composite editor, boolean force) {
    }
  }

  public CellEditorEvents(Composite parent) {
    this(parent, SWT.NONE);
  }

  public CellEditorEvents(Composite parent, int style) {
    super(parent, style);
 
    doSetValue(1);
  }
  
  public void setCanvas(Canvas c){
	  canvas = c;
  }

  @Override
protected Control createContents(Composite cell) {
    Color bg = cell.getBackground();
    composite = new Composite(cell, getStyle());
    composite.setBackground(bg);
    composite.setLayout(new DocSectionCellLayout());
    return composite;
  }

  @Override
public void dispose() {
    if (image != null) {
      image.dispose();
      image = null;
    }
    super.dispose();
  }

  @Override
@SuppressWarnings("unchecked")
  protected Object openDialogBox(Control cellEditorWindow) {
    //    DefaultDocSectionDialog dialog = new DefaultDocSectionDialog(cellEditorWindow.getShell());
    Object value = getValue();
    DialogEvents table = new DialogEvents(composite, (ArrayList<Event>) value,canvas);
    value = table.open();
    
    return value;
    
    
  }

  @Override
protected void updateContents(Object value) {
    System.err.println(value);

  }
}
