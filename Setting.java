package mah_setting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedHashMap;

import mah_item.*;

// 場風、自風→親判定、連荘回数、リーチの有無、和了の仕方の設定。
public class Setting extends SettingData {
	private Item item;

	// Settingコンストラクタ。SettingDataコンストラクタも実体化。
	public Setting() throws IOException {
		super();
		setSetting(this);
	}

	// Setteingの内容を下記のとおりに設定する。sample
	public Setting(String sample) {
		super();
		this.setSettingData("場風", "東");
		this.setSettingData("自風", "東");
		this.setSettingData("親番", "親");
		this.setSettingData("連荘回数", "3");
		this.setSettingData("リーチ", "無");
		this.setSettingData("一発", "無");
		this.setSettingData("和了方法", "ツモ");
		this.setSettingData("ホーテイ", "無");
		this.setSettingData("チャンカン", "無");
		this.setSettingData("リンシャン", "無");
		showSetting(this.getSettingData());
	}

	// 確認。Settingの内容を表示。
	public static void showSetting(LinkedHashMap<String, String> settingList) {
		for (Iterator<String> iterator = settingList.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			System.out.println(key + "--" + settingList.get(key));
		}
	}

	// SettingDataの内容の設定。
	protected void setSetting(SettingData setD) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		SettingData setMap = new SettingData();
		item = new Item();

		// 最終確認でyが押されるまで繰り返し設定。
		while (item.getYn() == false) {
			setMap.getSettingData().put("場風", setStFun(br));
			setPlFun(setMap, br);
			setMap.getSettingData().put("連荘回数", setRenCount(br));
			setReach(setMap, br);
			setAgariStyle(setMap, br);

			// 確認。setの内容を表示と最終確認。
			showSetting(setMap.getSettingData());
			System.out.print("\nOK");
			item.isYn();
		}
		setD.setSettingData(setMap.getSettingData());
	}

	// 場風の設定。
	protected String setStFun(BufferedReader br) throws IOException {
		Item item = new Item();
		String setStFun = "";

		// 質問と場風の入力。
		while (item.getYn() == false) {
			// 東西南北いずれ一文字の入力。
			System.out.println("何場か入力[東=E,南=S,西=W,北=N]:");
			setStFun = SetHaiName.setFun(br.readLine());
			if (setStFun == "") {
				System.out.println("Error:無効な牌。再入力↓");
				continue;
			}

			// 再度の確認。yなら終了、setting_stage_funにセット。nならもう一度最初から。
			System.out.print("確認:" + setStFun + "\nOK");
			item.isYn();
		}
		return setStFun;
	}

	// 自風の設定。
	protected void setPlFun(SettingData setD, BufferedReader br) throws IOException {
		Item item = new Item();
		String setPlFun = "";
		LinkedHashMap<String, String> map = new LinkedHashMap<>(setD.getSettingData());

		// 質問と場風の入力。
		while (item.getYn() == false) {
			System.out.println("自風を入力[東=E,南=S,西=W,北=N]:");

			// 東西南北いずれ一文字の入力。
			setPlFun = SetHaiName.setFun(br.readLine());
			if (setPlFun == "") {
				System.out.println("Error:無効な牌。再入力↓");
				continue;
			}
			// 再度の確認。yなら終了、setting_player_funにセット。nならもう一度最初から。
			System.out.print("確認:" + setPlFun + "\nOK");
			item.isYn();
		}
		map.put("自風", setPlFun);
		// 自風が東なら、親番で確定。
		if (setPlFun.equals("東"))
			map.put("親番", "親");
		else
			map.put("親番", "子");
		setD.setSettingData(map);
	}

	// 連チャン回数の設定。
	protected String setRenCount(BufferedReader br) throws IOException {
		Item item = new Item();
		int countR = 0;
		// 連チャン回数の入力。
		while (item.getYn() == false) {
			System.out.print("連チャン回数の入力:");
			String str = br.readLine();
			if (str.matches("[0-9]|([0-9][0-9])")) {
				countR = Integer.parseInt(str);
			} else {
				System.out.println("Error:無効な文字が入力されている。再入力。");
			}

			// 再度の確認。yなら終了、nならもう一度最初から。
			System.out.print("確認:" + countR + "\nOK");
			item.isYn();
		}
		return Integer.toString(countR);
	}

	// リーチの有無の設定。
	protected void setReach(SettingData setD, BufferedReader br) throws IOException {
		String reach = "";
		String reachturn = "";
		Item item = new Item();

		// 質問がnで返される限り続く。
		while (item.getYn() == false) {

			// リーチ方法の入力。指定の文字以外を入力すると再入力。
			System.out.print("リーチの確認:\n有=y,無=n,初手リーチ=D いずれかを入力:");
			switch (br.readLine()) {
			case "y":
			case "Y":
				reach = "有";
				break;
			case "n":
			case "N":
				reach = "無";
				break;
			case "d":
			case "D":
				reach = "ダブルリーチ";
				break;
			default:
				System.out.println("Error:無効な入力。再入力↓");
				continue;
			}
			System.out.print("確認:リーチ=" + reach + "\nOK");
			item.isYn();
		}

		// リーチの一発判定。
		if (!(reach.equals("無"))) {
			System.out.print("リーチした巡目で和了した");
			if (item.isYn())
				reachturn = "有";
			else
				reachturn = "無";
			setD.setSettingData("一発", reachturn);
		}
		// 最終的にsetDにセットして終了。
		setD.setSettingData("リーチ", reach);
	}

	// 和了の仕方の設定。チャンカン、リンシャンの設定。
	protected void setAgariStyle(SettingData setD, BufferedReader br) throws IOException {
		String aStyle = "";
		Item item = new Item();

		while (item.getYn() == false) {
			System.out.print("和了の確認:\nロン=r,ツモ=t,配牌で和了=a いずれかを入力:");
			// 指定文字以外は再入力。
			switch (br.readLine()) {
			case "r":
				aStyle = "ロン";
				break;
			case "t":
				aStyle = "ツモ";
				break;
			case "a":// 親かどうかで役が変わる。
				if (setD.getSettingData().get("親番").equals("親"))
					aStyle = "天和";
				else
					aStyle = "地和";
			default:
				System.out.println("Error:無効な入力。再入力↓");
				continue;
			}
			System.out.print("確認:和了=" + aStyle + "\nOK");
			item.isYn();
		}

		// ロンの場合、チャンカンやホーテイ、ツモの場合、リンシャンの確認をする。
		if (aStyle.equals("ロン")) {
			System.out.print("対戦相手の加カンでロンした");
			if (item.isYn())
				setD.setSettingData("チャンカン", "有");
			else
				setD.setSettingData("チャンカン", "無");
			System.out.print("ホーテイでロンした");
			if (item.isYn())
				setD.setSettingData("ホーテイ", "有");
			else
				setD.setSettingData("ホーテイ", "無");
		} else {
			System.out.print("自分のカン時、王牌からツモった牌で和了した");
			if (item.isYn())
				setD.setSettingData("リンシャン", "有");
			else
				setD.setSettingData("リンシャン", "無");

		}
		// 最終的にsetDに和了方法を入力して終了。
		setD.setSettingData("和了方法", aStyle);
	}
}
