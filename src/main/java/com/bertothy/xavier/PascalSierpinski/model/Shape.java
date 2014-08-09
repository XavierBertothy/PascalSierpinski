package com.bertothy.xavier.PascalSierpinski.model;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Shape {
	Point point;
	int width;
	int height;
	String number;
	boolean isNumberVisible;
	Color shapeColor;
	Color textColor;

	public Shape() {
		super();
	}

	public void setPoint(Point point) {
		this.point = point;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public Color getShapeColor() {
		return shapeColor;
	}

	public Color getTextColor() {
		return textColor;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}

	public void setNumberVisible(boolean isNumberVisible) {
		this.isNumberVisible = isNumberVisible;
	}

	public void setShapeColor(Color shapeColor) {
		this.shapeColor = shapeColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public abstract void draw(Graphics2D g);
	
	public abstract void fill(Graphics2D g);

	public void drawText(Graphics2D g, String number) {
		g.setColor(textColor);
		int x = 0;
		switch (number.length()) {
		case 1:
			x = 22; break;
		case 2:
			x = 17; break;
		case 3:
			x = 12; break;
		default:
			x = 10;
		}
		g.drawString(number, (int)(point.x+(width*x/50)), (int)(point.y+(height*30/50)));
	}

	public static Shape loadShape(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}
