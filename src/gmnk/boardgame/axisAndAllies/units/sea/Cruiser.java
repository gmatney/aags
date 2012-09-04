package gmnk.boardgame.axisAndAllies.units.sea;

import gmnk.boardgame.axisAndAllies.units.UnitName;
import gmnk.boardgame.axisAndAllies.units.types.SeaUnit;

public class Cruiser extends SeaUnit{
	public Cruiser(){
		cost	= 12;
		attack 	= 3;
		defense = 3;
		move 	= 2;
	}
	@Override
	public UnitName getUnitName() {
		return UnitName.CRUISER;
	}
}
