package net.doyouhike.app.bbs.biz.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class JPushNotificationInfo {

	private String alert;
	private String extras;

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public String getExtras() {
		return extras;
	}

	public void setExtrasByJson(String msgTypeJson) {
		if (msgTypeJson == null || msgTypeJson.length() == 0) {
			return;
		}
		try {
			JSONObject json = new JSONObject(msgTypeJson);
			this.extras = json.getString("msgType");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void setExtras(String extras) {
		this.extras = extras;
	}

}
