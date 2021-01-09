package com.github.ElgorTheWanderer.AlbionKillboardClient;

import discord4j.core.object.entity.channel.MessageChannel;
import reactor.netty.http.client.HttpClient;

public class AlbionKillboardClientImpl implements AlbionKillboardClient {

    public static final GuildIdRepository guildIdRepository = new GuildIdRepository();

    private String killboardMethod(String guildName, MessageChannel channelId) {
        return GuildInfoParser.parseGuildInfo(HttpClient.create().get().uri(UrlBuilder.buildGuildInfoUrl(guildName))
                .responseContent().aggregate().asString().block(), guildName, channelId);
    }

    @Override
    public String addGuildTracking(String guildName, MessageChannel channelId) {
        return killboardMethod(guildName, channelId);
    }
}