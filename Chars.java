package mah_hai;

import java.util.ArrayList;

//字牌の設定。
public class Chars extends HaiData {
	private Fun fun;
	private Sangen san;

	private Chars() {
		super("東,南,西,北,白,發,中");
	}

	public Chars(ArrayList<String> hai) {
		this();
		this.setHaiListData(sortCh(hai));
		setFun(new Fun(hai));
		setSan(new Sangen(hai));
	}

	public Fun getFun() {
		return fun;
	}

	public Sangen getSan() {
		return san;
	}

	protected void setSan(Sangen san) {
		this.san = (Sangen) san.clone();
	}

	protected void setFun(Fun fun) {
		this.fun = (Fun) fun.clone();
	}

	// 字牌をsortして返す。引数：入力された文字列。戻り値：東→南→西→北→白→發→中の順にソートされた牌。
	public static ArrayList<String> sortCh(ArrayList<String> hai) {
		Fun fun = new Fun(hai);
		Sangen san = new Sangen(hai);
		ArrayList<String> ch = new ArrayList<>();

		// 翻と三元で分けてソートしたものをArrayListに入れて返す。
		for (String f : fun.getHaiListData()) {
			if (fun.getHaiListData().size() == 0)
				break;
			ch.add(f);
		}
		for (String s : san.getHaiListData()) {
			if (san.getHaiListData().size() == 0)
				break;
			ch.add(s);
		}
		return ch;
	}
}
