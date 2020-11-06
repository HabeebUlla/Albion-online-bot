package com.github.ElgorTheWanderer.BusinessLogic;

import com.github.ElgorTheWanderer.AlbionClient.AlbionClient;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManager;
import discord4j.core.object.entity.Message;

public class AddProcessor {

    private final AlbionClient albionClient;
    private final DiscordManager discordManager;

    public AddProcessor(AlbionClient albionClient, DiscordManager discordManager) {
        this.albionClient = albionClient;
        this.discordManager = discordManager;
    }

    private void replyToAddEvent(Message message) {

        String guildName = null;

        try {
            guildName = getCommandFromMessage(message.getContent());
            message.getChannel().subscribe(channel -> discordManager.sendMessage("Add command is not implemented yet.\nWork in progress.", channel));
        } catch (Exception e) {
            e.printStackTrace();
            String s = "Error: " + e.getMessage();
            message.getChannel().flatMap(Channel -> Channel.createMessage(s)).subscribe();
        }
    }

    private String getCommandFromMessage(String messageContent) {
        if (messageContent.length() <= 4 || messageContent.toLowerCase().startsWith("!add") != true) {
            return null;
        }
        String temp = messageContent.substring(5);
        return temp;
    }

    public void processAddCommand(Message message) {
        replyToAddEvent(message);
    }
}