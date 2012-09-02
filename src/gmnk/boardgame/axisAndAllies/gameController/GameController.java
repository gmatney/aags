package gmnk.boardgame.axisAndAllies.gameController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;

import gmnk.boardgame.axisAndAllies.territory.World;
import gmnk.boardgame.axisAndAllies.worldPowers.Players;
import gmnk.boardgame.axisAndAllies.worldPowers.WorldPower;
import gmnk.boardgame.axisAndAllies.worldPowers.WorldPowerJsonDeserializer;
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
		Gson g = new Gson();
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


			WorldPowers powers = wpDeser.deserialize(configJsonElement);
			
			//TODO figure out how to make this work (with the serializer you made)
			//TODO not sure how to get GSON to know where to find my serializer
			//WorldPowers powers = g.fromJson(configJsonElement, WorldPowers.class);
			
			log.info("SIZE =  "+powers.getPowersSize());
		}

		

		
				//g.fromJson(configReader, gmnk.boardgame.axisAndAllies.worldPowers.WorldPowerDeserializer.class);
		
		
//		JsonElement json = new JsonObject();
//		Type typeOfT;
//		JsonDeserializationContext context;
//		
//		Hashtable<String,WorldPower> powers = wpDeser.deserialize(json, typeOfT, context);
	}

	
	
	public static void main(String[] args) {
		try{
			
			GameController g = new GameController();
			g.initializeGame();
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
		}
	}
}
