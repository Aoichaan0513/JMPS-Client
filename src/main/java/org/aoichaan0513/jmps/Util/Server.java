package org.aoichaan0513.jmps.Util;

import org.aoichaan0513.jmps.API.CoreAPI;
import org.aoichaan0513.jmps.Main;
import org.json.JSONObject;

public class Server {

    private String id;
    private String name;
    private String address;
    private String owner;

    public Server() {
        String result = CoreAPI.sendPost("getServer.php", "{\"apiKey\": \"" + Main.getInstance().getConfig().getString("apiKey") + "\"}");

        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getBoolean("result")) {
                this.id = jsonObject.getString("id");
                this.name = jsonObject.getString("name");
                this.address = jsonObject.getString("address");
                this.owner = jsonObject.getString("owner");
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public Server(String id) {
        String result = CoreAPI.sendPost("getServer.php", "{\"id\": \"" + id + "\"}");

        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getBoolean("result")) {
                this.id = jsonObject.getString("id");
                this.name = jsonObject.getString("name");
                this.address = jsonObject.getString("address");
                this.owner = jsonObject.getString("owner");
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getOwner() {
        return owner;
    }
}
