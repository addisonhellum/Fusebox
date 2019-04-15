package us.blockcade.fusebox.libraries.hologram.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import us.blockcade.fusebox.libraries.chat.ChatUtil;
import us.blockcade.fusebox.libraries.hologram.Hologram;
import us.blockcade.fusebox.libraries.items.ItemStackBuilder;
import us.blockcade.fusebox.libraries.menu.InventoryMenu;
import us.blockcade.fusebox.libraries.menu.PagedMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *  A library full of useful resources.
 *  Each resource comes equip with demonstration plugins
 *  and clear documentation. All rights reserved. 2018.
 *  @author Blockcade Studios
 */
public class HologramCommand implements CommandExecutor {

    private boolean isPlayer(CommandSender s) {
        return s instanceof Player;
    }

    private boolean hasPermission(CommandSender s) {
        if (isPlayer(s)) return s.isOp();
        return true;
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            // TODO: Show chat menu of command arguments

        } else if (args.length == 1) {
            String arg = args[0];

            if (arg.equalsIgnoreCase("manage")) {
                if (!hasPermission(s)) {
                    s.sendMessage(ChatUtil.format("&cYou are not allowed to do this!"));
                    return false;
                }

                if (!isPlayer(s)) {
                    s.sendMessage(ChatUtil.format("&cYou must be a player to do this!"));
                    return false;
                }
                Player player = (Player) s;

                PagedMenu menu = new PagedMenu("Manage Holograms");

                for (int i = 0; i < Hologram.getHolograms().size(); i++) {
                    Hologram h = Hologram.getHolograms().get(i);

                    List<String> lore = new ArrayList<>();
                    double distance = player.getLocation().distance(h.getClosestTo(player.getLocation()));
                    lore.add("Distance from you: &e" + (Math.round(distance * 100.0) / 100.0) + " blocks");
                    lore.add("");
                    lore.add("Line contents (" + h.getLines().size() + ")");
                    for (int j = 0; j < h.getLines().size(); j++) {
                        lore.add((j + 1) + ". " + h.getLine(j));
                    }
                    lore.add("");
                    lore.add("&8Left Click for spawn settings.");
                    lore.add("&8Right Click to edit hologram.");

                    ItemStack icon = new ItemStackBuilder(Material.ARMOR_STAND).withName("&aHologram #" + (i + 1))
                            .withAmount(h.getLocations().size()).withLore(lore).build();

                    menu.withItem(icon);

                    //menu.bindLeft(icon, "hologram spawnsettings " + h.getUniqueId());
                    //menu.bindRight(icon, "hologram edit " + h.getUniqueId());
                    //menu.add(icon);
                }

                //menu.freezeItems(true);
                menu.display(player);
            }

        } else if (args.length == 2) {
            String arg = args[0];
            String id = args[1];

            if (!isPlayer(s)) {
                s.sendMessage(ChatUtil.format("&cYou must be a player to do this!"));
                return false;
            }

            Player player = (Player) s;

            UUID uuid = UUID.fromString(id);
            if (Hologram.fromUUID(uuid) == null) {
                s.sendMessage(ChatUtil.format("&cThere is no hologram with that UUID."));
                return false;
            }

            Hologram hologram = Hologram.fromUUID(uuid);

            if (arg.equalsIgnoreCase("spawnsettings")) {
                s.sendMessage(ChatUtil.format("&cComing soon."));

                // TEMPORARY
                hologram.spawn(player.getLocation());

            } else if (arg.equalsIgnoreCase("edit")) {
                s.sendMessage(ChatUtil.format("&cComing soon."));

            }

        }

        return false;
    }

}
