package com.bertothy.xavier.PascalSierpinski.model;

import java.awt.Graphics2D;
import java.util.ArrayList;

public interface Personnalizer {
	void personnalize(Graphics2D graphics, Shape shape, Node node, ArrayList<Choice> list);
}
