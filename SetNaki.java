package mah_naki;

import java.io.IOException;
import java.util.ArrayList;

import mah_dora.Dora;
import mah_item.Item;
import mah_kan.Kan;
import mah_setting.Setting;

//鳴きの設定。
public class SetNaki extends Item {

	public static void setCountN(Kan k, Naki n) {
		int countN = 0;

		for (String kl : k.getKantsu().values())
			if (kl.equals("L"))
				++countN;

		n.setCountN(countN + n.getPonData().size() + n.getChiData().size());
	}

	//
	public static void setNaki(Setting s, Kan k, Dora d, Naki n) throws IOException {
		if (!(s.getSettingData().get("リーチ").equals("無")))
			return;

		Item item = new Item();

		System.out.print("鳴いた");
		if (item.isYn()) {
			while (true) {
				n.setPonData(Pon.setPon(k, d));
				if (k.getKantsu().size() + n.getPonData().size() != 4)
					n.setChiData(Chi.setChi(k, d, n));

				setNakiData(n);
				Naki.showNaki(n);
				System.out.print("\nOK");
				if (item.isYn())
					break;
			}
		}
	}

	//
	protected static void setNakiData(Naki n) {
		ArrayList<String> nakiData = new ArrayList<>();

		StringBuilder sb = new StringBuilder(n.getChiData().toString() + n.getPonData().toString());
		while (sb.indexOf("[") != -1)
			sb.deleteCharAt(sb.indexOf("["));
		while (sb.indexOf("]") != -1)
			sb.deleteCharAt(sb.indexOf("]"));
		for (String str : sb.toString().split("[,]"))
			nakiData.add(str.trim());

		n.setNakiData(nakiData);
	}

}
