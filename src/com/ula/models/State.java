package com.ula.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;


import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.ula.listeners.ElementListener;
import com.ula.listeners.LabelListener;
import com.ula.listeners.StateListener;




public class State extends Element implements IPropertySource  {

	
	
	private List<StateListener> listeners = new ArrayList<StateListener>();
	
	private List<ArcShape> sourceConnections = new ArrayList<ArcShape>();
	private List<ArcShape> targetConnections = new ArrayList<ArcShape>();
	private LabelState name;
	
	private Integer type;
	public static Integer FINAL = new Integer(1);
	public static Integer NORMAL = new Integer(0);
	public static Integer INITIAL = new Integer(2);
	
	
	public static final String NAME_PROP = "Lugar.Name";
	public static final String ID_ESTATE = "Lugar";
	
	private static IPropertyDescriptor[] descriptors;
	
	
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	static {
		descriptors = new IPropertyDescriptor[] {
				new TextPropertyDescriptor(NAME_PROP, "1.Name"),
				new ComboBoxPropertyDescriptor(
						ID_ESTATE,
						"2. Type",
						new String[] {
								"Normal",
								"Final",
								"Initial"
								 }),

			
				
		};
	}
	
	public State() {

		this.name = new LabelState();
		this.setType(State.NORMAL);
	}
	
	public State(String name, int x,int y,int w,int h) {
	
		
		this.x= x;
		this.y= y;
		this.w = w;
		this.h = h;
		this.name = new LabelState();
		this.name.setText(name);
	}
	
	public List<ArcShape> getSourceConnections() {
		return sourceConnections;
	}
	
	public List<ArcShape> getTargetConnections() {
		return targetConnections;
	}
	
	public Integer getType(){
		return type;
	}
	
	public void setType(Integer type){
		this.type = type;
		firePropertyChange(ID_ESTATE, null, null);
	}
	
	
	public void addConnection(ArcShape conn) {
		
		
		if (conn == null) {
			throw new IllegalArgumentException();
		}
		if (conn.getSource() == conn.getTarget()) {
			sourceConnections.add(conn);
			targetConnections.add(conn);
			for (StateListener l : listeners) {
				l.addLoopListener(conn.getSource());
			}
			return;

		}
		if (conn.getSource() == this) {
			
			sourceConnections.add(conn);
			for (StateListener l : listeners) {
				l.addSourceListener(conn.getSource());
			}
		} else if (conn.getTarget() == this) {
			
			targetConnections.add(conn);
			for (StateListener l : listeners) {
				l.addTargetListener(conn.getTarget());
			}
		}
	}
	
		
	
	public void removeConnection(ArcShape conn) {
		if (conn == null) {
			throw new IllegalArgumentException();
		}
		if(conn.getSource() == conn.getTarget()){
			sourceConnections.remove(conn);
			targetConnections.remove(conn);
			for(StateListener l: listeners){
				l.addLoopListener(conn.getSource());
			}
			return;
		}
		if (conn.getSource() == this) {
			sourceConnections.remove(conn);
			
			for(StateListener l: listeners){
				l.removeTargetConnectionListener(conn);
			}
		} else if (conn.getTarget() == this) {
			targetConnections.remove(conn);
			
			for(StateListener l: listeners){
				l.removeSourceConnectionListener(conn);
			}
		}
	}
	
	
	public void addStateListener(StateListener l) {
	     listeners.add(l);
	}
	
	public void removeStateListener(StateListener l){
		listeners.remove(l);
	}
	
	protected void fireLocationChanged(int newX, int newY) {
		for (ElementListener l : listeners)
			l.changeLocationListener(newX, newY);
	}

	protected void fireSizeChanged(int newWidth, int newHeight) {
		for (ElementListener l : listeners)
			l.changeSizeListener(newWidth, newHeight);
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
		return descriptors;
	}

	@Override
	public Object getPropertyValue(Object propertyId) {
		
		if (NAME_PROP.equals(propertyId)) {
			return this.getLabelState().getText();
		}
		else if(ID_ESTATE.equals(propertyId)){
			return this.getType();
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
			if (this.getLabelState().isWord(nameAux)) {
				this.getLabelState().setText(nameAux);
				int lenght = this.getLabelState().getText().length();
				this.getLabelState().setSize(lenght*10, 20);
			}
			

		}
		if(ID_ESTATE.equals(propertyId)){
			if((Integer)value == 0){
				this.setType(State.NORMAL);
			}
			if((Integer) value == 1){
				this.setType(State.FINAL);
			}
			if((Integer) value == 2){
				this.setType(State.INITIAL);
			}
			
		}
	}
	
	public LabelBase getLabelState(){
		return name;
	}
	public void setName(LabelState name){
		this.name = name;
	}
	
	

	
	

	
}
