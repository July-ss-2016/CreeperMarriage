package vip.creeper.mcserverplugins.creepermarriage.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vip.creeper.mcserverplugins.creepermarriage.configs.MainConfig;
import vip.creeper.mcserverplugins.creepermarriage.utils.Util;

public class InfoCommand implements ICommand {
	@Override
	public boolean execute(CommandSender cs, Command cmd, String lable, String[] args) {
		Player player=(Player)cs;
		if(MainConfig.infoShopName==null || MainConfig.infoShopName.equals("")) {
			Util.sendMsg(player, "信息商店功能未启用!");
			return true;
		}
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bs open "+MainConfig.infoShopName+" "+player.getName());
		return true;
	}

}
