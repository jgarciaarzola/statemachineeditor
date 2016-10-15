
package com.ula.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.ula.listeners.ArcListener;
import com.ula.listeners.LabelListener;

public class ArcShape implements IPropertySource {

	private boolean isConnected;

	private State source;

	private State target;

	private Event event;

	private Canvas canvas;

	private int comboOption;

	private List<ArcShapeBendPoint> bendPoints = new ArrayList<ArcShapeBendPoint>();

	
	public static final String ID_EVENT = "Connection.Event";

	private static IPropertyDescriptor[] descriptors;

	private LabelEvent labelEvent = new LabelEvent();

	private List<ArcListener> listeners = new ArrayList<ArcListener>();

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);
	
	

	public List<ArcShapeBendPoint> getBendPoints() {
		return bendPoints;

	}
	public List<ArcListener> getConnectionListeners(){
		return listeners;
	}

	public ArcShape(State source, State target) {
		comboOption = 0;
		event = new Event();
		reconnect(source, target);
//		hadEvent();
		

	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void addArcListener(ArcListener c) {
		listeners.add(c);
	}

	public void removeArcListener(ArcListener c) {
		listeners.remove(c);
	}

	public void disconnect() {
		if (isConnected) {
			if (source == target) {
				source.removeConnection(this);
				isConnected = false;
				validateEvents();
				return;
			}
			source.removeConnection(this);
			target.removeConnection(this);
			isConnected = false;
			validateEvents();
		}
	}

	public State getSource() {
		return source;
	}

	public State getTarget() {
		return target;
	}



	public void reconnect() {
		
		if (!isConnected) {
			if (source == target) {
				source.addConnection(this);
				isConnected = true;
				hadEvent();
				validateEvents();
				return;
			}
			source.addConnection(this);
			target.addConnection(this);
			isConnected = true;
			hadEvent();
			validateEvents();
		}
	}

	private void reconnect(State newSource, State newTarget) {
		if (newSource == null || newTarget == null) {
			throw new IllegalArgumentException();
		}
		if (newSource == newTarget) {
			this.source = newSource;
			this.target = newTarget;
			
		}
		disconnect();
		this.source = newSource;
		this.target = newTarget;
		reconnect();
	}

	

	

	@Override
	public Object getEditableValue() {

		return null;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		int tam = canvas.getEvents().size();
		String propertyEvents[];
		propertyEvents = new String[tam+1];
		int i = 1;
		propertyEvents[0] ="select"; 
		for (Event e : canvas.getEvents()) {
			String s = e.getName();
			propertyEvents[i] = s;
			i++;

		}

		descriptors = new IPropertyDescriptor[] {
//				new TextPropertyDescriptor(NAME_PROP, "1.Name"),
				new ComboBoxPropertyDescriptor(ID_EVENT, "1. Event",
						propertyEvents)

		};

		return descriptors;
	}

	@Override
	public Object getPropertyValue(Object propertyId) {
		
		if (ID_EVENT.equals(propertyId)) {
			return getComboOption();
		}
		
		return null;
	}

	@Override
	public boolean isPropertySet(Object arg0) {

		return false;
	}

	@Override
	public void resetPropertyValue(Object arg0) {

	}

	@Override
	public void setPropertyValue(Object propertyId, Object value) {
		
		if (ID_EVENT.equals(propertyId)) {
			setComboOption((Integer) value);

		}

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

	public LabelBase getLabelEvent() {
		return labelEvent;
	}

	

	public void insertBendpoint(int index, Bendpoint point) {
		getBendPoints().add(index, (ArcShapeBendPoint) point);
		for (ArcListener c : listeners)
			c.bendPointChangeListener();

	}

	public void removeBendpoint(int index) {
		getBendPoints().remove(index);
		for (ArcListener c : listeners)
			c.bendPointChangeListener();
	}

	public void setBendpoint(int index, Bendpoint point) {
		getBendPoints().set(index, (ArcShapeBendPoint) point);
		for (ArcListener c : listeners)
			c.bendPointChangeListener();
	}

	public void setBendpoints(Vector points) {
		bendPoints = points;
		for (ArcListener c : listeners)
			c.bendPointChangeListener();
	}

	public int getComboOption() {
		return comboOption;
	}

	public void setComboOption(int comboOption) {
		this.comboOption = comboOption;
		canvas.removeElement(getLabelEvent());
		if (comboOption == 0) {
			hadEvent();
			return;
		} else {
			setEvent(canvas.getEvents().get(comboOption - 1));
		}
		if (source == target) {

			getLabelEvent().setLocation(source.getX(), source.getY() + 50);

		} else {

			getLabelEvent().setLocation(
					(getSource().getX() + getTarget().getX()) / 2,
					(getSource().getY() + getTarget().getY()) / 2);

		}
		
		int lenght = labelEvent.getText().length();
		labelEvent.setSize(lenght * 10, 20);
		canvas.addElement(getLabelEvent());
		validateEvents();
		firePropertyChange(ID_EVENT, null, comboOption);
	}

	private void validateEvents() {

		for (ArcShape c : source.getSourceConnections()) {

			if (c == this) {
				continue;
			}

			if (c.getEvent().equals(this.getEvent())) {

				for (ArcShape conn : source.getSourceConnections()) {
					if (conn.getEvent().getName() != null) {
						for (ArcListener l : conn
								.getConnectionListeners()) {
							l.validateEventsListener(true);
						}
					}

				}
				break;

			}

		}
		int k = 0;
		for (ArcShape c1 : source.getSourceConnections()) {
			for (ArcShape c2 : source.getSourceConnections()) {

				if (!c1.getEvent().equals(c2.getEvent())) {
					k++;
				}
			}
		}

		if (k == source.getSourceConnections().size()
				* (source.getSourceConnections().size() - 1)) {
			for (ArcShape c : source.getSourceConnections()) {
				if (c.getEvent().getName() != null) {
					for (ArcListener l : c.getConnectionListeners()) {
						l.validateEventsListener(false);
					}
				}

			}
		}

	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
		this.event.getListConnection().add(this);
		labelEvent.setText(this.event.getName());
		
	}
	private void hadEvent(){
		if(this.getEvent().getName() == null|| this.getComboOption() ==0){
			for(ArcListener l : listeners)
				l.hadEventsListener();
		}		
		else{
			for(ArcListener l: listeners)
				l.validateEventsListener(false);
		}
	}

}