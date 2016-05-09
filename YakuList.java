package mah_yaku;

import java.util.ArrayList;

import mah_dora.Dora;
import mah_item.Item;
import mah_kan.Kan;
import mah_mentsu.Mentsu;
import mah_mentsu.MentsuList;
import mah_naki.Naki;
import mah_setting.Setting;

/*
 * 役。単一で1~6ハン、役満は13ハン。
 */

public class YakuList extends Item {
	private ArrayList<YakuData> yakuList;

	private YakuList() {
		setYakuList(new ArrayList<>());
	}

	public YakuList(Setting s, Kan k, Dora d, Naki n, MentsuList ml) {
		this();
		SetYaku.setYaku(s, k, d, n, ml, this);
		SetHu.setHu(s, this);
		showYaku(s, this);
	}

	public ArrayList<YakuData> getYakuList() {
		return yakuList;
	}

	public void setYakuList(YakuData yd) {
		this.yakuList.add((YakuData) yd.clone());
	}

	public void setYakuList(ArrayList<YakuData> yakuList) {
		this.yakuList = new ArrayList<>(yakuList);
	}

	public static void showYaku(Setting s, YakuList yl) {
		YakuData yd1 = yl.getYakuList().get(0);
		Setting.showSetting(s.getSettingData());
		System.out.println(yd1.getMen().getKantsu().toString());
		Dora.showDora(yd1.getDora());
		Naki.showNaki(yl.getYakuList().get(0).getNaki());

		for (YakuData yd : yl.getYakuList()) {
			Mentsu.showMen(yd.getMen());
			System.out.println("役:" + yd.getYakumei() + "\n" + yd.getHan() + "ハン  " + yd.getHu() + "符");
		}
	}
}
