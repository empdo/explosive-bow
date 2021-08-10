package dev.essung.plugintest1;

import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;

public class ExplosiveEnch extends EnchantmentWrapper {
    public ExplosiveEnch() {
        super("explosive");
    }
    @Override
    public boolean canEnchantItem(ItemStack item) {
        if (item.getType() == Material.BOW){
            return true;
        }return false;
    }
}
