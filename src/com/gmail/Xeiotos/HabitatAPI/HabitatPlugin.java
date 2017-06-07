package com.gmail.Xeiotos.HabitatAPI;

import com.gmail.Xeiotos.HabitatAPI.Enumerations.HabitatPluginType;
import com.gmail.Xeiotos.HabitatAPI.Managers.HabitatManager;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Chris
 */
public abstract class HabitatPlugin extends JavaPlugin{
    
    public List<Habitat> habitats = new LinkedList<>();
    public HabitatAPI habitatAPI;
    private boolean initialised;
    protected HabitatPluginType pluginType;
    
    public abstract void tick();

    public void intit() {
        if (initialised == true) {
            return;
        }
        
        getConfig().options().copyDefaults(true);
        this.saveConfig();
        
        List<String> habitatCoords = getConfig().getStringList("habitats");
              
        for (String string : habitatCoords) {
            String[] split = string.split(",");
            Habitat habitat = HabitatManager.getManager().getHabitat(new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
            if (habitat != null) {
                habitats.add(habitat);
                habitat.addFlag(this.getName());
            }
        }
        
        this.getLogger().log(Level.INFO, "Registered {0} habitat(s)!", habitats.size());
        
        initialised = true;
    }
    
    @Override
    public void onEnable() {
        
    }
    
    public HabitatPluginType getType() {
        return pluginType;
    }
}
