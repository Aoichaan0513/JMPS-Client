package org.aoichaan0513.jmps.Command;

import org.aoichaan0513.jmps.API.CoreAPI;
import org.aoichaan0513.jmps.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Admin implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;

    public Admin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (CoreAPI.hasPlayer(sender)) {
            Player sp = (Player) sender;
            if (CoreAPI.hasPermission(sp, 3)) {
                if (args.length != 0) {
                    if (args[0].equalsIgnoreCase("add")) {
                        if (args.length != 1) {
                            if (CoreAPI.isOnlinePlayer(args[1])) {
                                Player p = Bukkit.getPlayerExact(args[1]);
                                if (CoreAPI.isPlayed(p)) {
                                    if (!CoreAPI.hasPermission(p, 3)) {
                                        if (!CoreAPI.hasPermission(p)) {
                                            String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"type\": \"add\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"server\": \"" + Main.getServerId() + "\", \"authority\": 2}";
                                            String result = CoreAPI.sendPost("Permission.php", json);

                                            try {
                                                JSONObject jsonObject = new JSONObject(result);
                                                if (jsonObject.getBoolean("result")) {
                                                    sender.sendMessage(CoreAPI.getSuccessPrefix() + p.getName() + "に権限を付与しました。");
                                                    return true;
                                                } else {
                                                    sender.sendMessage(CoreAPI.getErrorPrefix() + p.getName() + "に権限を付与できませんでした。\n" +
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
                                        sender.sendMessage(CoreAPI.getErrorPrefix() + p.getName() + "はすでに権限を持っています。");
                                        return true;
                                    }
                                    sender.sendMessage(CoreAPI.getErrorPrefix() + p.getName() + "はサーバーオーナーのため実行できません。");
                                    return true;
                                }
                                sender.sendMessage(CoreAPI.getErrorPrefix() + p.getName() + "は1度もこのサーバーに参加したことがありません。");
                                return true;
                            } else {
                                OfflinePlayer op = Bukkit.getOfflinePlayer(args[1]);
                                if (CoreAPI.isPlayed(op)) {
                                    if (!CoreAPI.hasPermission(op, 3)) {
                                        if (!CoreAPI.hasPermission(op)) {
                                            String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"type\": \"add\", \"uuid\": \"" + op.getUniqueId().toString() + "\", \"server\": \"" + Main.getServerId() + "\", \"authority\": 2}";
                                            String result = CoreAPI.sendPost("Permission.php", json);

                                            try {
                                                JSONObject jsonObject = new JSONObject(result);
                                                if (jsonObject.getBoolean("result")) {
                                                    sender.sendMessage(CoreAPI.getSuccessPrefix() + op.getName() + "に権限を付与しました。");
                                                    return true;
                                                } else {
                                                    sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "に権限を付与できませんでした。\n" +
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
                                        sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "はすでに権限を持っています。");
                                        return true;
                                    }
                                    sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "はサーバーオーナーのため実行できません。");
                                    return true;
                                }
                                sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "は1度もこのサーバーに参加したことがありません。");
                                return true;
                            }
                        }
                        sender.sendMessage(CoreAPI.getErrorPrefix() + "引数が不正です。\n" +
                                CoreAPI.getErrorPrefix() + "コマンドの使い方: /" + label + " <add|remove> <プレイヤー名>");
                        return true;
                    } else if (args[0].equalsIgnoreCase("remove")) {
                        if (args.length != 1) {
                            if (CoreAPI.isOnlinePlayer(args[1])) {
                                Player p = Bukkit.getPlayerExact(args[1]);
                                if (CoreAPI.isPlayed(p)) {
                                    if (!CoreAPI.hasPermission(p, 3)) {
                                        if (CoreAPI.hasPermission(p)) {
                                            String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"type\": \"remove\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"server\": \"" + Main.getServerId() + "\", \"authority\": 2}";
                                            String result = CoreAPI.sendPost("Permission.php", json);

                                            try {
                                                JSONObject jsonObject = new JSONObject(result);
                                                if (jsonObject.getBoolean("result")) {
                                                    sender.sendMessage(CoreAPI.getSuccessPrefix() + p.getName() + "の権限をはく奪しました。");
                                                    return true;
                                                } else {
                                                    sender.sendMessage(CoreAPI.getErrorPrefix() + p.getName() + "の権限をはく奪できませんでした。\n" +
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
                                        sender.sendMessage(CoreAPI.getErrorPrefix() + p.getName() + "はすでに権限を持っていません。");
                                        return true;
                                    }
                                    sender.sendMessage(CoreAPI.getErrorPrefix() + p.getName() + "はサーバーオーナーのため実行できません。");
                                    return true;
                                }
                                sender.sendMessage(CoreAPI.getErrorPrefix() + p.getName() + "は1度もこのサーバーに参加したことがありません。");
                                return true;
                            } else {
                                OfflinePlayer op = Bukkit.getOfflinePlayer(args[1]);
                                if (CoreAPI.isPlayed(op)) {
                                    if (!CoreAPI.hasPermission(op, 3)) {
                                        if (CoreAPI.hasPermission(op)) {
                                            String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"type\": \"remove\", \"uuid\": \"" + op.getUniqueId().toString() + "\", \"server\": \"" + Main.getServerId() + "\", \"authority\": 2}";
                                            String result = CoreAPI.sendPost("Permission.php", json);

                                            try {
                                                JSONObject jsonObject = new JSONObject(result);
                                                if (jsonObject.getBoolean("result")) {
                                                    sender.sendMessage(CoreAPI.getSuccessPrefix() + op.getName() + "の権限をはく奪しました。");
                                                    return true;
                                                } else {
                                                    sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "の権限をはく奪できませんでした。\n" +
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
                                        sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "はすでに権限を持っていません。");
                                        return true;
                                    }
                                    sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "はサーバーオーナーのため実行できません。");
                                    return true;
                                }
                                sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "は1度もこのサーバーに参加したことがありません。");
                                return true;
                            }
                        }
                        sender.sendMessage(CoreAPI.getErrorPrefix() + "引数が不正です。\n" +
                                CoreAPI.getErrorPrefix() + "コマンドの使い方: /" + label + " <add|remove> <プレイヤー名>");
                        return true;
                    }
                    sender.sendMessage(CoreAPI.getErrorPrefix() + "引数が不正です。\n" +
                            CoreAPI.getErrorPrefix() + "コマンドの使い方: /" + label + " <add|remove> <プレイヤー名>");
                    return true;
                }
                sender.sendMessage(CoreAPI.getErrorPrefix() + "引数が不正です。\n" +
                        CoreAPI.getErrorPrefix() + "コマンドの使い方: /" + label + " <add|remove> <プレイヤー名>");
                return true;
            }
            sender.sendMessage(CoreAPI.getErrorPrefix() + "権限がありません。");
            return true;
        } else {
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!command.getName().equalsIgnoreCase("admin")) return null;
        if (sender instanceof Player) {
            Player sp = (Player) sender;
            if (sp.isOp()) {
                if (args.length == 1) {
                    if (args[0].length() == 0) {
                        return Arrays.asList("add", "remove");
                    } else {
                        if ("add".startsWith(args[0])) {
                            return Collections.singletonList("add");
                        } else if ("remove".startsWith(args[0])) {
                            return Collections.singletonList("remove");
                        }
                    }
                }
            }
        }
        return null;
    }
}
