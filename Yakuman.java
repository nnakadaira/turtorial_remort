package mah_yaku;

import java.util.ArrayList;
import java.util.HashSet;

import mah_hai.Numbers;
import mah_item.SetHaiName;
import mah_mentsu.Mentsu;
import mah_naki.Naki;
import mah_setting.Setting;
/*
 *13ハン
 *鳴き有り-スーカンツ、大三元、大四喜、小四喜、字一色、緑一色、清老頭
 *
 *鳴き無し-スーアンコ、国士無双、天和、地和、九連宝燈
 */

public class Yakuman {

	//
	public static ArrayList<String> setYakuman(Setting s, Naki n, Mentsu m) {
		ArrayList<String> yakuman = new ArrayList<>();
		yakuman.add(FourKantsu(m));
		yakuman.add(DaiSangen(n, m));
		yakuman.add(FourShiho(n, m));
		yakuman.add(CharOne(n, m));
		yakuman.add(GreenOne(n, m));
		yakuman.add(ChinRoutou(n, m));

		yakuman.add(FourAnko(n, m));
		yakuman.add(KokushiMusou(m));
		yakuman.add(TenChiHo(s));
		yakuman.add(Nine(m));

		return yakuman;
	}

	/*
	 * 九連宝燈。 (純)聴牌形:1112345678999, 和了可能:[1-9] (準)和了形:1112345678999+[1-9]
	 */
	private static String Nine(Mentsu m) {
		if (m.getHaiData().size() != 14)
			return "";

		ArrayList<String> hai = new ArrayList<>(m.getHaiData());
		Integer[] num = { 1, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9 };
		String name = hai.get(0).substring(hai.get(0).length() - 1);

		for (String h : hai)
			if (!h.endsWith(name))
				return "";

		for (int n : num)
			if (hai.contains(n + name))
				hai.remove(n + name);

		if (hai.size() == 1)
			if (m.getJantou().contains(hai.get(0)))
				return "純正九連宝燈";
			else
				return "九連宝燈";
		else
			return "";
	}

	// 天和と地和。
	private static String TenChiHo(Setting s) {
		if (s.getSettingData().get("和了方法").endsWith("和"))
			return s.getSettingData().get("和了方法");
		return "";
	}

	// 国士無双。
	private static String KokushiMusou(Mentsu m) {
		if (m.getHaiData().size() != 14)
			return "";

		HashSet<String> haiD = new HashSet<>();
		String regex = "[2-8]";

		for (String h : m.getHaiData())
			haiD.add(h);
		// Pattern haiP = Pattern.compile("(r5|[2-8])");
		// Matcher haiM = haiP.matcher(m.getHaiData().toString());
		// if (haiM.find())
		// return "";
		if (haiD.size() != 13 | haiD.toString().matches(".*" + regex + ".*"))
			return "";

		if (m.getAgari().contains(m.getJantou()))
			return "国士無双(13面待ち)";
		else
			return "国士無双";
	}

	// 四暗刻。
	private static String FourAnko(Naki n, Mentsu m) {
		if (m.getKantsu().size() + m.getKoutsu().size() == 4 && n.getCountN() == 0)
			if (m.getMachi().containsKey("単騎"))
				return "四暗刻(単)";
			else
				return "四暗刻";
		return "";
	}

	// 清老頭。
	private static String ChinRoutou(Naki n, Mentsu m) {
		if (n.getPonData().size() + m.getKantsu().size() + m.getKoutsu().size() != 4)
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
			if (!(h.matches("(1.|9.)")))
				return "";

		return "清老頭";
	}

	// 索子の内、絵柄に赤を使用していないものと、發を用いた役。赤を使用しているのは[5s,7s,9s]
	private static String GreenOne(Naki n, Mentsu m) {
		String name = new Numbers().getName()[2];
		String ht = "發";
		ArrayList<String> hai = new ArrayList<>(m.getHaiData());

		for (String naki : n.getNakiData())
			hai.add(naki);

		for (String kan : m.getKantsu().keySet())
			hai.add(kan);

		for (String h : hai)
			if (!(h.endsWith(name)) | !(h.equals(ht)))
				return "";

		return "緑一色";
	}

	// 字一色。
	private static String CharOne(Naki n, Mentsu m) {
		if (n.getPonData().size() + m.getKantsu().size() + m.getKoutsu().size() != 4)
			return "";

		ArrayList<String> haiD = new ArrayList<>();
		for (String h : m.getHaiData())
			haiD.add(SetHaiName.setCh(h));
		for (String kan : m.getKantsu().keySet())
			haiD.add(SetHaiName.setCh(kan));
		for (String naki : n.getNakiData())
			haiD.add(SetHaiName.setCh(naki));

		if (haiD.contains(""))
			return "";
		else
			return "字一色";
	}

	// 四喜和。
	private static String FourShiho(Naki n, Mentsu m) {
		if (n.getPonData().size() + m.getKantsu().size() + m.getKoutsu().size() < 3)
			return "";

		ArrayList<String> hai = new ArrayList<>();
		int countFun = 0;

		for (String pon : n.getPonData())
			hai.add(pon.split("[,]")[0]);
		for (String kou : m.getKoutsu())
			hai.add(kou.split("[,]")[0]);
		for (String kan : m.getKantsu().keySet())
			hai.add(kan);

		for (String h : hai)
			if (h.matches("[東南西北]"))
				++countFun;

		if (countFun == 4)
			return "大四喜";
		else if (countFun == 3 && m.getJantou().split("[,]")[0].matches("[東南西北]"))
			return "小四喜";
		else
			return "";
	}

	// 大三元。
	private static String DaiSangen(Naki n, Mentsu m) {
		if (n.getPonData().size() + m.getKantsu().size() + m.getKoutsu().size() < 3)
			return "";

		ArrayList<String> hai = new ArrayList<>();
		int countSan = 0;

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
			return "大三元";
		else
			return "";
	}

	// 四槓子。
	private static String FourKantsu(Mentsu m) {
		if (m.getKantsu().size() == 4)
			return "四槓子";
		return "";
	}
}
