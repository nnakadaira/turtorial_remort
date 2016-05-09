package mah_yaku;

import java.util.ArrayList;

import mah_hai.Numbers;
import mah_item.SetHaiName;
import mah_mentsu.Mentsu;
import mah_naki.Naki;
import mah_setting.Setting;

/* *
 *2ハン
 *鳴き有り-トイトイホー、三色同項、三槓子
 *食い下がり有り-三色同順、一通、チャンタ
 *
 *鳴き無し-七対子、三暗子
 */

public class Han2 {

	public static ArrayList<String> setHan2(Setting s, Naki n, Mentsu m) {
		ArrayList<String> han2 = new ArrayList<>();
		han2.add(Toytoyho(n, m));
		han2.add(ThreeColordoukou(n, m));
		han2.add(ThreeColordoushun(n, m));
		han2.add(ThreeKantsu(m));
		han2.add(ThreeAnko(m));
		han2.add(Straight(n, m));
		han2.add(Chanta(n, m));
		han2.add(ChiToytsu(m));

		return han2;
	}

	// トイトイホー
	private static String Toytoyho(Naki n, Mentsu m) {
		String str = "";
		if (n.getPonData().size() + m.getKoutsu().size() + m.getKantsu().size() == 4)
			str = "トイトイホー";
		return str;
	}

	// 三色同項
	private static String ThreeColordoukou(Naki n, Mentsu m) {
		String str = "";
		if (n.getPonData().size() + m.getKoutsu().size() + m.getKantsu().size() < 3)
			return str;

		ArrayList<String> haiD = new ArrayList<>();

		for (String po : n.getPonData())
			haiD.add(SetHaiName.setNum(po.split("[,]")[0]));
		for (String ko : m.getKoutsu())
			haiD.add(SetHaiName.setNum(ko.split("[,]")[0]));
		for (String ka : m.getKantsu().keySet())
			haiD.add(SetHaiName.setNum(ka));

		while (haiD.remove(""))
			if (haiD.size() < 3)
				return str;

		if (ThreeColor(haiD))
			return str = "三色同刻";

		return str;
	}

	// 三槓子。
	private static String ThreeKantsu(Mentsu m) {
		String str = "";
		if (m.getKantsu().size() == 3)
			return str = "三槓子";
		return str;
	}

	// 三色同順。
	private static String ThreeColordoushun(Naki n, Mentsu m) {
		String str = "";
		if (n.getChiData().size() + m.getShuntsu().size() < 3)
			return str;

		ArrayList<String> haiD = new ArrayList<>();

		for (String sh : m.getShuntsu())
			haiD.add(sh.split("[,]")[0]);
		for (String chi : n.getChiData())
			haiD.add(chi.split("[,]")[0]);

		if (ThreeColor(haiD))
			if (n.getCountN() == 0)
				return str = "三色同順";
			else
				return str = "三色同順(鳴)";

		return str;
	}

	// 三色系の判断。
	private static boolean ThreeColor(ArrayList<String> hai) {
		if (hai.size() < 3)
			return false;

		ArrayList<String> haiD = new ArrayList<>(hai);
		ArrayList<String> num = new ArrayList<>();
		int countM = 0, countP = 0, countS = 0;

		for (int i = 0; i < haiD.size(); i++)
			if (haiD.get(i).matches("r5."))
				haiD.set(i, "5" + haiD.get(i).charAt(2));

		for (String h1 : haiD) {
			num.clear();
			countM = 0;
			countP = 0;
			countS = 0;

			for (String h2 : haiD) {
				if (Integer.valueOf(h1.charAt(0) - 48) == (Integer.valueOf(h2.charAt(0) - 48))) {
					num.add(h2);
				}
			}

			for (String nu : num)
				switch (nu.charAt(1)) {
				case 'm':
					++countM;
					break;
				case 'p':
					++countP;
					break;
				case 's':
					++countS;
					break;
				}

			if (countM == 1 && countP == 1 && countS == 1)
				return true;
		}

		return false;
	}

	// 一気通関。
	private static String Straight(Naki n, Mentsu m) {
		String str = "";
		if (n.getChiData().size() + m.getShuntsu().size() < 3)
			return str;

		ArrayList<String> haiD = new ArrayList<>();
		Numbers numbers = new Numbers();
		boolean boolean1 = false, boolean4 = false, boolean7 = false;

		for (String sh : m.getShuntsu())
			haiD.add(sh.split("[,]")[0]);
		for (String chi : n.getChiData())
			haiD.add(chi.split("[,]")[0]);

		for (String name : numbers.getName()) {
			boolean1 = false;
			boolean4 = false;
			boolean7 = false;

			for (String sh : haiD) {
				if (sh.equals("1" + name))
					boolean1 = true;
				if (sh.equals("4" + name))
					boolean4 = true;
				if (sh.equals("7" + name))
					boolean7 = true;
			}

			if (boolean1 && boolean4 && boolean7)
				if (n.getCountN() == 0)
					return str = "一気通関";
				else
					return str = "一気通関(鳴)";
		}

		return str;
	}

	// チャンタ。
	private static String Chanta(Naki n, Mentsu m) {
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
			switch (Integer.valueOf(s.charAt(0) - 48)) {
			case 1:
				break;
			case 7:
				break;
			default:
				return str;
			}

		for (String k : kou)
			switch (Integer.valueOf(k.charAt(0) - 48)) {
			case 1:
				break;
			case 9:
				break;
			default:
				if (SetHaiName.setCh(k).equals(""))
					return str;
			}

		if (n.getCountN() == 0)
			return str = "チャンタ";
		else
			return str = "チャンタ(鳴)";
	}

	private static String ThreeAnko(Mentsu m) {
		String str = "";
		int countAn = m.getKoutsu().size();

		for (String key : m.getKantsu().keySet())
			if (m.getKantsu().get(key).equals("D"))
				++countAn;

		if (countAn == 3)
			return str = "三暗刻";

		return str;
	}

	// 七対子。
	private static String ChiToytsu(Mentsu m) {
		String str = "";
		if (m.isToy7())
			return str = "七対子";
		return str;
	}

}
