package com.github.ElgorTheWanderer.BusinessLogic;

import com.github.ElgorTheWanderer.AlbionClient.AlbionClient;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManager;
import discord4j.core.object.entity.Message;

public class AddGuildKillboardTrackingProcessor implements CommandProcessor {

    static final String COMMAND_NAME = "!add";
    private final AlbionClient albionClient;
    private final DiscordManager discordManager;

    public AddGuildKillboardTrackingProcessor(AlbionClient albionClient, DiscordManager discordManager) {
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
            message.getChannel().subscribe(channel -> discordManager.sendMessage(s, channel));
        }
    }

    private String getCommandFromMessage(String messageContent) {
        return messageContent.substring(COMMAND_NAME.length()).trim();
    }
@Override
    public void processCommand(Message message) {
        replyToAddEvent(message);
    }
}