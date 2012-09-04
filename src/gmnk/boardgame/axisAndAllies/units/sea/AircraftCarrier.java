package gmnk.boardgame.axisAndAllies.units.sea;

import gmnk.boardgame.axisAndAllies.units.UnitName;
import gmnk.boardgame.axisAndAllies.units.types.SeaUnit;

public class AircraftCarrier extends SeaUnit{
	public AircraftCarrier(){
		cost	= 14;
		attack 	= 1;
		defense = 2;
		move 	= 2;
	}
	@Override
	public UnitName getUnitName() {
		return UnitName.AIRCRAFT_CARRIER;
	}
}
