package com.github.ElgorTheWanderer.AlbionKillboardClient.EventTracker;

import com.github.ElgorTheWanderer.AlbionKillboardClient.GuildIdRepository;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManager;

import static com.github.ElgorTheWanderer.AlbionKillboardClient.AlbionKillboardClientImpl.guildIdRepository;

public class KillboardScheduler {
    DiscordManager discordManager;

    KillboardScheduler(DiscordManager discordManager) {
        this.discordManager = discordManager;
    }

    public void run() {
        for (GuildIdRepository.GuildInfo guildInfo : guildIdRepository.guildList) {
            EventParser.parseLastEvents(guildInfo);
            for (EventStructure event : guildInfo.listOfLastEvents) {
                discordManager.sendMessage(KillboardEventFormatter.messageContent(event), guildInfo.channelId);
            }
            guildInfo.listOfLastEvents.clear();
        }
    }
}
