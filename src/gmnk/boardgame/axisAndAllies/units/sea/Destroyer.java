package gmnk.boardgame.axisAndAllies.units.sea;

import gmnk.boardgame.axisAndAllies.units.UnitName;
import gmnk.boardgame.axisAndAllies.units.types.SeaUnit;

public class Destroyer extends SeaUnit{
	public Destroyer(){
		cost	= 8;
		attack 	= 2;
		defense = 2;
		move 	= 2;
	}
	@Override
	public UnitName getUnitName() {
		return UnitName.DESTROYER;
	}
}
