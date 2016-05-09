package mah_hai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import mah_dora.Dora;
import mah_item.Determine;
import mah_item.Item;
import mah_item.SetHaiName;
import mah_kan.Kan;
import mah_naki.Naki;

public class SortHai implements HaiInterface {
	// 和了牌の入力処理。
	protected static String setAgari(Kan k, Dora d, Naki n) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Item item = new Item();
		String agari = "";
		// 和了牌の入力。
		while (item.getYn() == false) {
			System.out.println("和了牌の入力/1枚\n" + Item.showHaiValue() + ":");
			agari = br.readLine();
			agari = SetHaiName.setHai(agari);
			if (agari.equals("")) {
				System.out.println("無効な牌が入力されている。再入力。");
				continue;
			}
			if (Determine.determine(k, d, n, agari))
				continue;

			// 確認。
			System.out.print("OK");
			item.isYn();
		}
		return agari;
	}

	/*
	 * 引数:カン材(HashMap),ドラ。戻り値:String配列。
	 * haiの上限枚数(sheetsTotal)を和了牌とカンの分減算し、while(setHai→カン材処理→1種4枚上限の処理→)→while()
	 */
	public static ArrayList<String> sortHai(Kan k, Dora d, Naki n, String agari) throws IOException {
		ArrayList<String> numList = new ArrayList<>();
		ArrayList<String> chList = new ArrayList<>();
		ArrayList<String> num = new ArrayList<>();
		ArrayList<String> ch = new ArrayList<>();
		ArrayList<String> haiList = new ArrayList<>();

		int sheetsData = sheetsTotal - (k.getCountD() + n.getCountN()) * 3 - 1;
		ArrayList<String> hai = new ArrayList<>();
		ArrayList<String> h = new ArrayList<>();
		Item item = new Item();

		// 入力する牌の上限数を設定。-=カン数*3。

		while (true) {
			hai.clear();
			// 牌入力処理。
			hai = setHaiList(k, n, agari);

			if (Determine.determine(k, d, n, hai)) {
				continue;
			}
			break;
		}
		hai.remove(agari);
		numList = Numbers.sortNum(hai);
		chList = Chars.sortCh(hai);

		// [数/字]牌の合計数が入力上限数より下回っていた場合の処理。
		while (hai.size() != sheetsData) {
			h = new ArrayList<>(hai);

			// 追加入力処理。
			h = plusHai(k, n, agari, hai);
			num = Numbers.sortNum(h);
			ch = Chars.sortCh(h);

			// 確認。falseの場合再入力。前回入力されていた牌の一覧も表示する。
			Hai.showHai(num, ch);
			System.out.print("これでOK");

			if (item.isYn() == false) {
				continue;
			}

			if (Determine.determine(k, d, n, h)) {
				continue;
			}
			// numとchに転写。
			h.remove(agari);
			hai = new ArrayList<>(h);
		}

		hai.add(agari);
		numList = Numbers.sortNum(hai);
		chList = Chars.sortCh(hai);

		// sortHaiの初期化。ソートしたsortNum(数)とsortCh(字)の情報をsortHaiに転写。
		StringBuilder sb = new StringBuilder(numList.toString() + "," + chList.toString());
		while (sb.indexOf("[") != -1)
			sb.deleteCharAt(sb.indexOf("["));
		while (sb.indexOf("]") != -1)
			sb.deleteCharAt(sb.indexOf("]"));
		for (String str : sb.toString().split("[,]"))
			haiList.add(str.trim());

		return haiList;
	}

	// haiの内容を入力する処理。
	protected static ArrayList<String> setHaiList(Kan k, Naki n, String agari) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int sheetsData = sheetsTotal - (k.getCountD() + n.getCountN()) * 3 - 1;
		Item item = new Item();
		ArrayList<String> hai = new ArrayList<>();

		ArrayList<String> num = new ArrayList<>();
		ArrayList<String> ch = new ArrayList<>();
		ArrayList<String> haiList = new ArrayList<>();

		while (true) {
			System.out.println("カン:" + k.getKantsu().toString() + "\n和了牌:[" + agari + "]");
			System.out.println("他の牌の入力" + sheetsData + "枚\n" + Item.showHaiValue() + ":");
			for (int i = 0; i < sheetsData; i++)
				hai.add(SetHaiName.setHai(br.readLine()));

			// numとchにhaiを整形して格納。
			num = Numbers.sortNum(hai);
			ch = Chars.sortCh(hai);
			Hai.showHai(num, ch);
			// 無効な文字列を消去したうえで、入力牌の確認。
			System.out.print("無効牌は消去された。入力牌はこれでOK");

			// yn=falseの場合の処理。再入力。
			if (item.isYn()) {
				hai.add(agari);
				num = Numbers.sortNum(hai);
				ch = Chars.sortCh(hai);
			} else {
				System.out.print("再入力:");
				hai.clear();
				continue;
			}
			break;
		}

		// sortHaiの初期化。ソートしたsortNum(数)とsortCh(字)の情報をsortHaiに転写。
		StringBuilder sb = new StringBuilder(num.toString() + "," + ch.toString());
		while (sb.indexOf("[") != -1)
			sb.deleteCharAt(sb.indexOf("["));
		while (sb.indexOf("]") != -1)
			sb.deleteCharAt(sb.indexOf("]"));
		String[] h = sb.toString().split("[,]");
		for (String str : h)
			haiList.add(str.trim());

		return haiList;

	}

	// 配列haiに残った枚数分の追加入力をして返す。
	protected static ArrayList<String> plusHai(Kan kan, Naki n, String agari, ArrayList<String> hai)
			throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 入力された牌の枚数。14-(カン*3+和了牌+入力済み他牌)
		int sizeHai = sheetsTotal - (kan.getCountD() + n.getCountN()) * 3 - 1 - hai.size();
		Item item = new Item();

		ArrayList<String> ha = new ArrayList<>(hai);
		ArrayList<String> num = new ArrayList<>();
		ArrayList<String> ch = new ArrayList<>();
		ArrayList<String> haiList = new ArrayList<>();

		while (true) {
			System.out.println("Error:入力されていない牌あり/" + sizeHai + "枚");
			System.out.println("カン:" + kan.getKantsu().toString() + "\n和了牌:[" + agari + "]");
			System.out.println("数牌：" + Numbers.sortNum(hai) + "\n字牌：" + Chars.sortCh(hai));
			System.out.println("追加入力:" + sizeHai + "枚\n" + Item.showHaiValue() + ":");
			for (int i = 0; i < sizeHai; i++)
				ha.add(SetHaiName.setHai(br.readLine()));

			num = Numbers.sortNum(ha);
			ch = Chars.sortCh(ha);
			Hai.showHai(num, ch);
			// 無効な文字列を消去したうえで、入力牌の確認。
			System.out.print("無効牌は消去された。入力牌はこれでOK");

			// yn=falseの場合の処理。再入力。
			if (item.isYn()) {
				ha.add(agari);
				num = Numbers.sortNum(ha);
				ch = Chars.sortCh(ha);
			} else {
				System.out.print("再入力:");
				ha = new ArrayList<>(hai);
				continue;
			}
			break;
		}

		// sortHaiの初期化。ソートしたsortNum(数)とsortCh(字)の情報をsortHaiに転写。
		StringBuilder sb = new StringBuilder(num.toString() + "," + ch.toString());
		while (sb.indexOf("[") != -1)
			sb.deleteCharAt(sb.indexOf("["));
		while (sb.indexOf("]") != -1)
			sb.deleteCharAt(sb.indexOf("]"));
		String[] h = sb.toString().split("[,]");
		for (String str : h)
			haiList.add(str.trim());

		return haiList;
	}
}