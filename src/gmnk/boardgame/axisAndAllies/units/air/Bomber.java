package gmnk.boardgame.axisAndAllies.units.air;

import gmnk.boardgame.axisAndAllies.units.UnitName;
import gmnk.boardgame.axisAndAllies.units.types.AirUnit;

public class Bomber extends AirUnit{
	public Bomber(){
		cost	= 12;
		attack 	= 4;
		defense = 1;
		move 	= 6;
	}
	@Override
	public UnitName getUnitName() {
		return UnitName.BOMBER;
	}
}
