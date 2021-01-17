package com.github.ElgorTheWanderer.AlbionKillboardClient.EventTracker;

import org.json.JSONArray;
import org.json.JSONObject;

public class PlayerParser {
    public static Player parsePlayer(JSONObject participant) {
        Player player = new Player();
        player.name = participant.getString("Name");
        player.Id = participant.getString("Id");
        player.averageItemPower = Math.round((participant.getFloat("AverageItemPower") * 100) / 100);
        player.guildName = participant.getString("GuildName");
        player.guildId = participant.getString("GuildId");
        player.killFame = participant.getInt("KillFame");
        player.deathFame = participant.getInt("DeathFame");
        JSONArray equipment = participant.getJSONArray("Equipment");

        for (int i = 0; i < equipment.length(); i++) {
            String bodyPart = equipment.getString(i);
            String itemId = equipment.getJSONObject(i).getString("Type");
            if (itemId != null) {
                player.equipment.put(bodyPart, itemId);
            }
        }
        return player;
    }
}
