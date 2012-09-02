package gmnk.boardgame.axisAndAllies.worldPowers;

import java.lang.reflect.Type;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class WorldPowerJsonDeserializer implements JsonDeserializer<WorldPowers> {
	private Logger log = Logger.getLogger(WorldPowerJsonDeserializer.class);
	@Override
	public WorldPowers deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context
			) throws JsonParseException {
		log.debug("Inside WorldPowerDeserializer");
		log.error("Figure out how you got this to be called if not directly");
		return deserialize(json);
	}
	public WorldPowers deserialize(JsonElement json) throws JsonParseException {
		log.debug("Inside WorldPowerDeserializer");
		WorldPowers wps = new WorldPowers();
		JsonArray worldPowers = json.getAsJsonObject().get("WorldPower").getAsJsonArray();
		log.debug("Found "+worldPowers.size()+" WorldPower JSON Objects");
		
		Iterator<JsonElement> it = worldPowers.iterator();
		
		while(it.hasNext()){
			JsonObject worldPower = it.next().getAsJsonObject();
			String worldPowerName = worldPower.get("WorldPowerName").getAsString();
			Faction faction = worldPower.get("Faction").getAsString().equalsIgnoreCase("AXIS")
					? Faction.Axis : Faction.Alliance;
			int startingIncome = worldPower.get("StartingIncome").getAsInt(); 
			
			log.info("Loading in WorldPower named '"+worldPowerName+"'");
			WorldPower wp = new WorldPower( worldPowerName,faction,startingIncome);
			wps.addWorldPower(wp);
		}
		
		
		
		return wps;
	}

}


