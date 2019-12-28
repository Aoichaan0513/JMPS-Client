package org.aoichaan0513.jmps.Command;

import org.aoichaan0513.jmps.API.CoreAPI;
import org.aoichaan0513.jmps.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONObject;

import java.net.InetSocketAddress;

public class Whois implements CommandExecutor {
    JavaPlugin plugin;

    public Whois(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (CoreAPI.hasPlayer(sender)) {
            Player sp = (Player) sender;
            if (CoreAPI.hasPermission(sp, 1)) {
                if (args.length != 0) {
                    if (CoreAPI.isOnlinePlayer(args[0])) {
                        Player p = Bukkit.getPlayerExact(args[0]);
                        sender.sendMessage(CoreAPI.getSuccessPrefix() + p.getName() + " (" + p.getUniqueId() + ")の検索結果\n" +
                                CoreAPI.getWarningPrefix() + "プレイヤー名" + ChatColor.GRAY + ": " + ChatColor.YELLOW + p.getName() + "\n" +
                                CoreAPI.getWarningPrefix() + "UUID" + ChatColor.GRAY + ": " + ChatColor.YELLOW + p.getUniqueId() + "\n" +
                                CoreAPI.getWarningPrefix() + "権限" + ChatColor.GRAY + ": " + ChatColor.YELLOW + CoreAPI.getPermissionName(CoreAPI.getPermission(p)) + "\n" +
                                CoreAPI.getWarningPrefix() + "アドレス" + ChatColor.GRAY + ": " + ChatColor.YELLOW + p.getAddress().getAddress().getHostAddress() + "\n" +
                                CoreAPI.getWarningPrefix() + "参加国" + ChatColor.GRAY + ": " + ChatColor.YELLOW + CoreAPI.getCountry(p.getAddress()) + ChatColor.GRAY + " (" + CoreAPI.getCountryCode(p.getAddress()) + ")" + "\n" +
                                CoreAPI.getWarningPrefix() + "参加済み" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (CoreAPI.isPlayed(p) ? "はい" : "いいえ") + "\n" +
                                CoreAPI.getWarningPrefix() + "OP" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (p.isOp() ? "はい" : "いいえ"));
                        return true;
                    } else {
                        OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                        String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + op.getUniqueId().toString() + "\"}";
                        String result = CoreAPI.sendPost("getAddress.php", json);

                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            if (jsonObject.getBoolean("result")) {
                                sender.sendMessage(CoreAPI.getSuccessPrefix() + op.getName() + " (" + op.getUniqueId() + ")の検索結果\n" +
                                        CoreAPI.getWarningPrefix() + "プレイヤー名" + ChatColor.GRAY + ": " + ChatColor.YELLOW + op.getName() + "\n" +
                                        CoreAPI.getWarningPrefix() + "UUID" + ChatColor.GRAY + ": " + ChatColor.YELLOW + op.getUniqueId() + "\n" +
                                        CoreAPI.getWarningPrefix() + "権限" + ChatColor.GRAY + ": " + ChatColor.YELLOW + CoreAPI.getPermissionName(CoreAPI.getPermission(op)) + "\n" +
                                        CoreAPI.getWarningPrefix() + "アドレス" + ChatColor.GRAY + ": " + ChatColor.YELLOW + jsonObject.getString("ip") + ChatColor.GRAY + " (" + jsonObject.getString("date") + ")" + "\n" +
                                        CoreAPI.getWarningPrefix() + "参加国" + ChatColor.GRAY + ": " + ChatColor.YELLOW + CoreAPI.getCountry(new InetSocketAddress(jsonObject.getString("ip"), 0)) + ChatColor.GRAY + " (" + CoreAPI.getCountryCode(new InetSocketAddress(jsonObject.getString("ip"), 0)) + ")" + "\n" +
                                        CoreAPI.getWarningPrefix() + "参加済み" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (CoreAPI.isPlayed(op) ? "はい" : "いいえ") + "\n" +
                                        CoreAPI.getWarningPrefix() + "OP" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (op.isOp() ? "はい" : "いいえ"));
                                return true;
                            } else {
                                sender.sendMessage(CoreAPI.getSuccessPrefix() + op.getName() + " (" + op.getUniqueId() + ")の検索結果\n" +
                                        CoreAPI.getWarningPrefix() + "プレイヤー名" + ChatColor.GRAY + ": " + ChatColor.YELLOW + op.getName() + "\n" +
                                        CoreAPI.getWarningPrefix() + "UUID" + ChatColor.GRAY + ": " + ChatColor.YELLOW + op.getUniqueId() + "\n" +
                                        CoreAPI.getWarningPrefix() + "権限" + ChatColor.GRAY + ": " + ChatColor.YELLOW + CoreAPI.getPermissionName(CoreAPI.getPermission(op)) + "\n" +
                                        CoreAPI.getWarningPrefix() + "アドレス" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "データがありません。\n" +
                                        CoreAPI.getWarningPrefix() + "参加国" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "データがありません。\n" +
                                        CoreAPI.getWarningPrefix() + "参加済み" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (CoreAPI.isPlayed(op) ? "はい" : "いいえ") + "\n" +
                                        CoreAPI.getWarningPrefix() + "OP" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (op.isOp() ? "はい" : "いいえ"));
                                return true;
                            }
                        } catch (Exception e) {
                            sender.sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。\n" +
                                    CoreAPI.getInfoPrefix() + "エラーの詳細はコンソールで確認できます。");
                            Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。");
                            e.printStackTrace();
                            sender.sendMessage(CoreAPI.getSuccessPrefix() + op.getName() + " (" + op.getUniqueId() + ")の検索結果\n" +
                                    CoreAPI.getWarningPrefix() + "プレイヤー名" + ChatColor.GRAY + ": " + ChatColor.YELLOW + op.getName() + "\n" +
                                    CoreAPI.getWarningPrefix() + "UUID" + ChatColor.GRAY + ": " + ChatColor.YELLOW + op.getUniqueId() + "\n" +
                                    CoreAPI.getWarningPrefix() + "権限" + ChatColor.GRAY + ": " + ChatColor.YELLOW + CoreAPI.getPermissionName(CoreAPI.getPermission(op)) + "\n" +
                                    CoreAPI.getWarningPrefix() + "アドレス" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "データがありません。\n" +
                                    CoreAPI.getWarningPrefix() + "参加国" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "データがありません。\n" +
                                    CoreAPI.getWarningPrefix() + "参加済み" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (CoreAPI.isPlayed(op) ? "はい" : "いいえ") + "\n" +
                                    CoreAPI.getWarningPrefix() + "OP" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (op.isOp() ? "はい" : "いいえ"));
                            return true;
                        }
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
                    Player p = Bukkit.getPlayerExact(args[0]);
                    sender.sendMessage(CoreAPI.getSuccessPrefix() + p.getName() + " (" + p.getUniqueId() + ")の検索結果\n" +
                            CoreAPI.getWarningPrefix() + "プレイヤー名" + ChatColor.GRAY + ": " + ChatColor.YELLOW + p.getName() + "\n" +
                            CoreAPI.getWarningPrefix() + "UUID" + ChatColor.GRAY + ": " + ChatColor.YELLOW + p.getUniqueId() + "\n" +
                            CoreAPI.getWarningPrefix() + "権限" + ChatColor.GRAY + ": " + ChatColor.YELLOW + CoreAPI.getPermissionName(CoreAPI.getPermission(p)) + "\n" +
                            CoreAPI.getWarningPrefix() + "アドレス" + ChatColor.GRAY + ": " + ChatColor.YELLOW + p.getAddress().getAddress().getHostAddress() + "\n" +
                            CoreAPI.getWarningPrefix() + "参加国" + ChatColor.GRAY + ": " + ChatColor.YELLOW + CoreAPI.getCountry(p.getAddress()) + ChatColor.GRAY + " (" + CoreAPI.getCountryCode(p.getAddress()) + ")" + "\n" +
                            CoreAPI.getWarningPrefix() + "参加済み" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (CoreAPI.isPlayed(p) ? "はい" : "いいえ") + "\n" +
                            CoreAPI.getWarningPrefix() + "OP" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (p.isOp() ? "はい" : "いいえ"));
                    return true;
                } else {
                    OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                    String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + op.getUniqueId().toString() + "\"}";
                    String result = CoreAPI.sendPost("getAddress.php", json);

                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getBoolean("result")) {
                            sender.sendMessage(CoreAPI.getSuccessPrefix() + op.getName() + " (" + op.getUniqueId() + ")の検索結果\n" +
                                    CoreAPI.getWarningPrefix() + "プレイヤー名" + ChatColor.GRAY + ": " + ChatColor.YELLOW + op.getName() + "\n" +
                                    CoreAPI.getWarningPrefix() + "UUID" + ChatColor.GRAY + ": " + ChatColor.YELLOW + op.getUniqueId() + "\n" +
                                    CoreAPI.getWarningPrefix() + "権限" + ChatColor.GRAY + ": " + ChatColor.YELLOW + CoreAPI.getPermissionName(CoreAPI.getPermission(op)) + "\n" +
                                    CoreAPI.getWarningPrefix() + "アドレス" + ChatColor.GRAY + ": " + ChatColor.YELLOW + jsonObject.getString("ip") + ChatColor.GRAY + " (" + jsonObject.getString("date") + ")" + "\n" +
                                    CoreAPI.getWarningPrefix() + "参加国" + ChatColor.GRAY + ": " + ChatColor.YELLOW + CoreAPI.getCountry(new InetSocketAddress(jsonObject.getString("ip"), 0)) + ChatColor.GRAY + " (" + CoreAPI.getCountryCode(new InetSocketAddress(jsonObject.getString("ip"), 0)) + ")" + "\n" +
                                    CoreAPI.getWarningPrefix() + "参加済み" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (CoreAPI.isPlayed(op) ? "はい" : "いいえ") + "\n" +
                                    CoreAPI.getWarningPrefix() + "OP" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (op.isOp() ? "はい" : "いいえ"));
                            return true;
                        } else {
                            sender.sendMessage(CoreAPI.getSuccessPrefix() + op.getName() + " (" + op.getUniqueId() + ")の検索結果\n" +
                                    CoreAPI.getWarningPrefix() + "プレイヤー名" + ChatColor.GRAY + ": " + ChatColor.YELLOW + op.getName() + "\n" +
                                    CoreAPI.getWarningPrefix() + "UUID" + ChatColor.GRAY + ": " + ChatColor.YELLOW + op.getUniqueId() + "\n" +
                                    CoreAPI.getWarningPrefix() + "権限" + ChatColor.GRAY + ": " + ChatColor.YELLOW + CoreAPI.getPermissionName(CoreAPI.getPermission(op)) + "\n" +
                                    CoreAPI.getWarningPrefix() + "アドレス" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "データがありません。\n" +
                                    CoreAPI.getWarningPrefix() + "参加国" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "データがありません。\n" +
                                    CoreAPI.getWarningPrefix() + "参加済み" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (CoreAPI.isPlayed(op) ? "はい" : "いいえ") + "\n" +
                                    CoreAPI.getWarningPrefix() + "OP" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (op.isOp() ? "はい" : "いいえ"));
                            return true;
                        }
                    } catch (Exception e) {
                        sender.sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。\n" +
                                CoreAPI.getInfoPrefix() + "エラーの詳細はコンソールで確認できます。");
                        Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。");
                        e.printStackTrace();
                        sender.sendMessage(CoreAPI.getSuccessPrefix() + op.getName() + " (" + op.getUniqueId() + ")の検索結果\n" +
                                CoreAPI.getWarningPrefix() + "プレイヤー名" + ChatColor.GRAY + ": " + ChatColor.YELLOW + op.getName() + "\n" +
                                CoreAPI.getWarningPrefix() + "UUID" + ChatColor.GRAY + ": " + ChatColor.YELLOW + op.getUniqueId() + "\n" +
                                CoreAPI.getWarningPrefix() + "権限" + ChatColor.GRAY + ": " + ChatColor.YELLOW + CoreAPI.getPermissionName(CoreAPI.getPermission(op)) + "\n" +
                                CoreAPI.getWarningPrefix() + "アドレス" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "データがありません。\n" +
                                CoreAPI.getWarningPrefix() + "参加国" + ChatColor.GRAY + ": " + ChatColor.YELLOW + "データがありません。\n" +
                                CoreAPI.getWarningPrefix() + "参加済み" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (CoreAPI.isPlayed(op) ? "はい" : "いいえ") + "\n" +
                                CoreAPI.getWarningPrefix() + "OP" + ChatColor.GRAY + ": " + ChatColor.YELLOW + (op.isOp() ? "はい" : "いいえ"));
                        return true;
                    }
                }
            }
            sender.sendMessage(CoreAPI.getErrorPrefix() + "引数が不正です。\n" +
                    CoreAPI.getErrorPrefix() + "コマンドの使い方: /" + label + " <プレイヤー名>");
            return true;
        }
    }
}
