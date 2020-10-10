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


    @Override
    public PlayerStructure findPlayerByName(String accountName) throws Exception {

        String targetUrl = buildUrl(accountName);

        //http запрос-ответ.
        String jsonReplyString = HttpClient.create().get().uri(targetUrl).responseContent().aggregate().asString().block();
        System.out.println(jsonReplyString);
        System.out.println();

        //Получить нужный объект JSON. Передать данные структуре.
        PlayerStructure player = parseResultToStructure(jsonReplyString, accountName);
        return player;

    }

    //Склеить адрес для запроса.
    private String buildUrl(String name) {
        String url = ("https://gameinfo.albiononline.com/api/gameinfo/search?q=" + name);
        return url;
    }

    //Получить объект JSON с нужным именем игрока. Передать данные структуре.
    private PlayerStructure parseResultToStructure(String JSONString, String name) throws Exception {
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
