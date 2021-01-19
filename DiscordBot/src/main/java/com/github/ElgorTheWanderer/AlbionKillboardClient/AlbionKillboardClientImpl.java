package com.github.ElgorTheWanderer.AlbionKillboardClient;

import discord4j.core.object.entity.channel.MessageChannel;
import reactor.netty.http.client.HttpClient;

public class AlbionKillboardClientImpl implements AlbionKillboardClient {

    public static final GuildIdRepository guildIdRepository = new GuildIdRepository();

    private String killboardMethod(String guildName, MessageChannel channelId) {
        return GuildInfoParser.parseGuildInfo(HttpClient.create().get().uri(UrlBuilder.buildGuildInfoUrl(guildName))
                .responseContent().aggregate().asString().block(), guildName, channelId);
    }

    private String killboardRemoveGuild(String guildName) {
        GuildIdRepository.guildList.removeIf(guild -> guild.guildName.equals(guildName));
        return ("Guild " + guildName + " is no longer tracked.");
    }

    @Override
    public String addGuildTracking(String guildName, MessageChannel channelId) {
        return killboardMethod(guildName, channelId);
    }
    @Override
    public String removeGuildTracking(String guildName, MessageChannel channelId) {
        return killboardRemoveGuild(guildName);
    }
}