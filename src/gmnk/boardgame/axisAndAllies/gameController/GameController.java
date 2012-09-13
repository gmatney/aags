package gmnk.boardgame.axisAndAllies.gameController;

import gmnk.boardgame.axisAndAllies.CONSTANTS;
import gmnk.boardgame.axisAndAllies.GamePhase;
import gmnk.boardgame.axisAndAllies.territory.Territory;
import gmnk.boardgame.axisAndAllies.territory.World;
import gmnk.boardgame.axisAndAllies.units.UnitConcrete;
import gmnk.boardgame.axisAndAllies.units.types.LandUnit;
import gmnk.boardgame.axisAndAllies.worldPowers.Players;
import gmnk.boardgame.axisAndAllies.worldPowers.WorldPowerJsonDeserializer;
import gmnk.boardgame.axisAndAllies.worldPowers.WorldPowerName;
import gmnk.boardgame.axisAndAllies.worldPowers.WorldPowers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
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
	private LinkedList<GamePhase> gamePhaseOrder;
	private LinkedList<WorldPowerName> worldPowerOrder;
	private final int TURN_LENGTH = 1000; // Time per game stage (in ms);
	
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
			if(gamePhase == GamePhase.PURCHASE_UNITS)
				activePower = getNextWorldPower(activePower);
			phaseStartTime = System.currentTimeMillis();
		}
	}
	
	// TODO 
	public boolean combatMoveUnit(UnitConcrete unit, ArrayList<Territory> path) {
		// First do some validation to ensure that the unit can physically make the move.
		if(unit.getTerritorySource() != path.get(0)) {
			return false;
		}
		for(int i = 0; i < path.size() - 1; i++) {
			if(!path.get(i).getNeighbors().contains(path.get(i + 1)))
				return false;
		}
		if(unit.getProfile() instanceof LandUnit) {

		}
		return true;
	}
	
	public boolean isTurnOver() {
		return phaseStartTime < (System.currentTimeMillis() - TURN_LENGTH); 
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
