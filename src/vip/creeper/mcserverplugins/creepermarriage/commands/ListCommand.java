package vip.creeper.mcserverplugins.creepermarriage.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import vip.creeper.mcserverplugins.creepermarriage.Main;
import vip.creeper.mcserverplugins.creepermarriage.MarriageItem;
import vip.creeper.mcserverplugins.creepermarriage.MarriagePlayer;
import vip.creeper.mcserverplugins.creepermarriage.managers.MarriageManager;
import vip.creeper.mcserverplugins.creepermarriage.utils.Util;

public class ListCommand implements ICommand {
	private static Main plugin=Main.getInstance();
	private static List<Inventory> marriagePlayerListInv=new ArrayList<Inventory>();
	private static long lastInvUpdateTime=0;
	static {
		updateMarriagePlayerListInv();
		lastInvUpdateTime=System.currentTimeMillis();
	}
	private static void updateMarriagePlayerListInv() {
		List<MarriagePlayer> marriedPlayers=MarriageManager.getMarriedPlayers();
		List<Inventory> tempMarriagePlayerListInv=new ArrayList<Inventory>();
		//玩家总数
		int marriedPlayerSize=marriedPlayers.size();
		//总页数
		int pageCount=(marriedPlayerSize%53==0?marriedPlayerSize/53:marriedPlayerSize/53+1);
		//物品总索引计次
		int listIndex=0;
		for(int o=0;o<pageCount;o++) {
			Inventory tempInv=Bukkit.createInventory(null, 54, "§c§l虐狗榜 ❤ #"+(o+1));
			for(int i=0;i<53;i++) {
				if(listIndex>marriedPlayerSize-1) {
					break;
				}
				MarriagePlayer mp=marriedPlayers.get(listIndex);
				String playerName=mp.getName();
				String partnerName=mp.getPartnerName();
				ItemStack head=new ItemStack(Material.SKULL_ITEM);
				head.setDurability((short)3);
				SkullMeta skullMeta=(SkullMeta)head.getItemMeta();
				skullMeta.setOwner(mp.getName());
				skullMeta.setDisplayName("§f"+playerName+" §c❤ §f"+partnerName);
				List<String> lores=new ArrayList<String>();
				lores.add(Util.translateColorCodes("&7- &d情侣 &7> &d"+partnerName));
				lores.add(Util.translateColorCodes("&7- &d婚龄 &7> &d"+mp.getMarriageAge()));
				lores.add(Util.translateColorCodes("&7- &d结婚日期 &7> &d"+Util.dateToStr(mp.getMarriedDate())));
				skullMeta.setLore(lores);
				head.setItemMeta(skullMeta);
				tempInv.setItem(i, head);
				listIndex++;
			}
			//设置页数Item
			if(o==0 && pageCount>1) {
				tempInv.setItem(53, MarriageItem.ITEM_NEXTPAGE);
			} else if(o>0 && o==pageCount-1) {
				tempInv.setItem(53, MarriageItem.ITEM_LASTPAGE);
			} else if(o>0 && o<pageCount-1) {
				tempInv.setItem(53, MarriageItem.ITEM_LASTPAGE_AND_NEXTPAGE);				
			}
			tempMarriagePlayerListInv.add(tempInv);
		}
		//赋值更新
		marriagePlayerListInv=tempMarriagePlayerListInv;
	}
	@Override
	public boolean execute(CommandSender cs, Command cmd, String lable, String[] args) {
		Player player=(Player)cs;
		if(marriagePlayerListInv.size()==0) {
			Util.sendMsg(player, "&c当前没有任何一个玩家结婚!");
			return true;
		}
		if(args.length==1) {
			player.openInventory(marriagePlayerListInv.get(0));
			return true;
		}
		if(args.length==2) {
			if(args[1].matches("[0-9]*")) {
				int page=Integer.valueOf(args[1]);
				if(page<=0) {
					Util.sendMsg(player, "&c索引必须>0.");
					return true;
				}
				
				if(page>marriagePlayerListInv.size()) {
					player.openInventory(marriagePlayerListInv.get(0));
				} else {
					player.openInventory(marriagePlayerListInv.get(page-1));	
				}
				//判断更新list
				if(System.currentTimeMillis()-lastInvUpdateTime>600000) {
					Runnable syncRunnable=new Runnable() {
						@Override
						public void run() {
							updateMarriagePlayerListInv();
						}
					};
					//启动异步线程更新
					Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
						@Override
						public void run() {
							Bukkit.getScheduler().runTask(plugin, syncRunnable);
						}
					});
					//更新时间
					lastInvUpdateTime=System.currentTimeMillis();
				}
				return true;
			}
			Util.sendMsg(player, "&c你必须输入一个数字!");
			return true;
		}
		return false;
	}

}
