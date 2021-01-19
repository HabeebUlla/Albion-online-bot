package com.github.ElgorTheWanderer.AlbionKillboardClient.EventTracker;

import com.github.ElgorTheWanderer.AlbionKillboardClient.GuildIdRepository;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManager;

import java.util.Timer;
import java.util.TimerTask;

public class KillboardScheduler implements Runnable {

    DiscordManager discordManager;

    public KillboardScheduler(DiscordManager discordManager) {
        this.discordManager = discordManager;
    }

    @Override
    public void run() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                for (GuildIdRepository.GuildInfo guildInfo : GuildIdRepository.guildList) {

                    System.out.println("Guild events requested");

                    EventParser.parseLastEvents(guildInfo);
                    if(guildInfo.listOfLastEvents != null) {
                        for (EventStructure event : guildInfo.listOfLastEvents) {

                            System.out.println("Events parsed!");

                            discordManager.sendMessage(KillboardEventFormatter.messageContent(event), guildInfo.channelId);
                        }
                        guildInfo.listOfLastEvents.clear();
                    }
                }
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 1, 180000);
    }
}