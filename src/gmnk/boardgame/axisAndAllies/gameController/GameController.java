package gmnk.boardgame.axisAndAllies.gameController;

import gmnk.boardgame.axisAndAllies.CONSTANTS;
import gmnk.boardgame.axisAndAllies.GamePhase;
import gmnk.boardgame.axisAndAllies.PurchaseOrder;
import gmnk.boardgame.axisAndAllies.territory.Territory;
import gmnk.boardgame.axisAndAllies.territory.World;
import gmnk.boardgame.axisAndAllies.units.StationedGroup;
import gmnk.boardgame.axisAndAllies.units.UnitConcrete;
import gmnk.boardgame.axisAndAllies.units.UnitName;
import gmnk.boardgame.axisAndAllies.units.sea.AircraftCarrier;
import gmnk.boardgame.axisAndAllies.units.sea.Transport;
import gmnk.boardgame.axisAndAllies.units.types.AirUnit;
import gmnk.boardgame.axisAndAllies.units.types.LandUnit;
import gmnk.boardgame.axisAndAllies.units.types.SeaUnit;
import gmnk.boardgame.axisAndAllies.worldPowers.Players;
import gmnk.boardgame.axisAndAllies.worldPowers.WorldPower;
import gmnk.boardgame.axisAndAllies.worldPowers.WorldPowerJsonDeserializer;
import gmnk.boardgame.axisAndAllies.worldPowers.WorldPowerName;
import gmnk.boardgame.axisAndAllies.worldPowers.WorldPowers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;

public class GameController {
	private static Logger log = Logger.getLogger(GameController.class);
	private World w;
	private Players p;
	private WorldPowerJsonDeserializer wpDeser = new WorldPowerJsonDeserializer();
	private WorldPowers wp = new WorldPowers();
	private long phaseStartTime;
	
	public WorldPowerName activePower;
	public GamePhase gamePhase;
	private LinkedList<WorldPowerName> worldPowerOrder;
	private LinkedList<GamePhase> gamePhaseOrder;
	
	private PurchaseOrder currentPurchaseOrder;
	
	private final int TURN_LENGTH = 1000000; // Time per game stage (in ms);
	private boolean volunteerEndPhase; // This flag can be set by the client if they wish to end the current phase before the timer runs out.
	
	public void initializeGame() throws FileNotFoundException{
		log.info("Initalizing the Game");
		w = new World();
		log.info("Creating world");

		File configFile = new File( CONSTANTS.ETC_PATH+"1942SpringStartingConfig.json");
		Reader configReader = new FileReader(configFile);
		log.info("Trying to using gson");
		
		JsonStreamParser s = new JsonStreamParser(configReader);
		
		if(!s.hasNext()){
			log.error("No JSON Objects available from stream");
		}
		else{
			log.debug("Grabing JSON object from Stream");
			JsonElement configJsonElement = s.next();


			wp = wpDeser.deserialize(configJsonElement,w);
			
			//TODO figure out how to make this work (with the serializer you made)
			//TODO not sure how to get GSON to know where to find my serializer
			//WorldPowers powers = g.fromJson(configJsonElement, WorldPowers.class);
			
			log.info("SIZE =  "+wp.getPowersSize());
		}
		
		phaseStartTime = System.currentTimeMillis();
		activePower = WorldPowerName.SOVIET_UNION;
		gamePhase = GamePhase.PURCHASE_UNITS;
		gamePhaseOrder = new LinkedList<GamePhase>();
		for(int i = 0; i < GamePhase.values().length; i++) {
			gamePhaseOrder.addLast(GamePhase.values()[i]);
		}
		worldPowerOrder = new LinkedList<WorldPowerName>();
		for(int i = 0; i < WorldPowerName.values().length; i++) {
			worldPowerOrder.addLast(WorldPowerName.values()[i]);
		}
		resetMovementPoints(activePower);
	}
	public World getWorld(){
		return w;
	}
	public WorldPowers getWorldPowers() {
	    return wp;
	}
	
	public void update() {
		if(isTurnOver()) {
			gamePhase = getNextGamePhase(gamePhase);
			switch(gamePhase) {
				case PURCHASE_UNITS:
					activePower = getNextWorldPower(activePower);
					resetMovementPoints(activePower);
					currentPurchaseOrder = null;
				case COMBAT_MOVEMENT:
				case COMBAT:
				case NONCOMBAT_MOVEMENT:
				case PLACE_UNITS:
			}

			phaseStartTime = System.currentTimeMillis();
		}
	}
	
	public void resetMovementPoints(WorldPowerName power) {
		for(Territory t : w.getTerritories().values()) {
			StationedGroup group = t.getUnitsStationed(power);
			for(UnitConcrete unit : group.getUnits()) {
				unit.setMovementPoints(unit.getProfile().getMove());
			}
		}
	}
	
	// TODO 
	public boolean requestMoveUnit(UnitConcrete unit, Territory source, Territory destination) {
		// First do some validation to ensure that the unit can physically make the move.
		if(!source.getNeighbors().contains(destination)) {
			return false;
		}
		if(unit.getMovementPoints() <= 0) {
			return false;
		}
		
		// LandUnit -> Land or SeaUnit -> Sea
		if(unit.getProfile() instanceof LandUnit && !destination.isSeaZone()
				|| unit.getProfile() instanceof SeaUnit && destination.isSeaZone()) {
			source.removeUnit(activePower, unit);
			destination.addUnit(activePower, unit);
			unit.setTerritorySource(destination);
			unit.setMovementPoints(unit.getMovementPoints() - 1);
			if(unit.getProfile() instanceof Transport
					|| unit.getProfile() instanceof AircraftCarrier) {
				for(UnitConcrete cargo : unit.getCargo()) {
					source.removeUnit(activePower, cargo);
					destination.addUnit(activePower, cargo);
					cargo.setTerritorySource(destination);
				}
			}
			return true;
		}
		// LandUnit -> Sea (transport)
		else if(unit.getProfile() instanceof LandUnit && destination.isSeaZone() && !source.isSeaZone()) {
			ArrayList<UnitConcrete> transports = destination.getUnitsStationed(activePower).getUnitsByName(UnitName.TRANSPORT);
			for(UnitConcrete t : transports) {
				if(t.tryAddCargo(unit)) {
					source.removeUnit(activePower, unit);
					destination.addUnit(activePower, unit);
					unit.setTerritorySource(destination);
					unit.setMovementPoints(1); // All transported units can load-unload in same turn.
					return true;
				}
			}
		}
		// AirUnit 
		else if(unit.getProfile() instanceof AirUnit) {
			if(checkAirUnitHasReturnPath(unit, destination)) {
				source.removeUnit(activePower, unit);
				destination.addUnit(activePower, unit);
				unit.setTerritorySource(destination);
				unit.setMovementPoints(unit.getMovementPoints() - 1);
				return true;
			}
		}
		// SeaUnit -> Land (impossible)
		else if(unit.getProfile() instanceof SeaUnit && !destination.isSeaZone()) {
			return false; // Boats can never go on land.
		}
		return true;
	}
	
	public boolean checkAirUnitHasReturnPath(UnitConcrete unit, Territory source) {
		HashSet<Territory> reachableTerritories = getTerritoriesInRange(source, unit.getMovementPoints() - 1);
		for(Territory t : reachableTerritories) {
			// TODO: verify that air unit can land in a territory in range.
		}
		return true;
	}
	
	public HashSet<Territory> getTerritoriesInRange(Territory source, int movementPoints) {
		HashSet<Territory> territories = new HashSet<Territory>();
		territories.add(source);
		if(movementPoints > 0) {
			for(Territory neighbor : source.getNeighbors()) {
				territories.addAll(getTerritoriesInRange(neighbor, movementPoints - 1));
			}
		}
		return territories;
	}
	
	public boolean isTurnOver() {
		if(phaseStartTime < (System.currentTimeMillis() - TURN_LENGTH) || volunteerEndPhase) {
			volunteerEndPhase = false;
			return true;
		}
		return false;
	}
	public void volunteerEndPhase() {
		volunteerEndPhase = true;
	}
	public GamePhase getNextGamePhase(GamePhase currentPhase) {
		int nextPhaseIndex = gamePhaseOrder.indexOf(currentPhase) + 1;
		if(nextPhaseIndex >= gamePhaseOrder.size())
			return gamePhaseOrder.getFirst();
		return gamePhaseOrder.get(nextPhaseIndex);
	}
	public WorldPowerName getNextWorldPower(WorldPowerName currentWorldPower) {
		int nextPowerIndex = worldPowerOrder.indexOf(currentWorldPower) + 1;
		if(nextPowerIndex >= worldPowerOrder.size() 
				|| worldPowerOrder.get(nextPowerIndex) == WorldPowerName.UNKNOWN)
			return WorldPowerName.SOVIET_UNION;
		return worldPowerOrder.get(nextPowerIndex);
	}
	
	public boolean requestPurchaseUnits(PurchaseOrder order) {
		WorldPower currentPower = wp.getPower(activePower);
		if(order.getTotalCost() > currentPower.getStartingIpcIncome() + currentPower.getIpcSavings())
			return false;
		if(order.getTotalNumUnits() > currentPower.getUnitPlacementCount())
			return false;
		
		currentPurchaseOrder = order;
		return true;
	}
	
	public static void main(String[] args) {
		try{
			
			GameController g = new GameController();
			g.initializeGame();
			log.info("This should show troops for Germany:\n"+
					g.getWorld().getTerritoryByName("GERMANY").getUnitsStationed(WorldPowerName.GERMANY)
			);
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
		}
	}
}
