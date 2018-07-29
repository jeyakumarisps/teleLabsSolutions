package com.telelabs.analyser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

/**
 * This class analyses and finds the best set of plans for the given 
 * set of features 
 * 
 * @author Jeyakumari
 *
 */
public class BestPlanAnalyser {
	private final static Logger logger = Logger.getLogger(BestPlanAnalyser.class);
	//list of plans
	private List<Plan> allPlans;
	
	public BestPlanAnalyser() {
	}

	/**
	 * @return the allPlans
	 */
	public List<Plan> getAllPlans() {
		return allPlans;
	}
	



	/**
	 * Load the list of plans into the system
	 * @param plans
	 */
	public void loadPlans(List<Plan> plans) {
		this.allPlans = plans;
	}

	/**
	 * Returns the suitable list of plans in increasing order of cost for the given set of features
	 * @param features
	 * @return
	 */
	public List<List<Plan>> getSuitablePlans(Set<Feature> features) {
		Objects.requireNonNull(features, "Features cannot be null");
		
		//find all plans with at least one feature
		List<Plan> filteredPlans = allPlans.stream().filter(p -> p.getFeatures().stream().anyMatch(features::contains)).collect(Collectors.toList());
		
		logger.debug("FilteredPlans Plans: " + filteredPlans.size());
		filteredPlans.forEach(logger::debug);
		logger.debug("################################");
		
		List<List<Plan>> allSuitablePlans = findAllSuitableCombinations(filteredPlans, features);
		
		//sort the plan list by cost
		allSuitablePlans = allSuitablePlans.stream().sorted(Comparator.comparing(BestPlanAnalyser::sumOfCostForPlans
				, Comparator.<Double>naturalOrder())).collect(Collectors.toList());
		
		return allSuitablePlans;
	}
	
	/**
	 * Returns all combinations of plans that has the given set of features with no feature included twice
	 * 
	 * @param plans
	 * @param features
	 * @return
	 */
	private static List<List<Plan>> findAllSuitableCombinations(List<Plan> plans, Set<Feature> features) {
		List<List<Plan>> allSuitablePlans = new ArrayList<>();
		
		//iterate through all combinations of plans
		int size = plans.size();
		for ( long i = 1; i < Math.pow(2, size); i++ ) {
			
			//find a combination of plans
	        List<Plan> planList = new ArrayList<Plan>();
	        for ( int j = 0; j < size; j++ ) {
	            if ( (i & (long) Math.pow(2, j)) > 0 ) {
	            	planList.add(plans.get(j));
	            }
	        }
	        
	        //check if the set of plans has all the features
	        Set<Feature> featureSet = planList.stream().flatMap(p -> p.getFeatures().stream()).collect(Collectors.toSet());
	        if(featureSet.containsAll(features)) {
	        	//check and remove if a super set of plans is found
	        	boolean found = false;
	        	
	        	Iterator<List<Plan>> itr = allSuitablePlans.iterator();
	        	while(itr.hasNext()) {
	        		List<Plan> pl = itr.next();
	        		if( pl.size() > planList.size() && pl.containsAll(planList) ) {
	        			itr.remove();
	        			break;
	        		} else if(planList.containsAll(pl)){
	        			//list already found
	        			found = true;
	        		}
	        	}
	        	
	        	if(!found && !hasDuplicateFeatures(planList)) {
	        		allSuitablePlans.add(planList);
	        	}
	        }
	    }
		
	    return allSuitablePlans;
	}
	
	/**
	 * Returns true if the given list of panels has any duplicate feature
	 * @param plans
	 * @return
	 */
	private static boolean hasDuplicateFeatures(List<Plan> plans) {
		Set<Feature> allFeatures = new HashSet<>();
		for(Plan plan : plans) {
			List<Feature> features = plan.getFeatures();
			if(!Collections.disjoint(allFeatures, features)) {
				return true;
			}
			allFeatures.addAll(features);
		}
		
		return false;
	}
	
	/**
	 * Returns the total cost for the given set of plans
	 * @param plans
	 * @return
	 */
	public static Double sumOfCostForPlans(List<Plan> plans) {
		return plans.stream().map(Plan::getCost).collect(Collectors.summingDouble(i->i));
	}

}
