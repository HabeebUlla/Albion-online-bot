package com.github.ElgorTheWanderer.AlbionKillboardClient;

import discord4j.core.object.entity.channel.MessageChannel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.github.ElgorTheWanderer.AlbionKillboardClient.AlbionKillboardClientImpl.guildIdRepository;


public class GuildInfoParser {

    public static String parseGuildInfo(String jsonReplyString, String guildName, MessageChannel channelId) {
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
                guildInfo.lastEventTimestamp = ZonedDateTime.now(ZoneId.of("UTC"));

                System.out.println(guildInfo.lastEventTimestamp);

                guildIdRepository.guildList.add(guildInfo);
                break;
            }
        }
        return guildInfo.guildId;
    }
}
