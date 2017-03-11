package vip.creeper.mcserverplugins.creepermarriage.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class HelpCommand implements ICommand {

	@Override
	public boolean execute(CommandSender cs, Command cmd, String lable, String[] args) {
		cs.sendMessage(ChatColor.translateAlternateColorCodes('&',"&f&m &f&m &f&m &f&m &8&l&m[ &r &dCreeperMarriage &8&l&m ]&f&m &f&m &f&m &f&m &f&m&r "));
		cs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &f/cm help &7- &f查看帮助"));
		cs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &f/cm pro <游戏名> &7- &f向某人求婚"));
		cs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &f/cm tp &7- &f直接传送到你情侣所在的位置"));
		cs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &f/cm div &7- &f离婚"));
		cs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &f/cm info &7- &f查看你们的婚姻信息"));
		cs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &f/cm list &7- &f查看所有情侣"));
		return true;
	}

}
