package com.ula.dialogs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


import com.ula.freemarker.generator.Generator;
import com.ula.xml.StateMachine;




public class AddPathDialog extends Dialog
{
 
  

   private Text targetFileLocationField;
   private Generator generador;
   private String pathXmlStateMachine;
   private StateMachine xmlStateMachine;
   


   
	public AddPathDialog(Shell parentShell, Generator generador,String paString) {
		super(parentShell);
		this.generador = generador;
		this.pathXmlStateMachine = paString;
		try {
		xmlStateMachine=	this.generador.readXML(pathXmlStateMachine);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}





protected Control createDialogArea(Composite parent) {
      Composite container = (Composite) super.createDialogArea(parent);
      final GridLayout gridLayout = new GridLayout();
      gridLayout.numColumns = 3;
      container.setLayout(gridLayout);
      
      
      final Label label = new Label(container, SWT.NONE);
      final GridData gridData = new GridData();
      gridData.horizontalSpan = 3;
      label.setLayoutData(gridData);
      label.setText("Select the file ");

      final Label label_1 = new Label(container, SWT.NONE);
      final GridData gridData_1 = new GridData(GridData.HORIZONTAL_ALIGN_END);
      label_1.setLayoutData(gridData_1);
      label_1.setText("Source File:");

      targetFileLocationField = new Text(container, SWT.BORDER);
      targetFileLocationField.addModifyListener(new ModifyListener() {
         public void modifyText(ModifyEvent e) {
//            updatePageComplete();
         }
      });
      targetFileLocationField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      final Button button = new Button(container, SWT.NONE);
      button.addSelectionListener(new SelectionAdapter() {
         public void widgetSelected(SelectionEvent e) {
            browseForSourceFile();
         }

		
      });
      button.setText("Browse...");

      final Label label_2 = new Label(container, SWT.NONE);
      final GridData gridData_2 = new GridData();
      gridData_2.horizontalSpan = 3;
      label_2.setLayoutData(gridData_2);


      
      initContent();
      return container;
   }


   private void initContent() {


      targetFileLocationField.setText(xmlStateMachine.getName());
	   targetFileLocationField.setSize(200, 200);
      targetFileLocationField.addModifyListener(new ModifyListener() {
         public void modifyText(ModifyEvent e) {
//            locationPattern = targetFileLocationField.getText();
         }
      });

      
      
   }

   /**
    * Configures the given shell in preparation for opening this window in it.
    * In this case, we set the dialog title.
    */
   protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      newShell.setText("Select File for to save");
      newShell.setLocation(400, 400);
      newShell.setSize(500, 200);
   }


   

   
 
   public void browseForSourceFile() {
	   	String targetPath;
	      IPath path = browse(getSourceLocation());
	      if (path == null)
	         return;
	      IPath rootLoc = ResourcesPlugin.getWorkspace().getRoot().getLocation();
	      if (rootLoc.isPrefixOf(path))
	         path = path.setDevice(null).removeFirstSegments(rootLoc.segmentCount());
	      targetPath = path.toString();
	      if(!targetPath.endsWith(".java")){
	    	  targetPath = targetPath +".java";
	      }
	      
	      targetFileLocationField.setText(targetPath);
	      
	      generador.setPathStateMachine(targetPath);
	   }
   
   
   private IPath browse(IPath path) {
	      FileDialog dialog = new FileDialog(getShell(), SWT.SAVE);
	      if (path != null) {
	         if (path.segmentCount() > 1)
	            dialog.setFilterPath(path.removeLastSegments(1).toOSString());
	         if (path.segmentCount() > 0)
	            dialog.setFileName(path.lastSegment());
	      }
	      String result = dialog.open();
	      if (result == null)
	         return null;
	      return new Path(result);
	   }
   
   
   public IPath getSourceLocation() {
	      String text = targetFileLocationField.getText().trim();
	      if (text.length() == 0)
	         return null;
	      IPath path = new Path(text);
	      if (!path.isAbsolute())
	         path = ResourcesPlugin.getWorkspace().getRoot().getLocation().append(path);
	      return path;
	   }
   
   
}
