package com.bertothy.xavier.PascalSierpinski.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.math.BigInteger;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.bertothy.xavier.PascalSierpinski.model.Action;
import com.bertothy.xavier.PascalSierpinski.model.Choice;
import com.bertothy.xavier.PascalSierpinski.model.Shape;
import com.bertothy.xavier.PascalSierpinski.model.Square;
import com.bertothy.xavier.PascalSierpinski.model.Triangle;

public class TrianglePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int height;
	private int width;
	private Color backgroundColor;
	private String level;
	private ArrayList<Choice> choiceList;
	private Color shapeColor;
	private Color textColor;
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(width, height);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(backgroundColor);
		g2.fillRect(0, 0, width, height);

		int triangleLevel = Integer.parseInt(level);
		int squareHeight = height / triangleLevel;
		int squareWidth = squareHeight * 30 / 25;
		int fontSize = squareHeight * 16 / 50;
		g2.setFont(new Font("default", Font.BOLD, fontSize));
		Triangle t = new Triangle(triangleLevel);
		boolean isNumberVisible = (fontSize >= 8 && triangleLevel < 17) ? true
				: false;

		ArrayList<Choice> list = choiceList;

		Shape shape = new Square(squareWidth, squareHeight);
		shape.setNumberVisible(isNumberVisible);
		shape.setShapeColor(shapeColor);
		shape.setTextColor(textColor);

		t.draw(width,
				g2,
				shape,
				(graphics, selectedShape, node, choiceList) -> {
					BigInteger number = node.getNumber();
					shape.setNumber(number.toString());

					if (choiceList.size() == 0) {
						shape.draw(graphics);
					} else {
						Color color = new Color(0, 0, 0);
						for (Choice choice : choiceList) {
							if (choice.getAction() == Action.MULTIPLE
									&& number
											.mod(new BigInteger(choice
													.getNumber()))
											.equals(new BigInteger("0"))) {
								color = new Color(choice.getColor()
										.getRGB() + color.getRGB());
							}
							if (choice.getAction() == Action.EQUALS
									&& number.equals(choice.getNumber())) {
								color = new Color(choice.getColor()
										.getRGB() + color.getRGB());
							}
							if (choice.getAction() == Action.FINISHING
									&& number.toString().endsWith(
											choice.getNumber())) {
								color = new Color(choice.getColor()
										.getRGB() + color.getRGB());
							}
							if (choice.getAction() == Action.STARTING
									&& number.toString().startsWith(
											choice.getNumber())) {
								color = new Color(choice.getColor()
										.getRGB() + color.getRGB());
							}
							if (choice.getAction() == Action.CONTAINING
									&& number.toString().contains(
											choice.getNumber())) {
								color = new Color(choice.getColor()
										.getRGB() + color.getRGB());
							}
							if (choice.getAction() == Action.SUPERIOR
									&& number.compareTo(new BigInteger(
											choice.getNumber())) > 0) {
								color = new Color(choice.getColor()
										.getRGB() + color.getRGB());
							}
							if (choice.getAction() == Action.INFERIOR
									&& number.compareTo(new BigInteger(
											choice.getNumber())) < 0) {
								color = new Color(choice.getColor()
										.getRGB() + color.getRGB());
							}
						}

						if (color.equals(new Color(0, 0, 0))) {
							shape.draw(graphics);
						} else {
							Color tempShapeColor = shape
									.getShapeColor();
							Color tempTextColor = shape.getTextColor();
							shape.setShapeColor(color);
							shape.setTextColor(new Color(Color.WHITE
									.getRGB() - color.getRGB()));
							shape.fill(graphics);
							shape.setShapeColor(tempShapeColor);
							shape.setTextColor(tempTextColor);
						}
					}
				}, list);
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public String getLevel() {
		return level;
	}

	public ArrayList<Choice> getChoiceList() {
		return choiceList;
	}

	public Color getShapeColor() {
		return shapeColor;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void setChoiceList(ArrayList<Choice> choiceList) {
		this.choiceList = choiceList;
	}

	public void setShapeColor(Color shapeColor) {
		this.shapeColor = shapeColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

}
