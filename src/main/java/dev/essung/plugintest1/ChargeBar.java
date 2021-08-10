package dev.essung.plugintest1;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ChargeBar extends BukkitRunnable{
    Plugin plugin;
    ExplosiveSword explosiveSword;
    public ChargeBar(Plugin plugin, ExplosiveSword explosiveSword) {
        this.plugin = plugin;
        this.explosiveSword = explosiveSword;
    }

    @Override
    public void run(){
        for (Player p : Bukkit.getOnlinePlayers()) {
            String itemUUID = p.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(NamespacedKey.minecraft("explosivestuff-uuid"), PersistentDataType.STRING);
            long timeNow = System.currentTimeMillis();
            if(explosiveSword.cooldDown.containsKey(itemUUID)) {
                if (true){
                    Integer progress = (int)(3000L/(timeNow - explosiveSword.cooldDown.get(itemUUID)));
                    String progressBar = "[";
                    for (int i = 0; i<(10-progress); i++){
                        progressBar = progressBar + "#";
                    };
                    for (int i = 0; i<(progress > 10 ? 10 : progress); i++){
                        progressBar = progressBar + "-";
                    }
                    progressBar = progressBar + "]";

                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                            TextComponent.fromLegacyText(progressBar));
                }
            }else{
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                            TextComponent.fromLegacyText(""));
            }
        }
    }
}