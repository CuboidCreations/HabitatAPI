/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmail.Xeiotos.HabitatAPI.Managers;

import com.gmail.Xeiotos.HabitatAPI.HabitatAPI;
import com.gmail.Xeiotos.HabitatAPI.HabitatPlayer;
import java.util.ArrayList;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author Chris
 */
public class HabitatPlayerManager {
    
    private static HabitatPlayerManager habitatPlayerManager;
    private static ArrayList<HabitatPlayer> habitatPlayers = new ArrayList<>();
    
    /**
    * Create a new instance of a HabitatPlayerManager
    */    
    public HabitatPlayerManager() {
    }

    /**
    * Get the HabitatPlayerManager instance
    * @return HabitatPlayerManager instance
    */
    public static HabitatPlayerManager getManager() {
        if (habitatPlayerManager == null) {
            habitatPlayerManager = new HabitatPlayerManager();
        }

        return habitatPlayerManager; // NOT THREAD SAFE!
    }
    
    /**
    * Remove a player from the player list by HabitatPlayer
    * @param habitatPlayer Habitatplayer to remove
    */
    public void removePlayer(HabitatPlayer habitatPlayer) {
        habitatPlayers.remove(habitatPlayer);
    }
    
    /**
    * Add a player to the list by player object
    * @param player Player to add
    */
    public void addPlayer(Player player) {
        habitatPlayers.add(new HabitatPlayer(player.getName()));
    }
    
    /**
    * Remove a player from the player list by playername
    * @param playerName Habitatplayer to remove
    */
    public void removePlayer(String playerName) {
        habitatPlayers.remove(getHabitatPlayer(playerName));
    }
    
    /**
    * Remove a player from the player list by player
    * @param player Habitatplayer to remove
    */
    public void removePlayer(Player player) {
        habitatPlayers.remove(getHabitatPlayer(player));
    }
    
    /**
    * Get a HabitatPlayer by it's player object
    * @param p Player to lookup
    * @return HabitatPlayer from the list, null if not found
    */
    public HabitatPlayer getHabitatPlayer(Player p) {
        return getHabitatPlayer(p.getName());
    }
    
    /**
    * Get a HabitatPlayer by it's name
    * @param name Player name to lookup
    * @return HabitatPlayer from the list, null if not found
    */
    public HabitatPlayer getHabitatPlayer(String name) {
        if (name != null) {
            for (HabitatPlayer p : habitatPlayers) {
                if (p.getName().equalsIgnoreCase(name)) {
                    return p;
                }
            }
        }
        return null;
    }
    
    /**
     * Save all player data
     */
    public void saveHabitatPlayers() {
        ConfigManager configManager = HabitatAPI.getInstance().getConfigManager();
        for (HabitatPlayer habitatPlayer : habitatPlayers) {
            if (habitatPlayer == null) {
                continue;
            }
            
            String name = habitatPlayer.getName();
            configManager.loadConfigFiles(
                    new ConfigManager.ConfigPath(name, "Habitat.yml", "players/" + name + ".hbtp"));
            FileConfiguration configFile = configManager.getFileConfig(name); //TODO
            //configFile.set("errors", h.getErrorCount());
            configManager.saveConfig(name);
            configManager.unloadConfig(name);
        }
    }
    
    /**
    * Get all HabitatPlayers
    * @return List of all HabitatPlayers, null if none.
    */
    public ArrayList<HabitatPlayer> getHabitatPlayers() {
        return habitatPlayers;
    }
}
