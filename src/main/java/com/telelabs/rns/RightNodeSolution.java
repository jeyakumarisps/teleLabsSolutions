package com.telelabs.rns;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;

/**
 * 
 * @author Jeyakumari
 *
 */
public class RightNodeSolution {
	private final static Logger logger = Logger.getLogger(RightNodeSolution.class);
	
	/**
	 * sets the right node for all nodes of the tree with given root node
	 * 
	 * @param rootNode
	 */
	public void setRightNodes(Node rootNode) {
		Objects.requireNonNull(rootNode);

		List<Node> children = new ArrayList<>();
		children.add(rootNode);

		// set right for all children at each level
		while (children.size() > 0) {
			List<Node> allChildren = new ArrayList<>();

			for (Node child : children) {
				if (child.hasChildren()) {
					setRightForChildren(child);
					allChildren.addAll(child.getChildren());
				}
			}
			children = allChildren;
		}
	}
	
	/**
	 * Print all nodes of the tree with the given root node
	 * 
	 * @param rootNode
	 */
	public void printNodes(Node rootNode) {
		printNodes(rootNode, false);
	}
	
	/**
	 * Print all nodes of the tree with the given root node
	 * 
	 * @param rootNode The root node
	 * @param printToConsole Prints to the console if true
	 */
	public void printNodes(Node rootNode, boolean printToConsole) {
		Objects.requireNonNull(rootNode);

		// start with root
		List<Node> children = new ArrayList<>();
		children.add(rootNode);

		// print all children
		while (children.size() > 0) {
			List<Node> nodes = new ArrayList<>();
			for (Node child : children) {
				// print the node
				logger.debug(child);
				if(printToConsole) {
					System.out.println(child);
				}

				if (child.hasChildren()) {
					nodes.addAll(child.getChildren());
				}
			}
			children = nodes;
		}
	}

	/**
	 * Sets the right node for all children of the given parent node
	 * 
	 * @param parentNode
	 */
	protected void setRightForChildren(Node parentNode) {
		if (parentNode.hasChildren()) {
			List<Node> children = parentNode.getChildren();

			Node left = children.get(0);
			for (int i = 1; i < children.size(); i++) {
				Node child = children.get(i);
				// set the right sibling node
				left.setRight(child);
				left = child;
			}

			// set the nearest right cousin node for the rightmost child
			left.setRight(getNearestRightCousin(parentNode));
		}
	}

	/**
	 * Returns the nearest right cousin node for the given parent node
	 * 
	 * @param parentNode
	 * @return
	 */
	protected Node getNearestRightCousin(Node parentNode) {
		Node right = parentNode.getRight();
		while (right != null && !right.hasChildren()) {
			right = right.getRight();
		}

		if (right != null && right.hasChildren()) {
			return right.getChildren().get(0);
		} else {
			return null;
		}
	}
}
