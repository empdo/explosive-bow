package dev.essung.plugintest1;

import org.bukkit.plugin.Plugin;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalTime;

import java.util.HashMap;
import java.util.UUID;

import javax.print.DocFlavor.BYTE_ARRAY;
import javax.xml.crypto.Data;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.persistence.PersistentDataType;


public class ExplosiveSword implements Listener {
    Plugin plugin;
    ChargeBar chargeBar;
    public HashMap<String, Long> cooldDown = new HashMap<>();

    public ExplosiveSword(Plugin plugin) {
        this.plugin = plugin;
        this.chargeBar = new ChargeBar(this.plugin, this);
        startSchedule();
    }

    @EventHandler
    public void onSwing(PlayerInteractEvent event) {
        long timeNow = System.currentTimeMillis();
        String itemUUID = event.getItem().getItemMeta().getPersistentDataContainer().get(NamespacedKey.minecraft("explosivestuff-uuid"),PersistentDataType.STRING);
        if (!cooldDown.containsKey(itemUUID) || (timeNow - cooldDown.get(itemUUID) > 3000L)) {
            Player p = event.getPlayer();
            if (event.getItem().getType() == Material.DIAMOND_SWORD) {
                for (int x = 1; x < 24; x++) {
                    Location loc = p.getLocation().getDirection().multiply(x * 2).add(p.getEyeLocation().toVector())
                            .toLocation(p.getWorld());
                    loc.getWorld().createExplosion(loc, 2, false);
                }
                cooldDown.put(itemUUID, timeNow);
            }
            
        }

    }

    public void startSchedule(){
        this.chargeBar.runTaskTimer(plugin, 0L, 2L);
    }

/*    public void onHold(PlayerItemHeldEvent event) {
        if (event != null) {
            ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
            int timeNow = LocalTime.now().toSecondOfDay();
            if (cooldDown.containsKey(item)) {
                if ((timeNow - cooldDown.get(item)) < 3){
                    Integer progress = (int)(Math.floor((1 / (timeNow - cooldDown.get(item)))));
                    
                    //String progressBar = "["+("#".repeat(progress) + "-".repeat(10-progress))+"]";
                    String progressBar = "[";
                    for (int i = 0; i<(progress *10); i++){
                        progressBar = progressBar + "#";
                    };
                    for (int i = 0; i<(10-progress); i++){
                        progressBar = progressBar + "-";
                    }
                    progressBar = progressBar + "]";
                    
                    event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,
                            TextComponent.fromLegacyText(progressBar));
                    //return;
                }
            }
        }*/
            //event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,
              //              TextComponent.fromLegacyText(""));
    //}
}

// function
// timer på en stund