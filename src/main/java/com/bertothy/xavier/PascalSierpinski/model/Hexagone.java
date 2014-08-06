package com.bertothy.xavier.PascalSierpinski.model;

import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

import org.apache.log4j.Logger;

public class Hexagone extends Shape {

	static Logger log = Logger.getLogger(Hexagone.class.getName());
	
	private double[] yPoints = new double[6];
	private double[] xPoints = new double[6];

	public Hexagone() {super();}
	
	public Hexagone(int width, int height) {
		super(width, height);
	}

	public void setPoint(Point point) {
		this.point = point;

		xPoints[0] = point.x;
		xPoints[1] = point.x+width/2;
		xPoints[2] = point.x+width;
		xPoints[3] = point.x+width;
		xPoints[4] = point.x+width/2;
		xPoints[5] = point.x;
		
		yPoints[0] = point.y+height/6;
		yPoints[1] = point.y-height/6;
		yPoints[2] = point.y+height/6;
		yPoints[3] = point.y+height-height/6;
		yPoints[4] = point.y+height+height/6;
		yPoints[5] = point.y+height-height/6;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(shapeColor);
		GeneralPath polygon = 
		        new GeneralPath(GeneralPath.WIND_EVEN_ODD, xPoints.length);
		polygon.moveTo(xPoints[0], yPoints[0]);

		for (int index = 1; index < xPoints.length; index++) {
		        polygon.lineTo(xPoints[index], yPoints[index]);
		};

		polygon.closePath();
		g.draw(polygon);
        if (isNumberVisible)
        	drawText(g, number);
        log.debug("draw");
	}

	@Override
	public void fill(Graphics2D g) {
		g.setColor(shapeColor);
		GeneralPath polygon = 
		        new GeneralPath(GeneralPath.WIND_EVEN_ODD, xPoints.length);
		polygon.moveTo(xPoints[0], yPoints[0]);

		for (int index = 1; index < xPoints.length; index++) {
		        polygon.lineTo(xPoints[index], yPoints[index]);
		};

		polygon.closePath();
		g.fill(polygon);
        if (isNumberVisible)
        	drawText(g, number);
        log.debug("fill:\n" + 
        	xPoints[0] + ", " + xPoints[1] + ", " + 
        	xPoints[2] + ", " + xPoints[3] + ", " + 
        	xPoints[4] + ", " + xPoints[5] + "\n" +
        	yPoints[0] + ", " + yPoints[1] + ", " + 
        	yPoints[2] + ", " + yPoints[3] + ", " + 
        	yPoints[4] + ", " + yPoints[5] + "\n");

	}

}
