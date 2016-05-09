package mah_dora;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import mah_hai.Chars;
import mah_hai.Fun;
import mah_hai.Hai;
import mah_hai.Numbers;
import mah_hai.Sangen;
import mah_item.Determine;
import mah_item.Item;
import mah_item.SetHaiName;
import mah_kan.Kan;
import mah_setting.Setting;

//表示ドラと有効ドラの設定。
public class SetDora extends Determine {

	// ドラ表示牌の設定。
	protected static void setDispD(Setting set, Kan kan, Dora dora) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Item item = new Item();
		ArrayList<String> dispDList = new ArrayList<>();
		ArrayList<String> dispDNum = new ArrayList<>();
		ArrayList<String> dispDCh = new ArrayList<>();
		String ch = "";

		// 表示ドラの入力。
		while (item.getYn() == false) {
			dispDNum.clear();
			dispDCh.clear();
			dispDList.clear();
			System.out.println("表示ドラを入力(\",\"区切りで複数)\n" + Item.showHaiValue() + ":");
			StringTokenizer st = new StringTokenizer(br.readLine().toString(), "[,]");
			while (st.hasMoreTokens()) {

				// 一致した文字列のみdispDListに追加。
				ch = SetHaiName.setHai(st.nextToken());
				if (ch != "") {
					dispDList.add(ch);
				}
			}

			// ドラの矛盾判定。
			if (determineDora(set, kan, dispDList))
				continue;

			// ソートした牌を表示。
			System.out.println("確認:");
			dispDNum = Numbers.sortNum(dispDList);
			dispDCh = Chars.sortCh(dispDList);
			Hai.showHai(dispDNum, dispDCh);
			// 再度の確認。yなら終了、nならもう一度最初から。
			System.out.print("\nOK");
			item.isYn();
		}

		// dispDNum + dispDCh = dispDList -> dispDoraにセット。
		dispDList.clear();
		for (String n : dispDNum) {
			if (n == null)
				break;
			dispDList.add(n);
		}
		for (String c : dispDCh) {
			if (c == null)
				break;
			dispDList.add(c);
		}
		dora.setDispDora(dispDList);
		dora.setDoraList(dora.getDispDora());
	}

	// 有効ドラの設定。
	public static void setDora(Dora dora) throws IOException {
		ArrayList<String> num = new ArrayList<>();
		ArrayList<String> ch = new ArrayList<>();
		ArrayList<String> dispDList = new ArrayList<>();
		Sangen san = new Sangen();
		Fun fun = new Fun();

		// 表示ドラから1牌ずつ取り出す。その牌の次の順番の牌をnumに格納する。
		for (String d : dora.getDispDora()) {

			// 数牌の場合。ex:1→2,8→9になる。
			if (d.matches("[1-8].")) {
				num.add(d.charAt(0) - 47 + d.substring(1));
			}
			// 9→1になる。
			if (d.matches("9."))
				num.add("1" + d.substring(1));
			// r5→6になる。
			if (d.matches("r5.")) {
				num.add(6 + d.substring(2));
			}

			// 字牌だった場合、東→南→西→北→または白→發→中→の順に有効ドラを変える。
			String str = SetHaiName.setCh(d);
			for (int i = 0; i < fun.getName().length; i++)
				if (str.equals(fun.getName()[i])) {
					if (i == fun.getName().length - 1) {
						ch.add(fun.getName()[i]);
					} else {
						ch.add(fun.getName()[i + 1]);
					}
				}
			for (int i = 0; i < san.getName().length; i++)
				if (str.equals(san.getName()[i])) {
					if (i == san.getName().length - 1) {
						ch.add(san.getName()[i]);
					} else {
						ch.add(san.getName()[i + 1]);
					}
				}
		}

		// 表示ドラのサイズと有効ドラのサイズの比較。同じならbreak
		for (String n : num) {
			if (num == null || dispDList.size() == num.size())
				break;
			dispDList.add(n);
		}
		for (String c : ch) {
			if (ch == null || dispDList.size() == (num.size() + ch.size()))
				break;
			dispDList.add(c);
		}
		dora.setDoraList(dispDList);

	}
}
