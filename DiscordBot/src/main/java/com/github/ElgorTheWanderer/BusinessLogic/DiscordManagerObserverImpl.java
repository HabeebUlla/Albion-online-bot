package com.github.ElgorTheWanderer.BusinessLogic;

import com.github.ElgorTheWanderer.AlbionClient.AlbionClient;
import com.github.ElgorTheWanderer.AlbionDataClient.AlbionDataClient;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManager;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManagerObserver;
import discord4j.core.object.entity.Message;

import java.util.HashMap;
import java.util.Map;

public class DiscordManagerObserverImpl implements DiscordManagerObserver {

    private final Map<String, CommandProcessor> commandMap;

    public DiscordManagerObserverImpl(AlbionClient albionClient, AlbionDataClient albionDataClient, DiscordManager discordManager) {
        HashMap<String, CommandProcessor> commandMap = new HashMap<>();
        commandMap.put(FindUserByNameProcessor.COMMAND_NAME, new FindUserByNameProcessor(albionClient, discordManager));
        commandMap.put(AddGuildKillboardTrackingProcessor.COMMAND_NAME, new AddGuildKillboardTrackingProcessor(albionClient, discordManager));
        commandMap.put(RemoveGuildKillboardTrackingProcessor.COMMAND_NAME, new RemoveGuildKillboardTrackingProcessor(albionClient, discordManager));
        commandMap.put(CheckItemPriceProcessor.COMMAND_NAME, new CheckItemPriceProcessor(albionDataClient, discordManager));
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