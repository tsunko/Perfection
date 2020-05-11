package academy.hekiyou.tenkore.perfection;

import academy.hekiyou.door.annotations.GlobAll;
import academy.hekiyou.door.annotations.Module;
import academy.hekiyou.door.annotations.RegisterCommand;
import academy.hekiyou.door.model.Invoker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

@Module
public class WhisperSystem {
    
    private final Map<CommandSender, WeakReference<CommandSender>> replyMap = new WeakHashMap<>();
    
    @RegisterCommand(
            permission = "perfection.command.tell",
            description = "Sends a private message to a designated user",
            alias = {"whisper", "w", "msg", "pm"},
            override = true
    )
    public void tell(Invoker invoker, Player target, @GlobAll String message){
        message(invoker, target, message);
    }
    
    @RegisterCommand(
            permission = "perfection.command.tell",
            description = "Replies to the last person to whisper you",
            alias = {"r"},
            override = true
    )
    public void reply(Invoker invoker, @GlobAll String message){
        WeakReference<CommandSender> ref = replyMap.get(invoker.as(CommandSender.class));
        if(ref != null){
            CommandSender target = ref.get();
            if(target != null){
                message(invoker, target, message);
                return;
            }
        }
        invoker.sendMessage("%sNo one has sent you a message!", ChatColor.RED);
    }
    
    @RegisterCommand(
            permission = "perfection.command.say",
            description = "Say something in an administrative way.",
            usage = "/say <message ...>"
    )
    public void say(Invoker invoker, @GlobAll String message){
        CommandSender sender = invoker.as(CommandSender.class);
        if(sender instanceof ConsoleCommandSender){
            Bukkit.broadcastMessage("\u262F" + ChatColor.RED + "Console-tan" + ChatColor.RESET + "\u262F " + message);
        } else {
            Bukkit.broadcastMessage("\u262F" + ChatColor.RED + sender.getName() + ChatColor.RESET + "\u262F " + message);
        }
    }
    
    private void message(Invoker source, CommandSender target, String message){
        source.sendMessage(
                ChatColor.LIGHT_PURPLE + "To %s: %s",
                target.getName(),
                message
        );
        target.sendMessage(String.format(
                ChatColor.LIGHT_PURPLE + "From %s: %s",
                source.getName(),
                message
        ));
        replyMap.put(target, new WeakReference<>(source.as(CommandSender.class)));
    }

}
