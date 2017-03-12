package vip.creeper.mcserverplugins.creepermarriage.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vip.creeper.mcserverplugins.creepermarriage.Main;
import vip.creeper.mcserverplugins.creepermarriage.ProposeRequest;
import vip.creeper.mcserverplugins.creepermarriage.utils.Util;

public class DenyCommand implements ICommand {
	private static Main plugin=Main.getInstance();
	@Override
	public boolean execute(CommandSender cs, Command cmd, String lable, String[] args) {
		Player player=(Player)cs;
		String playerName=player.getName();
		if(args.length==2 && args[0].equalsIgnoreCase("deny")) {
			String suitorName=args[1];
			if(!plugin.marryRequests.containsKey(suitorName)) {
				Util.sendMsg(player, "&c你没有待处理的请求!");
				return true;
			}
			ProposeRequest request=plugin.marryRequests.get(suitorName);
			plugin.marryRequests.remove(suitorName);
			if(!request.getPartnerName().equalsIgnoreCase(playerName)) {
				Util.sendMsg(player, "&c参数错误: 对象不符!");
				return true;
			}
			Util.sendMsg(player, "&e已拒绝!");
			Util.sendMsg(Bukkit.getPlayer(suitorName), "&e"+playerName+" &e谢绝了你的结婚请求!");
			return true;
		}
		return false;
	}

}
