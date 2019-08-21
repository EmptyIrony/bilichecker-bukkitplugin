package me.cunzai.bilichecker.command;

import me.cunzai.bilichecker.Main;
import me.cunzai.bilichecker.util.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BiliCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length==1&&strings[0].equalsIgnoreCase("reload")&&commandSender.hasPermission("bili.admin")){

            Main.getIns().getConfigManager().loadConfig();
            commandSender.sendMessage(StringUtil.cf("&aBiliChecker配置文件已重载"));

            return true;
        }

        if (!(commandSender instanceof Player)){
            commandSender.sendMessage("Player Only!");
            return true;
        }
        Player player = (Player) commandSender;

        if (strings.length==0&&Main.getIns().getRequest().getPassed().get(player.getUniqueId())==null){

        }
        return false;
    }
}
