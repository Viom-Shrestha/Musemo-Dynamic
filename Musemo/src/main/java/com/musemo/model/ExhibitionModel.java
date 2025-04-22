package com.musemo.model;

import java.util.Date;

public class ExhibitionModel {
	private int exhibitionId;
	private String exhibitionTitle;
	private Date exhibitionDate;

	public ExhibitionModel() {
		super();
	}

	public ExhibitionModel(int exhibitionId, String exhibitionTitle, Date exhibitionDate) {
		super();
		this.exhibitionId = exhibitionId;
		this.exhibitionTitle = exhibitionTitle;
		this.exhibitionDate = exhibitionDate;
	}

	public int getExhibitionId() {
		return exhibitionId;
	}

	public void setExhibitionId(int exhibitionId) {
		this.exhibitionId = exhibitionId;
	}

	public String getExhibitionTitle() {
		return exhibitionTitle;
	}

	public void setExhibitionTitle(String exhibitionTitle) {
		this.exhibitionTitle = exhibitionTitle;
	}

	public Date getExhibitionDate() {
		return exhibitionDate;
	}

	public void setExhibitionDate(Date exhibitionDate) {
		this.exhibitionDate = exhibitionDate;
	}

}
