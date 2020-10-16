package com.github.ElgorTheWanderer.DiscordManager;

import discord4j.core.object.entity.Message;

public interface DiscordManagerObserver {
    public void onMessageReceived(Message message);
}
