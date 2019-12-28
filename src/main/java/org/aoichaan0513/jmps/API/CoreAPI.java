package org.aoichaan0513.jmps.API;

import org.aoichaan0513.jmps.Main;
import org.aoichaan0513.jmps.Util.JSONPost;
import org.aoichaan0513.jmps.Util.Server;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.URL;

public class CoreAPI {

    public static boolean hasPlayer(CommandSender sender) {
        if (sender instanceof Player)
            return true;
        else
            return false;
    }

    public static boolean hasPermission(OfflinePlayer op) {
        String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + op.getUniqueId().toString() + "\", \"server\": \"" + new Server().getId() + "\"}";
        String result = sendPost("getPermission.php", json);

        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getBoolean("result")) {
                int authority = jsonObject.getInt("authority");
                if (authority >= 1)
                    return true;
                else
                    return false;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean hasPermission(Player p) {
        String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"server\": \"" + new Server().getId() + "\"}";
        String result = sendPost("getPermission.php", json);

        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getBoolean("result")) {
                int authority = jsonObject.getInt("authority");
                if (authority >= 1)
                    return true;
                else
                    return false;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean hasPermission(Player p, PermissionType type) {
        if (p.isOp() && getPermission(p) == type)
            return true;
        else
            return false;
    }

    public static boolean hasPermission(Player p, int i) {
        String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"server\": \"" + Main.getServerId() + "\"}";
        String result = sendPost("getPermission.php", json);

        try {
            JSONObject jsonObject = new JSONObject(result);
            // p.sendMessage(json);
            // p.sendMessage(result);
            // p.sendMessage(String.valueOf(jsonObject.getBoolean("result")));
            if (jsonObject.getBoolean("result")) {
                int authority = jsonObject.getInt("authority");
                if (authority >= i)
                    return true;
                else
                    return false;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean hasPermission(OfflinePlayer p, int i) {
        String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"server\": \"" + Main.getServerId() + "\"}";
        String result = sendPost("getPermission.php", json);

        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getBoolean("result")) {
                int authority = jsonObject.getInt("authority");
                if (authority >= i)
                    return true;
                else
                    return false;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static PermissionType getPermission(Player p) {
        String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"server\": \"" + Main.getServerId() + "\"}";
        String result = sendPost("getPermission.php", json);

        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getBoolean("result")) {
                int authority = jsonObject.getInt("authority");

                for (PermissionType permissionType : PermissionType.values())
                    if (permissionType.getpInt() == authority)
                        return permissionType;
                return PermissionType.DEFAULT;
            }
            return PermissionType.DEFAULT;
        } catch (Exception e) {
            e.printStackTrace();
            return PermissionType.DEFAULT;
        }
    }

    public static PermissionType getPermission(OfflinePlayer p) {
        String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"server\": \"" + Main.getServerId() + "\"}";
        String result = sendPost("getPermission.php", json);

        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getBoolean("result")) {
                int authority = jsonObject.getInt("authority");

                for (PermissionType permissionType : PermissionType.values())
                    if (permissionType.getpInt() == authority)
                        return permissionType;
                return PermissionType.DEFAULT;
            }
            return PermissionType.DEFAULT;
        } catch (Exception e) {
            e.printStackTrace();
            return PermissionType.DEFAULT;
        }
    }

    public static String getPermissionName(PermissionType type) {
        for (PermissionType permissionType : PermissionType.values())
            if (permissionType == type)
                return permissionType.getName();
        return PermissionType.DEFAULT.getName();
    }

    public static int getPermissionInt(PermissionType type) {
        for (PermissionType permissionType : PermissionType.values())
            if (permissionType == type)
                return permissionType.getpInt();
        return PermissionType.DEFAULT.getpInt();
    }

    public enum PermissionType {
        MANAGER("JMPS Manager", 4),
        OWNER("サーバーオーナー", 3),
        ADMIN("サーバー運営", 2),
        STAFF("サーバースタッフ", 1),
        DEFAULT("なし", 0);

        private final String name;
        private final int pInt;

        private PermissionType(String name, int pInt) {
            this.name = name;
            this.pInt = pInt;
        }

        public String getName() {
            return name;
        }

        public int getpInt() {
            return pInt;
        }
    }

    public static boolean isPunished(Player p, PunishType type) {
        if (type == PunishType.MUTE) {
            String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
            String result = sendPost("getMute.php", json);

            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getBoolean("result")) {
                    return true;
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else if (type == PunishType.WARNING) {
            String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
            String result = sendPost("getWarn.php", json);

            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getBoolean("result")) {
                    return true;
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else if (type == PunishType.LOCAL) {
            String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
            String result = sendPost("getLBan.php", json);

            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getBoolean("result")) {
                    return true;
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else if (type == PunishType.TEMPORARY) {
            String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
            String result = sendPost("getTBan.php", json);

            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getBoolean("result")) {
                    return true;
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else if (type == PunishType.GLOBAL) {
            String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"active\": " + true + "}";
            String result = sendPost("getGBan.php", json);

            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getBoolean("result")) {
                    return true;
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static boolean isPunished(OfflinePlayer p, PunishType type) {
        if (type == PunishType.WARNING) {
            String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
            String result = sendPost("getWarn.php", json);

            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getBoolean("result")) {
                    return true;
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else if (type == PunishType.MUTE) {
            String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
            String result = sendPost("getMute.php", json);

            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getBoolean("result")) {
                    return true;
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else if (type == PunishType.LOCAL) {
            String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
            String result = sendPost("getLBan.php", json);

            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getBoolean("result")) {
                    return true;
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else if (type == PunishType.TEMPORARY) {
            String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"server\": \"" + new Server().getId() + "\", \"active\": " + true + "}";
            String result = sendPost("getTBan.php", json);

            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getBoolean("result")) {
                    return true;
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else if (type == PunishType.GLOBAL) {
            String json = "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\", \"uuid\": \"" + p.getUniqueId().toString() + "\", \"active\": " + true + "}";
            String result = sendPost("getGBan.php", json);

            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getBoolean("result")) {
                    return true;
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public enum PunishType {
        WARNING,
        MUTE,
        KICK,
        LOCAL,
        TEMPORARY,
        GLOBAL,
    }

    public static boolean isOnlinePlayer(String name) {
        Player target = Bukkit.getPlayerExact(name);
        if (target != null)
            return true;
        else
            return false;
    }

    public static boolean isPlayed(OfflinePlayer p) {
        return p.getPlayer() != null || p.hasPlayedBefore();
    }

    public static String getCountry(InetSocketAddress address) {
        try {
            URL url = new URL("http://ip-api.com/json/" + address.getHostName());
            BufferedReader stream = new BufferedReader(new InputStreamReader(
                    url.openStream()));
            StringBuilder entirePage = new StringBuilder();
            String inputLine;
            while ((inputLine = stream.readLine()) != null)
                entirePage.append(inputLine);
            stream.close();
            if (!(entirePage.toString().contains("\"country\":\"")))
                return "N/A";
            return entirePage.toString().split("\"country\":\"")[1].split("\",")[0];
        } catch (Exception e) {
            return "N/A";
        }
    }

    public static String getCountryCode(InetSocketAddress address) {
        try {
            URL url = new URL("http://ip-api.com/json/" + address.getHostName());
            BufferedReader stream = new BufferedReader(new InputStreamReader(
                    url.openStream()));
            StringBuilder entirePage = new StringBuilder();
            String inputLine;
            while ((inputLine = stream.readLine()) != null)
                entirePage.append(inputLine);
            stream.close();
            if (!(entirePage.toString().contains("\"countryCode\":\"")))
                return "N/A";
            return entirePage.toString().split("\"countryCode\":\"")[1].split("\",")[0];
        } catch (Exception e) {
            return "N/A";
        }
    }

    public static String sendPost(String path, String json) {
        String strPostUrl = "http://api.aoichaan0513.xyz/jmps/" + path;
        String result = new JSONPost(strPostUrl, json).getResult();
        return result;
    }

    public static String toString(String[] strings) {
        StringBuffer stringBuffer = new StringBuffer();

        for (String str : strings) {
            stringBuffer.append(str);
        }

        return stringBuffer.toString();
    }

    public static String toString(String[] strings, int v) {
        StringBuffer stringBuffer = new StringBuffer();

        for (int i = v; i < strings.length; i++) {
            stringBuffer.append(strings[i] + " ");
        }

        return stringBuffer.toString();
    }

    // プレフィックス取得
    public static String getSuccessPrefix() {
        return ChatColor.GREEN + "> ";
    }

    public static String getWarningPrefix() {
        return ChatColor.GOLD + "> ";
    }

    public static String getErrorPrefix() {
        return ChatColor.RED + "> ";
    }

    public static String getInfoPrefix() {
        return ChatColor.GRAY + "> ";
    }
}
