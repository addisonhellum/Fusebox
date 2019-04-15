package us.blockcade.fusebox.registration;

import org.bukkit.event.Listener;
import us.blockcade.fusebox.Fusebox;
import us.blockcade.fusebox.libraries.LibraryUtility;
import us.blockcade.fusebox.libraries.hologram.Hologram;
import us.blockcade.fusebox.libraries.hologram.commands.HologramCommand;
import us.blockcade.fusebox.libraries.hologram.demo.HologramDemo;
import us.blockcade.fusebox.libraries.menu.MenuHandler;

public class ModuleRegistrar implements Listener {

    public static void register() {
        Fusebox.commands.add(new CommandData("hologram", new HologramCommand()));

        Fusebox.handlers.add(new LibraryUtility());
        Fusebox.handlers.add(new MenuHandler());
        Fusebox.handlers.add(new Hologram());

        Fusebox.handlers.add(new HologramDemo());
    }

}
