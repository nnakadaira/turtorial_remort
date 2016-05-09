package mah_yaku;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mah_dora.Dora;
import mah_kan.Kan;
import mah_mentsu.Mentsu;
import mah_mentsu.MentsuList;
import mah_naki.Naki;
import mah_setting.Setting;

//役牌以外はbooleanで判定する。trueの場合、Stringで役名を返す。役名はyakuData(LinkedHashMap)のkeyになる。
public class SetYaku {
	//
	public static void setYaku(Setting s, Kan k, Dora d, Naki n, MentsuList ml, YakuList yl) {
		YakuData yd;

		for (Mentsu m : ml.getMenSh()) {
			yd = setYakuData(s, k, d, n, m);
			yl.setYakuList(yd);
		}

		for (Mentsu m : ml.getMenKo()) {
			yd = setYakuData(s, k, d, n, m);
			yl.setYakuList(yd);
		}
	}

	private static YakuData setYakuData(Setting s, Kan k, Dora d, Naki n, Mentsu m) {
		int han = setDoraHan(k, d, n, m);
		YakuData yd = new YakuData(d, n, m);
		ArrayList<String> yakumei = new ArrayList<>();
		ArrayList<String> yakuman = new ArrayList<>();

		for (String h1 : Han1.setHan1(s, n, m))
			if (h1 != "")
				yakumei.add(h1);

		for (String h2 : Han2.setHan2(s, n, m))
			if (h2 != "")
				yakumei.add(h2);

		for (String h3 : Han3.setHan3(s, n, m))
			if (h3 != "")
				yakumei.add(h3);

		for (String h4 : Han4.setHan4(s, n, m))
			if (h4 != "")
				yakumei.add(h4);

		for (String h6 : Han6.setHan6(s, n, m))
			if (h6 != "")
				yakumei.add(h6);

		for (String ym : Yakuman.setYakuman(s, n, m))
			if (ym != "")
				yakuman.add(ym);

		if (yakuman.size() != 0) {
			yakumei = new ArrayList<>(yakuman);
			han = 0;
		}

		for (String yname : yakumei)
			han += yd.getYaku().get(yname);

		if (yakumei.isEmpty())
			yakumei.add("役無し");
		yd.setHan(han);
		yd.setYakumei(yakumei);
		return yd;
	}

	// ドラと手牌の比較によるハン設定。
	private static int setDoraHan(Kan k, Dora d, Naki n, Mentsu m) {
		int han = 0;
		Pattern pa;
		Matcher ma;

		ArrayList<String> haiD = new ArrayList<>(m.getHaiData());
		for (String nd : n.getNakiData())
			haiD.add(nd);

		for (String dl : d.getDoraList()) {
			for (String kan : k.getKantsu().keySet()) {

				// カンとドラの比較。一致した場合は4枚分なので+4、カンが5[mps]の場合は赤牌の分も+。
				if (kan.matches("(r5|5)."))
					switch (kan.substring(kan.length() - 1)) {
					case "p":
						++han;
					default:
						++han;
					}

				if (dl.equals(kan))
					han += 4;
			}

			pa = Pattern.compile(dl);
			ma = pa.matcher(haiD.toString());
			while (ma.find())
				++han;
		}
		pa = Pattern.compile("r");
		ma = pa.matcher(haiD.toString());
		while (ma.find())
			++han;

		return han;
	}

}
