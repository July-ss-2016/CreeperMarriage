package vip.creeper.mcserverplugins.creepermarriage.managers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import vip.creeper.mcserverplugins.creepermarriage.Main;
import vip.creeper.mcserverplugins.creepermarriage.MarriagePlayer;
//import vip.creeper.mcserverplugins.creepermarriage.marriage.SexType;
import vip.creeper.mcserverplugins.creepermarriage.utils.Util;

public class MarriageManager {
	private static Main plugin=Main.getInstance();
	public static File dataFolder=new File(plugin.getDataFolder().getAbsolutePath()+File.separator+"data");
	public static File playerDataFolder=new File(plugin.getDataFolder().getAbsolutePath()+File.separator+"data"+File.separator+"players");
	public static File marriedPlayerFile=new File(plugin.getDataFolder().getAbsolutePath()+File.separator+"data"+File.separator+"marriedplayers.yml");
	//是否为已婚玩家
	public static boolean isMarriedPlayer(String playerName) {
		return new File(playerDataFolder.getAbsolutePath()+File.separator+playerName+".yml").exists();
	}
	//获得所有已结婚的玩家列表
	public static List<MarriagePlayer> getMarriedPlayers() {
		List<String> marriedPlayers=YamlConfiguration.loadConfiguration(marriedPlayerFile).getStringList("players");
		List<MarriagePlayer> result=new LinkedList<MarriagePlayer>();
		for(int i=0;i<marriedPlayers.size();i++) {
			result.add(new MarriagePlayer(marriedPlayers.get(i)));
		}
		return result;
	}
	//添加玩家到已结婚的玩家列表
	public static boolean addPlayerToMarriedPlayerList(String playerName) {
		YamlConfiguration marriedPlayerYaml=YamlConfiguration.loadConfiguration(marriedPlayerFile);
		//旧的玩家列表
		List<String> players=marriedPlayerYaml.getStringList("players");
		players.add(0, playerName);
		marriedPlayerYaml.set("players", players);
		try {
			marriedPlayerYaml.save(marriedPlayerFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	//从已结婚的玩家列表删除玩家
	public static boolean removePlayerFromMarriedPlayerList(String playerName) {
		YamlConfiguration marriedPlayerYaml=YamlConfiguration.loadConfiguration(marriedPlayerFile);
		//旧的玩家列表
		List<String> players=marriedPlayerYaml.getStringList("players");
		players.remove(playerName);
		marriedPlayerYaml.set("players", players);
		try {
			marriedPlayerYaml.save(marriedPlayerFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;		
	}
	//创建玩家
	public static MarriagePlayer createMarriagePlayer(String playerName,String partnerName,Date marriedDate) {
		//禁止重婚
		if(isMarriedPlayer(playerName) || isMarriedPlayer(partnerName)) {
			return null;
		}
		try {
			File playerFile=new File(playerDataFolder.getAbsolutePath()+File.separator+playerName+".yml");
			File partnerFile=new File(playerDataFolder.getAbsolutePath()+File.separator+partnerName+".yml"); 
			if(!playerFile.exists()) {
				playerFile.createNewFile();
			} 
			if(!partnerFile.exists()) {
				partnerFile.createNewFile();
			}
			//1
			YamlConfiguration playerYaml=YamlConfiguration.loadConfiguration(playerFile);
			playerYaml.set("playerName", playerName);
			playerYaml.set("partnerName", partnerName);
			//playerYaml.set("playerSex", playerSex.name());
			playerYaml.set("marriedDate", new SimpleDateFormat("yyyy/MM/dd").format(marriedDate));
			playerYaml.save(playerFile);
			//2
			YamlConfiguration partnerYaml=YamlConfiguration.loadConfiguration(partnerFile);
			partnerYaml.set("playerName", partnerName);
			partnerYaml.set("partnerName", playerName);
			//partnerYaml.set("playerSex", partnerSex.name());
			partnerYaml.set("marriedDate", new SimpleDateFormat("yyyy/MM/dd").format(marriedDate));
			partnerYaml.save(partnerFile);
			//存到已结婚玩家的列表中
			addPlayerToMarriedPlayerList(playerName);
		} catch(IOException e) {
			e.printStackTrace();
		} 
		return new MarriagePlayer(playerName);
	}
	//设置最后登录日期
	public static boolean setMarriagePlayerLastLoginTime(String playerName,Date date) {
		File playerFile=new File(playerDataFolder.getAbsolutePath()+File.separator+playerName+".yml");
		boolean result=false;
		if(playerFile.exists()) {
			YamlConfiguration playerYaml=YamlConfiguration.loadConfiguration(playerFile);
			playerYaml.set("lastLoginTime", Util.dateToStr(date));			
			result=true;
		}
		return result;
	}
}
