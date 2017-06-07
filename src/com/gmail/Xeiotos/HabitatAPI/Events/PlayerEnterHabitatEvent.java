package com.gmail.Xeiotos.HabitatAPI.Events;

import com.gmail.Xeiotos.HabitatAPI.Habitat;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Xeiotos
 */
public class PlayerEnterHabitatEvent extends Event {

    private final static HandlerList handlers = new HandlerList();
    private final Player player;
    private final Habitat habitat;
    private final Habitat oldHabitat;

    public PlayerEnterHabitatEvent(final Player player, final Habitat habitat, final Habitat oldHabitat) {
        this.player = player;
        this.habitat = habitat;
        this.oldHabitat = oldHabitat;
    }

    /**
     * @return the player leaving the habitat
     */
    public Player getPlayer() {
        return player;
    }
    
    /**
     * @return the habitat being entered
     */
    public Habitat getHabitat() {
        return habitat;
    }
    
    /**
     * @return the habitat being left, null if none
     */
    public Habitat getOldHabitat() {
        return oldHabitat;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
