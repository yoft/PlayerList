package yoft.PlayerList;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerListCommandExecutor implements CommandExecutor {
	
	private PlayerList plugin;
	
	public PlayerListCommandExecutor(PlayerList plugin) {
		this.plugin = plugin;
	}


	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("players")){
			List<String> list = new ArrayList<String>();
			int players = 0;
			String message;
			World world = null;
			
			if (args.length == 0){
				if (sender instanceof Player)
					world = ((Player) sender).getWorld();
			}else if (args.length == 1){
				if (!args[0].equalsIgnoreCase("all")){
					if (plugin.getServer().getWorld(args[0]) != null){
						world = plugin.getServer().getWorld(args[0]);
					}else{
						if (sender instanceof Player){
							sender.sendMessage(ChatColor.RED + "\"" + args[0] + "\" is not a valid world.");
						}else{
							sender.sendMessage("\"" + args[0] + "\" is not a valid world.");
						}
						return true;
					}
				}
			}else{
				sender.sendMessage(ChatColor.RED + "Wrong number of arguments.");
				return true;
			}
			
			if (world == null){
				for (Player player : plugin.getServer().getOnlinePlayers()){
					if (!plugin.hidden.contains(player.getName())){
						list.add(player.getDisplayName());
					}else{
						if (!plugin.hideNumbers)
							players+=1;
					}
				}
				
				message = "connected to the server";
			}else{
				for (Player player : world.getPlayers()){
					if (!plugin.hidden.contains(player.getName())){
						list.add(player.getDisplayName());
					}else{
						if (!plugin.hideNumbers)
							players+=1;
					}
				}
				
				message = "in \"" + world.getName() + "\"";
			}
			
			players += list.size();
			
			if (players == 0){
				message = ChatColor.GOLD + "There is no one " + message + ".";
			}else if (players == 1){
				message = ChatColor.GOLD + "The only player " + message + " is: ";
			}else{
				message = ChatColor.GOLD + "The " + players + " players " + message + " are: ";
			}
			
			for (String name : list){
				if (list.indexOf(name) != list.size()-1){
					message = message + ChatColor.WHITE + name + ChatColor.GOLD + ", ";
				}else{
					message = message + ChatColor.WHITE + name + ChatColor.GOLD + ".";
				}
			}
			
			if (!plugin.tellAll){
				if (sender instanceof Player){
					sender.sendMessage(message);
				}else{
					System.out.println(ChatColor.stripColor(message));
				}
				return true;
			}
			
			if (world != null){
				for (Player player : world.getPlayers())
					player.sendMessage(message);
				
				if (sender instanceof Player){
					if (((Player) sender).getWorld() != world)
						sender.sendMessage(message);
				}
				
			}else{
				plugin.getServer().broadcastMessage(message);
			}
			
			System.out.println(ChatColor.stripColor(message));
		}
		return true;
	}

}
