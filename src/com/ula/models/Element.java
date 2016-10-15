package com.ula.models;

public abstract class Element {
	protected int x;
	protected int y;
	protected int w;
	protected int h;
	protected Canvas canvas;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}
	protected abstract void  fireLocationChanged(int x,int y);
	
	protected abstract void fireSizeChanged(int w, int h);
	
	public boolean setLocation(int newX, int newY) {
		if (x == newX && y == newY)
			return false;
		x = newX;
		y = newY;
		fireLocationChanged(x, y);
		return true;
	}
	public boolean setSize(int newWidth, int newHeight) {
		if (w == newWidth && h == newHeight)
			return false;
		w = newWidth;
		h = newHeight;
		fireSizeChanged(w, h);
		return true;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}
	
	
	
	
	

}
