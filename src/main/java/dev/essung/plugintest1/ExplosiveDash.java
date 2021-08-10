package dev.essung.plugintest1;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.UUID;

public class ExplosiveDash implements Listener {
    Plugin plugin;
    HashSet<UUID> dashingPlayers;



    public ExplosiveDash(Plugin plugin) {
        this.plugin = plugin;
        this.dashingPlayers = new HashSet<UUID>();
    }

    //när man trycker sneakar i luften ge en en väldigt låg i den riktningen man är påväg och hög fart ner
    @EventHandler
    public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        Location hitLocation = player.getLocation();

        if (event.isSneaking() && !player.isFlying()){
            this.dashingPlayers.add(player.getUniqueId());
            player.setVelocity(player.getVelocity().add(player.getLocation().getDirection().multiply(5)));
        } else {
            this.dashingPlayers.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        Location hitLocation = player.getLocation();
        if (this.dashingPlayers.contains(player.getUniqueId()) && !player.isFlying() && player.getFallDistance() <= 1 && hitLocation.getWorld() != null) {
            hitLocation.getWorld().createExplosion(hitLocation, 4, true, true, (Entity) player);
            this.dashingPlayers.remove(player.getUniqueId());
        }
    }
}
