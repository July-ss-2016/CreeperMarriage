package vip.creeper.mcserverplugins.creepermarriage.utils;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import vip.creeper.mcserverplugins.creepertitleapi.CreeperTitleApi;
import vip.creeper.mcserverplugins.creepertitleapi.CreeperTitleApi.TitleType;


public class Util {
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
	public static final String HEADMESSAGE="§a[CreeperMarriage] §b";
	public static final String SERVERVERSION=Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	public static void info(final String text) {
		Bukkit.getConsoleSender().sendMessage(HEADMESSAGE+text);
	}
	public static String dateToStr(final Date date) {
		return date!=null?sdf.format(date):null;
	}
	public static Date strToDate(final String str) {
		try {
			return str!=null?sdf.parse(str):null;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static void sendMsg(final CommandSender cs,final String msg) {
		if(cs!=null) {
			cs.sendMessage(HEADMESSAGE+ChatColor.translateAlternateColorCodes('&',msg));
		}
	}
	public static void sendRawMsg(final Player player,final String json) {
		if(player==null) {
			return;
		}
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw "+player.getName()+" "+json);
	}
	public static void broadcastTitle(final TitleType type,final String text) {
		CreeperTitleApi.broadcastTitle(type, ChatColor.translateAlternateColorCodes('&', text), 100, 100, 100);
	}
	//燃放烟花
	public static void detonateFirework(Entity entity) {
		Location loc=entity.getLocation();
		loc.setY(loc.getY()+3);
		Firework firework=(Firework)loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		FireworkMeta fireworkMeta=firework.getFireworkMeta();
		//添加效果
		fireworkMeta.addEffect(FireworkEffect
				.builder()
				.with(FireworkEffect.Type.CREEPER)
				.withColor(Color.RED)
				.build());
		fireworkMeta.setPower(2);
		firework.setFireworkMeta(fireworkMeta);
	}
	public static String translateColorCodes(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
}
