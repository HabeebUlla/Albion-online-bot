package com.github.ElgorTheWanderer.BusinessLogic;

import com.github.ElgorTheWanderer.AlbionClient.AlbionClient;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManager;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManagerObserver;
import com.github.ElgorTheWanderer.PlayerStructure;
import discord4j.core.object.entity.Message;

public class DiscordManagerObserverImpl implements DiscordManagerObserver {

    private final AlbionClient albionClient;
    private final DiscordManager discordManager;

    public DiscordManagerObserverImpl(AlbionClient albionClient, DiscordManager discordManager) {
        this.albionClient = albionClient;
        this.discordManager = discordManager;
    }


    private void processIncomingMessage(Message message) {
        if(canProcessMessage(message.getContent()) == false){
            return;
        }
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
            message.getChannel().flatMap(Channel -> Channel.createMessage(s)).subscribe();
        }
    }


    private String getCommandFromMessage(String messageContent) {
        if (messageContent.length() <= 5 || messageContent.startsWith("!user") != true) {
            return null;
        }
        String temp = messageContent.substring(6);
        return temp;
    }

    private boolean canProcessMessage(String messageContent){
        if(messageContent.startsWith("!user")) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void onMessageReceived(Message message) {
        processIncomingMessage(message);
    }
}
