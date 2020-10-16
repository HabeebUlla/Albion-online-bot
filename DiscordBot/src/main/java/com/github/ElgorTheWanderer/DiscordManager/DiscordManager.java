package com.github.ElgorTheWanderer.DiscordManager;

import discord4j.core.object.entity.channel.MessageChannel;

public interface DiscordManager {
    public void run(String discordToken);
    public void sendMessage(String message, MessageChannel channel);
    public void setObserver(DiscordManagerObserver observer);
}
