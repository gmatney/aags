package gmnk.boardgame.axisAndAllies.units;

import gmnk.boardgame.axisAndAllies.territory.SeaTerritory;
import gmnk.boardgame.axisAndAllies.territory.Territory;
import gmnk.boardgame.axisAndAllies.units.sea.Transport;

public class LandUnit extends UnitProfile{
	private Transport transport;
	
	public boolean isInBoat(){
		if(transport == null){
			return false;
		}
		return true;
	}
	public void loadOntoTransport(Transport transport){
		this.transport = transport;
	}
	
	@Override
	public boolean canReachTerritoryToAttack(Territory territory){
		if(territory.getClass().equals(SeaTerritory.class)){
			if(isInBoat()){
				return transport.canReachTerritoryToAttack(territory);
			}
			else{
				return false;
			}
		}
		return super.canReachTerritoryToAttack(territory);
	}
	
}
