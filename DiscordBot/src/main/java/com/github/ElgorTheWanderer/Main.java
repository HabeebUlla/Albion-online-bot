package com.github.ElgorTheWanderer;

import com.github.ElgorTheWanderer.AlbionClient.AlbionClient;
import com.github.ElgorTheWanderer.AlbionClient.AlbionClientSyncImpl;
import com.github.ElgorTheWanderer.AlbionDataClient.*;
import com.github.ElgorTheWanderer.BusinessLogic.DiscordManagerObserverImpl;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManager;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManagerImpl;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManagerObserver;
import java.util.Map;


public class Main {

    private static final AlbionClient albionClient = AlbionClientSyncImpl.getInstance();


    public static void main(String[] args) {
        Map<String, String> env = System.getenv();
        String discordToken = env.get("DISCORD_API_KEY");
        assert discordToken != null : "Discord API Key is missing";
        String databasePath = env.get("DATABASE_PATH");
        assert databasePath != null : "Database file is missing";
        ItemInfoRepositoryImpl itemInfoRepository = new ItemInfoRepositoryImpl();
        itemInfoRepository.initializeDatabase(databasePath);
        DiscordManager discordManager = new DiscordManagerImpl();
        DiscordManagerObserver observer = new DiscordManagerObserverImpl(albionClient, discordManager);
        discordManager.setObserver(observer);
        discordManager.run(discordToken);
    }
}



