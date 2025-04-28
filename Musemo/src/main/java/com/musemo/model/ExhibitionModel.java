package com.musemo.model;

import java.util.Date;

public class ExhibitionModel {
	private int exhibitionId;
	private String exhibitionTitle;
	private String exhibitionDescription;
	private Date startDate;
	private Date endDate;
	private String exhibitionImage;

	public ExhibitionModel() {
		super();
	}

	public ExhibitionModel(int exhibitionId, String exhibitionTitle, String exhibitionDescription, Date startDate,
			Date endDate, String exhibitionImage) {
		super();
		this.exhibitionId = exhibitionId;
		this.exhibitionTitle = exhibitionTitle;
		this.exhibitionDescription = exhibitionDescription;
		this.startDate = startDate;
		this.endDate = endDate;
		this.exhibitionImage = exhibitionImage;
	}

	public ExhibitionModel(String exhibitionTitle, String exhibitionDescription, Date startDate, Date endDate,
			String exhibitionImage) {
		super();
		this.exhibitionTitle = exhibitionTitle;
		this.exhibitionDescription = exhibitionDescription;
		this.startDate = startDate;
		this.endDate = endDate;
		this.exhibitionImage = exhibitionImage;
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

	public String getExhibitionDescription() {
		return exhibitionDescription;
	}

	public void setExhibitionDescription(String exhibitionDescription) {
		this.exhibitionDescription = exhibitionDescription;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getExhibitionImage() {
		return exhibitionImage;
	}

	public void setExhibitionImage(String exhibitionImage) {
		this.exhibitionImage = exhibitionImage;
	}

}