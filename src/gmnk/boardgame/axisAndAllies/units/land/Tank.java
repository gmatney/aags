package gmnk.boardgame.axisAndAllies.units.land;

import gmnk.boardgame.axisAndAllies.units.UnitName;
import gmnk.boardgame.axisAndAllies.units.types.LandUnit;

public class Tank extends LandUnit{
	public Tank(){
		cost	= 5;
		attack 	= 3;
		defense = 3;
		move 	= 2;
	}
	@Override
	public UnitName getUnitName() {
		return UnitName.TANK;
	}
}
