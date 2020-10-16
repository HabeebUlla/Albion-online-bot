package com.github.ElgorTheWanderer.AlbionClient;

import com.github.ElgorTheWanderer.PlayerStructure;
import org.json.JSONArray;
import org.json.JSONObject;
import reactor.netty.http.client.HttpClient;

public class AlbionClientSyncImpl implements AlbionClient {

    private static final AlbionClientSyncImpl instance = new AlbionClientSyncImpl();
    AlbionClientResponseParser parser = new AlbionClientResponseParser();


    private AlbionClientSyncImpl() {
    }

    public static AlbionClientSyncImpl getInstance() {
        return instance;
    }

    @Override
    public PlayerStructure findPlayerByName(String accountName) throws Exception {

        String targetUrl = buildUrl(accountName);
        String jsonReplyString = HttpClient.create().get().uri(targetUrl).responseContent().aggregate().asString().block();
        PlayerStructure player = parser.getPlayerByNameFromResponse(jsonReplyString, accountName);
        return player;
    }

    private String buildUrl(String name) {
        String url = ("https://gameinfo.albiononline.com/api/gameinfo/search?q=" + name);
        return url;
    }


}
