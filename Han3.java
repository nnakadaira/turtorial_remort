package mah_yaku;

import java.util.ArrayList;

import mah_mentsu.Mentsu;
import mah_naki.Naki;
import mah_setting.Setting;

/*
 *3ハン
 *食い下がり有り-混一、純チャン
 *鳴き無し-二盃口
 */
public class Han3 {

	public static ArrayList<String> setHan3(Setting s, Naki n, Mentsu m) {
		ArrayList<String> han3 = new ArrayList<>();
		han3.add(HonOne(n, m));
		han3.add(JunChan(n, m));
		han3.add(Twopeko(m));

		return han3;
	}

	// 混一。
	private static String HonOne(Naki n, Mentsu m) {
		String str = "";
		String name = "";
		ArrayList<String> haiD = new ArrayList<>();

		for (String h : m.getHaiData())
			haiD.add(h);

		for (String naki : n.getNakiData())
			haiD.add(naki);

		for (String k : m.getKantsu().keySet())
			haiD.add(k);

		for (String h : haiD) {
			name = haiD.get(0).substring(haiD.get(0).length() - 1);
			if (!(h.endsWith(name)) && h.length() > 1)
				return str;
		}

		if (n.getCountN() != 0)
			return "混一(鳴)";

		return "混一";
	}

	// 純チャン。
	private static String JunChan(Naki n, Mentsu m) {
		String str = "";
		ArrayList<String> shun = new ArrayList<>(n.getChiData());
		ArrayList<String> kou = new ArrayList<>(n.getPonData());

		for (String sh : m.getShuntsu())
			shun.add(sh);

		for (String ko : m.getKoutsu())
			kou.add(ko);
		for (String ka : m.getKantsu().keySet())
			kou.add(ka);

		for (String s : shun)
			switch (s.split("[,]")[0].charAt(0)) {
			case 1:
				break;
			case 7:
				break;
			default:
				return str;
			}

		for (String k : kou)
			switch (k.split("[,]")[0].charAt(0)) {
			case 1:
				break;
			case 9:
				break;
			default:
				return str;
			}

		if (n.getCountN() == 0)
			return str = "純チャン";
		else
			return str = "純チャン(鳴)";
	}

	// 二盃口。
	private static String Twopeko(Mentsu m) {
		String str = "";
		if (m.getShuntsu().size() != 4)
			return str;

		ArrayList<String> shun = new ArrayList<>(m.getShuntsu());
		int countSh = 0;
		int countPeko = 0;

		for (String sh1 : shun) {
			countSh = 0;
			for (String sh2 : shun) {
				if (sh1.equals(sh2))
					++countSh;
			}

			if (countSh == 2) {
				String rm = sh1;
				++countPeko;
				if (countPeko == 2)
					return str = "二盃口";

				while (shun.remove(rm)) {
					--countSh;
					if (countSh == 0)
						break;
				}
			}
		}
		return str;
	}

}
