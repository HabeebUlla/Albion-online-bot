package com.github.ElgorTheWanderer;

import com.github.ElgorTheWanderer.AlbionClient.AlbionClient;
import com.github.ElgorTheWanderer.AlbionClient.AlbionClientSyncImpl;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.rest.http.client.ClientException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;


public class Bot {

    private static final AlbionClient albionClient = AlbionClientSyncImpl.getInstance();


    public static void main(String[] args) {
        Map<String, String> env = System.getenv();
        String discordToken = env.get("DISCORD_API_KEY");
        assert discordToken != null : "Discord API Key is missing";

        GatewayDiscordClient client = DiscordClientBuilder.create(discordToken).build().login().block();

        client.getEventDispatcher().on(ReadyEvent.class)
                .subscribe(event -> {
                    User self = event.getSelf();
                    System.out.println(String.format("Logged in as %s#%s", self.getUsername(), self.getDiscriminator()));
                });
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().toLowerCase().startsWith("!user"))
                .subscribe(Bot::processIncomingMessage);
        client.onDisconnect().block();
    }

    //Основной метод.
    public static void processIncomingMessage(Message message) {

        String accountName = null;

        try {
            accountName = getCommandFromMessage(message.getContent());
            System.out.println(accountName);
            PlayerStructure player = albionClient.findPlayerByName(accountName);


            //Склеить и вернут сообщение в чат.
            String s1 = ("Player Id is - " + player.playerId + "\n");
            String s2 = ("Player Name is - " + player.playerName + "\n");
            int i1 = (player.playerKillFame);
            String s3 = ("Player KillFame is - " + i1);
            System.out.println(s1 + s2 + s3);
            message.getChannel().flatMap(Channel -> Channel.createMessage(s1 + s2 + s3)).subscribe();
        } catch (Exception e) {
            e.printStackTrace();
            String s = "Error: " + e.getMessage();
            message.getChannel().flatMap(Channel -> Channel.createMessage(s)).subscribe();
        }
    }

    //Отрезать команду от имени.
    public static String getCommandFromMessage(String messageContent) {
        if (messageContent.length() <= 5) {
            return null;
        }
        String temp = messageContent.substring(6);
        return temp;
    }
}