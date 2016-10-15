package com.ula.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.ula.dialogs.DialogBrowseDescriptor;
import com.ula.dialogs.DialogEventDescriptor;
import com.ula.dialogs.DialogPackageDescriptor;
import com.ula.listeners.CanvasListener;
import com.ula.listeners.LabelListener;

public class Canvas implements IPropertySource {

	private String name = "";
	private String pathPackage = null;
	private String targetPath = null;
	private IPath alternativeTemplateDirectory=null;
	
	private String alternativeNameTemplate = "";

	private final List<Element> elements = new ArrayList<Element>();
	private ArrayList<Event> events = new ArrayList<Event>();
	private final Collection<CanvasListener> listeners = new HashSet<CanvasListener>();

	public static final String NAME_PROP = "Canvas.Name";
	public static final String PATH_PACKAGE_PROP = "Canvas.DocType";
	public static final String EVENTS_PROP = "Canvas.Events";
	public static final String ALTERNATIVE_TEMPLATE_PROP = "Canvas.Template";

	public static IPropertyDescriptor[] descriptors;

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	public void clear() {
		elements.clear();

	}

	public String getName() {
		return name;
	}

	public String getPathPackage() {
		return pathPackage;
	}

	public void setPathPackage(String docType) {
		this.pathPackage = docType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Element> getElements() {
		return elements;
	}

	public boolean addElement(Element c) {
		if (c == null || !elements.add(c))
			return false;

		for (CanvasListener l : listeners) {
			l.elementAddedListener(c);
		}
		elements.get(elements.size() - 1).setCanvas(this);

		return true;
	}

	public boolean removeElement(Element c) {

		if (!elements.remove(c))
			return false;
		for (CanvasListener l : listeners)
			l.elementRemovedListenr(c);
		return true;
	}

	// ============================================================
	// Listeners

	public void addCanvasListener(CanvasListener l) {
		listeners.add(l);
	}

	public void removeCanvasListener(CanvasListener l) {
		listeners.remove(l);
	}

	protected void firePropertyChange(String property, Object oldValue,
			Object newValue) {
		if (propertyChangeSupport.hasListeners(property)) {
			propertyChangeSupport.firePropertyChange(property, oldValue,
					newValue);
		}
	}

	public synchronized void addPropertyChangeListener(PropertyChangeListener l) {
		if (l == null) {
			throw new IllegalArgumentException();
		}

		propertyChangeSupport.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		if (l == null) {
			throw new IllegalArgumentException();
		}

		propertyChangeSupport.removePropertyChangeListener(l);
	}

	@Override
	public Object getEditableValue() {
		return null;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		descriptors = new IPropertyDescriptor[] {

		new DialogEventDescriptor(EVENTS_PROP, "1. Events", this),
				new DialogPackageDescriptor(PATH_PACKAGE_PROP, "2. Package",this),
				new DialogBrowseDescriptor(ALTERNATIVE_TEMPLATE_PROP, "3.Alternative Template")

		};

		return descriptors;
	}

	@Override
	public Object getPropertyValue(Object propertyId) {
		
		if (NAME_PROP.equals(propertyId)) {
			return this.getName();
		} else if (PATH_PACKAGE_PROP.equals(propertyId)) {
			return this.getPathPackage();

		} else if (EVENTS_PROP.equals(propertyId)) {
			return getEvents();
		} 
		else if (ALTERNATIVE_TEMPLATE_PROP.equals(propertyId)) {
			if(alternativeTemplateDirectory != null){
				return getAlternativeNameTemplate();
			}

			
		}

		return null;
	}

	@Override
	public boolean isPropertySet(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resetPropertyValue(Object arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPropertyValue(Object propertyId, Object value) {

		if (NAME_PROP.equals(propertyId)) {
			String nameAux = ((String) value);
			this.setName(nameAux);
		} else if (PATH_PACKAGE_PROP.equals(propertyId)) {
			this.setPathPackage((String) value);
		} else if (EVENTS_PROP.equals(propertyId)) {
			ArrayList<Event> eventList = ((ArrayList<Event>) value);
			this.setEvents(eventList);
		} else if (ALTERNATIVE_TEMPLATE_PROP.equals(propertyId)) {
			IPath path = (IPath) value;

			if (path.toString().equals("ERROR")) {

				alternativeTemplateDirectory = null;
				alternativeNameTemplate = null;
				return;
			}
			this.setAlternativeNameTemplate(path.lastSegment());
			this.setAlternativeTemplateDirectory(path.removeLastSegments(1));

		}
	}

	public ArrayList<Event> getEvents() {
		return events;
	}

	public void setEvents(ArrayList<Event> eventList) {
		this.events = eventList;
	}

	public String getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(String targetPath) {

		this.targetPath = targetPath;

	}

	public String getAlternativeNameTemplate() {
		return alternativeNameTemplate;
	}

	public void setAlternativeNameTemplate(String alternativeNameTemplate) {
		this.alternativeNameTemplate = alternativeNameTemplate;
	}
	
	public IPath getAlternativeTemplateDirectory() {
		return alternativeTemplateDirectory;
	}

	public void setAlternativeTemplateDirectory(IPath alternativeTemplateDirectory) {
		this.alternativeTemplateDirectory = alternativeTemplateDirectory;
	}


	
}
