package com.telelabs.solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import org.apache.log4j.Logger;

import com.telelabs.analyser.BestPlanAnalyser;
import com.telelabs.analyser.Feature;
import com.telelabs.analyser.Plan;

public class BestPlanAnalyserApp {
	private final static Logger logger = Logger.getLogger(BestPlanAnalyserApp.class);
	private Set<Feature> features;
	
	public static void main(String[] args ) {
		logger.info("*********** Best Plan Analyser *********** ");
		BestPlanAnalyserApp app = new BestPlanAnalyserApp();
		//test the plan analyser
		app.testPlanAnalyser();
	}

	protected Scanner planAnalyserAppMenu(Scanner console) {
		logger.info("*********** Best Plan Analyser *********** ");
		System.out.println("*********** Best Plan Analyser *********** ");
		BestPlanAnalyser analyser = new BestPlanAnalyser();
		
		//load a random list of plans
		loadPlans(analyser);
		
		//print the plans
		System.out.println("\n############################## List of available plans ##############################\n");
		printAllPlans(analyser.getAllPlans());
		
		while (true) {
			System.out.println("\nMENU");
			System.out.println("1) Set Required Features\n2) Find Suitable Plans\n0) Go to main menu");
			System.out.print("\nSelection: ");
			
			int choice = console.nextInt();
			logger.debug("Selected Option: " + choice);

			switch (choice) {
			case 0:
				return console;

			case 1:
				//consume line
				console.nextLine();
				//set required features
				setFeatures(console);
				break;

			case 2:
				if(features == null || features.isEmpty()) {
					System.out.println("Feature list is empty. Select features to find best plans that suits you.");
					break;
				} else {
					System.out.println("############################## List of available plans ##############################");
					printAllPlans(analyser.getAllPlans());
					System.out.println("\nSelected Featuers: " + features);
				}
				
				List<List<Plan>> suitablePlans = analyser.getSuitablePlans(features);
				printSuitablePlans(suitablePlans);
						        
		        //clear buffer
		        console.nextLine();
		        System.out.println("Press 'Enter' to continue...");
		        console.nextLine();
				break;

			default:
				System.out.println("Unrecognized option. Please try again.\n");
			}
		}
		
	}

	protected void setFeatures(Scanner console) {
		features = new HashSet<>();
		System.out.println("LIST OF FEATURES: ");
		int i = 0;
		for(Feature feature : Feature.values()) {
			System.out.println("[" + ++i + "] " + feature.getLabel());
		}
		System.out.println();
		
		while (true) {
			System.out.println("\n1) Add Feature \n0) Done");
			System.out.print("Selection: ");
			
			int choice = console.nextInt();
			logger.debug("Selected Option: " + choice);

			switch (choice) {
			case 1:
				Feature feature = readFeature(i, console);
				this.features.add(feature);
				
				System.out.println("\nSelected Featuers: " + features);
				break;
				
			case 0:
				return;
				
			default:
				System.out.println("Unrecognized option. Please try again.\n");
			}
		}
	}
	
	private Feature readFeature(int maxId, Scanner console) {
		while(true) {
			System.out.print("\nSelect Feature: ");
			int featureId = console.nextInt();
			
			if(featureId < 0 && featureId > maxId) {
				System.out.println("Invalid Feature");
				continue;
			}
			
			return Feature.values()[featureId - 1];
		}
	}
	
	/**
	 * This method tests the best plan analyzer
	 * @param args
	 */
	public void testPlanAnalyser() {
		logger.info("*********** Best Plan Analyser *********** ");
		BestPlanAnalyser analyser = new BestPlanAnalyser();
		
		//load a plan randomly
		loadPlans(analyser);
		
		//get the suitable plans for the given features
		List<List<Plan>> suitablePlans = analyser.getSuitablePlans(new HashSet<>(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.ARCHIVING)));
		printSuitablePlans(suitablePlans);
    }
	
	/**
	 * Print all plans
	 * @param plans
	 */
	private void printAllPlans(List<Plan> plans) {
		//print all the plans
		plans.forEach(logger::info);
		plans.forEach(System.out::println);
		System.out.println();
	}

	/**
	 * Print all suitable plans with total cost
	 * @param suitablePlans
	 */
	private void printSuitablePlans(List<List<Plan>> suitablePlans) {
		//print all the plans
		for(List<Plan> plans : suitablePlans) {
			Double totalCost = BestPlanAnalyser.sumOfCostForPlans(plans);
			logger.info("---------- Total Cost: " + totalCost);
			System.out.println("---------- Total Cost: " + totalCost);
			plans.forEach(logger::info);
			plans.forEach(System.out::println);
			System.out.println();
		}
	}
	
	/**
	 * load a random list of plans
	 * @param analyser
	 */
	protected static void loadPlans(BestPlanAnalyser analyser) {
		Random rand = new Random();
		int  num = rand.nextInt(5) + 1;
		logger.info("Loading plan set : " + num);
		
		switch(num) {
		case 1:
			loadPlanSet1(analyser);
			break;
		case 2:
			loadPlanSet2(analyser);
			break;
		case 3:
			loadPlanSet3(analyser);
			break;
		case 4:
			loadPlanSet4(analyser);
			break;
		case 5:
			loadPlanSet5(analyser);
			break;
		}
	}
	
	public static void loadPlanSet1(BestPlanAnalyser analyser) {
		//load all plans
		List<Plan> allPlans = new ArrayList<>();
		
		Plan plan1 = new Plan("Plan 1");
		plan1.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE));
		plan1.setCost(15.00);
		allPlans.add(plan1);
		
		Plan plan2 = new Plan("Plan 2");
		plan2.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.ARCHIVING));
		plan2.setCost(20.00);
		allPlans.add(plan2);
		
		Plan plan3 = new Plan("Plan 3");
		plan3.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.FEATUREX));
		plan3.setCost(25.00);
		allPlans.add(plan3);
		
		Plan plan4 = new Plan("Plan 4");
		plan4.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.FEATUREY));
		plan4.setCost(30.00);
		allPlans.add(plan4);
		
		Plan plan5 = new Plan("Plan 5");
		plan5.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.FEATUREZ));
		plan5.setCost(35.00);
		allPlans.add(plan5);
		
		Plan plan6 = new Plan("Plan 6");
		plan6.setFeatures(Arrays.asList(Feature.FEATUREX, Feature.FEATUREY, Feature.FEATUREZ));
		plan6.setCost(60.00);
		allPlans.add(plan6);
		
		Plan plan7 = new Plan("Plan 7");
		plan7.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.FEATUREX, Feature.FEATUREY));
		plan7.setCost(40.00);
		allPlans.add(plan7);
		
		Plan plan8 = new Plan("Plan 8");
		plan8.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.FEATUREY, Feature.FEATUREZ));
		plan8.setCost(50.00);
		allPlans.add(plan8);
		
		Plan plan9 = new Plan("Plan 9");
		plan9.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.FEATUREX, Feature.FEATUREY, Feature.FEATUREZ));
		plan9.setCost(70.00);
		allPlans.add(plan9);
		
		Plan plan10 = new Plan("Plan 10");
		plan10.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.ARCHIVING, Feature.FEATUREX, Feature.FEATUREY, Feature.FEATUREZ));
		plan10.setCost(75.00);
		allPlans.add(plan10);
		
		analyser.loadPlans(allPlans);
	}
	
	public static void loadPlanSet2(BestPlanAnalyser analyser) {
		//load all plans
		List<Plan> allPlans = new ArrayList<>();
		
		Plan plan1 = new Plan("Plan 1");
		plan1.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE));
		plan1.setCost(15.00);
		allPlans.add(plan1);
		
		Plan plan2 = new Plan("Plan 2");
		plan2.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.ARCHIVING));
		plan2.setCost(20.00);
		allPlans.add(plan2);
		
		Plan plan3 = new Plan("Plan 3");
		plan3.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.FEATUREX));
		plan3.setCost(25.00);
		allPlans.add(plan3);
		
		Plan plan4 = new Plan("Plan 4");
		plan4.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.FEATUREY));
		plan4.setCost(30.00);
		allPlans.add(plan4);
		
		Plan plan5 = new Plan("Plan 5");
		plan5.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.FEATUREZ));
		plan5.setCost(35.00);
		allPlans.add(plan5);
		
		Plan plan6 = new Plan("Plan 4");
		plan6.setFeatures(Arrays.asList(Feature.FEATUREX, Feature.FEATUREY, Feature.FEATUREZ));
		plan6.setCost(60.00);
		allPlans.add(plan6);
		
		Plan plan7 = new Plan("Plan 7");
		plan7.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.FEATUREX, Feature.FEATUREY));
		plan7.setCost(40.00);
		allPlans.add(plan7);
		
		Plan plan8 = new Plan("Plan 8");
		plan8.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.FEATUREY, Feature.FEATUREZ));
		plan8.setCost(50.00);
		allPlans.add(plan8);
		
		Plan plan9 = new Plan("Plan 9");
		plan9.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.FEATUREX, Feature.FEATUREY, Feature.FEATUREZ));
		plan9.setCost(70.00);
		allPlans.add(plan9);
		
		Plan plan10 = new Plan("Plan 10");
		plan10.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.ARCHIVING, Feature.FEATUREX, Feature.FEATUREY, Feature.FEATUREZ));
		plan10.setCost(75.00);
		allPlans.add(plan10);
		
		analyser.loadPlans(allPlans);
	}
	
	public static void loadPlanSet3(BestPlanAnalyser analyser) {
		//load all plans
		List<Plan> allPlans = new ArrayList<>();
		
		Plan plan1 = new Plan("Plan 1");
		plan1.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE));
		plan1.setCost(15.00);
		allPlans.add(plan1);
		
		Plan plan2 = new Plan("Plan 2");
		plan2.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.ARCHIVING));
		plan2.setCost(20.00);
		allPlans.add(plan2);
		
		Plan plan3 = new Plan("Plan 3");
		plan3.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.FEATUREX));
		plan3.setCost(25.00);
		allPlans.add(plan3);
		
		Plan plan4 = new Plan("Plan 4");
		plan4.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.FEATUREY));
		plan4.setCost(30.00);
		allPlans.add(plan4);
		
		Plan plan5 = new Plan("Plan 5");
		plan5.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.FEATUREZ));
		plan5.setCost(35.00);
		allPlans.add(plan5);
		
		Plan plan6 = new Plan("Plan 4");
		plan6.setFeatures(Arrays.asList(Feature.FEATUREX, Feature.FEATUREY, Feature.FEATUREZ));
		plan6.setCost(60.00);
		allPlans.add(plan6);
		
		Plan plan7 = new Plan("Plan 7");
		plan7.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.FEATUREX, Feature.FEATUREY));
		plan7.setCost(40.00);
		allPlans.add(plan7);
		
		Plan plan8 = new Plan("Plan 8");
		plan8.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.FEATUREY, Feature.FEATUREZ));
		plan8.setCost(50.00);
		allPlans.add(plan8);
		
		Plan plan9 = new Plan("Plan 9");
		plan9.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.FEATUREX, Feature.FEATUREY, Feature.FEATUREZ));
		plan9.setCost(55.00);
		allPlans.add(plan9);
		
		Plan plan10 = new Plan("Plan 10");
		plan10.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.ARCHIVING, Feature.FEATUREX, Feature.FEATUREY, Feature.FEATUREZ));
		plan10.setCost(75.00);
		allPlans.add(plan10);
		
		analyser.loadPlans(allPlans);
	}
	
	public static void loadPlanSet4(BestPlanAnalyser analyser) {
		//load all plans
		List<Plan> allPlans = new ArrayList<>();
		
		Plan plan1 = new Plan("Plan 1");
		plan1.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE));
		plan1.setCost(15.00);
		allPlans.add(plan1);
		
		Plan plan2 = new Plan("Plan 2");
		plan2.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.ARCHIVING));
		plan2.setCost(20.00);
		allPlans.add(plan2);
		
		Plan plan4 = new Plan("Plan 4");
		plan4.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.FEATUREY));
		plan4.setCost(30.00);
		allPlans.add(plan4);
		
		Plan plan5 = new Plan("Plan 5");
		plan5.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.FEATUREZ));
		plan5.setCost(35.00);
		allPlans.add(plan5);
		
		Plan plan6 = new Plan("Plan 4");
		plan6.setFeatures(Arrays.asList(Feature.FEATUREX, Feature.FEATUREY, Feature.FEATUREZ));
		plan6.setCost(60.00);
		allPlans.add(plan6);
		
		Plan plan8 = new Plan("Plan 8");
		plan8.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.FEATUREY, Feature.FEATUREZ));
		plan8.setCost(50.00);
		allPlans.add(plan8);
		
		Plan plan9 = new Plan("Plan 9");
		plan9.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.FEATUREX, Feature.FEATUREY, Feature.FEATUREZ));
		plan9.setCost(73.00);
		allPlans.add(plan9);
		
		Plan plan10 = new Plan("Plan 10");
		plan10.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.ARCHIVING, Feature.FEATUREX, Feature.FEATUREY, Feature.FEATUREZ));
		plan10.setCost(75.00);
		allPlans.add(plan10);
		
		Plan plan11 = new Plan("Plan 11");
		plan11.setFeatures(Arrays.asList(Feature.FEATUREX));
		plan11.setCost(13.00);
		allPlans.add(plan11);
		
		Plan plan12 = new Plan("Plan 12");
		plan12.setFeatures(Arrays.asList(Feature.FEATUREY));
		plan12.setCost(17.00);
		allPlans.add(plan12);
		
		Plan plan13 = new Plan("Plan 13");
		plan13.setFeatures(Arrays.asList(Feature.FEATUREZ));
		plan13.setCost(23.00);
		allPlans.add(plan13);
		
		analyser.loadPlans(allPlans);
	}
	
	public static void loadPlanSet5(BestPlanAnalyser analyser) {
		//load all plans
		List<Plan> allPlans = new ArrayList<>();
		
		Plan plan1 = new Plan("Plan 1");
		plan1.setFeatures(Arrays.asList(Feature.EMAIL));
		plan1.setCost(15.00);
		allPlans.add(plan1);
		
		Plan plan2 = new Plan("Plan 2");
		plan2.setFeatures(Arrays.asList(Feature.VOICE));
		plan2.setCost(20.00);
		allPlans.add(plan2);
		
		Plan plan4 = new Plan("Plan 4");
		plan4.setFeatures(Arrays.asList(Feature.ARCHIVING));
		plan4.setCost(30.00);
		allPlans.add(plan4);
		
		Plan plan5 = new Plan("Plan 5");
		plan5.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.FEATUREZ));
		plan5.setCost(35.00);
		allPlans.add(plan5);
		
		Plan plan6 = new Plan("Plan 6");
		plan6.setFeatures(Arrays.asList(Feature.VOICE, Feature.ARCHIVING, Feature.FEATUREZ));
		plan6.setCost(60.00);
		allPlans.add(plan6);
		
		Plan plan8 = new Plan("Plan 8");
		plan8.setFeatures(Arrays.asList(Feature.EMAIL, Feature.ARCHIVING, Feature.FEATUREZ));
		plan8.setCost(50.00);
		allPlans.add(plan8);
		
		Plan plan9 = new Plan("Plan 9");
		plan9.setFeatures(Arrays.asList(Feature.VOICE, Feature.FEATUREX, Feature.FEATUREY, Feature.FEATUREZ));
		plan9.setCost(60.00);
		allPlans.add(plan9);
		
		Plan plan10 = new Plan("Plan 10");
		plan10.setFeatures(Arrays.asList(Feature.EMAIL, Feature.FEATUREX, Feature.FEATUREY, Feature.FEATUREZ));
		plan10.setCost(75.00);
		allPlans.add(plan10);
		
		Plan plan11 = new Plan("Plan 11");
		plan11.setFeatures(Arrays.asList(Feature.FEATUREX));
		plan11.setCost(13.00);
		allPlans.add(plan11);
		
		Plan plan12 = new Plan("Plan 12");
		plan12.setFeatures(Arrays.asList(Feature.FEATUREY));
		plan12.setCost(17.00);
		allPlans.add(plan12);
		
		Plan plan13 = new Plan("Plan 13");
		plan13.setFeatures(Arrays.asList(Feature.FEATUREZ));
		plan13.setCost(23.00);
		allPlans.add(plan13);
		
		analyser.loadPlans(allPlans);
	}

}
