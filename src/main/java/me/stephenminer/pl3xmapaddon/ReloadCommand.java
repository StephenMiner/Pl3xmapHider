package me.stephenminer.pl3xmapaddon;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ReloadCommand implements CommandExecutor {
    private final Pl3xmapAddon plugin;

    public ReloadCommand(){
        this.plugin = JavaPlugin.getPlugin(Pl3xmapAddon.class);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (sender instanceof Player player){
            if (!player.hasPermission("mapaddon.reload")) {
                player.sendMessage(ChatColor.RED + "No permission");
                return false;
            }
        }
        plugin.settings.reloadConfig();
        plugin.reloadManager();
        sender.sendMessage(ChatColor.GREEN + "Reloaded map hider");
        return true;
    }

}
