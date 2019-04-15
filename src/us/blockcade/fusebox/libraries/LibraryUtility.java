package us.blockcade.fusebox.libraries;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import us.blockcade.fusebox.Fusebox;
import us.blockcade.fusebox.events.ServerLoadEvent;

import java.util.ArrayList;
import java.util.List;

/**
 *  A library full of useful resources.
 *  Each resource comes equip with demonstration plugins
 *  and clear documentation. All rights reserved. 2018.
 *  @author Blockcade Studios
 */
public class LibraryUtility implements Listener {

    private Plugin fusebox = Fusebox.getInstance();

    private static List<Listener> handlers = new ArrayList<>();
    private static List<CommandExecutor> commands = new ArrayList<>();

    public String getModuleName() { return "Prototype Module"; }
    public String getModuleVersion() { return "v1.0"; }
    public List<Listener> getHandlers() { return handlers; }
    public List<CommandExecutor> getCommands() { return commands; }

    public static void registerHandler(Listener handler) {
        handlers.add(handler);
    }

    public static void registerCommand(CommandExecutor executor) {
        commands.add(executor);
    }

    public void log(String message) {
        System.out.println("[Fusebox] [" + getModuleName() + "] " + message);
    }

    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        System.out.println("[Fusebox] [Module Status] Enabled " + getModuleName() + " " + getModuleVersion());

        registerHandler(this);
        getHandlers().forEach(handler -> fusebox.getServer().getPluginManager().registerEvents(handler, fusebox));
    }

}
