package com.github.ElgorTheWanderer.DiscordManager;


import discord4j.core.object.entity.channel.MessageChannel;

public interface DiscordManager {
    void run(String discordToken);

    void sendMessage(String message, MessageChannel channel);

    void setObserver(DiscordManagerObserver observer);
}
