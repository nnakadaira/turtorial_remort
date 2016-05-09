package mah_yaku;

import java.util.ArrayList;

import mah_mentsu.Mentsu;
import mah_naki.Naki;
import mah_setting.Setting;

/*
 *6ハン
 *食い下がり有り-清一
 *鳴き無し-人和
 */
public class Han6 {

	public static ArrayList<String> setHan6(Setting s, Naki n, Mentsu m) {
		ArrayList<String> han6 = new ArrayList<>();
		han6.add(ChinOne(n, m));
		return han6;
	}

	// 清老頭。
	private static String ChinOne(Naki n, Mentsu m) {
		String str = "";
		ArrayList<String> hai = new ArrayList<>(m.getHaiData());
		String name = hai.get(0).substring(hai.get(0).length() - 1);

		for (String naki : n.getNakiData())
			hai.add(naki);

		for (String kan : m.getKantsu().keySet())
			hai.add(kan);

		for (String h : hai)
			if (!(h.endsWith(name)))
				return str;

		if (n.getCountN() != 0)
			return "清一(鳴)";

		return "清一";
	}

	// public static String Renho() {
	// String str = "";
	// return str;
	// }
}
