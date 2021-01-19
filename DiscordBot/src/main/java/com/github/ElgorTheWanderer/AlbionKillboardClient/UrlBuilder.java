package com.github.ElgorTheWanderer.AlbionKillboardClient;

public class UrlBuilder {
    private static final String URL_HEAD = "https://gameinfo.albiononline.com/api/gameinfo/events?limit=";
    private static final String URL_MIDDLE = "&offset=";
    private static final String URL_TAIL = "&guildId=";

    public static String buildGuildInfoUrl(String guildName) {
        return ("https://gameinfo.albiononline.com/api/gameinfo/search?q=" + guildName.replace(" ", "%20"));
    }

    public static String buildTrackerUrl(GuildIdRepository.GuildInfo guildInfo) {
        return URL_HEAD + guildInfo.eventsLimit + URL_MIDDLE + guildInfo.eventsOffset
                + URL_TAIL + guildInfo.guildId;
    }
}
