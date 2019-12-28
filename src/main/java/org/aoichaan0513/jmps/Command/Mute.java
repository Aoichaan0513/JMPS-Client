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

public class Mute implements CommandExecutor {
    JavaPlugin plugin;

    public Mute(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (CoreAPI.hasPlayer(sender)) {
            Player sp = (Player) sender;
            if (CoreAPI.hasPermission(sp, 1)) {
                if (args.length != 0) {
                    if (args.length != 1) {
                        String str = CoreAPI.toString(args, 1);
                        if (CoreAPI.isOnlinePlayer(args[0])) {
                            Player p = Bukkit.getPlayerExact(args[0]);
                            if (CoreAPI.isPlayed(p)) {
                                if (!CoreAPI.isPunished(p, CoreAPI.PunishType.MUTE)) {
                                    String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"name\": \"" + p.getName() + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"reason\": \"" + str.trim() + "\", \"byName\": \"" + sender.getName() + "\", \"byUUID\": \"" + ((Player) sender).getUniqueId().toString() + "\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
                                    String result = CoreAPI.sendPost("Mute.php", json);

                                    try {
                                        JSONObject jsonObject = new JSONObject(result);
                                        if (jsonObject.getBoolean("result")) {
                                            p.sendMessage(CoreAPI.getErrorPrefix() + "あなたはミュートされました。\n" +
                                                    CoreAPI.getInfoPrefix() + "処罰理由: " + str.trim() + "\n" +
                                                    CoreAPI.getInfoPrefix() + "処罰者: " + sender.getName());
                                            sender.sendMessage(CoreAPI.getSuccessPrefix() + p.getName() + "をミュートしました。\n" +
                                                    CoreAPI.getInfoPrefix() + "処罰理由: " + str.trim());
                                            return true;
                                        } else {
                                            sender.sendMessage(CoreAPI.getErrorPrefix() + p.getName() + "をミュートできませんでした。\n" +
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
                                sender.sendMessage(CoreAPI.getErrorPrefix() + p.getName() + "はすでにこのサーバーでミュートされています。");
                                return true;
                            }
                            sender.sendMessage(CoreAPI.getErrorPrefix() + p.getName() + "は1度もこのサーバーに参加したことがありません。");
                            return true;
                        } else {
                            OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                            if (CoreAPI.isPlayed(op)) {
                                if (!CoreAPI.isPunished(op, CoreAPI.PunishType.MUTE)) {
                                    String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"name\": \"" + op.getName() + "\", \"uuid\": \"" + op.getUniqueId().toString() + "\", \"reason\": \"" + str.trim() + "\", \"byName\": \"" + sender.getName() + "\", \"byUUID\": \"" + ((Player) sender).getUniqueId().toString() + "\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
                                    String result = CoreAPI.sendPost("Mute.php", json);

                                    try {
                                        JSONObject jsonObject = new JSONObject(result);
                                        if (jsonObject.getBoolean("result")) {
                                            sender.sendMessage(CoreAPI.getSuccessPrefix() + op.getName() + "をミュートしました。\n" +
                                                    CoreAPI.getInfoPrefix() + "処罰理由: " + str.trim());
                                            return true;
                                        } else {
                                            sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "をミュートできませんでした。\n" +
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
                                sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "はすでにこのサーバーでミュートされています。");
                                return true;
                            }
                            sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "は1度もこのサーバーに参加したことがありません。");
                            return true;
                        }
                    } else {
                        String str = "Muted by operator.";
                        if (CoreAPI.isOnlinePlayer(args[0])) {
                            Player p = Bukkit.getPlayerExact(args[0]);
                            if (CoreAPI.isPlayed(p)) {
                                if (!CoreAPI.isPunished(p, CoreAPI.PunishType.MUTE)) {
                                    String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"name\": \"" + p.getName() + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"reason\": \"" + str.trim() + "\", \"byName\": \"" + sender.getName() + "\", \"byUUID\": \"" + ((Player) sender).getUniqueId().toString() + "\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
                                    String result = CoreAPI.sendPost("Mute.php", json);

                                    try {
                                        JSONObject jsonObject = new JSONObject(result);
                                        if (jsonObject.getBoolean("result")) {
                                            p.sendMessage(CoreAPI.getErrorPrefix() + "あなたはミュートされました。\n" +
                                                    CoreAPI.getInfoPrefix() + "処罰理由: " + str.trim() + "\n" +
                                                    CoreAPI.getInfoPrefix() + "処罰者: " + sender.getName());
                                            sender.sendMessage(CoreAPI.getSuccessPrefix() + p.getName() + "をミュートしました。\n" +
                                                    CoreAPI.getInfoPrefix() + "処罰理由: " + str.trim());
                                            return true;
                                        } else {
                                            sender.sendMessage(CoreAPI.getErrorPrefix() + p.getName() + "をミュートできませんでした。\n" +
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
                                sender.sendMessage(CoreAPI.getErrorPrefix() + p.getName() + "はすでにこのサーバーでミュートされています。");
                                return true;
                            }
                            sender.sendMessage(CoreAPI.getErrorPrefix() + p.getName() + "は1度もこのサーバーに参加したことがありません。");
                            return true;
                        } else {
                            OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                            if (CoreAPI.isPlayed(op)) {
                                if (!CoreAPI.isPunished(op, CoreAPI.PunishType.MUTE)) {
                                    String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"name\": \"" + op.getName() + "\", \"uuid\": \"" + op.getUniqueId().toString() + "\", \"reason\": \"" + str.trim() + "\", \"byName\": \"" + sender.getName() + "\", \"byUUID\": \"" + ((Player) sender).getUniqueId().toString() + "\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
                                    String result = CoreAPI.sendPost("Mute.php", json);

                                    try {
                                        JSONObject jsonObject = new JSONObject(result);
                                        if (jsonObject.getBoolean("result")) {
                                            sender.sendMessage(CoreAPI.getSuccessPrefix() + op.getName() + "をミュートしました。\n" +
                                                    CoreAPI.getInfoPrefix() + "処罰理由: " + str.trim());
                                            return true;
                                        } else {
                                            sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "をミュートできませんでした。\n" +
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
                                sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "はすでにこのサーバーでミュートされています。");
                                return true;
                            }
                            sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "は1度もこのサーバーに参加したことがありません。");
                            return true;
                        }
                    }
                }
                sender.sendMessage(CoreAPI.getErrorPrefix() + "引数が不正です。\n" +
                        CoreAPI.getErrorPrefix() + "コマンドの使い方: /" + label + " <プレイヤー名> [処罰理由]");
                return true;
            }
            sender.sendMessage(CoreAPI.getErrorPrefix() + "権限がありません。");
            return true;
        } else {
            if (args.length != 0) {
                if (args.length != 1) {
                    String str = CoreAPI.toString(args, 1);
                    if (CoreAPI.isOnlinePlayer(args[0])) {
                        Player p = Bukkit.getPlayerExact(args[0]);
                        if (CoreAPI.isPlayed(p)) {
                            if (!CoreAPI.isPunished(p, CoreAPI.PunishType.MUTE)) {
                                String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"name\": \"" + p.getName() + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"reason\": \"" + str.trim() + "\", \"byName\": \"" + sender.getName() + "\", \"byUUID\": \"f78a4d8d-d51b-4b39-98a3-230f2de0c670\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
                                String result = CoreAPI.sendPost("Mute.php", json);

                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    if (jsonObject.getBoolean("result")) {
                                        p.sendMessage(CoreAPI.getErrorPrefix() + "あなたはミュートされました。\n" +
                                                CoreAPI.getInfoPrefix() + "処罰理由: " + str.trim() + "\n" +
                                                CoreAPI.getInfoPrefix() + "処罰者: " + sender.getName());
                                        sender.sendMessage(CoreAPI.getSuccessPrefix() + p.getName() + "をミュートしました。\n" +
                                                CoreAPI.getInfoPrefix() + "処罰理由: " + str.trim());
                                        return true;
                                    } else {
                                        sender.sendMessage(CoreAPI.getErrorPrefix() + p.getName() + "をミュートできませんでした。\n" +
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
                            sender.sendMessage(CoreAPI.getErrorPrefix() + p.getName() + "はすでにこのサーバーでミュートされています。");
                            return true;
                        }
                        sender.sendMessage(CoreAPI.getErrorPrefix() + p.getName() + "は1度もこのサーバーに参加したことがありません。");
                        return true;
                    } else {
                        OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                        if (CoreAPI.isPlayed(op)) {
                            if (!CoreAPI.isPunished(op, CoreAPI.PunishType.MUTE)) {
                                String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"name\": \"" + op.getName() + "\", \"uuid\": \"" + op.getUniqueId().toString() + "\", \"reason\": \"" + str.trim() + "\", \"byName\": \"" + sender.getName() + "\", \"byUUID\": \"f78a4d8d-d51b-4b39-98a3-230f2de0c670\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
                                String result = CoreAPI.sendPost("Mute.php", json);

                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    if (jsonObject.getBoolean("result")) {
                                        sender.sendMessage(CoreAPI.getSuccessPrefix() + op.getName() + "をミュートしました。\n" +
                                                CoreAPI.getInfoPrefix() + "処罰理由: " + str.trim());
                                        return true;
                                    } else {
                                        sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "をミュートできませんでした。\n" +
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
                            sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "はすでにこのサーバーでミュートされています。");
                            return true;
                        }
                        sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "は1度もこのサーバーに参加したことがありません。");
                        return true;
                    }
                } else {
                    String str = "Muted by operator.";
                    if (CoreAPI.isOnlinePlayer(args[0])) {
                        Player p = Bukkit.getPlayerExact(args[0]);
                        if (CoreAPI.isPlayed(p)) {
                            if (!CoreAPI.isPunished(p, CoreAPI.PunishType.MUTE)) {
                                String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"name\": \"" + p.getName() + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"reason\": \"" + str.trim() + "\", \"byName\": \"" + sender.getName() + "\", \"byUUID\": \"f78a4d8d-d51b-4b39-98a3-230f2de0c670\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
                                String result = CoreAPI.sendPost("Mute.php", json);

                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    if (jsonObject.getBoolean("result")) {
                                        p.sendMessage(CoreAPI.getErrorPrefix() + "あなたはミュートされました。\n" +
                                                CoreAPI.getInfoPrefix() + "処罰理由: " + str.trim() + "\n" +
                                                CoreAPI.getInfoPrefix() + "処罰者: " + sender.getName());
                                        sender.sendMessage(CoreAPI.getSuccessPrefix() + p.getName() + "をミュートしました。\n" +
                                                CoreAPI.getInfoPrefix() + "処罰理由: " + str.trim());
                                        return true;
                                    } else {
                                        sender.sendMessage(CoreAPI.getErrorPrefix() + p.getName() + "をミュートできませんでした。\n" +
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
                            sender.sendMessage(CoreAPI.getErrorPrefix() + p.getName() + "はすでにこのサーバーでミュートされています。");
                            return true;
                        }
                        sender.sendMessage(CoreAPI.getErrorPrefix() + p.getName() + "は1度もこのサーバーに参加したことがありません。");
                        return true;
                    } else {
                        OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                        if (CoreAPI.isPlayed(op)) {
                            if (!CoreAPI.isPunished(op, CoreAPI.PunishType.MUTE)) {
                                String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"name\": \"" + op.getName() + "\", \"uuid\": \"" + op.getUniqueId().toString() + "\", \"reason\": \"" + str.trim() + "\", \"byName\": \"" + sender.getName() + "\", \"byUUID\": \"f78a4d8d-d51b-4b39-98a3-230f2de0c670\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
                                String result = CoreAPI.sendPost("Mute.php", json);

                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    if (jsonObject.getBoolean("result")) {
                                        sender.sendMessage(CoreAPI.getSuccessPrefix() + op.getName() + "をミュートしました。\n" +
                                                CoreAPI.getInfoPrefix() + "処罰理由: " + str.trim());
                                        return true;
                                    } else {
                                        sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "をミュートできませんでした。\n" +
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
                            sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "はすでにこのサーバーでミュートされています。");
                            return true;
                        }
                        sender.sendMessage(CoreAPI.getErrorPrefix() + op.getName() + "は1度もこのサーバーに参加したことがありません。");
                        return true;
                    }
                }
            }
            sender.sendMessage(CoreAPI.getErrorPrefix() + "引数が不正です。\n" +
                    CoreAPI.getErrorPrefix() + "コマンドの使い方: /" + label + " <プレイヤー名> [処罰理由]");
            return true;
        }
    }
}
