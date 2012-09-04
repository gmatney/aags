package gmnk.boardgame.axisAndAllies.units.land;

import gmnk.boardgame.axisAndAllies.units.UnitName;
import gmnk.boardgame.axisAndAllies.units.types.LandUnit;

public class Artillery extends LandUnit{
	public Artillery(){
		cost	= 4;
		attack 	= 2;
		defense = 2;
		move 	= 1;
	}
	@Override
	public UnitName getUnitName() {
		return UnitName.ARTILLERY;
	}
}
