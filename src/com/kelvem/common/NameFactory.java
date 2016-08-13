package com.kelvem.common;

import java.util.Date;
import java.util.Random;

public class NameFactory {

	private static Random rnd = new Random(new Date().getTime());
	private static String[] FamilyNames = new String[95];
	private static String[] GivenNames = new String[79];

	public String chineseName;
	public String pinyinName;
	
	private String[] GivenNameElement() {
		int i = rnd.nextInt(GivenNames.length);
		String[] givenName = new String[2];
		String[] buf = GivenNames[i].split("\\|");
		
		int j = rnd.nextInt(buf[1].length());
		givenName[1] = buf[0];
		givenName[0] = buf[1].substring(j, j + 1);
		
		return givenName;
	}

	private String[] getFamilyName(){
		int i = rnd.nextInt(FamilyNames.length);
		String[] familyName = FamilyNames[i].split("\\|");
		
		return familyName;
	}
	
	public void generator(){
		
		chineseName = "";
		pinyinName = "";

		// 姓
		String[] familyName = getFamilyName();
		chineseName += familyName[0];
		pinyinName += familyName[1];
		
		// 名
		String[] givenName = GivenNameElement();
		chineseName += givenName[0];
		pinyinName += givenName[1];
		
		// 名
		if (rnd.nextInt(2) == 1) {
			givenName = GivenNameElement();
			chineseName += givenName[0];
			pinyinName += givenName[1];
		}
		
	}

	/** * @param args */
	public static void main(String[] args) { 
		NameFactory	chineseName = new NameFactory();
		for (int i = 0; i < 10000; i++) {
			chineseName.generator();
			System.out.println(chineseName.chineseName + ", " + chineseName.pinyinName);
		}
	}

 	static {
		FamilyNames[0]="白|bai"; 
		FamilyNames[1]="白|bai"; 
		FamilyNames[2]="蔡|cai"; 
		FamilyNames[3]="曹|cao"; 
		FamilyNames[4]="陈|chen"; 
		FamilyNames[5]="戴|dai"; 
		FamilyNames[6]="窦|dou"; 
		FamilyNames[7]="邓|deng"; 
		FamilyNames[8]="狄|di"; 
		FamilyNames[9]="杜|du"; 
		FamilyNames[10]="段|duan"; 
		FamilyNames[11]="范|fan"; 
		FamilyNames[12]="樊|fan"; 
		FamilyNames[13]="房|fang"; 
		FamilyNames[14]="风|feng"; 
		FamilyNames[15]="符|fu"; 
		FamilyNames[16]="福|fu"; 
		FamilyNames[17]="高|gao"; 
		FamilyNames[18]="古|gu"; 
		FamilyNames[19]="关|guan"; 
		FamilyNames[20]="郭|guo"; 
		FamilyNames[21]="毛|mao"; 
		FamilyNames[22]="韩|han"; 
		FamilyNames[23]="胡|hu"; 
		FamilyNames[24]="花|hua"; 
		FamilyNames[25]="洪|hong"; 
		FamilyNames[26]="侯|hou"; 
		FamilyNames[27]="黄|huang"; 
		FamilyNames[28]="贾|jia"; 
		FamilyNames[29]="蒋|jiang"; 
		FamilyNames[30]="金|jin"; 
		FamilyNames[31]="廖|liao"; 
		FamilyNames[32]="梁|liang"; 
		FamilyNames[33]="李|li"; 
		FamilyNames[34]="林|lin"; 
		FamilyNames[35]="刘|liu"; 
		FamilyNames[36]="龙|long"; 
		FamilyNames[37]="陆|lu"; 
		FamilyNames[38]="卢|lu"; 
		FamilyNames[39]="罗|luo"; 
		FamilyNames[40]="马|ma"; 
		FamilyNames[41]="牛|niu"; 
		FamilyNames[42]="庞|pang"; 
		FamilyNames[43]="裴|pei"; 
		FamilyNames[44]="彭|peng"; 
		FamilyNames[45]="戚|qi"; 
		FamilyNames[46]="齐|qi"; 
		FamilyNames[47]="钱|qian"; 
		FamilyNames[48]="乔|qiao"; 
		FamilyNames[49]="秦|qin"; 
		FamilyNames[50]="邱|qiu"; 
		FamilyNames[51]="裘|qiu"; 
		FamilyNames[52]="仇|qiu"; 
		FamilyNames[53]="沙|sha"; 
		FamilyNames[54]="商|shang"; 
		FamilyNames[55]="尚|shang"; 
		FamilyNames[56]="邵|shao"; 
		FamilyNames[57]="沈|shen"; 
		FamilyNames[58]="师|shi"; 
		FamilyNames[59]="施|shi"; 
		FamilyNames[60]="宋|song"; 
		FamilyNames[61]="孙|sun"; 
		FamilyNames[62]="童|tong"; 
		FamilyNames[63]="万|wan"; 
		FamilyNames[64]="王|wang"; 
		FamilyNames[65]="魏|wei"; 
		FamilyNames[66]="卫|wei"; 
		FamilyNames[67]="吴|wu"; 
		FamilyNames[68]="武|wu"; 
		FamilyNames[69]="萧|xiao"; 
		FamilyNames[70]="肖|xiao"; 
		FamilyNames[71]="项|xiang"; 
		FamilyNames[72]="许|xu"; 
		FamilyNames[73]="徐|xu"; 
		FamilyNames[74]="薛|xue"; 
		FamilyNames[75]="杨|yang"; 
		FamilyNames[76]="羊|yang"; 
		FamilyNames[77]="阳|yang"; 
		FamilyNames[78]="易|yi"; 
		FamilyNames[79]="尹|yin"; 
		FamilyNames[80]="俞|yu"; 
		FamilyNames[81]="赵|zhao"; 
		FamilyNames[82]="钟|zhong"; 
		FamilyNames[83]="周|zhou"; 
		FamilyNames[84]="郑|zheng"; 
		FamilyNames[85]="朱|zhu"; 
		FamilyNames[86]="东方|dongfang"; 
		FamilyNames[87]="独孤|dugu"; 
		FamilyNames[88]="慕容|murong"; 
		FamilyNames[89]="欧阳|ouyang"; 
		FamilyNames[90]="司马|sima"; 
		FamilyNames[91]="西门|ximen"; 
		FamilyNames[92]="尉迟|yuchi"; 
		FamilyNames[93]="长孙|zhangsun"; 
		FamilyNames[94]="诸葛|zhuge"; 
		GivenNames[0]="ai|皑艾哀"; 
		GivenNames[1]="an|安黯谙"; 
		GivenNames[2]="ao|奥傲敖骜翱"; 
		GivenNames[3]="ang|昂盎"; 
		GivenNames[4]="ba|罢霸"; 
		GivenNames[5]="bai|白佰"; 
		GivenNames[6]="ban|斑般"; 
		GivenNames[7]="bang|邦"; 
		GivenNames[8]="bei|北倍贝备"; 
		GivenNames[9]="biao|表标彪飚飙"; 
		GivenNames[10]="bian|边卞弁忭"; 
		GivenNames[11]="bu|步不"; 
		GivenNames[12]="cao|曹草操漕"; 
		GivenNames[13]="cang|苍仓"; 
		GivenNames[14]="chang|常长昌敞玚"; 
		GivenNames[15]="chi|迟持池赤尺驰炽"; 
		GivenNames[16]="ci|此次词茨辞慈"; 
		GivenNames[17]="du|独都"; 
		GivenNames[18]="dong|东侗"; 
		GivenNames[19]="dou|都"; 
		GivenNames[20]="fa|发乏珐"; 
		GivenNames[21]="fan|范凡反泛帆蕃"; 
		GivenNames[22]="fang|方访邡昉"; 
		GivenNames[23]="feng|风凤封丰奉枫峰锋"; 
		GivenNames[24]="fu|夫符弗芙"; 
		GivenNames[25]="gao|高皋郜镐"; 
		GivenNames[26]="hong|洪红宏鸿虹泓弘"; 
		GivenNames[27]="hu|虎忽湖护乎祜浒怙"; 
		GivenNames[28]="hua|化花华骅桦"; 
		GivenNames[29]="hao|号浩皓蒿浩昊灏淏"; 
		GivenNames[30]="ji|积极济技击疾及基集记纪季继吉计冀祭际籍绩忌寂霁稷玑芨蓟戢佶 奇诘笈畿犄"; 
		GivenNames[31]="jian|渐剑见建间柬坚俭"; 
		GivenNames[32]="kan|刊戡"; 
		GivenNames[33]="ke|可克科刻珂恪溘牁"; 
		GivenNames[34]="lang|朗浪廊琅阆莨"; 
		GivenNames[35]="li|历离里理利立力丽礼黎栗荔沥栎璃"; 
		GivenNames[36]="lin|临霖林琳"; 
		GivenNames[37]="ma|马"; 
		GivenNames[38]="mao|贸冒貌冒懋矛卯瑁"; 
		GivenNames[39]="miao|淼渺邈"; 
		GivenNames[40]="nan|楠南"; 
		GivenNames[41]="pian|片翩"; 
		GivenNames[42]="qian|潜谦倩茜乾虔千"; 
		GivenNames[43]="qiang|强羌锖玱"; 
		GivenNames[44]="qin|亲琴钦沁芩矜"; 
		GivenNames[45]="qing|清庆卿晴"; 
		GivenNames[46]="ran|冉然染燃"; 
		GivenNames[47]="ren|仁刃壬仞"; 
		GivenNames[48]="sha|沙煞"; 
		GivenNames[49]="shang|上裳商"; 
		GivenNames[50]="shen|深审神申慎参莘"; 
		GivenNames[51]="shi|师史石时十世士诗始示适炻"; 
		GivenNames[52]="shui|水"; 
		GivenNames[53]="si|思斯丝司祀嗣巳"; 
		GivenNames[54]="song|松颂诵"; 
		GivenNames[55]="tang|堂唐棠瑭"; 
		GivenNames[56]="tong|统通同童彤仝"; 
		GivenNames[57]="tian|天田忝"; 
		GivenNames[58]="wan|万宛晚"; 
		GivenNames[59]="wei|卫微伟维威韦纬炜惟玮为"; 
		GivenNames[60]="wu|吴物务武午五巫邬兀毋戊"; 
		GivenNames[61]="xi|西席锡洗夕兮熹惜"; 
		GivenNames[62]="xiao|潇萧笑晓肖霄骁校"; 
		GivenNames[63]="xiong|熊雄"; 
		GivenNames[64]="yang|羊洋阳漾央秧炀飏鸯"; 
		GivenNames[65]="yi|易意依亦伊夷倚毅义宜仪艺译翼逸忆怡熠沂颐奕弈懿翊轶屹猗翌"; 
		GivenNames[66]="yin|隐因引银音寅吟胤訚烟荫"; 
		GivenNames[67]="ying|映英影颖瑛应莹郢鹰"; 
		GivenNames[68]="you|幽悠右忧猷酉"; 
		GivenNames[69]="yu|渔郁寓于余玉雨语预羽舆育宇禹域誉瑜屿御渝毓虞禺豫裕钰煜聿"; 
		GivenNames[70]="zhi|制至值知质致智志直治执止置芝旨峙芷挚郅炙雉帜"; 
		GivenNames[71]="zhong|中忠钟衷"; 
		GivenNames[72]="zhou|周州舟胄繇昼"; 
		GivenNames[73]="zhu|竹主驻足朱祝诸珠著竺"; 
		GivenNames[74]="zhuo|卓灼灼拙琢濯斫擢焯酌"; 
		GivenNames[75]="zi|子资兹紫姿孜梓秭"; 
		GivenNames[76]="zong|宗枞"; 
		GivenNames[77]="zu|足族祖卒"; 
		GivenNames[78]="zuo|作左佐笮凿"; 
	}
} 
