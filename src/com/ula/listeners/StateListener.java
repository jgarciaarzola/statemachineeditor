package com.ula.listeners;

import com.ula.models.State;
import com.ula.models.ArcShape;

public interface StateListener extends ElementListener {
	public void removeTargetConnectionListener(ArcShape conexion);
	public void addSourceListener(State source);
	public void addLoopListener(State source);
	public void addTargetListener(State target);
	public void removeSourceConnectionListener(ArcShape conexion);

}
