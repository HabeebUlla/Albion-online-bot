package com.github.ElgorTheWanderer.AlbionKillboardClient.EventTracker;

import com.github.ElgorTheWanderer.AlbionKillboardClient.GuildIdRepository;
import reactor.netty.http.client.HttpClient;
import java.util.TimerTask;
import static com.github.ElgorTheWanderer.AlbionKillboardClient.AlbionKillboardClientImpl.guildIdRepository;

public class KillboardScheduler extends TimerTask {
    @Override
    public void run() {
        for (GuildIdRepository.GuildInfo guildInfo : guildIdRepository.guildList) {
            EventParser.parseLastEvents(guildInfo);
            }
        }
    }