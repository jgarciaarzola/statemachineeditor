package com.ula.listeners;

public interface LabelListener extends ElementListener{
	void textChangedListener(String text);
	void setSelection(boolean isSelected);
}
