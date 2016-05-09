package mah_mentsu;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/*
 * 面子情報に含まれる文字列と和了牌を比較して、一致した場合にその面子を待ちとする。
 * SK=Shuntsu,Koutsu
 */

public class Machi {

	// 待ちの設定
	public static LinkedHashMap<String, String> setMachi(Mentsu men) {
		LinkedHashMap<String, String> m = new LinkedHashMap<>();

		// 面子それぞれと和了牌を比較して、和了牌を含むものを待ちとして保持させる。
		LinkedHashMap<String, String> shunM = setMachiMap(men.getAgari(), men.getJantou(), men.getShuntsu());
		LinkedHashMap<String, String> kouM = setMachiMap(men.getAgari(), men.getJantou(), men.getKoutsu());
		LinkedHashMap<String, String> janM = setMachi(men.getAgari(), men.getJantou());

		// ひとつにまとめる。
		if (shunM.size() != 0)
			for (String s : shunM.keySet())
				m.put(s, shunM.get(s));

		if (kouM.size() != 0)
			for (String k : kouM.keySet())
				m.put(k, kouM.get(k));

		if (janM.size() != 0)
			for (String j : janM.keySet())
				m.put(j, janM.get(j));

		return m;
	}

	// 待ちの具体的な種類設定。
	protected static LinkedHashMap<String, String> setMachiMap(String agari, String jan, ArrayList<String> SK) {
		LinkedHashMap<String, String> mMap = new LinkedHashMap<>();
		String[] m;
		LinkedHashSet<String> machi = setMachi(agari, jan, SK);

		for (String str : machi) {
			m = str.split("[,]");
			if (m[0].contains("r"))
				m[0] = "5" + m[0].indexOf(2);
			if (m[1].contains("r"))
				m[1] = "5" + m[1].indexOf(2);

			if (Integer.valueOf(m[0].charAt(0) - 48) == Integer.valueOf(m[1].charAt(0) - 48 - 1)) {
				String a = "1" + m[0].charAt(1);
				String b = "9" + m[0].charAt(1);
				if (m[0].equals(a) | m[1].equals(b))
					mMap.put(str, "ペンチャン");
				else
					mMap.put(str, "リャンメン");

			} else if (m[0].equals(m[1]))
				mMap.put(str, "シャンポン");
			else
				mMap.put(str, "カンチャン");
		}

		return mMap;
	}

	// 待ちの設定。和了牌と待ちを合わせると何かしらの面子になる。
	protected static LinkedHashSet<String> setMachi(String agari, String jan, ArrayList<String> SK) {
		LinkedHashSet<String> machi = new LinkedHashSet<>();
		String[] men = new String[3];

		String a = agari;
		if (a.startsWith("r"))
			a = "5" + a.charAt(2);

		//
		for (String sk : SK) {
			if (sk.contains(a)) {
				men = sk.split("[,]");

				//
				for (int i = 0; i < men.length; i++) {
					if (men[i].contains(a))
						switch (i) {
						case 0:
							machi.add(men[1] + "," + men[2]);
							break;
						case 1:
							machi.add(men[0] + "," + men[2]);
							break;
						case 2:
							machi.add(men[0] + "," + men[1]);
							break;
						}
				}
			}
		}
		for (String ma : machi) {
			String[] j = ma.split(",");
			if (j[0].equals(j[1]))
				machi.add(jan);
		}
		return machi;
	}

	// 単騎待ちの設定。
	protected static LinkedHashMap<String, String> setMachi(String agari, String jan) {
		LinkedHashMap<String, String> machi = new LinkedHashMap<>();
		String[] tan = jan.split("[,]");
		if (tan[0].equals(agari))
			machi.put(tan[1], "単騎");
		if (tan[1].equals(agari))
			machi.put(tan[0], "単騎");
		//
		return machi;
	}
}
