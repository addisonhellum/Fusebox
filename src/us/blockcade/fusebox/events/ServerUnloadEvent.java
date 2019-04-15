package us.blockcade.fusebox.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *  A library full of useful resources.
 *  Each resource comes equip with demonstration plugins
 *  and clear documentation. All rights reserved. 2018.
 *  @author Blockcade Studios
 */
public class ServerUnloadEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private JavaPlugin plugin;

    /**
     * Called inside the onDisable method.
     * @param plugin The plugin that was disabled.
     */
    public ServerUnloadEvent(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
