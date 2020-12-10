package com.github.ElgorTheWanderer.AlbionKillboardClient;

import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.entity.channel.MessageChannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuildIdRepository {
    //Store here list of structures with:
    //Guild info, last event info, last timestamp, channel ID.

    public List<GuildInfo> guildList = new ArrayList<>();

    static class GuildInfo{
        public String guildName;
        public String guildId;
        public String lastEventId;
        public String lastEventTimestamp;
        public String channelId;
        public Map<String, EventStructure> listOfLastEvents= new HashMap<>();
    }
}
