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
            long timeThen = explosiveSword.cooldDown.get(itemUUID);
            if(explosiveSword.cooldDown.containsKey(itemUUID)) {
                if (timeThen > 3000){
                    //int form 0 to 10 describing the progress from 0-100 %
                    Integer progress = (int)((1.0/300L)*(timeNow - timeThen));
                    String progressBar = "[";

                    progressBar = progressBar + new String(new char[progress]).replace("\0", "#");
                    progressBar = progressBar + new String(new char[10 - progress]).replace("\0", "-");
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