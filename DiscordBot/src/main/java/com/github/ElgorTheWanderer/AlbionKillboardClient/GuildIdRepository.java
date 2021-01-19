package com.github.ElgorTheWanderer.AlbionKillboardClient;

import com.github.ElgorTheWanderer.AlbionKillboardClient.EventTracker.EventStructure;
import discord4j.core.object.entity.channel.MessageChannel;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class GuildIdRepository {

    public static List<GuildInfo> guildList = new ArrayList<>();

    public static class GuildInfo {
        public String guildName;
        public String guildId;
        public int lastEventId;
        public ZonedDateTime lastEventTimestamp;
        public MessageChannel channelId;
        public String guildUrl;
        public int eventsLimit;
        public int eventsOffset;
        public List<EventStructure> listOfLastEvents = new ArrayList<>();
    }
}
