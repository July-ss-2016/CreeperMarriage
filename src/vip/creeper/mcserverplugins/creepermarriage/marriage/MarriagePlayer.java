package vip.creeper.mcserverplugins.creepermarriage.marriage;

import java.io.File;
import java.util.Date;

import org.bukkit.configuration.file.YamlConfiguration;

import vip.creeper.mcserverplugins.creepermarriage.managers.MarriageManager;
import vip.creeper.mcserverplugins.creepermarriage.utils.Util;



public class MarriagePlayer {
	private File playerFile;
	private File partnerFile;
	private YamlConfiguration playerYaml;
	private YamlConfiguration partnerYaml;
	private boolean isEmptyMarriagePlayer;
	private String name;
	private String partnerName;
	private Date marriedDate;
	public MarriagePlayer(String name) {
		if(name==null) {
			return;
		}
		this.name=name;
		//伴侣的数据文件
		this.partnerFile=new File(MarriageManager.playerDataFolder.getAbsolutePath()+File.separator+this.partnerName+".yml");
		//该玩家的数据文件
		this.playerFile=new File(MarriageManager.playerDataFolder.getAbsolutePath()+File.separator+name+".yml");
		//判断是否真的存在这个MarriagePlayer
		this.isEmptyMarriagePlayer=!playerFile.exists();
		if(isEmptyMarriagePlayer) {
			return;
		}
		this.playerYaml=YamlConfiguration.loadConfiguration(this.playerFile);
		this.partnerYaml=YamlConfiguration.loadConfiguration(new File(MarriageManager.playerDataFolder.getAbsolutePath()+File.separator+this.partnerName+".yml"));
		this.partnerName=playerYaml.getString("partnerName");
		this.marriedDate=Util.strToDate(playerYaml.getString("marriedDate"));
		//this.sex=SexType.valueOf(playerYaml.getString("playerSex"));
	}
	public boolean isEmpty() {
		return this.isEmptyMarriagePlayer;
	}
	public String getName() {
		return this.name;
	}
	//得到同伴的名字
	public String getPartnerName() {
		return this.partnerName;
	}
	//得到结婚日期
	public Date getMarriedDate() {
		return this.marriedDate;
	}
	//离婚
	public boolean divorce() {
		boolean result=false;
		if(!isEmptyMarriagePlayer) {
			String partnerName=getPartnerName();
			playerFile.delete();
			partnerFile.delete();
			MarriageManager.removePlayerFromMarriedPlayerList(this.name);
			MarriageManager.removePlayerFromMarriedPlayerList(partnerName);
			result=true;
		}
		return result;
	}
	//得到同伴最后下线时间
	public Date getPartnerLastLoginTime() {
		return isEmptyMarriagePlayer?null:Util.strToDate(partnerYaml.getString("lastLoginTime"));
	}
	//得到婚龄
	public long getMarriageAge() {
		return isEmptyMarriagePlayer?-1:(new Date().getTime()-this.marriedDate.getTime())/(1000*60*60*24)+1;
	}
}
