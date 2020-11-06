package com.github.ElgorTheWanderer.BusinessLogic;

import com.github.ElgorTheWanderer.AlbionClient.AlbionClient;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManager;
import com.github.ElgorTheWanderer.PlayerStructure;
import discord4j.core.object.entity.Message;

public class PriceProcessor {

    private final AlbionClient albionClient;
    private final DiscordManager discordManager;

    public PriceProcessor(AlbionClient albionClient, DiscordManager discordManager) {
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
            message.getChannel().flatMap(Channel -> Channel.createMessage(s)).subscribe();
        }
    }


    private String getCommandFromMessage(String messageContent) {
        if (messageContent.length() <= 5 || messageContent.toLowerCase().startsWith("!price") != true) {
            return null;
        }
        String temp = messageContent.substring(7);
        return temp;
    }
    public void processPriceCommand(Message message){
        replyToPriceEvent(message);
    }
}
