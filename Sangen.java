package mah_hai;

import java.util.ArrayList;

public class Sangen extends HaiData {

	public Sangen() {
		super("白,發,中");
	}

	public Sangen(ArrayList<String> hai) {
		this();
		this.setHaiListData(sortSan(hai));
	}

	// 字牌をsortして返す。引数：入力された文字列。戻り値：東→南→西→北→白→發→中の順にソートされた牌。
	public static ArrayList<String> sortSan(ArrayList<String> hai) {
		Sangen sanList = new Sangen();
		ArrayList<String> san = new ArrayList<>();

		// 字牌の種類分のfor文。
		// hai内容と現在調べている種類との比較。一致すればchに格納。
		for (String s : sanList.getName()) {
			for (String h : hai) {
				if (h == null)
					break;
				if (h.matches(s))
					san.add(s);
			}
		}

		return san;
	}
}
