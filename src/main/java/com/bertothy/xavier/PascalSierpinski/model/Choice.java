package com.bertothy.xavier.PascalSierpinski.model;

import java.awt.Color;

public class Choice {
	private Action action;
	private String number;
	private Color color;
	
	public Choice() {}

	public Choice(Action action, String number, Color color) {
		super();
		this.action = action;
		this.number = number;
		this.color = color;
	}

	public Action getAction() {
		return action;
	}

	public String getNumber() {
		return number;
	}

	public Color getColor() {
		return color;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Choice [" + action + "/" + number + "/"
				+ color + "]";
	}
	
	
}
