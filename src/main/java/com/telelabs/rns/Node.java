package com.telelabs.rns;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Jeyakumari
 *
 */
public class Node {
	private String name;
	private List<Node> children;
	private Node right;
	
	public Node(String name) {
		super();
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the children
	 */
	public List<Node> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<Node> children) {
		this.children = children;
	}

	/**
	 * @return the right
	 */
	public Node getRight() {
		return right;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(Node right) {
		this.right = right;
	}

	/**
	 * Returns true if the node has children
	 * @return
	 */
	public boolean hasChildren() {
		return (children != null && children.size() > 0);
	}
	
	/**
	 * Adds a child node to the node
	 * @param child
	 */
	public void addChild(Node child) {
		if(children == null) {
			children = new ArrayList<>();
		}
		children.add(child);
	}

	@Override
	public String toString() {
		return "Node [name=" + name + ", right=" + (right != null ? right.name : "null") + "]";
	}

}
