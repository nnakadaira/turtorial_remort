package mah_naki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import mah_dora.Dora;
import mah_hai.Numbers;
import mah_item.Determine;
import mah_item.Item;
import mah_item.SetHaiName;
import mah_kan.Kan;

//チー。順子を鳴く。
public class Chi {

	public static ArrayList<String> setChi(Kan k, Dora d, Naki n) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		ArrayList<String> chiList = new ArrayList<>();
		ArrayList<String> chiL = new ArrayList<>();
		Item item = new Item();
		int count = 0;
		String chi = "";

		System.out.print("チーした");
		if (item.isYn()) {
			while (true) {
				System.out.print("鳴いた回数の入力/0~");
				System.out.print((4 - k.getKantsu().size() - n.getPonData().size()) + ":");
				String str = br.readLine();
				if (str.matches("[0-4]")) {
					count = Integer.parseInt(str);
				} else {
					System.out.println("Error:無効な文字が入力されている。再入力。");
					continue;
				}
				if (count > (4 - k.getKantsu().size() - n.getPonData().size())) {
					System.out.println("Error:数が大きい。再入力。");
					continue;
				}

				while (count != 0) {
					System.out.println("チーした牌の入力(\",\"区切り、一度に付き3枚入力)。ex:\"1p,2p,3p\"");
					System.out.println(Item.showHaiValue() + ":");

					chi = br.readLine();
					for (String c : chi.split("[,]")) {
						c = SetHaiName.setNum(c);
						chiL.add(c);
						if (c.equals(""))
							chiL.clear();
					}
					if (Determine.determine(k, d, chiL)) {
						chiL.clear();
						continue;
					}
					chi = sortChi(chi);
					if (chi.equals("")) {
						System.out.println("Error:順子にならない。再入力。");
						continue;
					}
					chiList.add(chi);
					--count;
				}
				showChi(chiList);
				System.out.print("\nOK");
				if (item.isYn())
					break;
			}
		}
		return chiList;
	}

	public static void showChi(ArrayList<String> chiList) {
		System.out.print("チー:");
		for (String c : chiList)
			System.out.print("[" + c + "]");
	}

	// 順子検索。引数hai。というかsortHai。むしろsortNum。ArrayList(文字列(順子パターン、','区切り))を返す。
	public static String sortChi(String hai) {
		Numbers num = new Numbers();
		ArrayList<Integer> n = new ArrayList<>();
		ArrayList<String> r = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		String chi = "";

		// 牌の種類wpsのfor文。sに検索用の種類が代入される。数字のみを格納するnも初期化されている。
		for (int i = 0; i < num.getName().length; i++) {
			String s = num.getName()[i];
			n.clear();

			// 同種の牌を検索し、数字のみをnに格納していく。
			for (String h : hai.split("[,]")) {
				h = SetHaiName.setNum(h);
				;
				if (h.endsWith(s)) {
					if (h.equals("r5" + s)) {
						h = "5" + s;
						r.add(h);
					}
					n.add(Integer.valueOf(h.charAt(0) - 48));
				}
			}
			if (n.size() < 3)
				continue;
			Collections.sort(n);

			// n(0)をn(1),n(2)と比較している。合致した場合、chiに文字列(数字+種類)として代入(','区切り)。
			if (n.get(0) == (n.get(1) - 1) && n.get(0) == (n.get(2) - 2)) {
				;
				sb = new StringBuilder(n.get(0) + s + ',' + n.get(1) + s + ',' + n.get(2) + s);
				while (r.size() != 0 && sb.toString().contains(r.get(0))) {
					sb.insert(sb.indexOf(r.get(0)), "r");
					r.remove(0);
				}
				chi = sb.toString();
			}
		}
		return chi;
	}

}
