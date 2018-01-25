/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: LocationInfo.java
 * Author: wu-yoline(伍建鹏)
 * Version: 1.0
 * Create: 2015-9-25
 *
 * Changes (from 2015-10-21)
 * -----------------------------------------------------------------
 * 2015-9-25 : 创建本类 ，添加需要的属性(wu-yoline);
 * -----------------------------------------------------------------
 * 2015-10-21 : 把属性cityId改为city_id;(wu-yoline)
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.biz.entity;

public class LocationInfo {
	
	private double latitude;
	private double longitude;
	private double altitude;
	private String city_id;
	private String city;
	private String location;
	private String dest_id;
	
	private String snippet;
	private String cityCode;
	private String poiId;

	public String getDest_id() {
		return dest_id;
	}

	public void setDest_id(String dest_id) {
		this.dest_id = dest_id;
	}

	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getAltitude() {
		return altitude;
	}
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	public String getCity_id() {
		return city_id;
	}
	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getSnippet() {
		return snippet;
	}
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getPoiId() {
		return poiId;
	}
	public void setPoiId(String poiId) {
		this.poiId = poiId;
	}
}
