package gmnk.boardgame.axisAndAllies.gameController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import org.apache.log4j.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;

import gmnk.boardgame.axisAndAllies.territory.World;
import gmnk.boardgame.axisAndAllies.worldPowers.Players;
import gmnk.boardgame.axisAndAllies.worldPowers.WorldPowerJsonDeserializer;
import gmnk.boardgame.axisAndAllies.worldPowers.WorldPowerName;
import gmnk.boardgame.axisAndAllies.worldPowers.WorldPowers;

public class GameController {
	private static Logger log = Logger.getLogger(GameController.class);
	private World w;
	private Players p;
	private WorldPowerJsonDeserializer wpDeser = new WorldPowerJsonDeserializer();
	private WorldPowers wp = new WorldPowers();
	public void initializeGame() throws FileNotFoundException{
		log.info("Initalizing the Game");
		w = new World();
		log.info("Creating world");
		File configFile = new File("/home/garnett/workspace/aags/etc/1942SpringStartingConfig.json");
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

		

		
				//g.fromJson(configReader, gmnk.boardgame.axisAndAllies.worldPowers.WorldPowerDeserializer.class);
		
		
//		JsonElement json = new JsonObject();
//		Type typeOfT;
//		JsonDeserializationContext context;
//		
//		Hashtable<String,WorldPower> powers = wpDeser.deserialize(json, typeOfT, context);
	}
	public World getWorld(){
		return w;
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
