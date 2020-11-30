package com.github.ElgorTheWanderer;

import com.github.ElgorTheWanderer.AlbionClient.AlbionClient;
import com.github.ElgorTheWanderer.AlbionClient.AlbionClientSyncImpl;
import com.github.ElgorTheWanderer.AlbionDataClient.AlbionDataClient;
import com.github.ElgorTheWanderer.AlbionDataClient.AlbionDataClientImpl;
import com.github.ElgorTheWanderer.AlbionDataClient.ItemInfoRepositoryImpl;
import com.github.ElgorTheWanderer.BusinessLogic.DiscordManagerObserverImpl;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManager;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManagerImpl;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManagerObserver;

import java.util.Map;


public class Main {

    private static final AlbionClient albionClient = AlbionClientSyncImpl.getInstance();
    static ItemInfoRepositoryImpl itemInfoRepository = new ItemInfoRepositoryImpl();
    static AlbionDataClient albionDataClient = new AlbionDataClientImpl(itemInfoRepository);

    public static void main(String[] args) {
        Map<String, String> env = System.getenv();
        String discordToken = env.get("DISCORD_API_KEY");
        assert discordToken != null : "Discord API Key is missing";
        String databasePath = env.get("DATABASE_PATH");
        assert databasePath != null : "Database file is missing";
        itemInfoRepository.initializeDatabase(databasePath);
        DiscordManager discordManager = new DiscordManagerImpl();
        DiscordManagerObserver observer = new DiscordManagerObserverImpl(albionClient, discordManager, albionDataClient);
        discordManager.setObserver(observer);
        discordManager.run(discordToken);
    }
}



