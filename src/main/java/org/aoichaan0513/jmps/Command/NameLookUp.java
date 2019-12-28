package org.aoichaan0513.jmps.Command;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.aoichaan0513.jmps.API.CoreAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NameLookUp implements CommandExecutor {
    JavaPlugin plugin;

    public NameLookUp(JavaPlugin plugin) {
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
                        String uuid = op.getUniqueId().toString();
                        StringBuilder builder1 = new StringBuilder();
                        uuid = uuid.replace("-", "");
                        try {
                            URL url = new URL("https://api.mojang.com/user/profiles/" + uuid + "/names");
                            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                builder1.append(line);
                            }
                        } catch (IOException e) {
                            sender.sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。\n" +
                                    CoreAPI.getInfoPrefix() + "エラーの詳細はコンソールで確認できます。");
                            Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。");
                            e.printStackTrace();
                            return true;
                        }

                        StringBuilder builder2 = new StringBuilder();
                        builder2.append(CoreAPI.getSuccessPrefix() + op.getName() + " (" + op.getUniqueId() + ")の検索結果\n");
                        JsonParser parser = new JsonParser();
                        JsonArray ar = (JsonArray) parser.parse(builder1.toString());
                        if (!ar.isJsonNull()) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            for (int i = 0; i < ar.size(); i++) {
                                if (i > 0) {
                                    JsonObject job = (JsonObject) ar.get(i);
                                    Timestamp ts = new Timestamp(job.get("changedToAt").getAsLong());
                                    builder2.append(CoreAPI.getWarningPrefix() + "" + job.get("name").toString().substring(1, job.get("name").toString().length() - 1) + ChatColor.GRAY + ": " + ChatColor.YELLOW + sdf.format(new Date(ts.getTime())) + "\n");
                                } else {
                                    JsonObject job = (JsonObject) ar.get(i);
                                    builder2.append(CoreAPI.getWarningPrefix() + "" + job.get("name").toString().substring(1, job.get("name").toString().length() - 1) + ChatColor.GRAY + ": " + ChatColor.YELLOW + "オリジナル\n");
                                }
                            }
                        } else {
                            builder2.append(CoreAPI.getErrorPrefix() + "結果が見つかりませんでした。");
                        }
                        sender.sendMessage(builder2.toString());
                        return true;
                    } else {
                        OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                        String uuid = op.getUniqueId().toString();
                        StringBuilder builder1 = new StringBuilder();
                        uuid = uuid.replace("-", "");
                        try {
                            URL url = new URL("https://api.mojang.com/user/profiles/" + uuid + "/names");
                            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                builder1.append(line);
                            }
                        } catch (IOException e) {
                            sender.sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。\n" +
                                    CoreAPI.getInfoPrefix() + "エラーの詳細はコンソールで確認できます。");
                            Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。");
                            e.printStackTrace();
                            return true;
                        }

                        StringBuilder builder2 = new StringBuilder();
                        builder2.append(CoreAPI.getSuccessPrefix() + op.getName() + " (" + op.getUniqueId() + ")の検索結果\n");
                        JsonParser parser = new JsonParser();
                        JsonArray ar = (JsonArray) parser.parse(builder1.toString());
                        if (!ar.isJsonNull()) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            for (int i = 0; i < ar.size(); i++) {
                                if (i > 0) {
                                    JsonObject job = (JsonObject) ar.get(i);
                                    Timestamp ts = new Timestamp(job.get("changedToAt").getAsLong());
                                    builder2.append(CoreAPI.getWarningPrefix() + "" + job.get("name").toString().substring(1, job.get("name").toString().length() - 1) + ChatColor.GRAY + ": " + ChatColor.YELLOW + sdf.format(new Date(ts.getTime())) + "\n");
                                } else {
                                    JsonObject job = (JsonObject) ar.get(i);
                                    builder2.append(CoreAPI.getWarningPrefix() + "" + job.get("name").toString().substring(1, job.get("name").toString().length() - 1) + ChatColor.GRAY + ": " + ChatColor.YELLOW + "オリジナル\n");
                                }
                            }
                        } else {
                            builder2.append(CoreAPI.getErrorPrefix() + "結果が見つかりませんでした。");
                        }
                        sender.sendMessage(builder2.toString());
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
                    String uuid = op.getUniqueId().toString();
                    StringBuilder builder1 = new StringBuilder();
                    uuid = uuid.replace("-", "");
                    try {
                        URL url = new URL("https://api.mojang.com/user/profiles/" + uuid + "/names");
                        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder1.append(line);
                        }
                    } catch (IOException e) {
                        sender.sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。\n" +
                                CoreAPI.getInfoPrefix() + "エラーの詳細はコンソールで確認できます。");
                        Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。");
                        e.printStackTrace();
                        return true;
                    }

                    StringBuilder builder2 = new StringBuilder();
                    builder2.append(CoreAPI.getSuccessPrefix() + op.getName() + " (" + op.getUniqueId() + ")の検索結果\n");
                    JsonParser parser = new JsonParser();
                    JsonArray ar = (JsonArray) parser.parse(builder1.toString());
                    if (!ar.isJsonNull()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        for (int i = 0; i < ar.size(); i++) {
                            if (i > 0) {
                                JsonObject job = (JsonObject) ar.get(i);
                                Timestamp ts = new Timestamp(job.get("changedToAt").getAsLong());
                                builder2.append(CoreAPI.getWarningPrefix() + "" + job.get("name").toString().substring(1, job.get("name").toString().length() - 1) + ChatColor.GRAY + ": " + ChatColor.YELLOW + sdf.format(new Date(ts.getTime())) + "\n");
                            } else {
                                JsonObject job = (JsonObject) ar.get(i);
                                builder2.append(CoreAPI.getWarningPrefix() + "" + job.get("name").toString().substring(1, job.get("name").toString().length() - 1) + ChatColor.GRAY + ": " + ChatColor.YELLOW + "オリジナル\n");
                            }
                        }
                    } else {
                        builder2.append(CoreAPI.getErrorPrefix() + "結果が見つかりませんでした。");
                    }
                    sender.sendMessage(builder2.toString());
                    return true;
                } else {
                    OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                    String uuid = op.getUniqueId().toString();
                    StringBuilder builder1 = new StringBuilder();
                    uuid = uuid.replace("-", "");
                    try {
                        URL url = new URL("https://api.mojang.com/user/profiles/" + uuid + "/names");
                        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder1.append(line);
                        }
                    } catch (IOException e) {
                        sender.sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。\n" +
                                CoreAPI.getInfoPrefix() + "エラーの詳細はコンソールで確認できます。");
                        Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。");
                        e.printStackTrace();
                        return true;
                    }

                    StringBuilder builder2 = new StringBuilder();
                    builder2.append(CoreAPI.getSuccessPrefix() + op.getName() + " (" + op.getUniqueId() + ")の検索結果\n");
                    JsonParser parser = new JsonParser();
                    JsonArray ar = (JsonArray) parser.parse(builder1.toString());
                    if (!ar.isJsonNull()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        for (int i = 0; i < ar.size(); i++) {
                            if (i > 0) {
                                JsonObject job = (JsonObject) ar.get(i);
                                Timestamp ts = new Timestamp(job.get("changedToAt").getAsLong());
                                builder2.append(CoreAPI.getWarningPrefix() + "" + job.get("name").toString().substring(1, job.get("name").toString().length() - 1) + ChatColor.GRAY + ": " + ChatColor.YELLOW + sdf.format(new Date(ts.getTime())) + "\n");
                            } else {
                                JsonObject job = (JsonObject) ar.get(i);
                                builder2.append(CoreAPI.getWarningPrefix() + "" + job.get("name").toString().substring(1, job.get("name").toString().length() - 1) + ChatColor.GRAY + ": " + ChatColor.YELLOW + "オリジナル\n");
                            }
                        }
                    } else {
                        builder2.append(CoreAPI.getErrorPrefix() + "結果が見つかりませんでした。");
                    }
                    sender.sendMessage(builder2.toString());
                    return true;
                }
            }
            sender.sendMessage(CoreAPI.getErrorPrefix() + "引数が不正です。\n" +
                    CoreAPI.getErrorPrefix() + "コマンドの使い方: /" + label + " <プレイヤー名>");
            return true;
        }
    }
}
