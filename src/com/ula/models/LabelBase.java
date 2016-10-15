package com.ula.models;

import java.util.*;

import com.ula.listeners.ElementListener;
import com.ula.listeners.LabelListener;
import com.ula.parts.LabelBaseEditPart;



public  class LabelBase extends Element
{

	private String text = "";
	private final Collection<LabelListener> listeners = new HashSet<LabelListener>();
	
	public LabelBase() {
	}

	public LabelBase(String text) {
		setText(text);
	}

	public String getText() {
		return text;
	}
	
	public boolean setText(String newText) {
		if (newText == null)
			newText = "";
		if (text.equals(newText))
			return false;
		text = newText;
		
		if(!isWord(newText)){
			System.out.println("no es palabra!!!!!");
			return false;
			
		}
		for (LabelListener l : listeners)
			 l.textChangedListener(text);
		return true;
	}

	//============================================================
	// Listeners
	
	public void addLabelListener(LabelListener labelListener) {
		listeners.add(labelListener);
	}
	
	public void removeLabelListener(LabelListener l) {
		listeners.remove(l);
	}

	// Element
	@Override
	protected void fireLocationChanged(int x, int y) {
		for (ElementListener l : listeners)
			l.changeLocationListener(x, y);		
	}

	@Override
	protected void fireSizeChanged(int w, int h) {
		for (ElementListener l : listeners)
			l.changeSizeListener(w, h);		
	}
	
	public boolean isWord(String word) {
		for (int i = 0; i < word.length(); i++) {

			if (word.charAt(i) >= 32 && word.charAt(i) <= 47){
				return false;
			}
		}
		return true;
	}

	public Collection<LabelListener> getListeners() {
		return listeners;	
		
	}

}
