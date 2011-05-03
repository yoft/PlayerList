package yoft.PlayerList;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class PlayerList extends JavaPlugin{
	public Configuration config;
	public boolean tellAll = true;
	public boolean hideNumbers = false;
	public List<String> hidden = new ArrayList<String>();
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println("[" + pdfFile.getName() + "] has been disabled.");
	}
	
	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
	    System.out.println("[" + pdfFile.getName() + "] version [" + pdfFile.getVersion() + "] is enabled!");
	    
	    PlayerListCommandExecutor executor = new PlayerListCommandExecutor(this);
			this.getCommand("players").setExecutor(executor);
		
		config = this.getConfiguration();
		this.loadConfig();
	    
	}
	
	private void loadConfig() {
    	config.load();
    	
    	tellAll = config.getBoolean("TellAll", true);
    	hideNumbers = config.getBoolean("HideNumberOfPlayers", false);
		hidden = config.getStringList("HiddenPlayers", new ArrayList<String>());
    	
		this.saveConfig();
	}
    
    private void saveConfig() {
    	
    	config.setProperty("TellAll", tellAll);
    	config.setProperty("HideNumberOfPlayers", hideNumbers);
    	config.setProperty("HiddenPlayers", hidden);
    	
		config.save();
	}
    
}
