package vip.creeper.mcserverplugins.creepermarriage.commands;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vip.creeper.mcserverplugins.creepermarriage.Main;
import vip.creeper.mcserverplugins.creepermarriage.PayType;
import vip.creeper.mcserverplugins.creepermarriage.ProposeRequest;
import vip.creeper.mcserverplugins.creepermarriage.configs.MainConfig;
import vip.creeper.mcserverplugins.creepermarriage.economies.PlayerPointsEconomy;
import vip.creeper.mcserverplugins.creepermarriage.economies.VaultEconomy;
import vip.creeper.mcserverplugins.creepermarriage.managers.MarriageManager;
import vip.creeper.mcserverplugins.creepermarriage.utils.Util;

public class AcceptCommand implements ICommand {
	private static Main plugin=Main.getInstance();
	@Override
	public boolean execute(CommandSender cs, Command cmd, String lable, String[] args) {
		Player player=(Player)cs;
		String playerName=player.getName();
		
		if(args.length==2 && args[0].equals("accept")) {
			String suitorName=args[1];
			Player suitor=Bukkit.getPlayer(suitorName);
			
			//判断有没有请求
			if(plugin.marryRequests.containsKey(suitorName)) {
				ProposeRequest request=plugin.marryRequests.get(suitorName);
				//删除请求
				plugin.marryRequests.remove(suitorName);
				//不能自己和自己结婚
				if(playerName.equals(suitorName)) {
					Util.sendMsg(player, "&c你不能和自己结婚!");
					return true;					
				}
				//不能重婚
				if(MarriageManager.isMarriedPlayer(playerName)) {
					Util.sendMsg(player, "&c你已经结婚了!");
					return true;
				}
				//同上
				if(MarriageManager.isMarriedPlayer(suitorName)) {
					Util.sendMsg(player, "&c对方已经结婚了!");
					return true;
				}
				//名字比对
				if(!request.getPartnerName().equalsIgnoreCase(playerName)) {
					Util.sendMsg(player, "&c非法的参数(配偶名字不符).");
					return true;
				}
				//支付方式
				PayType payType=request.getPayType();
				if(payType==null) {
					Util.sendMsg(player, "&c非法的参数: 空的PayType!");
					return true;
				}
				//判断钱够不够
				if((payType==PayType.MONEY  && VaultEconomy.look(suitorName)-MainConfig.marrySpendMoney<0) || 
						payType==PayType.POINTS && PlayerPointsEconomy.look(suitorName)-MainConfig.marrySpendPoints<0) {	
					Util.sendMsg(suitor, "&c结婚失败: 你的财产不足以来支付结婚费用!");
					Util.sendMsg(player, "&c结婚失败: 对方的结婚费用不足!");
					return true;
				}
				//扣钱
				if(payType==PayType.MONEY) {
					VaultEconomy.take(suitorName, MainConfig.marrySpendMoney);
				} else {
					PlayerPointsEconomy.take(suitorName, MainConfig.marrySpendPoints);
				}
				//后续操作
				MarriageManager.createMarriagePlayer(suitorName, playerName, new Date());
				Util.broadcastTitle(vip.creeper.mcserverplugins.creepertitleapi.TitleManager.TitleType.TITLE, "&d有情人终成眷属");
				Util.broadcastTitle(vip.creeper.mcserverplugins.creepertitleapi.TitleManager.TitleType.SUBTITLE, "&e"+playerName+"&e 和 "+suitorName+" 现在结婚了!");
				Util.sendMsg(player, "&d结婚成功!");
				if(suitor!=null && suitor.isOnline()) {
					Util.sendMsg(suitor, "&d结婚成功!");
					Util.detonateFirework(suitor);
				}
				Util.detonateFirework(player);
				return true;
			} else {
				Util.sendMsg(player, "&c你没有未处理的请求!");
			}
			return true;
		} else {
			return false;
		}
	}
}
