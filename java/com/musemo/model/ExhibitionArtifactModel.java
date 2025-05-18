package com.musemo.model;

/**
 * Represents the relationship between an exhibition and an artifact, for
 * displaying which artifacts are part of which exhibitions.
 * 
 * @author 23048612 Viom Shrestha
 */
public class ExhibitionArtifactModel {
	private int exhibitionId;
	private String artifactId;
	private String exhibitionTitle;
	private String artifactName;

	/**
	 * Default constructor for the ExhibitionArtifactModel.
	 */
	public ExhibitionArtifactModel() {
		super();
	}

	/**
	 * Constructor for the ExhibitionArtifactModel with all fields.
	 *
	 * @param exhibitionId    The unique identifier of the exhibition.
	 * @param artifactId      The unique identifier of the artifact.
	 * @param exhibitionTitle The title of the exhibition.
	 * @param artifactName    The name of the artifact.
	 */
	public ExhibitionArtifactModel(int exhibitionId, String artifactId, String exhibitionTitle, String artifactName) {
		super();
		this.exhibitionId = exhibitionId;
		this.artifactId = artifactId;
		this.exhibitionTitle = exhibitionTitle;
		this.artifactName = artifactName;
	}

	/**
	 * Gets the unique identifier of the exhibition.
	 *
	 * @return The exhibition ID.
	 */
	public int getExhibitionId() {
		return exhibitionId;
	}

	/**
	 * Sets the unique identifier of the exhibition.
	 *
	 * @param exhibitionId The exhibition ID to set.
	 */
	public void setExhibitionId(int exhibitionId) {
		this.exhibitionId = exhibitionId;
	}

	/**
	 * Gets the unique identifier of the artifact.
	 *
	 * @return The artifact ID.
	 */
	public String getArtifactId() {
		return artifactId;
	}

	/**
	 * Sets the unique identifier of the artifact.
	 *
	 * @param artifactId The artifact ID to set.
	 */
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	/**
	 * Gets the title of the exhibition.
	 *
	 * @return The exhibition title.
	 */
	public String getExhibitionTitle() {
		return exhibitionTitle;
	}

	/**
	 * Sets the title of the exhibition.
	 *
	 * @param exhibitionTitle The exhibition title to set.
	 */
	public void setExhibitionTitle(String exhibitionTitle) {
		this.exhibitionTitle = exhibitionTitle;
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
}