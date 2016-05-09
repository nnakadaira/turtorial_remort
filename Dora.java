package mah_dora;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mah_item.Item;
import mah_kan.Kan;
import mah_setting.Setting;

/*
 * ドラ:場に表示された牌(ドラ表示牌)より順番が1つ後になる牌がドラとなる。
 * ドラを和了時の手牌に使用した場合、枚数に応じて翻数に加算される。
 * 赤牌:これもドラ。5w,5p,5p,5sの計4枚。
 *
 * disp_dora_list--表示ドラの牌情報
 * dora_list--ドラとして有効な牌の情報
 * red_dora_list--消すかも
 */

public class Dora extends Item {
	private ArrayList<String> disp_dora_list;
	private ArrayList<String> dora_list;
	private ArrayList<String> red_dora_list;

	// Doraコンストラクタ。初期化。
	private Dora() {
		setDispDora(new ArrayList<>());
		setDoraList(new ArrayList<>());
		setRedDora(new ArrayList<>());
	}

	// Doraコンストラクタのお試し入力用。Stringを引数にすると発動。
	public Dora(Setting set, Kan kan, String sample) throws IOException {
		this();
		ArrayList<String> dispD = new ArrayList<>();
		dispD.add("3m");
		dispD.add("4m");
		dispD.add("r5m");

		this.setDispDora(dispD);
		SetDora.setDora(this);
		showDora(this);
	}

	// Doraの設定。引数SettingとKan。
	public Dora(Setting set, Kan kan) throws IOException {
		this();
		SetDora.setDispD(set, kan, this);
		SetDora.setDora(this);
		showDora(this);
	}

	// セットしたドラの表示。
	public static void showDora(Dora d) {
		System.out.println("(表示ドラ):" + d.getDispDora().toString());
		System.out.println("(有効ドラ):" + d.getDoraList().toString());
	}

	public ArrayList<String> getDispDora() {
		return disp_dora_list;
	}

	protected void setDispDora(List<String> dispDList) {
		this.disp_dora_list = new ArrayList<>(dispDList);
	}

	public ArrayList<String> getDoraList() {
		return dora_list;
	}

	protected void setDoraList(List<String> doraList) {
		this.dora_list = new ArrayList<>(doraList);
	}

	public ArrayList<String> getRedDora() {
		return red_dora_list;
	}

	protected void setRedDora(ArrayList<String> red_dora_list) {
		this.red_dora_list = new ArrayList<>(red_dora_list);
	}

}