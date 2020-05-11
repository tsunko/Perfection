package academy.hekiyou.tenkore.perfection;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatFormatter implements Listener {
    
    @EventHandler(
            priority = EventPriority.HIGH
    )
    public void playerChatHook(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        User luckpermUser = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
        
        if(luckpermUser == null){
            player.sendMessage(ChatColor.RED + "Couldn't locate LuckPerm entry; alert system admin!");
            event.setCancelled(true);
            return;
        }
        
        if(player.hasPermission("perfection.function.coloredmessages"))
            event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
        
        CachedMetaData data = luckpermUser.getCachedData().getMetaData(QueryOptions.defaultContextualOptions());
        String prefix = formatFixes(data.getPrefix());
        String name = player.getCustomName();
        if(name == null || name.isEmpty())
            name = player.getName();
        String suffix = formatFixes(data.getSuffix());
        
        event.setFormat(prefix + name + ChatColor.RESET + ": %2$s " + suffix);
    }
    
    /**
     * Formats the fixture using Bukkit's internal {@link ChatColor#translateAlternateColorCodes(char, String)},
     * with the chat color character being '&'.
     * @param fix The fixture to translate the chat color for
     * @return An empty {@link String} or one with translated chat colors
     */
    // the root word for prefix and suffix is fix :^) the more you learn
    private String formatFixes(String fix){
        if(fix == null)
            return "";
        return ChatColor.translateAlternateColorCodes('&', fix);
    }
    
}
