package com.bertothy.xavier.PascalSierpinski.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import com.bertothy.xavier.PascalSierpinski.model.Action;
import com.bertothy.xavier.PascalSierpinski.model.Choice;
import com.bertothy.xavier.PascalSierpinski.model.Triangle;

public class Canvas {

	public static final Logger LOG = Logger.getLogger(Canvas.class.getName());

	public static final int COMMANDWIDTH = 450;	
	public static final int COMMANDHEIGHT = 900;
	public static final int TRIANGLEWIDTH = 1350;
	public static final int TRIANGLEHEIGHT = 900;
	public static final Color BACKGROUNDCOLOR = Color.BLACK;
	public static final Color SHAPECOLOR = Color.DARK_GRAY;
	public static final Color TEXTCOLOR = Color.WHITE;
	public static final Color[] COLORARRAY = { Color.RED, Color.ORANGE, Color.YELLOW,
		Color.GREEN, Color.CYAN, Color.BLUE, Color.PINK, Color.MAGENTA };
	public static final CellColorRenderer CELLCOLORRENDER = new CellColorRenderer();
	
	public static String level = "10";
	public static ArrayList<Choice> choiceList = new ArrayList<Choice>();
	
	public static TrianglePanel trianglePanel;
	public static JTextField levelField;
	public static JPanel choicesPanel;

	public static void main(String[] args) throws IOException { 

		JFrame frame = new JFrame("Pascal Sierpinski");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(TRIANGLEWIDTH+COMMANDWIDTH, TRIANGLEHEIGHT);
		Container container = frame.getContentPane();
		container.setLayout(new BorderLayout());

		//General panel
		JLabel levelLabel = new JLabel("Level: ");
		levelField = new JTextField(8);
		levelField.setText(level);
		
		JPanel levelPanel = new JPanel();
		levelPanel.setLayout(new BorderLayout());
		levelPanel.add(levelLabel, BorderLayout.WEST);
		levelPanel.add(levelField, BorderLayout.CENTER);

		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(e -> {
			level = levelField.getText();
			
			if (level.trim().equals("") || !isNumber(level) || !isLevelCorrect(level)){
				JOptionPane.showMessageDialog(frame,
					    "Level cannot be empty.\n" + "Level must be a positive number such as :\n" + 
					    Triangle.MINLEVEL + " <= Level <= " + Triangle.MAXLEVEL + "\n" 
					    + "Value entered: " + level,
					    "Level error",
					    JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			choiceList = new ArrayList<Choice>();
			for(int x = 0; x < choicesPanel.getComponentCount(); x++){
				JPanel row = (JPanel)choicesPanel.getComponent(x);
				Choice choice = new Choice();
				choice.setAction(Action.valueOf((String)((JComboBox<String>)row.getComponent(0)).getSelectedItem()));
				JPanel numberPanel = (JPanel)row.getComponent(1);
				String number = ((JTextField)numberPanel.getComponent(1)).getText();
				if (number.trim().equals("") || !isNumber(number)){
					JOptionPane.showMessageDialog(frame,
						    "Number cannot be empty.\n" + "Number must be positive.\n" 
						    + "Value entered: " + number,
						    "Number error",
						    JOptionPane.ERROR_MESSAGE);
					return;
				}
				choice.setNumber(((JTextField)numberPanel.getComponent(1)).getText());
				JPanel colorPanel = (JPanel)row.getComponent(2);
				choice.setColor((Color)((JComboBox<Color>)colorPanel.getComponent(1)).getSelectedItem());
				choiceList.add(choice);
			}
			
			LOG.info("level=" + level);
			if(choiceList!=null)
				for(Choice choice:choiceList)
					LOG.info(choice);
			LOG.info("submit");
			trianglePanel.setLevel(level);
			trianglePanel.setChoiceList(choiceList);
			trianglePanel.revalidate();  
			trianglePanel.repaint();
		});
		
		JButton plusButton = new JButton("+");
		plusButton.addActionListener(e -> {
			//Choice panel
			JComboBox<String> actionsComboBox = new JComboBox<>(Action.getValues());
			actionsComboBox.setSelectedIndex(0);

			JLabel numbersLabel = new JLabel("Numbers: ");
			JTextField numbersField = new JTextField(8);
			numbersField.setText("3");

			JPanel numbersPanel = new JPanel();
			numbersPanel.setLayout(new BorderLayout());
			numbersPanel.add(numbersLabel, BorderLayout.WEST);
			numbersPanel.add(numbersField, BorderLayout.CENTER);

			JLabel colorLabel = new JLabel("Colors: ");
			JComboBox<Color> colorComboBox = new JComboBox<>(COLORARRAY);
			colorComboBox.setRenderer(CELLCOLORRENDER);

			JPanel colorPanel = new JPanel();
			colorPanel.setLayout(new BorderLayout());
			colorPanel.add(colorLabel, BorderLayout.WEST);
			colorPanel.add(colorComboBox, BorderLayout.CENTER);

			JButton minusButton = new JButton("-");
			minusButton.addActionListener(ev -> {
				JButton selectedMinusButton = (JButton)ev.getSource();
				for(int x = 0; x < choicesPanel.getComponentCount(); x++){
					JPanel row = (JPanel)choicesPanel.getComponent(x);
					if (row.getComponent(3) instanceof JButton && ((JButton)row.getComponent(3)).equals(selectedMinusButton)){
						choicesPanel.remove(x);
						choicesPanel.revalidate();  
						choicesPanel.repaint();
						break;  
					}
				}
			});
			
			JPanel choicePanel = new JPanel();
			choicePanel.setLayout(new FlowLayout());
			choicePanel.add(actionsComboBox);
			choicePanel.add(numbersPanel);
			choicePanel.add(colorPanel);
			choicePanel.add(minusButton);
			choicesPanel.add(choicePanel);	
			
			frame.setVisible(true);
		});
		
		JPanel generalPanel = new JPanel();
		generalPanel.setLayout(new FlowLayout());
		generalPanel.add(levelPanel);
		generalPanel.add(submitButton);
		generalPanel.add(plusButton);
		
		//Choices panel
		choicesPanel = new JPanel();
		choicesPanel.setLayout(new FlowLayout());

		JPanel commandPanel = new JPanel();
		commandPanel.setLayout(new BorderLayout());
		commandPanel.add(generalPanel, BorderLayout.NORTH);
		commandPanel.add(choicesPanel, BorderLayout.CENTER);
		commandPanel.setPreferredSize(new Dimension(COMMANDWIDTH, COMMANDHEIGHT));

		container.add(commandPanel, BorderLayout.WEST);

		//Triangle panel
		trianglePanel = new TrianglePanel();
		trianglePanel.setBackgroundColor(BACKGROUNDCOLOR);
		trianglePanel.setShapeColor(SHAPECOLOR);
		trianglePanel.setTextColor(TEXTCOLOR);
		trianglePanel.setWidth(TRIANGLEWIDTH);
		trianglePanel.setHeight(TRIANGLEHEIGHT);
		trianglePanel.setLevel(level);
		trianglePanel.setChoiceList(choiceList);
		container.add(trianglePanel, BorderLayout.CENTER);

		frame.setVisible(true);
	}

	private static boolean isNumber(String level) {
		return Pattern.matches("\\d+", level.trim());
	}

	private static boolean isLevelCorrect(String level) {
		int levelInt = Integer.valueOf(level);
		return levelInt>=Triangle.MINLEVEL && levelInt<=Triangle.MAXLEVEL;
	}

}
