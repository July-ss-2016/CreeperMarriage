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

import vip.creeper.mcserverplugins.creepermarriage.MarriageItem;
import vip.creeper.mcserverplugins.creepermarriage.MarriagePlayer;
import vip.creeper.mcserverplugins.creepermarriage.managers.MarriageManager;
import vip.creeper.mcserverplugins.creepermarriage.utils.Util;

public class ListCommand implements ICommand {
	private static List<Inventory> marriagePlayerListInvs=new ArrayList<Inventory>();
	static {
		updateInvs();
	}
	private static void updateInvs() {
		List<MarriagePlayer> mps=MarriageManager.getMarriedPlayers();
		//mp总数
		int mpsSize=mps.size();
		//总页数
		int listIndex=0;
		int pageCount=(mpsSize%53==0?mpsSize/53:mpsSize/53+1);
		for(int o=0;o<pageCount;o++) {
			Inventory tempInv=Bukkit.createInventory(null, 54, "§c§l虐狗榜 ❤ #"+(o+1));
			for(int i=0;i<53;i++) {
				if(listIndex>mpsSize-1) {
					break;
				}
				MarriagePlayer mp=mps.get(listIndex);
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
			//设置pageItem
			if(o==0 && pageCount>1) {
				tempInv.setItem(53, MarriageItem.ITEM_NEXTPAGE);
			} else if(o>0 && o==pageCount-1) {
				tempInv.setItem(53, MarriageItem.ITEM_LASTPAGE);
			} else if(o>0 && o<pageCount-1) {
				tempInv.setItem(53, MarriageItem.ITEM_LASTPAGE_AND_NEXTPAGE);				
			}
			marriagePlayerListInvs.add(tempInv);
		}
	}
	@Override
	public boolean execute(CommandSender cs, Command cmd, String lable, String[] args) {
		Player player=(Player)cs;
		if(marriagePlayerListInvs.size()==0) {
			Util.sendMsg(player, "&c当前没有任何一个玩家结婚!");
			return true;
		}
		if(args.length==1) {
			player.openInventory(marriagePlayerListInvs.get(0));
			return true;
		}
		if(args.length==2) {
			if(args[1].matches("[0-9]*")) {
				int page=Integer.valueOf(args[1]);
				if(page<=0) {
					Util.sendMsg(player, "&c索引必须>0.");
					return true;
				}
				if(page>marriagePlayerListInvs.size()) {
					player.openInventory(marriagePlayerListInvs.get(0));
					return true;
				}
				player.openInventory(marriagePlayerListInvs.get(page-1));
				return true;
			}
			Util.sendMsg(player, "&c你必须输入一个数字!");
			return true;
		}
		return false;
	}

}
