package com.gmail.Xeiotos.HabitatAPI;

import com.gmail.Xeiotos.HabitatAPI.Managers.HabitatManager;
import com.gmail.Xeiotos.HabitatAPI.Misc.Util;
import java.awt.Point;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author Xeiotos
 */
public class HabitatPlayer {

    private String playerName = null;
    private int voice;
    private boolean whisper;
    private Player player;

    /**
     * Create a HabitatPlayer
     *
     * @param playerName Player's name
     */
    public HabitatPlayer(String playerName) {
        this.playerName = playerName;
        this.player = Bukkit.getPlayer(playerName);
    }

    /**
     * Get the player's name
     *
     * @return HabitatPlayer's name
     */
    public String getName() {
        return playerName;
    }

    /**
     * Get the level of a certain skill
     *
     * @param skill Skill name too get value of
     * @return String representing the value of that skill
     */
    public String getLevel(String skill) {
        switch (skill) {
            case "voice":
                return Integer.toString(voice);
            case "whisper":
                return Boolean.toString(whisper);
            default:
                return "";
        }
    }

    /**
     * Get the player's relative location
     *
     * @return HabitatPlayer's relative location
     */
    public Point getRelativeLocation() {
        Point coords = new Point();

        int roundedX = player.getLocation().getBlockX();
        roundedX = Util.round(roundedX);

        int roundedZ = player.getLocation().getBlockZ();
        roundedZ = Util.round(roundedZ);
        coords.setLocation(roundedX, roundedZ);

        return coords;
    }

    /**
     * Get the player's habitat
     *
     * @return HabitatPlayer's habitat, or null if not in habitat
     */
    public Habitat getHabitat() {
        Habitat habitat = HabitatManager.getManager().getHabitat(getRelativeLocation());

        if (habitat == null) {
            return null;
        }

        if (habitat.isPlayerInRange(player)) { //Player is in sphere radius
            return habitat;
        } else { //Player is not in sphere radius
            if (!habitat.isPlayerInExit(player)) {
                habitat.warnServer(playerName, habitat.getRelativeCenter(), player.getLocation());
                return null;
            } else {
                return habitat;
            }
        }
    }

    /**
     * Get the player's habitat
     *
     * @return Teleport the player to a habitat
     */
    public void teleportToHabitat(Habitat habitat) {
        player.teleport(habitat.getCenter());
        player.sendMessage(ChatColor.GRAY + "[HabitatAPI] Teleported to " + habitat.getRelativeCenter().getX() + "," + habitat.getRelativeCenter().getY());
    }
}
