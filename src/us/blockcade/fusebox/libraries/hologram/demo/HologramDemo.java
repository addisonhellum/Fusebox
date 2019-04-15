package us.blockcade.fusebox.libraries.hologram.demo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import us.blockcade.fusebox.Fusebox;
import us.blockcade.fusebox.libraries.LibraryDemo;
import us.blockcade.fusebox.libraries.hologram.Hologram;
import us.blockcade.fusebox.libraries.hologram.events.HologramSpawnEvent;

import java.util.UUID;

public class HologramDemo implements LibraryDemo, Listener {

    private int taskId = 0;

    @Override
    public void start() {
        System.out.println("[Fusebox] [Hologram Demo]: " + info());

        Location location = Bukkit.getWorld("world").getSpawnLocation();

        Hologram hologram = new Hologram("" + UUID.randomUUID());
        hologram.spawn(location);

        System.out.println("[Fusebox] [Hologram Demo]: Spawned @ " + location.toString());

        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Fusebox.getInstance(), new Runnable() {
            int iteration = 0;

            @Override
            public void run() {
                if (iteration % 2 == 0) hologram.setLine(0, hologram.getLine(0));
                else hologram.setLine(0, "&a" + hologram.getLine(0));

                iteration++;
            }
        }, 20, 20);
    }

    @Override
    public void stop() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    @Override
    public String info() {
        return "Spawns and animates a demonstration hologram.";
    }

    @EventHandler
    public void onSpawn(HologramSpawnEvent event) {
        Hologram hologram = event.getHologram();
        Bukkit.broadcastMessage("[Hologram Demo] " + ChatColor.GREEN + "Spawned hologram with UUID of " + hologram.getUniqueId());
    }

}
