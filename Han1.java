package mah_yaku;

import java.util.ArrayList;

import mah_item.Item;
import mah_item.SetHaiName;
import mah_mentsu.Mentsu;
import mah_naki.Naki;
import mah_setting.Setting;

/*
 *1ハン
 *鳴き有り-役牌、ハイテイ、リンシャン、チャンカン、タンヤオ
 *鳴き無し-リーチ、ダブリー(実質2ハン)、面前ツモ、平和、一発、一盃口
 */
public class Han1 extends Item {

	public static void showHan(ArrayList<String> han) {

	}

	//
	public static ArrayList<String> setHan1(Setting s, Naki n, Mentsu m) {
		ArrayList<String> han1 = new ArrayList<>();

		for (String y : Yakuhai(s, n, m))
			han1.add(y);
		for (String r : Reach(s))
			han1.add(r);

		han1.add(Tumo(s, n));
		han1.add(Haitei(s));
		han1.add(Chankan(s));
		han1.add(Rinshan(s));
		han1.add(Pinhu(s, m));
		han1.add(Onepeko(s, n, m));
		han1.add(Tanyao(s, n, m));

		while (han1.remove(""))
			;

		return han1;
	}

	// 役牌。刻子になるだけで適用されるため、ArrayListとして返す。
	private static ArrayList<String> Yakuhai(Setting s, Naki n, Mentsu m) {
		String h = "";
		String sf = s.getSettingData().get("場風");
		String pf = s.getSettingData().get("自風");
		ArrayList<String> y = new ArrayList<>(n.getPonData());
		ArrayList<String> yakuhai = new ArrayList<>();

		for (String ko : m.getKoutsu())
			y.add(ko);

		for (String ko : y) {
			h = SetHaiName.setCh(ko.split("[,]")[0]);
			if (h.matches("[白發中]") | h.equals(sf) | h.equals(pf))
				if (h.equals(sf) && h.equals(pf))
					h = "ダブ" + h;
			yakuhai.add(h);
		}
		for (String ka : m.getKantsu().keySet()) {
			h = SetHaiName.setCh(ka.split("[,]")[0]);
			if (h.matches("[白發中]") | h.equals(sf) | h.equals(pf))
				if (h.equals(sf) && h.equals(pf))
					h = "ダブ" + h;
			yakuhai.add(h);
		}
		return yakuhai;

	}

	// リーチと一発。ArrayList<String>で返す。
	private static ArrayList<String> Reach(Setting s) {
		ArrayList<String> reach = new ArrayList<>();
		String r = s.getSettingData().get("リーチ");

		switch (r) {
		case "有":
			reach.add("リーチ");
			if (s.getSettingData().get("一発").equals("有"))
				reach.add("一発");
			break;

		case "無":
			break;

		default:
			reach.add("ダブリー");
			if (s.getSettingData().get("一発").equals("有"))
				reach.add("一発");
		}

		return reach;
	}

	// 面前ツモ。
	private static String Tumo(Setting s, Naki n) {
		String str = "";
		if (s.getSettingData().get("和了方法").equals("ツモ") && n.getCountN() == 0)
			str = "面前ツモ";
		return str;
	}

	// ハイテイ。
	private static String Haitei(Setting s) {
		String str = "";
		if (s.getSettingData().get("ハイテイ").equals("有"))
			str = "ハイテイ";
		return str;
	}

	//
	private static String Chankan(Setting s) {
		String str = "";
		if (s.getSettingData().get("チャンカン").equals("有"))
			str = "チャンカン";
		return str;
	}

	//リンシャン。
	private static String Rinshan(Setting s) {
		String str = "";
		if (s.getSettingData().get("リンシャン").equals("有"))
			str = "リンシャン";
		return str;
	}

	// 平和。
	private static String Pinhu(Setting s, Mentsu m) {
		String str = "";
		if (m.getShuntsu().size() != 4 | !(m.getMachi().values().contains("リャンメン")))
			return str;

		String h = m.getJantou().split("[,]")[0];
		String sf = s.getSettingData().get("場風");
		String pf = s.getSettingData().get("自風");
		if (h.matches("[白發中]") | h.equals(sf) | h.equals(pf))
			return str;

		return str = "平和";
	}

	// 一盃口。
	private static String Onepeko(Setting s, Naki n, Mentsu m) {
		String str = "";
		if (n.getCountN() != 0 | m.getShuntsu().size() < 2)
			return str;

		int count = 0;

		for (String sh1 : m.getShuntsu()) {
			count = 0;
			for (String sh2 : m.getShuntsu())
				if (sh1.equals(sh2)) {
					++count;
					if (count == 2)
						return str = "一盃口";
				}
		}
		return "";
	}

	// タンヤオ。
	private static String Tanyao(Setting s, Naki n, Mentsu m) {
		ArrayList<String> hai = new ArrayList<>(m.getHaiData());
		String str = "";
		String sf = s.getSettingData().get("場風");
		String pf = s.getSettingData().get("自風");

		for (String nd : n.getNakiData())
			hai.add(nd);

		for (String h : hai)
			if (h.matches("[19].") | h.matches("[白發中]") | h.equals(pf) | h.equals(sf))
				return str;

		return str = "タンヤオ";
	}
}
