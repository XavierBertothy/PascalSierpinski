package com.bertothy.xavier.PascalSierpinski.model;

import java.awt.Graphics2D;

public class Square extends Shape {
	
	public Square(){super();}

	public Square(int width, int height) {
		super(width, height);
	}
	
//	public Square(Point point, int width, int height, String number, boolean isNumberVisible, Color shapeColor, Color textColor) {
//		super(point, width, height, number, isNumberVisible,shapeColor, textColor);
//	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(shapeColor);
        g.drawRect((int)point.x,(int)point.y, width, height);
        if (isNumberVisible)
        	drawText(g, number);
	}

	@Override
	public void fill(Graphics2D g) {
		g.setColor(shapeColor);
        g.fillRect((int)point.x,(int)point.y, width, height);
        if (isNumberVisible)
        	drawText(g, number);
		
	}

}
