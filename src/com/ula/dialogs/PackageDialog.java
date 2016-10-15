package com.ula.dialogs;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jdt.internal.ui.dialogs.PackageSelectionDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

import com.ula.models.Canvas;

public class PackageDialog {

	public String open(Shell shell, Canvas canvas) {

		String result = null;
		String targetPath;

		IProgressService progressService = PlatformUI.getWorkbench()
				.getProgressService();

		IEditorPart edt = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getActiveEditor();

		IResource r2 = (IResource) edt.getEditorInput().getAdapter(
				IResource.class);
		IProject p = r2.getProject();
		IJavaProject javaProject = JavaCore.create(p);
		
		if (javaProject == null) {
			MessageDialog.openError(null, "Error", "You should select a project");
		}

		PackageSelectionDialog dialog;

		IJavaSearchScope javaSearchScope = SearchEngine.createJavaSearchScope( //
				new IJavaElement[] { javaProject }, IJavaSearchScope.SOURCES);

		dialog = new PackageSelectionDialog(shell, progressService,
				PackageSelectionDialog.F_REMOVE_DUPLICATES, javaSearchScope);
		if (dialog.open() == 0) {
			PackageFragment packageFragment = (PackageFragment) dialog.getFirstResult();
			result = packageFragment.getElementName();
			targetPath =ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
			targetPath = targetPath + packageFragment.getPath().toString();
			targetPath = targetPath + "/" +canvas.getName() + ".java";
			canvas.setTargetPath(targetPath);
			
		} 
		

		return result;

	}

}
