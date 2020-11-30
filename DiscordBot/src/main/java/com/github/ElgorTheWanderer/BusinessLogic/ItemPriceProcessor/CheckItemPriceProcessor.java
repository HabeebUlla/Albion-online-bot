package com.github.ElgorTheWanderer.BusinessLogic.ItemPriceProcessor;

import com.github.ElgorTheWanderer.AlbionDataClient.AlbionDataClient;
import com.github.ElgorTheWanderer.BusinessLogic.CommandProcessor;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManager;
import discord4j.core.object.entity.Message;

public class CheckItemPriceProcessor implements CommandProcessor {

    public static final String COMMAND_NAME = "!price";
    private final DiscordManager discordManager;
    ItemPriceTableMessageFormatter itemPriceTableMessageFormatter = new ItemPriceTableMessageFormatterImpl();
    AlbionDataClient albionDataClient;

    public CheckItemPriceProcessor(DiscordManager discordManager, AlbionDataClient albionDataClient) {
        this.discordManager = discordManager;
        this.albionDataClient = albionDataClient;
    }

    private String getCommandFromMessage(String messageContent) {
        return messageContent.substring(COMMAND_NAME.length()).trim();
    }

    @Override
    public void processCommand(Message message) {
        try {
            String itemName = getCommandFromMessage(message.getContent());
            String responseMessage = itemPriceTableMessageFormatter
                    .formatItemPriceTable(albionDataClient.getItemPrice(itemName));
            message.getChannel().subscribe(channel -> discordManager.sendMessage(responseMessage, channel));
        } catch (Exception e) {
            e.printStackTrace();
            String s = "Error: " + e.getMessage();
            message.getChannel().subscribe(channel -> discordManager.sendMessage(s, channel));
        }
    }
}