package mah_hai;

import java.util.ArrayList;

import mah_item.Item;

//Hai情報の雛形。
public class HaiData extends Item implements HaiInterface {
	private ArrayList<String> haiListData;
	private String[] name;

	private HaiData() {
		setHaiListData(new ArrayList<String>());
		setName(null);
	}

	protected HaiData(String nameList) {
		this();
		this.setName(nameList);
	}

	public ArrayList<String> getHaiListData() {
		return haiListData;
	}

	protected void setHaiListData(ArrayList<String> haiListData) {
		this.haiListData = new ArrayList<>(haiListData);
	}

	public String[] getName() {
		return name;
	}

	protected void setName(String name) {
		if (name != null)
			this.name = name.trim().split("[,]");
		else
			this.name = new String[sheets];
	}

}
