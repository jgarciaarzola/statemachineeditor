package com.ula.freemarker.generator;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;

import com.ula.dialogs.AddPathDialog;
import com.ula.xml.StateMachine;


public class GenerateCodeActionDelegate implements IObjectActionDelegate,
		IViewActionDelegate, IEditorActionDelegate {

	private IWorkbenchPart targetPart;
	private IPath path;

	private Shell shell;

	public void setActivePart(IAction action, IWorkbenchPart part) {
		this.targetPart = part;

	}

	public void init(IViewPart view) {
		this.targetPart = view;
	}

	public void setActiveEditor(IAction action, IEditorPart editor) {
		this.targetPart = editor;
	}

	public void selectionChanged(IAction action, ISelection selection) {

		if (selection instanceof IStructuredSelection) {
			try {
				path = new Path(((IStructuredSelection) selection)
						.getFirstElement().toString());
				
			} catch (Exception e) {
				
			}
			

		}

	}

	public void run(IAction action) {
		Generator generado = new Generator();

		shell = new Shell(Display.getCurrent());
		if (path ==null || path.getFileExtension() == null || !path.getFileExtension().equals("smx")) {
			MessageDialog.openWarning(targetPart.getSite().getShell(),
					"Warning", "THE FILE MUST BE .smx");
			return;
		}
		
		
		try {
			StateMachine xmlStateMachine = generado.readXML(path.toString());
			if(xmlStateMachine.getInitial() == null){
				MessageDialog.openError(targetPart.getSite().getShell(), "ERROR",
						"YOU MUST SPECIFIC A INITIAL STATE");
				return;
			}
			generado.setPathStateMachine(xmlStateMachine.getPathJava());
			
				generado.run(path.toString());
				MessageDialog.openInformation(targetPart.getSite().getShell(),
						"Generating Code of State Machine",
						"Generating Code of State Machine");

			
			
			
			
		} catch (Exception e) {
			MessageDialog.openError(targetPart.getSite().getShell(), "ERROR",
					"THE CODE CANNOT BE GENERATED");
			e.printStackTrace();

		}

	}

}
