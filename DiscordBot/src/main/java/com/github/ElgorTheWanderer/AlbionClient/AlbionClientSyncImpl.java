package com.github.ElgorTheWanderer.AlbionClient;

import com.github.ElgorTheWanderer.PlayerStructure;
import org.json.JSONArray;
import org.json.JSONObject;
import reactor.netty.http.client.HttpClient;

public class AlbionClientSyncImpl implements AlbionClient{

    private static final AlbionClientSyncImpl instance = new AlbionClientSyncImpl();
    private AlbionClientSyncImpl(){};

    public static AlbionClientSyncImpl getInstance(){
        return instance;
    }

    AlbionClientResponseParser parser = new AlbionClientResponseParser();

    @Override
    public PlayerStructure findPlayerByName(String accountName) throws Exception {

        String targetUrl = buildUrl(accountName);

        //http запрос-ответ.
        String jsonReplyString = HttpClient.create().get().uri(targetUrl).responseContent().aggregate().asString().block();
        System.out.println(jsonReplyString);
        System.out.println();

        //Получить нужный объект JSON. Передать данные структуре.
        PlayerStructure player = parser.getPlayerByNameFromResponse(jsonReplyString, accountName);
        return player;

    }

    //Склеить адрес для запроса.
    private String buildUrl(String name) {
        String url = ("https://gameinfo.albiononline.com/api/gameinfo/search?q=" + name);
        return url;
    }



}
