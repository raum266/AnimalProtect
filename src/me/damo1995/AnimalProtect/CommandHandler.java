package me.damo1995.AnimalProtect;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class CommandHandler implements CommandExecutor{

private AnimalProtect plugin; 

public CommandHandler(AnimalProtect plugin){
this.plugin = plugin;
}
public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
    	if(commandLabel.equalsIgnoreCase("animalprotect")){
    		if(args.length < 1){
				sender.sendMessage(ChatColor.YELLOW + "+++++++++AnimalProtect++++++++++");
				sender.sendMessage(ChatColor.GREEN + "An animal friendly plugin");
				sender.sendMessage(ChatColor.RED + "+ Version: " + plugin.getDescription().getVersion());
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "+ Developer: " + plugin.getDescription().getAuthors());
				sender.sendMessage(ChatColor.AQUA + "http://www.dev.bukkit.org/server-mods/animalprotect");
				sender.sendMessage(ChatColor.YELLOW + "+++++++++++++++++++++++++++++");
				return true;
    		}
    		if(args[0].equalsIgnoreCase("-reload") && sender.isOp() || sender.hasPermission("animalprotect.admin")){
    			//reload config stuff.
    			plugin.reloadConfig();
    			//Set string on reload of config.
    			plugin.validateConfig();
    			sender.sendMessage(plugin.success + "Configuration Reloaded!");
    			return true;
    		}
    		if(args[0].equalsIgnoreCase("-list") && args[1].equalsIgnoreCase("player") && sender.isOp() || sender.hasPermission("animalprotect.list")){
    			List<String> pfp = plugin.getConfig().getStringList("protect-from-player");
    			sender.sendMessage(plugin.success + "The following are protected from players");
    			for(String i : pfp){
    				sender.sendMessage(i);
    			}
    		}
    		if(args[0].equalsIgnoreCase("-list") && args[1].equalsIgnoreCase("mobs") && sender.isOp() || sender.hasPermission("animalprotect.list")){
    			List<String> pfp = plugin.getConfig().getStringList("protect-from-monsters");
    			sender.sendMessage(plugin.success + "The following are protected from mobs");
    			for(String i : pfp){
    				sender.sendMessage(i);
    				}
    		}
    		else{ sender.sendMessage(plugin.cmdFail);
    		return true;
    		}
    	}
    	return false; 
    }
	}