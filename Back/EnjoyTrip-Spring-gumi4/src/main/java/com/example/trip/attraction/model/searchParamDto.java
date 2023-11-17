package com.example.trip.attraction.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


public class searchParamDto {
	private String service;
	private String areaCode;
	private String contentTypeId;
	private String keyword;
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getContentTypeId() {
		return contentTypeId;
	}
	public void setContentTypeId(String contentTypeId) {
		this.contentTypeId = contentTypeId;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	@Override
	public String toString() {
		return "searchParamDto [service=" + service + ", areaCode=" + areaCode + ", contentTypeId=" + contentTypeId
				+ ", keyword=" + keyword + "]";
	}
	
}
