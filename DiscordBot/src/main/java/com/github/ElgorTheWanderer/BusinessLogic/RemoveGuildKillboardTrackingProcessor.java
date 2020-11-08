package com.github.ElgorTheWanderer.BusinessLogic;

import com.github.ElgorTheWanderer.AlbionClient.AlbionClient;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManager;
import discord4j.core.object.entity.Message;

public class RemoveGuildKillboardTrackingProcessor implements CommandProcessor {
    private final AlbionClient albionClient;
    private final DiscordManager discordManager;

    public RemoveGuildKillboardTrackingProcessor(AlbionClient albionClient, DiscordManager discordManager) {
        this.albionClient = albionClient;
        this.discordManager = discordManager;
    }

    private void replyToRemoveEvent(Message message) {

        String guildName = null;

        try {
            guildName = getCommandFromMessage(message.getContent());
            message.getChannel().subscribe(channel -> discordManager.sendMessage("Remove command is not implemented yet.\nWork in progress.", channel));
        } catch (Exception e) {
            e.printStackTrace();
            String s = "Error: " + e.getMessage();
            message.getChannel().flatMap(Channel -> Channel.createMessage(s)).subscribe();
        }
    }

    private String getCommandFromMessage(String messageContent) {
        if (messageContent.length() <= 7 || messageContent.toLowerCase().startsWith("!remove") != true) {
            return null;
        }
        String temp = messageContent.substring(8);
        return temp;
    }
@Override
    public void processCommand(Message message) {
        replyToRemoveEvent(message);
    }
}
