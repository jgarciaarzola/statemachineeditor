package com.ula.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.ula.models.Canvas;
import com.ula.models.ArcShape;
import com.ula.models.Element;
import com.ula.models.State;

public class DialogEvents extends Dialog {
	private String message;
	private ArrayList<com.ula.models.Event> events = new ArrayList<com.ula.models.Event>();
	private ArrayList<com.ula.models.Event> backupEvents = new ArrayList<com.ula.models.Event>();
	private Canvas canvas;

	private Table table;
	private TableEditor editor;
	private Button btnRemove;
	private Button btnAdd;
	private Button btnOk;
	private Button btnCancel;
	private final int insetX = 4, insetY = 4;
	private Shell shell;
	private static int i = 0;
	private ArrayList<String> newEvents = new ArrayList<String>();
	private ArrayList<String> eventsRemoved = new ArrayList<String>();
	private boolean isModified = false;
	private boolean isRemove = false;

	public DialogEvents(Composite composite,
			ArrayList<com.ula.models.Event> value, Canvas c) {

		super(composite.getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

		backupEvents = (ArrayList<com.ula.models.Event>) value.clone();
		events = value;
		canvas = c;

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<com.ula.models.Event> open() {
		// Create the dialog windo
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				shell = new Shell(Display.getCurrent());
				shell.setBounds(200, 200, 500, 500);
				FormLayout formLayout = new FormLayout();
				formLayout.marginWidth = insetX;
				formLayout.marginHeight = insetY;
				shell.setLayout(formLayout);
				shell.setText("Events of State Machine");
				createContents();
			}
		});

		return events;
	}

	private void createContents() {
		table = new Table(shell, SWT.SINGLE | SWT.BORDER);
		btnAdd = new Button(shell, SWT.PUSH);
		btnRemove = new Button(shell, SWT.PUSH);
		btnCancel = new Button(shell, SWT.PUSH);
		btnOk = new Button(shell, SWT.PUSH);
		setUpBtnRemove();
		setUpBtnAdd();
		setUpCancelBtn();
		setUpBtnOk();
		setUpTable();

		editor = new TableEditor(table);
		// The editor must have the same size as the cell and must
		// not be any smaller than 50 pixels.
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		editor.minimumWidth = 50;
		// editing the second column

		shell.open();
	}

	private void setUpTable() {
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		FormData formMetaDataList = new FormData();
		formMetaDataList.left = new FormAttachment(0, 0);
		formMetaDataList.right = new FormAttachment(100, 0);
		formMetaDataList.top = new FormAttachment(btnAdd, insetY);
		formMetaDataList.bottom = new FormAttachment(btnCancel, -insetY);
		table.setLayoutData(formMetaDataList);

		TableColumn column1 = new TableColumn(table, SWT.NULL);
		column1.setText("Event");
		column1.setWidth(220);

		fetchTable();

	}

	private void fetchTable() {
		if (events == null || events.size() == 0) {
			return;
		}

		for (int k = 0; k < events.size(); k++) {

			String eventAux = events.get(k).getName();
			TableItem tableItem = new TableItem(table, SWT.NULL);
			String[] newStr = new String[] { eventAux };
			tableItem.setText(newStr);

		}

	}

	private void setUpBtnRemove() {

		btnRemove.setText("Remove");
		FormData formBtnRemove = new FormData();
		formBtnRemove.right = new FormAttachment(100, -insetX);
		// Table formdata
		btnRemove.setLayoutData(formBtnRemove);
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				TableItem[] itemsSelected = table.getSelection();
				for (int i = 0; i < itemsSelected.length; i++) {
					int indexSelect = table.indexOf(itemsSelected[i]);
					eventsRemoved.add(events.get(indexSelect).getName());
					table.remove(indexSelect);
					events.remove(indexSelect);
					isRemove = true;
				}
			}
		});
	}

	private void setUpBtnAdd() {
		btnAdd.setText(" Add ");
		final FormData formBtnAdd = new FormData();
		formBtnAdd.right = new FormAttachment(btnRemove, -insetX);
		btnAdd.setLayoutData(formBtnAdd);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				btnAddWidgetSelected(evt);
			}
		});
		table.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event evt) {
				tableMouseDownhandleEvent(evt);
			}
		});
	}

	private void setUpCancelBtn() {
		btnCancel.setText(" Cancel ");
		FormData formBtnCancel = new FormData();
		formBtnCancel.right = new FormAttachment(100, -insetX);
		formBtnCancel.bottom = new FormAttachment(100, 0);
		btnCancel.setLayoutData(formBtnCancel);

		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				events.clear();
				events.addAll(backupEvents);
				shell.close();

			}
		});

	}

	private void setUpBtnOk() {
		btnOk.setText("Ok");
		FormData formBtnOk = new FormData();
		formBtnOk.right = new FormAttachment(btnCancel, -insetX);
		formBtnOk.bottom = new FormAttachment(100, 0);
		btnOk.setLayoutData(formBtnOk);

		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {

				if (events == null) {
					return;
				}

				final List<Element> listElement = new ArrayList<Element>();

				for (Element e : canvas.getElements()) {
					listElement.add(e);
				}

				TableItem[] tableItem = table.getItems();


					for (int k = 0; k < tableItem.length; k++) {

						for (int i = 0; i < newEvents.size(); i++) {
							if (tableItem[k].getText(0)
									.equals(newEvents.get(i))) {

								events.get(k).setName(newEvents.get(i));
								if (isModified) {
									for (ArcShape c : events.get(k)
											.getListConnection()) {
										c.getLabelEvent().setText(
												events.get(k).getName());
									}
								}

							}
						}

					}

					if (!isRemove) {
						shell.close();
						return;

					}

					for (int i = 0; i < backupEvents.size(); i++) {
						for (int j = 0; j < eventsRemoved.size(); j++) {
							if (backupEvents.get(i).getName()
									.equals(eventsRemoved.get(j))) {
								for (ArcShape c : backupEvents.get(i)
										.getListConnection()) {
									if (c.getEvent()
											.getName()
											.equals(backupEvents.get(i)
													.getName())) {
										canvas.removeElement(c.getLabelEvent());
										c.setComboOption(0);
										
									}
								}
							}
						}
					}

				
				shell.close();
			}
		});

	}

	protected void tableMouseDownhandleEvent(Event evt) {

		Rectangle clientArea = table.getClientArea();

		Point pt = new Point(evt.x, evt.y);

		int index = table.getTopIndex();

		// iterates through all the table
		while (index < table.getItemCount()) {
			boolean visible = false;

			final TableItem item = table.getItem(index);

			for (int i = 0; i < table.getColumnCount(); i++) {
				Rectangle rect = item.getBounds(i);
				if (rect.contains(pt)) {
					final int column = i;

					final Text text = new Text(table, SWT.NONE);

					Listener textListener = new Listener() {
						@Override
						public void handleEvent(final Event e) {
							switch (e.type) {
							case SWT.FocusOut:
								item.setText(column, text.getText());
								text.dispose();
								isModified = false;
								break;
							case SWT.Traverse:
								switch (e.detail) {
								case SWT.TRAVERSE_RETURN:
									item.setText(column, text.getText());
									newEvents.add(text.getText());
									isModified = true;
									// FALL THROUGH
								case SWT.TRAVERSE_ESCAPE:
									text.dispose();
									e.doit = false;
								}

								break;
							}
						}
					};

					text.addListener(SWT.FocusOut, textListener);
					text.addListener(SWT.Traverse, textListener);

					editor.setEditor(text, item, i);
					text.setText(item.getText(i));
					text.selectAll();
					text.setFocus();
					return;
				}
				if (!visible && rect.intersects(clientArea)) {
					visible = true;
				}
			}
			if (!visible)
				return;
			index++;
		}
	}

	private void btnAddWidgetSelected(SelectionEvent event) {
		TableItem item3 = new TableItem(table, SWT.NULL);
		String[] newStr = new String[] { "New_Event" + i };
		String newMetaData;

		newMetaData = newStr[0];
		com.ula.models.Event newEvent = new com.ula.models.Event();
		newEvent.setName(newMetaData);
		events.add(newEvent);
		item3.setText(newStr);
		i++;
	}

}
