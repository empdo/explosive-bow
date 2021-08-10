package dev.essung.plugintest1;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.jar.Attributes.Name;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;


public final class PluginTest1 extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("tntbow plugin: alve");
        getServer().getPluginManager().registerEvents(this,this);
        getServer().getPluginManager().registerEvents(new ExplosiveArrows(this),this);
        getServer().getPluginManager().registerEvents(new ExplosiveSword(this),this);
        
    }

    @EventHandler
    public void onPlayerLoad(PlayerJoinEvent event){
        addItems(event.getPlayer());
    }

    void addItems(Player player){
        ItemStack bow = new ItemStack(Material.BOW, 1);
        ItemMeta bowMeta = bow.getItemMeta();
        bowMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("explosive"), PersistentDataType.INTEGER, 1);
        bowMeta.setUnbreakable(true);
        bow.setItemMeta(bowMeta);
        bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        player.getInventory().clear();
        player.getInventory().addItem(bow);

        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta swordMeta = sword.getItemMeta();
        
        String myuuid = UUID.randomUUID().toString();

        swordMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("explosivestuff-uuid"), PersistentDataType.STRING, myuuid);
        swordMeta.setUnbreakable(true);
        sword.setItemMeta(swordMeta);

        player.getInventory().addItem(sword);
        player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));
        player.getInventory().addItem(new ItemStack(Material.ARROW, 1));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("tntBow plugin: disabled");
    }
}
