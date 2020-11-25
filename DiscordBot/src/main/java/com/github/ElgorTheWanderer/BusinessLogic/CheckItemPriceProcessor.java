package com.github.ElgorTheWanderer.BusinessLogic;

import com.github.ElgorTheWanderer.AlbionDataClient.AlbionDataClient;
import com.github.ElgorTheWanderer.AlbionDataClient.AlbionDataClientImpl;
import com.github.ElgorTheWanderer.AlbionDataClient.ItemInfoRepositoryStructure;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManager;
import discord4j.core.object.entity.Message;

public class CheckItemPriceProcessor implements CommandProcessor {

    static final String COMMAND_NAME = "!price";
    private final DiscordManager discordManager;
    private final ItemInfoRepositoryStructure database;


    public CheckItemPriceProcessor(DiscordManager discordManager, ItemInfoRepositoryStructure database) {
        this.discordManager = discordManager;
        this.database = database;
    }

    private String getCommandFromMessage(String messageContent) {
        return messageContent.substring(COMMAND_NAME.length()).trim();
    }

    @Override
    public void processCommand(Message message) {
        try {
            AlbionDataClient albionDataClient = new AlbionDataClientImpl(database);
            String itemName = getCommandFromMessage(message.getContent());
            String responseMessage = albionDataClient.getItemPrice(itemName);
            message.getChannel().subscribe(channel -> discordManager.sendMessage(responseMessage, channel));
        } catch (Exception e) {
            e.printStackTrace();
            String s = "Error: " + e.getMessage();
            message.getChannel().subscribe(channel -> discordManager.sendMessage(s, channel));
        }
    }
}