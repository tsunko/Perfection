package academy.hekiyou.tenkore.perfection;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TabColor implements Listener {

    @EventHandler
    public void handleTabColorOnJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        LuckPerms perms = LuckPermsProvider.get();
        
        User user = perms.getUserManager().getUser(player.getUniqueId());
        if(user == null)
            return;
        
        Group group = perms.getGroupManager().getGroup(user.getPrimaryGroup());
        if(group == null)
            return;
        
        CachedMetaData metadata = group.getCachedData().getMetaData(QueryOptions.defaultContextualOptions());
        String groupTabColor = metadata.getMetaValue("tab-color");
        if(groupTabColor == null)
            return;
        
        player.setPlayerListName(ChatColor.getByChar(groupTabColor) + player.getPlayerListName());
    }

}
