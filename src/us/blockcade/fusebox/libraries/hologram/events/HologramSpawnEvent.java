package us.blockcade.fusebox.libraries.hologram.events;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import us.blockcade.fusebox.libraries.hologram.Hologram;

public class HologramSpawnEvent extends Event implements Cancellable {

    private boolean cancelled;
    private static final HandlerList handlers = new HandlerList();
    private Hologram hologram;
    private Location location;

    public HologramSpawnEvent(Hologram hologram, Location location) {
        this.hologram = hologram;
        this.location = location;
    }

    public Hologram getHologram() {
        return hologram;
    }
    public Location getLocation() { return location; }

    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() { return handlers; }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

}
