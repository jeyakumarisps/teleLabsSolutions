package com.telelabs.solutions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.telelabs.analyser.BestPlanAnalyser;
import com.telelabs.analyser.Feature;
import com.telelabs.analyser.Plan;
import com.telelabs.rns.Node;
import com.telelabs.rns.RightNodeSolution;

/**
 * Unit test for TeleLabs solutions
 */
public class TeleLabSolutionsTest {
	
	private List<Plan> planListOne;
	private List<Plan> planListTwo;
	private BestPlanAnalyser planAnalyser;
	
	private final static Logger logger = Logger.getLogger(TeleLabSolutionsTest.class);
	
	public TeleLabSolutionsTest() {
		planAnalyser = new BestPlanAnalyser();
	}
	
	private void loadPlanListOne() {
		//load list one
		planListOne = new ArrayList<>();
		
		Plan plan1 = new Plan("Plan 1");
		plan1.setFeatures(Arrays.asList(Feature.EMAIL));
		plan1.setCost(15.00);
		planListOne.add(plan1);
		
		Plan plan2 = new Plan("Plan 2");
		plan2.setFeatures(Arrays.asList(Feature.VOICE));
		plan2.setCost(20.00);
		planListOne.add(plan2);
		
		Plan plan4 = new Plan("Plan 4");
		plan4.setFeatures(Arrays.asList(Feature.ARCHIVING));
		plan4.setCost(30.00);
		planListOne.add(plan4);
		
		Plan plan5 = new Plan("Plan 5");
		plan5.setFeatures(Arrays.asList(Feature.FEATUREX, Feature.FEATUREY, Feature.FEATUREZ));
		plan5.setCost(35.00);
		planListOne.add(plan5);
		
		Plan plan6 = new Plan("Plan 6");
		plan6.setFeatures(Arrays.asList(Feature.EMAIL, Feature.FEATUREX, Feature.FEATUREZ));
		plan6.setCost(35.00);
		planListOne.add(plan6);
	}
	
	private void loadPlanListTwo() {
		//load list one
		planListTwo = new ArrayList<>();
		
		Plan plan1 = new Plan("Plan 1");
		plan1.setFeatures(Arrays.asList(Feature.EMAIL));
		plan1.setCost(15.00);
		planListTwo.add(plan1);
		
		Plan plan2 = new Plan("Plan 2");
		plan2.setFeatures(Arrays.asList(Feature.VOICE));
		plan2.setCost(20.00);
		planListTwo.add(plan2);
		
		Plan plan4 = new Plan("Plan 4");
		plan4.setFeatures(Arrays.asList(Feature.ARCHIVING));
		plan4.setCost(30.00);
		planListTwo.add(plan4);
		
		Plan plan5 = new Plan("Plan 5");
		plan5.setFeatures(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.FEATUREZ));
		plan5.setCost(45.00);
		planListTwo.add(plan5);
		
		Plan plan6 = new Plan("Plan 6");
		plan6.setFeatures(Arrays.asList(Feature.EMAIL, Feature.FEATUREX, Feature.FEATUREZ));
		plan6.setCost(35.00);
		planListTwo.add(plan6);
	}	
	
	@Before
	public void prepare() {
		loadPlanListOne();
		loadPlanListTwo();
	}
	
	@Test
	public void testPlanAnalyser() {
		List<List<Plan>> suitablePlans;
		
		planAnalyser.loadPlans(planListOne);
		//get the suitable plans for the given features
		suitablePlans = planAnalyser.getSuitablePlans(new HashSet<>(Arrays.asList(Feature.FEATUREX, Feature.FEATUREY, Feature.FEATUREZ)));
		assertEquals(suitablePlans.size(), 1);
		
		suitablePlans = planAnalyser.getSuitablePlans(new HashSet<>(Arrays.asList(Feature.EMAIL, Feature.FEATUREX, Feature.FEATUREY)));
		assertEquals(suitablePlans.size(), 1);
		assertEquals(BestPlanAnalyser.sumOfCostForPlans(suitablePlans.get(0)), new Double(50.0));
		
		planAnalyser.loadPlans(planListTwo);
		
		//get the suitable plans for the given features
		suitablePlans = planAnalyser.getSuitablePlans(new HashSet<>(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.ARCHIVING)));
		assertEquals(suitablePlans.size(), 3);
		
		suitablePlans = planAnalyser.getSuitablePlans(new HashSet<>(Arrays.asList(Feature.EMAIL, Feature.VOICE, Feature.FEATUREZ)));
		assertEquals(suitablePlans.size(), 2);
		
		suitablePlans = planAnalyser.getSuitablePlans(new HashSet<>(Arrays.asList(Feature.EMAIL)));
		assertEquals(suitablePlans.size(), 3);
		assertEquals(BestPlanAnalyser.sumOfCostForPlans(suitablePlans.get(0)), new Double(15.0));
		
		suitablePlans = planAnalyser.getSuitablePlans(new HashSet<>(Arrays.asList(Feature.EMAIL, Feature.FEATUREZ)));
		assertEquals(suitablePlans.size(), 2);
		assertEquals(BestPlanAnalyser.sumOfCostForPlans(suitablePlans.get(0)), new Double(35.0));
	}

	/**
	 * this test case tests the right node solution
	 */
	@Test
	public void testRNSCaseOne() {
		Node rootNode = new Node("a0");
        
        Node a1 = new Node("a1");
        Node b1 = new Node("b1");
        Node c1 = new Node("c1");
        rootNode.setChildren(Arrays.asList(a1, b1, c1));
        
        Node a2 = new Node("a2");
        Node b2 = new Node("b2");
        a1.setChildren(Arrays.asList(a2, b2));
        
        Node c2 = new Node("c2");
        c1.setChildren(Arrays.asList(c2));
        
        RightNodeSolution rightNodeSoln = new RightNodeSolution();
        rightNodeSoln.setRightNodes(rootNode);
        
        assertNull(rootNode.getRight());
        
        assertEquals(a1.getRight(), b1);
        assertEquals(b1.getRight(), c1);
        assertNull(c1.getRight());
        
        assertEquals(a2.getRight(), b2);
        assertEquals(b2.getRight(), c2);
        assertNull(c2.getRight());
	}

	/**
	 * this test case tests the right node solution
	 */
	@Test
	public void testRNSCaseTwo() {
		Node rootNode = new Node("a0");
        
        Node a1 = new Node("a1");
        Node b1 = new Node("b1");
        Node c1 = new Node("c1");
        rootNode.setChildren(Arrays.asList(a1, b1, c1));
        
        Node a2 = new Node("a2");
        a1.setChildren(Arrays.asList(a2));
        
        Node c2 = new Node("c2");
        Node d2 = new Node("d2");
        b1.setChildren(Arrays.asList(c2, d2));
        
        Node e2 = new Node("e2");
        Node f2 = new Node("f2");
        c1.setChildren(Arrays.asList(e2, f2));
        
        Node a3 = new Node("a3");
        c2.setChildren(Arrays.asList(a3));
        
        Node b3 = new Node("b3");
        e2.setChildren(Arrays.asList(b3));
        
        RightNodeSolution rightNodeSoln = new RightNodeSolution();
        rightNodeSoln.setRightNodes(rootNode);
        
        assertNull(rootNode.getRight());
        
        assertEquals(a1.getRight(), b1);
        assertEquals(b1.getRight(), c1);
        assertNull(c1.getRight());
        
        assertEquals(a2.getRight(), c2);
        assertEquals(c2.getRight(), d2);
        assertEquals(d2.getRight(), e2);
        assertEquals(e2.getRight(), f2);
        assertNull(f2.getRight());
        
        assertEquals(a3.getRight(), b3);
        assertNull(b3.getRight());
	}

}
