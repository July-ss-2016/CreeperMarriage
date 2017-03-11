package vip.creeper.mcserverplugins.creepermarriage.marriage;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MarriageItem {
	public static final ItemStack ITEM_LASTPAGE_AND_NEXTPAGE;
	public  static final ItemStack ITEM_LASTPAGE;
	public static final ItemStack ITEM_NEXTPAGE;
	static {
		ITEM_LASTPAGE_AND_NEXTPAGE=new ItemStack(Material.BOOK);
		ITEM_LASTPAGE=new ItemStack(Material.BOOK);
		ITEM_NEXTPAGE=new ItemStack(Material.BOOK);
		ItemMeta lastAndNextPageMeta=ITEM_LASTPAGE_AND_NEXTPAGE.getItemMeta();
		ItemMeta lastPageMeta=ITEM_LASTPAGE.getItemMeta();
		ItemMeta nextPageMeta=ITEM_NEXTPAGE.getItemMeta();
		lastAndNextPageMeta.setDisplayName("§e翻页");
		lastPageMeta.setDisplayName("§e翻页");
		nextPageMeta.setDisplayName("§e翻页");
		List<String> lastAndNextPageLores=new ArrayList<String>();
		List<String> lastPageLores=new ArrayList<String>();
		List<String> nextPageLores=new ArrayList<String>();
		lastAndNextPageLores.add("§7- §f左键=下一页");
		lastAndNextPageLores.add("§7- §f右键=上一页");
		lastPageLores.add("§7- §f右键=上一页");
		nextPageLores.add("§7- §f左键=下一页");
		lastAndNextPageMeta.setLore(lastAndNextPageLores);
		lastPageMeta.setLore(lastPageLores);
		nextPageMeta.setLore(nextPageLores);
		ITEM_LASTPAGE_AND_NEXTPAGE.setItemMeta(lastAndNextPageMeta);
		ITEM_LASTPAGE.setItemMeta(lastPageMeta);
		ITEM_NEXTPAGE.setItemMeta(nextPageMeta);
	}
}
