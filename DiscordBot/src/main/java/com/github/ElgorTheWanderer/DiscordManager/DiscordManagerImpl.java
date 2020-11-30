package com.github.ElgorTheWanderer.DiscordManager;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;

public class DiscordManagerImpl implements DiscordManager {

    public DiscordManagerObserver observer;

    @Override
    public void run(String discordToken) {
        GatewayDiscordClient client = DiscordClientBuilder.create(discordToken).build().login().block();
        client.getEventDispatcher().on(ReadyEvent.class)
                .subscribe(event -> {
                    User self = event.getSelf();
                    System.out.println(String.format("Logged in as %s#%s", self.getUsername(), self.getDiscriminator()));
                });
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .subscribe(message -> this.observer.onMessageReceived(message));
        client.onDisconnect().block();
    }

    @Override
    public void sendMessage(String message, MessageChannel channel) {
        channel.createMessage(message).subscribe();
    }

    @Override
    public void setObserver(DiscordManagerObserver observer) {
        this.observer = observer;
    }
}
