package org.aoichaan0513.jmps.Listener;

import org.aoichaan0513.jmps.API.CoreAPI;
import org.aoichaan0513.jmps.Main;
import org.aoichaan0513.jmps.Util.Server;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.json.JSONObject;

public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerPreLogin(PlayerPreLoginEvent e) {
        if (!e.getAddress().getHostAddress().equals("127.0.0.1")) {
            CoreAPI.sendPost("Address.php", "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + e.getUniqueId().toString() + "\", \"address\": \"" + e.getAddress().getHostAddress() + "\"}");
        }
    }

    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent e) {
        OfflinePlayer p = Bukkit.getOfflinePlayer(e.getUniqueId());

        if (CoreAPI.isPunished(p, CoreAPI.PunishType.GLOBAL)) {
            String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
            String result = CoreAPI.sendPost("getGBan.php", json);

            try {
                JSONObject jsonObject = new JSONObject(result);
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, "\n" + ChatColor.RED + "あなたはグローバルBanされています。\n" +
                        ChatColor.GRAY + "処罰ID: " + jsonObject.getString("id") + "\n" +
                        ChatColor.GRAY + "処罰理由: " + jsonObject.getString("reason") + "\n" +
                        ChatColor.GRAY + "処罰者: " + jsonObject.getString("by") + "\n" +
                        ChatColor.GRAY + "処罰サーバー: " + new Server(jsonObject.getString("server")).getName() + "\n\n" +
                        ChatColor.GRAY + "処罰情報は\"http://punish.aoichaan0513.xyz/gban/" + jsonObject.getString("id") + "\"で確認ができます。");
                Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + p.getName() + " (" + p.getUniqueId() + ")はグローバルBanされています。\n" +
                        CoreAPI.getInfoPrefix() + "処罰ID: " + jsonObject.getString("id") + "\n" +
                        CoreAPI.getInfoPrefix() + "処罰理由: " + jsonObject.getString("reason") + "\n" +
                        CoreAPI.getInfoPrefix() + "処罰者: " + jsonObject.getString("by") + "\n" +
                        CoreAPI.getInfoPrefix() + "処罰サーバー: " + new Server(jsonObject.getString("server")).getName());
                return;
            } catch (Exception err) {
                Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。");
                err.printStackTrace();
                return;
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(CoreAPI.getSuccessPrefix() + p.getName() + " (" + p.getUniqueId() + ")はグローバルBanされていません。");
            if (CoreAPI.isPunished(p, CoreAPI.PunishType.TEMPORARY)) {
                String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
                String result = CoreAPI.sendPost("getTBan.php", json);

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, "\n" + ChatColor.RED + "あなたは期限Banされています。\n" +
                            ChatColor.GRAY + "処罰ID: " + jsonObject.getString("id") + "\n" +
                            ChatColor.GRAY + "処罰理由: " + jsonObject.getString("reason") + "\n" +
                            ChatColor.GRAY + "処罰者: " + jsonObject.getString("by") + "\n" +
                            ChatColor.GRAY + "処罰解除日時: " + jsonObject.getString("date") + "\n\n" +
                            ChatColor.GRAY + "処罰情報は\"http://punish.aoichaan0513.xyz/tban/" + jsonObject.getString("id") + "\"で確認ができます。");
                    Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + p.getName() + " (" + p.getUniqueId() + ")は期限Banされています。\n" +
                            CoreAPI.getInfoPrefix() + "処罰ID: " + jsonObject.getString("id") + "\n" +
                            CoreAPI.getInfoPrefix() + "処罰理由: " + jsonObject.getString("reason") + "\n" +
                            CoreAPI.getInfoPrefix() + "処罰者: " + jsonObject.getString("by") + "\n" +
                            CoreAPI.getInfoPrefix() + "処罰解除日時: " + jsonObject.getString("date"));
                    return;
                } catch (Exception err) {
                    Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。");
                    err.printStackTrace();
                    return;
                }
            } else {
                Bukkit.getConsoleSender().sendMessage(CoreAPI.getSuccessPrefix() + p.getName() + " (" + p.getUniqueId() + ")は期限Banされていません。");
                if (CoreAPI.isPunished(p, CoreAPI.PunishType.LOCAL)) {
                    String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
                    String result = CoreAPI.sendPost("getLBan.php", json);

                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, "\n" + ChatColor.RED + "あなたはローカルBanされています。\n" +
                                ChatColor.GRAY + "処罰ID: " + jsonObject.getString("id") + "\n" +
                                ChatColor.GRAY + "処罰理由: " + jsonObject.getString("reason") + "\n" +
                                ChatColor.GRAY + "処罰者: " + jsonObject.getString("by") + "\n" +
                                ChatColor.GRAY + "処罰情報は\"http://punish.aoichaan0513.xyz/lban/" + jsonObject.getString("id") + "\"で確認ができます。");
                        Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + p.getName() + " (" + p.getUniqueId() + ")はローカルBanされています。\n" +
                                CoreAPI.getInfoPrefix() + "処罰ID: " + jsonObject.getString("id") + "\n" +
                                CoreAPI.getInfoPrefix() + "処罰理由: " + jsonObject.getString("reason") + "\n" +
                                CoreAPI.getInfoPrefix() + "処罰者: " + jsonObject.getString("by"));
                        return;
                    } catch (Exception err) {
                        Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。");
                        err.printStackTrace();
                        return;
                    }
                } else {
                    Bukkit.getConsoleSender().sendMessage(CoreAPI.getSuccessPrefix() + p.getName() + " (" + p.getUniqueId() + ")はローカルBanされていません。");
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (CoreAPI.hasPermission(player, 1)) {
                player.sendMessage(ChatColor.GRAY + "-> " + p.getName() + " (" + CoreAPI.getCountryCode(p.getAddress()) + ")");

                if (!CoreAPI.getCountryCode(p.getAddress()).equals("JP")) {
                    player.sendMessage(CoreAPI.getErrorPrefix() + "警告\n" +
                            CoreAPI.getErrorPrefix() + "日本以外からのログインを検知しました。\n" +
                            CoreAPI.getErrorPrefix() + "念の為ユーザーに注意してください。\n" +
                            CoreAPI.getInfoPrefix() + "プレイヤー: " + p.getName() + "\n" +
                            CoreAPI.getInfoPrefix() + "参加国: " + CoreAPI.getCountry(p.getAddress()) + " (" + CoreAPI.getCountryCode(p.getAddress()) + ")");
                }
            }
        }

        if (CoreAPI.isPunished(p, CoreAPI.PunishType.MUTE)) {
            String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
            String result = CoreAPI.sendPost("getMute.php", json);

            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getBoolean("result")) {
                    p.sendMessage(CoreAPI.getErrorPrefix() + "あなたはミュートされています。\n" +
                            CoreAPI.getInfoPrefix() + "処罰理由: " + jsonObject.getString("reason"));
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (CoreAPI.isPunished(p, CoreAPI.PunishType.WARNING)) {
            String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
            String result = CoreAPI.sendPost("getWarn.php", json);

            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getBoolean("result")) {
                    p.sendMessage(CoreAPI.getErrorPrefix() + "あなたは警告されています。\n" +
                            CoreAPI.getInfoPrefix() + "処罰理由: " + jsonObject.getString("reason"));
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (CoreAPI.hasPermission(player, 1)) {
                player.sendMessage(ChatColor.GRAY + "<- " + p.getName() + " (" + CoreAPI.getCountryCode(p.getAddress()) + ")");
            }
        }
    }

    @EventHandler
    public void onAsyncChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        if (CoreAPI.isPunished(p, CoreAPI.PunishType.MUTE)) {
            e.setCancelled(true);
            p.sendMessage(CoreAPI.getErrorPrefix() + "あなたはミュートされています。");
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String cmd = e.getMessage();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (CoreAPI.hasPermission(player, 1)) {
                player.sendMessage(CoreAPI.getInfoPrefix() + p.getName() + ": " + cmd);
            }
        }
    }
}
