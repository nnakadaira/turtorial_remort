package mah_naki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import mah_dora.Dora;
import mah_item.Determine;
import mah_item.Item;
import mah_item.SetHaiName;
import mah_kan.Kan;

//ポン。刻子を鳴く。
public class Pon {

	public static ArrayList<String> setPon(Kan k, Dora d) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		ArrayList<String> ponList = new ArrayList<>();
		ArrayList<String> pl = new ArrayList<>();

		Item item = new Item();
		String pon = "";
		int count = 0;

		System.out.print("ポンした");
		if (item.isYn()) {
			while (true) {
				System.out.print("鳴いた回数の入力/0~" + (4 - k.getKantsu().size()) + ":");
				String str = br.readLine();
				if (str.matches("[0-4]")) {
					count = Integer.parseInt(str);
				} else {
					System.out.println("Error:無効な文字が入力されている。再入力。");
					continue;
				}
				if (count > (4 - k.getKantsu().size())) {
					System.out.println("Error:数が大きい。再入力。");
					continue;
				}

				System.out.println("ポンした牌の入力(\",\"区切り、一度に付き3枚入力)。ex:\"3p,3p,3p\"");
				System.out.println(Item.showHaiValue() + ":");
				while (count != 0) {
					pon = br.readLine();

					for (String p : pon.split("[,]"))
						pl.add(SetHaiName.setHai(p));

					if (pl.contains("")) {
						System.out.print("無効な牌が入力されている。再入力。");
						pl.clear();
						continue;
					}

					if (Determine.determine(k, d, pl)) {
						pl.clear();
						continue;
					}
					pon = sortPon(pon);
					if (pon.equals("")) {
						System.out.println("Error:刻子にならない。再入力。");
						continue;
					}
					ponList.add(pon);
					--count;
				}
				showPon(ponList);
				System.out.print("\nOK");
				if (item.isYn())
					break;
				else
					ponList.clear();
			}
		}
		return ponList;

	}

	public static void showPon(ArrayList<String> ponList) {
		System.out.print("ポン:");
		for (String p : ponList)
			System.out.print("[" + p + "]");
	}

	private static String sortPon(String hai) {
		ArrayList<String> r = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		ArrayList<String> ponList = new ArrayList<>();
		String p = "";
		String pon = "";

		for (String h : hai.split("[,]")) {
			p = SetHaiName.setHai(h);
			ponList.add(p);
		}
		if (ponList.size() == 3 && !(ponList.get(0).equals("")))
			if (ponList.get(0).equals(ponList.get(1)) && ponList.get(0).equals(ponList.get(2))) {
				sb = new StringBuilder(ponList.get(0) + ',' + ponList.get(0) + ',' + ponList.get(0));
				while (r.size() != 0 && sb.toString().contains(r.get(0))) {
					sb.insert(sb.indexOf(r.get(0)), "r");
					r.remove(0);
				}
				pon = sb.toString();
			}
		return pon;
	}

}
