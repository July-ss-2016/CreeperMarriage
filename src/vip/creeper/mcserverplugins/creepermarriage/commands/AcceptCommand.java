package vip.creeper.mcserverplugins.creepermarriage.commands;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vip.creeper.mcserverplugins.creepermarriage.Main;
import vip.creeper.mcserverplugins.creepermarriage.configs.MainConfig;
import vip.creeper.mcserverplugins.creepermarriage.economies.PlayerPointsEconomy;
import vip.creeper.mcserverplugins.creepermarriage.economies.VaultEconomy;
import vip.creeper.mcserverplugins.creepermarriage.managers.MarriageManager;
import vip.creeper.mcserverplugins.creepermarriage.marriage.PayType;
import vip.creeper.mcserverplugins.creepermarriage.marriage.ProposeRequest;
import vip.creeper.mcserverplugins.creepermarriage.utils.Util;
import vip.creeper.mcserverplugins.creepertitleapi.CreeperTitleApi.TitleType;

public class AcceptCommand implements ICommand {
	private static Main plugin=Main.getInstance();
	@Override
	public boolean execute(CommandSender cs, Command cmd, String lable, String[] args) {
		Player player=(Player)cs;
		String playerName=player.getName();
		if(args.length==2 && args[0].equals("accept")) {
			String suitorName=args[1];
			//判断有没有请求
			if(plugin.marryRequests.containsKey(suitorName)) {
				ProposeRequest request=plugin.marryRequests.get(suitorName);
				//拒绝重婚
				if(MarriageManager.isMarriedPlayer(playerName)) {
					Util.sendMsg(player, "&c你已经结婚了!");
					plugin.marryRequests.remove(suitorName);
					return true;
				}
				//同上
				if(MarriageManager.isMarriedPlayer(suitorName)) {
					Util.sendMsg(player, "&c对方已经结婚了!");
					plugin.marryRequests.remove(suitorName);
					return true;
				}
				//名字比对
				if(request.getPartnerName()==playerName) {
					Player suitor=Bukkit.getPlayer(suitorName);
					PayType payType=plugin.marryRequests.get(suitorName).getPayType();
					plugin.marryRequests.remove(suitorName);
					//判断钱数，扣钱
					if(payType==null) {
						Util.sendMsg(player, "&c非法的参数: 空的PayType!");
						return true;
					}
					if((payType==PayType.MONEY  && VaultEconomy.look(suitorName)-MainConfig.marrySpendMoney<0) || 
							payType==PayType.POINTS && PlayerPointsEconomy.look(suitorName)-MainConfig.marrySpendPoints<0) {
							
						Util.sendMsg(suitor, "&c结婚失败: 你的财产不足以来支付结婚费用!");
						Util.sendMsg(player, "&c结婚失败: 对方的结婚费用不足!");
						return true;
					} 
					if(payType==PayType.MONEY) {
						VaultEconomy.take(suitorName, MainConfig.marrySpendMoney);
					} else {
						PlayerPointsEconomy.take(suitorName, MainConfig.marrySpendPoints);
					}
					MarriageManager.createMarriagePlayer(suitorName, playerName, new Date());
					Util.broadcastTitle(TitleType.TITLE, "&d有情人终成眷属");
					Util.broadcastTitle(TitleType.SUBTITLE, "&e"+playerName+"&e 和 "+suitorName+" 现在结婚了!");
					Util.sendMsg(player, "&d结婚成功!");
					if(suitor.isOnline()) {
						Util.sendMsg(suitor, "&d结婚成功!");
					}
					return true;
				}
				plugin.marryRequests.remove(suitorName);
				Util.sendMsg(player, "&c非法的参数(配偶名字不符).");
				return true;
			} else {
				Util.sendMsg(player, "&c你没有未处理的请求!");
				return true;
			}
		}
		return false;
	}

}
