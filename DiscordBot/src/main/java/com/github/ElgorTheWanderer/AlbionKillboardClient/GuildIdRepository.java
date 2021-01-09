package com.github.ElgorTheWanderer.AlbionKillboardClient;

import com.github.ElgorTheWanderer.AlbionKillboardClient.EventTracker.EventStructure;
import discord4j.core.object.entity.channel.Channel;

import java.time.LocalDateTime;
import java.util.*;

public class GuildIdRepository {

    public List<GuildInfo> guildList = new ArrayList<>();

    public static class GuildInfo{
        public String guildName;
        public String guildId;
        public String lastEventId;
        public LocalDateTime lastEventTimestamp;
        public Channel channelId;
        public String guildUrl;
        public int eventsLimit;
        public int eventsOffset;
        public Map<String, EventStructure> listOfLastEvents= new HashMap<>();
    }
}
