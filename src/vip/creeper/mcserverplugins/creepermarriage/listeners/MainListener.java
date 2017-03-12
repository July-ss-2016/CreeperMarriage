package vip.creeper.mcserverplugins.creepermarriage.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import vip.creeper.mcserverplugins.creepermarriage.MarriageItem;
import vip.creeper.mcserverplugins.creepermarriage.MarriagePlayer;
import vip.creeper.mcserverplugins.creepermarriage.caches.MarriageCacheManager;
import vip.creeper.mcserverplugins.creepermarriage.managers.MarriageManager;
import vip.creeper.mcserverplugins.creepermarriage.utils.Util;


public class MainListener implements Listener {
	//背包点击事件
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		Player player=(Player)event.getWhoClicked();
		InventoryAction act=event.getAction();
		String title=event.getInventory().getTitle();
		ItemStack item=event.getCurrentItem();
		
		if(item==null) {
			return;
		}
		if(title.startsWith("§c§l虐狗榜")) {
			event.setCancelled(true);
			event.setResult(Result.DENY);
			int page=Integer.valueOf(title.substring(title.indexOf("#")+1, title.length()));
			if(item.equals(MarriageItem.ITEM_LASTPAGE)) {
				if(act==InventoryAction.PICKUP_ALL) {
					player.performCommand("cmarry list "+(page+1));						
					return;
				}
				if(act==InventoryAction.PICKUP_HALF) {
					player.performCommand("cmarry list "+(page-1));	
					return;		
				}
			}
			
			if(item.equals(MarriageItem.ITEM_NEXTPAGE)) {
				player.performCommand("cmarry list "+(page+1));	
				return;						
			}
			
			if(item.equals(MarriageItem.ITEM_LASTPAGE_AND_NEXTPAGE)) {
				if(act==InventoryAction.PICKUP_ALL) {
					player.performCommand("cmarry list "+(page+1));	
					return;
				}
				if(act==InventoryAction.PICKUP_HALF) {
					player.performCommand("cmarry list "+(page-1));
					return;
				}	
			}
		}
	}
	//上线事件
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) throws Exception {
		MarriageManager.updateMarriagePlayerLastLoginTime(event.getPlayer().getName());
	}
	//下线事件
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event) {	
		MarriageManager.updateMarriagePlayerLastLoginTime(event.getPlayer().getName());
	}
	//Fuck事件
	@EventHandler
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
		Player player=event.getPlayer();
		String playerName=player.getName();
		Entity entity=event.getRightClicked();
		//判断是否在潜行
		if(!player.isSneaking()) {
			return;
		}
		if((entity instanceof Player)) {
			Player partner=(Player)entity;
			if(MarriageManager.isMarriedPlayer(playerName)) {
				MarriagePlayer marriedPlayer=MarriageCacheManager.getMarriagePlayer(playerName);
				if(marriedPlayer.getPartnerName().equals(partner.getName())) {
					Location loc=partner.getLocation();
					loc.setY(loc.getY()+3);
					partner.getWorld().playEffect(loc, Effect.HEART, 3);
					Util.sendMsg(player, "a~ a~ a~");
					Util.sendMsg(player, "a~ a~ a~");
				}	
			}
		}
	}
}
		

