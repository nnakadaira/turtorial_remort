package mah_yaku;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import mah_dora.Dora;
import mah_item.Item;
import mah_mentsu.Mentsu;
import mah_naki.Naki;

/*
 * 役。単一で1~6ハン、役満は13ハン。
 *
 *1ハン
 *鳴き有り-役牌、ハイテイ、リンシャン、チャンカン、タンヤオ
 *鳴き無し-リーチ、面前ツモ、平和、一発、一盃口
 *
 *2ハン
 *鳴き有り-トイトイホー、三色同項、三槓子
 *食い下がり有り-三色同順、一通、チャンタ
 *
 *鳴き無し-七対子、三暗子、ダブリー
 *
 *3ハン
 *食い下がり有り-混一、純チャン
 *鳴き無し-二盃口
 *
 *
 *4ハン
 * *鳴き有り-小三元、混老頭
 *
 *6ハン
 *食い下がり有り-清一
 *鳴き無し-人和
 *
 *13ハン
 *鳴き有り-スーカンツ、大三元、四喜和、字一色、緑一色、清老頭
 *
 *鳴き無し-スーアンコ、国士無双、天和、地和、九連宝燈
 *
 */

//役名とハン数
public class YakuData extends Item {
	private LinkedHashMap<String, Integer> yaku;
	private ArrayList<String> yakumei;
	private int han, hu;
	private Dora dora;
	private Naki naki;
	private Mentsu men;

	protected YakuData(Dora d, Naki n, Mentsu m) {
		setYakumei(new ArrayList<>());
		setHan(0);
		setHu(20);
		setYaku(setY());

		setDora(d);
		setNaki(n);
		setMen(m);
	}

	public ArrayList<String> getYakumei() {
		return yakumei;
	}

	protected void setYakumei(ArrayList<String> yakumei) {
		this.yakumei = new ArrayList<>(yakumei);
	}

	public int getHan() {
		return han;
	}

	protected void setHan(int han) {
		this.han = han;
	}

	public int getHu() {
		return hu;
	}

	protected void setHu(int hu) {
		this.hu = hu;
	}

	public LinkedHashMap<String, Integer> getYaku() {
		return yaku;
	}

	protected void setYaku(LinkedHashMap<String, Integer> yaku) {
		this.yaku = new LinkedHashMap<>(yaku);
	}

	public Dora getDora() {
		return dora;
	}

	private void setDora(Dora dora) {
		this.dora = (Dora) dora.clone();
	}

	public Mentsu getMen() {
		return men;
	}

	protected void setMen(Mentsu men) {
		this.men = (Mentsu) men.clone();
	}

	public Naki getNaki() {
		return naki;
	}

	private void setNaki(Naki naki) {
		this.naki = (Naki) naki.clone();
	}

	protected LinkedHashMap<String, Integer> setY() {
		LinkedHashMap<String, Integer> y = new LinkedHashMap<>();
		// 三元牌または自風・場風牌を刻子で使用したときの役。
		y.put("東", 1);
		y.put("ダブ東", 2);
		y.put("南", 1);
		y.put("ダブ南", 2);
		y.put("西", 1);
		y.put("ダブ西", 2);
		y.put("北", 1);
		y.put("ダブ北", 2);
		y.put("白", 1);
		y.put("發", 1);
		y.put("中", 1);
		// リーチしたときの役。
		y.put("リーチ", 1);
		// リーチした巡目に和了したときの役。
		y.put("一発", 1);
		// 鳴き無しの状態でツモをしたときの役。
		y.put("面前ツモ", 1);
		// 面子・待ち:関係なし,局における最後の牌でロンした場合の役。
		y.put("ハイテイ", 1);
		// 対戦相手が加カンしたとき、それが和了牌だった場合ロンできる。そのときに付く役。
		y.put("チャンカン", 1);
		// カンして王牌からひいてきた牌で和了したときの役。
		y.put("リンシャン", 1);
		// 面子:頭-役牌以外,順子-4,待ち:両面。
		y.put("平和", 1);
		// 面子:同じ種類、数字の順子を二つ組み合わせた役。
		y.put("一盃口", 1);
		// 1,9,字牌を使用せずに和了したときの役。
		y.put("タンヤオ", 1);
		// 刻子のみで構成された役。
		y.put("トイトイホー", 2);
		// 3つカンして和了したときの役。。
		y.put("三槓子", 2);
		// 上記の暗刻版。
		y.put("三暗刻", 2);
		// 三色(mps)の同じ数字を刻子で使用したときに付く役。
		y.put("三色同刻", 2);
		// 対子のみで構成された役。
		y.put("七対子", 2);
		// 初手でリーチしたときに付く役。１ハンだが、リーチと合わせて実質２ハン。
		y.put("ダブリー", 2);
		// 三色(mps)の同じ数字を順子で揃えたときに付く役。
		y.put("三色同順", 2);
		y.put("三色同順(鳴)", 1);
		// 1~3,4~6,7~9を同色順子で揃えたときに付く役。
		y.put("一気通関", 2);
		y.put("一気通関(鳴)", 1);
		// それぞれの面子に19字牌1枚以上使用したときに付く役。
		y.put("チャンタ", 2);
		y.put("チャンタ(鳴)", 1);
		// 同色+字牌で手を構成したときに付く役。
		y.put("混一", 3);
		y.put("混一(鳴)", 2);
		// チャンタの字牌無しバージョン。
		y.put("純チャン", 3);
		y.put("純チャン(鳴)", 2);
		// 一盃口を二つ作ると付く役。
		y.put("二盃口", 3);
		// 三元牌のうち、2種を刻子、1種を雀頭にしたときに付く役。
		y.put("小三元", 4);
		// 19字牌の刻子かカンで構成したときに付く役。
		y.put("混老頭", 4);
		// 混一の字牌無しバージョン。
		y.put("清一", 6);
		y.put("清一(鳴)", 5);
		// 条件いろいろ。メソッド作ってない。
		y.put("人和", 6);
		// 4回カンして和了すると付く役満。
		y.put("四槓子", 13);
		// 三元牌を刻子以上で持ってると付く役満。
		y.put("大三元", 13);
		// 風牌のうち3種を刻子以上、1種を雀頭または刻子以上で使用すると付く役満。
		y.put("大四喜", 13);
		y.put("小四喜", 13);
		// 字牌のみで構成したときに付く役満。
		y.put("字一色", 13);
		// 索子のうち、赤色の入ってない牌と、發のみで構成したときに付く役満。
		y.put("緑一色", 13);
		// 19のみの刻子以上で構成したときに付く役満。
		y.put("清老頭", 13);
		// 4つの暗刻を使用して和了したときに付く役満。
		y.put("四暗刻", 13);
		y.put("四暗刻(単)", 26);
		// 19字牌を一枚ずつ揃え、雀頭のみを2枚にして和了すると付く役満。
		y.put("国士無双", 13);
		y.put("国士無双(13面待ち)", 26);
		// 最初のツモで和了したときにつく役満。(親のみ)
		y.put("天和", 13);
		// 1順目以内に和了したときにつく役満。(子のみ)
		y.put("地和", 13);
		// 1112345678999+a、同色で揃え、且つこの形で和了すると付く役満。aは任意の同色牌。
		y.put("九連宝燈", 13);
		y.put("純正九連宝燈", 13);
		return y;
	}

}
