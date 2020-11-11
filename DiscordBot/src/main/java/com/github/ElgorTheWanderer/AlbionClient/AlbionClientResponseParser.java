package com.github.ElgorTheWanderer.AlbionClient;

import org.json.JSONArray;
import org.json.JSONObject;

class AlbionClientResponseParser {

    public PlayerStructure getPlayerByNameFromResponse(String JSONString, String name) throws Exception {
        JSONArray arr = new JSONObject(JSONString).getJSONArray("players");

        PlayerStructure player = null;
        for (int i = 0; i < arr.length(); i++) {
            if (arr.getJSONObject(i).getString("Name").equals(name)) {
                JSONObject jo = arr.getJSONObject(i);
                player = playerFromJSONObject(jo);
                break;
            }
        }
        if (player == null) {
            throw new Exception("User not found");
        }

        return player;
    }

    private PlayerStructure playerFromJSONObject(JSONObject jsonObject){
        PlayerStructure player = new PlayerStructure();
        player.playerId = jsonObject.getString("Id");
        player.playerName = jsonObject.getString("Name");
        player.playerKillFame = jsonObject.getInt("KillFame");
        return player;
    }
}
