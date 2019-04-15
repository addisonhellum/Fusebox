package us.blockcade.fusebox;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import us.blockcade.fusebox.registration.CommandData;
import us.blockcade.fusebox.events.ServerLoadEvent;
import us.blockcade.fusebox.events.ServerUnloadEvent;
import us.blockcade.fusebox.libraries.hologram.demo.HologramDemo;
import us.blockcade.fusebox.registration.ModuleRegistrar;

import java.util.ArrayList;
import java.util.List;

/**
 *  A library full of useful resources.
 *  Each resource comes equip with demonstration plugins
 *  and clear documentation. All rights reserved. 2018.
 *  @author Blockcade Studios
 */
public class Fusebox extends JavaPlugin {

    private static Plugin plugin;
    public static Plugin getInstance() { return plugin; }

    public static List<Listener> handlers = new ArrayList<>();
    public static List<CommandData> commands = new ArrayList<>();

    @Override
    public void onEnable() {
        plugin = this;

        ModuleRegistrar.register();

        for (Listener handler : handlers)
            getServer().getPluginManager().registerEvents(handler, this);

        for (CommandData command : commands)
            getCommand(command.getLabel()).setExecutor(command.getExecutor());

        ServerLoadEvent loadEvent = new ServerLoadEvent(this);
        getServer().getPluginManager().callEvent(loadEvent);

        HologramDemo demo = new HologramDemo();
        demo.start();
    }

    @Override
    public void onDisable() {
        ServerUnloadEvent unloadEvent = new ServerUnloadEvent(this);
        getServer().getPluginManager().callEvent(unloadEvent);
    }

}
