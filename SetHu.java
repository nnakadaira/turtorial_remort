package mah_yaku;

import java.util.LinkedHashMap;

import mah_item.SetHaiName;
import mah_mentsu.Mentsu;
import mah_naki.Naki;
import mah_setting.Setting;

//符の設定。
public class SetHu {

	// 符計算処理の合算。。
	public static void setHu(Setting s, YakuList yl) {
		int hu = setAgariHu(s, yl.getYakuList().get(0).getNaki());
		int h = 0;

		hu += setKanHu(yl.getYakuList().get(0).getMen());
		hu += setPonHu(yl.getYakuList().get(0).getNaki());

		for (YakuData yd : yl.getYakuList()) {
			h = hu;
			h += setJanHu(s, yd.getMen());
			h += setKouHu(yd.getMen());
			h += setMachiHu(s, yd.getMen());
			h = setSpecialHu(s, yd, h);

			if (h % 10 > 0)
				h += 10 - (h % 10);

			yd.setHu(h);
		}
	}

	// 特殊な符。(鳴き平和系とか)
	private static int setSpecialHu(Setting s, YakuData yd, int h) {
		int sh = yd.getMen().getShuntsu().size() + yd.getNaki().getChiData().size();
		if (yd.getMen().isToy7())
			return 25;

		if (yd.getYakumei().contains("平和"))
			if (s.getSettingData().get("和了方法").equals("ツモ"))
				return 20;
			else
				return 30;

		if (sh == 4 && yd.getMen().getMachi().values().contains("リャンメン"))
			return 30;

		return h;
	}

	// 待ちに関する符計算。
	private static int setMachiHu(Setting s, Mentsu men) {
		LinkedHashMap<String, Integer> hulist = new LinkedHashMap<>();
		int hu = 0;

		if (s.getSettingData().get("和了方法").equals("ツモ"))
			hu = 2;

		for (String key : men.getMachi().keySet())
			switch (men.getMachi().get(key)) {
			case "リャンメン":
			case "シャンポン":
				hulist.put(men.getMachi().get(key), hu);
				break;
			default:
				hulist.put(men.getMachi().get(key), hu + 2);
			}

		for (String key : hulist.keySet())
			hu = Math.max(hu, hulist.get(key));

		return hu;

	}

	// 刻子に関する符計算。
	private static int setKouHu(Mentsu men) {
		int hu = 0;
		int huTotal = 0;

		for (String k : men.getKoutsu()) {
			hu = 0;
			if (k.matches("([2-8]|r5)."))
				hu = 4;
			else
				hu = 8;

			huTotal += hu;
		}

		return huTotal;
	}

	// 雀頭に関する符計算。
	private static int setJanHu(Setting s, Mentsu men) {
		String sf = s.getSettingData().get("場風");
		String pf = s.getSettingData().get("自風");

		String jan = men.getJantou().split("[,]")[0];
		if (jan.equals(sf) | jan.equals(pf) | SetHaiName.setSan(jan) != "")
			return 2;

		return 0;
	}

	// 明刻に関する符計算。
	private static int setPonHu(Naki naki) {
		int hu = 0;

		for (String p : naki.getPonData())
			if (p.matches("([2-8]|r5)."))
				hu += 2;
			else
				hu += 4;
		return hu;
	}

	// カンに関する符計算。
	private static int setKanHu(Mentsu men) {
		int hu = 0;
		int huTotal = 0;

		for (String k : men.getKantsu().keySet()) {
			hu = 0;
			if (k.matches("([2-8]|r5)."))
				hu = 8;
			else
				hu = 16;

			if (men.getKantsu().get(k).equals("D"))
				hu *= 2;

			huTotal += hu;
		}

		return huTotal;
	}

	// 和了の仕方に関する符計算。
	private static int setAgariHu(Setting s, Naki n) {
		if (s.getSettingData().get("和了方法").equals("ロン") && n.getCountN() == 0)
			return 30;
		else
			return 20;
	}
}
