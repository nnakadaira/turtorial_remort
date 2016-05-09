package mah_mentsu;

import java.util.ArrayList;
import java.util.List;

import mah_hai.Numbers;

//セットされた牌の面子を探る。

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
*面子に関すること。
*
*カン材は固定。
*対子:ArrayLis<Stirng>
*順子:
*刻子:ArrayList<String>
*
*/

public class SortMen {

	// 対子検索。引数List<String>hai。具体的にはsortHai。ArrayList(対子パターン)を返す。
	public static ArrayList<String> setToy(List<String> hai) {
		ArrayList<String> toytsu = new ArrayList<>();
		;
		ArrayList<String> haiList = new ArrayList<>(hai);
		ArrayList<String> r = new ArrayList<>();
		StringBuilder sb = new StringBuilder();

		// 赤牌を普通の牌に置換。置換した牌はrにも別保持。
		for (String name : new Numbers().getName())
			while (haiList.contains("r5" + name)) {
				haiList.set(haiList.indexOf("r5" + name), "5" + name);
				r.add("5" + name);
			}

		// haiの重複検索。haiはあらかじめsortされているため、i番目とi+1番目の文字列を比較するだけでよい。
		for (int i = 0; i < haiList.size() - 1; i++) {
			// 重複する文字列(対子)があった場合の処理。jantouに該当する文字列を格納し、該当箇所を再検索しないように++iする。
			if (haiList.get(i).equals(haiList.get(i + 1))) {
				sb = new StringBuilder(haiList.get(i) + "," + haiList.get(i + 1));
				while (r.size() != 0 && sb.toString().contains(r.get(0))) {
					sb.insert(sb.indexOf(r.get(0)), "r");
					r.remove(0);
				}
				toytsu.add(sb.toString());
				++i;
			}
		}
		return toytsu;

	}

	// 順子検索。引数hai。というかsortHai。むしろsortNum。ArrayList(文字列(順子パターン、','区切り))を返す。
	public static ArrayList<String> setShun(List<String> hai) {
		Numbers num = new Numbers();
		ArrayList<String> shuntsu = new ArrayList<>();
		ArrayList<Integer> n = new ArrayList<>();
		ArrayList<String> r = new ArrayList<>();
		StringBuilder sb = new StringBuilder();

		// 牌の種類wpsのfor文。sに検索用の種類が代入される。数字のみを格納するnも初期化されている。
		for (int i = 0; i < num.getName().length; i++) {
			String s = num.getName()[i];
			n.clear();

			// 同種の牌を検索し、数字のみをnに格納していく。
			for (String h : hai) {
				if (h.endsWith(s)) {
					if (h.equals("r5" + s)) {
						h = "5" + s;
						r.add(h);
					}
					n.add(Integer.valueOf(h.charAt(0) - 48));
				}
			}

			// 3つの連続した数字(n,n+1,n+2)を検索する処理。
			for (int x = 0; x < n.size(); x++) {
				for (int y = x + 1; y < n.size(); y++) {
					for (int z = y + 1; z < n.size(); z++) {

						// x(n)をy(n+1),z(n+2)と比較している。合致した場合、shuntsuに文字列(数字+種類)として格納(','区切り)。
						if (n.get(x) == (n.get(y) - 1) && n.get(x) == (n.get(z) - 2)) {
							sb = new StringBuilder(n.get(x) + s + ',' + n.get(y) + s + ',' + n.get(z) + s);
							while (r.size() != 0 && sb.toString().contains(r.get(0))) {
								sb.insert(sb.indexOf(r.get(0)), "r");
								r.remove(0);
							}
							shuntsu.add(sb.toString());

							// 使用した牌は削除される。
							n.remove(z);
							n.remove(y);
							n.remove(x);

							// ArrayListから要素削除したため、x,y,zの初期化。zはこの後+1されるためyと同値。
							if (x == 0)
								x = 0;
							else
								x -= 1;
							y = x + 1;
							z = x + 1;
						}
					}
				}
			}
		}
		return shuntsu;
	}

	// 刻子検索。引数hai。戻り値:ArrayList(刻子パターン)。同一の文字列が3つ重なったら刻子として格納する。
	public static ArrayList<String> setKou(List<String> hai) {
		int duplicationCount = 0;
		ArrayList<String> koutsu = new ArrayList<>();
		ArrayList<String> r = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		int i = 0;

		// 赤牌を普通の牌に置換。置換した牌はrにも別保持。
		for (String name : new Numbers().getName())
			while (hai.contains("r5" + name)) {
				hai.set(hai.indexOf("r5" + name), "5" + name);
				r.add("5" + name);
			}

		// 重複検索。検索の起点になるiが3枚以上検索できない状態になるまで繰り返し。
		while (i < hai.size() - 2) {
			// 重複枚数カウント用変数。0初期化。
			duplicationCount = 0;

			// 重複があった場合、duplicationCountに+1する。
			for (String h : hai) {
				if (hai.get(i).equals(h))
					++duplicationCount;

				// sheetsCount=3→同牌が3枚検知された場合、koutsuに該当の文字列1つを格納。
				if (duplicationCount == 3) {
					sb = new StringBuilder(h + ',' + h + ',' + h);
					while (r.size() != 0 && sb.toString().contains(r.get(0))) {
						sb.insert(sb.indexOf(r.get(0)), "r");
						r.remove(0);
					}
					koutsu.add(sb.toString());

					// duplicationCountが0になる(3枚削除される)までhaiから該当する文字列を削除。
					while (hai.remove(h)) {
						// 3枚削除完了したらループを抜ける。
						--duplicationCount;
						if (duplicationCount == 0)
							break;

					}
					// 重複牌の削除終了のため、for文を抜ける。ArrayListから検索基点の文字列も削除してあるため、--iで帳尻あわせ。
					--i;
					break;
				}
			}
			// ++iで次の文字列を検索。
			++i;

		}
		return koutsu;
	}

	// 先に判断しておいた順子か刻子を入力牌から削除する。
	public static ArrayList<String> removeShKo(ArrayList<String> hai, ArrayList<String> shunKou) {
		if (shunKou.size() != 0)
			// remove(Object o)でshKo内と一致する要素を削除。
			for (String shko : shunKou)
				for (String sk : shko.split(","))
					hai.remove(sk);

		// 残った牌を返す。
		return hai;
	}

}
