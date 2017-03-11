package vip.creeper.mcserverplugins.creepermarriage.commands;

import org.black_ixx.bossshop.core.BSShop;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vip.creeper.mcserverplugins.creepermarriage.Main;
import vip.creeper.mcserverplugins.creepermarriage.configs.MainConfig;

public class InfoCommand implements ICommand {
	private static Main plugin=Main.getInstance();
	private static BSShop shop;
	static {
		shop=plugin.getBsApi().getShop(MainConfig.infoShopName);
	}
	@Override
	public boolean execute(CommandSender cs, Command cmd, String lable, String[] args) {
		if(shop!=null) {
			plugin.getBsApi().openShop((Player) cs, shop);	
		}
		return true;
	}

}
