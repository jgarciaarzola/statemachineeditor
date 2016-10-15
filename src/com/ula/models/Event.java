package com.ula.models;

import java.util.ArrayList;

public class Event {
	private String name;
	private ArrayList<ArcShape> listConnection = new ArrayList<ArcShape>();

	
	public Event(){
		
	}
	
	public Event(String name){
		this.name = name;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<ArcShape> getListConnection() {
		return listConnection;
	}

	public void setListConnection(ArrayList<ArcShape> listConnection) {
		this.listConnection = listConnection;
	}

}
