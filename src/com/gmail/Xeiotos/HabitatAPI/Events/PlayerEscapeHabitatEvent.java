package com.gmail.Xeiotos.HabitatAPI.Events;

import com.gmail.Xeiotos.HabitatAPI.Habitat;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Xeiotos
 */
public class PlayerEscapeHabitatEvent extends Event {

    private final static HandlerList handlers = new HandlerList();
    private final Player player;
    private final Habitat habitat;

    public PlayerEscapeHabitatEvent(final Player player, final Habitat habitat) {
        this.player = player;
        this.habitat = habitat;
    }

    /**
     * @return the player leaving the habitat
     */
    public Player getPlayer() {
        return player;
    }
    
    /**
     * @return the habitat being left
     */
    public Habitat getHabitat() {
        return habitat;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
