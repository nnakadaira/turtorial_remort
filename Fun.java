package mah_hai;

import java.util.ArrayList;

//風牌の設定。
public class Fun extends HaiData {

	public Fun() {
		super("東,南,西,北");
	}

	public Fun(ArrayList<String> hai) {
		this();
		this.setHaiListData(sortFun(hai));
	}

	// 字牌をsortして返す。引数：入力された文字列。戻り値：東→南→西→北→白→發→中の順にソートされた牌。
	public static ArrayList<String> sortFun(ArrayList<String> hai) {
		Fun funList = new Fun();
		ArrayList<String> fun = new ArrayList<>();

		// 字牌の種類分のfor文。
		// hai内容と現在調べている種類との比較。一致すればchに格納。
		for (String f : funList.getName()) {
			for (String h : hai) {
				if (h == null)
					break;
				if (h.matches(f))
					fun.add(f);
			}
		}

		return fun;
	}
}
