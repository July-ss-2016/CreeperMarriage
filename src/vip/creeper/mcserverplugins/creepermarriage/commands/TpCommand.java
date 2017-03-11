package vip.creeper.mcserverplugins.creepermarriage.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vip.creeper.mcserverplugins.creepermarriage.managers.MarriageManager;
import vip.creeper.mcserverplugins.creepermarriage.marriage.MarriagePlayer;
import vip.creeper.mcserverplugins.creepermarriage.utils.Util;

public class TpCommand implements ICommand {

	@Override
	public boolean execute(CommandSender cs, Command cmd, String lable, String[] args) {
		if(cs instanceof Player) {
			Player player=(Player)cs;
			String playerName=player.getName();
			if(!MarriageManager.isMarriedPlayer(playerName)) {
				Util.sendMsg(player, "&c你还没有结婚!");
				return true;
			}
			MarriagePlayer mp=new MarriagePlayer(playerName);
			Player partner=Bukkit.getPlayer(mp.getPartnerName());
			if(!partner.isOnline()) {
				Util.sendMsg(player, "&c你的情侣不在线!");
				return true;
			}
			player.teleport(partner.getLocation());
			Util.sendMsg(player, "&e已传送!");
		}
		return false;
	}

}
