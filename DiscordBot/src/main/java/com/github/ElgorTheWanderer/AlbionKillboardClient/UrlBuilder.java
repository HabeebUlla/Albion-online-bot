package com.github.ElgorTheWanderer.AlbionKillboardClient;

import com.github.ElgorTheWanderer.AlbionKillboardClient.GuildIdRepository;

public class UrlBuilder {
    private static final String URL_HEAD = "https://gameinfo.albiononline.com/api/gameinfo/events?limit=";
    private static final String URL_MIDDLE = "&offset=";
    private static final String URL_TAIL = "&guildId=";

    public static String buildGuildInfoUrl(String guildName) {
        return ("https://gameinfo.albiononline.com/api/gameinfo/search?q=" + guildName);
    }

    public static String buildTrackerUrl(GuildIdRepository.GuildInfo guildInfo) {
        return URL_HEAD + URL_MIDDLE + guildInfo.eventsLimit + guildInfo.eventsOffset
                + URL_TAIL + guildInfo.guildId;
    }
}
