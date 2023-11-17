package com.example.trip.attraction.model;

import lombok.Setter;
import lombok.ToString;
import lombok.Getter;

@Getter
@Setter
@ToString
public class AttractionDescriptionDto {
	private int contentId;
	private String homepage;
	private String overview;
	private String telname;
}
