package com.musemo.model;

public class ExhibitionArtifactModel {
	private int exhibitionId;
	private int artifactId;

	public ExhibitionArtifactModel() {
		super();
	}

	public ExhibitionArtifactModel(int exhibitionId, int artifactId) {
		super();
		this.exhibitionId = exhibitionId;
		this.artifactId = artifactId;
	}

	public int getExhibitionId() {
		return exhibitionId;
	}

	public void setExhibitionId(int exhibitionId) {
		this.exhibitionId = exhibitionId;
	}

	public int getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(int artifactId) {
		this.artifactId = artifactId;
	}

}
