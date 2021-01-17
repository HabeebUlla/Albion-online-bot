package com.github.ElgorTheWanderer.BusinessLogic;

import discord4j.core.object.entity.Message;

public interface CommandProcessor {
    void processCommand(Message message);
}
