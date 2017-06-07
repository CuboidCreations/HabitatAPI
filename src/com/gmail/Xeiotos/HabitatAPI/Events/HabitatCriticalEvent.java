package com.gmail.Xeiotos.HabitatAPI.Events;

import com.gmail.Xeiotos.HabitatAPI.Habitat;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Xeiotos
 */
public class HabitatCriticalEvent extends Event {

    private final static HandlerList handlers = new HandlerList();
    private final Habitat habitat;

    public HabitatCriticalEvent(final Habitat habitat) {
        this.habitat = habitat;
    }
    
    /**
     * @return the critical habitat
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
