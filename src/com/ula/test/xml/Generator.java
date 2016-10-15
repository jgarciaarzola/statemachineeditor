package com.ula.test.xml;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;


import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;


import com.ula.xml.ObjectFactory;
import com.ula.xml.StateMachine;



import freemarker.cache.URLTemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class Generator extends URLTemplateLoader {
	
	
	private String pathStateMachine;
	
	public Generator(){
		
	}

  public void run(String pathXmlStateMachine) throws Exception {
    Configuration configuration = new Configuration();
    configuration.setObjectWrapper(new BeansWrapper());
    configuration.setTemplateLoader(this);
    FileWriter fileWriter = null;
    if(pathXmlStateMachine!=null){
     fileWriter = new FileWriter( //
        pathStateMachine);
    }

    StateMachine xmlStateMAchine = readXML(pathXmlStateMachine);

    configuration.getTemplate( //
        "com/ula/freemarker/templates/class-state.ftl").process( //
        xmlStateMAchine, fileWriter);

    fileWriter.close();
  }

  public StateMachine readXML(String pathXmlStateMachine) throws Exception {
    // ----------------------------------------
    // Obtener el InputStream
    // ----------------------------------------
	 IPath path = new Path(pathXmlStateMachine);
	 String string = pathXmlStateMachine.substring(2, pathXmlStateMachine.length());
	 Path rootLoc = (Path) ResourcesPlugin.getWorkspace().getRoot().getLocation();
	 String aux = rootLoc.toString();
	 aux = aux+"/";
	 aux = aux+string;
	 FileInputStream file = new FileInputStream(aux);
	 System.err.println(string);
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

  public static void main(String[] args) throws Exception {
//    new Generador().run();
  }
}
