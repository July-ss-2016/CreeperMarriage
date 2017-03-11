package vip.creeper.mcserverplugins.creepermarriage;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import org.black_ixx.bossshop.BossShop;
import org.black_ixx.bossshop.api.BossShopAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import vip.creeper.mcserverplugins.creepermarriage.commands.BaseCommandHandler;
import vip.creeper.mcserverplugins.creepermarriage.configs.MainConfig;
import vip.creeper.mcserverplugins.creepermarriage.economies.PlayerPointsEconomy;
import vip.creeper.mcserverplugins.creepermarriage.economies.VaultEconomy;
import vip.creeper.mcserverplugins.creepermarriage.listeners.MainListener;
import vip.creeper.mcserverplugins.creepermarriage.managers.MarriageManager;
import vip.creeper.mcserverplugins.creepermarriage.utils.Util;

public class Main extends JavaPlugin {
	public  HashMap<String,ProposeRequest> marryRequests=new HashMap<String,ProposeRequest>();
	private static Main instance;
	private static BossShopAPI bsApi;
	
	public static Main getInstance() {
		return instance;
	}
	public BossShopAPI getBsApi() {
		return bsApi;
	}
	public void onEnable() {
		//实例赋值
		instance=this;
		//载入配置
		MainConfig.loadConfig();
		getLogger().info("配置载入完毕!");
		//创建目录
		File playersDataFile=new File(getDataFolder().getAbsolutePath()+File.separator+"data"+File.separator+"players");
		if(!playersDataFile.exists()) {
			playersDataFile.mkdirs();
			getLogger().info("data/players 目录创建完毕!");
		}
		if(!MarriageManager.marriedPlayerFile.exists()) {
			try {
				MarriageManager.marriedPlayerFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			getLogger().info("data/marriedplayers.yml 文件创建完毕!");
		}
		//hook插件
		if(!VaultEconomy.hookVaultEconomy() || !PlayerPointsEconomy.hookPlayerPointsEconomy()) {
			getLogger().info("Hook Vault/PlayerPoints 失败!");
			getLogger().info("插件将被卸载!");
			setEnabled(false);
		}
		if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			new PlaceHolderApiExpansion(this).hook();
			getLogger().info("PlaceholderAPI Hook 成功!");
		}
		if(Bukkit.getPluginManager().isPluginEnabled("BossShop")) {
			bsApi=((BossShop)Bukkit.getPluginManager().getPlugin("BossShop")).getAPI();
			getLogger().info("BossShop Hokk 成功!");			
		}
		//注册命令
		getCommand("cmarry").setExecutor(new BaseCommandHandler());
		getLogger().info("命令注册完毕!");
		Bukkit.getPluginManager().registerEvents(new MainListener(), this);
		getLogger().info("事件注册完毕!");
		Util.info("插件初始化完毕!");
		test();
	}
	public void onDisable() {
		Util.info("插件已被卸载!");
	}
	public void test() {
		for(int i=1;i<=500;i++) {
			//MarriageManager.createMarriagePlayer(i+"", i+"", new Date());
		}
	}
}
