package com.example.trip.attraction.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttractionDetailDto {
	private int contentId;
	private String cat1;
	private String cat2;
	private String cat3;
	private String createdTime;
	private String modifiedTime;
	private String bookTour;
}
