package com.github.ElgorTheWanderer.BusinessLogic;

import com.github.ElgorTheWanderer.AlbionClient.AlbionClient;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManager;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManagerObserver;
import discord4j.core.object.entity.Message;

import java.util.HashMap;
import java.util.Map;

public class DiscordManagerObserverImpl implements DiscordManagerObserver {

    private final Map<String, CommandProcessor> commandMap;

    public DiscordManagerObserverImpl(AlbionClient albionClient, DiscordManager discordManager) {
        HashMap<String, CommandProcessor> commandMap = new HashMap<>();
        commandMap.put("!user", new FindUserByNameProcessor(albionClient, discordManager));
        commandMap.put("!add", new AddGuildKillboardTrackingProcessor(albionClient, discordManager));
        commandMap.put("!remove", new RemoveGuildKillboardTrackingProcessor(albionClient, discordManager));
        commandMap.put("!price", new CheckItemPriceProcessor(albionClient, discordManager));
        this.commandMap = commandMap;
    }

    private void processIncomingMessage(Message message) {
        String messageContent = message.getContent().toLowerCase();
        int commandDelimiterIndex = messageContent.indexOf(" ");
        if (commandDelimiterIndex != -1) {
            String commandName = messageContent.substring(0, commandDelimiterIndex);
            CommandProcessor processor = commandMap.get(commandName);
            if (processor != null) {
                processor.processCommand(message);
            }
        }
    }

    @Override
    public void onMessageReceived(Message message) {
        processIncomingMessage(message);
    }
}