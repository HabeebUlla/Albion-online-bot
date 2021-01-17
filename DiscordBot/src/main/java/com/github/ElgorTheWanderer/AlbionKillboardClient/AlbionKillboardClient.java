package com.github.ElgorTheWanderer.AlbionKillboardClient;

import discord4j.core.object.entity.channel.MessageChannel;

public interface AlbionKillboardClient {
    String addGuildTracking(String guildName, MessageChannel channelId);
}
