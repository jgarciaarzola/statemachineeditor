package com.ula.freemarker.generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.ula.xml.ObjectFactory;
import com.ula.xml.StateMachine;

import freemarker.cache.URLTemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class Generator extends URLTemplateLoader {

	private String pathStateMachine;

	private String defaultPathTemplate = "src/com/ula/freemarker/templates/class-state.ftl";

	public Generator() {

	}

	public void run(String pathXmlStateMachine) throws Exception {
		Configuration configuration = new Configuration();
		configuration.setObjectWrapper(new BeansWrapper());
		configuration.setTemplateLoader(this);
		FileWriter fileWriter = null;
		if (pathXmlStateMachine != null) {
			fileWriter = new FileWriter( //
					pathStateMachine);
		}

		StateMachine xmlStateMAchine = readXML(pathXmlStateMachine);

		if (xmlStateMAchine.getAlternativeTemplateDirectory() != null) {
			IEditorPart edt = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().getActiveEditor();

			IResource r2 = (IResource) edt.getEditorInput().getAdapter(
					IResource.class);
			IProject p = r2.getProject();
			IJavaProject javaProject = JavaCore.create(p);
			configuration.setDirectoryForTemplateLoading(new File(
					ResourcesPlugin.getWorkspace().getRoot().getLocation()
							.toString()+"/"
							+ javaProject.getElementName()+"/"+ xmlStateMAchine.getAlternativeTemplateDirectory()));
			configuration.getTemplate(xmlStateMAchine.getAlternativeTemplateName())
					.process(xmlStateMAchine, fileWriter);

		} else {
			configuration.getTemplate( //
					defaultPathTemplate).process( //
					xmlStateMAchine, fileWriter);
		}

		fileWriter.close();
	}

  public StateMachine readXML(String pathXmlStateMachine) throws Exception {
    // ----------------------------------------
    // Obtener el InputStream
    // ----------------------------------------
	 IPath path = new Path(pathXmlStateMachine);
	 String string = pathXmlStateMachine.substring(2, pathXmlStateMachine.length());
	 Path rootLoc = (Path) ResourcesPlugin.getWorkspace().getRoot().getLocation();
	 
	 String pathAbsolute = rootLoc.toString();
	 pathAbsolute = pathAbsolute+"/";
	 pathAbsolute = pathAbsolute+string;
	 
	 FileInputStream file = new FileInputStream(pathAbsolute);
	 InputStream is =file;
    

    // ----------------------------------------
    // Inicializar JAXB y leer el XML
    // ----------------------------------------

    JAXBContext jc = JAXBContext.newInstance( //
        ObjectFactory.class.getPackage().getName());

    Unmarshaller unmarshaller = jc.createUnmarshaller();

    StateMachine xmlStateMachine = (StateMachine) unmarshaller.unmarshal(is);

    return xmlStateMachine;
  }

  // ----------------------------------------
  // URLTemplateLoader
  // ----------------------------------------

  public String getPathStateMachine() {
	return pathStateMachine;
}

public void setPathStateMachine(String pathStateMachine) {
	this.pathStateMachine = pathStateMachine;
}

@Override
  protected URL getURL(String name) {
    return getClass().getClassLoader().getResource(name);
  }

  // ----------------------------------------

 
}
