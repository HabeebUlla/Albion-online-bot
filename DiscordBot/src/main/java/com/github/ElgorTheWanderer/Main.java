package com.github.ElgorTheWanderer;

import com.github.ElgorTheWanderer.AlbionClient.AlbionClient;
import com.github.ElgorTheWanderer.AlbionClient.AlbionClientSyncImpl;
import com.github.ElgorTheWanderer.AlbionDataClient.*;
import com.github.ElgorTheWanderer.BusinessLogic.DiscordManagerObserverImpl;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManager;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManagerImpl;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManagerObserver;

import java.io.IOException;
import java.util.Map;


public class Main {

    private static final AlbionClient albionClient = AlbionClientSyncImpl.getInstance();
    private static final AlbionDataClient albionDataClient = AlbionDataClientImpl.getInstance();


    public static void main(String[] args) throws IOException {
        Map<String, String> env = System.getenv();
        String discordToken = env.get("DISCORD_API_KEY");
        assert discordToken != null : "Discord API Key is missing";
        ItemInfoRepositoryStructure database;
        ItemInfoRepository iterator = new ItemInfoRepositoryImpl();
        database = iterator.iterateDatabase();
        DiscordManager discordManager = new DiscordManagerImpl();
        DiscordManagerObserver observer = new DiscordManagerObserverImpl(albionClient, albionDataClient, discordManager, database);
        discordManager.setObserver(observer);
        discordManager.run(discordToken);

    }
}



