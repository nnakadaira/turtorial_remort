import java.io.IOException;

import mah_dora.Dora;
import mah_hai.Hai;
import mah_kan.Kan;
import mah_mentsu.MentsuList;
import mah_naki.Naki;
import mah_point.PointList;
import mah_setting.Setting;
import mah_yaku.YakuList;

public class Main {
	public static void main(String[] args) throws IOException {
		Setting s = new Setting();
		Kan k = new Kan(s);
		Dora d = new Dora(s, k);
		Naki n = new Naki(s, k, d);
		Hai h = new Hai(k, d, n);
		MentsuList ml = new MentsuList(k, n, h);
		YakuList yl = new YakuList(s, k, d, n, ml);
		PointList pl = new PointList(s, yl);
		// StringをSetting,Kan,Doraの引数追加すると、サンプルが入力される。
		// String str = "sample";
		// Setting s = new Setting(str);
		// Kan k = new Kan(str);
		// Dora d = new Dora(s, k, str);
		// Naki n = new Naki(s, k, d);
		// Hai h = new Hai(k, d, n);
		// MentsuList ml = new MentsuList(k, n, h);
		// YakuList yl = new YakuList(s, k, d, n, ml);
		// PointList pl = new PointList(s, yl);
	}
}
