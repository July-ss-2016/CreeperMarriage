package vip.creeper.mcserverplugins.creepermarriage.commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import vip.creeper.mcserverplugins.creepermarriage.utils.Util;

public class BaseCommandHandler implements CommandExecutor {
	private static HashMap<String,ICommand> commands=new HashMap<String,ICommand>();
	static {
		commands.put("accept", new AcceptCommand());
		commands.put("div", new DivorceCommand());
		commands.put("info", new InfoCommand());
		commands.put("pro", new ProposeCommand());
		commands.put("tp", new TpCommand());
		commands.put("help", new HelpCommand());
		commands.put("deny", new DenyCommand());
		commands.put("list", new ListCommand());
	}
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String lable, String[] args) {
		if(args.length>=1) {
			String lowerArg=args[0].toLowerCase();
			if(!cs.hasPermission("CreeperMarriage.use")) {
				Util.sendMsg(cs, "&c你没有权限!");
				return true;
			}
			if(commands.containsKey(lowerArg)) {
				if(!commands.get(lowerArg).execute(cs, cmd, lable, args)) {
					Util.sendMsg(cs, "&c指令有误!");
					commands.get("help").execute(cs, cmd, lable, args);
				}
				return true;
			}
		}
		Util.sendMsg(cs, "&c指令有误!");
		commands.get("help").execute(cs, cmd, lable, args);
		return true;
	}
	
}
