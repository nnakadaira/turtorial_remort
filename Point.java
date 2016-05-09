package mah_point;

import java.util.HashMap;

import mah_item.Item;
import mah_setting.Setting;
import mah_yaku.YakuData;

//ハンと符から点数を計算。付随する役のデータも保持。
public class Point extends Item {
	private HashMap<String, Integer> point;
	private int pointTotal;
	private YakuData yd;

	// 点数の設定。
	public Point(Setting s, YakuData yd) {
		setPoint(SetPoint.setPoint(s, yd));
		setPointTotal(SetPoint.setPT(s, this));
		setYd(yd);
	}

	public HashMap<String, Integer> getPoint() {
		return point;
	}

	protected void setPoint(HashMap<String, Integer> point) {
		this.point = new HashMap<>(point);
	}

	public int getPointTotal() {
		return pointTotal;
	}

	protected void setPointTotal(int pointTotal) {
		this.pointTotal = pointTotal;
	}

	public YakuData getYd() {
		return yd;
	}

	protected void setYd(YakuData yd) {
		this.yd = (YakuData) yd.clone();
	}

}
