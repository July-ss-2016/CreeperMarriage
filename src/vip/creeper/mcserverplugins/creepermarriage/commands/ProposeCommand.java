package vip.creeper.mcserverplugins.creepermarriage.commands;

import java.util.HashMap;

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

public class ProposeCommand implements ICommand {
	private static Main plugin=Main.getInstance();
	private HashMap<String,Long> colddowns=new HashMap<String,Long>();
	@Override
	public boolean execute(CommandSender cs, Command cmd, String lable, String[] args) {
		if(cs instanceof Player) {
			Player player=(Player)cs;
			String playerName=player.getName();
			//发起结婚请求
			if(args.length==2 && args[0].equalsIgnoreCase("pro")) {
				Player partner=Bukkit.getPlayer(args[1]);
				if(partner==null || !partner.isOnline()) {
					Util.sendMsg(player, "&c该玩家不在线!");
					return true;
				}
				String partnerName=partner.getName();
				Util.sendMsg(player, "请先选择支付方式(左键下面的其中一个文字)：");
				Util.sendMsg(player, "");
				Util.sendRawMsg(player, 
						"[\"\",{\"text\":\"[CreeperMarriage] \",\"color\":\"green\"},{\"text\":\"点我花费"+MainConfig.marrySpendMoney+"个金币来支付\",\"color\":\"yellow\",\"underlined\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/cmarry pro paytype money "+partnerName+"\"}},{\"text\":\" \",\"color\":\"none\",\"underlined\":false},{\"text\":\"点我花费"+MainConfig.marrySpendPoints+"个点券来支付\",\"color\":\"yellow\",\"underlined\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/cmarry pro paytype points "+partnerName+"\"}}]");
				Util.sendMsg(player, "");
			}
			//确认支付方式
			if(args.length==4 && args[0].equals("pro") && args[1].equals("paytype")) {
				Player partner=Bukkit.getPlayer(args[3]);
				//再次判断复核
				//判断是否在线
				if(partner==null || !partner.isOnline()) {
					Util.sendMsg(player, "&c该玩家不在线!");
					return true;
				}
				String partnerName=partner.getName();
				//判断自己是否已经结婚
				if(MarriageManager.isMarriedPlayer(playerName)) {
					Util.sendMsg(player, "&c你不能出轨!");
					return true;
				}
				//判断被求婚的是否已经结婚
				if(MarriageManager.isMarriedPlayer(partner.getName())) {
					Util.sendMsg(player, "&c该玩家已经结婚了,你不能当小三!");
					return true;
				}
				//拒绝重复频繁发送请求
				if(colddowns.containsKey(playerName)) {
					long cd=colddowns.get(playerName);
					long interval=System.currentTimeMillis()-cd;
					long min=(900000-interval)/1000/60;
					long s=(900000-interval)/1000%60;
					if(plugin.marryRequests.containsKey(playerName) && interval<900000) {
						Util.sendMsg(player, "&c你已经存在一个还没被处理的求婚请求,若请求在 &e"+min+"分"+s+"秒 &c之后还没被处理,请求将会失效!");
						return true;
					}	
				}
				
				//金币支付
				if(args[2].equals("money")) {
					if(VaultEconomy.look(player.getName())<MainConfig.marrySpendMoney) {
						Util.sendMsg(player, "&c你至少需要 §e"+MainConfig.marrySpendMoney+"个 &c金币才能发出结婚请求!");
						return true;
					}
					plugin.marryRequests.put(playerName, new ProposeRequest(partnerName,PayType.MONEY));
					colddowns.put(playerName, System.currentTimeMillis());
					Util.sendMsg(partner, "&d"+playerName+" 现在向你求婚啦!");
					Util.sendMsg(partner, "");
					Util.sendRawMsg(partner,"[\"\",{\"text\":\"[CreeperMarriage] \",\"color\":\"green\"},{\"text\":\"点我接受\",\"color\":\"green\",\"underlined\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/cmarry accept "+playerName+"\"}},{\"text\":\" \",\"color\":\"none\",\"underlined\":false},{\"text\":\"点我拒绝\",\"color\":\"red\",\"underlined\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/cmarry deny "+playerName+"\"}}]");
					Util.sendMsg(partner, "");
					Util.sendMsg(player, "结婚请求已发出,有效时间: 15min!");
					return true;
				}
				//点券支付
				if(args[2].equals("points")) {
					if(PlayerPointsEconomy.look(player.getName())<MainConfig.marrySpendPoints) {
						Util.sendMsg(player, "&c你至少需要 §e"+MainConfig.marrySpendPoints+"个 &c点券才能发出结婚请求!");
						return true;
					}					
					plugin.marryRequests.put(playerName, new ProposeRequest(partnerName,PayType.POINTS));
					colddowns.put(playerName, System.currentTimeMillis());
					Util.sendMsg(partner, "&d"+playerName+" 现在向你求婚啦!");
					Util.sendMsg(partner, "");
					Util.sendRawMsg(partner,"[\"\",{\"text\":\"[CreeperMarriage] \",\"color\":\"green\"},{\"text\":\"点我接受\",\"color\":\"green\",\"underlined\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/cmarry accept "+playerName+"\"}},{\"text\":\" \",\"color\":\"none\",\"underlined\":false},{\"text\":\"点我拒绝\",\"color\":\"red\",\"underlined\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/cmarry deny "+playerName+"\"}}]");
					Util.sendMsg(partner, "");
					Util.sendMsg(player, "结婚请求已发出,有效时间: 15min!");
					return true;
				}
			}
		}
		return false;
	}

}
