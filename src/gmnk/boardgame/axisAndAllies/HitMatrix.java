package gmnk.boardgame.axisAndAllies;

import java.math.BigInteger;
import java.util.HashMap;

public class HitMatrix {
	//http://gwydir.demon.co.uk/jo/probability/calcdice.htm
	BigInteger zero = new BigInteger("0");
	BigInteger one  = new BigInteger("1");
	long milliStart;
	long lastMilli;
	long milliDiffOneBack;
	long milliDiffTwoBack;
	
	
	public static void main(String[] args) {
		//2d6 = 2:1, 3:2, 4:3, 5:4, 6:5, 7:6, 8:5, 9:4, 10:3, 11:2, 12:1
		//3d6 = 3:1, 4:3, 5:6, 6:10, 7:15, 8:21, 9:25, 10:27, 11:27, 12:25, 13:21, 14:15, 15:10, 17:3, 16:6, 18:1, 
				
		//2d4 = 2:1, 3:2, 4:3, 5:4, 6:3, 7:2, 8:1 
		//3d4 = 3:1, 4:3, 5:6, 6:10, 7:12, 8:12, 9:10, 10:6, 11:3, 12:1

		HitMatrix h = new HitMatrix();
		for(int sides=6;sides<7;sides++){
			for(int dice=1; dice<16;dice++){
				System.out.println("-->"+h.getDiceStat(dice, sides).getSummaryPointFrequency());
			}
		}

	}
	public HitMatrix(){
		milliStart = System.currentTimeMillis();
		lastMilli=milliStart;
	}
	public long lap(){
		long newMilli = System.currentTimeMillis();
		long diff = newMilli - lastMilli;
		lastMilli = newMilli;
		milliDiffTwoBack = milliDiffOneBack;
		milliDiffOneBack = diff;
		return diff;
	}
	public String lapFactor(){
		if(milliDiffTwoBack==0||milliDiffOneBack==0){
			return "NA";
		}
		else{
			double d = (0.0+milliDiffOneBack)/(0.0+milliDiffTwoBack);
			return String.format("%.4f", d);
		}
	}
	

	/**
	 * dice works like the a number of for loops.  Example dice=3 means 3 nested for loops
	 * 
	 * @param dice
	 * @param sides
	 * @param carryValue
	 * @param map
	 * @return
	 */
	public DiceStat getDiceStat(int dice, int sides){
		return new DiceStat(dice, sides, getMap(dice, sides, 0, new HashMap<Integer,BigInteger>()));
	}
	private HashMap<Integer,BigInteger> getMap(
		int dice, int sides, Integer carryValue, HashMap<Integer,BigInteger> map
	){
		for(int x =1; x <= sides; x=x+1){
			//System.out.println("num("+dice+")x("+x+") = "+carryValue);
			if(dice == 1){
				Integer key = carryValue+x;
				if(map.containsKey(key)){
					map.put(key, map.get(key).add(one));
				}else{
					map.put(key, one);
				}
			}
			else{
				map = getMap(dice-1,sides,carryValue+x,map);
			}
		}
		return map;
		
	}	
	
	/*
			2	1+1	1/36 = 3%
			3	1+2, 2+1	2/36 = 6%
			4	1+3, 2+2, 3+1	3/36 = 8%
			5	1+4, 2+3, 3+2, 4+1	4/36 = 11%
			6	1+5, 2+4, 3+3, 4+2, 5+1	5/36 = 14%
			7	1+6, 2+5, 3+4, 4+3, 5+2, 6+1	6/36 = 17%
			8	2+6, 3+5, 4+4, 5+3, 6+2	5/36 = 14%
			9	3+6, 4+5, 5+4, 6+3	4/36 = 11%
			10	4+6, 5+5, 6+4	3/36 = 8%
			11	5+6, 6+5	2/36 = 6%
			12	6+6	1/36 = 3%

	 */


}
