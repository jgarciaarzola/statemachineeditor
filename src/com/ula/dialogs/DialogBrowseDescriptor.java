package com.ula.dialogs;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.ula.models.Canvas;

public class DialogBrowseDescriptor extends PropertyDescriptor {


	public DialogBrowseDescriptor(Object id, String displayName) {
		super(id, displayName);
		

	}

	public CellEditor createPropertyEditor(Composite parent) {
		CellEditorBrowse editor = new CellEditorBrowse(parent);

		return editor;

	}

}
