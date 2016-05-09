package mah_mentsu;

import java.util.*;

import mah_hai.Hai;

import java.io.*;

import mah_kan.Kan;
import mah_naki.Naki;

// セットされた面子の種類を判定する。
/*
 * 等価判定 --1番目:対子判定--(n,n) になるものだけを抽出。合致した場合、tCount+=1。 tCount = 1の場合、雀頭確定。
 * 2<=tCount<=6の場合、一旦保留。 tCount = 7 の場合、役確定(七対子)。
 *
 * --2番目:順子判定--(n,n+1,n+2) になるものを検索。合致した場合、sCount+=1。 2<=count<=6
 * の場合、残ったもので再度対子判定して雀頭確定。 tCount=1に修正。
 *
 * --3番目:刻子判定--(n,n,n)になるものを検索。合致した場合、kCount+=1。 最終的に、t+s+k=5で完成。
 *
 *
 */

public class SetMen extends SortMen {

	// // men(順先or刻先)に面子パターンをセット。
	/*
	 * ArrayList<Mentsu>のMentsuにtoyをそれぞれ設定する。引数:kan,sortHai。戻り値:面子パターン。
	 * toyの数だけMentsu menを生成し、kantsuもそれぞれに代入しておく。ArrayList<Mentsu>にまとめる。
	 * あとでclone()して面子判定(順子先)と面子判定(刻子先)に対応させる。
	 *
	 */

	// 雀頭のみをセットされた面子パターンをtoyの数だけ生成したものをListにまとめる。
	protected static ArrayList<Mentsu> setListJan(Kan k, Hai hai) throws IOException {
		ArrayList<Mentsu> menJan = new ArrayList<>();
		ArrayList<String> toy = new ArrayList<>();

		ArrayList<String> h = new ArrayList<>(hai.getHaiListData());

		// 入力された牌を対子判定。抽出された対子情報をtoyに格納。
		toy = setToy(h);

		// men.jantouにtoyを代入したものを、menJanに格納する。
		for (int i = 0; i < toy.size(); i++) {
			menJan.add(setMenJan(k, hai, toy.get(i)));

			// toyの内容が7個に達した場合、toy7がtrueになる。
			if (toy.size() == 7) {
				menJan.get(i).setToy7(true);
			}
		}

		return menJan;
	}

	// toyをMentsuにセット。
	private static Mentsu setMenJan(Kan k, Hai h, String toy) {
		Mentsu men = new Mentsu(k, h);
		men.setJantou(toy);
		return men;
	}

	// 面子判定。順子を先に判定する。引数:kan,menJan,sortHai。戻り値:面子パターン(1)。
	protected static ArrayList<Mentsu> setMenSK(Kan k, Naki n, Hai hai) throws IOException {
		ArrayList<String> shun = new ArrayList<>();
		ArrayList<String> kou = new ArrayList<>();

		ArrayList<String> h = new ArrayList<>();
		int count = 0;

		ArrayList<Mentsu> men = setListJan(k, hai);
		// menJan.jantouを雀頭と仮定し、hから除外する。以降は、このhを用いて面子判定を行う。
		for (int i = 0; i < men.size(); ++i) {
			// haiの内容をhに転写。初期状態に戻す。
			h.clear();
			for (String str : hai.getHaiListData())
				h.add(str);
			count = 0;

			// 対子パターンと合致する文字列を2つ消去。
			for (String t : men.get(i).getJantou().split("[,]"))
				for (int j = 0; j < h.size(); j++)
					if (t.equals(h.get(j)) && count < 2) {
						h.remove(t);
						j--;
						++count;
					}

			// 順子→刻子の順に判定する。
			shun = setShun(h);
			h = removeShKo(h, shun);
			kou = setKou(h);

			// 順子・刻子・カン材の合計数が面子構成に必要な4つを満たしていれば、menのそれぞれの変数に対応する値を転写。
			if (shun.size() + kou.size() + k.getCountD() + n.getCountN() == 4) {
				men.get(i).setShuntsu(shun);
				men.get(i).setKoutsu(kou);
				men.get(i).setMachi(Machi.setMachi(men.get(i)));
			} else if (men.get(i).isToy7())
				men.get(i).setMachi(Machi.setMachi(men.get(i)));

			// 違うなら対応するmenを消去。
			else {
				men.remove(i);
				i--;
			}
		}

		// 完成したmentsuを返す。
		return men;
	}

	// 面子判定。刻子を先に判定する。引数:kan,menJan,sortHai。戻り値:面子パターン(2)。
	protected static ArrayList<Mentsu> setMenKS(Kan k, Naki n, Hai hai) throws IOException {
		ArrayList<String> shun = new ArrayList<>();
		ArrayList<String> kou = new ArrayList<>();

		ArrayList<String> h = new ArrayList<>();
		int count = 0;
		ArrayList<Mentsu> men = setListJan(k, hai);

		// menJan.jantouを雀頭と仮定し、hから除外する。以降は、このhを用いて面子判定を行う。
		for (int i = 0; i < men.size(); ++i) {
			// haiの内容をhに転写。初期状態に戻す。
			h = new ArrayList<>(hai.getHaiListData());
			count = 0;

			// 対子パターンと合致する文字列を2つ消去。
			for (String jan : men.get(i).getJantou().split(","))
				for (int j = 0; j < h.size(); j++)
					if (jan.equals(h.get(j)) && count < 2) {
						h.remove(j);
						j--;
						++count;
					}

			// 順子→刻子の順に判定する。
			kou = setKou(h);
			h = removeShKo(h, shun);
			shun = setShun(h);

			// 順子・刻子・カン材の合計数が面子構成に必要な4つを満たしていれば、menのそれぞれの変数に対応する値を転写。
			if (shun.size() + kou.size() + k.getCountD() + n.getCountN() == 4) {
				men.get(i).setShuntsu(shun);
				men.get(i).setKoutsu(kou);
				men.get(i).setMachi(Machi.setMachi(men.get(i)));
			}

			// 違うなら対応するmenを消去。
			else {
				men.remove(i);
				i--;
			}
		}

		// 完成したmentsuを返す。
		return men;
	}

	// 総点数の設定。
	public static ArrayList<String> setHaiTotalData(Naki n, Hai h) {
		ArrayList<String> haiList = new ArrayList<>(n.getNakiData());
		for (String hd : h.getHaiListData())
			haiList.add(hd);
		return haiList;
	}

}
