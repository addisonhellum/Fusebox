package us.blockcade.fusebox.libraries.hologram;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.metadata.FixedMetadataValue;
import us.blockcade.fusebox.Fusebox;
import us.blockcade.fusebox.libraries.LibraryUtility;
import us.blockcade.fusebox.libraries.hologram.demo.HologramDemo;
import us.blockcade.fusebox.libraries.hologram.events.HologramDespawnEvent;
import us.blockcade.fusebox.libraries.hologram.events.HologramSpawnEvent;
import us.blockcade.fusebox.libraries.hologram.events.PlayerInteractHologramEvent;

import java.util.*;

/**
 *  A library full of useful resources.
 *  Each resource comes equip with demonstration plugins
 *  and clear documentation. All rights reserved. 2018.
 *  @author Blockcade Studios
 */
public class Hologram extends LibraryUtility implements Listener {

    /**
     *  LibraryUtility regulation information.
     */
    public String getModuleName() { return "Hologram"; }
    public String getModuleVersion() { return "BETA v0.1"; }
    public List<Listener> getHandlers() { return new ArrayList<>(); }

    /**
     * Static references
     */
    private static List<Hologram> holograms = new ArrayList<>();
    public static List<Hologram> getHolograms() { return holograms; }

    public static void despawnAll() {
        getHolograms().forEach(hologram -> {
            hologram.getLocations().forEach(location -> hologram.despawn(location));
        });
    }

    public static Hologram fromUUID(UUID uuid) {
        for (Hologram hologram : getHolograms())
            if (hologram.getUniqueId().equals(uuid)) return hologram;

        return null;
    }

    public static boolean isHologram(ArmorStand armorStand) {
        return armorStand.hasMetadata("hologram");
    }

    private UUID uuid;
    private List<String> lines;
    private Map<Location, ArmorStandCollection> stands;

    private double spacing = 0.27;

    /**
     * Create a new Hologram using lines.
     * @param lines Lines the hologram displays.
     */
    public Hologram(String... lines) {
        this.uuid = UUID.randomUUID();
        this.lines = Arrays.asList(lines);
        this.stands = new HashMap<>();

        Fusebox.getInstance().getServer().getPluginManager().registerEvents(this, Fusebox.getInstance());
        LibraryUtility.registerHandler(new HologramDemo());
        holograms.add(this);
    }

    public Hologram(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUniqueId() { return uuid; }

    /**
     * Get the contents of the hologram.
     * @return The list of line contents.
     */
    public List<String> getLines() {
        return lines;
    }

    /**
     * Get the contents of the hologram at a line.
     * @param index The index, starting at 0, of the line.
     * @return The content of the line at the index.
     */
    public String getLine(int index) {
        if (index >= lines.size()) return "";
        return ChatColor.translateAlternateColorCodes('&', lines.get(index));
    }

    /**
     * Get the contents of the hologram, similar to a string.
     * @param identifier String contained in line.
     * @return The line containing the identifier.
     */
    public String getLine(String identifier) {
        for (String line : getLines())
            if (line.contains(identifier)) return ChatColor.translateAlternateColorCodes('&', line);
        return "";
    }

    /**
     * Set the line of the hologram at all locations.
     * @param index The line number to edit.
     * @param line The contents of the new line.
     */
    public void setLine(int index, String line) {
        for (Location location : stands.keySet()) {
            ArmorStandCollection collection = stands.get(location);

            collection.getArmorStands().get(index)
                    .setCustomName(ChatColor.translateAlternateColorCodes('&', line));

            stands.replace(location, collection);
        }
    }

    /**
     * Get all locations where hologram is displayed.
     * @return The list of locations showing hologram.
     */
    public Set<Location> getLocations() {
        return stands.keySet();
    }

    /**
     * Set the vertical distance between lines. (Negative flips the line order)
     * @param spacing The delta-y between hologram lines.
     */
    public void setSpacing(double spacing) {
        this.spacing = spacing;
    }

    /**
     * Get the closest hologram location to a given location.
     * @param location The estimated location.
     * @return The closest valid location.
     */
    public Location getClosestTo(Location location) {
        double closeness = -1;
        Location closest = null;

        for (Location loc : getLocations()) {
            double distance = loc.distanceSquared(location);
            if ((distance < closeness) || closest == null) {
                closeness = distance;
                closest = loc;
            }
        } return closest;
    }

    /**
     * Spawn the hologram at a given location.
     * @param location The location to show the hologram.
     */
    public void spawn(Location location) {
        HologramSpawnEvent spawnEvent = new HologramSpawnEvent(this, location);
        Bukkit.getPluginManager().callEvent(spawnEvent);

        if (spawnEvent.isCancelled()) return;
        ArmorStandCollection collection = new ArmorStandCollection();
        for (int i = 0; i < lines.size(); i++) {
            Location loc = location.subtract(0, spacing, 0);

            if (lines.get(i) != "") {
                ArmorStand stand = location.getWorld().spawn(loc, ArmorStand.class);
                stand.setCustomName(ChatColor.YELLOW + getLine(i));
                stand.setCustomNameVisible(true);

                stand.setVisible(false);
                stand.setGravity(false);
                stand.setBasePlate(false);
                stand.setMetadata("Hologram", new FixedMetadataValue(Fusebox.getInstance(), getUniqueId()));

                collection.addArmorStand(stand);
            }
        }
        stands.put(location, collection);
    }

    /**
     * Remove hologram spawned at location.
     * @param location Position of hologram to remove.
     */
    public void despawn(Location location) {
        HologramDespawnEvent despawnEvent = new HologramDespawnEvent(this, location);
        Bukkit.getPluginManager().callEvent(despawnEvent);

        if (despawnEvent.isCancelled()) return;

        if (stands.get(location) == null) return;
        ArmorStandCollection collection = stands.get(location);

        for (ArmorStand armorStand : collection.getArmorStands())
            armorStand.remove();

        stands.remove(location);
    }

    public class ArmorStandCollection {

        private List<ArmorStand> armorStands;

        public ArmorStandCollection() { this.armorStands = new ArrayList<>(); }
        public ArmorStandCollection(List<ArmorStand> armorStands) { this.armorStands = armorStands; }

        public List<ArmorStand> getArmorStands() { return armorStands; }
        public void addArmorStand(ArmorStand armorStand) { armorStands.add(armorStand); }

    }

    @EventHandler
    public void onArmorStandInteract(PlayerInteractAtEntityEvent event) {
        Entity entity = event.getRightClicked();
        Player player = event.getPlayer();

        if (!(entity instanceof ArmorStand)) return;
        ArmorStand armorStand = (ArmorStand) entity;

        if (!isHologram(armorStand)) return;
        PlayerInteractHologramEvent interactHologramEvent = new PlayerInteractHologramEvent(player, this);
        Bukkit.getPluginManager().callEvent(interactHologramEvent);

        event.setCancelled(true);
    }

    @EventHandler
    public void onArmorStandAttack(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();

        if (!(entity instanceof ArmorStand)) return;
        ArmorStand armorStand = (ArmorStand) entity;

        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();

            if (!isHologram(armorStand)) return;
            PlayerInteractHologramEvent interactHologramEvent = new PlayerInteractHologramEvent(player, this);
            Bukkit.getPluginManager().callEvent(interactHologramEvent);
        }

        event.setCancelled(true);
    }

}
