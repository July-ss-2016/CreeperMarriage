package vip.creeper.mcserverplugins.creepermarriage.economies;

import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;

import vip.creeper.mcserverplugins.creepermarriage.Main;

public class PlayerPointsEconomy {
	private static PlayerPointsAPI playerPoints=null;
	private static Main plugin=Main.getInstance();
	public static boolean hookPlayerPointsEconomy() {
	    playerPoints = PlayerPoints.class.cast(plugin.getServer().getPluginManager().getPlugin("PlayerPoints")).getAPI();
	    return playerPoints != null; 
	}
	@SuppressWarnings("deprecation")
	public static int look(String player) {
		return playerPoints.look(player);
	}
	@SuppressWarnings("deprecation")
	public static boolean take(String player,int amount) {
		return playerPoints.take(player, amount);
	}
}
