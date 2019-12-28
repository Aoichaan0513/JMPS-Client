package org.aoichaan0513.jmps.Command;

import org.aoichaan0513.jmps.API.CoreAPI;
import org.aoichaan0513.jmps.Main;
import org.aoichaan0513.jmps.Util.Server;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONObject;

public class UnMute implements CommandExecutor {
    JavaPlugin plugin;

    public UnMute(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (CoreAPI.hasPlayer(sender)) {
            Player sp = (Player) sender;
            if (CoreAPI.hasPermission(sp, 1)) {
                if (args.length != 0) {
                    if (CoreAPI.isOnlinePlayer(args[0])) {
                        Player op = Bukkit.getPlayerExact(args[0]);
                        if (CoreAPI.isPlayed(op)) {
                            if (CoreAPI.isPunished(op, CoreAPI.PunishType.MUTE)) {
                                String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + op.getUniqueId().toString() + "\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
                                String result = CoreAPI.sendPost("UnMute.php", json);

                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    if (jsonObject.getBoolean("result")) {
                                        sender.sendMessage(CoreAPI.getSuccessPrefix() + op.getName() + "のミュートを解除しました。");
                                        return true;
                                    } else {
                                        sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "のミュートを解除できませんでした。\n" +
                                                CoreAPI.getInfoPrefix() + "理由: " + jsonObject.getString("reason"));
                                        return true;
                                    }
                                } catch (Exception e) {
                                    sender.sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。\n" +
                                            CoreAPI.getInfoPrefix() + "エラーの詳細はコンソールで確認できます。");
                                    Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。");
                                    e.printStackTrace();
                                    return true;
                                }
                            }
                            sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "はすでにこのサーバーでミュートされていません。");
                            return true;
                        }
                        sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "は1度もこのサーバーに参加したことがありません。");
                        return true;
                    } else {
                        OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                        if (CoreAPI.isPlayed(op)) {
                            if (CoreAPI.isPunished(op, CoreAPI.PunishType.MUTE)) {
                                String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + op.getUniqueId().toString() + "\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
                                String result = CoreAPI.sendPost("UnMute.php", json);

                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    if (jsonObject.getBoolean("result")) {
                                        sender.sendMessage(CoreAPI.getSuccessPrefix() + op.getName() + "のミュートを解除しました。");
                                        return true;
                                    } else {
                                        sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "のミュートを解除できませんでした。\n" +
                                                CoreAPI.getInfoPrefix() + "理由: " + jsonObject.getString("reason"));
                                        return true;
                                    }
                                } catch (Exception e) {
                                    sender.sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。\n" +
                                            CoreAPI.getInfoPrefix() + "エラーの詳細はコンソールで確認できます。");
                                    Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。");
                                    e.printStackTrace();
                                    return true;
                                }
                            }
                            sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "はすでにこのサーバーでミュートされていません。");
                            return true;
                        }
                        sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "は1度もこのサーバーに参加したことがありません。");
                        return true;
                    }
                }
                sender.sendMessage(CoreAPI.getErrorPrefix() + "引数が不正です。\n" +
                        CoreAPI.getErrorPrefix() + "コマンドの使い方: /" + label + " <プレイヤー名>");
                return true;
            }
            sender.sendMessage(CoreAPI.getErrorPrefix() + "権限がありません。");
            return true;
        } else {
            if (args.length != 0) {
                if (CoreAPI.isOnlinePlayer(args[0])) {
                    Player op = Bukkit.getPlayerExact(args[0]);
                    if (CoreAPI.isPlayed(op)) {
                        if (CoreAPI.isPunished(op, CoreAPI.PunishType.MUTE)) {
                            String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + op.getUniqueId().toString() + "\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
                            String result = CoreAPI.sendPost("UnMute.php", json);

                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                if (jsonObject.getBoolean("result")) {
                                    sender.sendMessage(CoreAPI.getSuccessPrefix() + op.getName() + "のミュートを解除しました。");
                                    return true;
                                } else {
                                    sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "のミュートを解除できませんでした。\n" +
                                            CoreAPI.getInfoPrefix() + "理由: " + jsonObject.getString("reason"));
                                    return true;
                                }
                            } catch (Exception e) {
                                sender.sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。\n" +
                                        CoreAPI.getInfoPrefix() + "エラーの詳細はコンソールで確認できます。");
                                Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。");
                                e.printStackTrace();
                                return true;
                            }
                        }
                        sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "はすでにこのサーバーでミュートされていません。");
                        return true;
                    }
                    sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "は1度もこのサーバーに参加したことがありません。");
                    return true;
                } else {
                    OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                    if (CoreAPI.isPlayed(op)) {
                        if (CoreAPI.isPunished(op, CoreAPI.PunishType.MUTE)) {
                            String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + op.getUniqueId().toString() + "\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
                            String result = CoreAPI.sendPost("UnMute.php", json);

                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                if (jsonObject.getBoolean("result")) {
                                    sender.sendMessage(CoreAPI.getSuccessPrefix() + op.getName() + "のミュートを解除しました。");
                                    return true;
                                } else {
                                    sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "のミュートを解除できませんでした。\n" +
                                            CoreAPI.getInfoPrefix() + "理由: " + jsonObject.getString("reason"));
                                    return true;
                                }
                            } catch (Exception e) {
                                sender.sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。\n" +
                                        CoreAPI.getInfoPrefix() + "エラーの詳細はコンソールで確認できます。");
                                Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。");
                                e.printStackTrace();
                                return true;
                            }
                        }
                        sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "はすでにこのサーバーでミュートされていません。");
                        return true;
                    }
                    sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "は1度もこのサーバーに参加したことがありません。");
                    return true;
                }
            }
            sender.sendMessage(CoreAPI.getErrorPrefix() + "引数が不正です。\n" +
                    CoreAPI.getErrorPrefix() + "コマンドの使い方: /" + label + " <プレイヤー名>");
            return true;
        }
    }
}
