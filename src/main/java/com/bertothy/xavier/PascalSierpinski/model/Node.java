package com.bertothy.xavier.PascalSierpinski.model;

import java.math.BigInteger;

import org.apache.log4j.Logger;

public class Node {
	static Logger log = Logger.getLogger(Node.class.getName());
	
	private BigInteger number;
	private Node lChild;
	private Node rChild;
	private Node rSibling;
	private int level;
	
	public Node(BigInteger number, int level) {
		this.number = number;
		this.level = level;
	}

	public Node(BigInteger number, int level, Node rSibling) {
		this.number = number;
		this.level = level;
		this.rSibling = rSibling;
	}

	public BigInteger getNumber() {
		return number;
	}

	public Node getlChild() {
		return lChild;
	}

	public void setlChild(Node lChild) {
		this.lChild = lChild;
	}

	public Node getrChild() {
		return rChild;
	}

	public void setrChild(Node rChild) {
		this.rChild = rChild;
	}

	public Node getrSibling() {
		return rSibling;
	}

	public void setrSibling(Node rSibling) {
		this.rSibling = rSibling;
	}

	public int getLevel() {
		return level;
	}

	public void createChildren(int triangleLevel) {
		if(level<triangleLevel){
			Node rSiblingrChild = null;
			BigInteger rSiblingNumber = new BigInteger("0");
			if (rSibling!=null){ 
				rSiblingrChild = rSibling.rChild;
				rSiblingNumber = rSibling.number;
			}
			rChild = new Node(number.add(rSiblingNumber), level+1, rSiblingrChild);
			if ((number.equals(new BigInteger("1")) && (level==1 || rSibling!=null && level!=1)))
				lChild = new Node(number, level+1, rChild);
			rChild.createChildren(triangleLevel);
			if(lChild!=null)
				lChild.createChildren(triangleLevel);
		}
	}
	
	@Override
	public String toString() {
		return "Node [Num=" + number + "/Lev=" + level + "/LChld=" + exits(lChild)+ "/RChld=" + exits(rChild) + "/RSblng=" + exits(rSibling) + "]";
	}
	
	private static String exits(Node node){
		return (node==null)?null:"exists";
	}
	
	
	
}
