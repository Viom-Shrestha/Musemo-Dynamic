package com.musemo.model;

import java.sql.Date;

/**
 * Represents an exhibition with details such as title, description, dates, and
 * image.
 * 
 * @author 23048612 Viom Shrestha
 */
public class ExhibitionModel {
	private int exhibitionId;
	private String exhibitionTitle;
	private String exhibitionDescription;
	private Date startDate;
	private Date endDate;
	private String exhibitionImage;

	/**
	 * Default constructor for the ExhibitionModel.
	 */
	public ExhibitionModel() {
		super();
	}

	/**
	 * Constructor for the ExhibitionModel with all fields.
	 *
	 * @param exhibitionId          The unique identifier for the exhibition.
	 * @param exhibitionTitle       The title of the exhibition.
	 * @param exhibitionDescription A brief description of the exhibition.
	 * @param startDate             The date on which the exhibition starts.
	 * @param endDate               The date on which the exhibition ends.
	 * @param exhibitionImage       The file path or URL to the image representing
	 *                              the exhibition.
	 */
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
	 * Gets the brief description of the exhibition.
	 *
	 * @return The exhibition description.
	 */
	public String getExhibitionDescription() {
		return exhibitionDescription;
	}

	/**
	 * Sets the brief description of the exhibition.
	 *
	 * @param exhibitionDescription The exhibition description to set.
	 */
	public void setExhibitionDescription(String exhibitionDescription) {
		this.exhibitionDescription = exhibitionDescription;
	}

	/**
	 * Gets the date on which the exhibition starts.
	 *
	 * @return The start date.
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Sets the date on which the exhibition starts.
	 *
	 * @param startDate The start date to set.
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Gets the date on which the exhibition ends.
	 *
	 * @return The end date.
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Sets the date on which the exhibition ends.
	 *
	 * @param endDate The end date to set.
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Gets the file path or URL to the image representing the exhibition.
	 *
	 * @return The exhibition image path or URL.
	 */
	public String getExhibitionImage() {
		return exhibitionImage;
	}

	/**
	 * Sets the file path or URL to the image representing the exhibition.
	 *
	 * @param exhibitionImage The exhibition image path or URL to set.
	 */
	public void setExhibitionImage(String exhibitionImage) {
		this.exhibitionImage = exhibitionImage;
	}
}