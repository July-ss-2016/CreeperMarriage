package vip.creeper.mcserverplugins.creepermarriage.economies;

import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;
import vip.creeper.mcserverplugins.creepermarriage.Main;

public class VaultEconomy {
	private static Economy vault=null;
	private static Main plugin=Main.getInstance();
	public static boolean hookVaultEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
           vault = economyProvider.getProvider();
        }
        return (vault != null);
	}
	@SuppressWarnings("deprecation")
	public static double look(String player) {
		return vault.getBalance(player);
	}
	public static boolean take(String player,int amount) {
		@SuppressWarnings("deprecation")
		ResponseType resultType=vault.withdrawPlayer(player, amount).type;
		return resultType==ResponseType.SUCCESS?true:false;
	}
}
