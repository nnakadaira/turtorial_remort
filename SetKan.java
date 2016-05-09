package mah_kan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

import mah_hai.Chars;
import mah_hai.Numbers;

import java.io.*;

import mah_item.Item;
import mah_setting.Setting;

/*
 * カン材の入力。指定された種類の牌をkantsuに格納する。
 * 以降の牌入力において、kantsuに格納された種類の牌は除外されるものとする。
 * Kanで作成するもの：カンの使用判定。暗・明カンの判定。入力文字列から指定されたカン材の抽出、格納。
 * カン材の面子分の入力する牌の減算。格納されたカン材と入力された牌の比較判定。ドラ表示への干渉。
 */

public class SetKan {

	// ankan→<文字列,(D)>とminkan→<文字列,(L)>をkantsuにまとめる処理。
	protected static LinkedHashMap<String, String> kanDL(Setting s) throws IOException {
		Item item = new Item();
		boolean duplication = false;
		ArrayList<String> ankan = new ArrayList<>();
		ArrayList<String> minkan = new ArrayList<>();

		// ankanとminkanそれぞれのセット。
		while (item.getYn() == false) {
			ankan = setKan("暗");
			if (s.getSettingData().get("リーチ").equals("無"))
				minkan = setKan("明");

			// ankanとminkanの内容に重複があった場合の処理。再入力。
			for (String an : ankan) {
				if (an.matches("r5[mps]"))
					an = "5" + an.charAt(2);
				for (String min : minkan) {
					if (min.matches("r5[mps]"))
						min = "5" + min.charAt(2);
					duplication = an.equals(min);
					if (duplication == true) {
						System.out.println("カンに重複が存在する。やり直し。");
						continue;
					}
				}
			}

			// 入力したものに対する最終確認。
			System.out.println("確認:");
			System.out.println("暗:" + ankan + "\n明:" + minkan);
			System.out.print("OK");
			item.callYesNo();
			if (s.getSettingData().get("リンシャン").equals("有") && ankan.size() + minkan.size() == 0) {
				System.out.println("リンシャンカイホウしてるのに何もカンしていない。再入力。");
				item.setYn(false);
			}
		}

		return setDL(ankan, minkan);
	}

	// 暗カンの数を設定。
	protected static void setCountD(Kan k) {
		int countD = 0;
		for (String kd : k.getKantsu().values())
			if (kd.matches("D"))
				++countD;
		k.setCountD(countD);
	}

	/*
	 * カン(同牌を全て使用)を用いたかの確認。sには[暗・明]いずれかを代入。戻り値ArrayList<String>。
	 * 'y'の場合：列挙された牌をsetKan()でkanに格納して返す。 'n'の場合：ArrayList=nullとして返す。
	 */
	protected static ArrayList<String> setKan(String s) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Item item = new Item();
		ArrayList<String> kan = new ArrayList<>();

		System.out.print(s + "カンの有無");

		// カン材を使用した場合(yn=true)の処理。カン材にした牌の種類を列挙→kanに格納する。
		if (item.isYn()) {

			// カンの処理。
			while (true) {
				System.out.println(s + "カンの確認：" + kan);
				System.out.println("牌の種類列挙(複数の場合','区切り)\n" + Item.showHaiValue() + ":");
				kan = sortKan(br.readLine());

				// 入力したものの確認。
				System.out.print("OK");

				// yn=trueならループを抜ける。
				if (item.isYn())
					break;

				// yn=falseなら再入力
				else {
					kan.clear();
					System.out.print("Retry:");
					continue;
				}
			}
		}

		// カン材を未使用の場合(yn=false)の処理。kan=null。
		if (!item.getYn())
			kan.clear();
		return kan;
	}

	// kanにanK(文字列,暗(D))とminK(文字列,明(L))を格納。HashMapとして返す。
	protected static LinkedHashMap<String, String> setDL(ArrayList<String> anK, ArrayList<String> minK) {
		LinkedHashMap<String, String> kan = new LinkedHashMap<>();
		for (String a : anK)
			kan.put(a, "D");
		for (String m : minK)
			kan.put(m, "L");
		return kan;
	}

	// 入力されたカン子の判定。牌の種類のみ列挙(複数の場合','区切り)。 牌の種類と合致したもののみ返す。重複した場合は再入力。確認有り。
	protected static ArrayList<String> sortKan(String s) throws IOException {
		HashSet<String> kan = new HashSet<>();

		ArrayList<String> num = new ArrayList<>();
		ArrayList<String> ch = new ArrayList<>();
		ArrayList<String> kantsu = new ArrayList<>();
		ArrayList<String> ka = new ArrayList<>();

		// HashSetにstrの内容を入れて重複消去。
		String[] str = s.toString().split("[,]");
		for (String st : str)
			kan.add(st);

		// kanの内容を再び配列に入れる。
		for (String k : kan) {
			ka.add(k);
		}

		// [数牌/字牌]それぞれソートしてkantsuに格納。
		num = Numbers.sortNum(ka);
		for (String name : new Numbers().getName())
			while (num.contains("r5" + name))
				num.set(num.indexOf("r5" + name), "5" + name);
		ch = Chars.sortCh(ka);
		kantsu.addAll(num);
		kantsu.addAll(ch);

		return kantsu;
	}
}
