package com.github.ElgorTheWanderer;

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
            String targetUrl = buildUrl(accountName);
            System.out.println(targetUrl);

            //http запрос-ответ.
            String jsonReplyString = HttpClient.create().get().uri(targetUrl).responseContent().aggregate().asString().block();
            System.out.println(jsonReplyString);
            System.out.println();

            //Получить нужный объект JSON. Передать данные структуре.
            PlayerStructure player1 = parseResultToStructure(jsonReplyString, accountName);
            //Склеить и вернут сообщение в чат.
            String s1 = ("Player Id is - " + player1.playerId + "\n");
            String s2 = ("Player Name is - " + player1.playerName + "\n");
            int i1 = (player1.playerKillFame);
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

    //Склеить адрес для запроса.
    public static String buildUrl(String name) {
        String url = ("https://gameinfo.albiononline.com/api/gameinfo/search?q=" + name);
        return url;
    }

    //Получить объект JSON с нужным именем игрока. Передать данные структуре.
    public static PlayerStructure parseResultToStructure(String JSONString, String name) throws Exception {
        JSONArray arr = new JSONObject(JSONString).getJSONArray("players");

        PlayerStructure player = null;
        for (int i = 0; i < arr.length(); i++) {
            if (arr.getJSONObject(i).getString("Name").equals(name)) {
                JSONObject jo;
                jo = arr.getJSONObject(i);
                player = new PlayerStructure();
                player.playerId = jo.getString("Id");
                player.playerName = jo.getString("Name");
                player.playerKillFame = jo.getInt("KillFame");
                break;
            }
        }
        if (player == null){
         throw new Exception("User not found");
        }

        return player;
    }
}