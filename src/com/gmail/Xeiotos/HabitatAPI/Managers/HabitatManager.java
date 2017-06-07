package com.gmail.Xeiotos.HabitatAPI.Managers;

import com.gmail.Xeiotos.HabitatAPI.Enumerations.HabitatType;
import com.gmail.Xeiotos.HabitatAPI.Events.HabitatCriticalEvent;
import com.gmail.Xeiotos.HabitatAPI.HabitatAPI;
import com.gmail.Xeiotos.HabitatAPI.Habitat;
import com.gmail.Xeiotos.HabitatAPI.Misc.Util;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author Xeiotos
 */
public class HabitatManager {

    private final HabitatAPI plugin;
    //the class instance
    private static HabitatManager habitatManager;
    //list of habitats
    private static List<Habitat> habitats = new ArrayList<>();
    int habitatSize = 0;

    /**
     * Create a new instance of a HabitatManager for a specific plugin
     */
    public HabitatManager() {
        this.plugin = HabitatAPI.getInstance();
    }

    /**
     * Get the HabitatManager insance
     *
     * @return HabitatManager instance
     */
    public static HabitatManager getManager() {
        if (habitatManager == null) {
            habitatManager = new HabitatManager();
        }

        return habitatManager; // NOT THREAD SAFE!
    }

    /**
     * Get habitat by ID
     *
     * @param i Habitat ID to lookup
     * @return Habitat with ID, returns null if not found
     */
    public Habitat getHabitat(int i) {
        for (Habitat h : habitats) {
            if (h.getId() == i) {
                return h;
            }
        }
        return null;
    }

    /**
     * Gets the number of habitats on the server
     *
     * @returns The number of habitats
     */
    public int getNumHabitats() {
        return habitatSize;
    }

    /**
     * Get habitat by relative coordinates
     *
     * @param point Habitat relative coordinates to lookup
     * @return Habitat on coordinates, returns null if not found
     */
    public Habitat getHabitat(Point point) {
        if (point == null) {
            return null;
        }
        for (Habitat h : habitats) {
            if (h.getRelativeCenter().getLocation().equals(point.getLocation())) {
                return h;
            }
        }
        return null;
    }

    /**
     * Get all habitats
     *
     * @return List of all habitats, null if none
     */
    public List<Habitat> getHabitats() {
        return habitats;
    }

    /**
     * Save all habitat data
     */
    public void saveHabitats() {
        ConfigManager configManager = plugin.getConfigManager();
        for (Habitat h : habitats) {
            int num = h.getId();
            configManager.loadConfigFiles(
                    new ConfigManager.ConfigPath("Habitat" + num, "Habitat.yml", "habitats/Habitat" + num + ".hbt"));
            FileConfiguration configFile = configManager.getFileConfig("Habitat" + num);
            configFile.set("errors", h.getErrorCount());
            configManager.saveConfig("Habitat" + num);
            configManager.unloadConfig("Habitat" + num);
        }
    }

    /**
     * Loads a habitat from a FileConfiguration
     *
     * @param habitatFile The habitat's configuration object.
     */
    public Habitat loadHabitat(FileConfiguration habitatFile) {
        Location center = deserializeLoc(habitatFile.getString("location"));
        boolean[] exits = deserializeExits(habitatFile.getString("exits"));
        HabitatType habitatType = HabitatType.valueOf(habitatFile.getString("type"));
        int errors = habitatFile.getInt("errors");

        int num = habitatSize;
        habitatSize++;

        Habitat habitat;

        if (!habitatType.equals(HabitatType.INVALID)) {
            habitat = new Habitat(center, exits, habitatType.getTypeName(), num, errors);
        } else {
            return null;
        }

        habitats.add(habitat);

        if (errors >= HabitatAPI.getInstance().getConfig().getInt("errorTreshold")) {
            Bukkit.getServer().getPluginManager().callEvent(new HabitatCriticalEvent(habitat));
        }

        return habitat;
    }

    /**
     * Resets all habitats loaded in memory
     *
     */
    public void flushHabitats() {
        habitats.clear();
        habitatSize = 0;
    }

    private String serializeLoc(Location l) {
        return l.getWorld().getName() + "," + Util.round(l.getBlockX()) + "," + Util.round(l.getBlockZ());
    }

    private String serializeExits(boolean[] exits) {
        return exits[0] + "," + exits[1] + "," + exits[2] + "," + exits[3];
    }

    private Location deserializeLoc(String s) {
        String[] st = s.split(",");
        return new Location(Bukkit.getWorld(st[0]), Integer.parseInt(st[1]) * 253, 127, Integer.parseInt(st[2]) * 253);
    }

    private boolean[] deserializeExits(String s) {
        String[] st = s.split(",");
        boolean[] exits = {Boolean.parseBoolean(st[0]), Boolean.parseBoolean(st[1]), Boolean.parseBoolean(st[2]), Boolean.parseBoolean(st[3])};
        return exits;
    }
}