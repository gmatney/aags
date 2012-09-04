package gmnk.boardgame.axisAndAllies.units.land;

import gmnk.boardgame.axisAndAllies.units.UnitName;
import gmnk.boardgame.axisAndAllies.units.types.LandUnit;

public class Infantry extends LandUnit{
	
	public Infantry(){
		cost	= 3;
		attack 	= 1;
		defense = 2;
		move 	= 1;
	}
	@Override
	public UnitName getUnitName() {
		return UnitName.INFANTRY;
	}

	
}
