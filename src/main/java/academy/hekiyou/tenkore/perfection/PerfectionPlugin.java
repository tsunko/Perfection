package academy.hekiyou.tenkore.perfection;

import academy.hekiyou.door.FrontDoor;
import academy.hekiyou.tenkore.plugin.TenkorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class PerfectionPlugin extends TenkorePlugin {
    
    @Override
    public void enable(){
        FrontDoor.load(WhisperSystem.class);
        Bukkit.getPluginManager().registerEvents(new ChatFormatter(), (JavaPlugin)getCore());
    }
    
}
