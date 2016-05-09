package mah_hai;

import java.util.ArrayList;
import java.util.Collections;

//
public class Numbers extends HaiData {

	public Numbers() {
		super("m,p,s");
	}

	public Numbers(ArrayList<String> hai) {
		this();
		this.setHaiListData(sortNum(hai));
	}

	// 数牌をsortして返す。引数：入力された文字列。戻り値：1→9,m→p→sの順にソートされた数牌。
	public static ArrayList<String> sortNum(ArrayList<String> hai) {
		Numbers numList = new Numbers();
		ArrayList<Integer> n = new ArrayList<>();
		ArrayList<String> r = new ArrayList<>();
		ArrayList<String> num = new ArrayList<>();
		int i = 0, k = 0;

		// mpsの種類分のfor文による繰り返し処理。
		for (i = 0; i < numList.getName().length; i++) {
			n.clear();
			r.clear();

			// haiの内容が牌と一致するか、現在調べている種類と一致するかを調べている。trueの場合、nに数字部分のみを追加。
			for (String h : hai) {
				if (h == null)
					break;
				if (h.matches("[1-9]" + numList.getName()[i]))
					n.add(Integer.valueOf(h.charAt(0) - 48));
				// 赤牌入力の場合。上記のほかにrにも文字列を追加しておく。
				else if (h.equals("r5" + numList.getName()[i])) {
					n.add(5);
					r.add(h);
				}
			}
			// 数字をソートした後、[nの内容+種類]文字列としてnumに追加。
			Collections.sort(n);
			for (int j : n)
				num.add(j + numList.getName()[i]);

			// rの該当牌とnumの牌を置き換え。
			while (k < r.size()) {
				num.set(n.lastIndexOf(5), r.get(k));
				n.remove(n.lastIndexOf(5));
				++k;
			}
		}
		return num;
	}
}
