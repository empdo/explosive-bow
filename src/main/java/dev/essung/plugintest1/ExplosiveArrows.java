package dev.essung.plugintest1;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.NamespacedKey;
import org.bukkit.projectiles.ProjectileSource;

public class ExplosiveArrows implements Listener {
    Plugin plugin;
    public ExplosiveArrows(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent event){
        if (!checkIfExplosive(event.getBow())){return;}
        if (event.getProjectile() instanceof Arrow){
            LivingEntity shooter = event.getEntity();
            if (shooter instanceof Player){
                Player playerShooter = (Player) shooter;
                event.getProjectile().addScoreboardTag("explosive");
                if (!playerShooter.isSneaking()) {
                    playerShooter.setVelocity(playerShooter.getVelocity().add(playerShooter.getLocation().getDirection().multiply(-2 * event.getForce())));
                }
            }
        }
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        if (!event.getEntity().getScoreboardTags().contains("explosive")){return;}
        ProjectileSource shooter = event.getEntity().getShooter();
            Location hitLocation = event.getEntity().getLocation();
            event.getEntity().remove();
            if (hitLocation.getWorld() != null) {
                hitLocation.getWorld().createExplosion(hitLocation, 4, true, true, (Entity) shooter);
            }
    }

    boolean checkIfExplosive(ItemStack bow){
        if(bow.getItemMeta().getPersistentDataContainer().has(NamespacedKey.minecraft("explosive"), PersistentDataType.INTEGER)){
            return true;
            // bowMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("sak"), PersistentDataType.SHORT, value);
            // if(bow.getItemMeta().getLore().contains("Explosive")){
            // }
        }
        return false;
    }
}
