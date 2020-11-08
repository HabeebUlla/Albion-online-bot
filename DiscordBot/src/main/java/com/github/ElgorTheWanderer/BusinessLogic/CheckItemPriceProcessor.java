package com.github.ElgorTheWanderer.BusinessLogic;

import com.github.ElgorTheWanderer.AlbionClient.AlbionClient;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManager;
import com.github.ElgorTheWanderer.PlayerStructure;
import discord4j.core.object.entity.Message;

public class CheckItemPriceProcessor implements CommandProcessor {

    static final String COMMAND_NAME = "!price";
    private final AlbionClient albionClient;
    private final DiscordManager discordManager;

    public CheckItemPriceProcessor(AlbionClient albionClient, DiscordManager discordManager) {
        this.albionClient = albionClient;
        this.discordManager = discordManager;
    }

    private void replyToPriceEvent(Message message) {

        String itemName = null;

        try {
            itemName = getCommandFromMessage(message.getContent());
            message.getChannel().subscribe(channel -> discordManager.sendMessage("Price command is not implemented yet.\nWork in progress.", channel));
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
