package com.bertothy.xavier.PascalSierpinski.model;

import java.awt.Graphics2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;


public class Triangle {
	static final Logger LOG = Logger.getLogger(Triangle.class.getName());
	
	public static final int MINLEVEL;
	public static final int MAXLEVEL;
	
	private Node root;
	private int level;
	private int nodeTotal;
	
	private Graphics2D graphics;
	private Shape shape;
	private Personnalizer personnalizer;
	private ArrayList<Choice> list;
	
	static{
		URL propertiesURL = Triangle.class.getClassLoader().getResource( Triangle.class.getSimpleName() + ".properties");
		Properties defaultProps = new Properties();
		try (FileInputStream in = new FileInputStream(propertiesURL.getFile())){
			defaultProps.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		MINLEVEL = Integer.valueOf(defaultProps.getProperty("MINLEVEL"));
		MAXLEVEL = Integer.valueOf(defaultProps.getProperty("MAXLEVEL"));
	}
	
	public Triangle(int level){
		
		
		if(level<MINLEVEL || level>MAXLEVEL) 
			throw new IllegalArgumentException("Level " + level + " not valid. " + MINLEVEL + " <= level <= " + MAXLEVEL + ".");
		this.level = level;
		root = new Node(new BigInteger("1"), 1);
		nodeTotal = getTotal(level);
		build();
	}
	
	private static int getTotal(int level) {
		if (level==MINLEVEL)
			return MINLEVEL;
		else
			return level + getTotal(level-1);
	}

	public int getLevel() {
		return level;
	}

	public int getNodeTotal() {
		return nodeTotal;
	}

	public void build(){
		if(level>MINLEVEL){
			root.createChildren(level);
		}
	};

	public BigInteger[] getNumberHoryzontally(){
		ArrayList<BigInteger> list = new ArrayList<BigInteger>();
		return readHorizontally(root, list).toArray(new BigInteger[nodeTotal]);
	}
	
	public BigInteger[] getNumberDiagonally(){
		ArrayList<BigInteger> list = new ArrayList<BigInteger>();
		return readDiagonally(root, list).toArray(new BigInteger[nodeTotal]);
	}


	public static ArrayList<BigInteger>  readHorizontally(Node node, ArrayList<BigInteger> list) {
		list.add(node.getNumber());
		if (node.getrSibling() != null)
			readHorizontally(node.getrSibling(), list);
		if (node.getlChild() != null) 
			readHorizontally(node.getlChild(), list);
		return list;
	}

	public static ArrayList<BigInteger> readDiagonally(Node node, ArrayList<BigInteger> list) {
		list.add(node.getNumber());
		if (node.getrChild() != null)
			readDiagonally(node.getrChild(), list);
		if (node.getlChild() != null)
			readDiagonally(node.getlChild(), list);
		return list;
	}

	public void draw(int width, Graphics2D g, Shape shape, Personnalizer personnalizer, ArrayList<Choice> list) {
		graphics = g;
		this.shape = shape;
		this.personnalizer = personnalizer;
		this.list = list;
		draw(root, new Point((width-shape.getWidth())/2,0));
	}

	public void draw(Node node, Point point) {
		shape.setPoint(point);
		personnalizer.personnalize(graphics, shape, node, list);
//		if (node.getrSibling()!=null)
//			draw(node.getrSibling(), new Point(point.x+shape.getWidth(), point.y));
		if (node.getrChild()!=null)
			draw(node.getrChild(), new Point(point.x+shape.getWidth()/2, point.y+shape.getHeight()));
		if (node.getlChild()!=null)
			draw(node.getlChild(), new Point(point.x-shape.getWidth()/2, point.y+shape.getHeight()));
	}
}
