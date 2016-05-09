package mah_point;

import java.util.ArrayList;
import java.util.HashMap;

import mah_setting.Setting;
import mah_yaku.YakuData;
import mah_yaku.YakuList;

public class SetPoint {

	// ロン時の点数計算。
	//
	// 基本点:符*2^(ハン+2)->
	// 親の合計点数: 基本点*2 + 基本点*2 + 基本点*2。子の1,5倍。
	// 子の合計点数: 基本点*2 + 基本点 + 基本点。
	// ->点数:合計点数の10の位切り上げ。
	private static HashMap<String, Integer> setPointRon(Setting s, YakuData yd) {
		int pointStd = 0;
		// 4ハン70符未満または5ハン40符未満の場合。
		if (yd.getHan() < 4 && yd.getHu() < 70 | yd.getHan() < 5 && yd.getHu() < 40)
			pointStd = (yd.getHu() * ((int) Math.pow(2, yd.getHan() + 2))) * 3;
		// 13ハン以上の場合。
		else if (yd.getHan() > 12)
			pointStd = 8000 * (yd.getHan() / 12 - yd.getHan() / 12 % 1);
		// 残りの状態の場合。
		else
			pointStd = 1000 * (yd.getHan() / 2 - yd.getHan() / 2 % 1);

		// 総合点数は基本点*4。
		int pointTotal = pointStd * 4;
		HashMap<String, Integer> pointRT = new HashMap<>();

		// 親の場合、基本点*2を足す。
		if (s.getSettingData().get("親番").equals("親"))
			pointTotal += pointStd * 2;

		// 下2桁は切り上げ。
		if (pointTotal % 100 != 0)
			pointTotal += 100 - pointTotal % 100;

		// ロン和了のため、"総"のみの点数入力。
		pointRT.put("総点数　", pointTotal);
		return pointRT;
	}

	// ツモ時の点数計算。
	// 基本点:符*2^(ハン+2)->
	// 親の合計点数: 基本点*2 + 基本点*2 + 基本点*2。
	// 子の合計点数: 基本点*2 + 基本点 + 基本点。
	// ->点数:合計点数の10の位切り上げ。
	private static HashMap<String, Integer> setPointTsumo(Setting s, YakuData yd) {
		int pointStd = 0;
		// 4ハン70符未満または5ハン40符未満の場合。
		if (yd.getHan() < 4 && yd.getHu() < 70 | yd.getHan() < 5 && yd.getHu() < 40)
			pointStd = (yd.getHu() * ((int) Math.pow(2, yd.getHan() + 2))) * 3;
		// 13ハン以上の場合。
		else if (yd.getHan() > 12)
			pointStd = 8000 * (yd.getHan() / 12 - yd.getHan() / 12 % 1);
		// 残りの状態の場合。
		else
			pointStd = 1000 * (yd.getHan() / 2 - yd.getHan() / 2 % 1);

		// 親は基本点*2、子は基本点そのまま。
		int pointOya = pointStd * 2;
		int pointKo = pointStd;

		HashMap<String, Integer> pointRT = new HashMap<>();

		// 下2桁の切り上げ。
		if (pointOya % 100 != 0)
			pointOya += 100 - pointOya % 100;

		if (pointKo % 100 != 0)
			pointKo += 100 - pointKo % 100;

		// 親の場合、総点数はpointOya*3。
		if (s.getSettingData().get("親番").equals("親")) {
			pointRT.put("総点数　", pointOya * 3);
			// 子の払いはpointOya。
			pointRT.put("子の払い", pointOya);
		}

		// 親番が子の場合、総点数はpointOya*2。親の払いはpointOya。子の払いはpointKo。
		if (s.getSettingData().get("親番").equals("子")) {
			pointRT.put("総点数　", pointOya * 2);
			pointRT.put("親の払い", pointOya);
			pointRT.put("子の払い", pointKo);
		}

		return pointRT;
	}

	// Pointの設定。HashMapを返す。
	public static HashMap<String, Integer> setPoint(Setting s, YakuData yd) {
		if (s.getSettingData().get("和了方法").equals("ロン"))
			return setPointRon(s, yd);
		else
			return setPointTsumo(s, yd);
	}

	// 取得する総点数の設定。
	public static int setPT(Setting s, Point p) {
		int pt = 0;

		for (String key : p.getPoint().keySet())
			switch (key) {
			case "親の払い":
				pt += p.getPoint().get(key);
				if (p.getPoint().values().contains("子"))
					return p.getPoint().get(key) * 3;
				break;
			case "子の払い":
				pt += p.getPoint().get(key) * 2;
				break;
			case "総点数　":
				return p.getPoint().get(key);
			}
		return pt;

	}

	// ArrayList<Point>の設定。引数Setting,YakuList。戻り値ArrayList<Point>。
	public static ArrayList<Point> setPointList(Setting s, YakuList yl) {
		ArrayList<Point> pl = new ArrayList<>();

		for (YakuData yd : yl.getYakuList()) {
			pl.add(new Point(s, yd));
		}
		return pl;
	}
}
