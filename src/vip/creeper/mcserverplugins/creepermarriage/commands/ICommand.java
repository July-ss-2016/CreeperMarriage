package vip.creeper.mcserverplugins.creepermarriage.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface ICommand {
	boolean execute(CommandSender cs,Command cmd,String lable,String[] args);
}
