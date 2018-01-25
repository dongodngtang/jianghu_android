package net.doyouhike.app.bbs.biz.entity;

/**
 * 设置城市对话框点击“完成”之后要使用到的类， 主要是为了往Activity回传数据
 */
public class CitySelectInfo {

	private int provinceId;
	private String provinceName;
	private int cityId;
	private String cityName;
	
	public CitySelectInfo(int provinceId, String provinceName,
			int cityId, String cityName) {
		this.provinceId = provinceId;
		this.provinceName = provinceName;
		this.cityId = cityId;
		this.cityName = cityName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
}
