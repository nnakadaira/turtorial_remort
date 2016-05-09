package mah_hai;

/*麻雀における牌の定義：1種4枚
 * 							数字牌：9種		-萬子	:n+"萬"
 * 											-索子	:n+"索"
 * 											-筒子	:n+"筒"
 * 							  字牌：3種と4種	-三元牌	:"白","撥","中"
 * 											-翻牌	:"東","南","西","北"
 * 
 * Haiで設定することは、入力された牌をソートして表示することまで。
 * 入力された牌は、Num(ArrayList)とCh(ArrayList)と入力牌([]|ArrayList)に分けられて構成される。
 * Kan(LinkedHashMap<String,String>),Dora(ArrayList<String>)辺りから先に使用牌情報をもらっておかなければならない。
 * 段階として、
 * 牌を入力(String[]hai)→ArrayList<>(setNum(hai)|setCh(hai))→それぞれソート→二つを合わせた内容を保持(HaiData)
 */

public interface HaiInterface {
	final int sheets = 4;
	final int sheetsTotal = 14;
}
