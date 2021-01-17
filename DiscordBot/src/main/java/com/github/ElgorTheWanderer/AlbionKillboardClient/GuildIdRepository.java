package com.github.ElgorTheWanderer.AlbionKillboardClient;

import com.github.ElgorTheWanderer.AlbionKillboardClient.EventTracker.EventStructure;
import discord4j.core.object.entity.channel.MessageChannel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GuildIdRepository {

    public List<GuildInfo> guildList = new ArrayList<>();

    public static class GuildInfo {
        public String guildName;
        public String guildId;
        public String lastEventId;
        public LocalDateTime lastEventTimestamp;
        public MessageChannel channelId;
        public String guildUrl;
        public int eventsLimit;
        public int eventsOffset;
        public List<EventStructure> listOfLastEvents;
    }
}
