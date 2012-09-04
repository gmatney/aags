package gmnk.boardgame.axisAndAllies.units.air;

import gmnk.boardgame.axisAndAllies.units.UnitName;
import gmnk.boardgame.axisAndAllies.units.types.AirUnit;

public class Fighter extends AirUnit{
	public Fighter(){
		cost	= 10;
		attack 	= 3;
		defense = 4;
		move 	= 4;
	}
	@Override
	public UnitName getUnitName() {
		return UnitName.FIGHTER;
	}
}
