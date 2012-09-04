package gmnk.boardgame.axisAndAllies.worldPowers;

import gmnk.boardgame.axisAndAllies.gameController.EnumInterpreter;
import gmnk.boardgame.axisAndAllies.territory.World;
import gmnk.boardgame.axisAndAllies.units.StationedGroup;

import java.awt.Color;
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
	private static Logger log = Logger.getLogger(WorldPowerJsonDeserializer.class);
	@Override
	public WorldPowers deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context
			) throws JsonParseException {
		log.debug("Inside WorldPowerDeserializer");
		log.error("Figure out how you got this to be called if not directly");
		return deserialize(json);
	}
	private WorldPowers deserialize(JsonElement json) throws JsonParseException {
		log.debug("Inside WorldPowerDeserializer");
		WorldPowers wps = new WorldPowers();
		JsonArray worldPowers = json.getAsJsonObject().get("WorldPower").getAsJsonArray();
		log.debug("Found "+worldPowers.size()+" WorldPower JSON Objects");
		
		Iterator<JsonElement> it = worldPowers.iterator();
		
		while(it.hasNext()){
			JsonObject worldPower = it.next().getAsJsonObject();
			WorldPowerName worldPowerName = EnumInterpreter.getWorldPowerName( 
					worldPower.get("WorldPowerName").getAsString());
			Faction faction = EnumInterpreter.getFaction(
					worldPower.get("Faction").getAsString()); 
			int startingIncome = worldPower.get("StartingIncome").getAsInt();
	        Color guiColor = new Color(Integer.decode(worldPower.get("GuiColor").getAsString()));
	        guiColor = new Color(guiColor.getRed(), guiColor.getGreen(), guiColor.getBlue(), 180);
			String capitalTerritory = World.getStandardizedTerritoryKey(
					worldPower.get("capitalTerritory").getAsString());
			log.info("Loading in WorldPower named '"+worldPowerName+"'");
			WorldPower wp = new WorldPower( worldPowerName,faction,startingIncome,capitalTerritory,guiColor);
			//CANNOT POPULATE WORLD ELEMENT INDIRECTLY NOW
			//WOULD NEED TO DO IF WANT TO USE
			wps.addWorldPower(wp);
		}
		
		return wps;
	}
	public WorldPowers deserialize(JsonElement json,World w) throws JsonParseException {
		log.debug("Inside WorldPowerDeserializer");
		WorldPowers wps = new WorldPowers();
		JsonArray worldPowers = json.getAsJsonObject().get("WorldPower").getAsJsonArray();
		log.debug("Found "+worldPowers.size()+" WorldPower JSON Objects");
		
		Iterator<JsonElement> it = worldPowers.iterator();
		
		while(it.hasNext()){
			JsonObject worldPower = it.next().getAsJsonObject();
			WorldPowerName worldPowerName = EnumInterpreter.getWorldPowerName( 
					worldPower.get("WorldPowerName").getAsString());
			Faction faction = EnumInterpreter.getFaction(
					worldPower.get("Faction").getAsString()); 
			int startingIncome = worldPower.get("StartingIncome").getAsInt(); 
			Color guiColor = new Color(Integer.decode(worldPower.get("GuiColor").getAsString()));
			guiColor = new Color(guiColor.getRed(), guiColor.getGreen(), guiColor.getBlue(), 180);
			String capitalTerritory = World.getStandardizedTerritoryKey(
					worldPower.get("CapitalTerritory").getAsString());
			log.info("Loading in WorldPower named '"+worldPowerName+"'");
			WorldPower wp = new WorldPower( worldPowerName,faction,startingIncome,capitalTerritory,guiColor);
			
			log.info("Stationing original starting troops");
			
			JsonArray startingTerr = worldPower.get("TerritoryControlled").getAsJsonArray();
			for(int i=0; i<startingTerr.size();i++){
				JsonObject terr = startingTerr.get(i).getAsJsonObject();
				String terrName = World.getStandardizedTerritoryKey(terr.get("TerritoryName").getAsString()); 
				wp.addTerritory(terrName);
				JsonArray unitsStationed = terr.get("UnitsStationed").getAsJsonArray();
				StationedGroup sg = new StationedGroup(w.getTerritoryByName(terrName),worldPowerName);
				for(JsonElement unitElem : unitsStationed){
					JsonObject unit = unitElem.getAsJsonObject();
					String type = unit.get("type").getAsString();
					int quantity = unit.get("quantity").getAsInt();
					sg.addUnit(EnumInterpreter.getUnitName(type), quantity);
				}
				w.addStationedGroupToTerritory(sg, terrName);
			}
			
			
			wps.addWorldPower(wp);
		}
		
		
		
		return wps;
	}

}


