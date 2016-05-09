package mah_dora;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
 * 赤牌。rが最初に付く。数牌5[wps]のみ。
 * wは1枚、pは2枚、sは１枚ある。
 */

public class Red {
	private ArrayList<String> redArray = new ArrayList<>();
	private ArrayList<String> redList = new ArrayList<>();
	private ArrayList<String> redData = new ArrayList<>();

	protected void setRedArray(ArrayList<String> redArray) {
		this.redArray.clear();
		for (String red : redArray)
			this.redArray.add(red);
	}

	public ArrayList<String> getRedArray() {
		return this.redArray;
	}

	protected void setRedList(ArrayList<String> redList) {
		this.redList.clear();
		for (String red : redList)
			this.redList.add(red);
	}

	public ArrayList<String> getRedList() {
		return this.redList;
	}

	protected void setRedDispDora(ArrayList<String> redDispD) {
		this.redData.clear();
		for (String red : redDispD)
			this.redData.add(red);
	}

	public ArrayList<String> getRedDispDora() {
		return this.redData;
	}

	// 赤牌の枚数確認。それぞれの種類ごとにカウントして、規定枚数を超えたらtrueを返す。
	public static boolean determineR(String[] hai, Dora d) {
		int countW = 0, countP = 0, countS = 0;

		// r5[wps]を検知した場合、対応するカウントを+する。
		for (String r : hai) {
			StringTokenizer st1 = new StringTokenizer(r, "[,]");
			while (st1.hasMoreTokens())
				switch (st1.nextToken()) {
				case "r5w":
					++countW;
					break;
				case "r5p":
					++countP;
					break;
				case "r5s":
					++countS;
					break;
				default:
					break;
				}
		}
		StringTokenizer st2 = new StringTokenizer(d.getDispDora().toString(), "[,]");
		while (st2.hasMoreTokens())
			switch (st2.nextToken()) {
			case "r5w":
				++countW;
				break;
			case "r5p":
				++countP;
				break;
			case "r5s":
				++countS;
				break;
			default:
				break;
			}

		// いずれかが規定枚数を超えたとき、true。
		if (countW > 1 || countP > 2 || countS > 1) {
			return true;
		}
		return false;
	}

	// 赤牌の枚数確認。それぞれの種類ごとにカウントして、規定枚数を超えたらtrueを返す。
	public static boolean determineR(List<String> hai) {
		int countW = 0, countP = 0, countS = 0;

		// r5[wps]を検知した場合、対応するカウントを+する。
		for (String r : hai) {
			StringTokenizer st = new StringTokenizer(r, "[,]");
			while (st.hasMoreTokens())
				switch (st.nextToken()) {
				case "r5w":
					++countW;
					break;
				case "r5p":
					++countP;
					break;
				case "r5s":
					++countS;
					break;
				default:
					break;
				}
		}

		// いずれかが規定枚数を超えたとき、true。
		if (countW > 1 || countP > 2 || countS > 1) {
			return true;
		}
		return false;
	}

	// r部分を追加。配列用。
	public String[] setR(String[] hai) {
		// haiに何も入っていない or redArrayが何も持っていない場合、そのまま返す。
		if (hai[0] == null || redArray.size() == 0)
			return hai;

		// redArrayに保存されている文字列とhaiの持つ文字列の検索。
		for (int i = 0; i < hai.length; i++)
			if (redArray.contains("r" + hai[i])) {

				// redから削除。hai[i]の文字列に"r"を付けて置換。
				redArray.remove("r" + hai[i]);
				hai[i] = "r5" + hai[i].charAt(1);
			}
		return hai;
	}

	// r部分を追加。ArrayList用。
	public List<String> setR(List<String> hai) {
		// haiが何も持っていない or redListに何も入っていない場合、そのまま返す。
		if (hai.size() == 0 || redList.size() == 0)
			return (ArrayList<String>) hai;

		// StringBuilderにhaiの文字列を操作。
		StringBuilder sb;
		for (String str : hai) {
			sb = new StringBuilder(str);

			// redList内に文字列("r"+sb)があった場合、redLisから削除。
			if (redList.contains("r" + sb)) {
				redList.remove("r" + sb);
				hai.set(hai.indexOf("r" + sb), "r" + sb);
			}
		}
		return hai;
	}

	// r部分を削除。配列用。
	public String[] removeR(String[] hai) {
		// 何も入っていないとみなしての処理。
		if (hai[0] == null)
			return hai;

		StringBuilder sb;
		// haiの持つ文字列からrのみを削除。
		for (int i = 0; i < hai.length; i++) {
			sb = new StringBuilder(hai[i]);

			// "r"がある限り検索。
			while (sb.indexOf("r") != -1) {
				// 後で使用できるようにredに保存。
				redArray.add(sb.toString());
				hai[i] = sb.deleteCharAt(sb.indexOf("r")).toString();
			}
		}
		return hai;
	}

	// r部分を削除。ArrayList用。
	public ArrayList<String> removeR(List<String> hai) {
		// haiが何も持っていない場合の処理。
		if (hai.size() == 0)
			return (ArrayList<String>) hai;

		StringBuilder sb;
		ArrayList<String> h = new ArrayList<>();

		// haiの持つ文字列からrのみを削除。
		for (String str : hai) {
			sb = new StringBuilder(str);
			while (sb.indexOf("r") != -1) {
				// 後で使用できるようにredに保存。
				redList.add(sb.toString());
				sb.deleteCharAt(sb.indexOf("r"));
			}

			// ","付きでhに追加。
			h.add(sb.toString());
		}
		return h;
	}

}