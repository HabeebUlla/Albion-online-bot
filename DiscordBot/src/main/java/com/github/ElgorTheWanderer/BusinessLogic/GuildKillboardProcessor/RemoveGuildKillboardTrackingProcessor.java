package com.github.ElgorTheWanderer.BusinessLogic.GuildKillboardProcessor;

import com.github.ElgorTheWanderer.AlbionClient.AlbionClient;
import com.github.ElgorTheWanderer.AlbionKillboardClient.AlbionKillboardClient;
import com.github.ElgorTheWanderer.AlbionKillboardClient.GuildIdRepository;
import com.github.ElgorTheWanderer.BusinessLogic.CommandProcessor;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManager;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

public class RemoveGuildKillboardTrackingProcessor implements CommandProcessor {

    public static final String COMMAND_NAME = "!remove";
    private final AlbionKillboardClient albionKillboardClient;
    private final DiscordManager discordManager;

    public RemoveGuildKillboardTrackingProcessor(AlbionKillboardClient albionKillboardClient, DiscordManager discordManager) {
        this.albionKillboardClient = albionKillboardClient;
        this.discordManager = discordManager;
    }

    private void replyToRemoveEvent(Message message) {

        try {
            String guildName = getCommandFromMessage(message.getContent());
            MessageChannel channelId = message.getChannel().block();
            String reply = albionKillboardClient.removeGuildTracking(guildName, channelId);
            message.getChannel().subscribe(channel -> discordManager.sendMessage(reply, channel));
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
        replyToRemoveEvent(message);
    }
}
