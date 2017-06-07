package com.gmail.Xeiotos.HabitatAPI;

import com.gmail.Xeiotos.HabitatAPI.Managers.HabitatManager;
import com.gmail.Xeiotos.HabitatAPI.Managers.HabitatPlayerManager;
import com.gmail.Xeiotos.HabitatAPI.Listeners.PlayerJoinListener;
import com.gmail.Xeiotos.HabitatAPI.Listeners.PlayerLeaveListener;
import com.gmail.Xeiotos.HabitatAPI.Managers.HabitatPluginManager;
import com.gmail.Xeiotos.HabitatAPI.Managers.ConfigManager;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Xeiotos
 */
public class HabitatAPI extends JavaPlugin {

    //TODO add getPrefix()
    private static HabitatAPI instance;
    private static ArrayList<Plugin> hookedPlugins = new ArrayList<>();
    /**
     * Manager for config.
     */
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        instance = this;
        configManager = new ConfigManager(this);

        loadHabitats();

        for (Player p : Bukkit.getOnlinePlayers()) {
            HabitatPlayerManager.getManager().addPlayer(p);
        }

        registerEvents();
    }

    @Override
    public void onDisable() {
        HabitatManager.getManager().saveHabitats();
    }

    /**
     * Get a hook to HabitatAPI
     *
     * @param plugin The plugin instance to hook
     */
    public static HabitatAPI getHook(Plugin plugin) {
        if (plugin == null) {
            return null;
        }

        if (hookedPlugins.contains(plugin) || HabitatPluginManager.getManager().getPlugin(plugin.getName()) != null) {
            return instance;
        }

        if (plugin instanceof HabitatPlugin) {
            HabitatPluginManager.getManager().addPlugin((HabitatPlugin) plugin);
        } else {
            hookedPlugins.add(plugin);
        }

        HabitatAPI.getInstance().getLogger().log(Level.INFO, "Plugin {0} hooked!", plugin.getName());

        return instance;
    }

    /**
     * Unhook from HabitatAPI
     *
     * @param plugin The plugin instance to unhook
     */
    public static void unHook(Plugin plugin) {
        if (plugin == null) {
            return;
        }
        
        if (plugin instanceof HabitatPlugin) {
            if (HabitatPluginManager.getManager().getPlugin(plugin.getName()) == null) {
                return;
            }
            HabitatPluginManager.getManager().removePlugin((HabitatPlugin) plugin);

        } else {
            hookedPlugins.remove(plugin);
        }

        HabitatAPI.getInstance().getLogger().log(Level.INFO, "Plugin {0} unhooked!", plugin.getName());
    }

    /**
     * Get a all plugins hooked to HabitatAPI
     *
     * @return List of all hooked plugins
     */
    public static ArrayList<Plugin> getHookedPlugins() {
        return hookedPlugins;
    }

    private void loadHabitats() {
        HabitatManager manager = HabitatManager.getManager();
        manager.flushHabitats();

        File dir = new File(getDataFolder() + "/habitats/");

        if (!dir.exists()) {
            dir.mkdirs();
        }

        if (dir.listFiles().length != 0) {
            for (File child : dir.listFiles()) {
                configManager.loadConfigFiles(
                        new ConfigManager.ConfigPath(child.getName(), "Habitat.yml", "habitats/" + child.getName()));

                if (!child.getName().startsWith("Habitat") || !child.getName().endsWith(".hbt")) {
                    continue;
                }

                FileConfiguration config = configManager.getFileConfig(child.getName());
                manager.loadHabitat(config);
                configManager.unloadConfig(child.getName());
            }
        }

        this.getLogger().log(Level.INFO, "Loaded {0} habitats into memory", manager.getNumHabitats());
    }

    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerLeaveListener(), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("debug")) {
            if (sender instanceof Player) {
                if (!sender.getName().equals("Xeiotos")) {
                    sender.sendMessage(ChatColor.RED + "[HabitatAPI] Only Xeiotos may perform this command.");
                    return true;
                }
            }
        }
        return false;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public static HabitatAPI getInstance() {
        return instance;
    }
}
