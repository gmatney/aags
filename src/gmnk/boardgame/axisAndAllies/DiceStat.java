package gmnk.boardgame.axisAndAllies;

import java.math.BigInteger;
import java.util.HashMap;

public class DiceStat {
	BigInteger zero  = new BigInteger("0");
	BigInteger one   = new BigInteger("1");
	BigInteger total = zero;
	double expectedValueOfDice;
	double expectedValueOfOneDie;
	int dice;
	int sides;
	int lowValue;
	int highValue;
	
	public HashMap<Integer,BigInteger> totalPointFrequencies;
	public double expectedTotalValue; 

	private double getExpectedValudeOfOneDice(int sides){
		double value = 0.0;
		for(int i=1; i<=sides; i++){
			value = value + i;
		}
		return value/sides;
	}
	
	public DiceStat(int dice, int sides, HashMap<Integer, BigInteger> totalPointFrequencies) {
		this.dice = dice;
		this.sides = sides;
		this.expectedValueOfOneDie = getExpectedValudeOfOneDice(sides);
		this.totalPointFrequencies = totalPointFrequencies;
		for(Integer key : totalPointFrequencies.keySet() ){
			BigInteger value = totalPointFrequencies.get(key); 
			total = total.add(value);
		}
		expectedTotalValue =  expectedValueOfOneDie * dice;
		lowValue = sides;
		highValue = dice * sides;
	}
	
	/**
	 * Power is the number for hit where die count less than or equal to.
	 * Power 2 gets hits on 1 and 2
	 * Power 1 gets hits on 1 etc..
	 * 
	 * @param power
	 * @return
	 */
	public String getHitSummaryForPower(int power){
		//Is there any correlation?
		return "TODO - fix";//TODO
	}
	
	
	public String getSummaryPointFrequency(){
		StringBuilder sb = new StringBuilder();
		String delin="\t";
		String split="|";
		sb.append(toString());
		sb.append(delin+expectedTotalValue);
		for(int key=1; key<highValue;key++){
			if(totalPointFrequencies.containsKey(key)){
				sb.append(delin+key+split+totalPointFrequencies.get(key));
			}
			else{
				sb.append(delin+key+split+0);
			}	
		}
		return sb.toString();
	}
	public String toString(){
		return dice+"d"+sides;
	}
	
}
