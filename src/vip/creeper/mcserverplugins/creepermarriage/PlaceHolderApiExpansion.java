package vip.creeper.mcserverplugins.creepermarriage;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import vip.creeper.mcserverplugins.creepermarriage.caches.MarriageCacheManager;

import org.bukkit.entity.Player;
public class PlaceHolderApiExpansion extends EZPlaceholderHook {

	public PlaceHolderApiExpansion(final Main plugin) {
		super(plugin, "creepermarriage");
	}

	@Override
	public String onPlaceholderRequest(final Player player, final String str) {
		String playerName=player.getName();
		String result=null;
		switch(str) {
		case "ismarried":
			result=MarriageCacheManager.getMarriagePlayer(player.getName()).isEmpty()?"&f[未婚]":"&d[已婚]";
			break;
		case "partner":
			String partner=MarriageCacheManager.getMarriagePlayer(playerName).getPartnerName();
			result=partner==null?"无":partner;
			break;
		}
		return result;
	}
	
}