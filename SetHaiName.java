package mah_item;

//牌の名前判定。
public class SetHaiName {

	// 風牌の判定。
	public static String setFun(String hai) {
		if (hai.matches("[東南西北NEWS]")) {
			switch (hai) {
			case "E":
				hai = "東";
				break;
			case "S":
				hai = "南";
				break;
			case "W":
				hai = "西";
				break;
			case "N":
				hai = "北";
				break;
			default:
				break;
			}
			return hai;
		} else {
			return hai = "";
		}
	}

	// 三元牌の判定。
	public static String setSan(String hai) {
		if (hai.matches("([HC][KTH])|[白發中]")) {
			switch (hai) {
			case "HK":
				hai = "白";
				break;
			case "HT":
				hai = "發";
				break;
			case "CH":
				hai = "中";
				break;
			default:
				break;
			}
			return hai;
		} else {
			return hai = "";
		}
	}

	// 上記の処理二つを用いた字牌の判定。
	public static String setCh(String hai) {
		if (hai.matches("([HC][KTH])|[白發中]")) {
			switch (hai) {
			case "HK":
				hai = "白";
				break;
			case "HT":
				hai = "發";
				break;
			case "CH":
				hai = "中";
				break;
			default:
				break;
			}
			return hai;
		} else {
			hai = setFun(hai);
			return hai;
		}
	}

	// 上記に加えて数牌の判定。
	public static String setHai(String hai) {
		if (hai.matches("([1-9]|r5)[mps]")) {
			return hai;
		} else {
			return hai = setCh(hai);
		}
	}

	// 数牌のみの判定。
	public static String setNum(String hai) {
		if (hai.matches("([1-9]|r5)[mps]"))
			return hai;
		else
			return "";
	}

	// 数牌と風牌の判定。
	public static String setNumFun(String hai) {
		if (hai.matches("([1-9]|r5)[mps]"))
			return hai;
		else {
			hai = setFun(hai);
			return hai;
		}
	}

}
