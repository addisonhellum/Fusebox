package us.blockcade.fusebox.libraries.menu;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import us.blockcade.fusebox.Fusebox;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Set;

public class MenuHandler implements Listener {

    public static Set<Inventory> lockedInventories = new HashSet<>();
    public static Set<InventoryMenu> boundMenus = new HashSet<>();

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();
        ItemStack item = event.getCurrentItem();
        InventoryAction action = event.getAction();

        if (item == null || item.getType() == null) return;

        if (item.getItemMeta() != null) {
            if (item.getItemMeta().getDisplayName() != null) {
                if (item.getType().equals(Material.BARRIER) &&
                        (item.getItemMeta().getDisplayName().contains("Close") ||
                                item.getItemMeta().getDisplayName().contains("Cancel"))) {

                    player.playSound(player.getLocation(), Sound.NOTE_SNARE_DRUM, 8, 8);
                    player.closeInventory();
                }
            }
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    for (InventoryMenu menu : boundMenus) {
                        if (menu.build().equals(inv)) {
                            if (action.equals(InventoryAction.PICKUP_ALL)) {
                                if (menu.commandBindsLeft.get(item) != null) {
                                    player.performCommand(menu.commandBindsLeft.get(item));
                                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 8, 8);
                                }

                            } else if (action.equals(InventoryAction.PICKUP_HALF)) {
                                if (menu.commandBindsRight.get(item) != null) {
                                    player.performCommand(menu.commandBindsRight.get(item));
                                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 8, 8);
                                }
                            }
                        }
                    }
                } catch (ConcurrentModificationException e) {
                    // Ignore this, it doesn't cause any problems, but does
                    // occasionally occur when a menu is spammed
                }
            }
        }.runTaskLater(Fusebox.getInstance(), 4);

        if (lockedInventories.contains(inv)) event.setCancelled(true);
    }

}
