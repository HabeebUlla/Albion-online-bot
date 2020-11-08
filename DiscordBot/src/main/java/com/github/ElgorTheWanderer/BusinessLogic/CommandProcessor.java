package com.github.ElgorTheWanderer.BusinessLogic;

import discord4j.core.object.entity.Message;

public interface CommandProcessor {
    public void processCommand(Message message);
}
