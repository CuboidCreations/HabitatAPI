package com.gmail.Xeiotos.HabitatAPI.Managers;

import com.gmail.Xeiotos.HabitatAPI.Enumerations.HabitatPluginType;
import com.gmail.Xeiotos.HabitatAPI.HabitatPlugin;
import com.gmail.Xeiotos.HabitatAPI.Habitat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Xeiotos
 */
public class HabitatPluginManager {

    private static HabitatPluginManager habitatPluginManager;
    private static List<HabitatPlugin> tickablePlugins = new ArrayList<>();
    private static List<HabitatPlugin> habitatPlugins = new ArrayList<>();

    /**
     * Get the HabitatPluginManager insance
     *
     * @return HabitatPluginManager instance
     */
    public static HabitatPluginManager getManager() {
        if (habitatPluginManager == null) {
            habitatPluginManager = new HabitatPluginManager();
        }

        return habitatPluginManager; // NOT THREAD SAFE!
    }

    /**
     * Get all HabitatPlugins
     *
     * @return List of all HabitatPlugins, null if none
     */
    public List<HabitatPlugin> getHabitats() {
        return habitatPlugins;
    }

    /**
     * Add a HabitatPlugin to the list
     *
     * @param habitatPlugin HabitatPlugin to add
     */
    public void addPlugin(HabitatPlugin habitatPlugin) {
        if (habitatPlugins.contains(habitatPlugin) || habitatPlugin == null) {
            return;
        }
        habitatPlugin.intit();
        
        habitatPlugins.add(habitatPlugin);
        
        if (habitatPlugin.getType() == HabitatPluginType.TICK || habitatPlugin.getType() == HabitatPluginType.COMBINED) {
            tickablePlugins.add(habitatPlugin);
        }
    }

    /**
     * Get a HabitatPlugin from the list by name
     *
     * @param string String to search for
     * @return HabitatPlugin with string's name, null if not found
     */
    public HabitatPlugin getPlugin(String string) {
        for (HabitatPlugin habitatPlugin : habitatPlugins) {
            if (habitatPlugin.getName().equalsIgnoreCase(string)) {
                return habitatPlugin;
            }
        }
        return null;
    }
    
    /**
     * Get a all HabitatPlugins
     *
     * @return List of all HabitatPlugins
     */
    public List<HabitatPlugin> getPlugins() {
        return habitatPlugins;
    }

    /**
     * Remove a HabitatPlugin from the list
     *
     * @param habitatPlugin HabitatPlugin to remove
     */
    public void removePlugin(HabitatPlugin habitatPlugin) {
        if (!habitatPlugins.contains(habitatPlugin) || habitatPlugin == null) {
            return;
        }
        habitatPlugins.remove(habitatPlugin);
        habitatPlugin.habitats = null;
    }

    /**
     * Register an Habitat to a HabitatPlugin
     *
     * @param habitat the Habitat to register to the HabitatPlugin
     * @param habitatPlugin the HabitatPlugin to register the Habitat to
     */
    public void registerPlugin(Habitat habitat, HabitatPlugin habitatPlugin) {
        habitatPlugin.habitats.add(habitat);
    }

    /**
     * Ticks all HabitatPlugins
     */
    public void tickHabitatPlugins() {
        for (HabitatPlugin h : tickablePlugins) {
            h.tick();
        }
    }
}