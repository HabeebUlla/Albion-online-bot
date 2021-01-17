package com.github.ElgorTheWanderer.DiscordManager;

import discord4j.core.object.entity.Message;

public interface DiscordManagerObserver {
    void onMessageReceived(Message message);
}
