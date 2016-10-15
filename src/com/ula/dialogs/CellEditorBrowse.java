package com.ula.dialogs;

import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;

public class CellEditorBrowse extends DialogCellEditor {
	private Composite composite;

	private Image image;

	private class DocSectionCellLayout extends Layout {
		@Override
		public Point computeSize(Composite editor, int wHint, int hHint,
				boolean force) {
			return new Point(wHint, hHint);
		}

		@Override
		public void layout(Composite editor, boolean force) {
		}
	}

	public CellEditorBrowse(Composite parent) {
		this(parent, SWT.NONE);
	}

	public CellEditorBrowse(Composite parent, int style) {
		super(parent, style);

		doSetValue(1);
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

		Shell shell = new Shell(Display.getCurrent());
		Object value = getValue();
		DialogBrowse dialog = new DialogBrowse();
		value = dialog.open(shell);
		return value;

	}

	@Override
	protected void updateContents(Object value) {

	}

}
