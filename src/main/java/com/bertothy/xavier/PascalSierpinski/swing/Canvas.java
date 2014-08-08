package com.bertothy.xavier.PascalSierpinski.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
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

	public static final int COMMANDWIDTH;	
	public static final int COMMANDHEIGHT;
	public static final int TRIANGLEWIDTH;
	public static final int TRIANGLEHEIGHT;
	public static final Color BACKGROUNDCOLOR;
	public static final Color SHAPECOLOR;
	public static final Color TEXTCOLOR;
	public static final Color[] COLORARRAY;
	public static final CellColorRenderer CELLCOLORRENDERER = new CellColorRenderer();
	public static final Properties defaultProps = new Properties();
	
	static{
		URL propertiesURL = Canvas.class.getClassLoader().getResource( Canvas.class.getSimpleName() + ".properties");
		try (FileInputStream in = new FileInputStream(propertiesURL.getFile())){
			defaultProps.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		COMMANDWIDTH = Integer.valueOf(defaultProps.getProperty("COMMANDWIDTH"));	
		COMMANDHEIGHT = Integer.valueOf(defaultProps.getProperty("COMMANDHEIGHT"));
		TRIANGLEWIDTH = Integer.valueOf(defaultProps.getProperty("TRIANGLEWIDTH"));
		TRIANGLEHEIGHT = Integer.valueOf(defaultProps.getProperty("TRIANGLEHEIGHT"));
		BACKGROUNDCOLOR = createColor(defaultProps.getProperty("BACKGROUNDCOLOR"));
		SHAPECOLOR = createColor(defaultProps.getProperty("SHAPECOLOR"));
		TEXTCOLOR = createColor(defaultProps.getProperty("TEXTCOLOR"));
		COLORARRAY = createColorArray(defaultProps.getProperty("COLORARRAY"));
		
		System.out.println("DARK_GRAY" + Color.DARK_GRAY);
		System.out.println("RED" + Color.RED);
		System.out.println("ORANGE" + Color.ORANGE);
		System.out.println("YELLOW" + Color.YELLOW);
		System.out.println("GREEN" + Color.GREEN);
		System.out.println("CYAN" + Color.CYAN);
		System.out.println("BLUE" + Color.BLUE);
		System.out.println("PINK" + Color.PINK);
		System.out.println("MAGENTA" + Color.MAGENTA);
	}
	
	public static void main(String[] args) throws IOException { 

		//put here to be visible from all
		JPanel choicesPanel = new JPanel();
		String level = defaultProps.getProperty("LEVEL"); 
		
		JFrame frame = new JFrame("Pascal Sierpinski");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(TRIANGLEWIDTH+COMMANDWIDTH, TRIANGLEHEIGHT);
		Container container = frame.getContentPane();
		container.setLayout(new BorderLayout());

		//Triangle panel
		TrianglePanel trianglePanel = new TrianglePanel();
		trianglePanel.setBackgroundColor(BACKGROUNDCOLOR);
		trianglePanel.setShapeColor(SHAPECOLOR);
		trianglePanel.setTextColor(TEXTCOLOR);
		trianglePanel.setWidth(TRIANGLEWIDTH);
		trianglePanel.setHeight(TRIANGLEHEIGHT);
		trianglePanel.setLevel(level);
		trianglePanel.setChoiceList(new ArrayList<Choice>());
		
		container.add(trianglePanel, BorderLayout.CENTER);
		//General panel
		JLabel levelLabel = new JLabel("Level: ");
		JTextField levelField = new JTextField(8);
		levelField.setText(level);
		
		JPanel levelPanel = new JPanel();
		levelPanel.setLayout(new BorderLayout());
		levelPanel.add(levelLabel, BorderLayout.WEST);
		levelPanel.add(levelField, BorderLayout.CENTER);

		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(e -> {
			String selectedlevel = levelField.getText();
			
			if (selectedlevel.trim().equals("") || !isNumber(selectedlevel) || !isLevelCorrect(selectedlevel)){
				JOptionPane.showMessageDialog(frame,
					    "Level cannot be empty.\n" + "Level must be a positive number such as :\n" + 
					    Triangle.MINLEVEL + " <= Level <= " + Triangle.MAXLEVEL + "\n" 
					    + "Value entered: " + level,
					    "Level error",
					    JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			ArrayList<Choice> choiceList = new ArrayList<Choice>();
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
			
			LOG.info("level=" + selectedlevel);
			if(choiceList!=null)
				for(Choice choice:choiceList)
					LOG.info(choice);
			LOG.info("submit");
			trianglePanel.setLevel(selectedlevel);
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
			colorComboBox.setRenderer(CELLCOLORRENDERER);

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
		choicesPanel.setLayout(new FlowLayout());

		JPanel commandPanel = new JPanel();
		commandPanel.setLayout(new BorderLayout());
		commandPanel.add(generalPanel, BorderLayout.NORTH);
		commandPanel.add(choicesPanel, BorderLayout.CENTER);
		commandPanel.setPreferredSize(new Dimension(COMMANDWIDTH, COMMANDHEIGHT));

		container.add(commandPanel, BorderLayout.WEST);

		frame.setVisible(true);
	}

	private static boolean isNumber(String level) {
		return Pattern.matches("\\d+", level.trim());
	}

	private static boolean isLevelCorrect(String level) {
		int levelInt = Integer.valueOf(level);
		return levelInt>=Triangle.MINLEVEL && levelInt<=Triangle.MAXLEVEL;
	}
		
	private static Color[] createColorArray(String value) {
		String[] stringArray = value.split(";");
		Color[] result = new Color[stringArray.length];
		for(int x=0; x<result.length; x++)
			result[x] = createColor(stringArray[x]);
		return result;
	}
	
	private static Color createColor(String colorNumber){
		String[] stringArray = colorNumber.split(",");
		int[] intArray = stringToInt(stringArray);
		return new Color(intArray[0], intArray[1], intArray[2]);
	}

	private static int[] stringToInt(String[] stringArray) {
		int[] result = new int[stringArray.length];
		for(int x=0; x<result.length; x++)
			result[x] = Integer.valueOf(stringArray[x]);
		return result;
	}

}
