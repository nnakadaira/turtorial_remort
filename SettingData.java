package mah_setting;

import java.util.LinkedHashMap;

//SettingDataはSetting内の情報を保持する。
public class SettingData {
	private LinkedHashMap<String, String> setting_data;

	// SettingDataの初期化。
	public SettingData() {
		setting_data = new LinkedHashMap<>();
		setting_data.put("場風", "");
		setting_data.put("自風", "");
		setting_data.put("親番", "");
		setting_data.put("連荘回数", "");
		setting_data.put("リーチ", "");
		this.setSettingData("一発", "無");
		setting_data.put("和了方法", "");
		setSettingData("ハイテイ", "無");
		setSettingData("チャンカン", "無");
		setSettingData("リンシャン", "無");
	}

	public LinkedHashMap<String, String> getSettingData() {
		return this.setting_data;
	}

	protected void setSettingData(LinkedHashMap<String, String> setting_data) {
		this.setting_data.clear();
		if (setting_data != null)
			for (String key : setting_data.keySet())
				this.setting_data.put(key, setting_data.get(key));
	}

	protected void setSettingData(String key, String value) {
		this.setting_data.put(key, value);
	}
}