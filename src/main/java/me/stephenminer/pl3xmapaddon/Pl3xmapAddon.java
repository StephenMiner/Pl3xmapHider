package me.stephenminer.pl3xmapaddon;

import net.pl3x.map.bukkit.Pl3xMapBukkit;
import net.pl3x.map.bukkit.Pl3xMapImpl;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.player.PlayerRegistry;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class Pl3xmapAddon extends JavaPlugin {
    public Pl3xMap api;
    public ConfigFile settings;
    public PlayerRegistry playerRegister;
    public BukkitTask manager;
    @Override
    public void onEnable() {
        api  = Pl3xMapImpl.api();
        playerRegister = api.getPlayerRegistry();
        this.settings = new ConfigFile(this, "settings");
        addCommands();
        reloadManager();
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        manager.cancel();
        // Plugin shutdown logic
    }
    private void addCommands(){
        getCommand("reloadmaphider").setExecutor(new ReloadCommand());
    }

    public int hideBelowY(){
        return this.settings.getConfig().getInt("hide-below-y");
    }
    public int checkingPeriod(){
        return this.settings.getConfig().getInt("checking-period");
    }

    public void reloadManager(){
        if (manager != null){
            if (!manager.isCancelled()) manager.cancel();
            manager = new HidePlayerManager().runTaskTimer(this, 0,checkingPeriod());
        }else manager = new HidePlayerManager().runTaskTimer(this, 0,checkingPeriod());
    }
}
