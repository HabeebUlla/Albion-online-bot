package com.github.ElgorTheWanderer.AlbionKillboardClient;

import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.entity.channel.MessageChannel;
import org.json.JSONArray;
import org.json.JSONObject;
import reactor.netty.http.client.HttpClient;

public class AlbionKillboardClientImpl implements AlbionKillboardClient {

    //TODO Start here.
    // - Save timestamp to GuildIdRepository, when new guild tracking is added.
    // - For first ever request save LastEvent Id for further validation.
    // - KillboardScheduler calls endpoint for each GuildIdRepository guild once in 5 minutes to get 10 recent events.
    // - Search for GuildIdRepository.LastEvent Id and timestamp.
    // - If LastEvent timestamp is older than recent events, post these events to channel.
    // - If LastEvent Id is not found, request next 10 events with offset.
    // - Post events sorted from oldest to newest.



    GuildIdRepository guildIdRepository = new GuildIdRepository();

    private void killboardMethod(String guildName, MessageChannel channelId) {


    String targetUrl = buildUrl(guildName);
    String jsonReplyString = HttpClient.create().get().uri(targetUrl).responseContent().aggregate().asString().block();

    assert jsonReplyString != null;
    JSONArray arr = new JSONObject(jsonReplyString).getJSONArray("guilds");


    for (int i = 0; i < arr.length(); i++) {
        if (arr.getJSONObject(i).getString("Name").equals(guildName)) {
            JSONObject jo = arr.getJSONObject(i);
            GuildIdRepository.GuildInfo guildInfo = new GuildIdRepository.GuildInfo();
            guildInfo.guildId = jo.getString("Id");
            guildInfo.channelId = channelId.toString();
            guildInfo.guildName = guildName;
            guildIdRepository.guildList.add(guildInfo);
            System.out.println(guildName);
            System.out.println(guildInfo.guildId);
            String s = channelId.getId().toString();
            System.out.println(s);

            break;
        }
    }
}



    private String buildUrl(String guildName) {
        String url = ("https://gameinfo.albiononline.com/api/gameinfo/search?q=" + guildName);
        System.out.println(url);
        return url;
    }


    @Override
    public void addGuildTracking(String guildName, MessageChannel channelId) {
killboardMethod(guildName, channelId);
    }
}

