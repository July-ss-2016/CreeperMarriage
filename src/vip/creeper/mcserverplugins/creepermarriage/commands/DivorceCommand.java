package vip.creeper.mcserverplugins.creepermarriage.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vip.creeper.mcserverplugins.creepermarriage.MarriagePlayer;
import vip.creeper.mcserverplugins.creepermarriage.managers.MarriageManager;
import vip.creeper.mcserverplugins.creepermarriage.utils.Util;

public class DivorceCommand implements ICommand {

	@Override
	public boolean execute(CommandSender cs, Command cmd, String lable, String[] args) {
		Player player=(Player)cs;
		String playerName=player.getName();
		if(!MarriageManager.isMarriedPlayer(playerName)) {
			Util.sendMsg(player, "&c你还没结婚!");
			return true;
		}
		MarriagePlayer mp=new MarriagePlayer(playerName);
		Player partner=Bukkit.getPlayer(mp.getPartnerName());
		mp.divorce();
		//test
		Util.sendMsg(player, "");
		Util.sendMsg(player, "&c离婚了QAQ.");
		Util.sendMsg(partner, "&e"+playerName+" &c和你离婚了...");
		return true;
	}
}
