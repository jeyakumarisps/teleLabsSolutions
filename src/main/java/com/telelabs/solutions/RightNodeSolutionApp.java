package com.telelabs.solutions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.telelabs.rns.Node;
import com.telelabs.rns.RightNodeSolution;

public class RightNodeSolutionApp {
	private final static Logger logger = Logger.getLogger(RightNodeSolutionApp.class);
	private Node rootNode;
	private Map<String, Node> nodeByNameMap = new HashMap<>();
	
	public static void main(String[] args ) {
		logger.info("*********** Right Node Solution *********** ");
		//test the right node solution
		testRightNodeSolution();
	}
	
	/**
	 * Displays menu for the right node solution
	 * @return
	 */
	protected Scanner nodeSolutionsAppMenu(Scanner console) {
		System.out.println("*********** Right Node Solution *********** ");
		
		while (true) {
			System.out.println("\nMENU");
			System.out.println("1) Construct Tree\n2) Set right nodes\n0) Go to main menu\n");
			System.out.print("Selection: ");
			
			int choice = console.nextInt();
			logger.debug("Selected Option: " + choice);

			switch (choice) {
			case 0:
				return console;

			case 1:
				console.nextLine();
				constructTree(console);
				break;

			case 2:
				if(rootNode == null) {
					logger.info("Root node is null");
					System.out.println("Please construct the tree to set right nodes.");
					break;
				}
				
				RightNodeSolution rightNodeSoln = new RightNodeSolution();
		        rightNodeSoln.setRightNodes(rootNode);
		        
		        logger.info("---------- Right Nodes:");
		        System.out.println("\n---------- Right Nodes:");
		        rightNodeSoln.printNodes(rootNode, true);
		        
		        //clear buffer
		        console.nextLine();
		        System.out.println("\nPress 'Enter' to continue...");
		        console.nextLine();
				break;
			case 3:
				break;

			default:
				System.out.println("Unrecognized option. Please try again.\n");
			}
		}
		
	}
	
	/**
	 * constructs a tree by reading inputs from the console
	 */
	protected void constructTree(Scanner console) {
		//read root node
		System.out.println("\n######## CREATE TREE NODE");
		String nodeName = readNode(console);
		rootNode = new Node(nodeName);
		nodeByNameMap.put(nodeName, rootNode);
		
		//add children
		addChildren(console);
	}

	/**
	 * Creates a node from the console
	 * @param console
	 * @return
	 */
	private String readNode(Scanner console) {
		String nodeNameHelp = "NOTE: A node should be of length 2; The first character should be an alphabet"
				+ " and the second character should be an integer.";
		String invalidNode = "Invalid node name";
		
		while(true) {
			System.out.print("Node Name: ");
			String nodeName = console.nextLine();
			
			if(!isValidNode(nodeName)) {
				System.out.println(invalidNode);
				System.out.println(nodeNameHelp);
				continue;
			}
			
			nodeName = nodeName.trim();
			return nodeName;
		}
	}
	
	/**
	 * list all the nodes
	 * @param node
	 */
	protected void displayAllNodes(Node node) {
		System.out.print(node.getName() + " ");
		if(node.hasChildren()) {
			node.getChildren().forEach(this::displayAllNodes);
		}
	}
	
	/**
	 * Validates a node name. The name should be of length 2 and first character should be characte
	 * @param node
	 * @return
	 */
	protected boolean isValidNode(String node) {
		if(node != null && node.length() == 2) {
			return Character.isAlphabetic(node.charAt(0)) && Character.isDigit(node.charAt(1));
		}
		
		return false;
	}
	
	/**
	 * Adds children to a node
	 * @param console
	 */
	protected void addChildren(Scanner console) {
		while (true) {
			System.out.println("\n1) Add child \n0) Done");
			System.out.print("\nSelection: ");
			
			int choice = console.nextInt();
			logger.debug("Selected Option: " + choice);

			switch (choice) {
			case 1:
				addChild(console);
				break;
				
			case 0:
				return;
				
			default:
				System.out.println("Unrecognized option. Please try again.\n");
			}
		}
	}

	/**
	 * Adds a child to a selected parent
	 * @param console
	 */
	private void addChild(Scanner console) {
		//add a child
		System.out.print("\nLIST OF NODES: ");
		displayAllNodes(rootNode);
		//clear buffer
		console.nextLine();
		System.out.println("\n");
		
		System.out.println("######## SELECT PARENT NODE");
		Node parentNode = null;
		do {
			String parentName = readNode(console);
			parentNode = nodeByNameMap.get(parentName);
			
			if(parentNode == null) {
				System.out.println("Invalid parent node. Please select one of the given nodes as parent");
			} else {
				break;
			}
			
		} while(true);
		
		System.out.println("######## CREATE CHILD NODE");
		while(true) {
			String childName = readNode(console);
			
			if(nodeByNameMap.get(childName) == null) {
				Node child = new Node(childName);
				parentNode.addChild(child);
				nodeByNameMap.put(childName, child);
				break;
			} else {
				System.out.println("Duplicate node. Please provide new node name.");
			}
		}

	}
	
	/**
	 * This method test the the solution to set the right node for a tree
	 * @param args
	 */
	public static void testRightNodeSolution() {
		Node rootNode = new Node("a0");
        
        Node a1 = new Node("a1");
        Node b1 = new Node("b1");
        Node c1 = new Node("c1");
        rootNode.setChildren(Arrays.asList(new Node[] {a1, b1, c1}));
        
        Node a2 = new Node("a2");
        a1.setChildren(Arrays.asList(new Node[] {a2}));
        
        Node c2 = new Node("c2");
        Node d2 = new Node("d2");
        b1.setChildren(Arrays.asList(new Node[] {c2, d2}));
        
        Node e2 = new Node("e2");
        Node f2 = new Node("f2");
        c1.setChildren(Arrays.asList(new Node[] {e2, f2}));
        
        Node a3 = new Node("a3");
        c2.setChildren(Arrays.asList(new Node[] {a3}));
        
        Node b3 = new Node("b3");
        e2.setChildren(Arrays.asList(new Node[] {b3}));
        
        RightNodeSolution rightNodeSoln = new RightNodeSolution();
        rightNodeSoln.setRightNodes(rootNode);
        
        logger.debug("---------- Right Nodes:");
        rightNodeSoln.printNodes(rootNode, true);
    }

}
