package com.telelabs.solutions;

import java.util.Scanner;

import org.apache.log4j.Logger;

/**
 * 
 * @author Jeyakumari
 *
 */
public class TeleLabSolutions {
	private final static Logger logger = Logger.getLogger(TeleLabSolutions.class);
	
	public static void main(String[] args) {
		logger.info("*********** Welcome to TeleLabs Solutions! ***********");
		System.out.println("\n*********** Welcome to TeleLabs Solutions! ***********\n");
		try(Scanner console = new Scanner(System.in)) {
			TeleLabSolutions tlSolutions = new TeleLabSolutions();
			tlSolutions.mainMenu(console);
		}
		logger.debug("*********** Application is shutdown ***********");
	}

	/**
	 * get the main menu choice from user
	 * @return
	 */
	protected Scanner mainMenu(Scanner console) {
		while (true) {
			System.out.println("MAIN MENU");
			System.out.println("1) Load Node Solultions App\n2) Load Plan Analyser App\n0) Exit");
			System.out.print("\nSelection: ");
			
			int choice = console.nextInt();
			logger.debug("Selected Option: " + choice);

			switch (choice) {
			case 0:
				System.out.println("\nBye!");
				return console;

			case 1:
				System.out.println("\nLoading Node Solultions App...\n");
				new RightNodeSolutionApp().nodeSolutionsAppMenu(console);
				break;

			case 2:
				System.out.println("\nLoading Plan Analyser App...\n");
				new BestPlanAnalyserApp().planAnalyserAppMenu(console);
				break;

			default:
				System.out.println("Unrecognized option. Please try again.\n");
			}
		}
	}

}
