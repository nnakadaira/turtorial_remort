package mah_item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import mah_dora.Dora;
import mah_hai.Numbers;
import mah_kan.Kan;
import mah_naki.Naki;
import mah_setting.Setting;

//入力されてきたものの矛盾判定。5枚以上存在するとか、赤牌が足りないとか。
public class Determine {

	// public static boolean determine(Kan k, Dora d, String h) {
	// // 入力牌とカン材が重複した場合の処理。再入力。
	// if (determineKan(k.getKantsu(), h) == true) {
	// System.out.println("Error:カンで使用された牌が入力されている。やり直し。");
	// return true;
	// }
	//
	// // 入力牌の内、5枚以上使用された種類の牌がある場合の処理。再入力。
	// if (determineHai(d, h)) {
	// System.out.println("Error:5枚以上入力されている。やり直し。");
	// return true;
	// }
	//
	// // 赤牌が規定枚数以上の場合の処理。再入力。
	// if (determineR(d, new ArrayList<String>().add(h))) {
	// System.out.println("Error:赤牌が規定枚数以上しようされている。やり直し。");
	// return true;
	// }
	// return false;
	//
	// }

	public static boolean determineDora(Setting set, Kan kan, ArrayList<String> dispD) {
		int i = 1;
		// 最大枚数を超えていた、または満たない場合の処理。
		if (set.getSettingData().get("リーチ").equals("有") | set.getSettingData().get("リーチ").equals("ダブルリーチ"))
			i += 1 + kan.getKantsu().size();
		if (dispD.size() < kan.getKantsu().size() + i) {
			System.out.println("最低枚数を下回っている。再入力。");
			return true;
		}
		if (dispD.size() > 10 && 0 < kan.getKantsu().size() && kan.getKantsu().size() < 4) {
			System.out.println("最大枚数を超えている。再入力。");
			return true;
		}
		if (determineKan(kan.getKantsu(), dispD)) {
			System.out.println("カンと重複。再入力。");
			return true;
		}
		if (determineR(dispD)) {
			System.out.println("赤牌多すぎ。再入力。");
			return true;
		}
		return false;

	}

	// 列挙されたカン情報を持つkanと入力された牌情報haiの比較。haiの中でkanが使用されていた場合、trueを返す。1枚用。
	public static boolean determineKan(HashMap<String, String> kan, String hai) {
		for (String key : kan.keySet())
			if (hai.equals(key))
				return true;
		return false;
	}

	// 列挙されたカン情報を持つkanと入力された牌情報haiの比較。haiの中でkanが使用されていた場合、trueを返す。list用。
	public static boolean determineKan(HashMap<String, String> kan, ArrayList<String> hai) {
		if (hai.size() != 0)
			for (String h : hai)
				for (String key : kan.keySet())
					if (h.equals(key))
						return true;
		return false;
	}

	// 入力された牌の枚数の確認。5枚以上ならError、trueを返す。1枚用。
	public static boolean determineHai(Dora dora, String hai) {
		int sheetsCount;
		boolean flg = false;
		Numbers num = new Numbers();
		ArrayList<String> dispD = new ArrayList<>();
		ArrayList<String> haiData = new ArrayList<>();

		// 赤牌を普通の牌に置換。
		for (int i = 0; i < num.getName().length; i++) {
			if (hai.equals("r5" + num.getName()[i]))
				hai = "5" + num.getName()[i];
			haiData.add(hai);
			for (String d : dispD) {
				if (d.equals("r5" + num.getName()[i]))
					d = "5" + num.getName()[i];
				haiData.add(d);
			}
		}

		// sheetCountで重複した回数を数える。
		for (String str : haiData) {
			sheetsCount = 0;
			if (str.equals(hai))
				++sheetsCount;

			// 4回以上に達した場合の処理。
			if (sheetsCount > 4)
				flg = true;
		}
		return flg;

	}

	// 入力された牌の枚数の確認。5枚以上ならError、trueを返す。Array用。
	public static boolean determineHai(Dora dora, ArrayList<String> hai) {
		int sheetsCount;
		boolean flg = false;
		ArrayList<String> dispD = new ArrayList<>(dora.getDispDora());
		ArrayList<String> haiData = new ArrayList<>();

		// 赤牌を普通の牌に置換。
		for (String h : hai) {
			if (h.matches("r5[mps]"))
				h = "5" + h.charAt(2);
			haiData.add(h);
		}
		for (String d : dispD) {
			if (d.matches("r5[mps]"))
				d = "5" + d.charAt(2);
			haiData.add(d);
		}

		// sheetCountで重複した回数を数える。
		for (String str1 : haiData) {
			sheetsCount = 0;
			for (String str2 : haiData) {
				if (str1.equals(str2))
					++sheetsCount;

				// 4回以上に達した場合の処理。
				if (sheetsCount > 4)
					return flg = true;
			}
		}
		return flg;

	}

	// 赤牌の枚数確認。それぞれの種類ごとにカウントして、規定枚数を超えたらtrueを返す。1枚用。
	public static boolean determineN(Dora d, ArrayList<String> hai) {
		int countNM = 0, countNP = 0, countNS = 0;

		// r5[mps]を検知した場合、対応するカウントを+する。
		for (String n : hai) {
			switch (n) {
			case "5m":
				++countNM;
				break;
			case "5p":
				++countNP;
				break;
			case "5s":
				++countNS;
				break;
			default:
				break;
			}
		}

		StringTokenizer st = new StringTokenizer(d.getDispDora().toString(), "[,]");
		while (st.hasMoreTokens())
			switch (st.nextToken()) {
			case "5m":
				++countNM;
				break;
			case "5p":
				++countNP;
				break;
			case "5s":
				++countNS;
				break;
			default:
				break;
			}

		// いずれかが規定枚数を超えたとき、true。
		if (countNM > 3 || countNP > 2 || countNS > 3) {
			return true;
		}
		return false;
	}

	// 赤牌の枚数確認。それぞれの種類ごとにカウントして、規定枚数を超えたらtrueを返す。Array用。
	public static boolean determineR(Dora d, ArrayList<String> hai) {
		int countRM = 0, countRP = 0, countRS = 0;

		// r5[wps]を検知した場合、対応するカウントを+する。
		for (String r : hai) {
			StringTokenizer st1 = new StringTokenizer(r, "[,]");
			while (st1.hasMoreTokens())
				switch (st1.nextToken()) {
				case "r5m":
					++countRM;
					break;
				case "r5p":
					++countRP;
					break;
				case "r5s":
					++countRS;
					break;
				default:
					break;
				}
		}
		StringTokenizer st2 = new StringTokenizer(d.getDispDora().toString(), "[,]");
		while (st2.hasMoreTokens())
			switch (st2.nextToken()) {
			case "r5m":
				++countRM;
				break;
			case "r5p":
				++countRP;
				break;
			case "r5s":
				++countRS;
				break;
			default:
				break;
			}

		// いずれかが規定枚数を超えたとき、true。
		if (countRM > 1 || countRP > 2 || countRS > 1) {
			return true;
		}
		return false;
	}

	// 赤牌の枚数確認。それぞれの種類ごとにカウントして、規定枚数を超えたらtrueを返す。List用。
	public static boolean determineR(List<String> hai) {
		int countM = 0, countP = 0, countS = 0;

		// r5[mps]を検知した場合、対応するカウントを+する。
		for (String r : hai) {
			StringTokenizer st = new StringTokenizer(r, "[,]");
			while (st.hasMoreTokens())
				switch (st.nextToken()) {
				case "r5m":
					++countM;
					break;
				case "r5p":
					++countP;
					break;
				case "r5s":
					++countS;
					break;
				default:
					break;
				}
		}

		// いずれかが規定枚数を超えたとき、true。
		if (countM > 1 || countP > 2 || countS > 1) {
			return true;
		}
		return false;
	}

	// Kan,Setting,ArrayListの内容の矛盾判定。
	public static boolean determine(Kan k, Dora d, ArrayList<String> h) {
		// 入力牌とカン材が重複した場合の処理。再入力。
		if (h.isEmpty()) {
			System.out.println("Error:無効な牌が入力されている。やり直し。");
			return true;
		}

		if (determineKan(k.getKantsu(), h) == true) {
			System.out.println("Error:カンで使用された牌が入力されている。やり直し。");
			return true;
		}

		// 入力牌の内、5枚以上使用された種類の牌がある場合の処理。再入力。
		if (determineHai(d, h)) {
			System.out.println("Error:5枚以上入力されている。やり直し。");
			return true;
		}

		// 赤牌が規定枚数以上の場合の処理。再入力。
		if (determineR(d, h)) {
			System.out.println("Error:赤牌が規定枚数以上しようされている。やり直し。");
			return true;
		}

		if (determineN(d, h)) {
			System.out.println("Error:赤牌が規定枚数以下で入力されている。やり直し。");
			return true;
		}
		return false;

	}

	// 鳴きあり、String文字列が引数の場合の処理。
	public static boolean determine(Kan k, Dora d, Naki n, String h) {
		ArrayList<String> hai = new ArrayList<>(n.getNakiData());

		for (String str : h.split("[,]"))
			hai.add(str);

		return determine(k, d, hai);
	}

	// 鳴き有り、ArrayListが引数の場合の処理。
	public static boolean determine(Kan k, Dora d, Naki n, ArrayList<String> h) {
		ArrayList<String> hai = new ArrayList<>(n.getNakiData());

		for (String str : h)
			hai.add(str);

		return determine(k, d, hai);
	}

}
