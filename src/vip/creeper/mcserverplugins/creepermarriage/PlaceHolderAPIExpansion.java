package vip.creeper.mcserverplugins.creepermarriage;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import vip.creeper.mcserverplugins.creepermarriage.caches.MarriageCacheManager;
import vip.creeper.mcserverplugins.creepermarriage.configs.MainConfig;
import vip.creeper.mcserverplugins.creepermarriage.utils.Util;

import org.bukkit.entity.Player;
public class PlaceHolderAPIExpansion extends EZPlaceholderHook {

	public PlaceHolderAPIExpansion(final Main plugin) {
		super(plugin, "creepermarriage");
	}

	@Override
	public String onPlaceholderRequest(final Player player, final String str) {
		MarriagePlayer mp=MarriageCacheManager.getMarriagePlayer(player.getName());
		String result=null;
		switch(str) {
		case "ismarried":
			result=mp.isEmpty()?MainConfig.unMarriedPrefix:MainConfig.marriedPrefix;
			break;
		case "partner":
			String partner=mp.getPartnerName();
			result=partner==null?"无":partner;
			break;
		case "marriageage":
			result=String.valueOf(mp.getMarriageAge());
			break;
		case "marrieddate":
			result=Util.dateToStr(mp.getMarriedDate());
			if(result==null) {
				result="NULL";
			}
			break;
		case "partnerlastlogintime":
			result=Util.dateToStr(mp.getPartnerLastLoginTime());
			if(result==null) {
				result="NULL";
			}
			break;
		}
		return result;
	}
	
}