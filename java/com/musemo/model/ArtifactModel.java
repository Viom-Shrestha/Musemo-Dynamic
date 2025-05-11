package com.musemo.model;

public class ArtifactModel {
	private String artifactID;
	private String artifactName;
	private String artifactType;
	private String creatorName;
	private String timePeriod;
	private String origin;
	private String condition;
	private String description;
	private String artifactImage;

	public ArtifactModel() {
		super();
	}

	public ArtifactModel(String artifactID, String artifactName, String artifactType, String creatorName,
			String timePeriod, String origin, String condition, String description, String artifactImage) {
		super();
		this.artifactID = artifactID;
		this.artifactName = artifactName;
		this.artifactType = artifactType;
		this.creatorName = creatorName;
		this.timePeriod = timePeriod;
		this.origin = origin;
		this.condition = condition;
		this.description = description;
		this.artifactImage = artifactImage;
	}

	public String getArtifactID() {
		return artifactID;
	}

	public void setArtifactID(String artifactID) {
		this.artifactID = artifactID;
	}

	public String getArtifactName() {
		return artifactName;
	}

	public void setArtifactName(String artifactName) {
		this.artifactName = artifactName;
	}

	public String getArtifactType() {
		return artifactType;
	}

	public void setArtifactType(String artifactType) {
		this.artifactType = artifactType;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getArtifactImage() {
		return artifactImage;
	}

	public void setArtifactImage(String artifactImage) {
		this.artifactImage = artifactImage;
	}

}
