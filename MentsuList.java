package mah_mentsu;

import java.io.IOException;
import java.util.ArrayList;

import mah_hai.Hai;
import mah_kan.Kan;
import mah_naki.Naki;

//面子パターンをまとめたもの。
public class MentsuList {
	private ArrayList<String> haiTotalData;
	private ArrayList<Mentsu> menSh;
	private ArrayList<Mentsu> menKo;

	private MentsuList() {
		setHaiTotalData(new ArrayList<>());
		setMenSh(new ArrayList<>());
		setMenKo(new ArrayList<>());
	}

	public MentsuList(Kan k, Naki n, Hai h) throws IOException {
		this();
		setHaiTotalData(SetMen.setHaiTotalData(n, h));
		setMenSh(SetMen.setMenSK(k, n, h));
		setMenKo(SetMen.setMenKS(k, n, h));
		showMenList(this);
	}

	public static void showMenList(MentsuList menList) {
		if (menList.getMenSh().size() != 0 | menList.getMenSh().get(0).getKantsu().size() != 0) {
			System.out.println("カン材→" + menList.getMenSh().get(0).getKantsu().toString());
		}

		System.out.println("\n面子(順子→刻子)");
		for (Mentsu men : menList.menSh)
			Mentsu.showMen(men);
		System.out.println("\n\n面子(刻子→順子)");
		for (Mentsu men : menList.menKo)
			Mentsu.showMen(men);
	}

	public ArrayList<Mentsu> getMenSh() {
		return menSh;
	}

	protected void setMenSh(ArrayList<Mentsu> menSh) {
		if (menSh.size() == 0)
			this.menSh = new ArrayList<>();
		else
			for (Mentsu m : menSh) {
				Mentsu men = (Mentsu) m.clone();
				this.menSh.add(men);
			}
	}

	public ArrayList<Mentsu> getMenKo() {
		return menKo;
	}

	protected void setMenKo(ArrayList<Mentsu> menKo) {
		if (menKo.size() == 0)
			this.menKo = new ArrayList<>();
		else
			for (Mentsu m : menKo)
				this.menKo.add((Mentsu) m.clone());
	}

	public ArrayList<String> getHaiTotalData() {
		return haiTotalData;
	}

	private void setHaiTotalData(ArrayList<String> haiTotalData) {
		this.haiTotalData = new ArrayList<>(haiTotalData);
	}
}
