package mah_mentsu;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import mah_hai.Hai;
import mah_item.Item;
import mah_kan.Kan;

//面子データの設定。牌全体、雀頭、順子、刻子、カン、和了牌、待ち、七対子判定。
public class Mentsu extends Item {
	private ArrayList<String> haiData;
	private String jantou;
	private ArrayList<String> shuntsu;
	private ArrayList<String> koutsu;
	private LinkedHashMap<String, String> kantsu;
	private String agari;
	private LinkedHashMap<String, String> machi;
	private boolean toy7;

	// 初期化。
	private Mentsu() {
		setHaiData(new ArrayList<>());
		setJantou("");
		setShuntsu(new ArrayList<>());
		setKoutsu(new ArrayList<>());
		setKantsu(new LinkedHashMap<>());
		setAgari("");
		setMachi(new LinkedHashMap<>());

		setToy7(false);
	}

	// 設定。
	public Mentsu(Kan kan, Hai hai) {
		this();
		setHaiData(hai.getHaiListData());
		setKantsu(kan.getKantsu());
		setAgari(hai.getAgariHaiData());
	}

	// 面子パターンの表示
	public static void showMen(Mentsu men) {
		System.out.println("\n雀頭:[" + men.getJantou().toString() + "]");
		System.out.print("順子:");
		for (String sh : men.getShuntsu())
			System.out.print("[" + sh + "]");
		System.out.print("\n刻子:");
		for (String ko : men.getKoutsu())
			System.out.print("[" + ko + "]");
		System.out.println("\n和了牌:" + men.getAgari());
		System.out.print("待ち:\n");
		for (String ma : men.getMachi().keySet())
			System.out.println("[" + ma + "]" + " = [" + men.getMachi().get(ma) + "]");
	}

	public String getJantou() {
		return jantou;
	}

	protected void setJantou(String jantou) {
		if (jantou == null)
			this.jantou = "";
		this.jantou = jantou;
	}

	public ArrayList<String> getShuntsu() {
		return shuntsu;
	}

	protected void setShuntsu(ArrayList<String> shuntsu) {
		this.shuntsu = new ArrayList<>(shuntsu);
	}

	public ArrayList<String> getKoutsu() {
		return koutsu;
	}

	protected void setKoutsu(ArrayList<String> koutsu) {
		this.koutsu = new ArrayList<>(koutsu);
	}

	public LinkedHashMap<String, String> getKantsu() {
		return kantsu;
	}

	private void setKantsu(LinkedHashMap<String, String> kantsu) {
		this.kantsu = new LinkedHashMap<>(kantsu);
	}

	public boolean isToy7() {
		return toy7;
	}

	protected void setToy7(boolean toy7) {
		this.toy7 = toy7;
	}

	public String getAgari() {
		return agari;
	}

	private void setAgari(String agari) {
		this.agari = agari;
	}

	public LinkedHashMap<String, String> getMachi() {
		return machi;
	}

	protected void setMachi(LinkedHashMap<String, String> machi) {
		this.machi = new LinkedHashMap<>(machi);
	}

	public ArrayList<String> getHaiData() {
		return haiData;
	}

	protected void setHaiData(ArrayList<String> haiData) {
		this.haiData = new ArrayList<>(haiData);
	}
}
