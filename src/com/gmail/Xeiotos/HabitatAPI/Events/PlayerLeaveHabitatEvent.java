package com.gmail.Xeiotos.HabitatAPI.Events;

import com.gmail.Xeiotos.HabitatAPI.Habitat;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Xeiotos
 */
public class PlayerLeaveHabitatEvent extends Event {

    private final static HandlerList handlers = new HandlerList();
    private final Player player;
    private final Habitat habitat;
    private final Habitat newHabitat;

    public PlayerLeaveHabitatEvent(final Player player, final Habitat habitat, final Habitat newHabitat) {
        this.player = player;
        this.habitat = habitat;
        this.newHabitat = newHabitat;
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
    
    /**
     * @return the habitat being entered, null if none
     */
    public Habitat getNewHabitat() {
        return newHabitat;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
