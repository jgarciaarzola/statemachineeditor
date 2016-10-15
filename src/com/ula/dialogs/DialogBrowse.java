package com.ula.dialogs;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.internal.ui.dialogs.PackageSelectionDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.progress.IProgressService;

// eclipse packeage selection dialog
// org.eclipse.jdt.internal.ui.dialog.packeage selection dialog;

public class DialogBrowse {

	
	private ContainerSelectionDialog dialog;


	public DialogBrowse() {

	}

	public IPath open(Shell shell) {

//		dialog = new ContainerSelectionDialog(shell, null, false,
//				"Select Target:");
//		dialog.setTitle("Container Selection");
//		String resultString;
//		if(dialog.open() ==0){
//			Object[] resultObject = dialog.getResult();
//			 resultString = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
//			for (int i = 0; i < resultObject.length; i++) {
//				resultString = resultString + resultObject[i];
//			}
//		}
//		else{
//			resultString=null;
//		}
		
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(
			    shell,
			    new WorkbenchLabelProvider(),
			    new BaseWorkbenchContentProvider());
		    IResource r = null;
			dialog.setTitle("Alternative Template");
			dialog.setMessage("Select the template:");
			dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
			dialog.open();
			
			
			if(dialog.getReturnCode() == 1){
				IPath path = new Path("ERROR");
				return path;
			}
			if(dialog.getReturnCode() == 0){
				 r = (IResource) dialog.getFirstResult();
			}
			
			if(!r.getFileExtension().equals("ftl")){
				IPath path = new Path("ERROR");
				return path;
			}
			
		
			IPath resultPath = r.getProjectRelativePath();
			

			
		
		
		return resultPath;

	}
	
	  // --------------------------------------------------------------------------------

	 
}
