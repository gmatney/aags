package gmnk.boardgame.axisAndAllies.units.sea;

import gmnk.boardgame.axisAndAllies.units.UnitName;
import gmnk.boardgame.axisAndAllies.units.types.SeaUnit;

public class Transport extends SeaUnit {
	public Transport(){
		cost	= 7;
		attack 	= 0;
		defense = 0;
		move 	= 2;
	}
	@Override
	public UnitName getUnitName() {
		return UnitName.TRANSPORT;
	}
}
