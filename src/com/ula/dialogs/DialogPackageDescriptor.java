package com.ula.dialogs;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.ula.models.Canvas;

public class DialogPackageDescriptor extends PropertyDescriptor{
	private Canvas canvas;
	public DialogPackageDescriptor(Object id, String displayName,Canvas canvas) {
		super(id, displayName);
		this.canvas = canvas;

	}

	public CellEditor createPropertyEditor(Composite parent) {
		CellEditorPackage editor = new CellEditorPackage(parent);
		editor.setCanvas(canvas);
		return editor;

	}
	

}
