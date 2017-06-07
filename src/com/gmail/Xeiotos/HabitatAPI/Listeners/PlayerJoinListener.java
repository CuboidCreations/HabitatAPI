package com.gmail.Xeiotos.HabitatAPI.Listeners;

import com.gmail.Xeiotos.HabitatAPI.HabitatAPI;
import com.gmail.Xeiotos.HabitatAPI.Managers.HabitatPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 *
 * @author Xeiotos
 */
public class PlayerJoinListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(final PlayerLoginEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(HabitatAPI.getInstance(), new Runnable() {
            @Override
            public void run() {
                HabitatPlayerManager.getManager().addPlayer(event.getPlayer());
            }
        });
    }
}
