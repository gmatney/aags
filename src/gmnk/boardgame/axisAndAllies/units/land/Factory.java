package gmnk.boardgame.axisAndAllies.units.land;

import gmnk.boardgame.axisAndAllies.units.UnitName;
import gmnk.boardgame.axisAndAllies.units.types.LandUnit;

public class Factory extends LandUnit{
	public Factory(){
		cost	= 15;
		attack 	= 0;
		defense = 0;
		move 	= 0;
	}
	@Override
	public UnitName getUnitName() {
		return UnitName.FACTORY;
	}
}
