package me.damo1995.AnimalProtect;

import java.io.IOException;
import java.util.logging.Logger;


import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.MetricsLite;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class AnimalProtect extends JavaPlugin{
	Logger log = Logger.getLogger("Minecraft");

	String success = ChatColor.GREEN + "[AnimalProtect]: ";
	
	String fail = ChatColor.RED + "[AnimalProtect]: ";
	
	public boolean outdated = false;
	
	/*********************************************************\
	 * Language Localisation Stuff							 *
	 *														 *
	 * 														 *
	 *********************************************************/

	
	
	public String failMsg;
	public String cmdFail;
	public String adminNotifyMsg;
	protected Boolean isLatest;
	protected String latestVersion;
	
/**************************************************************/
  

//	public final DamageListeners dl = new DamageListeners(this);
	public final NewDamageListeners dl = new NewDamageListeners(this);
	public final InteractListener shear = new InteractListener(this);
	public final VersionCheck vc = new VersionCheck(this);
	public final UpdateChecker updateCheck = new UpdateChecker(this);
	//Enable stuff
	public void onEnable(){	
		
		// INIT STUFF //
		//event registration
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(dl, this);
		pm.registerEvents(vc, this);
		pm.registerEvents(shear, this);
		
		//Log msg
		this.logMessage("Enabled!");
		//Check for WorldGuard
		getWorldGuardPlugin();
		//CFG Setup
		setupConfig();
		//Check for Updates
		if(getConfig().getBoolean("update-check") == true){
			isLatest = updateCheck.isLatest();
			latestVersion = updateCheck.getUpdateVersion();
		}
		else{
			return;
		}
		//Check Config for any errors.
		validateConfig();
		collectStats();
		//Check for commands and such.
		this.getCommand("animalprotect").setExecutor(new CommandHandler(this));	
		
		this.failMsg = fail + getConfig().getString("FailMessage");
		this.cmdFail = fail + getConfig().getString("CommandFail");
		this.adminNotifyMsg = fail + getConfig().getString("AdminNotification");
	}
	
	public void collectStats(){
		try {
			MetricsLite metrics = new MetricsLite(this);
			metrics.start();
			this.logMessage("Collecting Stats");
			this.logMessage("If You do not wish for AnimalProtect to collect stats please set opt-out to true");
			} catch (IOException e) {
			this.logMessage("Coulden't submit stats!");
		}
		
	}
	
	public void onDisable(){
		//Log MSG disabled.
		this.logMessage("Disabled!");
		this.getServer().getPluginManager().disablePlugin(this);
		
	}
	
	//WorldGuard Check
	public WorldGuardPlugin getWorldGuardPlugin(){
		Plugin plugin = this.getServer().getPluginManager().getPlugin("WorldGuard");
		PluginManager pm = this.getServer().getPluginManager();
		if(plugin == null || !(plugin instanceof WorldGuardPlugin)){
			//warn WG was not found.
			logWarning("WorldGuard Plugin Not found!");
			logWarning("AnimalProtect Disabled!");
			//Disable the plugin.
			pm.disablePlugin(this);
			return null;
		}
		return (WorldGuardPlugin) plugin;
	}
	
	
	//Log Message to console stuff
	protected void logMessage(String msg){
	PluginDescriptionFile pdFile = this.getDescription();
	log.info(pdFile.getName() + " " + pdFile.getVersion() + " : " + msg);
	
	}
	//Warn for WorldGuard not found.
	protected void logWarning(String msg){
		PluginDescriptionFile pdFile = this.getDescription();
		log.warning(pdFile.getName() + " " + pdFile.getVersion() + " : " + msg);
	}
	//Configuration setup
	private void setupConfig(){
		final FileConfiguration cfg = getConfig();
		FileConfigurationOptions cfgOptions = cfg.options();
		this.saveDefaultConfig();
		cfgOptions.copyDefaults(true);
		cfgOptions.header("Default Config for AnimalProtect");
		cfgOptions.copyHeader(true);
		saveConfig();
	}
	
	public void validateConfig(){
		if(this.getConfig().getInt("notify-interval") > 20){
			this.logWarning("Notify interval greater then 20");
			this.logMessage("Notify Interval set to 20");
			this.getConfig().set("notify-interval", 20);
			this.saveConfig();
		}
	}

}
