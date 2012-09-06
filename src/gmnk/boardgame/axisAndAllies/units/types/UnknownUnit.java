package gmnk.boardgame.axisAndAllies.units.types;

import gmnk.boardgame.axisAndAllies.units.UnitName;

public class UnknownUnit extends UnitProfile {
	@Override
	public UnitName getUnitName() {
		return UnitName.UNKNOWN;
	}
}
