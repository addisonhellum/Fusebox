package us.blockcade.fusebox.libraries.hologram.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import us.blockcade.fusebox.libraries.hologram.Hologram;

public class PlayerInteractHologramEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Hologram hologram;
    private Player player;

    public PlayerInteractHologramEvent(Player player, Hologram hologram) {
        this.player = player;
        this.hologram = hologram;
    }

    public Hologram getHologram() {
        return hologram;
    }
    public Player getPlayer() { return player; }

    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() { return handlers; }

}
