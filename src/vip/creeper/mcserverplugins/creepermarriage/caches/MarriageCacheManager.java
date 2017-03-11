package vip.creeper.mcserverplugins.creepermarriage.caches;

import java.util.HashMap;

import vip.creeper.mcserverplugins.creepermarriage.MarriagePlayer;
import vip.creeper.mcserverplugins.creepermarriage.configs.MainConfig;

public class MarriageCacheManager {
	public static HashMap<String,MarriagePlayer> cache=new HashMap<String,MarriagePlayer>();
	public static HashMap<String,Long> cacheLiveTime=new HashMap<String,Long>();
	public static MarriagePlayer getMarriagePlayer(final String playerName) {
		MarriagePlayer result;
		boolean hasKey=cache.containsKey(playerName);
		//没Key，或有Key且cache过期了
		if(!hasKey || (System.currentTimeMillis()-cacheLiveTime.get(playerName)>(MainConfig.cacheLiveTime*1000))) {
			result=new MarriagePlayer(playerName);
			cache.put(playerName, result);
			cacheLiveTime.put(playerName, System.currentTimeMillis());
		} else {
			result=cache.get(playerName);	
		}
		return result;
	}
}
