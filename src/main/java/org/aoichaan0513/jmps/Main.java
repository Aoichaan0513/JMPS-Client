package org.aoichaan0513.jmps;

import org.aoichaan0513.jmps.API.CoreAPI;
import org.aoichaan0513.jmps.Command.*;
import org.aoichaan0513.jmps.Listener.PlayerListener;
import org.aoichaan0513.jmps.Util.JSONPost;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Main extends JavaPlugin {

    private static JavaPlugin plugin;
    private static String channel = "Dev";

    private static String id = "";

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        Bukkit.getConsoleSender().sendMessage(CoreAPI.getInfoPrefix() + "プラグインを開始しています…");
        if (authentication()) {
            loadConfig();
            loadCommand();
            loadListener();
            Bukkit.getConsoleSender().sendMessage(CoreAPI.getSuccessPrefix() + "プラグインを開始しました。");
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(CoreAPI.getInfoPrefix() + "プラグインを停止しています…");
    }

    // インスタンス取得
    public static JavaPlugin getInstance() {
        return plugin;
    }

    // チャンネル取得
    private String getChannel() {
        return channel;
    }

    public static String getServerId() {
        return id;
    }

    private void loadCommand() {
        Bukkit.getConsoleSender().sendMessage(CoreAPI.getInfoPrefix() + "コマンドを読み込んでいます…");
        getCommand("warn").setExecutor(new Warn(getInstance()));
        getCommand("unwarn").setExecutor(new UnWarn(getInstance()));
        getCommand("mute").setExecutor(new Mute(getInstance()));
        getCommand("unmute").setExecutor(new UnMute(getInstance()));
        getCommand("kick").setExecutor(new Kick(getInstance()));
        getCommand("ban").setExecutor(new LBan(getInstance()));
        getCommand("unban").setExecutor(new UnLBan(getInstance()));
        getCommand("tban").setExecutor(new TBan(getInstance()));
        getCommand("untban").setExecutor(new UnTBan(getInstance()));
        getCommand("gban").setExecutor(new GBan(getInstance()));
        getCommand("ungban").setExecutor(new UnGBan(getInstance()));

        getCommand("admin").setExecutor(new Admin(getInstance()));
        getCommand("staff").setExecutor(new Staff(getInstance()));
        getCommand("whois").setExecutor(new Whois(getInstance()));
        getCommand("namelookup").setExecutor(new NameLookUp(getInstance()));
        Bukkit.getConsoleSender().sendMessage(CoreAPI.getSuccessPrefix() + "コマンドを15件読み込みました。");
        return;
    }

    private void loadListener() {
        Bukkit.getConsoleSender().sendMessage(CoreAPI.getInfoPrefix() + "リスナーを読み込んでいます…");
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(), getInstance());
        Bukkit.getConsoleSender().sendMessage(CoreAPI.getSuccessPrefix() + "リスナーを1件読み込みました。");
        return;
    }

    private void loadConfig() {
        loadFolder("updates");
        return;
    }

    private void loadFile(String folderName, String fileName) {
        File file = new File(getDataFolder() + FileSystems.getDefault().getSeparator() + folderName, fileName);
        if (!file.exists()) {
            Bukkit.getConsoleSender().sendMessage(CoreAPI.getInfoPrefix() + "ファイル\"" + fileName + "\"を作成します…");
            saveResource(folderName + FileSystems.getDefault().getSeparator() + fileName, false);
            Bukkit.getConsoleSender().sendMessage(CoreAPI.getSuccessPrefix() + "ファイル\"" + fileName + "\"を作成しました。");
        } else {
            Bukkit.getConsoleSender().sendMessage(CoreAPI.getInfoPrefix() + "ファイル\"" + fileName + "\"が見つかったためスルーしました。");
        }
    }

    private void loadFolder(String folderName) {
        File file = new File(getDataFolder() + FileSystems.getDefault().getSeparator() + folderName);
        if (file.mkdir())
            Bukkit.getConsoleSender().sendMessage(CoreAPI.getSuccessPrefix() + "フォルダ\"" + folderName + "\"を作成しました。");
        else
            Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "フォルダ\"" + folderName + "\"を作成できませんでした。");
    }

    private boolean authentication() {
        String result = CoreAPI.sendPost("Api.php", "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\"}");

        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getBoolean("result")) {
                id = jsonObject.getString("id");
                Bukkit.getConsoleSender().sendMessage(CoreAPI.getSuccessPrefix() + "認証に成功しました。\n" +
                        CoreAPI.getInfoPrefix() + "サーバー名: " + jsonObject.getString("name"));
                return true;
            } else {
                Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "認証に失敗しました。\n" +
                        CoreAPI.getErrorPrefix() + "プラグインを停止しています…\n" +
                        CoreAPI.getInfoPrefix() + "理由: " + jsonObject.getString("reason"));
                getPluginLoader().disablePlugin(getInstance());
                return false;
            }
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。\n" +
                    CoreAPI.getErrorPrefix() + "プラグインを停止しています…");
            e.printStackTrace();
            return false;
        }
    }

    private void download() {
        String urlStr = "https://incha.work/files/plugins/org/aoichaan0513/jmps/";
        String strPostUrl = urlStr + "api";

        Bukkit.getConsoleSender().sendMessage(CoreAPI.getInfoPrefix() + "更新を確認しています…");
        String JSON = "{\"channel\":\"" + getChannel() + "\", \"current\":\"" + getDescription().getVersion().split("-")[1] + "\"}";
        String result = new JSONPost(strPostUrl, JSON).getResult();
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getBoolean("result")) {
                Bukkit.getConsoleSender().sendMessage(CoreAPI.getWarningPrefix() + "更新が見つかりました。\n" +
                        CoreAPI.getWarningPrefix() + "プラグインチャンネル: " + getChannel() + "\n" +
                        CoreAPI.getWarningPrefix() + "現在のバージョン: " + getDescription().getVersion().split("-")[1] + "\n" +
                        CoreAPI.getWarningPrefix() + "最新のバージョン: " + jsonObject.getString("latest") + "\n" +
                        CoreAPI.getWarningPrefix() + "最新のバージョンをダウンロードしています…");
                try {
                    URL url = new URL(urlStr + jsonObject.getString("file"));

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setAllowUserInteraction(false);
                    conn.setInstanceFollowRedirects(true);
                    conn.setRequestMethod("GET");
                    conn.connect();

                    int httpStatusCode = conn.getResponseCode();

                    if (httpStatusCode != HttpURLConnection.HTTP_OK) {
                        throw new Exception();
                    }

                    // Input Stream
                    DataInputStream dataInStream = new DataInputStream(conn.getInputStream());

                    // Output Stream
                    DataOutputStream dataOutStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(getDataFolder() + FileSystems.getDefault().getSeparator() + "updates" + FileSystems.getDefault().getSeparator() + jsonObject.getString("file"))));

                    // Read Data
                    byte[] b = new byte[4096];
                    int readByte = 0;

                    while (-1 != (readByte = dataInStream.read(b))) {
                        dataOutStream.write(b, 0, readByte);
                    }

                    // Close Stream
                    dataInStream.close();
                    dataOutStream.close();
                    Bukkit.getConsoleSender().sendMessage(CoreAPI.getSuccessPrefix() + "最新のバージョンをダウンロードしました。\n" +
                            CoreAPI.getInfoPrefix() + "プラグインを最新のバージョンに置き換えています…");

                    try {
                        Path sourcePath = Paths.get(getDataFolder() + FileSystems.getDefault().getSeparator() + "updates" + FileSystems.getDefault().getSeparator() + jsonObject.getString("file"));
                        Path targetPath = Paths.get(getServer().getWorldContainer().getAbsolutePath() + FileSystems.getDefault().getSeparator() + "plugins" + FileSystems.getDefault().getSeparator() + jsonObject.getString("file"));
                        Files.move(sourcePath, targetPath);
                        Bukkit.getConsoleSender().sendMessage(CoreAPI.getSuccessPrefix() + "最新のバージョンを移動しました。");

                        try {
                            Path targetPath2 = Paths.get(getServer().getWorldContainer().getAbsolutePath() + FileSystems.getDefault().getSeparator() + "plugins" + FileSystems.getDefault().getSeparator() + "A_TosoGame_Live-" + getDescription().getVersion() + ".jar");
                            if (Files.deleteIfExists(targetPath2)) {
                                Bukkit.getConsoleSender().sendMessage(CoreAPI.getSuccessPrefix() + "現在のバージョンを削除しました。\n" +
                                        CoreAPI.getWarningPrefix() + "サーバーを再起動しています…");
                                getServer().reload();
                            } else {
                                Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "ファイルがありませんでした。");
                            }

                        } catch (IOException e) {
                            Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。");
                            e.printStackTrace();
                            return;
                        }
                    } catch (IOException e) {
                        Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。");
                        e.printStackTrace();
                        return;
                    }
                    return;
                } catch (FileNotFoundException e) {
                    Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。");
                    e.printStackTrace();
                    return;
                } catch (ProtocolException e) {
                    Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。");
                    e.printStackTrace();
                    return;
                } catch (MalformedURLException e) {
                    Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。");
                    e.printStackTrace();
                    return;
                } catch (IOException e) {
                    Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。");
                    e.printStackTrace();
                    return;
                } catch (Exception e) {
                    Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。");
                    e.printStackTrace();
                    return;
                }
            } else {
                Bukkit.getConsoleSender().sendMessage(CoreAPI.getSuccessPrefix() + "更新はありませんでした。");
                return;
            }
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(CoreAPI.getErrorPrefix() + "予期しないエラーが発生しました。");
            e.printStackTrace();
            return;
        }
    }
}
