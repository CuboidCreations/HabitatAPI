package com.gmail.Xeiotos.HabitatAPI;

import com.gmail.Xeiotos.HabitatAPI.Enumerations.HabitatType;
import com.gmail.Xeiotos.HabitatAPI.Events.PlayerEnterHabitatEvent;
import com.gmail.Xeiotos.HabitatAPI.Events.PlayerEscapeHabitatEvent;
import com.gmail.Xeiotos.HabitatAPI.Events.PlayerLeaveHabitatEvent;
import com.gmail.Xeiotos.HabitatAPI.Misc.Util;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

/**
 *
 * @author Xeiotos
 */
public class Habitat {

    private final int id;
    private final int RADIUS = 127;
    private final int EXITRADIUSSQUARED = 400;
    private final int RADIUSSQUARED = 16129;
    private final Location center;
    private final Point relativeCenter = new Point();
    private final Location[] exitLocs = new Location[4];
    private final LinkedList<String> flags = new LinkedList<>();
    private List<String> players = new LinkedList<>();
    private final HabitatType habitatType;
    private int errorCount = 0;

    public Habitat(Location center, boolean[] exits, String habitatTypeName, int id, int errors) {
        this.center = center;
        this.errorCount = errors;
        this.id = id;
        this.relativeCenter.setLocation(Util.round(center.getBlockX()), Util.round(center.getBlockZ()));
        this.habitatType = lookupHabitatType(habitatTypeName);

        if (exits[0]) {
            exitLocs[0] = new Location(center.getWorld(), center.getX(), 127, center.getZ() + RADIUS);
        }
        if (exits[1]) {
            exitLocs[1] = new Location(center.getWorld(), center.getX() - RADIUS, 127, center.getZ());
        }
        if (exits[2]) {
            exitLocs[2] = new Location(center.getWorld(), center.getX(), 127, center.getZ() - RADIUS);
        }
        if (exits[3]) {
            exitLocs[3] = new Location(center.getWorld(), center.getX() + RADIUS, 127, center.getZ());
        }
    }

    public boolean isPlayerInRange(Player player) {
        if (player.getLocation().getBlock().getRelative(BlockFace.UP).getLocation().distanceSquared(center) <= RADIUSSQUARED) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isPlayerInExit(Player player) { //TODO make this use 2D distance instead of 3D
        if (player == null) {
            return false;
        }
        
        //TODO improve this
        for (Location location : getExits()) {
            if (location != null) {
                if (player.getLocation().getBlock().getRelative(BlockFace.UP).getLocation().distanceSquared(location) <= EXITRADIUSSQUARED) {
                    return true;
                }
            }
        }
        return false;
    }

    public static HabitatType lookupHabitatType(String habitatName) {
        for (HabitatType h : HabitatType.values()) {
            if (h.getTypeName().equals(habitatName)) {
                return h;
            }
        }
        return HabitatType.INVALID;
    }

    public Location[] getExits() {
        return exitLocs;
    }

    public void addPlayer(String player, Habitat oldHabitat) {
        if (!players.contains(player)) {
            players.add(player);
            Bukkit.getServer().getPluginManager().callEvent(new PlayerEnterHabitatEvent(Bukkit.getPlayer(player), this, oldHabitat));
        }
    }

    public void removePlayer(String player, Habitat newHabitat) {
        if (players.contains(player)) {
            players.remove(player);
            Bukkit.getServer().getPluginManager().callEvent(new PlayerLeaveHabitatEvent(Bukkit.getPlayer(player), this, newHabitat));
        }
    }

    public List<String> getPlayers() {
        return players;
    }

    public void teleportPlayer(Player player) {
        player.teleport(center);
    }

    public Location getCenter() {
        return center;
    }

    public Point getRelativeCenter() {
        return relativeCenter;
    }

    public int getRadius() {
        return RADIUS;
    }

    public int getId() {
        return id;
    }

    public LinkedList<String> getFlags() {
        return flags;
    }
    
    //TODO add flag system instead of strings
    public void addFlag(String flag) {
        flags.add(flag);
    }

    public HabitatType getType() {
        return habitatType;
    }

    public String getTypeName() {
        return habitatType.getTypeName();
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void resetErrorCount() {
        errorCount = 0;
    }

    public void warnServer(String playerName, Point relativeCenter, Location location) {
        Bukkit.getServer().getPluginManager().callEvent(new PlayerEscapeHabitatEvent(Bukkit.getPlayer(playerName), this));
        HabitatAPI.getInstance().getLogger().log(Level.INFO, "{0} was outside habitat {1}, possible structural error near {2}", new Object[]{playerName, relativeCenter, location});
        errorCount++;
    }
}
