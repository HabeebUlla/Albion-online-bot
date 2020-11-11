package com.github.ElgorTheWanderer;

import com.github.ElgorTheWanderer.AlbionClient.AlbionClient;
import com.github.ElgorTheWanderer.AlbionClient.AlbionClientSyncImpl;
import com.github.ElgorTheWanderer.AlbionDataClient.AlbionDataClient;
import com.github.ElgorTheWanderer.AlbionDataClient.AlbionDataClientImpl;
import com.github.ElgorTheWanderer.BusinessLogic.DiscordManagerObserverImpl;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManager;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManagerImpl;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManagerObserver;

import java.util.Map;


public class Main {

    private static final AlbionClient albionClient = AlbionClientSyncImpl.getInstance();
    private static final AlbionDataClient albionDataClient = AlbionDataClientImpl.getInstance();


    public static void main(String[] args) {
        Map<String, String> env = System.getenv();
        String discordToken = env.get("DISCORD_API_KEY");
        assert discordToken != null : "Discord API Key is missing";
        DiscordManager discordManager = new DiscordManagerImpl();
        DiscordManagerObserver observer = new DiscordManagerObserverImpl(albionClient, albionDataClient, discordManager);
        discordManager.setObserver(observer);
        discordManager.run(discordToken);

    }
}



