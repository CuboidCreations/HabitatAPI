package com.gmail.Xeiotos.HabitatAPI.Listeners;

import com.gmail.Xeiotos.HabitatAPI.Managers.HabitatPlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author Xeiotos
 */
public class PlayerLeaveListener implements Listener {
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onLeave(PlayerQuitEvent event) {
        HabitatPlayerManager.getManager().removePlayer(event.getPlayer().getName());
    }
}
