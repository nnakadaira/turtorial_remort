package mah_naki;

import java.io.IOException;
import java.util.ArrayList;

import mah_dora.Dora;
import mah_item.Item;
import mah_kan.Kan;
import mah_setting.Setting;

/*
 * 鳴いた牌。ポンとチー。
 *
 */

public class Naki extends Item {
	private ArrayList<String> nakiData;
	private ArrayList<String> ponData;
	private ArrayList<String> chiData;
	private int countN;

	private Naki() {
		setPonData(new ArrayList<>());
		setChiData(new ArrayList<>());
		setNakiData(new ArrayList<>());
		setCountN(0);
	}

	public Naki(Setting s, Kan k, Dora d) throws IOException {
		this();
		SetNaki.setNaki(s, k, d, this);
		SetNaki.setCountN(k, this);
	}

	public static void showNaki(Naki n) {
		Pon.showPon(n.getPonData());
		System.out.println();
		Chi.showChi(n.getChiData());
	}

	public ArrayList<String> getNakiData() {
		return nakiData;
	}

	protected void setNakiData(ArrayList<String> nakiData) {
		this.nakiData = new ArrayList<>(nakiData);
	}

	public ArrayList<String> getPonData() {
		return ponData;
	}

	protected void setPonData(ArrayList<String> ponData) {
		this.ponData = new ArrayList<>(ponData);
	}

	public ArrayList<String> getChiData() {
		return chiData;
	}

	protected void setChiData(ArrayList<String> chidata) {
		this.chiData = new ArrayList<>(chidata);
	}

	public int getCountN() {
		return countN;
	}

	public void setCountN(int countN) {
		this.countN = countN;
	}

}
