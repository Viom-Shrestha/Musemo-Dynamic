package com.musemo.model;

/**
 * Represents an artifact with various properties.
 * 
 * @author 23048612 Viom Shrestha
 */
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

	/**
	 * Default constructor for the ArtifactModel.
	 */
	public ArtifactModel() {
		super();
	}

	/**
	 * Constructor for the ArtifactModel with all fields.
	 *
	 * @param artifactID    The unique identifier for the artifact.
	 * @param artifactName  The name of the artifact.
	 * @param artifactType  The type or category of the artifact.
	 * @param creatorName   The name of the creator or artist of the artifact.
	 * @param timePeriod    The historical period or era the artifact belongs to.
	 * @param origin        The geographical origin or place of creation of the
	 *                      artifact.
	 * @param condition     The current state or condition of the artifact.
	 * @param description   A detailed description of the artifact.
	 * @param artifactImage The file path or URL to the image of the artifact.
	 */
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

	/**
	 * Gets the unique identifier of the artifact.
	 *
	 * @return The artifact ID.
	 */
	public String getArtifactID() {
		return artifactID;
	}

	/**
	 * Sets the unique identifier of the artifact.
	 *
	 * @param artifactID The artifact ID to set.
	 */
	public void setArtifactID(String artifactID) {
		this.artifactID = artifactID;
	}

	/**
	 * Gets the name of the artifact.
	 *
	 * @return The artifact name.
	 */
	public String getArtifactName() {
		return artifactName;
	}

	/**
	 * Sets the name of the artifact.
	 *
	 * @param artifactName The artifact name to set.
	 */
	public void setArtifactName(String artifactName) {
		this.artifactName = artifactName;
	}

	/**
	 * Gets the type or category of the artifact.
	 *
	 * @return The artifact type.
	 */
	public String getArtifactType() {
		return artifactType;
	}

	/**
	 * Sets the type or category of the artifact.
	 *
	 * @param artifactType The artifact type to set.
	 */
	public void setArtifactType(String artifactType) {
		this.artifactType = artifactType;
	}

	/**
	 * Gets the name of the creator or artist of the artifact.
	 *
	 * @return The creator's name.
	 */
	public String getCreatorName() {
		return creatorName;
	}

	/**
	 * Sets the name of the creator or artist of the artifact.
	 *
	 * @param creatorName The creator's name to set.
	 */
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	/**
	 * Gets the historical period or era the artifact belongs to.
	 *
	 * @return The time period.
	 */
	public String getTimePeriod() {
		return timePeriod;
	}

	/**
	 * Sets the historical period or era the artifact belongs to.
	 *
	 * @param timePeriod The time period to set.
	 */
	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}

	/**
	 * Gets the geographical origin or place of creation of the artifact.
	 *
	 * @return The origin of the artifact.
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * Sets the geographical origin or place of creation of the artifact.
	 *
	 * @param origin The origin of the artifact to set.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * Gets the current state or condition of the artifact.
	 *
	 * @return The condition of the artifact.
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * Sets the current state or condition of the artifact.
	 *
	 * @param condition The condition of the artifact to set.
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * Gets the detailed description of the artifact.
	 *
	 * @return The description of the artifact.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the detailed description of the artifact.
	 *
	 * @param description The description of the artifact to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the file path or URL to the image of the artifact.
	 *
	 * @return The artifact image path or URL.
	 */
	public String getArtifactImage() {
		return artifactImage;
	}

	/**
	 * Sets the file path or URL to the image of the artifact.
	 *
	 * @param artifactImage The artifact image path or URL to set.
	 */
	public void setArtifactImage(String artifactImage) {
		this.artifactImage = artifactImage;
	}
}