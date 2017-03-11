package vip.creeper.mcserverplugins.creepermarriage.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import vip.creeper.mcserverplugins.creepermarriage.configs.MainConfig;

public class OpCommand implements CommandExecutor {
	public boolean onCommand(CommandSender cs,Command cmd,String lable,String[] args) {
		if(!(cs.hasPermission("CreeperMarriage.admin"))) {
			return true;
		}
		if(args.length==1) {
			if(args[0].equalsIgnoreCase("reload")) {
				MainConfig.loadConfig();
				cs.sendMessage("配置重载完毕!");
				return true;
			}
		}
		return false;
	}
}
