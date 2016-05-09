package mah_hai;

import java.io.IOException;
import java.util.ArrayList;

import mah_kan.Kan;
import mah_naki.Naki;
import mah_dora.Dora;

/*
 * numbers--数牌情報(和了牌含まず)
 * 			数牌の種類情報。
 * chars--字牌情報(和了牌含まず)
 *		     字牌の種類情報。Fun=翻牌、Sangen=三元牌。
 *
 * haiListData--全体の牌情報(カン含まず)
 * agariHaiData--和了対象となった牌情報
 */
public class Hai implements HaiInterface {
	private Numbers numbers;
	private Chars chars;
	private ArrayList<String> haiListData;
	private String agariHaiData;

	// 初期化。
	private Hai() {
		setAgariHaiData("");
		setHaiListData(new ArrayList<>());
		setNumbers(new Numbers(this.getHaiListData()));
		setChars(new Chars(this.getHaiListData()));
	}

	// 設定。
	public Hai(Kan k, Dora d, Naki n) throws IOException {
		this();
		this.setAgariHaiData(SortHai.setAgari(k, d, n));
		this.setHaiListData(SortHai.sortHai(k, d, n, this.getAgariHaiData()));
		this.setNumbers(new Numbers(this.getHaiListData()));
		this.setChars(new Chars(this.getHaiListData()));
	}

	// 内容の確認。
	public static void showHai(ArrayList<String> num, ArrayList<String> ch) {
		System.out.println("数牌：" + num.toString() + "\n字牌：" + ch.toString());
	}

	public String getAgariHaiData() {
		return agariHaiData;
	}

	protected void setAgariHaiData(String agariHaiData) {
		this.agariHaiData = agariHaiData;
	}

	public ArrayList<String> getHaiListData() {
		return haiListData;
	}

	protected void setHaiListData(ArrayList<String> haiListData) {
		this.haiListData = new ArrayList<>(haiListData);
	}

	public Numbers getNumbers() {
		return numbers;
	}

	protected void setNumbers(Numbers numbers) {
		this.numbers = (Numbers) numbers.clone();
	}

	public Chars getChars() {
		return chars;
	}

	protected void setChars(Chars chars) {
		this.chars = (Chars) chars.clone();
	}

}
