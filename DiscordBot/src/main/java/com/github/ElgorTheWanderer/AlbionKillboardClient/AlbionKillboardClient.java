package com.github.ElgorTheWanderer.AlbionKillboardClient;

import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.entity.channel.MessageChannel;

public interface AlbionKillboardClient {
    void addGuildTracking(String guildName, MessageChannel channelId);

}
