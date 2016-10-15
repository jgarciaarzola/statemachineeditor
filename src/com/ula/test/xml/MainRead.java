package com.ula.test.xml;
import java.io.InputStream;

import javax.swing.text.StyleContext.SmallAttributeSet;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;


import com.ula.xml.ObjectFactory;
import com.ula.xml.State;
import com.ula.xml.StateMachine;





public class MainRead {
	public static void main(String[] args) throws Exception {

	    // ----------------------------------------
	    // Obtener el InputStream
	    // ----------------------------------------

	    InputStream is = //
	    ClassLoader.getSystemResourceAsStream( //
	        "com/ula/test/xml/statemachine.xml");

	    // ----------------------------------------
	    // Inicializar JAXB y leer el XML
	    // ----------------------------------------

	    JAXBContext jc = JAXBContext.newInstance( //
	        ObjectFactory.class.getPackage().getName());

	    Unmarshaller unmarshaller = jc.createUnmarshaller();

	    StateMachine xmlStateMachine = (StateMachine) unmarshaller.unmarshal(is);

	    // ----------------------------------------
	    // Procesar el arbol de objetos generado
	    // ----------------------------------------

	    System.err.println("Nombre de el editor: " + xmlStateMachine.getName());
	    

	    for (State s : xmlStateMachine.getState()) {
//	    	System.err.println("name "+s.getName());
	    	System.err.println("type "+s.getType());
	      System.err.println("cordX "+s.getX());
	      System.err.println("cordY "+s.getY());
	      System.err.println("cordW "+s.getW());
	      System.err.println("cordH "+s.getH());
	          
	    }
//	    for(Transition t: xmlStateMachine.getTransition()){
//	    	System.err.println("name of connection "+t.getName());
//	    	System.err.println("event "+t.getEvent());
//	    	System.err.println("source "+t.getSource().getState().getName());
//	    	System.err.println("target "+t.getTarget().getState().getName());
//	    	
//	    }
	    System.err.println("*****EVENTOS*****");
	    
//	    for(Event e:xmlStateMachine.getEvent()){
//	    	System.err.println("event canvas "+e.getName());
//	    }

	   


	
	

}
	
}
