package com.bertothy.xavier.PascalSierpinski.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.apache.log4j.Logger;

public class CellColorRenderer implements ListCellRenderer<Color> {

	static Logger log = Logger.getLogger(CellColorRenderer.class.getName());
	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

	@Override
	public Component getListCellRendererComponent(JList<? extends Color> list,
			Color value, int index, boolean isSelected, boolean cellHasFocus) {

	    JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
	        isSelected, cellHasFocus);
	    
	    Color color = value;
	    Icon icon = new MyIcon(color);
	    renderer.setIcon(icon);
	    renderer.setText("");

	    return renderer;
	}

	class MyIcon implements Icon {

		private Color color;

		public MyIcon(Color color) {
			this.color = color;
		}

		public int getIconHeight() {
			return 25;
		}

		public int getIconWidth() {
			return 25;
		}

		public void paintIcon(Component c, Graphics g, int x, int y) {
			g.setColor(color);
			g.fillRect(0, 0, 25, 25);
		}
	}

}
