package gmnk.boardgame.axisAndAllies;

import java.io.File;

public class CONSTANTS {
	private static void init(){
		getBaseWorkingDir();
	}
	
	private static String baseWorkingDir;
	public static String getBaseWorkingDir(){
		if(baseWorkingDir == null){
			String bin = "bin/";
			baseWorkingDir = (new File(
				CONSTANTS.class.getProtectionDomain()
				.getCodeSource().getLocation().getPath()
			)).getAbsolutePath();
			baseWorkingDir = baseWorkingDir.substring(0,baseWorkingDir.length()-"bin".length());
		}
		return baseWorkingDir;
	}
    public static final String RESOURCE_PATH = getBaseWorkingDir()+"src/gmnk/boardgame/axisAndAllies/gui/resources/";
    public static final String TERRITORY_CONFIG = "Territory_Config";
    
    
    /*
/home/garnett/workspace/aags/./gmnk/boardgame/axisAndAllies/gui/resources/Territory_Config (No such file or directory)
/home/garnett/workspace/aags/src/gmnk/boardgame/axisAndAllies/territory/World.java

     */
    
}
