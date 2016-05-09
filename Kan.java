package mah_kan;

import java.io.IOException;
import java.util.LinkedHashMap;

import mah_setting.Setting;

/*
 * kantsu--暗・明のカンした牌情報。(key=カンした牌,value=暗・明)
 *
 */
public class Kan {
	private LinkedHashMap<String, String> kantsu;
	private int countD;

	// Kanの設定。
	public Kan(Setting s) throws IOException {
		setKantsu(SetKan.kanDL(s));
		SetKan.setCountD(this);
		showKan(this.getKantsu());
	}

	// お試し。下記の入力。引数にStringを入れることで動作。
	public Kan(String sample) {
		LinkedHashMap<String, String> k = new LinkedHashMap<>();
		k.put("東", "L");
		setKantsu(k);
		showKan(k);
	}

	public LinkedHashMap<String, String> getKantsu() {
		return kantsu;
	}

	public void setKantsu(LinkedHashMap<String, String> kantsu) {
		this.kantsu = kantsu;
	}

	public int getCountD() {
		return countD;
	}

	protected void setCountD(int countD) {
		this.countD = countD;
	}

	// Kanの内容の表示。
	public void showKan(LinkedHashMap<String, String> kan) {
		System.out.println("暗カン=D,明カン=L:" + kan.toString());
	}
}
