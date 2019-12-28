package org.aoichaan0513.jmps.API;

import org.aoichaan0513.jmps.Main;
import org.aoichaan0513.jmps.Util.Server;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.JSONObject;

public class VoteManager {

    private static final int voteSec = 20;
    private static final String senderName = "Vote System";

    private static Player player = null;
    private static CoreAPI.PunishType punishType = null;
    private static int voteYes = 0;
    private static int voteNo = 0;

    public static boolean startVote(Player p, CoreAPI.PunishType type, String reason) {
        if (player != null && punishType != null) return false;

        player = p;
        punishType = type;

        sendMessage(p, reason);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (voteYes >= voteNo) {
                    if (punishType == CoreAPI.PunishType.WARNING) {
                        String r = reason.trim();

                        String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"name\": \"" + p.getName() + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"reason\": \"" + r + "\", \"byName\": \"" + senderName + "\", \"byUUID\": \"f78a4d8d-d51b-4b39-98a3-230f2de0c670\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
                        String result = CoreAPI.sendPost("Warn.php", json);

                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            if (jsonObject.getBoolean("result")) {
                                p.sendMessage(CoreAPI.getErrorPrefix() + "あなたは警告されました。\n" +
                                        CoreAPI.getInfoPrefix() + "処罰理由: " + r + "\n" +
                                        CoreAPI.getInfoPrefix() + "処罰者: " + senderName);
                                Bukkit.broadcastMessage(CoreAPI.getSuccessPrefix() + p.getName() + "を警告しました。\n" +
                                        CoreAPI.getInfoPrefix() + "処罰理由: " + r);
                                return;
                            } else {
                                Bukkit.broadcastMessage(CoreAPI.getErrorPrefix() + p.getName() + "を警告できませんでした。\n" +
                                        CoreAPI.getInfoPrefix() + "理由: " + jsonObject.getString("reason"));
                                return;
                            }
                        } catch (Exception e) {
                            Bukkit.broadcastMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。\n" +
                                    CoreAPI.getInfoPrefix() + "エラーの詳細はコンソールで確認できます。");
                            Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。");
                            e.printStackTrace();
                            return;
                        }
                    } else if (punishType == CoreAPI.PunishType.MUTE) {
                        String r = reason.trim();

                        String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"name\": \"" + p.getName() + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"reason\": \"" + r + "\", \"byName\": \"" + senderName + "\", \"byUUID\": \"f78a4d8d-d51b-4b39-98a3-230f2de0c670\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
                        String result = CoreAPI.sendPost("Mute.php", json);

                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            if (jsonObject.getBoolean("result")) {
                                Bukkit.broadcastMessage(CoreAPI.getErrorPrefix() + "あなたはミュートされました。\n" +
                                        CoreAPI.getInfoPrefix() + "処罰理由: " + r + "\n" +
                                        CoreAPI.getInfoPrefix() + "処罰者: " + senderName);
                                Bukkit.broadcastMessage(CoreAPI.getSuccessPrefix() + p.getName() + "をミュートしました。\n" +
                                        CoreAPI.getInfoPrefix() + "処罰理由: " + r);
                                return;
                            } else {
                                Bukkit.broadcastMessage(CoreAPI.getErrorPrefix() + p.getName() + "をミュートできませんでした。\n" +
                                        CoreAPI.getInfoPrefix() + "理由: " + jsonObject.getString("reason"));
                                return;
                            }
                        } catch (Exception e) {
                            Bukkit.broadcastMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。\n" +
                                    CoreAPI.getInfoPrefix() + "エラーの詳細はコンソールで確認できます。");
                            Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。");
                            e.printStackTrace();
                            return;
                        }
                    } else if (punishType == CoreAPI.PunishType.KICK) {
                        String r = reason.trim();

                        String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"name\": \"" + p.getName() + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"reason\": \"" + r + "\", \"byName\": \"" + senderName + "\", \"byUUID\": \"f78a4d8d-d51b-4b39-98a3-230f2de0c670\", \"server\": \"" + new Server().getId() + "\"}";
                        String result = CoreAPI.sendPost("Kick.php", json);

                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            if (jsonObject.getBoolean("result")) {
                                p.kickPlayer(ChatColor.RED + "あなたはキックされました。\n" +
                                        ChatColor.GRAY + "処罰理由: " + r + "\n" +
                                        ChatColor.GRAY + "処罰者: " + senderName);
                                Bukkit.broadcastMessage(CoreAPI.getSuccessPrefix() + p.getName() + "をキックしました。\n" +
                                        CoreAPI.getInfoPrefix() + "処罰理由: " + r);
                                return;
                            } else {
                                Bukkit.broadcastMessage(CoreAPI.getErrorPrefix() + p.getName() + "をキックできませんでした。\n" +
                                        CoreAPI.getInfoPrefix() + "理由: " + jsonObject.getString("reason"));
                                return;
                            }
                        } catch (Exception e) {
                            Bukkit.broadcastMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。\n" +
                                    CoreAPI.getInfoPrefix() + "エラーの詳細はコンソールで確認できます。");
                            Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。");
                            e.printStackTrace();
                            return;
                        }
                    }
                } else {

                }
            }
        }.runTaskLaterAsynchronously(Main.getInstance(), 20 * voteSec);
        return true;
    }

    private static void sendMessage(Player p, String reason) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (p.getUniqueId() == player.getUniqueId()) continue;
            player.sendMessage(CoreAPI.getInfoPrefix() + "投票を開始しました。\n" +
                    CoreAPI.getWarningPrefix() + "被処罰者" + ChatColor.GRAY + ": " + ChatColor.YELLOW + p.getName() + "\n" +
                    CoreAPI.getWarningPrefix() + "理由: " + ChatColor.GRAY + ": " + ChatColor.YELLOW + reason + "\n" +
                    CoreAPI.getWarningPrefix() + "この投票に" + ChatColor.UNDERLINE + "賛成" + ChatColor.RESET + ChatColor.GOLD + "の場合は\"/v yes\"を、\n" +
                    CoreAPI.getWarningPrefix() + "この投票に" + ChatColor.UNDERLINE + "反対" + ChatColor.RESET + ChatColor.GOLD + "の場合は\"/v no\"を入力してください。");
        }
    }
}
