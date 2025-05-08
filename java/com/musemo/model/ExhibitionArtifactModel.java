package com.musemo.model;

public class ExhibitionArtifactModel {
	private int exhibitionId;
	private String artifactId;
	private String exhibitionTitle;
	private String artifactName;

	public ExhibitionArtifactModel() {
		super();
	}

	public ExhibitionArtifactModel(int exhibitionId, String artifactId) {
		this.exhibitionId = exhibitionId;
		this.artifactId = artifactId;
	}

	public int getExhibitionId() {
		return exhibitionId;
	}

	public void setExhibitionId(int exhibitionId) {
		this.exhibitionId = exhibitionId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getExhibitionTitle() {
		return exhibitionTitle;
	}

	public void setExhibitionTitle(String exhibitionTitle) {
		this.exhibitionTitle = exhibitionTitle;
	}

	public String getArtifactName() {
		return artifactName;
	}

	public void setArtifactName(String artifactName) {
		this.artifactName = artifactName;
	}

}
