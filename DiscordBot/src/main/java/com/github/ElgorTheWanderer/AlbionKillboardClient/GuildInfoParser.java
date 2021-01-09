package com.github.ElgorTheWanderer.AlbionKillboardClient;

import discord4j.core.object.entity.channel.Channel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;

import static com.github.ElgorTheWanderer.AlbionKillboardClient.AlbionKillboardClientImpl.guildIdRepository;


public class GuildInfoParser {

    public static String parseGuildInfo(String jsonReplyString, String guildName, Channel channelId) {
        JSONArray arr = new JSONObject(jsonReplyString).getJSONArray("guilds");
        GuildIdRepository.GuildInfo guildInfo = new GuildIdRepository.GuildInfo();
        for (int i = 0; i < arr.length(); i++) {
            if (arr.getJSONObject(i).getString("Name").equals(guildName)) {
                JSONObject jo = arr.getJSONObject(i);
                guildInfo.guildId = jo.getString("Id");
                guildInfo.eventsLimit = 10;
                guildInfo.eventsOffset = 0;
                guildInfo.channelId = channelId;
                guildInfo.guildName = guildName;
                guildInfo.lastEventTimestamp = LocalDateTime.now();
                guildIdRepository.guildList.add(guildInfo);
                break;
            }
        }
        return guildInfo.guildId;
    }
}
