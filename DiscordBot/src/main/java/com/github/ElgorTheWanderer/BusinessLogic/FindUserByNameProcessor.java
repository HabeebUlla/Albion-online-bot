package com.github.ElgorTheWanderer.BusinessLogic;

import com.github.ElgorTheWanderer.AlbionClient.AlbionClient;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManager;
import com.github.ElgorTheWanderer.AlbionClient.PlayerStructure;
import discord4j.core.object.entity.Message;

public class FindUserByNameProcessor implements CommandProcessor {

    static final String COMMAND_NAME = "!user";
    private final AlbionClient albionClient;
    private final DiscordManager discordManager;

    public FindUserByNameProcessor(AlbionClient albionClient, DiscordManager discordManager) {
        this.albionClient = albionClient;
        this.discordManager = discordManager;
    }

    private void replyToUserEvent(Message message) {

        String accountName = null;

        try {
            accountName = getCommandFromMessage(message.getContent());
            PlayerStructure player = albionClient.findPlayerByName(accountName);
            String s1 = ("Player Id is - " + player.playerId + "\n");
            String s2 = ("Player Name is - " + player.playerName + "\n");
            int i1 = (player.playerKillFame);
            String s3 = ("Player KillFame is - " + i1);
            message.getChannel().subscribe(channel -> discordManager.sendMessage(s1 + s2 + s3, channel));
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
        replyToUserEvent(message);
    }
}