package mah_yaku;

import java.util.ArrayList;

import mah_mentsu.Mentsu;
import mah_naki.Naki;
import mah_setting.Setting;

/*
 *4ハン
 *鳴き有り-小三元、混老頭
 */
public class Han4 {

	public static ArrayList<String> setHan4(Setting s, Naki n, Mentsu m) {
		ArrayList<String> han4 = new ArrayList<>();
		han4.add(Shou3gen(n, m));
		han4.add(HonRoutou(n, m));

		return han4;
	}

	// 小三元。
	private static String Shou3gen(Naki n, Mentsu m) {
		if (n.getPonData().size() + m.getKantsu().size() + m.getKoutsu().size() < 2)
			return "";

		ArrayList<String> hai = new ArrayList<>();
		int countSan = 0;

		hai.add(m.getJantou().split("[,]")[0]);
		for (String pon : n.getPonData())
			hai.add(pon.split("[,]")[0]);
		for (String kou : m.getKoutsu())
			hai.add(kou.split("[,]")[0]);
		for (String kan : m.getKantsu().keySet())
			hai.add(kan);

		for (String h : hai)
			switch (h) {
			case "白":
			case "發":
			case "中":
				++countSan;
				break;
			default:
			}

		if (countSan == 3)
			return "小三元";
		else
			return "";
	}

	// 混老頭。
	private static String HonRoutou(Naki n, Mentsu m) {
		if (n.getPonData().size() + m.getKantsu().size() + m.getKoutsu().size() < 4)
			return "";

		ArrayList<String> hai = new ArrayList<>();

		hai.add(m.getJantou().split("[,]")[0]);
		for (String pon : n.getPonData())
			hai.add(pon.split("[,]")[0]);
		for (String kou : m.getKoutsu())
			hai.add(kou.split("[,]")[0]);
		for (String kan : m.getKantsu().keySet())
			hai.add(kan);

		for (String h : hai)
			if (h.matches("(r5|[2-8])."))
				return "";

		return "混老頭";
	}
}
