package com.telelabs.analyser;

/**
 * This enum describes the set of features provided by the service
 * 
 * @author Jeyakumari
 *
 */
public enum Feature {
	EMAIL("Email")
	, VOICE("Voice")
	, ARCHIVING("Archiving")
	, FEATUREX("FeatureX")
	, FEATUREY("FeatureY")
	, FEATUREZ("FeatureZ");
	
	private String label;

	private Feature(String label) {
		this.label = label;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	
}
