package mah_point;

import java.util.ArrayList;

import mah_dora.Dora;
import mah_mentsu.Mentsu;
import mah_naki.Naki;
import mah_setting.Setting;
import mah_yaku.YakuData;
import mah_yaku.YakuList;

//PointをまとめたPointListの設定。
public class PointList {
	private ArrayList<Point> pointlist;

	private PointList() {
		setPointlist(new ArrayList<>());
	}

	public PointList(Setting s, YakuList yl) {
		this();
		setPointlist(SetPoint.setPointList(s, yl));
		showPoint(s, this);
	}

	// Pointの内容の表示。というか今まで入力したものの表示。
	public static void showPoint(Setting s, PointList pl) {
		YakuData yd1 = pl.getPointlist().get(0).getYd();
		Setting.showSetting(s.getSettingData());
		System.out.println(yd1.getMen().getKantsu().toString());
		Dora.showDora(yd1.getDora());
		Naki.showNaki(yd1.getNaki());
		System.out.println();

		// Point内の役やらハンやら符やらをひとつずつ表示。
		for (Point p : pl.getPointlist()) {
			Mentsu.showMen(p.getYd().getMen());
			System.out.println("役:" + p.getYd().getYakumei());
			System.out.println(p.getYd().getHan() + "ハン  " + p.getYd().getHu() + "符");
			for (String key : p.getPoint().keySet())
				System.out.println(key + ":" + p.getPoint().get(key) + "点");
		}
	}

	public ArrayList<Point> getPointlist() {
		return pointlist;
	}

	protected void setPointlist(ArrayList<Point> pointlist) {
		this.pointlist = new ArrayList<>(pointlist);
	}
}
