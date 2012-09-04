package gmnk.boardgame.axisAndAllies.units.land;

import gmnk.boardgame.axisAndAllies.units.UnitName;
import gmnk.boardgame.axisAndAllies.units.types.LandUnit;

public class AntiaircraftArtillery extends LandUnit{
	public AntiaircraftArtillery(){
		cost	= 5;
		attack 	= 0;
		defense = 0;
		move 	= 1;
	}
	@Override
	public UnitName getUnitName() {
		return UnitName.ANTIAIRCRAFTGUN;
	}
}
