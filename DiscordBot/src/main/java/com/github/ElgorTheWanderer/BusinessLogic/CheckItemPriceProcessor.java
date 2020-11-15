package com.github.ElgorTheWanderer.BusinessLogic;

import com.github.ElgorTheWanderer.AlbionClient.AlbionClient;
import com.github.ElgorTheWanderer.AlbionDataClient.AlbionDataClient;
import com.github.ElgorTheWanderer.AlbionDataClient.ItemPriceTable;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManager;
import discord4j.core.object.entity.Message;

public class CheckItemPriceProcessor implements CommandProcessor {

    static final String COMMAND_NAME = "!price";
    private final AlbionDataClient albionDataClient;
    private final DiscordManager discordManager;

    public CheckItemPriceProcessor(AlbionDataClient albionDataClient, DiscordManager discordManager) {
        this.albionDataClient = albionDataClient;
        this.discordManager = discordManager;
    }

    private void replyToPriceEvent(Message message) {

        try {
            String itemName = getCommandFromMessage(message.getContent());
            ItemPriceTable result = albionDataClient.findItemPrice(itemName);
            System.out.println(result);
//            message.getChannel().subscribe(channel -> discordManager.sendMessage("Price command is not implemented yet.\nWork in progress.\n" + result, channel));
        } catch (Exception e) {
            e.printStackTrace();
            String s = "Error: " + e.getMessage();
            message.getChannel().subscribe(channel -> discordManager.sendMessage(s, channel));
        }
    }


    private String getCommandFromMessage(String messageContent) {
        return messageContent.substring(COMMAND_NAME.length()).trim();
    }
    @Override
    public void processCommand(Message message){
        replyToPriceEvent(message);
    }
}
