package me.cunzai.bilichecker.command;

import me.cunzai.bilichecker.Main;
import me.cunzai.bilichecker.data.Data;
import me.cunzai.bilichecker.util.StringUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


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

        if (strings.length == 0 && Data.dataMap.get(player.getUniqueId()) == null) {
            player.sendMessage(StringUtil.cf(Main.getIns().getConfigManager().getNotInt()));
            return true;
        }

        if (strings.length == 1) {
            if (Data.dataMap.get(player.getUniqueId()) != null) {
                Data data = Data.dataMap.get(player.getUniqueId());
                if ((System.currentTimeMillis() - data.getLastTime()) / 1000 < Main.getIns().getConfigManager().getLimit()) {
                    player.sendMessage(StringUtil.cf(Main.getIns().getConfigManager().getInCoolDown()));
                    return true;
                }
            }
            if (StringUtil.isNum(strings[0])) {
                if (!Main.getIns().getConfigManager().isVerified(strings[0])) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            String sign = Main.getIns().getRequest().check(strings[0]);
                            if (sign != null) {
                                String str = StringUtil.getRandomString(10);
                                Data data = new Data(player.getUniqueId(), strings[0], System.currentTimeMillis(), str, sign);
                                Data.dataMap.put(player.getUniqueId(), data);

                                player.sendMessage(StringUtil.cf(Main.getIns().getConfigManager().getVerifySetup1().replace("<Sign>", Main.getIns().getConfigManager().getVerify() + str)));
                                TextComponent text = new TextComponent(StringUtil.cf("&a点击复制"));
                                text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringUtil.cf("&a点击复制")).create()));
                                text.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "服务器主播验证:" + str));

                                player.spigot().sendMessage(text);
                            } else {
                                player.sendMessage(StringUtil.cf(Main.getIns().getConfigManager().getFailedToPass().replaceAll("<Fans>", "" + Main.getIns().getConfigManager().getFans()).replaceAll("<Views", "" + Main.getIns().getConfigManager().getViews())));
                            }
                        }
                    }.runTaskAsynchronously(Main.getIns());
                    return true;
                } else {
                    player.sendMessage(StringUtil.cf(Main.getIns().getConfigManager().getVerified()));
                    return true;
                }
            } else {
                player.sendMessage(StringUtil.cf(Main.getIns().getConfigManager().getNotInt()));
                return true;
            }
        }
        if (strings.length == 0) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (Main.getIns().getRequest().checkSign(Data.dataMap.get(player.getUniqueId()))) {
                        Main.getIns().getConfigManager().setVerified(Data.dataMap.get(player.getUniqueId()).getMid());

                        new BukkitRunnable() {
                            @Override
                            public void run() {

                                Main.getIns().getConfigManager().getCommands().forEach(command -> {
                                    command = command.replaceAll("<Player>", player.getName());
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                                });

                                player.sendMessage(StringUtil.cf(Main.getIns().getConfigManager().getSuccess()));
                                TextComponent text = new TextComponent(StringUtil.cf("&a点击复制你的原始签名以还原签名"));
                                text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringUtil.cf("&a点击复制")).create()));
                                text.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, Data.dataMap.get(player.getUniqueId()).getSign()));
                                player.spigot().sendMessage(text);

                            }
                        }.runTask(Main.getIns());
                    } else {
                        player.sendMessage(StringUtil.cf(Main.getIns().getConfigManager().getNotVerified()));
                    }
                }
            }.runTaskAsynchronously(Main.getIns());
            return true;
        }

        return false;
    }
}
