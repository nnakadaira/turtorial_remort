package mah_item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Item implements Cloneable {
	protected static BufferedReader br;
	protected String str;
	private boolean yn;

	// 汎用性のあるなんかいろいろ使いそうなもの。
	public Item() {
		br = new BufferedReader(new InputStreamReader(System.in));
		str = null;
		setYn(false);
	}

	@Override
	public Object clone() { // throwsを無くす
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
	}

	// 牌の種類を返す。
	public static String showHaiValue() {
		String value = "[萬子=m,筒子=p,索子=s,東南西北白發中(赤牌は最初にrを付けること)]";
		return value;
	}

	// [y/n]による質問メソッド。'y'true or 'n'false
	public void callYesNo() throws IOException {

		// 質問確認
		System.out.println("?[y/n]:");
		str = br.readLine();

		// "yYnN"以外の入力がされた場合。
		while (str.matches("[ynYN]") == false) {
			System.out.println("Error.\"是=yY,非=nN\"?[y/n]");
			str = br.readLine();
		}

		// "yY"入力の場合:true。"nN"入力の場合:false。
		setYn(str.matches("[yY]"));
	}

	public boolean isYn() throws IOException {
		callYesNo();
		return yn;
	}

	public boolean getYn() {
		return yn;
	}

	public void setYn(boolean yn) {
		this.yn = yn;
	}

}

// class YNException extends Exception {
// if (str.matches("[ynYN]") == false) {
// YNException e = new YNException();
// throw e;
// }
