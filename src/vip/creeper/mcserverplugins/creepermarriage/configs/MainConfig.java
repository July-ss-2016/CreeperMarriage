package vip.creeper.mcserverplugins.creepermarriage.configs;

import org.bukkit.configuration.file.FileConfiguration;

import vip.creeper.mcserverplugins.creepermarriage.Main;

public class MainConfig {
	public static String marriedPrefix=null;
	public static String unMarriedPrefix=null;
	public static int marrySpendMoney=0;
	public static int marrySpendPoints=0;
	public static long cacheLiveTime=0;
	private static Main plugin=Main.getInstance();
	private static FileConfiguration config=plugin.getConfig();
	public static void loadConfig() {
		plugin.saveDefaultConfig();
		plugin.reloadConfig();
		marriedPrefix=config.getString("settings.married_prefix");
		unMarriedPrefix=config.getString("settings.unmarried_prefix");
		marrySpendMoney=config.getInt("settings.marry_spend_money");
		marrySpendPoints=config.getInt("settings.marry_spend_points");
		cacheLiveTime=config.getInt("settings.cache_livetime");
	}
}
