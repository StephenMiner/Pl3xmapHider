package me.stephenminer.pl3xmapaddon;

import net.pl3x.map.core.player.Player;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;
import java.util.Iterator;

public class HidePlayerManager extends BukkitRunnable{
    private final Pl3xmapAddon plugin;
    public HidePlayerManager(){
        super();
        this.plugin = JavaPlugin.getPlugin(Pl3xmapAddon.class);
    }
    @Override
    public void run() {
        plugin.getServer().getOnlinePlayers().forEach((bukkitPlayer)->{
            Player mapPlayer = plugin.playerRegister.get(bukkitPlayer.getUniqueId());
            if (mapPlayer != null){
                PlayerInventory inv = bukkitPlayer.getInventory();
                boolean handCheck = checkHands(inv.getItemInMainHand(),inv.getItemInOffHand());
                boolean yCheck = checkY(bukkitPlayer.getLocation());
                if (handCheck || yCheck){
                    if (!mapPlayer.isHidden()) mapPlayer.setHidden(true, false);
                }else if (mapPlayer.isHidden()) mapPlayer.setHidden(false,false);
                /*
                //hands good but above hiding y-level
                if (handCheck && !yCheck){
                    if (!mapPlayer.isHidden()) mapPlayer.setHidden(true, false);
                }
                //Above y-level and fail hand check
                if (!handCheck && !yCheck){
                    if (mapPlayer.isHidden()) mapPlayer.setHidden(false, false);
                }
                //Fail hand check but below y-level
                if (!handCheck && yCheck){
                    if (!mapPlayer.isHidden()) mapPlayer.setHidden(true, false);
                }
                if (handCheck && yCheck){
                    if (!mapPlayer.isHidden()) mapPlayer.setHidden(true, false);
                }

                 */


            }
        });
    }

    /**
     *
     * @param main
     * @param off
     * @return true if player has any combination of diamond sword and compass in their main and off hands, false if otherwise
     */
    private boolean checkHands(@Nonnull ItemStack main, @Nonnull ItemStack off){
        return ((main.getType() == Material.COMPASS  && off.getType() == Material.DIAMOND_SWORD)
                || (main.getType() == Material.DIAMOND_SWORD && off.getType() == Material.COMPASS));
    }

    private boolean checkY(Location loc){
        return loc.getY() <= plugin.hideBelowY();
    }
}
